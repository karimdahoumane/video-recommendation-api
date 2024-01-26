package com.api.videorecommendation.video;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.api.videorecommendation.VideoRecommendationApplication;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = VideoRecommendationApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
class VideoControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private VideoService videoService;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void givenVideo_whenCreateVideo_thenStatus201() throws Exception {
		Video video = new Video(UUID.randomUUID(), "matrix", List.of("sci-fi", "dystopia"));
		String videoJson = objectMapper.writeValueAsString(video);

		mockMvc.perform(post("/api/video").contentType(MediaType.APPLICATION_JSON).content(videoJson))
				.andExpect(status().isCreated()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").isNotEmpty()).andExpect(jsonPath("$.title").value(video.getTitle()))
				.andExpect(jsonPath("$.labels", hasSize(2)))
				.andExpect(jsonPath("$.labels", hasItems("sci-fi", "dystopia")));
	}

	@Test
	public void givenVideoId_whenGetVideoById_thenStatus200() throws Exception {
		Video video = new Video(UUID.randomUUID(), "matrix", List.of("sci-fi", "dystopia"));
		UUID createdVideoId = videoService.createVideo(video).getId();

		mockMvc.perform(get("/api/video/{id}", createdVideoId)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(createdVideoId.toString()))
				.andExpect(jsonPath("$.title").value(video.getTitle())).andExpect(jsonPath("$.labels", hasSize(2)))
				.andExpect(jsonPath("$.labels", hasItems("sci-fi", "dystopia")));
	}

}