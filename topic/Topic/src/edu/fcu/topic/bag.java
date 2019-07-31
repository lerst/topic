package edu.fcu.topic;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;

public class bag extends Activity 
{
	private Intent intent;

	private ImageButton status;
	private ImageButton skill;
	private ImageButton mission;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bag);
		

		skill = (ImageButton) findViewById(R.id.skill);
		status = (ImageButton) findViewById(R.id.status);
		mission= (ImageButton) findViewById(R.id.mission);
		
		skill.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				intent = new Intent();
				intent.setClass(bag.this, skill.class);
				startActivity(intent);
				bag.this.finish();

		}});

		status.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				intent = new Intent();
				intent.setClass(bag.this, status.class);
				startActivity(intent);
				bag.this.finish();

		}});
		

		mission.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				intent = new Intent();
				intent.setClass(bag.this, mission.class);
				startActivity(intent);
				bag.this.finish();

		}});
		
		
		
		
		
	     GridView gridview = (GridView) findViewById(R.id.baggrid);  
	     ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();  
	     for(int i=0;i<50;i++)  
	      {  
	        HashMap<String, Object> map = new HashMap<String, Object>();  
	        map.put("ItemImage", R.drawable.slime);
	        map.put("ItemText", "NO."+String.valueOf(i));
	        lstImageItem.add(map);  
	      }  
	     SimpleAdapter saImageItems = new SimpleAdapter(this,lstImageItem,R.layout.night_item,new String[] {"ItemImage","ItemText"},new int[] {R.id.ItemImage,R.id.ItemText});  
	     gridview.setAdapter(saImageItems);  
	     gridview.setOnItemClickListener(new ItemClickListener());  
		

		
	}


}
	class  ItemClickListener implements OnItemClickListener  
	{  
	public void onItemClick(AdapterView<?> arg0,View arg1,int arg2,long arg3) 
	{

	}
	
	
	
	
	}

