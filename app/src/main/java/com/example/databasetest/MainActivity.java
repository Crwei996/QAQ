package com.example.databasetest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity<datadelete> extends AppCompatActivity {
    private MyDatabaseHelper dpHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dpHelper = new MyDatabaseHelper(this,"BookStore.db",null,3);
        Button createDatabse = (Button) findViewById(R.id.create_database);
        createDatabse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dpHelper.getWritableDatabase();
            }
        });
        Button add_btn = (Button) findViewById(R.id.add_data);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = dpHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("name","The Da Vinci Code");
                values.put("author","Dan Brown");
                values.put("pages",454);
                values.put("price",16.96);
                db.insert("Book",null,values);
                values.clear();
                values.put("name","The Lost Symbol");
                values.put("author","Dan Brown");
                values.put("pages",510);
                values.put("price",19.95);
                db.insert("Book",null,values);
            }
        });
        Button update_btn = (Button) findViewById(R.id.updata_data);
        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = dpHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("price",10.99);
                db.update("Book",values,"name = ?",new String[]{"The Da Vinci Code"});
            }
        });
        Button delete_btn = (Button) findViewById(R.id.delete_data);
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = dpHelper.getWritableDatabase();
                db.delete("Book","pages > ?",new String[]{"500"});
            }
        });
        Button query_btn = (Button) findViewById(R.id.query_data);
        query_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = dpHelper.getWritableDatabase();
                Cursor cursor = db.query("Book",null,null,null,null,null,null);
                if(cursor.moveToFirst()){
                    do{
                        @SuppressLint("Range")String name = cursor.getString(cursor.getColumnIndex("name"));
                        @SuppressLint("Range")String author = cursor.getString(cursor.getColumnIndex("author"));
                        @SuppressLint("Range")int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        @SuppressLint("Range")double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        Log.d("MainActivity","Book name is "+name);
                        Log.d("MainActivity","Book author is "+ author);
                        Log.d("MainActivity","Book pages is "+ pages);
                        Log.d("MainActivity","Book price is "+price);
                    }while(cursor.moveToNext());
                }
                cursor.close();
            }
        });
    }
}