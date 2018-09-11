package com.hhly.cms.utils;

import java.util.Random;

/**
 * 
 * @author HouXB
 *
 */
public class StringUtil {
	/**
	 * @see 将字符串左边填充指定字符，使字符串长度达到指定长度
	 * @param str 源字符串
	 * @param padchar 填充字符
	 * @param len 最终长度
	 * @return 返回长度为len的字符串
	 */
	public static String padLeft(String str, char padchar, int len)
	{
		String result = str;
		int i = str.length();
		for (; i < len; i++)
			result = padchar + result;
		return result;
	}

	/**
	 * @see 将字符串右边填充指定字符，使字符串长度达到指定长度
	 * @param str 源字符串
	 * @param padchar 填充字符
	 * @param len 最终长度
	 * @return 返回长度为len的字符串
	 */
	public static String padRight(String str, char padchar, int len)
	{
		StringBuffer sb = new StringBuffer(str);
		int i = sb.length();
		for (; i < len; i++)
			sb.append(padchar);
		return sb.toString();
	}

	/**
	 * 将字符串中每个字符之间增加一个分割符号
	 * @param str 012345
	 * @param pad | or . or ,
	 * @return 0|1|2|3|4|5
	 */
	public static String padSplitChar(String str, String pad)
	{
		StringBuffer result = new StringBuffer("");
		int size = str.length();
		for (int i = 0; i < size; i++)
		{
			if (i > 0)
			{
				result.append(pad);
			}
			result.append(str.charAt(i));
		}
		return result.toString();
	}

	public static String join(String[] arr, String split)
	{
		StringBuffer result = new StringBuffer("");
		for (int i = 0; i < arr.length; i++)
		{
			if (i > 0)
				result.append(split);
			result.append(arr[i]);
		}
		return result.toString();
	}

	public static String[] splitArr(String string)
	{
		String[] result = new String[string.length()];
		for (int i = 0; i < string.length(); i++)
			result[i] = String.valueOf(string.charAt(i));
		return result;
	}

	public static String[] splitArr(String value, int len)
	{
		if (value == null || value.equals(""))
			return null;
		if (len < 0)
			len = 0;
		int idx = value.length() / len;
		if (value.length() % len != 0)
			idx = idx + 1;
		String[] result = new String[idx];
		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < result.length; i++)
		{
			for (int j = 0; j < len; j++)
			{
				sb.append(String.valueOf(value.charAt(i * len + j)));
				if (i * len + j == value.length() - 1)
					break;
			}
			result[i] = sb.toString();
			sb.setLength(0);
		}
		return result;
	}

	public static String join(String[] arr)
	{
		StringBuffer result = new StringBuffer("");
		for (int i = 0; i < arr.length; i++)
		{
			result.append(arr[i]);
		}
		return result.toString();
	}

	public static String[] sort(String[] args)
	{
		int len = args.length;
		for (int i = 1; i < len; i++)
		{
			for (int j = 0; j < i; j++)
			{
				if (args[j].compareTo(args[i]) > 0)
				{
					String tmp = args[j];
					args[j] = args[i];
					args[i] = tmp;
				}
			}
		}
		return args;
	}

	public static String[] sortByNumber(String[] args)
	{
		return sortByNumber(args, false);
	}

	public static String[] sortByNumber(String[] args, boolean addZero)
	{
		int len = args.length;
		for (int i = 0; i < len; i++)
		{
			for (int j = i + 1; j < len; j++)
			{
				int x = Integer.parseInt(args[i]);
				int y = Integer.parseInt(args[j]);

				if (x > y)
				{
					String tmp = args[j];
					args[j] = args[i];
					args[i] = tmp;
				}
			}
			if (addZero)
			{
				int x = Integer.parseInt(args[i]);
				if (x < 10)
					args[i] = "0" + x;
			}
		}

		return args;
	}

	public static String[] padZeroNumberString(String[] strarr)
	{
		for (int i = 0; i < strarr.length; i++)
		{
			if (Integer.parseInt(strarr[i]) < 10)
				strarr[i] = "0" + strarr[i];
		}
		return strarr;
	}

	/**
	 * 给一串包含数字的字符串，各个小于10的数字前补0
	 * @param str
	 * @param splitstr
	 * @return
	 */
	public static String padZeroNumberString(String str, String splitstr)
	{
		String[] strarr = StringUtil.padZeroNumberString(str.split("[" + splitstr + "]"));
		return StringUtil.join(strarr, splitstr);
	}

	/**
	 * 清除一串包含数字的字符串中<10前面的0 01,02,10,20 结果：1,2,10,20
	 * @param str
	 * @param splitstr 字符串的分割符,如 ",","|"
	 * @return
	 */
	public static String clearZeroNumberString(String str, String splitstr)
	{
		if (splitstr == null || splitstr.equals(""))
			return str;
		String[] strarr = str.split("[" + splitstr + "]");
		StringBuffer sb = new StringBuffer();
		int x = 0;
		for (String number : strarr)
		{
			x = Integer.parseInt(number);
			sb.append("" + x).append(splitstr);

		}
		sb.setLength(sb.length() - 1);
		return sb.toString();
	}

	/**
	 * 清除一串包含数字的字符串中<10前面的0 01,02,10,20|03,09 结果：1,2,10,20|3,9
	 * "|"為第split2,","為split1
	 * @param str
	 * @param split1 字符串的第二分割符,如 ",","|"
	 * @param split2 字符串的第一分割符,如 ",","|"
	 * @return
	 */
	public static String clearZeroNumberString(String str, String split1, String split2)
	{

		if (split2 == null || split1 == null || split1.equals(""))
			return str;
		if (split2.equals(""))
			return StringUtil.clearZeroNumberString(str, split1);
		StringBuffer sb = new StringBuffer();
		String[] strarr = str.split("[" + split2 + "]");
		for (String number : strarr)
		{
			sb.append(StringUtil.clearZeroNumberString(number, split1)).append(split2);
		}
		sb.setLength(sb.length() - 1);
		return sb.toString();

	}

	/**
	 * 
	 */
	public static String splitStr(String str, String split1, String split2, String playtype, String modeplay, int multiple)
	{
		if (str == null || str.equals(""))
			return null;
		String[] arr = str.split("[" + split1 + "]");
		if (arr.length < 2)
		{
			str = str + ":" + playtype + ":" + modeplay + ":" + multiple;
			return str;
		}
		StringBuffer sb = new StringBuffer();
		for (String number : arr)
		{
			sb.append(number).append(":").append(playtype).append(":").append(modeplay).append(":").append(multiple).append(split2);
		}
		sb.setLength(sb.length() - 1);
		return sb.toString();
	}
	/**
	 * 生成随机字符串
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2018年1月9日 下午3:58:50
	 * @param length
	 * @return
	 */
	public static String randomNumber(int length){
		String str = "";
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			str += random.nextInt(10);
		}
		return str;
	}
}
