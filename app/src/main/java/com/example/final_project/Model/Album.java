package com.example.final_project.Model;

import java.util.ArrayList;
import java.util.Date;

public class Album {
    private String title;
    private String description;
    private Date releaseDate;
    private ArrayList<String> artists;
    private String cover;
    private int rating;

    public Album(String title, String description, Date releaseDate, ArrayList<String> artists, String cover, int rating) {
        this.title = title;
        this.description = description;
        this.releaseDate = releaseDate;
        this.artists = artists;
        this.cover = cover;
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public ArrayList<String> getArtists() {
        return artists;
    }

    public void setArtists(ArrayList<String> artists) {
        this.artists = artists;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
