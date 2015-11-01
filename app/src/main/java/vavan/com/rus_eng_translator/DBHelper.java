package vavan.com.rus_eng_translator;

/**
*   Class provide several methods for work with database
*
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context){
        super(context,AppData.DATABASE_NAME,null,AppData.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "create table " + AppData.TABLE_NAME + " (" +
                AppData.COLUMN_ID + " integer primary key autoincrement, " +
                AppData.COLUMN_TEXT_FROM + " text not null," +
                AppData.COLUMN_TEXT_TO + " text not null);";
        db.execSQL(createTable);
        //db.execSQL("create table records (_id integer primary key autoincrement, lfrom text not null, lto text not null);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (AppData.DATABASE_VERSION < newVersion){
            db.execSQL("DROP TABLE IF EXISTS " + AppData.TABLE_NAME);
            onCreate(db);
        }
    }

    public void addRecord(String langFrom, String langTo){
        if (langFrom.length()>0 && langTo.length()>0){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(AppData.COLUMN_TEXT_FROM,langFrom);
            cv.put(AppData.COLUMN_TEXT_TO,langTo);
            db.insert(AppData.TABLE_NAME, null, cv);
            db.close();
        }

    }

    public int getRecordCount(){
        String query = "SELECT * FROM " + AppData.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public List<DBRecord> getAllRecord(){
        List<DBRecord> recordList = new ArrayList<>();
        String query = "SELECT * FROM " + AppData.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()) {

            do {
                DBRecord record = new DBRecord();
                record.setId(Integer.parseInt(cursor.getString(0)));
                record.setLangFrom(cursor.getString(1));
                record.setLangTo(cursor.getString(2));
                recordList.add(record);
            } while(cursor.moveToNext());

        }
        return recordList;
    }


}
