package com.trot.trotwithtabs;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBOpenHelper extends SQLiteOpenHelper {

    static final String DB_NAME = "Jjim.db";
    public static final String DB_TABLE_SONG = "songJjim";
    public static final String DB_TABLE_SINGER = "singerJjim";
    static final int DB_VERSION = 1;

    public DBOpenHelper (Context context){
        // 생성자를 이용하여 클래스 사용 시 기본적으로 화면의 제어권자를 입력받는다.
        super(context, DB_NAME, null, DB_VERSION);
    }

    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                    int version) {
        super(context, name, factory, version);
    }
    // state = 0이면 찜 X, 1이면 찜 O
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + DB_TABLE_SONG + "(" +
                "number INTEGER PRIMARY KEY AUTOINCREMENT," +
                "videoId TEXT," +
                "title TEXT," +
                "thumbnail TEXT," +
                "state INTEGER)");

        Log.d("songJjim ","노래 찜 테이블 생성");

        db.execSQL("CREATE TABLE " + DB_TABLE_SINGER + "(" +
                "number INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT)");

        Log.d("singerJjim ","가수 찜 테이블 생성");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertSongJjim(String id, String title, String thumbnail) {
        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
        db.execSQL( "INSERT INTO " + DB_TABLE_SONG + "(videoId, title, thumbnail,state)" + "values" + "(" + "'" + id + "'" + ",'" + title + "'" + ",'" + thumbnail + "'" + "," + 1 + ");");
        db.close();
    }

    public void insertSingerJjim(String name) {
        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
        db.execSQL( "INSERT INTO " + DB_TABLE_SINGER +"(name)" + "values" + "('" +  name + "');");
        db.close();
    }

    public ArrayList<SongJjimList> selectSongJjim() {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + DB_TABLE_SONG, null);
        ArrayList<SongJjimList> list = new ArrayList<>();
        cursor.moveToFirst();
        if (cursor.moveToFirst()) {
            do {
                list.add(new SongJjimList(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public boolean checkSongJjim(String videoId) {

        SQLiteDatabase db = getWritableDatabase();
        String[] tokens = {videoId};

        String sql = "select * from songJjim ";
        sql +=" where videoId = ?";

        Cursor result;

        result = db.rawQuery(sql, tokens);

        int count = result.getCount();

        db.close();

        if(count==0) {
            return false;
        }
        else if(count==1) {
            return true;
        }
        else {
            return false;
        }
    }

    public ArrayList<SingerJjimList> selectSingerJjim() {
        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + DB_TABLE_SINGER, null);
        ArrayList<SingerJjimList> list = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                list.add(new SingerJjimList(cursor.getString(1)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public String selectSongJjimState() {
        SQLiteDatabase db = getWritableDatabase();
        String state = "";
        Cursor cursor = db.rawQuery("SELECT * FROM " + DB_TABLE_SONG, null);
        if (cursor.moveToFirst()) {
            do {
                state = cursor.getString(4);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return state;
    }

    /*
    public int selectSongJjimState(String id) {
        SQLiteDatabase db = getWritableDatabase();
        int state = 0;
        ArrayList<Integer> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT state from " + DB_TABLE_SONG, null);
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getInt(4));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return state;
    }
     */

    public void IsSongJjimState(String id) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE " + DB_TABLE_SONG + " set state = " + "찜" + " WHERE videoId = '" + id + "';");
        db.close();
    }

    /*public void changeSongJjimState(String id, int state) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE " + DB_TABLE_SONG + " set state = " + state + " WHERE videoId = '" + id + "';");
        db.close();
    }

     */

    public void deleteSongJjim(String id) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행 삭제
        db.execSQL("DELETE FROM " + DB_TABLE_SONG + " WHERE videoId='" + id + "';");
        db.close();
    }

    public void deleteSingerJjim(String name) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행 삭제
        db.execSQL("DELETE FROM " + DB_TABLE_SINGER + " WHERE name='" + name + "';");
        db.close();
    }

}
