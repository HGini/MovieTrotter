package com.personal.android.movietrotter.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


/**
 * Created by Hemangini on 3/29/17.
 */

public class MoviesContentProvider extends ContentProvider {

    private SQLiteOpenHelper dbHelper;

    @Override
    public boolean onCreate() {
        dbHelper = new MoviesDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        switch (buildUriMatcher().match(uri)) {

            case URI_MATCH_TABLE: {
                cursor = dbHelper.getReadableDatabase().query(MoviesDBContract.TABLE_NAME, projection,
                        selection, selectionArgs, null, null, sortOrder);
                break;
            }

            case URI_MATCH_TABLE_ITEM: {
                cursor = dbHelper.getReadableDatabase().query(MoviesDBContract.TABLE_NAME, projection,
                        MoviesDBContract.MoviesEntry.COLUMN_MOVIE_ID + " ? ",
                        new String[]{String.valueOf(ContentUris.parseId(uri))}, null, null, sortOrder);
                break;
            }

            default: {
                throw new UnsupportedOperationException("Unknown uri : " + uri);
            }
        }

        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Uri returnUri;
        switch (buildUriMatcher().match(uri)) {

            case URI_MATCH_TABLE: {
                long id = db.insert(MoviesDBContract.TABLE_NAME, null, values);
                if (id > 0)
                    returnUri = MoviesDBContract.buildUriForID(id);
                else
                    throw new SQLiteException("Failed to insert row in uri : " + uri);
                break;
            }

            default: {
                throw new UnsupportedOperationException("Unknown uri : " + uri);
            }
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsDeleted = 0;
        switch (buildUriMatcher().match(uri)) {

            case URI_MATCH_TABLE: {
                rowsDeleted = db.delete(MoviesDBContract.TABLE_NAME, selection, selectionArgs);
                db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + MoviesDBContract.TABLE_NAME + "'");
                break;
            }

            case URI_MATCH_TABLE_ITEM: {
                rowsDeleted = db.delete(MoviesDBContract.TABLE_NAME, MoviesDBContract.MoviesEntry._ID +
                        " ? ", new String[]{String.valueOf(ContentUris.parseId(uri))});
                db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + MoviesDBContract.TABLE_NAME + "'");
                break;
            }

            default: {
                throw new UnsupportedOperationException("Unknown uri : " + uri);
            }
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsUpdated = 0;

        if (values == null) {
            throw new IllegalArgumentException("Cannot have null content values");
        }

        switch (buildUriMatcher().match(uri)) {

            case URI_MATCH_TABLE: {
                rowsUpdated = db.update(MoviesDBContract.TABLE_NAME, values, selection, selectionArgs);
                break;
            }

            case URI_MATCH_TABLE_ITEM: {
                rowsUpdated = db.update(MoviesDBContract.TABLE_NAME, values, MoviesDBContract.MoviesEntry._ID + " ? ",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            }

            default: {
                throw new UnsupportedOperationException("Unknown uri : " + uri);
            }
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (buildUriMatcher().match(uri)) {

            case URI_MATCH_TABLE: {
                return MoviesDBContract.CONTENT_TYPE_DIR;
            }

            case URI_MATCH_TABLE_ITEM: {
                return MoviesDBContract.CONTENT_TYPE_ITEM;
            }

            default: {
                throw new UnsupportedOperationException("Unknown uri : " + uri);
            }
        }
    }


    private static final int URI_MATCH_TABLE = 0;
    private static final int URI_MATCH_TABLE_ITEM = 1;

    private static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(MoviesDBContract.CONTENT_AUTHORITY, MoviesDBContract.TABLE_NAME, URI_MATCH_TABLE);
        uriMatcher.addURI(MoviesDBContract.CONTENT_AUTHORITY, MoviesDBContract.TABLE_NAME + "/#", URI_MATCH_TABLE_ITEM);

        return uriMatcher;
    }
}
