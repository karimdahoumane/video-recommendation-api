package com.api.videorecommendation.video;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonPropertyOrder({ "id", "title", "director", "release_date", "labels" })
@JsonTypeName("movie")
public class Movie extends Video {
	private String director;
	@JsonProperty("release_date")
	private String releaseDate;

	public Movie(UUID id, String title, List<String> labels, String director, String releaseDate) {
		super(id, title, labels);
		this.director = director;
		this.releaseDate = releaseDate;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
}