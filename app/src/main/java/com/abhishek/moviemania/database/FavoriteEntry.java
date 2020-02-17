package com.abhishek.moviemania.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "favoritetable")
public class FavoriteEntry {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "movieid")
    private int movieid;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "posterpath")
    private String posterpath;

    @ColumnInfo(name = "overview")
    private String overview;

    @ColumnInfo(name = "backdrop_path")
    private String backdrop_path;

    @ColumnInfo(name = "vote_average")
    private String vote_average;

    @ColumnInfo(name = "release_date")
    private String release_date;

    @Ignore
    public FavoriteEntry(int movieid, String title, String posterpath, String overview, String backdrop_path, String release_date, String vote_average) {
        this.movieid = movieid;
        this.title = title;
        this.posterpath = posterpath;
        this.overview = overview;
        this.release_date = release_date;
        this.vote_average = vote_average;
        this.backdrop_path = backdrop_path;
    }

    public FavoriteEntry(int id, int movieid, String title, String posterpath, String overview, String backdrop_path, String release_date, String vote_average) {
        this.id = id;
        this.movieid = movieid;
        this.title = title;
        this.posterpath = posterpath;
        this.overview = overview;
        this.release_date = release_date;
        this.vote_average = vote_average;
        this.backdrop_path = backdrop_path;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMovieid() {
        return movieid;
    }

    public void setMovieid(int movieid) {
        this.movieid = movieid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPosterpath(String posterpath){ this.posterpath = posterpath; }

    public String getPosterpath() { return posterpath; }

    public void setOverview(String image) { this.overview = overview; }

    public String getOverview() { return overview; }

}