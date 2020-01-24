package com.abhishek.moviemania.model;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.abhishek.moviemania.R;
import com.bumptech.glide.Glide;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private Context context;
    private List<MyDataa> myDataas;

    public Adapter(List<MyDataa> myDataas, Context context){
        this.myDataas = myDataas;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_item_movie, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        final MyViewHolder holder = myViewHolder;
        final MyDataa model = myDataas.get(i);

        Log.i("Response Data - ", "Response Data-"+myDataas);

        myViewHolder.mMovieTitle.setText(myDataas.get(i).getMovie_title());

        Glide.with(context)
                .load("https://image.tmdb.org/t/p/w500" + String.valueOf(model.getPoster_path()))
                .into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return myDataas.size();
    }


    public void addAll(List<MyDataa> myDataas){
        for(MyDataa myDataa:myDataas)
            add(myDataa);
    }

    public void add(MyDataa myDataa){
        this.myDataas.add(myDataa);
        notifyItemInserted(myDataas.size()-1);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView mImageView;
        TextView mMovieTitle;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.iv_movie_poster);
            mMovieTitle = itemView.findViewById(R.id.tv_movie_title);
        }
    }

}
