package chapter_5_stringproblem;

import java.util.HashMap;
import java.util.Map;

public class Problem_01_IsDeformation {

	public static boolean isDeformation(String str1, String str2) {
		if (str1 == null || str2 == null || str1.length() != str2.length()) {
			return false;
		}
		char[] chas1 = str1.toCharArray();
		char[] chas2 = str2.toCharArray();
		int[] map = new int[256];
		for (int i = 0; i < chas1.length; i++) {
			map[chas1[i]]++;
		}
		for (int i = 0; i < chas2.length; i++) {
			if (map[chas2[i]]-- == 0) {
				return false;
			}
		}
		return true;
	}

	public static boolean isDeformationUsingMap(String str1, String str2) {
		if (str1 == null || str2 == null || str1.length() != str2.length()) {
			return false;
		}
		Map<String, Integer> map = new HashMap<>();
		for (int i = 0; i < str1.length(); i++) {
			String s = str1.substring(i, i + 1);
			if(map.containsKey(s)) {
				map.put(s, map.get(s) + 1);
			} else {
				map.put(s, 1);
			}
		}
		for (int i = 0; i < str2.length(); i++) {
			String s = str2.substring(i, i + 1);
			if(map.containsKey(s)) {
				Integer num = map.get(s);
				if(num <= 0) {
					return false;
				} else {
					map.put(s, num - 1);
				}
			} else {
				return false;
			}
		}
		return true;
	}
	
	public static void main(String[] args) {
		String A = "abcabcabc";
		String B = "bcacbaacb";
		System.out.println(isDeformation(A, B));
		A = "ÄãºÃÄãÄãÄã";
		B = "ºÃÅ¶ÄãÄãÄã";
		System.out.println(isDeformationUsingMap(A, B));
	}

}
