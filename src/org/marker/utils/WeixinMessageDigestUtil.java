package org.marker.utils;

import java.security.*;
import java.util.Arrays;

/*
 * SHA1 水印算法工具类
 * AJ 2013-04-12
 */
public final class WeixinMessageDigestUtil {
	private static final WeixinMessageDigestUtil _instance = new WeixinMessageDigestUtil();

	private MessageDigest alga;
	
	private WeixinMessageDigestUtil() {
		try {
			alga = MessageDigest.getInstance("SHA-1");
		} catch(Exception e) {
			throw new InternalError("init MessageDigest error:" + e.getMessage());
		}
	}

	public static WeixinMessageDigestUtil getInstance() {
		return _instance;
	}

	public static String byte2hex(byte[] b) {
		String des = "";
		String tmp = null;
		for (int i = 0; i < b.length; i++) {
			tmp = (Integer.toHexString(b[i] & 0xFF));
			if (tmp.length() == 1) {
				des += "0";
			}
			des += tmp;
		}
		return des;
	}
	
	public String encipher(String strSrc) {
		String strDes = null;
		byte[] bt = strSrc.getBytes();
		alga.update(bt);
		strDes = byte2hex(alga.digest()); //to HexString
		return strDes;
	}

	public static void main(String[] args) {
		String signature="ee698bd52b894d16e7f70f98d9ed79dec7c9372e";
		String timestamp="1499357330";
		String nonce="10882411";
		
		String[] ArrTmp = { "doumitest", timestamp, nonce };
		Arrays.sort(ArrTmp);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < ArrTmp.length; i++) {
			sb.append(ArrTmp[i]);
		}
		String pwd =WeixinMessageDigestUtil.getInstance().encipher(sb.toString());
		System.out.println(">>>pwd:"+pwd);
		if (signature.equals(pwd)) {
			System.out.println("token 验证成功~!");
		}else {
			System.out.println("token 验证失败~!");
		}
		checkSignature(signature,timestamp,nonce);
	}

	
	 public static boolean checkSignature(String signature, String timestamp, String nonce) {
	        String[] arr = new String[] { "doumitest", timestamp, nonce };
	        // 将token、timestamp、nonce三个参数进行字典序排序
	        // Arrays.sort(arr);
	        sort(arr);
	        StringBuilder content = new StringBuilder();
	        for (int i = 0; i < arr.length; i++) {
	            content.append(arr[i]);
	        }
	        MessageDigest md = null;
	        String tmpStr = null;

	        try {
	            md = MessageDigest.getInstance("SHA-1");
	            // 将三个参数字符串拼接成一个字符串进行sha1加密
	            byte[] digest = md.digest(content.toString().getBytes());
	            tmpStr = byteToStr(digest);
	        } catch (NoSuchAlgorithmException e) {
	            e.printStackTrace();
	        }
	        content = null;
	        System.out.println(">>>pwd:1"+tmpStr);
	        // 将sha1加密后的字符串可与signature对比，标识该请求来源于微信
	        return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
	    }

	    /**
	     * 将字节数组转换为十六进制字符串
	     * 
	     * @param byteArray
	     * @return
	     */
	    private static String byteToStr(byte[] byteArray) {
	        String strDigest = "";
	        for (int i = 0; i < byteArray.length; i++) {
	            strDigest += byteToHexStr(byteArray[i]);
	        }
	        return strDigest;
	    }

	    /**
	     * 将字节转换为十六进制字符串
	     * 
	     * @param mByte
	     * @return
	     */
	    private static String byteToHexStr(byte mByte) {
	        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
	        char[] tempArr = new char[2];
	        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
	        tempArr[1] = Digit[mByte & 0X0F];
	        String s = new String(tempArr);
	        return s;
	    }
	    public static void sort(String a[]) {
	        for (int i = 0; i < a.length - 1; i++) {
	            for (int j = i + 1; j < a.length; j++) {
	                if (a[j].compareTo(a[i]) < 0) {
	                    String temp = a[i];
	                    a[i] = a[j];
	                    a[j] = temp;
	                }
	            }
	        }
	    }
}