package ya.haojun.roadtoadventure.sqlite;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import ya.haojun.roadtoadventure.model.GroupChat;

public class DAOGroupChat {

    public static final String TABLENAME = "GroupChat";
    // pk
    private static final String GROUP_CHAT_ID = "GroupChatId";
    // other column
    private static final String GROUP_ID = "GroupId";
    private static final String USER_ID = "UserId";
    private static final String CONTENT = "Content";
    private static final String CREATE_DATE = "CreateDate";

    public static String createTable() {
        StringBuilder sb = new StringBuilder();
        sb.append("Create Table " + TABLENAME + " ( ");
        sb.append(GROUP_CHAT_ID + " INTEGER PRIMARY KEY, ");
        sb.append(GROUP_ID + " INTEGER NOT NULL, ");
        sb.append(USER_ID + " TEXT NOT NULL, ");
        sb.append(CONTENT + " TEXT NOT NULL, ");
        sb.append(CREATE_DATE + " TEXT NOT NULL) ");
        return sb.toString();
    }

    private SQLiteDatabase db;

    public DAOGroupChat(Context context) {
        db = SQLiteHelper.getDatabase(context);
    }

    public boolean insert(GroupChat item) {

        ContentValues cv = new ContentValues();

        cv.put(GROUP_CHAT_ID, item.getGroupChatId());
        cv.put(GROUP_ID, item.getGroupId());
        cv.put(USER_ID, item.getUserId());
        cv.put(CONTENT, item.getContent());
        cv.put(CREATE_DATE, item.getCreateDate());

        return db.insert(TABLENAME, null, cv) > 0;
    }

    public boolean update(GroupChat item) {

        ContentValues cv = new ContentValues();

        cv.put(GROUP_ID, item.getGroupId());
        cv.put(USER_ID, item.getUserId());
        cv.put(CONTENT, item.getContent());
        cv.put(CREATE_DATE, item.getCreateDate());

        return db.update(TABLENAME, cv, GROUP_CHAT_ID + "=" + item.getGroupChatId(), null) > 0;
    }


    public boolean delete(int id) {
        return db.delete(TABLENAME, GROUP_CHAT_ID + "=" + id, null) > 0;
    }

    public ArrayList<GroupChat> getAll() {
        ArrayList<GroupChat> result = new ArrayList<>();

        Cursor cursor = db.query(
                TABLENAME, null, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(getRecord(cursor));
        }

        cursor.close();
        return result;
    }


    public GroupChat get(int id) {
        GroupChat item = null;

        String where = GROUP_CHAT_ID + "=" + id;
        Cursor result = db.query(
                TABLENAME, null, where, null, null, null, null, null);

        if (result.moveToFirst()) {
            item = getRecord(result);
        }
        result.close();
        return item;
    }

    public GroupChat getRecord(Cursor cursor) {
        GroupChat result = new GroupChat();
        result.setGroupChatId(cursor.getInt(0));
        result.setGroupId(cursor.getInt(1));
        result.setUserId(cursor.getString(2));
        result.setContent(cursor.getString(3));
        result.setCreateDate(cursor.getString(4));
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
