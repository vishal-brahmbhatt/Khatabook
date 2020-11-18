package com.example.khatabook;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddProduct extends Activity {

	Button btn_save;
	EditText et_prodname, et_uom, et_rop, et_ros;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_product);

		getActionBar().setDisplayHomeAsUpEnabled(true); // to add back button on action bar

		btn_save = (Button) findViewById(R.id.btn_pa_save);
		et_prodname = (EditText) findViewById(R.id.et_pa_prodname);
		et_uom = (EditText) findViewById(R.id.et_pa_uom);
		et_rop = (EditText) findViewById(R.id.et_pa_rop);
		et_ros = (EditText) findViewById(R.id.et_pa_ros);

		btn_save.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String prodname = et_prodname.getText().toString();
				String uom = et_uom.getText().toString();
				String rop = et_rop.getText().toString();
				String ros = et_ros.getText().toString();

				String userid = "0";
				StringBuffer sb = new StringBuffer();
				try {
					// Attaching BufferedReader to the FileInputStream by the help of
					// InputStreamReader
					BufferedReader inputReader = new BufferedReader(
							new InputStreamReader(openFileInput("khatabook_udata.txt")));
					String inputString;
					while ((inputString = inputReader.readLine()) != null) {
						sb.append(inputString + "\n");
					}

					JSONObject data = new JSONObject(sb.toString());
					userid = data.get("userid").toString();

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (Utils.Validate_Form_ProdAdd(uom, rop, prodname, ros)) {
					new AddProductTask().execute(prodname, uom, rop, ros," ",userid);
				}
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_product, menu);
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
	private class AddProductTask extends AsyncTask<String, String, String> {
		String result;

		@Override
		protected String doInBackground(String... vb) {
			JSONObject data = new JSONObject();
			try {
				URL url = new URL(Utils.get_BaseUrl() + "/productmaster-ins.php");
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setRequestMethod("POST");
				con.setRequestProperty("Content-Type", "application/json; utf-8");
				con.setRequestProperty("Accept", "application/json");
				con.setDoOutput(true);

				data.put("prodname", vb[0]);
				data.put("uom", vb[1]);
				data.put("rateofpurchase", vb[2]);
				data.put("rateofsell", vb[3]);
				data.put("proddesc", vb[4]);
				data.put("userid", vb[5]);

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
//TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
//TODO Auto-generated catch block
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
				if (res.get("status").toString().equals("Success")) {

					Intent loginIntent = new Intent(AddProduct.this, ViewProduct.class);
					AddProduct.this.startActivity(loginIntent);
					AddProduct.this.finish();
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
