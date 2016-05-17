package com.hotbitmapgg.rxzhihu.db;

public class DBConstant
{

    public static final String DB_NAME = "rxzhihu";

    public static final int DB_VERSION = 2;

    public static final String TABLE_READ = "read";

    public static final String COLUMN_ID = "newid";

    public static final String CREATE_TABLE_READ = "create table " + TABLE_READ + "(" + COLUMN_ID + " text)";
}
