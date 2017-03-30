package com.personal.android.movietrotter.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Hemangini on 3/29/17.
 */

public class MoviesDBHelper extends SQLiteOpenHelper {

    private static final int VERSION_BASIC = 1;

    private static final int VERSION = VERSION_BASIC;

    private static final String SQL_CREATE_MAIN = "CREATE TABLE " + MoviesDBContract.TABLE_NAME +
            " (" +
            MoviesDBContract.MoviesEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
            MoviesDBContract.MoviesEntry.COLUMN_BACKDROP_PATH + " TEXT NOT NULL, " +
            MoviesDBContract.MoviesEntry.COLUMN_GENRE_IDS + " TEXT NOT NULL, " +
            MoviesDBContract.MoviesEntry.COLUMN_IS_ADULT + " BOOLEAN, " +
            MoviesDBContract.MoviesEntry.COLUMN_IS_VIDEO + " BOOLEAN, " +
            MoviesDBContract.MoviesEntry.COLUMN_ORIGINAL_LANG + " TEXT NOT NULL, " +
            MoviesDBContract.MoviesEntry.COLUMN_ORIGINAL_TITLE + " TEXT NOT NULL, " +
            MoviesDBContract.MoviesEntry.COLUMN_OVERVIEW + " TEXT NOT NULL, " +
            MoviesDBContract.MoviesEntry.COLUMN_POPULARITY + " DOUBLE PRECISION NOT NULL, " +
            MoviesDBContract.MoviesEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, " +
            MoviesDBContract.MoviesEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL, " +
            MoviesDBContract.MoviesEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
            MoviesDBContract.MoviesEntry.COLUMN_VOTE_AVG + " DOUBLE PRECISION NOT NULL, " +
            MoviesDBContract.MoviesEntry.COLUMN_VOTE_COUNT + " INTEGER NOT NULL)";



    public MoviesDBHelper(Context context) {
        super(context, MoviesDBContract.TABLE_NAME + ".db", null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_MAIN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MoviesDBContract.TABLE_NAME);
        db.execSQL("DELETE FROM SQLITE SEQUENCE WHERE NAME = '" + MoviesDBContract.TABLE_NAME + "'");
        onCreate(db);
    }
}
