package edu.fcu.topic;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class NewListDataSQL extends SQLiteOpenHelper {
	private static final int VERSION = 1;
	  private static NewListDataSQL sInstance;

	  public static synchronized NewListDataSQL getInstance(Context context) {

		    // Use the application context, which will ensure that you 
		    // don't accidentally leak an Activity's context.
		    // See this article for more information: http://bit.ly/6LRzfx
		    if (sInstance == null) {
		      sInstance = new NewListDataSQL(context.getApplicationContext());
		    }
		    return sInstance;
		  }

	public NewListDataSQL(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	public NewListDataSQL(Context context, String name) {
		this(context, name, null, VERSION);
	}

	public NewListDataSQL(Context context) {
		this(context, "MYSQL", null, VERSION);
	}

	// 輔助類建立時運行該方法
	@Override
	public void onCreate(SQLiteDatabase db) {
		
		db.execSQL("CREATE TABLE PLAYER("+ 
				 "_ID CHAR(10) PRIMARY KEY NOT NULL,"+ 
				 "_Money INT,"			  +  "_Resource int"+ 
				 "_Quantity_Now_Base INT,"+  "_Quantity_Max_Base INT,"+
				 "Role_Name Char(20),"	  +	 "Role_HP_Now INT,"+
				 "Role_HP_Max INT,"       +	 "Role_MP_Now INT,"+
				 "Role_MP_Max INT,"  	  +  "Role_Exp INT,"+
				 ");");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS MYSQL");
		onCreate(db);
	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
	}

	@Override
	public synchronized void close() {
		super.close();
	}

}