package com.api.videorecommendation.video;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type", use = JsonTypeInfo.Id.NAME, defaultImpl = Video.class)
@JsonSubTypes({ @JsonSubTypes.Type(value = Movie.class, name = "movie"),
		@JsonSubTypes.Type(value = Show.class, name = "show") })
public class Video {
	private UUID id;
	private String title;
	private List<String> labels;

	public Video(UUID id, String title, List<String> labels) {
		this.id = id;
		this.title = title;
		this.labels = labels;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getLabels() {
		return labels;
	}

	public void setLabels(List<String> labels) {
		this.labels = labels;
	}

}
