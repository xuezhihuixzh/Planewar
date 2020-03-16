package com.liuxuejian.planefight.SQLiteDB;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.liuxuejian.planefight.entity.Advance_Score;
import com.liuxuejian.planefight.entity.Endless_Score;
import com.liuxuejian.planefight.entity.Hell_Score;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

/**
 * 
 * @author bruce.liu
 * @version 1.0
 * @time 2015/5/6/22.47
 * 
 */

public class PlaneFightDB {
	private static Context context;
	// 声明一个PlaneFightDB类的引用
	private static PlaneFightDB pfdb = null;
	// 数据库实例
	private SQLiteDatabase db;
	// 数据库文件夹目录
	public static final String DB_PATH = android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + "/PlaneFight";
	// 数据库文件路径
	public static final String BASE_PATH = android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + "/PlaneFight/planefight.db";

	public PlaneFightDB(Context context) {
		super();
		this.context = context;
	}

	public static PlaneFightDB getInstance() {
		if (pfdb == null) {
			pfdb = new PlaneFightDB(context);
		}
		return pfdb;
	}

	// 判断数据库是否存在
	private boolean hasDB() {
		if (new File(BASE_PATH).exists()) {
			return true;
		} else {
			return false;
		}
	}

	// 创建数据库文件
	private File createdb() {
		File file = new File(BASE_PATH);
		return file;
	}

	// 打开数据库
	private boolean openDB() {
		if (hasDB()) {
			if (db == null || !db.isOpen()) {
				db = SQLiteDatabase.openDatabase(BASE_PATH, null,
						SQLiteDatabase.OPEN_READWRITE);
			}
			return true;
		} else {
			File file = new File(DB_PATH);
			if(!file.exists()) 
			file.mkdirs(); 
			db = SQLiteDatabase.openOrCreateDatabase(BASE_PATH, null);
			CreateAStable(db);
			CreateEStable(db);
			CreateHStable(db);
			return true;
		}
	}

	// 关闭数据库
	public void closeDatabase() {
		if (db != null && db.isOpen()) {
			db.close();
			db = null;
		}
	}

	// 创建Advance_Score表
	public void CreateAStable(SQLiteDatabase db) {
		db.execSQL("create table Advance_Score("
				+ "_id integer primary key autoincrement,"
				+ "player_name text,score integer)");
	}

	// 创建Endless_Score表
	public void CreateEStable(SQLiteDatabase db) {
		db.execSQL("create table Endless_Score("
				+ "_id integer primary key autoincrement,"
				+ "player_name text,score integer)");
	}

	// 创建Hell_Score表
	public void CreateHStable(SQLiteDatabase db) {
		db.execSQL("create table Hell_Score("
				+ "_id integer primary key autoincrement,"
				+ "player_name text,score integer)");
	}

	// 向Advance_Score表中插入数据
	public void InsertAs(Advance_Score as) {
		if (openDB()) {
			ContentValues values = new ContentValues();
			values.put("player_name", as.getPlayer_name());
			values.put("score", as.getScore());
			db.insert("Advance_Score", null, values);
		}
	}

	// 查询闯关模式分值最大的前十条数据
	public List<Advance_Score> SelectAs() {
		List<Advance_Score> aslist = new ArrayList<Advance_Score>();
		if (openDB()) {
			Cursor cursor = db.rawQuery(
					"select  * from Advance_Score order by score desc limit 10 ",
					null);
			if (cursor != null && cursor.getCount() > 0) {
				cursor.moveToFirst();
				do {
					Advance_Score as = new Advance_Score();
					as.setPlayer_name(cursor.getString(cursor
							.getColumnIndex("player_name")));
					as.setScore(cursor.getInt(cursor.getColumnIndex("score")));
					aslist.add(as);
				} while (cursor.moveToNext());
			} else {
				cursor.close();
			}
		}
		return aslist;
	}

	// 向Endless_Score表中插入数据
	public void InsertEs(Endless_Score es) {
		if (openDB()) {
			ContentValues values = new ContentValues();
			values.put("player_name", es.getPlayer_name());
			values.put("score", es.getScore());
			db.insert("Endless_Score", null, values);
		}
	}

	// 查询无尽模式分值最大的前十条数据
	public List<Endless_Score> SelectEs() {
		List<Endless_Score> eslist = new ArrayList<Endless_Score>();
		if (openDB()) {
			Cursor cursor = db.rawQuery(
					"select  * from Endless_Score order by score desc limit 10",
					null);
			if (cursor != null && cursor.getCount() > 0) {
				cursor.moveToFirst();
				do {
					Endless_Score es = new Endless_Score();
					es.setPlayer_name(cursor.getString(cursor
							.getColumnIndex("player_name")));
					es.setScore(cursor.getInt(cursor.getColumnIndex("score")));
					eslist.add(es);
				} while (cursor.moveToNext());
			} else {
				cursor.close();
			}
		}
		return eslist;
	}

	// 向Hell_Score表中插入数据
	public void InsertHs(Hell_Score hs) {
		if (openDB()) {
			ContentValues values = new ContentValues();
			values.put("player_name", hs.getPlayer_name());
			values.put("score", hs.getScore());
			db.insert("Hell_Score", null, values);
		}
	}

	// 查询地狱模式分值最大的前十条数据
	public List<Hell_Score> SelectHs() {
		List<Hell_Score> hslist = new ArrayList<Hell_Score>();
		if (openDB()) {
			Cursor cursor = db.rawQuery(
					"select  * from Hell_Score order by score desc limit 10", null);
			if (cursor != null && cursor.getCount() > 0) {
				cursor.moveToFirst();
				do {
					Hell_Score hs = new Hell_Score();
					hs.setPlayer_name(cursor.getString(cursor
							.getColumnIndex("player_name")));
					hs.setScore(cursor.getInt(cursor.getColumnIndex("score")));
					hslist.add(hs);
				} while (cursor.moveToNext());
			} else {
				cursor.close();
			}
		}
		return hslist;
	}
}
