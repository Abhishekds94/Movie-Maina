package com.abhishek.moviemania.ViewModel;

import com.abhishek.moviemania.database.AppDatabase;
import com.abhishek.moviemania.database.FavoriteEntry;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class AddFavoriteViewModel extends ViewModel {

    private LiveData<FavoriteEntry> favorite;

    public AddFavoriteViewModel(AppDatabase database, int favoriteId) {
        favorite = database.favoriteDao().loadFavoriteById(favoriteId);
    }

    public LiveData<FavoriteEntry> getFavorite() {
        return favorite;
    }
}
