package com.hitotech.neighbour.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateUtil {

	public static boolean validateMac(String macAddress) {

		boolean result = false;
		String regex = "[A-F0-9]{16}";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(macAddress);
		if (matcher.matches()) {
			result = true;
		}
		return result;
	}

	public static boolean validateIP(String ipAddress) {

		boolean result = false;
		String regex = "\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(ipAddress);
		if (matcher.matches()) {
			result = true;
		}
		return result;
	}

	public static boolean validatePhone(String phone) {

		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$");
		m = p.matcher(phone);
		b = m.matches();
		return b;
	}

	public static boolean validateTel(String telNumber){
		boolean result = false;
		String regex = "^(010|02\\d|0[3-9]\\d{2})?\\d{6,8}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(telNumber);
		if (matcher.matches()) {
			result = true;
		}
		return result;
	}
	
	public static boolean validatePassword(String string){
		
		boolean result = false;
		String regex = "[0-9a-zA-Z]{6,20}";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(string);
		if (matcher.matches()) {
			result = true;
		}
		return result;
	}

	public static boolean validateCode(String string){

		boolean result = false;
		String regex = "[0-9]{6}";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(string);
		if (matcher.matches()) {
			result = true;
		}
		return result;
	}
}
