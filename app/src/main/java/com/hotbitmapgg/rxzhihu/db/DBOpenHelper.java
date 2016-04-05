package com.hotbitmapgg.rxzhihu.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper
{

    private static DBOpenHelper instance;

    private DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {

        super(context, name, factory, version);
    }

    public static DBOpenHelper getInstance(Context context)
    {

        if (instance == null)
        {
            synchronized (DBOpenHelper.class)
            {
                if (instance == null)
                {
                    instance = new DBOpenHelper(context.getApplicationContext(), DBConstant.DB_NAME, null, DBConstant.DB_VERSION);
                }
            }
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {

        db.execSQL(DBConstant.CREATE_TABLE_READ);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
}
