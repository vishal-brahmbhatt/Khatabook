package com.example.khatabook;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddCustomer extends Activity {

	Button btn_save;
	EditText et_custname, et_custemail, et_custmobile;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_customer);

		btn_save = (Button) findViewById(R.id.btn_ca_savecust);
		et_custemail = (EditText) findViewById(R.id.et_ca_custemail);
		et_custmobile = (EditText) findViewById(R.id.et_ca_custmobile);
		et_custname = (EditText) findViewById(R.id.et_ca_custname);

		btn_save.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String name = et_custname.getText().toString();
				String email = et_custemail.getText().toString();
				String mobile = et_custmobile.getText().toString();
				
				String userid = "0";
				StringBuffer sb = new StringBuffer();
				try
				{  
		            //Attaching BufferedReader to the FileInputStream by the help of InputStreamReader  
		            BufferedReader inputReader = new BufferedReader(new InputStreamReader(  
		                    openFileInput("khatabook_udata.txt")));  
		            String inputString;  	                    
		            while ((inputString = inputReader.readLine()) != null) {  
		                sb.append(inputString + "\n");  
		            }
		            
		            JSONObject data = new JSONObject(sb.toString());
		            userid = data.get("userid").toString();

		        } 
				catch (FileNotFoundException e)
				{
					e.printStackTrace();
				} 
				catch (IOException e) 
				{  
		            e.printStackTrace();  
		        } catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
				
				if (Utils.Validate_Form_CustAdd(email, mobile, name))
				{
					new AddCustomerTask().execute(name,email,mobile,userid);
				}
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_customer, menu);
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

//////////////////////Our Custom Class for API Call ////////////
	private class AddCustomerTask extends AsyncTask<String, String, String> {
		String result;

		@Override
		protected String doInBackground(String... vb) {
			JSONObject data = new JSONObject();
			try {
				URL url = new URL(Utils.get_BaseUrl() + "/customermaster-ins.php");
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setRequestMethod("POST");
				con.setRequestProperty("Content-Type", "application/json; utf-8");
				con.setRequestProperty("Accept", "application/json");
				con.setDoOutput(true);

				data.put("custname", vb[0]);
				data.put("email", vb[1]);
				data.put("mobile", vb[2]);
				data.put("userid", vb[3]);

				String jsonInputString = data.toString();
				System.out.println(jsonInputString);

				OutputStream os = con.getOutputStream();
				byte[] input = jsonInputString.getBytes("utf-8");
				os.write(input, 0, input.length);

				BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));

				StringBuilder response = new StringBuilder();
				String responseLine = null;
				while ((responseLine = br.readLine()) != null) {
					response.append(responseLine.trim());
				}
				System.out.println(response.toString());
				result = response.toString();
				return response.toString();

			} catch (MalformedURLException e) {
// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(String aVoid) {

			super.onPostExecute(aVoid);
			try {
				JSONObject res = new JSONObject(result);
				System.out.println(res);
				if (res.get("status").toString().equals("Success")) 
				{					
					
					Intent loginIntent = new Intent(AddCustomer.this, ViewCustomer.class);
					AddCustomer.this.startActivity(loginIntent);
					AddCustomer.this.finish();
				} else {
					Toast.makeText(getApplicationContext(), "Unable to save Customer now", Toast.LENGTH_LONG).show();
				}
			} catch (JSONException e) {

//TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
