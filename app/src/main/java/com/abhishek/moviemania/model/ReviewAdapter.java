package com.abhishek.moviemania.model;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.abhishek.moviemania.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MyViewHolder1> {

    private Context mContext1;
    private List<Review> reviewList;


    public ReviewAdapter(Context mContext1, List<Review> reviewList){
        this.mContext1 = mContext1;
        this.reviewList = reviewList;
    }

    @Override
    public ReviewAdapter.MyViewHolder1 onCreateViewHolder(ViewGroup viewGroup1, int j) {
        View view1 = LayoutInflater.from(viewGroup1.getContext())
                .inflate(R.layout.review_card, viewGroup1, false);
        return new ReviewAdapter.MyViewHolder1(view1);
    }

    @Override
    public void onBindViewHolder(final ReviewAdapter.MyViewHolder1 viewHolder1, int j) {
        viewHolder1.name.setText(reviewList.get(j).getAuthor());
        String letter = reviewList.get(j).getAuthor();
        String rLetter=letter.substring(0,1);
        viewHolder1.rLetter.setText(rLetter);
        viewHolder1.content.setText(reviewList.get(j).getContent());
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public class MyViewHolder1 extends RecyclerView.ViewHolder{

        public TextView name;
        public TextView rLetter;
        public TextView content;

        public MyViewHolder1(View view1) {
            super(view1);
            name = (TextView) view1.findViewById(R.id.review_name);
            rLetter = (TextView) view1.findViewById(R.id.review_letter);
            content = (TextView) view1.findViewById(R.id.review_content);

        }
    }
}
