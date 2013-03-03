package de.flapdoodle.tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// replace with google collections
public class Lists {

	public static <T> List<T> newArrayList(T...values) {
		ArrayList<T> ret = new ArrayList<T>(values.length);
		ret.addAll(Arrays.asList(values));
		return ret;
	}
}
