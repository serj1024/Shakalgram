package com.ShakalStudio.shakalgram;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.squareup.picasso.Picasso;

public class LikeHandler {
    private String _tableName = "image";
    private SQLiteDatabase _dataBase;

    public LikeHandler(Context context)
    {
        _dataBase = context.openOrCreateDatabase("app.db", context.MODE_PRIVATE, null);
        CraeteTable();
    }

    public boolean TrySetLike(String currentUrl) {
        if (FindLikeToURL(currentUrl)){
            Delete(currentUrl);
            return false;
        }
        else {
            Insert(currentUrl);
            return true;
        }
    }

    public boolean FindLikeToURL(String url){
        boolean isFinded = false;
        Cursor query = _dataBase.rawQuery("SELECT url FROM "+_tableName+" WHERE url = '"+url+"';", null);
        if(query.moveToFirst()){
            isFinded = !query.getString(0).isEmpty();
        }
        return isFinded;
    }

    private void Insert(String url){
        _dataBase.execSQL("INSERT INTO "+_tableName+" VALUES ('"+ url +"');");
    }

    private void Delete(String url){
        _dataBase.execSQL("DELETE FROM "+ _tableName +" WHERE url = '"+ url +"';");
    }


    private void CraeteTable() {
        _dataBase.execSQL("CREATE TABLE IF NOT EXISTS "+_tableName+" (url TEXT);");
    }
}
