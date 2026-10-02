package com.example.kespy;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "SpyGameDB";
    private static final int DATABASE_VERSION = 3;

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
                {"আপেল", "Apple"}, {"কলা", "Banana"}, {"আম", "Mango"}, {"কমলা", "Orange"},
                {"আঙ্গুর", "Grapes"}, {"তরমুজ", "Watermelon"}, {"আনারস", "Pineapple"},
                {"পেঁপে", "Papaya"}, {"পেয়ারা", "Guava"}, {"কাঁঠাল", "Jackfruit"},
                {"লিচু", "Litchi"}, {"ডালিম", "Pomegranate"}, {"নারকেল", "Coconut"},
                {"লেবু", "Lemon"}, {"কালোজাম", "Blackberry"}, {"বরই", "Plum"},
                {"ড্রাগন ফল", "Dragon Fruit"}, {"খেজুর", "Date"}, {"তেঁতুল", "Tamarind"},
                {"জামরুল", "Star Apple"}, {"কদবেল", "Wood Apple"}, {"আতা", "Custard Apple"},
                {"বাঙ্গি", "Melon"}, {"জলপাই", "Olive"}, {"সফেদা", "Sapodilla"},
                {"জাম্বুরা", "Grapefruit"}, {"আমলকী", "Gooseberry"}, {"কামরাঙ্গা", "Starfruit"},
                {"আখ", "Sugar Cane"}, {"বাতাবি লেবু", "Pomelo"}, {"তাল", "Palmyra Palm"},
                {"বেল", "Bael"}, {"আমড়া", "Hog Plum"}, {"চালতা", "Elephant Apple"},
                {"জাম", "Java Plum"}, {"মাল্টা", "Sweet Orange"}, {"কিশমিশ", "Raisin"},
                {"চিনাবাদাম", "Peanut"}, {"টমেটো", "Tomato"}, {"শসা", "Cucumber"},
                {"মিষ্টি কুমড়া", "Pumpkin"}
        };
        for (String[] fruit : fruits) {
            insertWord(db, fruit[0], fruit[1], 1);
        }

        String[][] animals = {
                {"সিংহ", "Lion"}, {"বাঘ", "Tiger"}, {"হাতি", "Elephant"}, {"ঘোড়া", "Horse"},
                {"কুকুর", "Dog"}, {"বিড়াল", "Cat"}, {"গরু", "Cow"}, {"বানর", "Monkey"},
                {"হরিণ", "Deer"}, {"খরগোশ", "Rabbit"}, {"উট", "Camel"}, {"শিয়াল", "Fox"},
                {"জেব্রা", "Zebra"}, {"জিরাফ", "Giraffe"}, {"গণ্ডার", "Rhinoceros"},
                {"জলহস্তী", "Hippopotamus"}, {"কুমির", "Crocodile"}, {"সাপ", "Snake"},
                {"ব্যাঙ", "Frog"}, {"কচ্ছপ", "Turtle"}, {"ছাগল", "Goat"}, {"ভেড়া", "Sheep"},
                {"গাধা", "Donkey"}, {"শূকর", "Pig"}, {"ইঁদুর", "Mouse"}, {"মাছ", "Fish"},
                {"মহিষ", "Buffalo"}, {"তিমি", "Whale"}, {"অক্টোপাস", "Octopus"}, {"কাক", "Crow"},
                {"কবুতর", "Pigeon"}, {"হাঁস", "Duck"}, {"মুরগি", "Hen"}, {"বক", "Heron"},
                {"শকুন", "Vulture"}, {"টিকটিকি", "Lizard"}, {"প্রজাপতি", "Butterfly"},
                {"মৌমাছি", "Bee"}, {"পিঁপড়া", "Ant"}, {"মশা", "Mosquito"}, {"মাছি", "Fly"},
                {"মাকড়সা", "Spider"}, {"তেলাপোকা", "Cockroach"}, {"জোনাকি", "Firefly"},
                {"কেঁচো", "Earthworm"}, {"চিংড়ি", "Prawn"}, {"কাঁকড়া", "Crab"}
        };
        for (String[] animal : animals) {
            insertWord(db, animal[0], animal[1], 2);
        }

        String[][] foods = {
                {"বিরিয়ানি", "Biryani"}, {"ফুচকা", "Fuchka"}, {"সিঙ্গারা", "Singara"},
                {"ফ্রাইড রাইস", "Fried Rice"}, {"আইসক্রিম", "Ice Cream"}, {"চকোলেট", "Chocolate"},
                {"সন্দেশ", "Sandesh"}, {"পাস্তা", "Pasta"}, {"তেহারি", "Tehari"},
                {"খিচুড়ি", "Khichuri"}, {"পোলাও", "Polao"}, {"হালিম", "Haleem"},
                {"চটপটি", "Chotpoti"}, {"জিলাপি", "Jilapi"}, {"দই", "Doi"}, {"নান", "Naan"},
                {"নুডলস", "Noodles"}, {"সুপ", "Soup"}, {"সালাদ", "Salad"}, {"কেক", "Cake"},
                {"বিস্কুট", "Biscuit"}, {"পাউরুটি", "Bread"}, {"মধু", "Honey"}, {"রোস্ট", "Roast"},
                {"ভুনা খিচুড়ি", "Bhuna Khichuri"}, {"কাচ্চি বিরিয়ানি", "Kacchi Biryani"},
                {"মোরগ পোলাও", "Morog Polao"}, {"শুটকি", "Shutki"}, {"ভাত", "Rice"},
                {"পান্তা ভাত", "Panta Bhat"}, {"পিঠা", "Pitha"}, {"ভাপা পিঠা", "Bhapa Pitha"},
                {"পায়েস", "Payesh"}, {"ফিরনি", "Firni"}, {"সেমাই", "Semai"}, {"হালুয়া", "Halua"},
                {"গাজরের হালুয়া", "Gajor Halua"}, {"মিষ্টি দই", "Mishti Doi"}, {"বোরহানি", "Borhani"},
                {"লাচ্ছি", "Lassi"}, {"রসমালাই", "Rasmalai"}, {"ঝালমুড়ি", "Jhalmuri"},
                {"চানাচুর", "Chanachur"}, {"পুরি", "Puri"}, {"রুটি", "Roti"}, {"কফি", "Coffee"},
                {"চা", "Tea"}, {"দুধ চা", "Milk Tea"}, {"লেবুর শরবত", "Lemonade"},
                {"শরবত", "Sharbat"}, {"পাপড়", "Papad"}, {"ডিম ভুনা", "Dim Bhuna"},
                {"নিহারি", "Nehari"}, {"পায়া", "Paya"}, {"নারকেল নাড়ু", "Naru"},
                {"জর্দা", "Zarda"}, {"আমসত্ত্ব", "Amshotto"}, {"লাল চা", "Red Tea"}, {"চপ", "Chop"}
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