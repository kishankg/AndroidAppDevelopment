package com.example.voldemort.jsontut;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {


    public class DownloadTask extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            InputStream is = null;
            BufferedReader br;
            String line;


            try
            {
                url = new URL(urls[0]);

                is = url.openStream();
                br = new BufferedReader(new InputStreamReader(is));

                while((line=br.readLine())!=null)
                {
                    result = result + line;
                }
                return result;


            }catch (Exception e)
            {
                e.printStackTrace();

                return "Failed";
            }finally {
                try{
                    if(is!=null) is.close();
                }catch (IOException e)
                {
                    e.printStackTrace();
                    //Nothing to see here;
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DownloadTask task = new DownloadTask();
        String result =null;
        try
        {
            result = task.execute("https://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=b6907d289e10d714a6e88b30761fae22").get();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        catch(ExecutionException e)
        {
            e.printStackTrace();
        }

        try {
            JSONObject jsonObject = new JSONObject(result);

            String weatherInfo = jsonObject.getString("weather");

            Log.i("Weather Information", weatherInfo);

            JSONArray arr = new JSONArray(weatherInfo);

            for(int i=0;i<arr.length();i++)
            {
                JSONObject jsonPart = arr.getJSONObject(i);

                Log.i("main", jsonPart.getString("main"));
                Log.i("Description",jsonPart.getString("description"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
