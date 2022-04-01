package com.artemis.ispeaksigns;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "iSpeakDB";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table IF NOT EXISTS CategoryTable(categoryName TEXT primary key, categoryColor TEXT, categoryTotalItems TEXT, categoryType TEXT, imageURL TEXT, categoryProgress INTEGER)");
        String[] bgColors = new String[]{
                        "outrageous_orange", "auburn", "bright_navy_blue", "jungle_green", "plump_purple",
                        "tulip", "japanese_indigo", "silver_lake_blue", "pink", "blue_cola",
                        "golden_puppy", "cornflower_blue", "flame", "apple", "may_green"};
        int[] itemCount = new int[]{7, 12, 7, 7, 26, 10, 7, 5, 7,
                                     6, 11, 8, 6, 4, 6};
        String[] categoryName = new String[]{
                        "Araw ng Linggo", "Buwan", "Lugar", "Emosyon", "Alpabeto",
                        "Numero", "Hayop", "Hugis", "Kulay", "Prutas",
                        "Gulay", "Parte ng Katawan", "Sasakyan", "Kasarian", "Miyembro ng Pamilya"  };
        String categorytype = "Salita";
        String[] imageURL = new String[]{
                "ic_araw_ng_linggo", "ic_buwan", "ic_lugar", "ic_emosyon", "ic_alpabeto",
                "ic_numero", "ic_hayop", "ic_hugis", "ic_kulay", "ic_prutas",
                "ic_gulay", "ic_parte_ng_katawan", "ic_sasakyan", "ic_kasarian", "ic_miyembro_ng_pamilya"
        };
        int[] progress = new int[]{3, 5, 7, 7, 19, 7, 3, 1, 0, 4, 5, 7, 3, 4, 1};

        for (int i = 0; i<categoryName.length; i++){
            ContentValues values = new ContentValues();
            values.put("categoryName", categoryName[i]);
            values.put("categoryColor", bgColors[i]);
            values.put("categoryTotalItems", itemCount[i]);
            values.put("categoryType", categorytype);
            values.put("imageURL", imageURL[i]);
            values.put("categoryProgress", progress[i]);
            DB.insert("CategoryTable", null, values);
        }
        String[] bgVideoColors = new String[]{"veronese_green", "violet_blue", "grape"};
        int[] itemVideoCount = new int[]{6, 7, 10};
        String[] categoryVideoName = new String[]{"Pagbati", "Pang-Emergency", "Pangkomunikasyon"};
        String[] imageVideoURL = new String[]{
          "ic_pagbati", "ic_pang_emergency", "ic_pangkomunikasyon"
        };
        int[] categoryVideoProgress = new int[]{3, 4, 8};
        String categoryVideotype = "Parirala";

        for (int i = 0; i<categoryVideoName.length; i++){
            ContentValues values = new ContentValues();
            values.put("categoryName", categoryVideoName[i]);
            values.put("categoryColor", bgVideoColors[i]);
            values.put("categoryTotalItems", itemVideoCount[i]);
            values.put("categoryType", categoryVideotype);
            values.put("imageURL", imageVideoURL[i]);
            values.put("categoryProgress", categoryVideoProgress[i]);
            DB.insert("CategoryTable", null, values);
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists CategoryTable");
    }


    public Cursor getAllCategory(String categoryType, String modifier){
        SQLiteDatabase DB = this.getWritableDatabase();
        if (modifier.equals("Learn")){
            return DB.rawQuery("Select categoryName, categoryColor, categoryTotalItems, categoryType from CategoryTable WHERE categoryType=? ORDER BY categoryName ASC", new String[]{categoryType});
        }else if (modifier.equals("By5")){
            return DB.rawQuery("Select * from CategoryTable WHERE categoryType=? ORDER BY categoryName ASC LIMIT 5", new String[]{categoryType});
        }
        else if (modifier.equals("Profile")){
            return DB.rawQuery("Select * from CategoryTable WHERE categoryProgress != categoryTotalItems AND categoryProgress != 0 ORDER BY categoryProgress DESC LIMIT 5", null);
        }
        else {
            return DB.rawQuery("Select * from CategoryTable ORDER BY categoryName ASC", null);
        }

    }
}
