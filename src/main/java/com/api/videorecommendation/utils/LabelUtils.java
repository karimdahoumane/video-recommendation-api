package com.api.videorecommendation.utils;

import java.util.List;

public class LabelUtils {

	public static int countCommonLabels(List<String> labels1, List<String> labels2) {
		return Math.toIntExact(labels1.stream().distinct().filter(labels2::contains).count());
	}
}