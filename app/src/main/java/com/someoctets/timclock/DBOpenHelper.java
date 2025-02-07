package com.someoctets.timclock;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class DBOpenHelper extends SQLiteOpenHelper {

    public static final String TABLE_HEURES_DE_TRAVAIL = "HEURES_DE_TRAVAIL";
    public static final String TABLE_PLAGES_DE_DATES = "PLAGES_DE_DATES";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_DATE = "_date";
    public static final String COLUMN_HOURIN = "_hourin";
    public static final String COLUMN_HOUROUT = "_hourout";
    public static final String COLUMN_PAUSE = "_pause";
    public static final String COLUMN_DATEIN = "_datein";
    public static final String COLUMN_DATEOUT = "_dateout";
    public static final String COLUMN_LIBELLE = "_libelle";
    private static final String DATABASE_NAME = "timclockdate.db";
    private static final int DATABASE_VERSION = 1;

  //  private static final String DATABASE_CREATE_color = "CREATE TABLE IF NOT EXISTS files(color text, incident_id text)";

    // Commande sql pour la création de la base de données
    private static final String DATABASE_CREATE_HEURES_DE_TRAVAIL = " create table "
            +  TABLE_HEURES_DE_TRAVAIL + " ("
            +  COLUMN_ID + " INTEGER primary key autoincrement, "
            +  COLUMN_DATE + " TEXT NOT NULL, "
            +  COLUMN_HOURIN + " INTEGER, "
            +  COLUMN_HOUROUT + " INTEGER, "
            +  COLUMN_PAUSE + " INTEGER);";

    private static final String DATABASE_CREATE_PLAGES_DE_DATES = " create table "
            +  TABLE_PLAGES_DE_DATES + " ("
            +  COLUMN_ID + " INTEGER primary key autoincrement, "
            +  COLUMN_DATEIN + " INTEGER, "
            +  COLUMN_DATEOUT + " INTEGER, "
            +  COLUMN_LIBELLE + " TEXT);";

    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE_HEURES_DE_TRAVAIL);
        database.execSQL(DATABASE_CREATE_PLAGES_DE_DATES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DBOpenHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HEURES_DE_TRAVAIL );
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAGES_DE_DATES );
        onCreate(db);
    }





    //public static String DB_FILEPATH = "/data/data/timclock/databases/export_";
    // public static String DB_FILEPATH2 = this.context.getDatabasePath("db_filename");
    public boolean exportDatabase(String sourceDbPath, String cibleDbPath) throws IOException {

        // Close the SQLiteOpenHelper so it will commit the created empty
        // database to internal storage.
        close();
        File source = new File(sourceDbPath);
        File cible = new File(cibleDbPath);
        if (source.exists()) {
            Outils.copyFile(new FileInputStream(source), new FileOutputStream(cible));
            // Access the copied database so SQLiteHelper will cache it and mark
            // it as created.
            getWritableDatabase().close();
            return true;
        }
        return false;
    }



}