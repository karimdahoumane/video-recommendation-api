package com.api.videorecommendation.video;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonPropertyOrder({ "id", "title", "number_of_episodes", "labels" })
@JsonTypeName("show")
public class Show extends Video {
	@JsonProperty("number_of_episodes")
	private int numberOfEpisodes;

	public Show(UUID id, String title, List<String> labels, int numberOfEpisodes) {
		super(id, title, labels);
		this.numberOfEpisodes = numberOfEpisodes;
	}

	public int getNumberOfEpisodes() {
		return numberOfEpisodes;
	}

	public void setNumberOfEpisodes(int numberOfEpisodes) {
		this.numberOfEpisodes = numberOfEpisodes;
	}
}
