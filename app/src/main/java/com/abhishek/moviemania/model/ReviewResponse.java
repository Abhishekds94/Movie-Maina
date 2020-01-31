package com.abhishek.moviemania.model;

import java.util.List;

public class ReviewResponse {

    private int id_review;
    private List<Review> results;


    public List<Review> getResults() {
        return results;
    }

    public void setReviews(List<Review> reviews) {
        this.results = reviews;
    }

    public int getId_review() {
        return id_review;
    }

    public void setId_review(int id_review) {
        this.id_review = id_review;
    }

    public ReviewResponse(int id_review) {
        this.id_review = id_review;
    }

}
