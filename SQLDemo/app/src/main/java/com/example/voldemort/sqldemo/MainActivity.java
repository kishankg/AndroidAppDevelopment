package com.example.voldemort.sqldemo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        try{

            SQLiteDatabase myDataBase = this.openOrCreateDatabase("Users",MODE_PRIVATE,null);

            myDataBase.execSQL("CREATE TABLE IF NOT EXISTS newUsers(name VARCHAR,age INT(3), id INT PRIMARY KEY)");

            myDataBase.execSQL("CREATE TABLE IF NOT EXISTS users (name VARCHAR,age INT(3))");

            //myDataBase.execSQL("INSERT INTO newUsers(name, age) VALUES ('Kishan',19)");

            //myDataBase.execSQL("INSERT INTO newUsers(name, age) VALUES ('Arpit',19)");

            //myDataBase.execSQL("INSERT INTO newUsers(name,age) VALUES('Yash',20)");

            //myDataBase.execSQL("INSERT INTO newUsers(name,age) VALUES('Yash',21)");

            //myDataBase.execSQL("UPDATE users SET age = 20 WHERE name  ='Arpit'");

            //myDataBase.execSQL("DELETE FROM users WHERE name = 'Yash'");

            Cursor c = myDataBase.rawQuery("SELECT * FROM users",null);

            int nameIndex = c.getColumnIndex("name");

            int ageIndex = c.getColumnIndex("age");

            int idIndex = c.getColumnIndex("id");

            c.moveToFirst();
            while (c!=null)
            {
                Log.i("UserResult",c.getString(nameIndex));
                Log.i("UserResult",c.getString(ageIndex).toString());

                Log.i("UserResult",c.getString(idIndex));

                c.moveToNext();
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
