package com.personal.android.movietrotter.beans;

/**
 * Created by Hemangini on 3/17/17.
 */

public class Movie {

    public static final String JSON_KEY_POSTER_PATH = "poster_path";
    public static final String JSON_KEY_ADULT = "adult";
    public static final String JSON_KEY_OVERVIEW = "overview";
    public static final String JSON_KEY_RELEASE_DATE = "release_date";
    public static final String JSON_KEY_GENRE_IDS = "genre_ids";
    public static final String JSON_KEY_ID = "id";
    public static final String JSON_KEY_ORIGINAL_TITLE = "original_title";
    public static final String JSON_KEY_ORIGINAL_LANGUAGE = "original_language";
    public static final String JSON_KEY_TITLE = "title";
    public static final String JSON_KEY_BACKDROP_PATH = "backdrop_path";
    public static final String JSON_KEY_POPULARITY = "popularity";
    public static final String JSON_KEY_VOTE_COUNT = "vote_count";
    public static final String JSON_KEY_VIDEO = "video";
    public static final String JSON_KEY_VOTE_AVERAGE = "vote_average";

    private int id;
    private int voteCount;
    private double voteAverage;
    private double popularity;
    private boolean isAdult = true;
    private boolean isVideo = false;
    private String posterPath;
    private String overview;
    private String releaseDate;
    private String genreIDs;
    private String originalTitle;
    private String originalLang;
    private String title;
    private String backdropPath;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public boolean isAdult() {
        return isAdult;
    }

    public void setAdult(boolean adult) {
        isAdult = adult;
    }

    public boolean isVideo() {
        return isVideo;
    }

    public void setVideo(boolean video) {
        isVideo = video;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getGenreIDs() {
        return genreIDs;
    }

    public void setGenreIDs(String genreIDs) {
        this.genreIDs = genreIDs;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOriginalLang() {
        return originalLang;
    }

    public void setOriginalLang(String originalLang) {
        this.originalLang = originalLang;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }
}
