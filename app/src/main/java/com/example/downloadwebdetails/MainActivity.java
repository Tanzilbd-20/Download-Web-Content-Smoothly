package com.example.downloadwebdetails;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.spec.ECField;

public class MainActivity extends AppCompatActivity {

    public static void longInfo(String str) {
        if(str.length() > 4000) {
            Log.i("Info", str.substring(0, 4000));
            longInfo(str.substring(4000));
        } else
            Log.i("Info", str);
    }

    public class DownloadTask extends AsyncTask<String,Void, String>{

        StringBuilder stringBuilder = new StringBuilder();

        @Override
        protected String doInBackground(String... strings) {

            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();
                while(data !=-1){
                    char current = (char) data;
                   stringBuilder.append(current);
                    data = reader.read();
                }
                result = stringBuilder.toString();
                return result;
            }catch (Exception e){
                e.printStackTrace();
                return "Failed";
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DownloadTask task = new DownloadTask();
        String result = null;
        try {
            //here the site you want to download content.
            result = task.execute("https://google.com/").get();
        }catch (Exception e){
            e.printStackTrace();
        }
        Log.i("Result", result);
        longInfo(result);

    }
}