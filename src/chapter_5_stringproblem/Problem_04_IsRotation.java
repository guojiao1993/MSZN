package chapter_5_stringproblem;

public class Problem_04_IsRotation {

	public static boolean isRotation(String a, String b) {
		if (a == null || b == null || a.length() != b.length()) {
			return false;
		}
		return getIndexOf(b + b, a) != -1;
	}

	// KMP Algorithm
	public static int getIndexOf(String s, String m) {
		if (s.length() < m.length()) {
			return -1;
		}
		char[] ss = s.toCharArray();
		char[] ms = m.toCharArray();
		int si = 0;
		int mi = 0;
		int[] next = getNextArray(ms);
		while (si < ss.length && mi < ms.length) {
			if (ss[si] == ms[mi]) {
				si++;
				mi++;
			} else if (next[mi] == -1) {
				si++;
			} else {
				mi = next[mi];
			}
		}
		return mi == ms.length ? si - mi : -1;
	}

	public static int[] getNextArray(char[] ms) {
		if (ms.length == 1) {
			return new int[] { -1 };
		}
		int[] next = new int[ms.length];
		next[0] = -1;
		next[1] = 0;
		int pos = 2;
		int cn = 0;
		while (pos < next.length) {
			if (ms[pos - 1] == ms[cn]) {
				next[pos++] = ++cn;
			} else if (cn > 0) {
				cn = next[cn];
			} else {
				next[pos++] = 0;
			}
		}
		return next;
	}

	public static void main(String[] args) {
		System.out.println(isRotation("zuochengyun", "yunzuocheng"));
		System.out.println(isRotation("zuochengyun", "zuochengyun"));
		System.out.println(isRotation("zuochengyun", "yunzuochenz"));
		System.out.println(isRotation("你好啊你好啊", "啊你好啊你好"));
		System.out.println(isRotation("你好啊你好啊", "啊你好好你好"));
	}

}
