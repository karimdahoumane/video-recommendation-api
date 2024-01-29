package com.api.videorecommendation.utils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LabelUtils {

	public static int countCommonLabels(List<String> labels1, List<String> labels2) {
		Set<String> set1 = new HashSet<>(labels1);
		Set<String> set2 = new HashSet<>(labels2);
		set1.retainAll(set2);
		return set1.size();
	}
}