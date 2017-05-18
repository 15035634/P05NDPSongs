package sg.edu.rp.c347.p05ndpsongs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by 15035634 on 18/5/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "songs.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_SONG = "song";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_SONGTITLE = "songtitle";
    private static final String COLUMN_SINGER = "singer";
    private static final String COLUMN_YEAR = "year";
    private static final String COLUMN_STAR = "star";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createSongTableSql = "CREATE TABLE " + TABLE_SONG + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_SONGTITLE + " TEXT, "
                + COLUMN_SINGER + " TEXT, "
                + COLUMN_YEAR + " INTEGER, "
                + COLUMN_STAR + " INTEGER )";
        db.execSQL(createSongTableSql);
        Log.i("info", "created tables");


    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SONG);
        onCreate(db);
    }


    public long insertSong(String songtitle, String singers, int year, int star) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SONGTITLE, songtitle);
        values.put(COLUMN_SINGER, singers);
        values.put(COLUMN_YEAR, year);
        values.put(COLUMN_STAR, star);
        long result = db.insert(TABLE_SONG, null, values);
        db.close();
        return result;
    }

    public ArrayList<Songs> getAllNotes() {
        ArrayList<Songs> songs = new ArrayList<Songs>();

        String selectQuery = "SELECT " + COLUMN_ID + ", "
                + COLUMN_SONGTITLE + ", " + COLUMN_SINGER + ", " + COLUMN_YEAR + ", "
                + COLUMN_STAR + " FROM " + TABLE_SONG;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String songTitle = cursor.getString(1);
                String singer = cursor.getString(2);
                int year = cursor.getInt(3);
                int star = cursor.getInt(4);
                Songs obj = new Songs(id, songTitle, singer, year, star);
                songs.add(obj);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return songs;
    }

    public ArrayList<Songs> getSongsWith5Stars() {
        ArrayList<Songs> songs = new ArrayList<Songs>();

        String selectQuery = "SELECT " + COLUMN_ID + ", "
                + COLUMN_SONGTITLE + ", " + COLUMN_SINGER + ", " + COLUMN_YEAR + ", "
                + COLUMN_STAR + " FROM " + TABLE_SONG + " WHERE " + COLUMN_STAR + "= 5";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String songTitle = cursor.getString(1);
                String singer = cursor.getString(2);
                int year = cursor.getInt(3);
                int star = cursor.getInt(4);
                Songs obj = new Songs(id, songTitle, singer, year, star);
                songs.add(obj);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return songs;

    }

    public ArrayList<String> getYears() {
        ArrayList<String> songs = new ArrayList<String>();

        String selectQuery = "SELECT " +  COLUMN_YEAR +  " FROM " + TABLE_SONG;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null) {
            do {
                String year = Integer.toString(cursor.getInt(0));
                songs.add(year);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return songs;


}}
