package com.demo.dj.HuanXin.tools.dbtools;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by guozhaohui on 2016/8/9.
 */
public class SQLiteDatabaseHelper extends SQLiteOpenHelper {

    private static final String dbName = "HuanXin";
    private static final int dbVersion = 1;

    public SQLiteDatabaseHelper(Context context) {

        super(context, dbName, null, dbVersion);
    }

    private void CreateTablesVer1(SQLiteDatabase db) {
        String tblInfo = "CREATE TABLE IF NOT EXISTS meter_reader " +
                "(reader_id varchar(20) primary key, " +
                "reader_name varchar(20), " +
                "password varchar(20))";
        db.execSQL(tblInfo);

        tblInfo = "CREATE TABLE IF NOT EXISTS area " +
                "(area_no varchar(50) primary key," +
                "area_name varchar(255))";
        db.execSQL(tblInfo);

        tblInfo = "CREATE TABLE IF NOT EXISTS well_info " +
                "(well_no varchar(50) primary key," +
                "well_name varchar(50)," +
                "area_no varchar(50))";
        db.execSQL(tblInfo);

        tblInfo = "CREATE TABLE IF NOT EXISTS user_info " +
                "(user_no varchar(50) primary key," +
                "user_name varchar(50)," +
                "card_no varchar(50)," +
                "area_no varchar(50))";
        db.execSQL(tblInfo);

        tblInfo = "CREATE TABLE IF NOT EXISTS user_data " +
                "(user_no varchar(20) primary key," +
                "user_name varchar(50)," +
                "card_no varchar(20)," +
                "area_no varchar(20))";
        db.execSQL(tblInfo);

    }

    private void CreateTables(SQLiteDatabase db, int dbVersion) {
        if (dbVersion == 1) {
            CreateTablesVer1(db);
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        CreateTables(db, dbVersion);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}
