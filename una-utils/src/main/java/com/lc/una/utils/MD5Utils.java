package com.lc.una.utils;

import cn.hutool.log.StaticLog;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5工具类
 *
 * @author xzx19950624@qq.com
 * @date 2017年9月24日16:40:20
 */
public class MD5Utils {

	/**
	 * MD5加码 生成32位md5码(不可逆的)
	 *
	 * @param inStr 输入字符串
	 * @return 结果
	 */
	public static String string2MD5(String inStr) {
		MessageDigest md5;
		String string = "";
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			StaticLog.error("MD5加密实现的错误日志-->>" + e.getMessage(), e);
			return string;
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];
		for (int i = 0; i < charArray.length; i++) {
			byteArray[i] = (byte) charArray[i];
		}
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		string = hexValue.toString();
		StaticLog.debug("MD5加密的32位密钥的调试日志-->>" + string);
		return string;
	}

	/**
	 * 加密解密算法 执行一次加密，两次解密
	 *
	 * @param inStr 输入字符串
	 * @return 结果
	 * @throws Exception
	 */
	public static String convertMD5(String inStr) throws Exception {
		char[] a = inStr.toCharArray();
		for (int i = 0; i < a.length; i++) {
			a[i] = (char) (a[i] ^ 't');
		}
		String string = new String(a);
		StaticLog.debug("MD5加密的二次加密的字符串的调试日志-->>" + string);
		return string;
	}

}
