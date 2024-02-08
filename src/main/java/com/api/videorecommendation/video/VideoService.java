package com.api.videorecommendation.video;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.api.videorecommendation.utils.LabelUtils;

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
		return inMemoryDatabase.values().stream()
				.filter(video -> video.getTitle().toLowerCase().contains(title.toLowerCase()) && title.length() >= 3)
				.collect(Collectors.toList());
	}

	public Video createVideo(Video video) {
		UUID uuid = UUID.randomUUID();
		video.setId(uuid);
		inMemoryDatabase.put(uuid, video);
		return video;
	}

	public List<Movie> getAllMovies() {
		return inMemoryDatabase.values().stream().filter(video -> video instanceof Movie).map(video -> (Movie) video)
				.collect(Collectors.toList());
	}

	public List<Show> getAllShows() {
		return inMemoryDatabase.values().stream().filter(video -> video instanceof Show).map(video -> (Show) video)
				.collect(Collectors.toList());
	}

	public List<Video> getSimilarVideos(Video originalVideo, int minCommonLabels) {
		return inMemoryDatabase.values().stream().filter(video -> !video.getId().equals(originalVideo.getId())).filter(
				video -> LabelUtils.countCommonLabels(originalVideo.getLabels(), video.getLabels()) >= minCommonLabels)
				.collect(Collectors.toList());
	}

}