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

        //DATABASE TABLE FOR USER TABLE
        DB.execSQL("create Table IF NOT EXISTS UserTable(userID INTEGER primary key ," +
                " userName TEXT , isOldUser INTEGER , dateToday TEXT , currentStreak INTEGER," +
                "longestStreak INTEGER, wordDiscovered INTEGER , phraseDiscovered INTEGER , " +
                "favoriteCount INTEGER , itemNameWOTD TEXT )");

        ContentValues userValue = new ContentValues();
        userValue.put("userID", 201810336);
        userValue.put("userName", "Kabayan");
        userValue.put("isOldUser", 0);
        userValue.put("dateToday", "Mon Apr 02 2022");
        userValue.put("currentStreak", 0);
        userValue.put("longestStreak", 0);
        userValue.put("wordDiscovered", 0);
        userValue.put("phraseDiscovered", 0);
        userValue.put("favoriteCount", 0);
        userValue.put("itemNameWOTD", "");
        DB.insert("UserTable", null, userValue);

        //DATABASE TABLE FOR CATEGORY TABLE
        DB.execSQL("create Table IF NOT EXISTS CategoryTable(categoryName TEXT primary key," +
                " categoryColor TEXT, categoryTotalItems TEXT," +
                " categoryType TEXT, imageURL TEXT," +
                " categoryProgress INTEGER)");
        String[] categoryName = new String[]{
                "Araw ng Linggo", "Buwan", "Lugar", "Emosyon", "Alpabeto",
                "Numero", "Hayop", "Hugis", "Kulay", "Prutas",
                "Gulay", "Parte ng Katawan", "Sasakyan", "Kasarian", "Miyembro ng Pamilya",
                "Pagbati", "Pang-Emergency", "Pangkomunikasyon"};

        String[] bgColors = new String[]{
                        "outrageous_orange", "auburn", "bright_navy_blue", "jungle_green", "plump_purple",
                        "tulip", "japanese_indigo", "silver_lake_blue", "pink", "blue_cola",
                        "golden_puppy", "cornflower_blue", "flame", "apple", "may_green",
                        "veronese_green", "violet_blue", "grape"};
        int[] itemCount = new int[]{7, 12, 7, 7, 26, 10, 7, 5, 7,
                                     6, 11, 8, 6, 4, 6,
                                     6, 7, 10};
        String[] imageURL = new String[]{
                "ic_araw_ng_linggo", "ic_buwan", "ic_lugar", "ic_emosyon", "ic_alpabeto",
                "ic_numero", "ic_hayop", "ic_hugis", "ic_kulay", "ic_prutas",
                "ic_gulay", "ic_parte_ng_katawan", "ic_sasakyan", "ic_kasarian", "ic_miyembro_ng_pamilya",
                "ic_pagbati", "ic_pang_emergency", "ic_pangkomunikasyon"};
        int[] progress = new int[]{3, 5, 7, 7, 19, 7, 3, 1, 0, 4, 5, 7, 3, 4, 1,
                                    3, 4, 8};
        for (int i = 0; i<categoryName.length; i++){
            ContentValues values = new ContentValues();
            values.put("categoryName", categoryName[i]);
            values.put("categoryColor", bgColors[i]);
            values.put("categoryTotalItems", itemCount[i]);
            if (i<15){
                values.put("categoryType", "Salita");
            }else{
                values.put("categoryType", "Parirala");
            }
            values.put("imageURL", imageURL[i]);
            values.put("categoryProgress", progress[i]);
            DB.insert("CategoryTable", null, values);




        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists CategoryTable");
    }

    public Cursor getUserData(String value, String modifier){
        SQLiteDatabase DB = this.getWritableDatabase();

        switch (modifier) {
            case "GetNameStreak":
                return DB.rawQuery("Select userName, longestStreak from UserTable WHERE userID=201810336", null);
            case "GetUserName":
                return DB.rawQuery("Select userName from UserTable where userID=201810336", null);
            case "StreakCard":
                return DB.rawQuery("Select currentStreak, longestStreak, wordDiscovered, phraseDiscovered, favoriteCount from UserTable WHERE userID=201810336", null);
            case "Splash":
                return DB.rawQuery("Select dateToday, isOldUser from UserTable where userID=201810336", null);
            default:
                return DB.rawQuery("Select * from UserTable", null);
        }
    }

    public boolean updateSingleData (String value, int intValue, String modifier){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        switch (modifier) {
            case "Splash":
                values.put("dateToday", value);
                try (Cursor cursor = DB.rawQuery("Select dateToday from UserTable where userID=201810336", null)) {
                    if (cursor.getCount() > 0) {
                        long result = DB.update("UserTable", values, "userID=201810336", null);
                        return result != -1;
                    } else {
                        return false;
                    }
                }
            case "ChangeUserName":
                values.put("userName", value);
                try (Cursor cursor = DB.rawQuery("Select userName from UserTable where UserID=201810336", null)) {
                    if (cursor.getCount() > 0) {
                        long result = DB.update("UserTable", values, "userID=201810336", null);
                        return result != -1;
                    } else {
                        return false;
                    }
                }
            case "Streak":
                values.put("currentStreak", intValue);
                try (Cursor cursor = DB.rawQuery("Select currentStreak from UserTable where UserID=201810336", null)) {
                    if (cursor.getCount() > 0) {
                        long result = DB.update("UserTable", values, "userID=201810336", null);
                        return result != -1;
                    } else {
                        return false;
                    }
                }
            default:
                return false;
        }
    }

    public boolean UpdateMultipleData(int[] intValue, String[] stringValue, String modifier){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        if (modifier.equals("Streak")){
            values.put("currentStreak", intValue[0]);
            values.put("longestStreak", intValue[1]);
            try (Cursor cursor = DB.rawQuery("Select currentStreak, longestStreak from UserTable where UserID=201810336", null)){
                if (cursor.getCount()>0){
                    long result = DB.update("UserTable", values, "userID=201810336", null);
                    return result != -1;
                }else{
                    return false;
                }
            }
        }else{
            return false;
        }
    }

    public Cursor getCategory(String categoryType, String modifier){
        SQLiteDatabase DB = this.getWritableDatabase();
        if (modifier.equals("Learn")){
            return DB.rawQuery("Select categoryName, categoryColor, categoryTotalItems, categoryType from CategoryTable WHERE categoryType=? ORDER BY categoryName ASC", new String[]{categoryType});
        }else if (categoryType.equals("Salita") && modifier.equals("By5")){
            return DB.rawQuery("Select * from CategoryTable WHERE categoryType='Salita' AND categoryProgress != categoryTotalItems ORDER BY categoryProgress DESC LIMIT 5", null);
        }else if (categoryType.equals("Parirala") && modifier.equals("By5")){
            return DB.rawQuery("Select categoryName, categoryColor, imageURL from CategoryTable WHERE categoryType='Parirala' AND categoryProgress != categoryTotalItems ORDER BY categoryName ASC LIMIT 3", null);
        }else if (modifier.equals("Profile")){
            return DB.rawQuery("Select * from CategoryTable WHERE categoryProgress != categoryTotalItems ORDER BY categoryProgress DESC LIMIT 5", null);
        }else if (modifier.equals("Search")){
            return DB.rawQuery("Select categoryName, categoryType from CategoryTable ORDER By categoryName ASC", null);
        }else if (modifier.equals("SingleCategory")){
            return DB.rawQuery("Select categoryColor, categoryTotalItems, imageURL, categoryProgress from CategoryTable WHERE categoryName=?", new String[]{categoryType});
        }else {
            return DB.rawQuery("Select * from CategoryTable ORDER BY categoryName ASC", null);
        }

    }
}
