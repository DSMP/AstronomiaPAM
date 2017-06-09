package com.example.damian.astronomiapam.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by student on 6/9/2017.
 */

public class SQLiteAdapter {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "favourites.db";
    private static final String DB_TABLE = "favourites";
    public static final String KEY_ID = "_id";
    public static final String ID_OPTIONS = "INTEGER PRIMARY KEY AUTOINCREMENT";

    public static final String KEY_OPTIONS = "TEXT NOT NULL";
    public static final String KEY_CITY = "City";


    private SQLiteDatabase db;
    private Context context;

    private DatabaseHelper dbHelp;
    private static final String DB_CREATE_TABLE =
            "CREATE TABLE " + DB_TABLE + "( " +
                    KEY_ID + " " + ID_OPTIONS + ", " +
                    KEY_CITY + " " + KEY_OPTIONS +
                    ");";
    private static final String DROP_TABLE =
            "DROP TABLE IF EXISTS " + DB_TABLE;


    private static class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context, String name,
                              SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(DB_CREATE_TABLE);
            Log.d("DB", "Database creating...");
            Log.d("DB", "Table " + DB_TABLE + " ver." + DB_VERSION + " created");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL(DROP_TABLE);

            Log.d("DB", "Database updating...");
            Log.d("DB", "Table " + DB_TABLE + " updated from ver." + i + " to ver." + i1);
            Log.d("DB", "All data is lost.");

            onCreate(sqLiteDatabase);
        }
    }

    public SQLiteAdapter(Context context) {
        this.context = context;
    }

    public SQLiteAdapter open() {
        dbHelp = new DatabaseHelper(context, DB_NAME, null, DB_VERSION);
        try {
            db = dbHelp.getWritableDatabase();
        } catch (SQLException e) {
            db = dbHelp.getReadableDatabase();
        }
        return this;
    }

    public void close() {
        dbHelp.close();
    }

    public long insert(FavouriteEntity entity) {
        ContentValues newValues = new ContentValues();
        newValues.put(KEY_CITY, entity.getCity());
        return db.insert(DB_TABLE, null, newValues);
    }

    public List<String> printDB() {
        List<String> elements = new ArrayList<>();
        Cursor allRows = db.rawQuery("SELECT * FROM " + DB_TABLE, null);
        String[] columnNames = allRows.getColumnNames();

        if(allRows.moveToFirst()) {
            do {
                String input = "";

                for (String name : columnNames) {if(!name.equals("_id"))
                    input += String.format("%s: %s ", name,
                            allRows.getString(allRows.getColumnIndex(name)));
                }
                elements.add(input);
            }while (allRows.moveToNext());
        }


       /* if (allRows.moveToFirst()) {
            String[] columnNames = allRows.getColumnNames();
            do {
                for (String name : columnNames) {
                    tableString += String.format("%s: %s\n", name,
                            allRows.getString(allRows.getColumnIndex(name)));
                }
                tableString += "\n";

            } while (allRows.moveToNext());
        }

        Log.d("TABLE", tableString);*/
        return elements;
    }
}
