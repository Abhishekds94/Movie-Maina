package com.abhishek.moviemania.model;

import java.util.List;

public class ReviewResponse {

    private int id_review;
    private List<Review> reviews;


    public int getId_review() {
        return id_review;
    }

    public void setId_review(int id_review) {
        this.id_review = id_review;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }


    public ReviewResponse(List<Review> reviews) {
        this.reviews = reviews;
    }


}
