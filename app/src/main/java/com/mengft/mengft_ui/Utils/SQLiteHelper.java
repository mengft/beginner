package com.mengft.mengft_ui.Utils;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String TAG = SQLiteUtils.class.getName();

    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.e(TAG, "创建数据库");
        initDatabase(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.e(TAG, "更新数据库");
    }

    /**
     * 初始化数据库
     */
    private void initDatabase (SQLiteDatabase sqLiteDatabase) {
        // 初始化命令
        String[] sqlList = {
                "create table News("
                        +"id integer primary key,"
                        +"head text,"
                        +"iconPath text,"
                        +"linkPage text)"
        };

        sqLiteDatabase.beginTransaction();
        try {
            for (String sql: sqlList) {
                sqLiteDatabase.execSQL(sql);
            }
            sqLiteDatabase.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            sqLiteDatabase.endTransaction();
        }
    }
}
