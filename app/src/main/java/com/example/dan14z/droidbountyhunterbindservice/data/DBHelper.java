package com.example.dan14z.droidbountyhunterbindservice.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.dan14z.droidbountyhunterbindservice.model.Fugitivo;

import java.util.ArrayList;

/**
 * Created by Dan14z on 11/09/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    private SQLiteDatabase database;
    private DBHelper helper;
    private Context context;

    private DBHelper open(){
        helper = new DBHelper(context);
        database = helper.getWritableDatabase();
        return this;
    }

    private DBHelper open_read(){
        helper = new DBHelper(context);
        database = helper.getReadableDatabase();
        return this;
    }

    public void close(){
        helper.close();
        database.close();
    }

    private Cursor querySQL(String sql, String[] selectionArgs){
        Cursor regreso = null;
        open_read();
        regreso = database.rawQuery(sql, selectionArgs);
        return regreso;
    }

    public DBHelper(Context context) {
        super(context, DBProvider.DATABASE_NAME, null, DBProvider.DATABASE_VERSION);
        this.context = context;
    }

    public ArrayList<Fugitivo> getFugitivos(){
        ArrayList<Fugitivo> fugitivos = new ArrayList<>();
        Cursor dataCursor = querySQL("SELECT * FROM " + DBProvider.FugitivoEntry.TABLE_NAME + " ORDER BY " + DBProvider.FugitivoEntry.COLUMN_NAME_NAME, null);

        if(dataCursor != null && dataCursor.getCount() > 0){
            for(dataCursor.moveToFirst(); !dataCursor.isAfterLast(); dataCursor.moveToNext()){
                String name = dataCursor.getString(dataCursor.getColumnIndex(DBProvider.FugitivoEntry.COLUMN_NAME_NAME));
                String status = dataCursor.getString(dataCursor.getColumnIndex(DBProvider.FugitivoEntry.COLUMN_NAME_STATUS));
                String photo = dataCursor.getString(dataCursor.getColumnIndex(DBProvider.FugitivoEntry.COLUMN_NAME_PHOTO));

                fugitivos.add(new Fugitivo(name,status,photo));
            }
        }
//        close();
        return fugitivos;
    }

    public void insertFugitivos(ArrayList<Fugitivo> fugitivos){
        for(int i = 0; i < fugitivos.size(); i++){
            Fugitivo currFugitivo = fugitivos.get(i);
            ContentValues values = new ContentValues();
            values.put(DBProvider.FugitivoEntry.COLUMN_NAME_NAME,currFugitivo.getNombre());
            values.put(DBProvider.FugitivoEntry.COLUMN_NAME_STATUS,currFugitivo.getStatus());
            values.put(DBProvider.FugitivoEntry.COLUMN_NAME_PHOTO,currFugitivo.getPhoto());
            open();
            database.insert(DBProvider.FugitivoEntry.TABLE_NAME,null,values);
//            close();
        }

        Toast.makeText(context, "Fugitivos Added successfully", Toast.LENGTH_SHORT).show();
    }

    public void insertFugitivo(Fugitivo fugitivo){
        ContentValues values = new ContentValues();
        values.put(DBProvider.FugitivoEntry.COLUMN_NAME_NAME,fugitivo.getNombre());
        values.put(DBProvider.FugitivoEntry.COLUMN_NAME_STATUS,fugitivo.getStatus());
        values.put(DBProvider.FugitivoEntry.COLUMN_NAME_PHOTO,fugitivo.getPhoto());
        open();
        database.insert(DBProvider.FugitivoEntry.TABLE_NAME,null,values);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DBProvider.FugitivoEntry.CREATE_TABLE_FUGITIVOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DBProvider.FugitivoEntry.DROP_IF_EXISTS);
        onCreate(sqLiteDatabase);
    }

}
