package br.com.senac.findbus.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

	public DbHelper(Context ctx) {
		super(ctx, "findbusweb.db", null, 7);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(getScriptStops());
		db.execSQL(getScriptRoutes());
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS stops");
		db.execSQL("DROP TABLE IF EXISTS routes");
		onCreate(db);
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
	
	private String getScriptRoutes() {
		return "CREATE TABLE routes ( "
				+ " sequence_android integer primary key autoincrement,"
				+ " route_id VARCHAR(10) NOT NULL," 
				+ " agency_id VARCHAR(50) NULL,"
				+ " route_short_name VARCHAR(10) NOT NULL,"
				+ " route_long_name VARCHAR(100) NOT NULL,"
				+ " route_desc  VARCHAR(100) NULL,"
				+ " route_type INT NOT NULL,"
				+ " route_url VARCHAR(100) NULL,"
				+ " route_color VARCHAR(10) NULL,"
				+ " route_text_color VARCHAR(10) NULL);";

	}

}