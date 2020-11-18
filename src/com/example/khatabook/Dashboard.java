package com.example.khatabook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class Dashboard extends Activity {

	Button btn_goto_custview,btn_goto_prodview;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard);
		btn_goto_custview = (Button)findViewById(R.id.btn_goto_customerview);
		btn_goto_prodview = (Button)findViewById(R.id.btn_goto_productview);
		
		
		btn_goto_custview.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent viewcustIntent = new Intent(Dashboard.this, ViewCustomer.class);
				Dashboard.this.startActivity(viewcustIntent);
				Dashboard.this.finish();
			}
		});
		
		btn_goto_prodview.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent viewcustIntent = new Intent(Dashboard.this, ViewProduct.class);
				Dashboard.this.startActivity(viewcustIntent);
				Dashboard.this.finish();
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dashboard, menu);
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
