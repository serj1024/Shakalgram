package com.ShakalStudio.shakalgram;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class LikeHandler {
    private String _tableName = "image";
    private SQLiteDatabase _dataBase;

    public LikeHandler(Context context)
    {
        _dataBase = context.openOrCreateDatabase("app.db", context.MODE_PRIVATE, null);
        CraeteTable();
    }

    public boolean trySetLike(String currentUrl) {
        if (findLikeToURL(currentUrl)){
            delete(currentUrl);
            return false;
        }
        else {
            insert(currentUrl);
            return true;
        }
    }

    public boolean findLikeToURL(String url){
        boolean isFinded = false;
        Cursor query = _dataBase.rawQuery("SELECT url FROM "+_tableName+" WHERE url = '"+url+"';", null);
        if(query.moveToFirst()){
            isFinded = !query.getString(0).isEmpty();
        }
        return isFinded;
    }

    private void insert(String url){
        _dataBase.execSQL("INSERT INTO "+_tableName+" VALUES ('"+ url +"');");
    }

    private void delete(String url){
        _dataBase.execSQL("DELETE FROM "+ _tableName +" WHERE url = '"+ url +"';");
    }


    private void CraeteTable() {
        _dataBase.execSQL("CREATE TABLE IF NOT EXISTS "+_tableName+" (url TEXT);");
    }
}
