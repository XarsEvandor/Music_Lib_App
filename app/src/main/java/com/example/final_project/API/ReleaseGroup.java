package com.example.final_project.API;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReleaseGroup {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("type-id")
    @Expose
    private String typeId;
    @SerializedName("score")
    @Expose
    private Integer score;
    @SerializedName("primary-type-id")
    @Expose
    private String primaryTypeId;
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("first-release-date")
    @Expose
    private String firstReleaseDate;
    @SerializedName("primary-type")
    @Expose
    private String primaryType;
    @SerializedName("artist-credit")
    @Expose
    private List<Object> artistCredit = null;
    @SerializedName("releases")
    @Expose
    private List<Object> releases = null;
    @SerializedName("tags")
    @Expose
    private List<Object> tags = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getPrimaryTypeId() {
        return primaryTypeId;
    }

    public void setPrimaryTypeId(String primaryTypeId) {
        this.primaryTypeId = primaryTypeId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstReleaseDate() {
        return firstReleaseDate;
    }

    public void setFirstReleaseDate(String firstReleaseDate) {
        this.firstReleaseDate = firstReleaseDate;
    }

    public String getPrimaryType() {
        return primaryType;
    }

    public void setPrimaryType(String primaryType) {
        this.primaryType = primaryType;
    }

    public List<Object> getArtistCredit() {
        return artistCredit;
    }

    public void setArtistCredit(List<Object> artistCredit) {
        this.artistCredit = artistCredit;
    }

    public List<Object> getReleases() {
        return releases;
    }

    public void setReleases(List<Object> releases) {
        this.releases = releases;
    }

    public List<Object> getTags() {
        return tags;
    }

    public void setTags(List<Object> tags) {
        this.tags = tags;
    }

}