package com.api.videorecommendation.video;

import static org.hamcrest.Matchers.containsInAnyOrder;
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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.api.videorecommendation.VideoRecommendationApplication;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = VideoRecommendationApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class VideoControllerIntegrationTest {

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

	@Test
	public void givenTitle_whenGetVideosByTitle_thenStatus200() throws Exception {
		Video video1 = new Video(UUID.randomUUID(), "matrix", List.of("sci-fi", "dystopia"));
		Video video2 = new Video(UUID.randomUUID(), "indiana jones", List.of("adventurers", "action"));
		Video video3 = new Video(UUID.randomUUID(), "les indestructibles", List.of("comedy", "action"));

		videoService.createVideo(video1);
		videoService.createVideo(video2);
		videoService.createVideo(video3);

		String titleInput = "ind";

		mockMvc.perform(get("/api/video/search/{title}", titleInput)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[*].title", containsInAnyOrder(video2.getTitle(), video3.getTitle())));
	}

	@Test
	public void givenVideos_whenGetAllMovies_thenStatus200() throws Exception {
		Movie movie1 = new Movie(UUID.randomUUID(), "Indiana Jones : Raiders of the Lost Ark",
				List.of("adventure", "whip", "archeology"), "Steven Spielberg", "1982-03-18");
		Movie movie2 = new Movie(UUID.randomUUID(), "Society of the Snow", List.of("drama", "thriller", "history"),
				"Juan Antonio García Bayona", "2023-10-16");
		Show show = new Show(UUID.randomUUID(), "Breaking Bad", List.of("chemistry", "drug", "desert", "cancer"), 62);

		videoService.createVideo(movie1);
		videoService.createVideo(movie2);
		videoService.createVideo(show);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/video/movies")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[*].title", containsInAnyOrder(movie1.getTitle(), movie2.getTitle())))
				.andExpect(jsonPath("$[*].director", containsInAnyOrder(movie1.getDirector(), movie2.getDirector())))
				.andExpect(jsonPath("$[*].release_date",
						containsInAnyOrder(movie1.getReleaseDate(), movie2.getReleaseDate())));
	}

	@Test
	public void givenVideos_whenGetAllShows_thenStatus200() throws Exception {
		Movie movie1 = new Movie(UUID.randomUUID(), "Indiana Jones : Raiders of the Lost Ark",
				List.of("adventure", "whip", "archeology"), "Steven Spielberg", "1982-03-18");
		Movie movie2 = new Movie(UUID.randomUUID(), "Society of the Snow", List.of("drama", "thriller", "history"),
				"Juan Antonio García Bayona", "2023-10-16");
		Show show = new Show(UUID.randomUUID(), "Breaking Bad", List.of("chemistry", "drug", "desert", "cancer"), 62);

		videoService.createVideo(movie1);
		videoService.createVideo(movie2);
		videoService.createVideo(show);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/video/shows")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[*].title", containsInAnyOrder(show.getTitle())))
				.andExpect(jsonPath("$[*].number_of_episodes", containsInAnyOrder(show.getNumberOfEpisodes())));
	}
}