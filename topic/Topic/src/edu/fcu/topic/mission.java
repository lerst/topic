package edu.fcu.topic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

public class mission extends Activity
{
	private ImageButton bag;
	private ImageButton status;
	private ImageButton skill;
	
	private Button canmission;
	private Button finishmission;
	private Button accpmission;
	private Button acbutton;
	
	private Intent intent;
	private ListView listView;
	private String[] list = {"任務1","任務2","任務3","任務4","任務5","任務6","任務7","任務8","任務9"};
	private ArrayAdapter<String> listAdapter;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mission);

		bag = (ImageButton) findViewById(R.id.bag);
		status = (ImageButton) findViewById(R.id.status);
		skill= (ImageButton) findViewById(R.id.skill);
		canmission = (Button) findViewById(R.id.canmission);
		accpmission = (Button) findViewById(R.id.accpmission);
		finishmission = (Button) findViewById(R.id.finishmission);
		acbutton = (Button) findViewById(R.id.acbutton);
		listView = (ListView)findViewById(R.id.listView1);
		listAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
		listView.setAdapter(listAdapter);
		  
		canmission.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub		
				acbutton.setText("接受");
		}});
		
		finishmission.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub		
				acbutton.setText("重新接受");

		}});
		
		accpmission.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub		
				acbutton.setText("完成");

		}});
		
		bag.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub		
				intent = new Intent();
				intent.setClass(mission.this, bag.class);
				startActivity(intent);
				mission.this.finish();

		}});

		status.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub		
				intent = new Intent();
				intent.setClass(mission.this, status.class);
				startActivity(intent);
				mission.this.finish();
		}});

		skill.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub	
				intent = new Intent();
				intent.setClass(mission.this, skill.class);
				startActivity(intent);
				mission.this.finish();
	    }});
		
		
	}

	
	
	
	
	

}