package edu.fcu.topic;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class firstin extends Activity implements OnClickListener
{
	private Intent intent;
	Boolean isFirstIn = false;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.first);
		SharedPreferences pref = getSharedPreferences("myActivityName", 0);
		isFirstIn = pref.getBoolean("isFirstIn", true);

		if(isFirstIn==true)
		{
			intent = new Intent();
			intent.setClass(firstin.this, login.class);
			startActivity(intent);
			firstin.this.finish();	
		}
		else
		{
			intent = new Intent();
			intent.setClass(firstin.this, login.class);
			startActivity(intent);
			firstin.this.finish();	

			
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.imageView1)
		{
			
		if(isFirstIn==true)
		{
			intent = new Intent();
			intent.setClass(firstin.this, login.class);
			startActivity(intent);
			firstin.this.finish();	
		}
		else
		{
			intent = new Intent();
			intent.setClass(firstin.this, login.class);
			startActivity(intent);
			firstin.this.finish();	
			
		}
		}
	}
	
}