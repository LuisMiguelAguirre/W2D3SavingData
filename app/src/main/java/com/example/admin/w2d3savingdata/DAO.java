package com.example.admin.w2d3savingdata;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

class DAO extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "DATA";
    private static final String TABLE_NAME = "PERSON";
    private static final String FIELD_ID = "ID";
    private static final String FIELD_NAME = "NAME";
    private static final String FIELD_PHONE = "PHONE";

    private static final int VERSION = 1;


    DAO(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Object[] values = {TABLE_NAME, FIELD_ID, FIELD_NAME, FIELD_PHONE};
        String CREATE_STATEMENT = "CREATE TABLE " +
                "{0} " +
                "(" +
                "{1} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "{2} TEXT, " +
                "{3} TEXT" +
                ")";
        String query = MessageFormat.format(CREATE_STATEMENT, values);

        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    long Create(String name, String phone) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(FIELD_NAME, name);
        contentValues.put(FIELD_PHONE, phone);

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    List<String> Read(String id) {
        Object[] values = {TABLE_NAME, FIELD_ID, FIELD_NAME, FIELD_PHONE, id};

        String SELECT_STATEMENT = "SELECT {1}, {2}, {3} FROM {0}";

        if (!id.isEmpty()) {
            String whereStatement = " WHERE {1} = {4}";
            SELECT_STATEMENT = SELECT_STATEMENT + whereStatement;
        }

        String query = MessageFormat.format(SELECT_STATEMENT, values);

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        List<String> persons = new ArrayList<>();
        try (Cursor cursor = sqLiteDatabase.rawQuery(query, null)) {
            if (cursor.moveToNext()) {
                do {
                    persons.add(cursor.getString(0) + "|" + cursor.getString(1) + "|" + cursor.getString(2));
                } while (cursor.moveToNext());
            }
        }

        return persons;
    }

    int Update(String id, String name, String phone) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(FIELD_NAME, name);
        contentValues.put(FIELD_PHONE, phone);

        Object[] values = {FIELD_ID, id};
        String CREATE_STATEMENT = "{0}={1}";
        String whereClause = MessageFormat.format(CREATE_STATEMENT, values);

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.update(TABLE_NAME, contentValues, whereClause, null);


    }

    int Drop(String id) {

        Object[] values = {FIELD_ID, id};
        String CREATE_STATEMENT = "{0}={1}";
        String whereClause = MessageFormat.format(CREATE_STATEMENT, values);

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        if (!id.isEmpty()) {
            return sqLiteDatabase.delete(TABLE_NAME, whereClause, null);
        }else{
            return sqLiteDatabase.delete(TABLE_NAME, null, null);
        }
    }

}
