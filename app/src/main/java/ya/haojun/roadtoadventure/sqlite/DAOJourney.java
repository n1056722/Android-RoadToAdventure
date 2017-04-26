package ya.haojun.roadtoadventure.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ya.haojun.roadtoadventure.model.JourneyModel;


public class DAOJourney {
    // table name
    public static final String TABLENAME = "Journey";
    // pk
    private static final String JOURNEY_ID = "JourneyId";
    // other column
    private static final String JOURNEY_NAME_COL = "JourneyName";
    private static final String JOURNEY_CONTENT_COL = "JourneyContent";
    private static final String START_TIME_COL = "StartTime";
    private static final String STOP_TIME_COL = "StopTime";

    public static String createTable() {
        StringBuilder sb = new StringBuilder();
        sb.append("Create Table " + TABLENAME + " ( ");
        sb.append(JOURNEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , ");
        sb.append(JOURNEY_NAME_COL + " TEXT NOT NULL, ");
        sb.append(JOURNEY_CONTENT_COL + " TEXT NOT NULL, ");
        sb.append(START_TIME_COL + " TEXT NOT NULL, ");
        sb.append(STOP_TIME_COL + " TEXT NOT NULL) ");
        return sb.toString();
    }

    private SQLiteDatabase db;

    public DAOJourney(Context context) {
        db = SQLiteHelper.getDatabase(context);
    }

    public boolean insert(JourneyModel item) {

        ContentValues cv = new ContentValues();

        cv.put(JOURNEY_NAME_COL, item.getJourneyName());
        cv.put(JOURNEY_CONTENT_COL, item.getJourneyContent());
        cv.put(START_TIME_COL, item.getStartTime());
        cv.put(STOP_TIME_COL, item.getStopTime());

        long id =  db.insert(TABLENAME, null, cv);
        item.setJourneyId((int) id);
        return id > 0;
    }

    public boolean update(JourneyModel item) {

        ContentValues cv = new ContentValues();

        cv.put(JOURNEY_NAME_COL, item.getJourneyName());
        cv.put(JOURNEY_CONTENT_COL, item.getJourneyContent());
        cv.put(START_TIME_COL, item.getStartTime());
        cv.put(STOP_TIME_COL, item.getStopTime());

        String where = JOURNEY_ID + "=" + item.getJourneyId();

        return db.update(TABLENAME, cv, where, null) > 0;
    }


    public boolean delete(int id) {

        String where = JOURNEY_ID + "=" + id;

        return db.delete(TABLENAME, where, null) > 0;
    }

    public List<JourneyModel> getAll() {
        List<JourneyModel> result = new ArrayList<>();
        Cursor cursor = db.query(
                TABLENAME, null, null, null, null, null, JOURNEY_ID + " desc", null);

        while (cursor.moveToNext()) {
            result.add(getRecord(cursor));
        }

        cursor.close();
        return result;
    }


    public JourneyModel get(int id) {

        JourneyModel item = null;

        String where = JOURNEY_ID + "=" + id;

        Cursor result = db.query(
                TABLENAME, null, where, null, null, null, null, null);


        if (result.moveToFirst()) {

            item = getRecord(result);
        }


        result.close();

        return item;
    }

    public JourneyModel getLast() {

        JourneyModel item = null;

        Cursor result = db.rawQuery("SELECT * FROM "+TABLENAME+" ORDER BY "+JOURNEY_ID+" DESC LIMIT 1;", null);


        if (result.moveToFirst()) {

            item = getRecord(result);
        }


        result.close();

        return item;
    }

    public JourneyModel getRecord(Cursor cursor) {
        // Course
        JourneyModel result = new JourneyModel();
        result.setJourneyId((int) cursor.getLong(0));
        result.setJourneyName(cursor.getString(1));
        result.setJourneyContent(cursor.getString(2));
        result.setStartTime(cursor.getString(3));
        result.setStopTime(cursor.getString(4));
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
}
