package com.artemis.ispeaksigns;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
                "longestStreak INTEGER, itemNameWOTD INTEGER , selectedLanguage TEXT, avatarName TEXT)");

        ContentValues userValue = new ContentValues();
        userValue.put("userID", 201810336);
        userValue.put("userName", "Juan");
        userValue.put("isOldUser", 0);
        userValue.put("dateToday", "Mon Apr 02 2022");
        userValue.put("currentStreak", 0);
        userValue.put("longestStreak", 0);
        userValue.put("itemNameWOTD", 0);
        userValue.put("selectedLanguage", "tl");
        userValue.put("avatarName", "avatar1");
        DB.insert("UserTable", null, userValue);

        //DATABASE TABLE FOR CATEGORY TABLE
        DB.execSQL("create Table IF NOT EXISTS CategoryTable(categoryName TEXT primary key," +
                " categoryColor TEXT, categoryTotalItems INTEGER," +
                " categoryType TEXT, imageURL TEXT," +
                " categoryProgress INTEGER)");

        String[] categoryName = new String[]{
                "Araw ng Linggo", "Buwan", "Lugar", "Emosyon", "Alpabeto",
                "Numero", "Hayop", "Hugis", "Kulay", "Prutas",
                "Gulay", "Parte ng Katawan", "Transportasyon", "Kasarian", "Miyembro ng Pamilya",
                "Pagbati", "Pang-Emergency", "Pangkomunikasyon", "Ekspresyon ng Oras", "Ekspresyon ng Pagmamahal"};

        String[] bgColors = new String[]{
                        "outrageous_orange", "auburn", "bright_navy_blue", "jungle_green", "plump_purple",
                        "tulip", "japanese_indigo", "silver_lake_blue", "pink", "blue_cola",
                        "golden_puppy", "cornflower_blue", "flame", "apple", "may_green",
                        "veronese_green", "violet_blue", "grape", "steel_teal", "blue_surf"};
        int[] itemCount = new int[]{7, 12, 7, 7, 26, 11, 7, 5, 7, 6, 11, 8, 6, 4, 7, 5, 7, 10, 8, 6};

        String[] imageURL = new String[]{
                "ic_araw_ng_linggo", "ic_buwan", "ic_lugar", "ic_emosyon", "ic_alpabeto",
                "ic_numero", "ic_hayop", "ic_hugis", "ic_kulay", "ic_prutas",
                "ic_gulay", "ic_parte_ng_katawan", "ic_sasakyan", "ic_kasarian", "ic_miyembro_ng_pamilya",
                "ic_pagbati", "ic_pang_emergency", "ic_pangkomunikasyon", "ic_ekspresyon_ng_oras", "ic_ekspresyon_ng_pagmamahal"};

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
            values.put("categoryProgress", 0);
            DB.insert("CategoryTable", null, values);
        }

        //DATABASE FOR ITEM TABLE
        DB.execSQL("create Table IF NOT EXISTS ItemTable(itemName TEXT primary key," +
                " itemCategory TEXT, itemType TEXT, isLearned INTEGER, partsOfSpeech TEXT, imagesNo INTEGER, youtubeId TEXT)");

        String[] itemName = new String[]{
                "Lunes", "Martes", "Miyerkules", "Huwebes", "Biyernes", "Sabado", "Linggo",
                "Enero", "Pebrero", "Marso", "Abril", "Mayo", "Hunyo", "Hulyo", "Agosto", "Setyembre", "Oktubre", "Nobyembre", "Disyembre",
                "Bahay", "Botika", "Ospital",  "Paaralan", "Palengke", "Parke", "Simbahan",
                "Galit", "Gulat", "Hiya", "Lungkot", "Saya", "Takot", "Tuwa",
                "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
                "Sero", "Isa", "Dalawa", "Tatlo", "Apat", "Lima", "Anim", "Pito", "Walo", "Siyam", "Sampu",
                "Aso", "Ahas", "Ibon", "Isda", "Palaka", "Pusa", "Unggoy",
                "Bilog", "Bituin", "Parihaba", "Parisukat", "Tatsulok",
                "Berde", "Bughaw", "Dilaw", "Itim", "Lila", "Pula", "Puti",
                "Buko", "Mansanas", "Mangga", "Pakwan", "Pinya", "Saging",
                "Bawang", "Kalabasa", "Kamatis", "Patatas", "Petsay", "Pipino", "Repolyo", "Sibuyas", "Sili", "Sitaw", "Talong",
                "Bibig", "Ilong", "Kamay", "Leeg", "Mata", "Paa", "Tainga", "Ulo",
                "Bangka", "Bisikleta", "Bus", "Dyip", "Eroplano", "Motorsiklo",
                "Babae", "Lalaki", "Bakla", "Tomboy",
                "Nanay", "Tatay", "Ate", "Kuya", "Lolo", "Lola", "Bunso",
                "Kamusta ka?", "Magandang umaga sa iyo", "Magandang hapon sa iyo", "Magandang gabi  sa iyo", "Paalam na sa’yo",
                "Tulungan nyo ako!", "Nanakawan ako", "Ako ay nawawala", "Paki bilisan po", "Anong lugar ito?", "Maaari bang humiram ng telepono?", "Paki tigil po",
                "Ano ang pangalan mo?", "Patawarin mo ako", "Salamat sa iyo", "Saan ka nakatira", "Ingat ka sa iyong patutunguhan", "Magandang araw sa’yo", "Kain tayo", "Nauunawaan mo ba ako?", "Nasaan ang banyo?", "Nasaan ang kusina?",
                "Anong oras na?", "Pwede mo ba sabihin ang oras?", "Alam mo ba kung anong oras na?", "Ala sais na ng umaga", "Alas dose na ng tanghali", "Alas otso na ng gabi", "Magkita tayo mamaya", "Magkita tayo bukas",
                "Nandito lang ako para sa’yo", "Ikaw ay isang kaakit-akit na babae", "Siya ay may may magandang mata", "May paghanga ako sayo", "Gusto kitang yakapin", "Gusto kita"
        };

        String[] itemCategory = new String[itemName.length];
        String[] itemType = new String[itemName.length];
        int[] imagesNo = new int[itemName.length];

        for (int i = 0; i < itemName.length; i++){
            if (i < 7){
                itemCategory[i] = "Araw ng Linggo";
                itemType[i] = "Salita";

                if (i == 3){
                    imagesNo[i] = 2;
                }else {
                    imagesNo[i] = 1;
                }
            }else if (i < 19){
                itemCategory[i] = "Buwan";
                itemType[i] = "Salita";
            }else if (i < 26){
                itemCategory[i] = "Lugar";
                itemType[i] = "Salita";
            }else if (i < 33){
                itemCategory[i] = "Emosyon";
                itemType[i] = "Salita";
            }else if (i < 59){
                itemCategory[i] = "Alpabeto";
                itemType[i] = "Salita";

                imagesNo[i] = 1;
            }else if (i < 70){
                itemCategory[i] = "Numero";
                itemType[i] = "Salita";

                imagesNo[i] = 1;
            }else if (i < 77){
                itemCategory[i] = "Hayop";
                itemType[i] = "Salita";
            }else if (i < 82){
                itemCategory[i] = "Hugis";
                itemType[i] = "Salita";
            }else if (i < 89){
                itemCategory[i] = "Kulay";
                itemType[i] = "Salita";
            }else if (i < 95){
                itemCategory[i] = "Prutas";
                itemType[i] = "Salita";
            }else if (i < 106){
                itemCategory[i] = "Gulay";
                itemType[i] = "Salita";
            }else if (i < 114){
                itemCategory[i] = "Parte ng Katawan";
                itemType[i] = "Salita";
            }else if (i < 120){
                itemCategory[i] = "Transportasyon";
                itemType[i] = "Salita";
            }else if (i < 124){
                itemCategory[i] = "Kasarian";
                itemType[i] = "Salita";
            }else if (i < 131){
                itemCategory[i] = "Miyembro ng Pamilya";
                itemType[i] = "Salita";
            }else if (i < 136){
                itemCategory[i] = "Pagbati";
                itemType[i] = "Parirala";
            }else if (i < 143){
                itemCategory[i] = "Pang-Emergency";
                itemType[i] = "Parirala";
            }else if (i < 153){
                itemCategory[i] = "Pangkomunikasyon";
                itemType[i] = "Parirala";
            }else if (i < 161){
                itemCategory[i] = "Ekspresyon ng Oras";
                itemType[i] = "Parirala";
            }else {
                itemCategory[i] = "Ekspresyon ng Pagmamahal";
                itemType[i] = "Parirala";
            }
        }

        String[] partsOfSpeech = new String[]{
                "pangngalan", "pangngalan", "pangngalan", "pangngalan", "pangngalan", "pangngalan", "pangngalan",
                "pangngalan", "pangngalan", "pangngalan", "pangngalan", "pangngalan", "pangngalan", "pangngalan", "pangngalan", "pangngalan", "pangngalan", "pangngalan", "pangngalan",
                "pangngalan", "pangngalan", "pangngalan", "pangngalan", "pangngalan", "pangngalan", "pangngalan",
                "pang-uri", "pangngalan", "pandiwa", "pangngalan", "pangngalan", "pangngalan", "pangngalan",
                "Letrang A", "Letrang B", "Letrang C", "Letrang D", "Letrang E", "Letrang F", "Letrang G", "Letrang H", "Letrang I", "Letrang J", "Letrang K", "Letrang L", "Letrang M", "Letrang N", "Letrang O", "Letrang P", "Letrang Q", "Letrang R", "Letrang S", "Letrang T", "Letrang U", "Letrang V", "Letrang W", "Letrang X", "Letrang Y", "Letrang Z",
                "0","1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
                "pangngalan", "pangngalan", "pangngalan", "pangngalan", "pangngalan", "pangngalan", "pangngalan",
                "pangngalan", "pangngalan", "pangngalan", "pang-uri", "pangngalan",
                "pang-uri","pang-uri","pang-uri","pang-uri","pangngalan","pang-uri","pang-uri",
                "pangngalan","pangngalan","pangngalan","pangngalan","pangngalan","pangngalan",
                "pangngalan","pangngalan","pangngalan","pangngalan","pangngalan","pangngalan","pangngalan","pangngalan","pangngalan","pangngalan","pangngalan",
                "pangngalan","pangngalan","pangngalan","pangngalan","pangngalan","pangngalan","pangngalan","pangngalan",
                "pangngalan", "pangngalan", "pangngalan", "pangngalan"," pangngalan", "pangngalan",
                "pang-uri", "pang-uri", "pang-uri", "pang-uri", "pang-uri",
                "pangngalan", "pangngalan", "pangngalan", "pangngalan"," pangngalan", "pangngalan", "pangngalan",
        };

        for (int i = 0; i < itemName.length; i++){
            ContentValues values = new ContentValues();
            values.put("itemName", itemName[i]);
            values.put("itemCategory", itemCategory[i]);
            values.put("itemType", itemType[i]);
            values.put("isLearned", 0);
            if (i < 131){
                values.put("partsOfSpeech", partsOfSpeech[i]);
            }else{
                values.put("partsOfSpeech", "");
            }
            values.put("imagesNo", imagesNo[i]);
            DB.insert("ItemTable", null, values);
        }

        //DATABASE FOR FAVORITE TABLE
        DB.execSQL("create Table IF NOT EXISTS FavoriteTable(itemName TEXT primary key," +
                " itemType TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists CategoryTable");
    }

    public Cursor getUserData(String modifier){
        SQLiteDatabase DB = this.getWritableDatabase();

        switch (modifier) {
            case "GetNameStreak":
                return DB.rawQuery("Select userName, longestStreak from UserTable WHERE userID=201810336", null);
            case "GetLocale":
                return DB.rawQuery("Select selectedLanguage from UserTable WHERE userID=201810336", null);
            case "GetUserName":
                return DB.rawQuery("Select userName from UserTable where userID=201810336", null);
            case "StreakAvatarCard":
                return DB.rawQuery("Select currentStreak, longestStreak, avatarName from UserTable WHERE userID=201810336", null);
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
            case "currentDate":
                values.put("dateToday", value);
                try (Cursor cursor = DB.rawQuery("Select dateToday from UserTable where userID=201810336", null)) {
                    if (cursor.getCount() > 0) {
                        long result = DB.update("UserTable", values, "userID=201810336", null);
                        return result != -1;
                    } else {
                        return false;
                    }
                }
            case "UserName":
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
            case "Language":
                values.put("selectedLanguage", value);
                try (Cursor cursor = DB.rawQuery("Select selectedLanguage from UserTable where UserID=201810336", null)) {
                    if (cursor.getCount() > 0) {
                        long result = DB.update("UserTable", values, "userID=201810336", null);
                        return result != -1;
                    } else {
                        return false;
                    }
                }
            case "Avatar":
                values.put("avatarName", value);
                try (Cursor cursor = DB.rawQuery("Select avatarName from UserTable where UserID=201810336", null)) {
                    if (cursor.getCount() > 0) {
                        long result = DB.update("UserTable", values, "userID=201810336", null);
                        return result != -1;
                    } else {
                        return false;
                    }
                }
            case "updateCategoryProgress":
                values.put("categoryProgress", intValue);
                try (Cursor cursor = DB.rawQuery("Select categoryProgress from CategoryTable where categoryName=?", new String[]{value})) {
                    if (cursor.getCount() > 0) {
                        long result = DB.update("CategoryTable", values, "categoryName=?", new String[]{value});
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
        }else if (modifier.equals("NewUser")){
            values.put("userName", stringValue[0]);
            values.put("selectedLanguage", stringValue[1]);
            values.put("avatarName", stringValue[2]);
            values.put("isOldUser", 1);

            try(Cursor cursor = DB.rawQuery("Select userName, selectedLanguage, avatarName, isOldUser from UserTable where UserID=201810336", null)){
                if (cursor.getCount()>0){
                    long result = DB.update("UserTable", values, "userID=201810336", null);
                    return result != -1;
                }else{
                    return false;
                }
            }
        }else {
            return false;
        }
    }

    public Cursor getCategory(String categoryType, String modifier){
        SQLiteDatabase DB = this.getWritableDatabase();
        if (modifier.equals("Learn")){
            return DB.rawQuery("Select categoryName, categoryColor, categoryTotalItems, categoryType from CategoryTable WHERE categoryType=? ORDER BY categoryName ASC", new String[]{categoryType});
        }
        else if (categoryType.equals("Salita") && modifier.equals("By5")){
            return DB.rawQuery("Select * from CategoryTable WHERE categoryType='Salita' AND categoryProgress != categoryTotalItems ORDER BY categoryProgress DESC LIMIT 5", null);
        }else if (categoryType.equals("Parirala") && modifier.equals("By5")){
            return DB.rawQuery("Select categoryName, categoryColor, imageURL from CategoryTable WHERE categoryType='Parirala' AND categoryProgress != categoryTotalItems ORDER BY categoryProgress DESC LIMIT 3", null);
        }else if (categoryType.equals("Salita") && modifier.equals("All")){
            return DB.rawQuery("Select * from CategoryTable WHERE categoryType='Salita' ORDER BY categoryProgress DESC LIMIT 5", null);
        }else if (categoryType.equals("Parirala") && modifier.equals("All")){
            return DB.rawQuery("Select categoryName, categoryColor, imageURL from CategoryTable WHERE categoryType='Parirala' ORDER BY categoryProgress DEsC LIMIT 3", null);
        }
        else if (modifier.equals("Profile")){
            return DB.rawQuery("Select * from CategoryTable ORDER BY categoryName ASC LIMIT 5", null);
        }else if (modifier.equals("Search")){
            return DB.rawQuery("Select categoryName, categoryType from CategoryTable ORDER By categoryName ASC", null);
        }else if (modifier.equals("SingleCategory")){
            return DB.rawQuery("Select categoryColor, categoryTotalItems, imageURL, categoryProgress from CategoryTable WHERE categoryName=?", new String[]{categoryType});
        }else {
            return DB.rawQuery("Select * from CategoryTable ORDER BY categoryName ASC", null);
        }
    }

    public Cursor getItem(String value, String modifier){
        SQLiteDatabase DB = this.getWritableDatabase();
        switch (modifier) {
            case "SearchItem":
                return DB.rawQuery("Select itemName from ItemTable WHERE itemType=? ORDER BY itemName ASC", new String[]{value});
            case "ItemList":
                return DB.rawQuery("Select itemName, isLearned from ItemTable WHERE itemCategory=?", new String[]{value});
            case "getCategoryProgress":
                return DB.rawQuery("Select categoryProgress from CategoryTable WHERE categoryName=?", new String[]{value});
            case "getItemCategory":
                return DB.rawQuery("Select itemCategory, isLearned from ItemTable WHERE itemName=?", new String[]{value});
            case "getItemHeart":
                return DB.rawQuery("Select * from FavoriteTable WHERE itemName=?", new String[]{value});
            case "Favorite":
                return DB.rawQuery("Select * from FavoriteTable", null);
            case "getWordOfTheDay":
                return DB.rawQuery("Select itemNameWOTD from UserTable WHERE UserID=201810336", null);
            case "getWOTDItem":
                return DB.rawQuery("Select * from ItemTable WHERE itemType='Salita'", null);
            default:
                return DB.rawQuery("Select * from ItemTable WHERE itemName=?", new String[]{value});
        }
    }

    public boolean UpdateItem (String stringValue, int intValue, String modifier){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        if (modifier.equals("isLearned")){
            values.put("isLearned", intValue);

            try(Cursor cursor = DB.rawQuery("Select isLearned from ItemTable where itemName=?", new String[]{stringValue})){
                if (cursor.getCount()>0){
                    long result = DB.update("ItemTable", values, "itemName=?", new String[]{stringValue});
                    return result != -1;
                }else{
                    return false;
                }
            }
        }else if (modifier.equals("NewWordOfTheDay")){
            values.put("itemNameWOTD", intValue);

            try(Cursor cursor = DB.rawQuery("Select itemNameWOTD from UserTable where UserID=201810336", null)){
                if (cursor.getCount()>0){
                    long result = DB.update("UserTable", values, "UserID=201810336", null);
                    return result != -1;
                }else{
                    return false;
                }
            }
        }else {
            return false;
        }
    }

    public boolean newFavorite(String itemName, String itemType, String modifier){
        SQLiteDatabase DB = this.getWritableDatabase();

        if (modifier.equals("Add")){
            ContentValues contentValues = new ContentValues();
            contentValues.put("itemName", itemName);
            contentValues.put("itemType", itemType);
            long result = DB.insert("FavoriteTable", null, contentValues);
            return result != -1;
        }else if (modifier.equals("Remove")){
            try (Cursor cursor = DB.rawQuery("Select * from FavoriteTable where itemName =?", new String[]{itemName})) {
                if (cursor.getCount() > 0) {
                    long result = DB.delete("FavoriteTable", "itemName=?", new String[]{itemName});
                    return result != -1;
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
    }

    public Cursor countItems(String modifier){
        SQLiteDatabase DB = this.getWritableDatabase();
        switch (modifier) {
            case "WordDiscovered":
                return DB.rawQuery("Select COUNT(*) FROM ItemTable WHERE isLearned='1' AND itemType='Salita'", null);
            case "PhraseDiscovered":
                return DB.rawQuery("Select COUNT(*) FROM ItemTable WHERE isLearned='1' AND itemType='Parirala'", null);
            case "FavoriteCount":
                return DB.rawQuery("Select COUNT(*) from FavoriteTable", null);
            case "countAllLearned":
                return DB.rawQuery("Select COUNT(*) FROM ItemTable WHERE isLearned='1'", null);
            default:
                return null;
        }
    }


}
