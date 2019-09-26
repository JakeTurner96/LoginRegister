package com.example.loginregister;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Student.db";
    private static final String TABLE_NAME = "Student_table";
    private static final String COL_1 = "Email";
    private static final String COL_2 = "Name";
    private static final String COL_3  = "Surname";
    private static final String COL_4  = "Password";
    private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ("+COL_1+" NVARCHAR(320) PRIMARY KEY, "+COL_2+" VARCHAR(255) , "+COL_3+" VARCHAR(255), "+COL_4+" VARCHAR(255));";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String email, String firstName, String lastName, String password){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, email);
        contentValues.put(COL_2, firstName);
        contentValues.put(COL_3, lastName);
        contentValues.put(COL_4, password);
        long result = db.insert(TABLE_NAME, null, contentValues);

        if(result == -1){
            return false;
        } else{
            return true;
        }
    }

    public Integer deleteData(String email){

        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "email = ?", new String[] {email});
    }

    public boolean verifyLogin(String email, String password, Context context){

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * from " + TABLE_NAME + " where " + COL_1 + " = " + "'" + email + "'" + " AND " + COL_4 + " = " + "'" + password + "'";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.getCount() <=0){
            cursor.close();
            Toast.makeText(context, "Login failed", Toast.LENGTH_SHORT).show();
            return false;
        }else {
            cursor.close();
            return true;
        }
    }
}