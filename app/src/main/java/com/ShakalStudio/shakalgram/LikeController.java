package com.ShakalStudio.shakalgram;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class LikeController {
    private String _tableName = "image";
    private SQLiteDatabase _dataBase;
    public LikeController(Context context)
    {
        _dataBase = context.openOrCreateDatabase("app.db", context.MODE_PRIVATE, null);
        CraeteTable();
    }

    public void Insert(String url){
        _dataBase.execSQL("INSERT INTO "+_tableName+" VALUES ('"+ url +"');");
    }

    public void Delete(String url){
        _dataBase.execSQL("DELETE FROM "+ _tableName +" WHERE url = '"+ url +"';");
    }

    public boolean FindLikeToURL(String url){
        boolean isFinded = false;
        Cursor query = _dataBase.rawQuery("SELECT url FROM "+_tableName+" WHERE url = '"+url+"';", null);
        if(query.moveToFirst()){
            isFinded = !query.getString(0).isEmpty();
        }
        return isFinded;
    }

    private void CraeteTable() {
        _dataBase.execSQL("CREATE TABLE IF NOT EXISTS "+_tableName+" (url TEXT);");
    }
}
