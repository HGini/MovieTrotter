package com.personal.android.movietrotter.beans;

/**
 * Created by Hemangini on 3/22/17.
 */

public class Trailer {

    private String id;
    private String name;
    private String videoUrl;

    public static final String API_KEY_ID = "id";
    public static final String API_KEY_NAME = "name";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
