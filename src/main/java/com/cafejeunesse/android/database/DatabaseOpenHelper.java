package com.cafejeunesse.android.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by David Levayer on 21/03/15.
 */
public class DatabaseOpenHelper extends SQLiteOpenHelper {

    public static final String TABLE_SERVICES = "cafejeunesseservices";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "Nom";
    public static final String COLUMN_PHONE = "Téléphone";
    public static final String COLUMN_INFO = "Description";
    public static final String COLUMN_SERVICES = "Services";
    public static final String COLUMN_PRICE = "Tarif";
    public static final String COLUMN_WEBSITE = "Site web";
    public static final String COLUMN_FACEBOOK = "page Facebook";
    public static final String COLUMN_ADDRESS = "Adresse";
    public static final String COLUMN_CATEGORIES = "Catégorie(s)";
    public static final String COLUMN_KEYWORDS = "Mots clés";
    public static final String COLUMN_LAT = "Latitude";
    public static final String COLUMN_LON = "Longitude";

    private static final String DATABASE_NAME = "services.db";
    private static final int DATABASE_VERSION = 1;

    private Context mContext;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_SERVICES + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_NAME + " text not null, "
            + COLUMN_PHONE + " text, "
            + COLUMN_INFO + " text, "
            + COLUMN_SERVICES + " text, "
            + COLUMN_PRICE + " text, "
            + COLUMN_WEBSITE + " text, "
            + COLUMN_FACEBOOK + " text, "
            + COLUMN_ADDRESS + " text, "
            + COLUMN_CATEGORIES + " text, "
            + COLUMN_KEYWORDS + " text, "
            + COLUMN_LAT + " float, "
            + COLUMN_LON + " float"
            +");";

    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Toast.makeText(mContext,"Update from "+oldVersion+" to "+newVersion,Toast.LENGTH_LONG).show();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SERVICES);
        onCreate(db);
    }
}

