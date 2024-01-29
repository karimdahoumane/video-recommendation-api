package com.api.videorecommendation.video;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class VideoService {
	private final Map<UUID, Video> inMemoryDatabase = new HashMap<>();

	public List<Video> getAllVideos() {
		return new ArrayList<>(inMemoryDatabase.values());
	}

	public Video getVideoById(UUID id) {
		return inMemoryDatabase.get(id);
	}

	public List<Video> getVideosByTitle(String title) {
		List<Video> matchingVideos = new ArrayList<>();

		for (Video video : inMemoryDatabase.values()) {
			if (video.getTitle().toLowerCase().contains(title.toLowerCase()) && title.length() >= 3) {
				matchingVideos.add(video);
			}
		}
		return matchingVideos;
	}

	public Video createVideo(Video video) {
		UUID uuid = UUID.randomUUID();
		video.setId(uuid);
		inMemoryDatabase.put(uuid, video);
		return video;
	}

	public List<Movie> getAllMovies() {
		List<Movie> movies = inMemoryDatabase.values().stream().filter(video -> video instanceof Movie)
				.map(video -> (Movie) video).collect(Collectors.toList());
		return movies;
	}

	public List<Show> getAllShows() {
		List<Show> shows = inMemoryDatabase.values().stream().filter(video -> video instanceof Show)
				.map(video -> (Show) video).collect(Collectors.toList());
		return shows;
	}
}
