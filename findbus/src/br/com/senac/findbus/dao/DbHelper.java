package br.com.senac.findbus.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

	public DbHelper(Context ctx) {
		super(ctx, "findbusweb.db", null, 5);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//db.execSQL(getScriptStops());
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//b.execSQL("DROP TABLE IF EXISTS stops");
		//onCreate(db);
	}

	private String getScriptStops() {
		return "CREATE TABLE stops ( sequence_android integer primary key autoincrement,"
				+ " stop_id INT NOT NULL,"
				+ "	stop_code VARCHAR(50) NULL,"
				+ "	stop_name VARCHAR(100) NOT NULL,"
				+ "	stop_desc VARCHAR(50) NULL,"
				+ "	stop_lat NUMERIC(18,6) NOT NULL,"
				+ "	stop_lon NUMERIC(18,6) NOT NULL);";
	}

}