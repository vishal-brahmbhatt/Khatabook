package com.example.khatabook;

import android.app.Activity;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Registration extends Activity {

	TextView  tv_gotologin,tv_matchpassword;
	EditText  et_password, et_conf_pass;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
		tv_gotologin = (TextView)findViewById(R.id.tv_goto_login);
		tv_matchpassword = (TextView)findViewById(R.id.tv_passwordnotmatch);
		et_password = (EditText)findViewById(R.id.et_password);
		et_conf_pass = (EditText)findViewById(R.id.et_conf_password);
		
		
		
		
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
}
