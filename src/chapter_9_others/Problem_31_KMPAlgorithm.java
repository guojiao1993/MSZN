package chapter_9_others;

public class Problem_31_KMPAlgorithm {

	public static int getIndexOf(String s, String m) {
		if (s == null || m == null || m.length() < 1 || s.length() < m.length()) {
			return -1;
		}
		char[] ss = s.toCharArray();
		char[] ms = m.toCharArray();
		int si = 0;
		int mi = 0;
		int[] next = getNextArray(ms);
		while (si < ss.length && mi < ms.length) {
			// 当前字符相等时，同时向后移动
			if (ss[si] == ms[mi]) {
				si++;
				mi++;
			// 若回到m的起点，则s向右移动
			} else if (next[mi] == -1) {
				si++;
			// 跳过m中的最大公共前缀，从m中的最大公共前缀后的第一个字符继续匹配
			} else {
				mi = next[mi];
			}
		}
		// 若mi==ms.length，说明m被完全匹配，则返回s中的匹配起始位置si-mi，否则返回-1
		return mi == ms.length ? si - mi : -1;
	}

	/**
	 * next[i]的含义是在ms[i]之前的字符串ms[0..i-1]中，必须以ms[i-1]结尾的后缀子串（不包括ms[0]）
	 * 与必须以ms[0]开头的前缀子串（不包含ms[i-1]）的最大匹配长度是多少。
	 * <p>注意，next[0]=-1，next[1]=0
	 */
	public static int[] getNextArray(char[] ms) {
		if (ms.length == 1) {
			return new int[] { -1 };
		}
		int[] next = new int[ms.length];
		next[0] = -1;
		next[1] = 0;
		int pos = 2;
		// cn表示当前字符的前一个字符前的前后缀最大长度
		int cn = next[pos - 1];
		// 计算所有的next[]值
		while (pos < next.length) {
			// 若当前字符的前一个字符和当前字符的前一个字符前的最大前缀后的第一个字符相等，
			// 则当前字符前的最大前后缀长度为cn+1。
			if (ms[pos - 1] == ms[cn]) {
				next[pos++] = ++cn;
			// 当cn>0，利用nc及next，迭代此过程，直到找到和当前字符的前一个字符相等的最大前缀
			} else if (cn > 0) {
				cn = next[cn];
			// 当cn<=0时，将当前字符前的最大前后缀长度置为0。
			} else {
				next[pos++] = 0;
			}
		}
		return next;
	}

	public static void main(String[] args) {
		System.out.println(getIndexOf("abcabcababaccc", "ababa"));
		System.out.println(getIndexOf("abcabcababaccc", "ababc"));
		System.out.println(getIndexOf("你好啊你好啊你好你好你啊啊啊", "你好你好你"));
		System.out.println(getIndexOf("你好啊你好啊你好你好你啊啊啊", "你好你好好"));
	}

}
