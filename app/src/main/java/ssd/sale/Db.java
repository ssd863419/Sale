package ssd.sale;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2014/11/22.
 */
public class Db extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "fuZXD";
    private static final int DATABASE_VERSION = 3;
    private String sql = null;
    private SQLiteDatabase database;

    public Db(Context context, String name,
                            SQLiteDatabase.CursorFactory factory, int version) {

        super(context, name, null, version);
    }

    public Db(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        database = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public SQLiteDatabase getDataBase() {
        return database;
    }
}
