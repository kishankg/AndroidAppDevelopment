package com.example.voldemort.whatstheweather;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    EditText cityName;
    TextView resultTextView;

    public void findWeather(View view)
    {

        InputMethodManager mgr = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(cityName.getWindowToken(),0);

        DownloadTask task = new DownloadTask();
        String result =null;
        try
        {
            result = task.execute("https://samples.openweathermap.org/data/2.5/weather?q="+cityName.getText().toString()).get();
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

                String main = "";
                String desciption="";

                main = jsonPart.getString("main");
                desciption = jsonPart.getString("description");



                if(main!="" && desciption!="") {
                    String message = main+ " " +desciption;
                    resultTextView.setText(message);
                }

                Log.i("main", jsonPart.getString("main"));
                Log.i("Description",jsonPart.getString("description"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

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

        cityName = (EditText)findViewById(R.id.cityName);
        resultTextView =(TextView)findViewById(R.id.resultTextView);
    }
}
