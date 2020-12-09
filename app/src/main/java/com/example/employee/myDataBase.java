package com.example.employee;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Objects;

public class myDataBase {
    private SQLiteDatabase db;
    private Cursor rs;

    public myDataBase(SQLiteDatabase db) {
        this.db = db;
        db.execSQL("create table if not exists emp(id Integer primary key , name varchar(15),sex char(1),BaseSalary float , TotalSalary float,CommissionRate float);");
    }

    public ArrayList<emp> getAllData()
    {
        try
        {
            rs =db.rawQuery("select * from emp ",null);
        }
        catch (Exception ex)
        {
            Log.e("catchError", Objects.requireNonNull(ex.getMessage()));
        }

        return getData(rs);
    }

    public ArrayList<emp> search(int id)
    {
        try {
            rs = db.rawQuery("select * from emp where id LIKE '%" + id + "%' ", null);
        }
        catch (Exception ex)
        {
            Log.e("catchError", Objects.requireNonNull(ex.getMessage()));
            return null;
        }
        return getData(rs);
    }

    public ArrayList<emp> search(String name) {

        try {
            rs = db.rawQuery("select * from emp where name LIKE '%" + name + "%' ", null);
        } catch (Exception ex) {
            Log.e("catchError", Objects.requireNonNull(ex.getMessage()));
        }
        return getData(rs);
    }

    public void insert(Object[] info) {
        db.execSQL("insert into emp values(?,?,?,?,?,?);", info);
    }

    public void delete(int id) {
        db.delete("emp", "id = ?", new String[]{"" + id});
    }

    public void update(int id, float salary, float sales, float rate) {
        ContentValues vs = new ContentValues();
        vs.put("BaseSalary", salary);
        vs.put("TotalSalary", sales);
        vs.put("CommissionRate", rate);
        db.update("emp", vs, "id = " + id, null);
    }

    private ArrayList<emp> getData(Cursor rs) {
        ArrayList<emp> employee;
        employee = new ArrayList<>();

        while (rs.moveToNext()) {
            emp person = new emp();
            person.setId(rs.getInt(0));
            person.setName(rs.getString(1));
            person.setGender(rs.getString(2).equals("m") ? "Male" : "Female");
            person.setSalary(rs.getFloat(3));
            person.setSales(rs.getFloat(4));
            person.setRate(rs.getFloat(5));
            employee.add(person);
        }
        return employee;
    }
}
