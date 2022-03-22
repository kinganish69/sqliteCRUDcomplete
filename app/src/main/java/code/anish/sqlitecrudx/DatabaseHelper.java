package code.anish.sqlitecrudx;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {


    public DatabaseHelper(@Nullable Context context){
        super(context, Constants.DB_NAME,null ,Constants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Constants.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    db.execSQL("DROP TABLE IF EXISTS "+Constants.TABLE_NAME);
    onCreate(db);
    }

    //insert info function
    public long insertInfo(String name,
                           String age,
                           String phone,
                           String image,
                           String addTimeStamp,
                           String updateTimeStamp) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.C_NAME, name);
        values.put(Constants.C_AGE, age);
        values.put(Constants.C_PHONE, phone);
        values.put(Constants.C_IMAGE, image);
        values.put(Constants.C_ADD_TIMESTAMP, addTimeStamp);
        values.put(Constants.C_UPDATE_TIMESTAMP, updateTimeStamp);

        long id = db.insert(Constants.TABLE_NAME, null, values);
        db.close();
        return id;

    }

    //update info
    public void updateInfo(String id,
                           String name,
                           String age,
                           String phone,
                           String image,
                           String addTimeStamp,
                           String updateTimeStamp) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.C_NAME, name);
        values.put(Constants.C_AGE, age);
        values.put(Constants.C_PHONE, phone);
        values.put(Constants.C_IMAGE, image);
        values.put(Constants.C_ADD_TIMESTAMP, addTimeStamp);
        values.put(Constants.C_UPDATE_TIMESTAMP, updateTimeStamp);

        db.update(Constants.TABLE_NAME, values, Constants.C_ID+ " = ?", new String[]{id});

        db.close();
    }

    public ArrayList<Model> getAllData(String orderBy) {

        ArrayList<Model> arrayList = new ArrayList<>();

        //query for select all info in database
        String selectQuery = "SELECT * FROM " + Constants.TABLE_NAME + "ORDER BY " + orderBy;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //when we select all info from database nev get the data from columns
        if (cursor.moveToNext()) {
            do {
                //is used because first it gets the data from columns then move to next condition

                Model model = new Model(
                        ""+cursor.getInt(cursor.getColumnIndex(Constants.C_ID)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_IMAGE)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_NAME)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_AGE)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_PHONE)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_ADD_TIMESTAMP)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_UPDATE_TIMESTAMP))
                );

                arrayList.add(model);
            } while (cursor.moveToNext());
        }

        db.close();
        return arrayList;
    }

}
