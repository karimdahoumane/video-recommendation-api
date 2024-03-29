package com.api.videorecommendation.video;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/video")
public class VideoController {

	private final VideoService videoService;

	public VideoController(VideoService videoService) {
		this.videoService = videoService;
	}

	@GetMapping
	public ResponseEntity<List<Video>> getAllVideos() {
		try {
			List<Video> videos = videoService.getAllVideos();
			return new ResponseEntity<>(videos, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Video> getVideoById(@PathVariable UUID id) {
		try {
			Video video = videoService.getVideoById(id);
			if (video != null) {
				return new ResponseEntity<>(video, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/search/{title}")
	public ResponseEntity<List<Video>> getVideosByTitle(@PathVariable String title) {
		try {
			List<Video> matchingVideos = videoService.getVideosByTitle(title);
			if (!matchingVideos.isEmpty()) {
				return new ResponseEntity<>(matchingVideos, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping
	public ResponseEntity<Video> createVideo(@RequestBody Video video) {
		try {
			Video createdVideo = videoService.createVideo(video);
			return new ResponseEntity<>(createdVideo, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/movies")
	public ResponseEntity<List<Movie>> getAllMovies() {
		try {
			List<Movie> movies = videoService.getAllMovies();
			if (!movies.isEmpty()) {
				return new ResponseEntity<>(movies, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/shows")
	public ResponseEntity<List<Show>> getAllShows() {
		try {
			List<Show> shows = videoService.getAllShows();
			if (!shows.isEmpty()) {
				return new ResponseEntity<>(shows, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/similar")
	public ResponseEntity<List<Video>> getSimilarVideos(@RequestParam UUID id, @RequestParam int minCommonLabels) {
		try {
			Video originalVideo = videoService.getVideoById(id);
			if (originalVideo == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

			List<Video> similarVideos = videoService.getSimilarVideos(originalVideo, minCommonLabels);
			return new ResponseEntity<>(similarVideos, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
