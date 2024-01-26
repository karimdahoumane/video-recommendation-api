package com.api.videorecommendation.video;

import com.api.videorecommendation.VideoRecommendationApplication;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(
  webEnvironment = SpringBootTest.WebEnvironment.MOCK,
  classes = VideoRecommendationApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
  locations = "classpath:application-integrationtest.properties")
class VideoControllerIntegrationTest {

	 @Autowired
	 private MockMvc mockMvc;
	 
	 @Autowired
	 private ObjectMapper objectMapper;

	 @Test
	 public void givenVideo_whenCreateVideo_thenStatus201() throws Exception {
	 Video video = new Video(UUID.randomUUID(), "matrix", List.of("sci-fi", "dystopia"));
	 String videoJson = objectMapper.writeValueAsString(video);
	 
	 mockMvc.perform(post("/api/video")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(videoJson))
	                .andExpect(status().isCreated())
	                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
	                .andExpect(jsonPath("$.id").isNotEmpty())
	                .andExpect(jsonPath("$.title").value(video.getTitle()))
	                .andExpect(jsonPath("$.labels", hasSize(2)))
	                .andExpect(jsonPath("$.labels", hasItems("sci-fi", "dystopia")));
	 }

}