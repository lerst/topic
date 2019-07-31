package edu.fcu.topic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class skill extends Activity
{
	private ImageButton bag;
	private ImageButton status;
	private ImageButton mission;
	private Intent intent;


	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.skill);

		bag = (ImageButton) findViewById(R.id.bag);
		status = (ImageButton) findViewById(R.id.status);
		mission= (ImageButton) findViewById(R.id.mission);
		
		bag.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				intent = new Intent();
				intent.setClass(skill.this, bag.class);
				startActivity(intent);
				skill.this.finish();

		}});

		status.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				intent = new Intent();
				intent.setClass(skill.this, status.class);
				startActivity(intent);
				skill.this.finish();

		}});
		

		mission.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				intent = new Intent();
				intent.setClass(skill.this, mission.class);
				startActivity(intent);
				skill.this.finish();

		}});

	
	}
	
	
	
	
	

}