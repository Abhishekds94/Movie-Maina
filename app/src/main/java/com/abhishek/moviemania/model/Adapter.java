package com.abhishek.moviemania.model;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.abhishek.moviemania.DetailActivity;
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
        myViewHolder.setIsRecyclable(false);
        Log.i("Response Data - ", "Response Data-"+myDataas);

        myViewHolder.mMovieTitle.setText(myDataas.get(i).gettitle());

        String xc = model.getPoster_path();
        char [] c_ = xc.toCharArray();
        if (c_[0] == 'f'){
            String x = "";
            for (int aa = 1; aa < c_.length; aa++){
                x += c_[aa];
            }
            Glide.with(context)
                    .load(x)
                    .into(holder.mImageView);
        }else{
            Glide.with(context)
                    .load("https://image.tmdb.org/t/p/w500" + String.valueOf(model.getPoster_path()))
                    .into(holder.mImageView);
        }
    }

    public void setMyDataas(List<MyDataa> MyDataa) {
        myDataas = MyDataa;
        notifyDataSetChanged();
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
        notifyItemInserted(myDataas.size()- myDataas.size());
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView mImageView;
        TextView mMovieTitle;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.iv_movie_poster);
            mMovieTitle = itemView.findViewById(R.id.tv_movie_title);

            //For navigation with the clicked movie details
            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick (View v){
                    int pos = getAdapterPosition();
                    if (pos!= RecyclerView.NO_POSITION){
                        MyDataa clickedDataItem = myDataas.get(pos);
                        Log.e("ID : ",String.valueOf(myDataas.get(pos).getId()));
                        Intent intent = new Intent(context, DetailActivity.class);
                        Log.e("c",String.valueOf(clickedDataItem.getId()));
                        Log.e("cBD",String.valueOf(clickedDataItem.getBackdrop_path()));
                        intent.putExtra("title", clickedDataItem.gettitle());
                        //intent.putExtra("id", Integer.valueOf(myDataas.get(pos).getId()));
                        intent.putExtra("id", Integer.valueOf(clickedDataItem.getId()));
                        intent.putExtra("poster_path", clickedDataItem.getPoster_path());
                        intent.putExtra("overview", clickedDataItem.getOverview());
                        intent.putExtra("vote_average", clickedDataItem.getVote_average());
                        intent.putExtra("release_date", clickedDataItem.getRelease_date());
                        intent.putExtra("backdrop_path", clickedDataItem.getBackdrop_path());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        Toast.makeText(v.getContext(),"You clicked " + clickedDataItem.getTitle(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
            );


        }
    }
}
