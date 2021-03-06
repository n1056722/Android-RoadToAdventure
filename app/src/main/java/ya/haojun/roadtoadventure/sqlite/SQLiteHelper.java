package ya.haojun.roadtoadventure.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {

    // database name
    public static final String DATABASE_NAME = "roa.db";
    // version
    public static final int VERSION = 8;
    // database object
    private static SQLiteDatabase database;

    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static SQLiteDatabase getDatabase(Context context) {
        if (database == null || !database.isOpen()) {
            database = new SQLiteHelper(context, DATABASE_NAME,
                    null, VERSION).getWritableDatabase();
        }
        return database;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DAOLocationRecord.createTable());
        db.execSQL(DAOJourney.createTable());
        db.execSQL(DAOGroupChat.createTable());
        db.execSQL(DAOFriendChat.createTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DAOLocationRecord.TABLENAME);
        db.execSQL("DROP TABLE IF EXISTS " + DAOJourney.TABLENAME);
        db.execSQL("DROP TABLE IF EXISTS " + DAOGroupChat.TABLENAME);
        db.execSQL("DROP TABLE IF EXISTS " + DAOFriendChat.TABLENAME);
        onCreate(db);
    }
}
