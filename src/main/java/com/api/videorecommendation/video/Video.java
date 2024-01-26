package com.api.videorecommendation.video;

import java.util.List;
import java.util.UUID;

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
