package edu.fcu.topic;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class status extends Activity
{
	private ImageButton bag;
	private ImageButton skill;
	private ImageButton mission;
	
	private Button weapon;
	private Button item;
	private Button spell;
	
	private TextView name;
	private Intent intent;
	private ContentValues cv;

	SQLiteDatabase db;
	public String db_name = "MYSQL";
	NewListDataSQL helper;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.status);
		name = (TextView) findViewById(R.id.namestring);
		
	    cv = new ContentValues();

		helper= helper.getInstance(status.this);
		db = helper.getWritableDatabase();

		cv.put("_ID","abc");
		cv.put("_Money",0);
		db.insert("PLAYER", "", cv);
		db.close();
		
		
		
		bag = (ImageButton) findViewById(R.id.bag);
		skill = (ImageButton) findViewById(R.id.skill);
		mission= (ImageButton) findViewById(R.id.mission);
		
		weapon = (Button) findViewById(R.id.weapon0);
		item = (Button) findViewById(R.id.item0);
		spell= (Button) findViewById(R.id.skill0);
		
		weapon.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				intent = new Intent();
				intent.setClass(status.this, bag.class);
				startActivity(intent);
		}});

		item.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				intent = new Intent();
				intent.setClass(status.this, bag.class);
				startActivity(intent);
		}});

		spell.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				intent = new Intent();
				intent.setClass(status.this, skill.class);
				startActivity(intent);
		}});
		
		bag.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				intent = new Intent();
				intent.setClass(status.this, bag.class);
				startActivity(intent);
				status.this.finish();
		}});

		mission.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				intent = new Intent();
				intent.setClass(status.this, mission.class);
				startActivity(intent);
				status.this.finish();
		}});

		skill.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub				
				intent = new Intent();
				intent.setClass(status.this, skill.class);
				startActivity(intent);
				status.this.finish();
		}});
	
	}


	
	
	
	
	

}