package com.example.voldemort.guesstheceleb;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    Button option0;
    Button option1;
    Button option2;
    Button option3;
    Random rand = new Random();
    int count=0;

    TextView textView4;

    String[] option = new String[4] ;
    int correctOption;

    String result1;
    Pattern p1 = Pattern.compile("img alt=\"(.*?)\"height=");
    Pattern p2 = Pattern.compile("src=\"(.*?)\"width=");
    Matcher m1;
    Matcher m2;


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

    public class ImageDownloader extends AsyncTask<String,Void,Bitmap>
    {
        @Override
        protected Bitmap doInBackground(String... urls) {

            try{
                URL url = new URL(urls[0]);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.connect();

                InputStream inputStream = connection.getInputStream();

                Bitmap myBitMap = BitmapFactory.decodeStream(inputStream);

                return myBitMap;

            }catch (MalformedURLException e)
            {
                e.printStackTrace();
            }catch (IOException e)
            {
                e.printStackTrace();
            }

            Toast.makeText(getApplicationContext(),"Errrrr",Toast.LENGTH_LONG).show();
            return null;

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView)findViewById(R.id.imageView);
        option0 = (Button)findViewById(R.id.option0);
        option1 = (Button)findViewById(R.id.option1);
        option2 = (Button)findViewById(R.id.option2);
        option3 = (Button)findViewById(R.id.option3);



        DownloadTask task = new DownloadTask();
        String result =null;
        try
        {
            result = task.execute("https://www.imdb.com/list/ls052283250/").get();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        catch(ExecutionException e)
        {
            e.printStackTrace();
        }
        result1=result;
        m1 = p1.matcher(result);
        m2 = p2.matcher(result);
        Log.i("Contents of URL: ",result);

        while (m1.find())
        {
            option0.setText(m1.group(1));
            break;
        }
        while (m1.find())
        {
            option0.setText(m1.group(1));
            break;
        }

        while (m2.find())
        {
            option1.setText(m2.group(1));
            break;
        }
        while (m2.find())
        {
            ImageDownloader task1 = new ImageDownloader();
            Bitmap myImage;
            try {
                myImage = task1.execute(m2.group(1)).get();

                imageView.setImageBitmap(myImage);

            }catch (Exception e)
            {
                e.printStackTrace();
            }

            option1.setText(m2.group(1));
            break;
        }

        newOptions(m1.group(1),m2.group(1));
    }

    public void newOptions(String m,String p)
    {

        ImageDownloader task1 = new ImageDownloader();
        Bitmap myImage;
        try {
            myImage = task1.execute(p).get();

            imageView.setImageBitmap(myImage);

        }catch (Exception e)
        {
            e.printStackTrace();
        }


        correctOption = rand.nextInt(4);

        option[correctOption] = m;

        for(int i=0;i<4;i++)
        {
            Matcher m3 = p1.matcher(result1);
            if(i==correctOption)
            {
                continue;
            }
            else
            {
                int r = rand.nextInt(92)+3;
                while (m3.find())
                {
                    r--;
                    if(r==0) {
                        break;
                    }
                }
                option[i] = m3.group(1);
            }
        }

        option0.setText(option[0]);
        option1.setText(option[1]);
        option2.setText(option[2]);
        option3.setText(option[3]);
    }

    public void clicked(View view)
    {
        count++;
        String id1 = view.getTag().toString();
        int id = Integer.valueOf(id1);
        if(id==correctOption)
        {

            Toast.makeText(getApplicationContext(),"Correct",Toast.LENGTH_SHORT).show();
        }
        else
        {

            Toast.makeText(getApplicationContext(),"Incorrect",Toast.LENGTH_SHORT).show();
        }

        while(m1.find())
        {
            break;
        }
        while (m2.find())
        {
            break;
        }

        newOptions(m1.group(1),m2.group(1));
    }


}
