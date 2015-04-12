package com.cafejeunesse.android.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.cafejeunesse.android.filemanager.DownloadTask;
import com.cafejeunesse.android.fragment.home.HomeFragment;

/**
 * Created by David Levayer on 21/03/15.
 */
public class DatabaseOpenHelper extends SQLiteOpenHelper {

    // TODO rendre la classe générique pour gérer plusieurs tables

    // TODO ajouter deux tables : une pour chaque calendrier récupéré sur Google Calendar

    // todo: verify this is the good way to reference local database
    @SuppressLint("SdCardPath")
    public static String DB_PATH = "/data/data/android.cafejeunesse.com.cafejeunesse/databases/";

    // Todo: use a rest api to sync with it instead
    // @see http://developer.android.com/training/sync-adapters/index.html
    public static final String DB_URL = "https://github.com/cafejeunesse/application-cafe-jeunesse-android/raw/master/cafeDB.sqlite";

    public static final String TABLE_SERVICES = "Services";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "Nom";
    public static final String COLUMN_PHONE = "telephone";
    public static final String COLUMN_INFO = "description";
    public static final String COLUMN_SERVICES = "services";
    public static final String COLUMN_PRICE = "tarif";
    public static final String COLUMN_WEBSITE = "siteweb";
    public static final String COLUMN_FACEBOOK = "pagefacebook";
    public static final String COLUMN_ADDRESS = "adresse";
    public static final String COLUMN_CATEGORIES = "categories";
    public static final String COLUMN_KEYWORDS = "motscles";
    public static final String COLUMN_LAT = "latitude";
    public static final String COLUMN_LON = "longitude";

    public static final String DB_NAME = "cafeDB.sqlite";
    private static final int DATABASE_VERSION = 1;

    private Context mContext;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
        + TABLE_SERVICES + "("
        + COLUMN_ID + " integer primary key autoincrement, "
        + COLUMN_NAME + " text, "
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
        + ");";

    public DatabaseOpenHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Toast.makeText(mContext, "Update from " + oldVersion + " to " + newVersion, Toast.LENGTH_LONG).show();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SERVICES);
        onCreate(db);
    }
}

