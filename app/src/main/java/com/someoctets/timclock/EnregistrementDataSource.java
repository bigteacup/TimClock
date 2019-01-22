package com.someoctets.timclock;





import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class EnregistrementDataSource {




    public SQLiteDatabase getDatabase() {

        return database;
    }

    // Champs de la base de données
    private SQLiteDatabase database;
    private DBOpenHelper dbHelper;
    private String[] allColumns = { DBOpenHelper.COLUMN_ID, DBOpenHelper.COLUMN_DATE, DBOpenHelper.COLUMN_HOURIN, DBOpenHelper.COLUMN_HOUROUT, DBOpenHelper.COLUMN_PAUSE };

    public EnregistrementDataSource(Context context) {

        dbHelper = new DBOpenHelper(context);

    }






    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }







    public void close() {

        dbHelper.close();

    }




    public Enregistrement createEnregistrement(String keyDate, long keyIn, long keyOut, long keyPause ) {

        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.COLUMN_DATE, keyDate);
        values.put(DBOpenHelper.COLUMN_HOURIN, keyIn);
        values.put(DBOpenHelper.COLUMN_HOUROUT, keyOut);
        values.put(DBOpenHelper.COLUMN_PAUSE, keyPause);
        long insertId = database.insert(DBOpenHelper.TABLE_DATE, null, values);
        Cursor cursor = database.query(DBOpenHelper.TABLE_DATE, allColumns, DBOpenHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Enregistrement newEnregistrement = cursorToEnregistrement(cursor);
        cursor.close();
        Log.i("debug", "ZZZZZZZZZZZZZZZZZZ: ");
        return newEnregistrement;
}


    public Enregistrement lireEnregistrement(String keyDate ) {

        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.COLUMN_DATE, keyDate);
                Cursor cursor = database.query(DBOpenHelper.TABLE_DATE, allColumns, null,null, null, null, null);
        cursor.moveToFirst();
        Enregistrement enr = null;
        while (!cursor.isAfterLast()) {
            if (cursor.getString(1).equals(keyDate)) {
                enr = cursorToEnregistrement(cursor);

            //    Log.i("debug", "trouve : ");
                cursor.moveToNext();
                break;
            } else {
                cursor.moveToNext();
            }
        }
        cursor.close();

    return enr ;

    }



        public void deleteEnregistrement(Enregistrement date) {
        long id = date.getId();
        System.out.println("Enregistrement deleted with id: " + id);

        database.delete(DBOpenHelper.TABLE_DATE, DBOpenHelper.COLUMN_ID + " = " + id, null);

    }











    public ArrayList<Enregistrement> getAllEnregistrements() {
        ArrayList<Enregistrement> dates = new ArrayList<Enregistrement>();
        open();
        database.isReadOnly();


      //  try {
            Cursor cursor = database.query(DBOpenHelper.TABLE_DATE, allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Enregistrement date = cursorToEnregistrement(cursor);
            dates.add(date);
            cursor.moveToNext();
        }
        // assurez-vous de la fermeture du curseur
        cursor.close();
     //   }catch (Exception e){

     //   }
        return dates;
    }











    private Enregistrement cursorToEnregistrement(Cursor cursor) {
        Enregistrement enregistrement = new Enregistrement();
        enregistrement.setId(cursor.getLong(0));
        enregistrement.setDate(cursor.getString(1));
        enregistrement.setIn(cursor.getLong(2));
        enregistrement.setOut(cursor.getLong(3));
        enregistrement.setPause(cursor.getLong(4));
        return enregistrement;
    }
}