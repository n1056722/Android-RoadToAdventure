package ya.haojun.roadtoadventure.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ya.haojun.roadtoadventure.model.Friend;
import ya.haojun.roadtoadventure.model.FriendChat;
import ya.haojun.roadtoadventure.model.GroupChat;
import ya.haojun.roadtoadventure.model.JourneyModel;


public class DAOFriendChat {
    // table name
    public static final String TABLENAME = "FriendChat";
    // pk
    private static final String FRIEND_CHAT_ID = "ChatId";
    // other column
    private static final String USER_ID = "UserId";
    private static final String USER_NAME = "UserName";
    private static final String USER_PICTURE = "UserPicture";
    private static final String FRIEND_ID = "FriendId";
    private static final String FRIEND_NAME = "FriendName";
    private static final String FRIEND_PICTURE = "FriendPicture";
    private static final String CONTENT = "Content";
    private static final String CREATE_DATE = "CreateDate";

    public static String createTable() {
        StringBuilder sb = new StringBuilder();
        sb.append("Create Table " + TABLENAME + " ( ");
        sb.append(FRIEND_CHAT_ID + " INTEGER PRIMARY KEY , ");
        sb.append(USER_ID + " TEXT NOT NULL, ");
        sb.append(USER_NAME + " TEXT NOT NULL, ");
        sb.append(USER_PICTURE + " TEXT NOT NULL, ");
        sb.append(FRIEND_ID + " TEXT NOT NULL) ");
        sb.append(FRIEND_NAME + " TEXT NOT NULL, ");
        sb.append(FRIEND_PICTURE + " TEXT NOT NULL, ");
        sb.append(CONTENT + " TEXT NOT NULL, ");
        sb.append(CREATE_DATE + " TEXT NOT NULL) ");
        return sb.toString();
    }

    private SQLiteDatabase db;

    public DAOFriendChat(Context context) {
        db = SQLiteHelper.getDatabase(context);
    }

    public boolean insert(FriendChat item) {

        ContentValues cv = new ContentValues();

        cv.put(FRIEND_CHAT_ID, item.getChatID());
        cv.put(USER_ID, item.getUserID());
        cv.put(USER_NAME, item.getUserName());
        cv.put(USER_PICTURE,item.getUserPicture());
        cv.put(FRIEND_ID,item.getFriendID());
        cv.put(FRIEND_NAME,item.getFriendName());
        cv.put(FRIEND_PICTURE,item.getFriendPicture());
        cv.put(CONTENT,item.getContent());
        cv.put(CREATE_DATE, item.getCreateDate());

        return db.insert(TABLENAME, null, cv) > 0;
    }

    public boolean update(FriendChat item) {

        ContentValues cv = new ContentValues();

        cv.put(USER_ID, item.getUserID());
        cv.put(USER_NAME, item.getUserName());
        cv.put(USER_PICTURE,item.getUserPicture());
        cv.put(FRIEND_ID,item.getFriendID());
        cv.put(FRIEND_NAME,item.getFriendName());
        cv.put(FRIEND_PICTURE,item.getFriendPicture());
        cv.put(CONTENT,item.getContent());
        cv.put(CREATE_DATE, item.getCreateDate());

        return db.update(TABLENAME, cv, FRIEND_CHAT_ID + "=" + item.getChatID(), null) > 0;
    }


    public boolean delete(int id) {

        return db.delete(TABLENAME, FRIEND_CHAT_ID + "=" + id, null) > 0;
    }

    public List<FriendChat> getAll() {
        ArrayList<FriendChat> result = new ArrayList<>();

        Cursor cursor = db.query(
                TABLENAME, null, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(getRecord(cursor));
        }

        cursor.close();
        return result;
    }


    public FriendChat get(int id) {

        FriendChat item = null;

        String where = FRIEND_CHAT_ID + "=" + id;

        Cursor result = db.query(
                TABLENAME, null, where, null, null, null, null, null);


        if (result.moveToFirst()) {

            item = getRecord(result);
        }


        result.close();

        return item;
    }

    public FriendChat getLast() {

        FriendChat item = null;

        Cursor result = db.rawQuery("SELECT * FROM "+TABLENAME+" ORDER BY "+FRIEND_CHAT_ID+" DESC LIMIT 1;", null);


        if (result.moveToFirst()) {

            item = getRecord(result);
        }


        result.close();

        return item;
    }

    public FriendChat getRecord(Cursor cursor) {
        // Course
        FriendChat result = new FriendChat();
        result.setUserID(( cursor.getString(0)));
        result.setUserName(cursor.getString(1));
        result.setUserPicture(cursor.getString(2));
        result.setFriendID(cursor.getString(3));
        result.setFriendName(cursor.getString(4));
        result.setFriendPicture(cursor.getString(5));
        result.setContent(cursor.getString(6));
        result.getCreateDate(cursor.getString(7));
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
