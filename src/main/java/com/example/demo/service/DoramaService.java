package com.example.demo.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.Channel;
import com.example.demo.model.Dorama;
import com.example.demo.model.DoramaFavoriteCount;
import com.example.demo.model.Favorite;
import com.example.demo.model.Season;
import com.example.demo.model.SiteUser;
import com.example.demo.repository.*;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DoramaService {

	private final DoramaRepository doramaRepository;
	private final ChannelRepository channelRepository;
	private final DoramaFavoriteCountService doramaFavoriteCountService;
	private final SeasonService seasonService;
	private final ChannelService channelService;
	private final FavoriteService favoriteService;
	private final ArticleService articleService;


	public Dorama getDoramaById(Long id) {
		return doramaRepository.findById(id).get();
	}

	public void insert(Dorama dorama,String start,String end,SiteUser user) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		try {
			Date dateStart = dateFormat.parse(start);
			Date dateEnd = dateFormat.parse(end);

			Calendar endCalendar = Calendar.getInstance();
			endCalendar.setTime(dateEnd);
			dorama.setEndDay(endCalendar);

			Calendar startCalendar = Calendar.getInstance();
			startCalendar.setTime(dateStart);
			dorama.setStartDay(startCalendar);


			dorama.setCreater(user);



			if (seasonService.collectStartMonth(start.substring(0,7)) == 0) {
				Season newSeason = seasonService.insert(startCalendar,endCalendar);
				dorama.setSeason(newSeason);

			} else {
				Season s = seasonService.CollectSeasonByStartMonth(start.substring(0,7));

				if (endCalendar.compareTo(s.getSeasonEndDay()) > 0) {
					s = seasonService.update(s,endCalendar);

				}

				dorama.setSeason(s);


			}

			doramaRepository.save(dorama);

			Channel c = new Channel();
			c.setDorama(dorama);
			c.setTitle("放送日について");

			channelRepository.save(c);

			articleService.defaultInsert(c);

		} catch (ParseException e) {

			System.out.println("日付に変換できませんでした");
		}

	}



	public void update(Dorama dorama,String start,String end,SiteUser user) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		try {
			Date dateStart = dateFormat.parse(start);
			Date dateEnd = dateFormat.parse(end);

			Calendar endCalendar = Calendar.getInstance();
			endCalendar.setTime(dateEnd);
			dorama.setEndDay(endCalendar);

			Calendar startCalendar = Calendar.getInstance();
			startCalendar.setTime(dateStart);
			dorama.setStartDay(startCalendar);


			if (seasonService.collectStartMonth(start.substring(0,7)) == 0) {
				Season newSeason = seasonService.insert(startCalendar,endCalendar);
				dorama.setSeason(newSeason);

			} else {
				Season s = seasonService.CollectSeasonByStartMonth(start.substring(0,7));

				if (endCalendar.compareTo(s.getSeasonEndDay()) > 0) {
					s = seasonService.update(s,endCalendar);

				}

				dorama.setSeason(s);


			}




			doramaRepository.save(dorama);


		} catch (ParseException e) {

			System.out.println("日付に変換できませんでした");
		}

	}

	public void delete(Long id) {

		List<Channel> channels = channelService.getChannelsByDoramaId(id);

		for (Channel channel:channels) {

			channelService.delete(channel.getId());

		}

		List<Favorite> favorites = favoriteService.collectFavoritesByDorama(id);

		for (Favorite f:favorites) {
			favoriteService.deleteById(f.getId());
		}

		List<DoramaFavoriteCount> dfs = doramaFavoriteCountService.collectDoramaFavoriteCountByDorama(id);

		for (DoramaFavoriteCount df:dfs) {

			doramaFavoriteCountService.deleteById(df.getId());
		}
		doramaRepository.deleteById(id);
	}

	public List<String> collectStartMonth() {


		return doramaRepository.collectStartMonth();
	}

	public List<Dorama> collectDoramaByStart(String d) {

		List<Dorama> doramas = doramaRepository.collectDoramaByStart(d);
		if (doramas.size() > 0) {
			Calendar latestStartDay = (Calendar)doramas.get(0).getStartDay().clone();
			System.out.println(latestStartDay.getTime());
			latestStartDay.add(Calendar.MONTH, -1);
			System.out.println(latestStartDay.getTime());
		}
		return doramaRepository.collectDoramaByStart(d);
	}


	public String[] collectDoramaNameByStart(String d) {
		return doramaRepository.collectDoramaNameByStart(d).toArray(new String[doramaRepository.collectDoramaNameByStart(d).size()]);

	}

	public List<Dorama> collectDoramaFavoriteByUser(Long id) {

		return doramaRepository.collectDoramaFavoriteByUser(id);
	}

	public List<Dorama> collectDoramaBySeason(Long id) {
		return doramaRepository.collectDoramaBySeason(id);
	}

	public List<Dorama> collectDoramaByKeyword(String keyword) {
		return doramaRepository.collectDoramaByKeyword(keyword);
	}

	public List<Dorama> collectDoramaByCreater(Long id) {
		return doramaRepository.collectDoramaByCreater(id);
	}

	public Dorama findById(Long id) {
		return doramaRepository.findById(id).get();
	}









}
