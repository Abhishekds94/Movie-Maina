package com.abhishek.moviemania;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //To make full screen for splash screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        //Remove notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        checkInternetAvailibility();
    }

    public void navigate(){
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    public void checkInternetAvailibility()
    {
        if(isInternetAvailable())
        {
            new IsInternetActive().execute();
            navigate();
        }
        else {
            Toast.makeText(getApplicationContext(), "Internet Not Connected", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MainActivity.this, NoInternet.class);
            startActivity(intent);
            finish();
        }
    }

    public boolean isInternetAvailable() {
        try {

            ConnectivityManager connectivityManager
                    = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        } catch (Exception e) {

            Log.e("isInternetAvailable:",e.toString());
            return false;
        }
    }

    class IsInternetActive extends AsyncTask<Void, Void, String>
    {
        InputStream is = null;
        String json = "Fail";

        @Override
        protected String doInBackground(Void... params) {
            try {
                URL strUrl = new URL("http://icons.iconarchive.com/icons/designbolts/handstitch-social/24/Android-icon.png");
                //Here I have taken one android small icon from server, you can put your own icon on server and access your URL, otherwise icon may removed from another server.

                URLConnection connection = strUrl.openConnection();
                connection.setDoOutput(true);
                is =  connection.getInputStream();
                json = "Success";

            } catch (Exception e) {
                e.printStackTrace();
                json = "Fail";
            }
            return json;

        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null)
            {
                if(result.equals("Fail"))
                {
                    Toast.makeText(getApplicationContext(), "Internet Not Active", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Internet Active " + result, Toast.LENGTH_LONG).show();
                }
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Internet Not Active", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onPreExecute() {
            Toast.makeText(getBaseContext(),"Validating Internet",Toast.LENGTH_LONG).show();
            super.onPreExecute();
        }
    }
}
