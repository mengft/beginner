package com.mengft.mengft_ui.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SQLiteUtils {
    private static final String TAG = SQLiteUtils.class.getName();
    private Context context;
    private String name = "beginner.db";        // 数据库名称
    private int version = 1;                    // 数据库版本
    private SQLiteDatabase.CursorFactory factory = null;

    public SQLiteUtils(Context context) {
        this.context = context;
    }

    public SQLiteUtils(Context context, String name, int version, SQLiteDatabase.CursorFactory factory) {
        this.context = context;
        this.name = name;
        this.version = version;
        this.factory = factory;
    }

    /**
     * 创建数据库
     */
    public void createDatabase () {
        new SQLiteHelper(context, name, factory, version).getWritableDatabase();
    }

    /**
     * 插表
     * @param table         表名
     * @param jsonArray     数据
     * @param insertKeys    需要插入表中的keys
     */
    public void insertAction (String table, JSONArray jsonArray, String[] insertKeys) {

        SQLiteDatabase sqLiteDatabase = new SQLiteHelper(context, name, factory, version).getWritableDatabase();
        sqLiteDatabase.beginTransaction();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject item = jsonArray.optJSONObject(i);
            Iterator iterator = item.keys();

            ContentValues contentValues = new ContentValues();

            while(iterator.hasNext()){
                String key = iterator.next().toString();
                String value = item.optString(key);

                List<String> keyList = Arrays.asList(insertKeys);
                if (keyList.contains(key))  contentValues.put(key, value);
            }
            sqLiteDatabase.insert(table, null, contentValues);
        }

        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
        sqLiteDatabase.close();
    }

    /**
     *
     * @param sql               sql命令
     * @param selectionArgs     格式化参数
     */
    public void searchAction (String sql, String[] selectionArgs) {

        SQLiteDatabase sqLiteDatabase = new SQLiteHelper(context, name, factory, version).getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);

        while (cursor.moveToNext()) {
            Integer id = cursor.getInt(cursor.getColumnIndex("id"));
            String head = cursor.getString(cursor.getColumnIndex("head"));
            String iconPath = cursor.getString(cursor.getColumnIndex("iconPath"));
            String linkPage = cursor.getString(cursor.getColumnIndex("linkPage"));
            System.out.println(String.format("id: %d, head: %s, iconPath: %s, linkPage: %s", id, head, iconPath, linkPage));
        }

        sqLiteDatabase.close();
    }

    /**
     * 清空表中数据
     * @param table
     */
    public void clearAction (String table) {
        SQLiteDatabase sqLiteDatabase = new SQLiteHelper(context, name, factory, version).getWritableDatabase();
        sqLiteDatabase.beginTransaction();

        String sql = "delete from " + table;
        sqLiteDatabase.execSQL(sql);

        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
        sqLiteDatabase.close();
    }
}
