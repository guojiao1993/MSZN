package chapter_5_stringproblem;

public class Problem_02_AllNumbersSum {

	public static int numSum(String str) {
		if (str == null) {
			return 0;
		}
		char[] charArr = str.toCharArray();
		int res = 0;
		int num = 0;
		int posi = 1;
		int cur = 0;
		for (int i = 0; i < charArr.length; i++) {
			cur = charArr[i] - '0';
			// 处理非数字字符
			if (cur < 0 || cur > 9) {
				// 此时要将数字值加到结果值中，然后数字值清零
				res += num;
				num = 0;
				// 处理'-'
				if (charArr[i] == '-') {
					// 处理连续的'-'时，连续取反
					if (i - 1 > -1 && charArr[i - 1] == '-') {
						posi *= -1;
					// 初次遇到'-'，直接置负
					} else {
						posi = -1;
					}
				// 对于其他非数字字符，直接将正负符号位置正
				} else {
					posi = 1;
				}
			// 对于连续数字中的值，直接累乘累加，注意正负符号
			} else {
				num = num * 10 + posi * cur;
			}
		}
		// 最后时需要再将数字值加到结果值中，发生在后面没有非数字字符来触发的情况
		res += num;
		return res;
	}

	public static void main(String[] args) {
		System.out.println(numSum("A1CD2E33"));
		System.out.println(numSum("A-1B--2C--D6E"));
	}

}
