package com.abhishek.moviemania.ViewModel;

import android.app.Application;
import android.util.Log;

import com.abhishek.moviemania.database.AppDatabase;
import com.abhishek.moviemania.database.FavoriteEntry;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MainViewModel extends AndroidViewModel {

    // Constant for logging
    private static final String TAG = MainViewModel.class.getSimpleName();

    private LiveData<List<FavoriteEntry>> favorite;

    public MainViewModel(Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the tasks from the DataBase");
        favorite = database.favoriteDao().loadAllFavorite();

    }
    public LiveData<List<FavoriteEntry>> getFavorite() {
        if (favorite == null){
            Log.e("N","Null from database");
        }else{
            return favorite;
        }
        return null;
    }
}
