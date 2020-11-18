package com.example.khatabook;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils 
{
	private static String base_url = "http://192.168.0.54:8080/khatabook/endpoints";
	
	public static String get_BaseUrl()
	{
		return base_url;
	}
	
	public static boolean isEmailValid(String email) {
	    String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
	    Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(email);
	    return matcher.matches();
	}
	
	public static Boolean Validate_Form_Registration(String email,String name,String shopname)
	{
		return name!=null && shopname != null && isEmailValid(email);
	}
	
	public static Boolean Validate_Form_Login(String email,String password)
	{
		return password!=null && isEmailValid(email);
	}
}

