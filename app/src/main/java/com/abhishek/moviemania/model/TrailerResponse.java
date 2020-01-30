package com.abhishek.moviemania.model;

import java.util.List;

public class TrailerResponse {

    private int id_trailer;
    private List<Trailer> results;


    public List<Trailer> getResults() {
        return results;
    }

    public void setResults(List<Trailer> results) {
        this.results = results;
    }


    public int getId_trailer() {
        return id_trailer;
    }

    public void setId_trailer(int id_trailer) {
        this.id_trailer = id_trailer;
    }


    public TrailerResponse(int id_trailer) {
        this.id_trailer = id_trailer;
    }

}
