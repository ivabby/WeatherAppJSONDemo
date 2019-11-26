package com.example.weatherappjsondemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    String KEY = "ea055d8560c97974a9d43585b143f909";
    String api = "http://api.openweathermap.org/data/2.5/weather?q=";
    EditText cityName;
    TextView weather;
    public class DownloadTask extends AsyncTask<String , Void , String>{

        @Override
        protected String doInBackground(String... urls) {
            try{
                URL url = new URL(urls[0]);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                InputStream in = connection.getInputStream();

                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                String res = "";

                while(data != -1){
                    res+=(char)data;
                    data = reader.read();
                }
                Log.d("res" , res);
                return res;
            } catch (Exception e){
                Log.d("error" , e.toString());
            }
            return null;
        }
    }

    public void weather(String result){
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray weather = jsonObject.getJSONArray("weather");

            jsonObject = weather.getJSONObject(0);
            Log.d("weather" , weather.toString());

            String description = jsonObject.getString("description");
            Log.d("weather" , description);

            this.weather.setText("It is " + description + " in " + cityName.getText().toString());
        } catch (Exception e){
            Log.d("error" , e.toString());
        }
    }

    public void find(View view){
        String url = api + cityName.getText().toString() + "&appid=" + KEY ;
        try {
            String result = (new DownloadTask()).execute(url).get();
            Log.d("Result" , result);

            weather(result);
        } catch (Exception e){
            Log.d("error" , e.toString());
        }
        Log.d("Url" , url);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityName = (EditText) findViewById(R.id.cityName);
        weather = (TextView) findViewById(R.id.weather);
    }
}
