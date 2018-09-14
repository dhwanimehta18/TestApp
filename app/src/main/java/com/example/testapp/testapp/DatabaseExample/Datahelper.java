package com.example.testapp.testapp.DatabaseExample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by admin0 on 28-Apr-17.
 */

class Datahelper {
    private final Context mcontext;
    private DBHelper db;
    private ArrayList<Employee> alist;
    private String id;
    private String name;

    public Datahelper(Context dbExample) {
        this.mcontext = dbExample;
    }

    public boolean addEmployeeRecords(Employee emp) {
        db=new DBHelper(mcontext,DBConstant.DataBase.DBNAME,null,1);
        SQLiteDatabase dbs = db.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBConstant.EmployeeTable.Fields.ID,emp.getId());
        cv.put(DBConstant.EmployeeTable.Fields.NAME,emp.getName());
        return dbs.insert(DBConstant.EmployeeTable.TABLENAME,null,cv) != -1;
    }

    public ArrayList<Employee> showRecordsEmployee() {
        db = new DBHelper(mcontext,DBConstant.DataBase.DBNAME,null,1);
        SQLiteDatabase dbs = db.getReadableDatabase();
        String[] columns = {DBConstant.EmployeeTable.Fields.ID,DBConstant.EmployeeTable.Fields.NAME};
        Cursor res = dbs.query(false, DBConstant.EmployeeTable.TABLENAME, columns, null, null, null, null, null, null);
        if(res != null){
            alist = new ArrayList<Employee>();
            while (res.moveToNext()){
                id = res.getString(res.getColumnIndex(DBConstant.EmployeeTable.Fields.ID));
                name = res.getString(res.getColumnIndex(DBConstant.EmployeeTable.Fields.NAME));
                alist.add(new Employee(id,name));
            }
        }
        return alist;
    }

    public boolean updateSelectedEmployee(Employee selectedItem, String etname) {
        db = new DBHelper(mcontext,DBConstant.DataBase.DBNAME,null,1);
        SQLiteDatabase dbs = db.getWritableDatabase();
        //update tablename set name = "new name" where id = "selected item"
        ContentValues cv = new ContentValues();
        cv.put(DBConstant.EmployeeTable.Fields.NAME,etname);
        return dbs.update(DBConstant.EmployeeTable.TABLENAME,cv,DBConstant.EmployeeTable.Fields.ID+"="+selectedItem.getId(),null) > 0;
    }

    public boolean removeRecord(Employee selectedItem) {
        db = new DBHelper(mcontext,DBConstant.DataBase.DBNAME,null,1);
        SQLiteDatabase dbs = db.getWritableDatabase();
        String[] whereargs = {DBConstant.EmployeeTable.Fields.ID};
        //delete from table where id = ""
        return dbs.delete(DBConstant.EmployeeTable.TABLENAME,DBConstant.EmployeeTable.Fields.ID+"="+selectedItem.getId(),null) > 0;
    }
}