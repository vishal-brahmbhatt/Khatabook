package com.example.khatabook;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Registration extends Activity {

	TextView  tv_gotologin,tv_matchpassword;
	EditText  et_password, et_conf_pass,et_email,et_name,et_shopname;
	Button btn_reg;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
		tv_gotologin = (TextView)findViewById(R.id.tv_goto_login);
		tv_matchpassword = (TextView)findViewById(R.id.tv_passwordnotmatch);
		et_password = (EditText)findViewById(R.id.et_password);
		et_conf_pass = (EditText)findViewById(R.id.et_conf_password);
		et_email = (EditText)findViewById(R.id.et_emailid);
		et_name = (EditText) findViewById(R.id.et_name);
		et_shopname = (EditText)findViewById(R.id.et_shopname);
		
		
		btn_reg = (Button)findViewById(R.id.btn_Registration);
		
		
		
		//////////////////////////////////  tv_gotologin ///////////////////////////////////////
		
		tv_gotologin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent loginIntent = new Intent(Registration.this,Login.class);
				Registration.this.startActivity(loginIntent);
				Registration.this.finish();
			}
		});
		
		////////////////////////////////// et_conf_pass //////////////////////

		et_conf_pass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(hasFocus == false)
				{
					if(!et_password.getText().toString().equals(et_conf_pass.getText().toString()))
					{
						tv_matchpassword.setVisibility(View.VISIBLE);
					}
					else
					{
						tv_matchpassword.setVisibility(View.INVISIBLE);
					}
					
				}
			}
		});
		
		
		///////////////////////////////////// btn_reg on click ///////////////////////////////////////
		
		btn_reg.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String email = et_email.getText().toString();
				String name = et_name.getText().toString();
				String shopname = et_shopname.getText().toString();
				String  password = et_conf_pass.getText().toString();
				if(Utils.Validate_Form_Registration(email,name,shopname))
				{
					new RegistrationTask().execute(shopname,name,email,password);					
				}
						
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registration, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	////////////////////// Our Custom Class for API Call ////////////
	private class RegistrationTask extends AsyncTask<String, String, String>{
	      String result;
	      
	      @Override
	      protected String doInBackground(String... vb) {
	    	  JSONObject data = new JSONObject();
				try
				{
					URL url = new URL (Utils.get_BaseUrl() + "/usermaster-ins.php");
					HttpURLConnection con = (HttpURLConnection)url.openConnection();
					con.setRequestMethod("POST");
					con.setRequestProperty("Content-Type", "application/json; utf-8");
					con.setRequestProperty("Accept", "application/json");
					con.setDoOutput(true);
					
					data.put("shopname",vb[0]); // 0 because we had passed shopname at 0th index
					data.put("name",vb[1]); // 1 because we had passed name at 1st index
					data.put("emailid",vb[2]);
					data.put("address"," ");
					data.put("gstno"," ");
					data.put("password", vb[3]);
					
					String jsonInputString = data.toString();
					System.out.println(jsonInputString);
					
					OutputStream os = con.getOutputStream();						
				    byte[] input = jsonInputString.getBytes("utf-8");
				    os.write(input, 0, input.length);			
					
				    BufferedReader br = new BufferedReader(
	    		    new InputStreamReader(con.getInputStream(), "utf-8"));
	    
	    		    StringBuilder response = new StringBuilder();
	    		    String responseLine = null;
	    		    while ((responseLine = br.readLine()) != null) {
	    		        response.append(responseLine.trim());
	    		    }
	    		    System.out.println(response.toString());
	    		    result = response.toString();
	    		    return response.toString();
	    		    
					

				} 
				catch (MalformedURLException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				catch (Exception e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
	         return null;
	      }
	      @Override
	      protected void onPostExecute(String aVoid) {
	         
	         super.onPostExecute(aVoid);
	         try
	         {
				JSONObject res = new JSONObject(result);
				if(res.get("status").toString().equals("Success"))
				{
					Intent loginIntent = new Intent(Registration.this,Login.class);
					Registration.this.startActivity(loginIntent);
					Registration.this.finish();
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Unable to perform registration", Toast.LENGTH_LONG).show();
				}
			} 
	         catch (JSONException e)
	         {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	         
	         	
	      }
	   }
}
