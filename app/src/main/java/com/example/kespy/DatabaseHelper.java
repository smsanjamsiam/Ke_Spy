package com.example.kespy;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "SpyGameDB";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_CATEGORIES = "categories";
    private static final String TABLE_WORDS = "words";

    private static final String COL_CAT_ID = "id";
    private static final String COL_CAT_NAME = "category_name";

    private static final String COL_WORD_ID = "id";
    private static final String COL_WORD_BN = "word_bn";
    private static final String COL_WORD_EN = "word_en";
    private static final String COL_WORD_CAT_ID = "category_id";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createCategoriesTable = "CREATE TABLE " + TABLE_CATEGORIES + " (" +
                COL_CAT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_CAT_NAME + " TEXT)";
        db.execSQL(createCategoriesTable);

        String createWordsTable = "CREATE TABLE " + TABLE_WORDS + " (" +
                COL_WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_WORD_BN + " TEXT, " +
                COL_WORD_EN + " TEXT, " +
                COL_WORD_CAT_ID + " INTEGER)";
        db.execSQL(createWordsTable);

        insertInitialData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORDS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
        onCreate(db);
    }

    private void insertInitialData(SQLiteDatabase db) {
        insertCategory(db, 1, "Fruits");
        insertCategory(db, 2, "Animals");
        insertCategory(db, 3, "Food");

        String[][] fruits = {
                {"আম", "Mango"}, {"কাঁঠাল", "Jackfruit"}, {"লিচু", "Lychee"}, {"পেয়ারা", "Guava"}, {"তরমুজ", "Watermelon"}, {"জামরুল", "Water Apple"}, {"বরই", "Jujube"}, {"কলা", "Banana"}, {"আনারস", "Pineapple"}, {"পেঁপে", "Papaya"}, {"কমলা", "Orange"}, {"আপেল", "Apple"}, {"আঙুর", "Grapes"}, {"ডালিম", "Pomegranate"}, {"নারকেল", "Coconut"}, {"লেবু", "Lemon"}, {"মাল্টা", "Malta Orange"}, {"বাতাবি লেবু", "Pomelo"}, {"আমড়া", "Hog Plum"}, {"জাম", "Java Plum"}, {"বেল", "Wood Apple"}, {"কদবেল", "Elephant Apple"}, {"সফেদা", "Sapodilla"}, {"তাল", "Palmyra Fruit"}, {"খেজুর", "Date"}, {"জলপাই", "Olive"}, {"কামরাঙা", "Star Fruit"}, {"আতা", "Custard Apple"}, {"শরিফা", "Sugar Apple"}, {"ড্রাগন ফল", "Dragon Fruit"}, {"স্ট্রবেরি", "Strawberry"}, {"চেরি", "Cherry"}, {"নাশপাতি", "Pear"}, {"বাঙ্গি", "Muskmelon"}, {"ডাব", "Green Coconut"}, {"কুল", "Indian Jujube"}, {"বিলিম্বি", "Bilimbi"}, {"লটকন", "Burmese Grape"}, {"করমচা", "Bengal Currant"}, {"ফলসা", "Phalsa"}, {"তেঁতুল", "Tamarind"}, {"কেওড়া ফল", "Keora Fruit"}, {"আমলকি", "Indian Gooseberry"}, {"ডেউয়া", "Monkey Jack"}, {"কাউ ফল", "Cowa Fruit"}, {"গাব", "Indian Persimmon"}, {"বেতফল", "Rattan Fruit"}, {"প্যাশন ফল", "Passion Fruit"}, {"কিউই", "Kiwi"}, {"অ্যাভোকাডো", "Avocado"}
        };
        for (String[] fruit : fruits) {
            insertWord(db, fruit[0], fruit[1], 1);
        }

        String[][] animals = {
                {"বাঘ", "Tiger"}, {"সিংহ", "Lion"}, {"হাতি", "Elephant"}, {"গরু", "Cow"}, {"ছাগল", "Goat"}, {"ভেড়া", "Sheep"}, {"মহিষ", "Buffalo"}, {"ঘোড়া", "Horse"}, {"গাধা", "Donkey"}, {"কুকুর", "Dog"}, {"বিড়াল", "Cat"}, {"বানর", "Monkey"}, {"শিয়াল", "Fox"}, {"হরিণ", "Deer"}, {"খরগোশ", "Rabbit"}, {"ভালুক", "Bear"}, {"নেকড়ে", "Wolf"}, {"জেব্রা", "Zebra"}, {"জিরাফ", "Giraffe"}, {"গণ্ডার", "Rhinoceros"}, {"জলহস্তী", "Hippopotamus"}, {"উট", "Camel"}, {"শূকর", "Pig"}, {"কাঠবিড়ালি", "Squirrel"}, {"ইঁদুর", "Rat"}, {"বেজি", "Mongoose"}, {"সজারু", "Porcupine"}, {"বনবিড়াল", "Wild Cat"}, {"চিতা", "Leopard"}, {"চিতাবাঘ", "Cheetah"}, {"কুমির", "Crocodile"}, {"সাপ", "Snake"}, {"টিকটিকি", "Lizard"}, {"ব্যাঙ", "Frog"}, {"কচ্ছপ", "Turtle"}, {"গুইসাপ", "Monitor Lizard"}, {"ডলফিন", "Dolphin"}, {"তিমি", "Whale"}, {"হাঙর", "Shark"}, {"অক্টোপাস", "Octopus"}, {"কাঁকড়া", "Crab"}, {"চিংড়ি", "Shrimp"}, {"বাদুড়", "Bat"}, {"কেঁচো", "Earthworm"}, {"প্রজাপতি", "Butterfly"}, {"মৌমাছি", "Bee"}, {"মশা", "Mosquito"}, {"মাছি", "Fly"}, {"তেলাপোকা", "Cockroach"}, {"পিঁপড়া", "Ant"}
        };
        for (String[] animal : animals) {
            insertWord(db, animal[0], animal[1], 2);
        }

        String[][] foods = {
                {"কাচ্চি বিরিয়ানি", "Kacchi Biryani"}, {"তেহারি", "Tehari"}, {"পোলাও", "Pulao"}, {"খিচুড়ি", "Khichuri"}, {"সাদা ভাত", "Steamed Rice"}, {"পান্তা ভাত", "Panta Bhat"}, {"ভুনা খিচুড়ি", "Bhuna Khichuri"}, {"মোরগ পোলাও", "Chicken Pulao"}, {"ফুচকা", "Fuchka"}, {"চটপটি", "Chotpoti"}, {"সিঙ্গারা", "Singara"}, {"সমুচা", "Samosa"}, {"পুরি", "Puri"}, {"পরোটা", "Paratha"}, {"রুটি", "Roti"}, {"নান", "Naan"}, {"হালিম", "Haleem"}, {"কাবাব", "Kebab"}, {"শিক কাবাব", "Seekh Kebab"}, {"চিকেন রোস্ট", "Chicken Roast"}, {"গরুর মাংস", "Beef Curry"}, {"খাসির মাংস", "Mutton Curry"}, {"মুরগির ঝোল", "Chicken Curry"}, {"ইলিশ ভাজা", "Fried Hilsa"}, {"সরিষা ইলিশ", "Hilsa in Mustard"}, {"মাছের ঝোল", "Fish Curry"}, {"ডাল", "Lentil Soup"}, {"আলু ভর্তা", "Mashed Potato"}, {"বেগুন ভর্তা", "Mashed Eggplant"}, {"শুঁটকি ভর্তা", "Mashed Dried Fish"}, {"ডিম ভাজি", "Fried Egg"}, {"ডিমের ঝোল", "Egg Curry"}, {"আলু ভাজি", "Fried Potato"}, {"বেগুনি", "Beguni"}, {"পেঁয়াজু", "Peyaju"}, {"মুড়ি", "Puffed Rice"}, {"ঝালমুড়ি", "Jhalmuri"}, {"চানাচুর", "Chanachur"}, {"চা", "Tea"}, {"দুধ চা", "Milk Tea"}, {"লাচ্ছি", "Lassi"}, {"দই", "Yogurt"}, {"রসগোল্লা", "Roshogolla"}, {"মিষ্টি দই", "Sweet Yogurt"}, {"চমচম", "Chomchom"}, {"সন্দেশ", "Sandesh"}, {"জিলাপি", "Jalebi"}, {"পায়েস", "Payesh"}, {"সেমাই", "Semai"}, {"পিঠা", "Pitha"}
        };
        for (String[] food : foods) {
            insertWord(db, food[0], food[1], 3);
        }
    }

    private void insertCategory(SQLiteDatabase db, int id, String name) {
        ContentValues values = new ContentValues();
        values.put(COL_CAT_ID, id);
        values.put(COL_CAT_NAME, name);
        db.insert(TABLE_CATEGORIES, null, values);
    }

    private void insertWord(SQLiteDatabase db, String wordBn, String wordEn, int categoryId) {
        ContentValues values = new ContentValues();
        values.put(COL_WORD_BN, wordBn);
        values.put(COL_WORD_EN, wordEn);
        values.put(COL_WORD_CAT_ID, categoryId);
        db.insert(TABLE_WORDS, null, values);
    }
}