package com.personal.android.movietrotter.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Hemangini on 3/29/17.
 */

public final class MoviesDBContract {

    // Authority
    public static final String CONTENT_AUTHORITY = "com.personal.android.movietrotter.provider";

    // Tables
    public static final String TABLE_NAME = "fav_movies";

    // Content URIs
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

    // MIME Types
    public static final String CONTENT_TYPE_DIR = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" +
            CONTENT_AUTHORITY + "/" + TABLE_NAME;
    public static final String CONTENT_TYPE_ITEM = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" +
            CONTENT_AUTHORITY + "/" + TABLE_NAME;


    public static final class MoviesEntry implements BaseColumns {

        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_VOTE_COUNT = "vote_count";
        public static final String COLUMN_VOTE_AVG = "vote_avg";
        public static final String COLUMN_POPULARITY = "popularity";
        public static final String COLUMN_IS_ADULT = "is_adult";
        public static final String COLUMN_IS_VIDEO = "is_video";
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_GENRE_IDS = "genre_ids";
        public static final String COLUMN_ORIGINAL_TITLE = "original_title";
        public static final String COLUMN_ORIGINAL_LANG = "original_lang";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_BACKDROP_PATH = "backdrop_path";
    }

    // Method to build Uri when a row is inserted (NOTE: The Uri points to the inserted row)
    public static Uri buildUriForID(long id) {
        return ContentUris.withAppendedId(CONTENT_URI, id);
    }
}
