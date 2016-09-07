package com.demo.dj.HuanXin.models;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.demo.dj.HuanXin.beans.AreaInfoBean;
import com.demo.dj.HuanXin.beans.MeterReaderBean;
import com.demo.dj.HuanXin.tools.dbtools.SQLiteDatabaseHelper;

/**
 * Created by guozhaohui on 2016/8/10.
 */
public class DBManager {

    private Context mContext;
    private SQLiteDatabaseHelper mDbHelper;

    public DBManager(Context context) {
        mContext = context;
        mDbHelper = new SQLiteDatabaseHelper(mContext);
    }

    //APP是否已授权
    public boolean isAuthorized() {
        String sql = "select count(1) from meter_reader";
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        db.close();

        return count > 0;
    }

    //验证授权
    public boolean checkAuthorization(String name, String pass) {
        String sql = "select count(1) from meter_reader where reader_id=? and password=?";
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, new String[]{name, pass});

        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        db.close();

        return count > 0;
    }

    //授权
    public boolean authorize(MeterReaderBean data) {
        String sql = "replace into meter_reader " +
                "(reader_id, reader_name, password) " +
                "values(?,?,?)";
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        try{
            db.execSQL(sql,
                    new Object[]{data.getReaderId(), data.getReaderName(), data.getPassword()});
            db.close();
            return true;
        }catch (Exception e){
            Log.d("usbh", "写入抄表员授权信息失败！错误信息：" + e.getMessage());
            db.close();
            return false;
        }
    }

    //行政区划信息更新
    public void replaceAreaInfo(AreaInfoBean data){
        String sql = "replace into area (area_no, area_name) values(?, ?)";
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        try{
            db.execSQL(sql,
                    new Object[]{data.getAreaNumber(), data.getAreaName()});
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.close();
        }
    }

    //获取行政区划信息
    public AreaInfoBean getAreaInfoItem(String areaNumber) {
        String sql = "select area_name from where area_no = ?";
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(sql, new String[]{areaNumber});

        AreaInfoBean data = new AreaInfoBean();
        data.setAreaNumber(areaNumber);
        if (cursor.moveToFirst()) {
            data.setAreaName(cursor.getString(0));
            db.close();
            return data;
        }
        else {
            db.close();
            return null;
        }
    }
}
