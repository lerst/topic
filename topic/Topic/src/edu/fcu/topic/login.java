package edu.fcu.topic;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class login extends Activity
{
	private Button register;
	private Button logincheck;
	private Intent intent;
	private EditText ID;
	
	SQLiteDatabase db;
	public String db_name = "MYSQL";
	TextView a;
	NewListDataSQL helper;
	
	private ContentValues cv;
	private TextView load;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
	    ID = (EditText)findViewById(R.id.accountedit);
	    cv = new ContentValues();
	    
		helper= helper.getInstance(login.this);
		db = helper.getWritableDatabase();
		
		logincheck = (Button) findViewById(R.id.login);
		register = (Button) findViewById(R.id.register);
		load = (TextView)findViewById(R.id.loading);

		register.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				cv.put("_ID",ID.getText().toString());
				cv.put("_Money",0);
				db.insert("PLAYER", "", cv);
				db.close();
				intent = new Intent();
				intent.setClass(login.this, map.class);
				startActivity(intent);
				login.this.finish();
				
			}
			
		});
		
		
		logincheck.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				load.setVisibility(0);
				intent = new Intent();
				intent.setClass(login.this, map.class);
				startActivity(intent);
				login.this.finish();
				
			}
			
		}
		);
		
		
		
	}





}