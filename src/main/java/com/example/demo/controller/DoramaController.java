package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.component.DoramaComponent;
import com.example.demo.model.Dorama;
import com.example.demo.service.ChartService;
import com.example.demo.service.DailyCountService;
import com.example.demo.service.DoramaService;
import com.example.demo.service.SeasonService;
import com.example.demo.service.SessionService;
import com.example.demo.service.UserDetailsImpl;
import com.example.demo.validator.startAndEndDayValidator;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class DoramaController {
	
	
	
	private final startAndEndDayValidator startAndEndDayValidator;
	private final DoramaService doramaService;
	private final SessionService sessionService;
	private final SeasonService seasonService;
	private final ChartService chartService;
	private final DailyCountService dailyCountService;
	
	@Autowired
	DoramaComponent targetDoramaComponent;
	
	
	@GetMapping("/topPage")
	public String topPage(Model model,@AuthenticationPrincipal UserDetailsImpl userDetail) {
		
		sessionService.setNullTargetDoramaComponent();
		dailyCountService.dailyCount(userDetail.getSiteUser());
		model.addAttribute("seasonss", seasonService.findAllSeasons());
		
		return "topPage";
	}
	
	@GetMapping("/doramaNew")
	public String doramaNew(@ModelAttribute("dorama") Dorama dorama) {
		
		return "doramaNew";
	}
	
	@PostMapping("/dorama_create")
	public String doramaCreate(@Validated @ModelAttribute("dorama") Dorama dorama,BindingResult result,@RequestParam("start") String start,@RequestParam("end") String end,Model model,@AuthenticationPrincipal UserDetailsImpl userDetail) {
		
		if (result.hasErrors() || !startAndEndDayValidator.StartAndEndDayCheck(start,end)) {
			
			if (!startAndEndDayValidator.StartAndEndDayCheck(start,end)) {
				model.addAttribute("dateError", "放送開始日より放送終了日が後になるように入力してください。");
			}
			
			model.addAttribute("start", start);
			model.addAttribute("end", end);
			
			return "doramaNew";
		} 
		
		doramaService.insert(dorama, start, end,userDetail.getSiteUser());
		
		return "redirect:/topPage?createSuccess";
		
		
	}
	
	@GetMapping("/doramaEdit/{id}/{fromFlag}")
	public String doramaEdit(@PathVariable Long id,@PathVariable Long fromFlag,Model model) {
		
		model.addAttribute("dorama", doramaService.getDoramaById(id));
		System.out.println(fromFlag);
		sessionService.setTargetDoramaComponent(id);
		
		model.addAttribute("sessionService", sessionService);
		model.addAttribute("start",doramaService.getDoramaById(id).translateCalendarToString1(doramaService.getDoramaById(id).getStartDay()));
		model.addAttribute("end", doramaService.getDoramaById(id).translateCalendarToString1(doramaService.getDoramaById(id).getEndDay()));
		model.addAttribute("fromFlag", fromFlag);
		return "doramaEdit";
	}
	
	@PostMapping("/dorama_update{fromFlag}") 
	public String doramaUpdate(@PathVariable Long fromFlag,@Validated @ModelAttribute Dorama dorama,BindingResult result,@RequestParam("start") String start,@RequestParam("end") String end,Model model,@AuthenticationPrincipal UserDetailsImpl userDetail) {
		
		
		if (result.hasErrors() || !startAndEndDayValidator.StartAndEndDayCheck(start,end)) {
					
					if (!startAndEndDayValidator.StartAndEndDayCheck(start,end)) {
						model.addAttribute("dateError", "放送開始日より放送終了日が後になるように入力してください。");
					}
					
					model.addAttribute("start", start);
					model.addAttribute("end", end);
					
					return "doramaEdit";
		} 
		
		System.out.println(fromFlag);
		doramaService.update(dorama, start, end,userDetail.getSiteUser());
		
		if (fromFlag == 0) {
			return "redirect:/doramaIndex"+dorama.getSeason().getId()+"?doramaUpdateSuccess";
		} else if (fromFlag == 1) {
			return "redirect:/my_doramas"+"?doramaUpdateSuccess";
		} else {
			return "redirect:/favorite_doramas"+"?doramaUpdateSuccess";
		}
		
	}
	
	@GetMapping("/doramaIndex{id}")
	public String doramaIndex(@PathVariable Long id,Model model) {
		
		
		sessionService.setSeasonComponent(id);
		
		model.addAttribute("couontDatas", chartService.createChart(sessionService.getSeasonComponent().getSeason()));
		
		model.addAttribute("doramas",doramaService.collectDoramaBySeason(sessionService.getSeasonComponent().getSeason().getId()));
		model.addAttribute("sessionService", sessionService);
	
		return "doramaIndex";
	}
	
	@GetMapping("/favorite_doramas")
	public String favoriteDoramas(@AuthenticationPrincipal UserDetailsImpl userDetail,Model model) {
		
		model.addAttribute("favoriteDoramas",doramaService.collectDoramaFavoriteByUser(userDetail.getSiteUser().getId()));
		return "favoriteDoramas";
	}
	
	@PostMapping("/dorama_delete{id}")
	public String doramaDelete(@PathVariable Long id) {
		
		doramaService.delete(id);
	
		return "redirect:/doramaIndex"+sessionService.getSeasonComponent().getSeason().getId()+"?delete";
	}
	
	@PostMapping("/searchDorama")
	public String searhDorama(@RequestParam("keyword") String keyword,Model model) {
		
		model.addAttribute("doramas",doramaService.collectDoramaByKeyword(keyword));
		model.addAttribute("keyword", keyword);
		return "resultDoramas";
	}
	
	@GetMapping("/my_doramas")
	public String myDoramas(@AuthenticationPrincipal UserDetailsImpl userDetail,Model model) {
		
		model.addAttribute("doramas",doramaService.collectDoramaByCreater(userDetail.getSiteUser().getId()));
		return "myDoramas";
	}

}
