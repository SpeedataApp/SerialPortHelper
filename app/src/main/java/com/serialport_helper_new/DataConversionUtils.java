package com.serialport_helper_new;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 串口部分常用数据转换方法
 * @author Echo
 * @date 2015-6-29
 * @version 1.0
 */
public class DataConversionUtils {
	
	/**
	 * 
	 * hexString-byte[] "130632199104213021"->{0x13,0x06....,0x21}
	 * 
	 * @param hex
	 * @return
	 */
	public static byte[] hexStringToByteArray(String hex) {
		int len = (hex.length() / 2);
		byte[] result = new byte[len];
		char[] achar = hex.toCharArray();
		for (int i = 0; i < len; i++) {
			int pos = i * 2;
			result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
		}
		return result;
	}

	/**
	 * byte[]->String {0x23,0x32,0x12}-->"233212" 比如从卡里解析出身份证
	 * 
	 * @param src
	 * @return
	 */
	public static String byteArrayToString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder();
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	private static byte toByte(char c) {
		byte b = (byte) "0123456789ABCDEF".indexOf(c);
		return b;
	}

	/**
	 * byte[]->String {0x23,0x32,0x12}-->"23 32 12" 查看log
	 * 
	 * @param res
	 * @param len
	 * @return
	 */
	public static String byteArrayToStringLog(byte[] res, int len) {
		String result = "";
		for (int i = 0; i < len; i++) {
			result += String.format("%02x ", res[i]);
		}
		return result;
	}
	
	/**
	 * byte[]->ascii String {0x71,0x72,0x73,0x41,0x42}->"qrsAB"
	 * 
	 * @param cmds
	 * @return
	 */
	public static String byteArrayToAscii(byte[] cmds) {
		int tRecvCount = cmds.length;
		StringBuffer tStringBuf = new StringBuffer();
		String nRcvString;
		char[] tChars = new char[tRecvCount];
		for (int i = 0; i < tRecvCount; i++) {
			tChars[i] = (char) cmds[i];
		}
		tStringBuf.append(tChars);
		nRcvString = tStringBuf.toString(); // nRcvString从tBytes转成了String类型的"123"
		return nRcvString;
	}
	
	
	/**
	 * byte[]转int
	 * 
	 * @param bytes
	 * @return
	 */
	public static int byteArrayToInt(byte[] bytes) {
		int value = 0;
		// 由高位到低位
		for (int i = 0; i < bytes.length; i++) {
			int shift = (bytes.length - 1 - i) * 8;
			value += (bytes[i] & 0x000000FF) << shift;// 往高位游
		}
		return value;
	}

	
	/**
	 * 
	 * @param format  example:yyyyMMddHHmmss
	 * @return
	 */

	public static String getCurrentTime(String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(new Date());
	}

}
