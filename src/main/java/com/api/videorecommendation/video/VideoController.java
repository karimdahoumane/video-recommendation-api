package com.api.videorecommendation.video;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

	@PostMapping
	public ResponseEntity<Video> createVideo(@RequestBody Video video) {
		try {
			Video createdVideo = videoService.createVideo(video);
			return new ResponseEntity<>(createdVideo, HttpStatus.CREATED);
		} catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
}
