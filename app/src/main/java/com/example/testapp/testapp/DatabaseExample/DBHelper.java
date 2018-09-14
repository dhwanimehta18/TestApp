package com.example.testapp.testapp.DatabaseExample;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by admin0 on 28-Apr-17.
 */

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE_EMPLOYEE = "CREATE TABLE " + DBConstant.EmployeeTable.TABLENAME + " ( " + DBConstant.EmployeeTable.Fields.ID
                + " TEXT , " + DBConstant.EmployeeTable.Fields.NAME + " TEXT )";

        db.execSQL(CREATE_TABLE_EMPLOYEE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
