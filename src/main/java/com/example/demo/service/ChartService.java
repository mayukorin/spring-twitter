package com.example.demo.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.demo.model.Dorama;
import com.example.demo.model.DoramaFavoriteCount;
import com.example.demo.model.Season;



import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChartService {


	private final DoramaFavoriteCountService doramaFavoriteCountDayService;
	private final DoramaService doramaService;

	public Map<List<String>,Map<String,List<Long>>> createChart(Season s) {

		List<Dorama> doramas = doramaService.collectDoramaBySeason(s.getId());
		Map<List<String>,Map<String,List<Long>>> monthDatas = new LinkedHashMap<>();

		if (doramas.size() > 0) {
			Calendar startDay = (Calendar)s.getSeasonStartDay().clone();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM");




			for (;startDay.compareTo(s.getSeasonEndDay()) <= 0;startDay.add(Calendar.MONTH, 1)) {

				Map<String,List<Long>> countData = new LinkedHashMap<>();

				List<String> days = doramaFavoriteCountDayService.collectFavoriteCountDay(sdf2.format(startDay.getTime()),s.getId());

				if (days.size() == 0) {
					days.add(sdf.format(startDay.getTime()));
					days.add(sdf2.format(startDay.getTime())+"-14");

				}


				for (Dorama d:doramas) {

					List<Long> counts = new ArrayList<>();
					for (String day:days) {
						DoramaFavoriteCount df = doramaFavoriteCountDayService.collectFavoriteCountByDoramaAndCreatedAt(day, d.getId());


						if (df == null) {
							counts.add(0L);

						} else {
							counts.add(df.getFavoriteCount());
						}

					}
					countData.put(d.getName(),counts);
				}

				monthDatas.put(days,countData);


			}
		}



		return monthDatas;


	}
}
