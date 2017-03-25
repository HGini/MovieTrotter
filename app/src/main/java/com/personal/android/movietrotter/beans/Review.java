package com.personal.android.movietrotter.beans;

/**
 * Created by Hemangini on 3/23/17.
 */

public class Review {

    public static final String API_KEY_ID = "id";
    public static final String API_KEY_AUTHOR = "author";
    public static final String API_KEY_CONTENT = "content";
    public static final String API_KEY_URL = "url";

    private String id;
    private String author;
    private String content;
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
