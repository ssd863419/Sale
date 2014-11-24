package ssd.sale;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2014/11/22.
 */
public class Db extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "fuZXD.db";
    private static final int DATABASE_VERSION = 1;

    public Db(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /* 每個要開的table, 放在這裡, sql語法放在Sql.groovy裡面 */
        db.execSQL(Sql.CREATE_TABLE_fuZXD003);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS fuZXD003");
        onCreate(db);
    }

}
