package com.example.voldemort.addingnewactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public void toSecondActivity(View view)
    {
        String t = view.getTag().toString();
        int tag=Integer.valueOf(t);
        String value="";
        if(tag==0)
        {
            value="Kishan";
        }
        else if(tag==1)
        {
            value="Arpit";
        }
        else if(tag==2)
        {
            value="Yash";
        }
        else if(tag==3)
        {
            value="Sandeep";
        }
        Intent intent = new Intent(getApplicationContext(),SecondActivity.class);
        intent.putExtra("username",value);

        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
