package chapter_4_recursionanddp;

import java.util.HashMap;

public class Problem_16_LongestConsecutive {

	public static int longestConsecutive(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int max = 1;
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (int i = 0; i < arr.length; i++) {
			if (!map.containsKey(arr[i])) {
				map.put(arr[i], 1);
				if (map.containsKey(arr[i] - 1)) {
					// 与前面的数进行合并
					max = Math.max(max, merge(map, arr[i] - 1, arr[i]));
				}
				if (map.containsKey(arr[i] + 1)) {
					// 与后面的数进行合并
					max = Math.max(max, merge(map, arr[i], arr[i] + 1));
				}
			}
		}
		return max;
	}

	public static int merge(HashMap<Integer, Integer> map, int less, int more) {
		// 对于less，其长度是往前数
		int left = less - map.get(less) + 1;
		// 对于more，其长度是往后数
		int right = more + map.get(more) - 1;
		int len = right - left + 1;
		// 更新最大最小值的长度，对于最小值，是往后（中心）数，对于最大值，是往前（中心）数
		map.put(left, len);
		map.put(right, len);
		return len;
	}

	public static void main(String[] args) {
		System.out.println(longestConsecutive(new int[] { 100, 4, 200, 1, 3, 2 }));
		System.out.println(longestConsecutive(new int[] { 100, 4, 101, 1, 99, 102, 5, 2 }));
		System.out.println(longestConsecutive(new int[] { 100, 4, 101, 1, 99, 102, 3, 2, 0 }));
	}

}
