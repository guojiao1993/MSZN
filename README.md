# 面试指南
本项目是《左程云. 程序员代码面试指南：IT名企算法与数据结构题目最优解[M]. 电子工业出版社, 2015》的配套代码  
使用Eclipse开发和测试
# 第5章 字符串问题
## 判断两个字符串是否互为变形词
- 变形词：两个字符串中出现的字符种类和每种字符出现的次数都相同。  
- 使用数组或Map保存每个字符出现的次数；
- 两次遍历：遍历第一个字符串时，累加并保存每个字符出现的次数；遍历第二个字符串时，累减并保存每个字符出现的次数，若中途出现次数为负数的情况，返回假，遍历完毕后返回真；
- 数组大小为256，对应英文字符的情况；对于中文等非英文情况，使用Map保存次数。  

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