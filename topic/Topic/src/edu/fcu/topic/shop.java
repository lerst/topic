package edu.fcu.topic;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;

public class shop extends Activity implements OnClickListener
{


	private Intent intent;
	
	int my_ids[] = {
            R.id.status,R.id.skill,R.id.mission,R.id.bag
   };

	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shop);
		
		initOnClickListener();
		
		 GridView gridview = (GridView) findViewById(R.id.shopgrid);  
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


	 void initOnClickListener() {
		  
	        ImageButton b = null;
	        for( int i=0 ; i< my_ids.length ; ++i )
	                if( ( b = (ImageButton)findViewById(my_ids[i]) ) != null ) 
	                        b.setOnClickListener(this);  
	  }
	  public void onClick(View v) {
	        switch( v.getId() ) {
	              	                        
	                case R.id.status:
	                	intent = new Intent();
						intent.setClass(shop.this, status.class);
						startActivity(intent);
	                	break;
	                	
	                case R.id.skill:
	                	intent = new Intent();
						intent.setClass(shop.this, skill.class);
						startActivity(intent);
	                	break;
	                	
	                case R.id.mission:
	                	intent = new Intent();
						intent.setClass(shop.this, mission.class);
						startActivity(intent);
	                	break;
	                	
	                case R.id.bag:
	                	intent = new Intent();
						intent.setClass(shop.this, bag.class);
						startActivity(intent);
	                	break;
	        }
	  }

}