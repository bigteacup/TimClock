package com.someoctets.timclock;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;



public class DBOpenHelper extends SQLiteOpenHelper {

    public static final String TABLE_DATE = "DBTable";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_DATE = "_date";
    public static final String COLUMN_HOURIN = "_hourin";
    public static final String COLUMN_HOUROUT = "_hourout";
    public static final String COLUMN_PAUSE = "_pause";

    private static final String DATABASE_NAME = "timclockdate.db";
    private static final int DATABASE_VERSION = 1;

    // Commande sql pour la création de la base de données
    private static final String DATABASE_CREATE = "create table "
            +  TABLE_DATE + " ("
            +  COLUMN_ID + " INTEGER primary key autoincrement, "
            +  COLUMN_DATE + " TEXT NOT NULL, "
            +  COLUMN_HOURIN + " INTEGER, "
            +  COLUMN_HOUROUT + " INTEGER, "
            +  COLUMN_PAUSE + " INTEGER);";

    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DBOpenHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATE);
        onCreate(db);
    }





}