package com.example.khatabook;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.OutputStream;

import org.json.JSONObject;

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
	
	public static Boolean Validate_Form_CustAdd(String email,String mobile,String name)
	{
		return name!=null && mobile != null && isEmailValid(email);
	}
	
	public static Boolean Validate_Form_ProdAdd(String uom,String rop,String prodname,String ros)
	{
		return prodname !=null && uom !=null && rop !=null && ros !=null;
	}
	
//	public static String GetUserIDFromLocal()
//	{
//		String userid = "0";
//		StringBuffer sb = new StringBuffer();
//		try
//		{  
//            //Attaching BufferedReader to the FileInputStream by the help of InputStreamReader  
//            BufferedReader inputReader = new BufferedReader(new InputStreamReader(  
//                    openFileInput("khatabook_udata.txt")));  
//            String inputString;  	                    
//            while ((inputString = inputReader.readLine()) != null) {  
//                sb.append(inputString + "\n");  
//            }
//            
//            JSONObject data = new JSONObject(sb.toString());
//            userid = data.get("userid").toString();
//
//        } 
//		catch (FileNotFoundException e)
//		{
//			e.printStackTrace();
//		} 
//		catch (IOException e) 
//		{  
//            e.printStackTrace();  
//        } catch (Exception e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		finally
//		{
//			return userid;
//		}
//	}
}

