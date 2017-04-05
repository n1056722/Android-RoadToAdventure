package ya.haojun.roadtoadventure.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ya.haojun.roadtoadventure.model.LocationRecordModel;


/**
 * Created by asus on 2016/5/8.
 */
public class DAOLocationRecord {
    // table name
    public static final String TABLENAME = "LocationRecord";
    // pk
    private static final String LOCATION_RECORD_ID = "LocationRecordId";
    // other column
    private static final String LATITUDE_COL = "Latitude";
    private static final String LONGITUDE_COL = "Longitude";
    private static final String TIME_COL = "Time";

    public static String createTable() {
        StringBuilder sb = new StringBuilder();
        sb.append("Create Table " + TABLENAME + " ( ");
        sb.append(LOCATION_RECORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , ");
        sb.append(LATITUDE_COL + " REAL NOT NULL, ");
        sb.append(LONGITUDE_COL + " REAL NOT NULL, ");
        sb.append(TIME_COL + " TEXT NOT NULL) ");
        return sb.toString();
    }

    private SQLiteDatabase db;

    public DAOLocationRecord(Context context) {
        db = SQLiteHelper.getDatabase(context);
    }

    public boolean insert(LocationRecordModel item) {

        ContentValues cv = new ContentValues();

        cv.put(LATITUDE_COL, item.getLatitude());
        cv.put(LONGITUDE_COL, item.getLongitude());
        cv.put(TIME_COL, item.getTime());

        return db.insert(TABLENAME, null, cv) > 0;
    }

    public boolean update(LocationRecordModel item) {

        ContentValues cv = new ContentValues();

        cv.put(LATITUDE_COL, item.getLatitude());
        cv.put(LONGITUDE_COL, item.getLongitude());
        cv.put(TIME_COL, item.getTime());

        String where = LOCATION_RECORD_ID + "=" + item.getLocationRecordId();

        return db.update(TABLENAME, cv, where, null) > 0;
    }


    public boolean delete(int id) {

        String where = LOCATION_RECORD_ID + "=" + id;

        return db.delete(TABLENAME, where, null) > 0;
    }

    public List<LocationRecordModel> getAll() {
        List<LocationRecordModel> result = new ArrayList<>();
        Cursor cursor = db.query(
                TABLENAME, null, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(getRecord(cursor));
        }

        cursor.close();
        return result;
    }

    public List<LocationRecordModel> filter(String from, String to) {
        List<LocationRecordModel> result = new ArrayList<>();
        String where = TIME_COL + " BETWEEN '" + from + "' AND '" + to + "'";
        Cursor cursor = db.query(
                TABLENAME, null, where, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(getRecord(cursor));
        }

        cursor.close();
        return result;
    }


    public LocationRecordModel get(int id) {

        LocationRecordModel item = null;

        String where = LOCATION_RECORD_ID + "=" + id;

        Cursor result = db.query(
                TABLENAME, null, where, null, null, null, null, null);


        if (result.moveToFirst()) {

            item = getRecord(result);
        }


        result.close();

        return item;
    }

    public LocationRecordModel getRecord(Cursor cursor) {
        // Course
        LocationRecordModel result = new LocationRecordModel();
        result.setLocationRecordId((int) cursor.getLong(0));
        result.setLatitude(cursor.getDouble(1));
        result.setLongitude(cursor.getDouble(2));
        result.setTime(cursor.getString(3));
        return result;
    }


    public int getCount() {
        int result = 0;
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLENAME, null);
        if (cursor.moveToNext()) {
            result = cursor.getInt(0);
        }
        cursor.close();
        return result;
    }

    public int getCount(String from, String to) {
        int result = 0;
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLENAME + " WHERE " + TIME_COL + " BETWEEN '" + from + "' AND '" + to + "'", null);
        if (cursor.moveToNext()) {
            result = cursor.getInt(0);
        }
        cursor.close();
        return result;
    }
}
