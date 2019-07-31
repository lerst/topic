package edu.fcu.topic;

import java.io.IOException;
import java.lang.ref.WeakReference;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
@SuppressWarnings("deprecation")
public class battle extends Activity implements SurfaceHolder.Callback,
								SensorEventListener, OnClickListener {

	private Camera mcamera;

	float[] accelerometerValues = new float[3];
	float[] magneticFieldValues = new float[3];
	float[] values = new float[3];
	float[] FM = new float[9];
	float[] LM = new float[9];
	int[] previewdegree = new int[3];
	int checkm=0,checka=0;
	
    int my_ids[] = {R.id.weapon0, R.id.weapon1, R.id.weapon2,R.id.weapon3,
            R.id.item0, R.id.item1, R.id.item2,R.id.item3,
            R.id.skill0, R.id.skill1, R.id.skill2,R.id.skill3,
            R.id.escape
    };

	private static int x = 0, y = 0, hp = 200;

	public static final int MEG_INVALIDATE = 1;

	private Handler mUIHandler;
	private Handler mThreadHandler;
	private HandlerThread mThread;

	public Message m;

	public static Bitmap bitmap, mbitmapa;

	private static ImageView image;
	private static Paint mPaint = new Paint();

	private SurfaceView previewSurfaceView;
	private SurfaceHolder previewSurfaceHolder;
	private boolean previewing = false;

	private Animation mAnimationRight;

	private TextView hptext, mptext,hpvaluetext,mpvaluetext;

	private SensorManager mSensorMgr;
	private Sensor mSensor;
	private Sensor aSensor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.battle);
		
		getWindow().setFormat(PixelFormat.UNKNOWN);
		setRequestedOrientation(0);
		
		previewdegree[0] = 0;
		previewdegree[1] = 0;
		previewdegree[2] = 0;
		
		initOnClickListener();
		
		mAnimationRight = AnimationUtils.loadAnimation(this, R.anim.roatate90);
		mAnimationRight.setFillAfter(true);

		hptext = (TextView) findViewById(R.id.hp);
		hpvaluetext = (TextView) findViewById(R.id.hpvalue);
		mptext = (TextView) findViewById(R.id.mp);
		mpvaluetext = (TextView) findViewById(R.id.mpvalue);

		hptext.setAnimation(mAnimationRight);
		hpvaluetext.setAnimation(mAnimationRight);
		mptext.setAnimation(mAnimationRight);
		mpvaluetext.setAnimation(mAnimationRight);

		previewSurfaceView = (SurfaceView) findViewById(R.id.previewsurface);
		previewSurfaceHolder = previewSurfaceView.getHolder();
		previewSurfaceHolder.addCallback(this);
		previewSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		image = (ImageView) findViewById(R.id.monster);
		mbitmapa = BitmapFactory.decodeResource(this.getResources(),R.drawable.goblin);
		mPaint.setColor(Color.WHITE);

		mSensorMgr = (SensorManager) getBaseContext().getSystemService(
				Context.SENSOR_SERVICE);
		mSensor = mSensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		aSensor = mSensorMgr.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

		mSensorMgr.registerListener(this, mSensor,
				SensorManager.SENSOR_DELAY_GAME);
		mSensorMgr.registerListener(this, aSensor,
				SensorManager.SENSOR_DELAY_GAME);

		mUIHandler = new MHandler(this);
		mThread = new HandlerThread("aa");
		mThread.start();
		mThreadHandler = new Handler(mThread.getLooper());
		mThreadHandler.post(r1);

	}

	static class MHandler extends Handler {
		WeakReference<battle> outerClass;

		MHandler(battle activity) {
			outerClass = new WeakReference<battle>(activity);
		}

		@Override
		public void handleMessage(Message msg) {

			if (msg.what == 1) {
				draw();
			}

			super.handleMessage(msg);

		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// remove the work from thread
		if (mThreadHandler != null) {
			mThreadHandler.removeCallbacks(r1);
		}
		// terminate the thread
		if (mThread != null) {
			mThread.quit();
		}

	}

	private Runnable r1 = new Runnable() {

		public void run() {

			Message m = new Message();
			m.what = MEG_INVALIDATE;

			mUIHandler.sendMessage(m);

			mThreadHandler.postDelayed(this, 100);

		}

	};

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		mcamera = Camera.open();
	
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		if (previewing) {
			mcamera.stopPreview();
			previewing = false;
		}

		try {
			mcamera.setPreviewDisplay(holder);
			mcamera.startPreview();
			previewing = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		mcamera.stopPreview();
		mcamera.release();
		mcamera = null;
		previewing = false;
		x = 0;
		y = 0;
		hp = 200;
		mSensorMgr.unregisterListener(this);

	}

	public static void draw() {

		bitmap = Bitmap.createBitmap(1000, 1000, Config.ARGB_4444);
		Canvas mcanvas = new Canvas(bitmap);
		mcanvas.drawBitmap(mbitmapa, x, y, mPaint);
		mPaint.setColor(Color.RED);
		mcanvas.drawRect(x - 20, y + 230 - hp, x - 10, y + 230, mPaint);
		mPaint.setColor(Color.BLACK);
		mcanvas.save();
		mcanvas.rotate(-90, x, y);
		mPaint.setTextSize(20);
		mcanvas.drawText(hp + "/200", x - 150, y - 20, mPaint);
		mcanvas.drawText("X=" + x, x, y, mPaint);
		mcanvas.drawText("Y=" + y, x, y + 20, mPaint);

		mcanvas.restore();

		Drawable drawable = new BitmapDrawable(bitmap);

		image.setBackgroundDrawable(drawable);

	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub

		if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
		{
			magneticFieldValues = event.values;
			checkm=1;
		}
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
		{	
			accelerometerValues = event.values;
			checka=1;
		}
		if(checkm==1&&checka==1)
		{
		
		SensorManager.getRotationMatrix(FM, null, accelerometerValues,magneticFieldValues);
		
		SensorManager.remapCoordinateSystem(FM,SensorManager.AXIS_Z,SensorManager.AXIS_MINUS_X,LM);
		
		SensorManager.getOrientation(LM, values);

		values[0] = (float) Math.toDegrees(values[0]);
		values[1] = (float) Math.toDegrees(values[1]);
		if (previewdegree[0] != 0) {
			x = x - 4*((Math.abs((int)values[1])-previewdegree[1]));
			y=  y - 4*((Math.abs((int)values[0])-previewdegree[0]));
		}
    	previewdegree[0]=Math.abs((int)values[0]);
        previewdegree[1]=Math.abs((int)values[1]);
        previewdegree[2]=Math.abs((int)values[2]);
        
        checkm=0;
        checka=0;
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}
	int buttonsetv(boolean zero,int cate,int setv)//zero to check the last number cate to check which type
	{					
        ImageButton b = null;
        
        if(zero)
        {
        	for(int count=0;count<3;count++)
        	{
        		if(count!=cate)
        		{
        		buttonsetv(false,count,4);
        		
        		}
        		else
        		{
        		buttonsetv(false,count,0);
        		}
        	}
        	
        	
        }
        else
        {
        	
        	if(( b = (ImageButton)findViewById(my_ids[cate*4+1]))!=null)
        	{
        	
        		if(b.getVisibility()!=4 && setv!=0) //if can see let it can't see and if set!=0        
        		{										
        			b.setVisibility(setv);
        				for(int count=cate*4+2;count<cate*4+4;count++)
        				{
        					b = (ImageButton)findViewById(my_ids[count]);
        					b.setVisibility(setv);
        				}
        			return 1;
        		}
        		else  // can't see and set is 0
        		{
        			if(b.getVisibility()==0)
        			{
        				setv=4;
        			}
        			b.setVisibility(setv);
        			for(int count=cate*4+2;count<cate*4+4;count++)
        			{
        				b = (ImageButton)findViewById(my_ids[count]);
        				b.setVisibility(setv);
        			}
        		}
  
        	}
        }
        return 0;
	}
	
	void initOnClickListener() {
        ImageButton b = null;
        for( int i=0 ; i< my_ids.length ; ++i )
                if( ( b = (ImageButton)findViewById(my_ids[i])) != null ) 
                        b.setOnClickListener(this);
                
  }
  public void onClick(View v) {
        switch( v.getId() ) {
        
                case R.id.weapon0:
                	buttonsetv(true,0,4);           
                	break;
                        
                case R.id.weapon1:             	
            		if(buttonsetv(false,0,4)==1)
            		{
            		hp=hp-20;	
            		}
            		
                    break;
                        
                case R.id.weapon2:
                	buttonsetv(false,0,4);
                	break;
                	
                case R.id.weapon3:            		
                	buttonsetv(false,0,4);
                	break;
                	
                case R.id.item0:
                	buttonsetv(true,1,4);
                	break;

                case R.id.item1:
            		buttonsetv(false,1,4);
                	break;

                case R.id.item2:
            		buttonsetv(false,1,4);
                	break;
                	
                case R.id.item3:
            		buttonsetv(false,1,4);
                	break;

                case R.id.skill0:
                	buttonsetv(true,2,4);
                	break;

                case R.id.skill1:
            		buttonsetv(false,2,4);
                	break;

                case R.id.skill2:
            		buttonsetv(false,2,4);
                	break;   
                	
                case R.id.skill3:
            		buttonsetv(false,2,4);
                	break;
                	
                case R.id.escape:
    				finish();
                	break;
        }
  }
}