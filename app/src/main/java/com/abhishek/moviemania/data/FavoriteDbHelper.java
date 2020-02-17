package com.abhishek.moviemania.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.abhishek.moviemania.model.MyDataa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static android.icu.text.MessagePattern.ArgType.SELECT;

public class FavoriteDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "favorite.db";

    private static final int DATABASE_VERSION = 2;

    public static final String LOGTAG = "FAVORITE";
    public static Collection<? extends MyDataa> getAllFavorite;

    SQLiteOpenHelper dbhandler;
    SQLiteDatabase db;

    public FavoriteDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void open(){
        Log.i(LOGTAG, "Database Opened");
        db = dbhandler.getWritableDatabase();
    }

    public void close(){
        Log.i(LOGTAG, "Database Closed");
        dbhandler.close();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_FAVORITE_TABLE = "CREATE TABLE " + FavoriteContract.FavoriteEntry.TABLE_NAME + " (" +
                FavoriteContract.FavoriteEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                FavoriteContract.FavoriteEntry.COLUMN_MOVIEID + " INTEGER, " +
                FavoriteContract.FavoriteEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                FavoriteContract.FavoriteEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, " +
                FavoriteContract.FavoriteEntry.COLUMN_PLOT_SYNOPSIS + " TEXT NOT NULL" +
                FavoriteContract.FavoriteEntry.COLUMN_PLOT_RATING + " TEXT NOT NULL" +
                FavoriteContract.FavoriteEntry.COLUMN_PLOT_RELEASE + " TEXT NOT NULL" +
                FavoriteContract.FavoriteEntry.COLUMN_BACKDROP_PATH + " TEXT NOT NULL" +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FavoriteContract.FavoriteEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);

    }

    public void addFavorite(MyDataa movie){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FavoriteContract.FavoriteEntry.COLUMN_MOVIEID, movie.getId());
        values.put(FavoriteContract.FavoriteEntry.COLUMN_TITLE, movie.getTitle());
        values.put(FavoriteContract.FavoriteEntry.COLUMN_POSTER_PATH, movie.getPoster_path());
        values.put(FavoriteContract.FavoriteEntry.COLUMN_PLOT_SYNOPSIS, movie.getOverview());
        values.put(FavoriteContract.FavoriteEntry.COLUMN_PLOT_RATING, movie.getVote_average());
        values.put(FavoriteContract.FavoriteEntry.COLUMN_PLOT_RELEASE, movie.getRelease_date());
        values.put(FavoriteContract.FavoriteEntry.COLUMN_BACKDROP_PATH, movie.getBackdrop_path());

        db.insert(FavoriteContract.FavoriteEntry.TABLE_NAME, null, values);
        db.close();
    }

    public void deleteFavorite(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(FavoriteContract.FavoriteEntry.TABLE_NAME, FavoriteContract.FavoriteEntry.COLUMN_MOVIEID+ "=" + id, null);
    }

    public List<MyDataa> getAllFavorite(){
        String[] columns = {
                FavoriteContract.FavoriteEntry._ID,
                FavoriteContract.FavoriteEntry.COLUMN_MOVIEID,
                FavoriteContract.FavoriteEntry.COLUMN_TITLE,
                FavoriteContract.FavoriteEntry.COLUMN_POSTER_PATH,
                FavoriteContract.FavoriteEntry.COLUMN_PLOT_SYNOPSIS,
                FavoriteContract.FavoriteEntry.COLUMN_PLOT_RATING,
                FavoriteContract.FavoriteEntry.COLUMN_PLOT_RELEASE,
                FavoriteContract.FavoriteEntry.COLUMN_BACKDROP_PATH

        };
        String sortOrder =
                FavoriteContract.FavoriteEntry._ID + " ASC";
        List<MyDataa> favoriteList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(FavoriteContract.FavoriteEntry.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                sortOrder);

        if (cursor.moveToFirst()) {
            do {
                MyDataa mydataa = new MyDataa();
                mydataa.setId((Integer.parseInt(String.valueOf(cursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_MOVIEID)))));
                mydataa.setTitle(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_TITLE)));
                mydataa.setPoster_path(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_POSTER_PATH)));
                mydataa.setOverview(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_PLOT_SYNOPSIS)));
                mydataa.setVote_average(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_PLOT_RATING)));
                mydataa.setRelease_date(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_PLOT_RELEASE)));
                mydataa.setBackdrop_path(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_BACKDROP_PATH)));

                favoriteList.add(mydataa);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return favoriteList;
        }
    }

