package com.cafejeunesse.android.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cafejeunesse.android.structure.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David Levayer on 21/03/15.
 */
public class ServiceDataSource {

    private Context mContext;
    private int mCurrentVersion;

    // Database fields
    private SQLiteDatabase database;
    private DatabaseOpenHelper dbHelper;
    private String[] allColumns = {
            DatabaseOpenHelper.COLUMN_ID,
            DatabaseOpenHelper.COLUMN_NAME,
            DatabaseOpenHelper.COLUMN_PHONE,
            DatabaseOpenHelper.COLUMN_INFO,
            DatabaseOpenHelper.COLUMN_SERVICES,
            DatabaseOpenHelper.COLUMN_PRICE,
            DatabaseOpenHelper.COLUMN_WEBSITE,
            DatabaseOpenHelper.COLUMN_FACEBOOK,
            DatabaseOpenHelper.COLUMN_ADDRESS,
            DatabaseOpenHelper.COLUMN_CATEGORIES,
            DatabaseOpenHelper.COLUMN_KEYWORDS,
            DatabaseOpenHelper.COLUMN_LAT,
            DatabaseOpenHelper.COLUMN_LON};

    public ServiceDataSource(Context context) {
        dbHelper = new DatabaseOpenHelper(context);
        mContext = context;
        mCurrentVersion = 0;
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void importDataFromAsset(String filePath){

        // Retire les services de la base de données
        dbHelper.onUpgrade(database,mCurrentVersion,mCurrentVersion+1);
        mCurrentVersion++;

        SQLiteDatabase newDatabase = SQLiteDatabase.openDatabase(
                filePath,
                null,
                SQLiteDatabase.OPEN_READONLY);

        if(newDatabase != null){
            List<Service> newServices = getAllServices(newDatabase);

            for(Service s: newServices)
                createService(s);

            newDatabase.close();
        }
    }

    public void createService(Service service) {

        ContentValues values = new ContentValues();
        values.put(DatabaseOpenHelper.COLUMN_NAME, service.getServiceName());
        values.put(DatabaseOpenHelper.COLUMN_PHONE, (String)service.getInfo(Service.TAG_PHONENUMBER));
        values.put(DatabaseOpenHelper.COLUMN_INFO, (String)service.getServiceDescription());
        //values.put(DatabaseOpenHelper.COLUMN_SERVICES, (String)service.getInfo(Service.TAG_PHONENUMBER));
        values.put(DatabaseOpenHelper.COLUMN_PRICE, (String)service.getInfo(Service.TAG_PRICE));
        values.put(DatabaseOpenHelper.COLUMN_WEBSITE, (String)service.getInfo(Service.TAG_WEBSITE));
        values.put(DatabaseOpenHelper.COLUMN_FACEBOOK, (String)service.getInfo(Service.TAG_FACEBOOK));
        values.put(DatabaseOpenHelper.COLUMN_ADDRESS, (String)service.getInfo(Service.TAG_ADDRESS));
        //values.put(DatabaseOpenHelper.COLUMN_CATEGORIES, (String)service.getInfo(Service.TAG_CATEGORIES));
        //values.put(DatabaseOpenHelper.COLUMN_KEYWORDS, (String)service.getInfo(Service.TAG_KEYWORDS));
        values.put(DatabaseOpenHelper.COLUMN_LAT, service.getLat());
        values.put(DatabaseOpenHelper.COLUMN_LON, service.getLon());

        long insertId = database.insert(DatabaseOpenHelper.TABLE_SERVICES, null,
                values);
        Cursor cursor = database.query(DatabaseOpenHelper.TABLE_SERVICES,
                allColumns, DatabaseOpenHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);

        cursor.moveToFirst();
        cursor.close();
    }

    private Service cursorToService(Cursor cursor) {

        if(cursor != null){
            Service service = new Service();
            service.setId(cursor.getLong(0));
            service.setServiceName(cursor.getString(1));
            service.addInfo(Service.TAG_PHONENUMBER,cursor.getString(2));
            service.setServiceDescription(cursor.getString(3));
            //service.addInfo(Service.TAG_SERVICES,cursor.getString(4));
            service.addInfo(Service.TAG_PRICE,cursor.getString(5));
            service.addInfo(Service.TAG_WEBSITE,cursor.getString(6));
            service.addInfo(Service.TAG_FACEBOOK,cursor.getString(7));
            service.addInfo(Service.TAG_ADDRESS,cursor.getString(8));
            //service.addInfo(Service.TAG_CATEGORIES,cursor.getString(9));
            service.addInfo(Service.TAG_KEYWORDS,cursor.getString(10));
            service.setLat(cursor.getFloat(11));
            service.setLon(cursor.getFloat(12));
            return service;
        }

        return null;
    }

    public void deleteService(Service service) {
        long id = service.getId();
        database.delete(DatabaseOpenHelper.TABLE_SERVICES, DatabaseOpenHelper.COLUMN_ID
                + " = " + id, null);
    }

    public void deleteAllServices(){
        database.delete(DatabaseOpenHelper.TABLE_SERVICES, null, null);
    }

    public List<Service> getAllServices() {
        return getAllServices(database);
    }

    private List<Service> getAllServices(SQLiteDatabase db){
        List<Service> services = new ArrayList<Service>();

        Cursor cursor = db.query(DatabaseOpenHelper.TABLE_SERVICES,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Service comment = cursorToService(cursor);
            services.add(comment);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return services;
    }
}
