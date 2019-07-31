package edu.fcu.topic;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.LatLng;

public class map extends Activity implements LocationListener,OnClickListener{

	private boolean getopen=false;
	private boolean getService = false;
	Double lng = 120.648331;
	Double lat = 24.181767;
	GoogleMap mMap;
	
    private ViewGroup infoWindow;
    private TextView infoTitle;
    private TextView infoSnippet;
    private Button infoButton;
    
    private SensorManager sm;  
    private Sensor aSensor;  
    private Sensor mSensor;  
    
    float[] accelerometerValues = new float[3];  
    float[] magneticFieldValues = new float[3];  
    
    private OnInfoWindowElemTouchListener infoButtonListener;

	private String best;
	
	private Location location;
	private LocationManager status;
	
	private Intent intent;
		
	int my_ids[] = {
             R.id.mlocation, R.id.status,R.id.skill,R.id.mission,R.id.bag,R.id.openmove
    };
	@SuppressLint("InflateParams")
	@Override
	protected void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);

				initOnClickListener();			

		        infoWindow = (ViewGroup)getLayoutInflater().inflate(R.layout.windowlayout, null);
		        infoTitle = (TextView)infoWindow.findViewById(R.id.title);
		        infoSnippet = (TextView)infoWindow.findViewById(R.id.snippet);
		        infoButton = (Button)infoWindow.findViewById(R.id.windowbattle);
		        infoButton.setFocusable(true);
		        
				mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
					
			    
			    final MapWrapperLayout mapWrapperLayout = (MapWrapperLayout)findViewById(R.id.map_relative_layout);
				
				mapWrapperLayout.init(mMap, getPixelsFromDp(this, 39 + 20)); 
		        
		        infoButtonListener = new OnInfoWindowElemTouchListener(infoButton,getResources().getDrawable(android.R.drawable.btn_default),
		                getResources().getDrawable(R.drawable.common_signin_btn_icon_pressed_dark)) 
		        {
		            @Override
		            protected void onClickConfirmed(View v, Marker marker) {
		                // Here we can perform some action triggered after clicking the button
		            	if(marker.getTitle().equals("Enemy"))
		            	{
		            	intent = new Intent();
						intent.setClass(map.this, battle.class);
						startActivity(intent);
		            	}
		            	
		            	if(marker.getTitle().equals("Shop"))
		            	{
		            	intent = new Intent();
						intent.setClass(map.this, shop.class);
						startActivity(intent);
		            	}
			            	
		            }
		        }; 
		        
		        infoButton.setOnTouchListener(infoButtonListener);
		        

		        mMap.setInfoWindowAdapter(new InfoWindowAdapter() {
		            @Override
		            public View getInfoWindow(Marker marker) {
		                return null;
		            }

		            @Override
		            public View getInfoContents(Marker marker) {
		                // Setting up the infoWindow with current's marker info
		                infoTitle.setText(marker.getTitle());
		                infoSnippet.setText(marker.getSnippet());
		                infoButtonListener.setMarker(marker);

		                // We must call this to set the current marker and infoWindow references
		                // to the MapWrapperLayout
		                mapWrapperLayout.setMarkerWithInfoWindow(marker, infoWindow);
		                return infoWindow;
		            }
		        });

				
				
				mMap.addMarker(new MarkerOptions().position(new LatLng(24.181767, 120.648331))
												  .icon(BitmapDescriptorFactory.fromResource(R.drawable.school))
												  .title("Base")
												  .snippet("Name:FCU\nHP:100")
						);
				
				mMap.addMarker(new MarkerOptions().position(new LatLng(24.178693,120.64674))
						  .icon(BitmapDescriptorFactory.fromResource(R.drawable.shop711))
						  .title("Shop")
						  .snippet("7-11 逢甲1")
						);	

				mMap.addMarker(new MarkerOptions().position(new LatLng(24.180000,120.64674))
						  .icon(BitmapDescriptorFactory.fromResource(R.drawable.slime))
						  .title("Enemy")
						  .snippet("Name:slime\nHP:100")
						);		
				
				
					testLocationProvider();

				if (getService == true) { 
					mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
							new LatLng(lat, lng), 20));
					
					mMap.addMarker(new MarkerOptions().position(
							new LatLng(lat, lng)).icon(
							BitmapDescriptorFactory.fromResource(R.drawable.player)));

					mMap.addCircle(new CircleOptions()
							.center(new LatLng(lat, lng)).radius(10)
							.strokeColor(Color.RED).fillColor(0x1f98F5FF)
							.strokeWidth(1));

				}
			
				sm = (SensorManager)getSystemService(Context.SENSOR_SERVICE);  
			    aSensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);  
			    mSensor = sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);  
			  
			}

	
	private void testLocationProvider() {
		status = (LocationManager) (this 
				.getSystemService(Context.LOCATION_SERVICE));
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);

		best = status.getBestProvider(criteria, true);

		if (status.isProviderEnabled(LocationManager.GPS_PROVIDER)|| status.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) 
		{

			getService = true;
			if (status.getLastKnownLocation("gps") != null) {
				status.requestLocationUpdates(best, 100, 0, this);
				location = status.getLastKnownLocation(best);
				if(location!=null)
				{
				lat = location.getLatitude();
				lng = location.getLongitude();
				}
				Toast.makeText(this, "gps", Toast.LENGTH_LONG).show();
				
			} else if (status.getLastKnownLocation("network") != null) {
				status.requestLocationUpdates("gps", 100, 0, this);
				best="network";
				location = status.getLastKnownLocation(best);
				lat = location.getLatitude();
				lng = location.getLongitude();
				Toast.makeText(this, "network", Toast.LENGTH_LONG).show();
			} 
			else {
				Toast.makeText(this, "Can't find location", Toast.LENGTH_LONG).show();
				getService = false;
			}

		}
		else {

			Toast.makeText(this, "please open GPS", Toast.LENGTH_LONG).show();

		}

	}
    final SensorEventListener myListener = new SensorEventListener() {
    public void onSensorChanged(SensorEvent sensorEvent) {  
          
    if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)  
    magneticFieldValues = sensorEvent.values;  
    if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER)  
        accelerometerValues = sensorEvent.values;  
    if(getService)
    {
    calculateOrientation();  
    }
    
    }  
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}  
    };  

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
        sm.unregisterListener(myListener);  

		if (getService) {

			status.removeUpdates(this);

		}

	}

	@Override
	protected void onRestart() { //
		// TODO Auto-generated method stub
		super.onRestart();
		testLocationProvider();
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		testLocationProvider();
		mMap.clear();
		
		mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).icon(
				BitmapDescriptorFactory.fromResource(R.drawable.player)));

		mMap.addCircle(new CircleOptions().center(new LatLng(lat, lng))
				.radius(10).strokeColor(Color.RED).fillColor(0x1f98F5FF)
				.strokeWidth(1)
		
		);
	
	
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub

	}
	public static int getPixelsFromDp(Context context, float dp) {
	        final float scale = context.getResources().getDisplayMetrics().density;
	        return (int)(dp * scale + 0.5f);
	}
	  
	    private  void calculateOrientation() {  
	          float[] values = new float[3];  
	          float[] R = new float[9];  
	          SensorManager.getRotationMatrix(R, null, accelerometerValues, magneticFieldValues);           
	          SensorManager.getOrientation(R, values);  
	          values[0] = (float) Math.toDegrees(values[0]);  
	          if(values[0]<0)
	          {
	        	  values[0]=values[0]+360;
	          }
              CameraPosition cameraPosition = new CameraPosition.Builder()
              .target(new LatLng(lat,lng))             	// Sets the center of the map to current location
              .zoom(18)                			 	 	// Sets the zoom
              .bearing(values[0]) 		 				// Sets the orientation of the camera to east
              .tilt(0)                				 	// Sets the tilt of the camera to 0 degrees
              .build();                  				// Creates a CameraPosition from the builder
              mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 100, null);
	    }	  
	  
	  
	  void initOnClickListener() {
		  
	        ImageButton b = null;
	        for( int i=0 ; i< my_ids.length ; ++i )
	                if( ( b = (ImageButton) findViewById( my_ids[i]) ) != null ) 
	                        b.setOnClickListener(this);  
	  }
	  public void onClick(View v) {
	        switch( v.getId() ) {
	              	                        
	                case R.id.mlocation:
						if (getService == true) {
							mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 20));
						}
	                        break;
	                        
	                case R.id.status:
	                	intent = new Intent();
						intent.setClass(map.this, status.class);
						startActivity(intent);
	                	break;
	                	
	                case R.id.skill:
	                	intent = new Intent();
						intent.setClass(map.this, skill.class);
						startActivity(intent);
	                	break;
	                	
	                case R.id.mission:
	                	intent = new Intent();
						intent.setClass(map.this, mission.class);
						startActivity(intent);
	                	break;
	                	
	                case R.id.bag:
	                	intent = new Intent();
						intent.setClass(map.this, bag.class);
						startActivity(intent);
	                    break;
	                    
	                case R.id.openmove:
	                	if(!getopen)
	                	{
	                		getopen=true;
	        			    sm.registerListener(myListener, aSensor, SensorManager.SENSOR_DELAY_NORMAL);  
	        			    sm.registerListener(myListener, mSensor,SensorManager.SENSOR_DELAY_NORMAL);  
	        			    
	                	}
	                	else
	                	{
		                	getopen=false;
		                    sm.unregisterListener(myListener);  

	                	}
	                	
	                    break;

	        }
	  }


	  
}