# 面试指南
1. 本项目是《左程云. 程序员代码面试指南：IT名企算法与数据结构题目最优解[M]. 电子工业出版社, 2015》的配套代码  
1. 使用Eclipse开发和测试

# 第4章 递归和动态规划
## 最小编辑代价
1. 给定插入、删除、替换的代价，求将字符串str1编辑成str2的最小代价
1. 用动态规划思想，步步为营，时间和空间复杂度都为O(M*N)
1. 每个字符串最开始的时候都是空白字符串，即""，要编辑为任何非空字符都是插入的代价
1. 第一个字符str1为竖列，str2为横列，加上空白字符串，dp矩阵大小为[row1 + 1][col2 + 1]
1. dp[i][j]表示将str1[0 .. i]编辑为str2[0 .. j]的最小代价
1. 最终结果为dp[row1][col2]
 
dp矩阵中[i][j]只可能由四种情况而来：
1. 先删str1[i]，再将str1[0 .. i - 1]编辑成str2[0 .. j]，此时代价为dc + dp[i - 1][j]
1. 先将str1[0..i]编辑为str[0 .. j - 1]，再插入字符str2[j]，此时代价为ic + dp[i][j - 1]
1. 如果str1[i]和str2[j]不等，则先替换，再将str1[0 .. i - 1]替换为str2[0 .. j - 1]，此时代价为rc + dp[i - 1][j - 1]
1. 如果str1[i]和str2[j]相等，则无需替换，可只将str1[0 .. i - 1]替换为str2[0 .. j - 1]，此时代价为dp[i - 1][j - 1]

对于dp[i][j]，选取上述四种情况的最小值即可。

```java
public static int minCost1(String str1, String str2, int ic, int dc, int rc) {
	if (str1 == null || str2 == null) {
		return 0;
	}
	char[] chs1 = str1.toCharArray();
	char[] chs2 = str2.toCharArray();
	int row = chs1.length + 1;
	int col = chs2.length + 1;
	int[][] dp = new int[row][col];
	for (int i = 1; i < row; i++) {
		dp[i][0] = dc * i;
	}
	for (int j = 1; j < col; j++) {
		dp[0][j] = ic * j;
	}
	for (int i = 1; i < row; i++) {
		for (int j = 1; j < col; j++) {
			if (chs1[i - 1] == chs2[j - 1]) {
				dp[i][j] = dp[i - 1][j - 1];
			} else {
				dp[i][j] = dp[i - 1][j - 1] + rc;
			}
			dp[i][j] = Math.min(dp[i][j], dp[i][j - 1] + ic);
			dp[i][j] = Math.min(dp[i][j], dp[i - 1][j] + dc);
		}
	}
	return dp[row - 1][col - 1];
}
```

## 数组中的最长连续序列
1. [100,4,200,3,1,2]，最长连续序列是[1,2,3,4]，所以返回4
1. 动态规划，利用哈希表记录出现的数及其最大长度
1. 这里有一个很有意思的地方，就是一个数的最大长度是前向数还是向后数是视情况而定的，比如4，刚开始时是1，后来有了5，在merge时就是[4,5]，将4的长度更新为2，此时是往后数的长度，再遇到3时，还是往后数。但是如果一开始是[3,4]，那就不一样了，4的长度同样更新为2，但此时是往前数的长度，再遇到5时，还是往前数。
1. 只记录序列中的最大和最小值的长度，因为中间的值不会再被用到了。
1. 遇到一个数时，要尝试其前后相邻的数，前后数的顺序没有要求。

```java
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
```
# 第5章 字符串问题
## 判断两个字符串是否互为变形词
1. 变形词：两个字符串中出现的字符种类和每种字符出现的次数都相同。  
1. 使用数组或Map保存每个字符出现的次数；
1. 两次遍历：遍历第一个字符串时，累加并保存每个字符出现的次数；遍历第二个字符串时，累减并保存每个字符出现的次数，若中途出现次数为负数的情况，返回假，遍历完毕后返回真；
1. 数组大小为256，对应英文字符的情况；对于中文等非英文情况，使用Map保存次数。  

英文字符

```java
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
```
中文字符

```java
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
```
## 字符串中数字字串的求和
1. 忽略小数点
1. 对于'-'，连续偶数为正，奇数为负
1. 使用三个变量res、num、posi来表示结果值、目前值和符号值
1. 单独处理符号，posi初始为正，遇'-'时，判断前一位是否为'-'，若是则翻转，否则为负
1. 处理数字，连续乘十再相加即可，注意符号
1. 结尾时再加上最后的结果

```java
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
```
## 判断两个字符串是否互为旋转词
1. 要求解法的复杂度是O(N)
1. 注意到，str+str构成的字符串拥有所有的旋转词子串，所以问题转化为在str1中寻找和str2相同的子串
1. KMP算法可以在O(N)的时间复杂度下完成字符串匹配问题

```java
public static boolean isRotation(String a, String b) {
	if (a == null || b == null || a.length() != b.length()) {
		return false;
	}
	// getIndexOf()为KMP算法
	return getIndexOf(b + b, a) != -1;
}
```
# 第9章 其他题目
## KMP算法
1. KMP算法是由Donald Knuth、Vaughan Pratt和James H. Morris于1977年联合发明的。
1. 普通解法的复杂度是O(N*M)，N为待匹配字符串的长度，M为子串的长度。
1. KMP算法使用匹配字符串match的next特征数组来优化已匹配的部分。
1. next[i]的含义是在ms[i]之前的字符串ms[0..i-1]中，必须以ms[i-1]结尾的后缀子串（不包括ms[0]）与必须以ms[0]开头的前缀子串（不包含ms[i-1]）的最大匹配长度是多少。注意，next[0]=-1，next[1]=0。
1. next[]利用已生成的数组部分来迭代生成，以降低复杂度为O(M)。
1. 在匹配过程中，match向右滑动的长度最大为N，所以复杂度为O(N)。
1. 整个KMP算法的复杂度为O(N)。

```java
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
```