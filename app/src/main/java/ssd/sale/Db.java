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

    /* 這邊放所有開啟table的sql */
    // table fuZXD003
    private static final String CREATE_TABLE_fuZXD003 = "CREATE TABLE IF NOT EXISTS " + "fuZXD003" + "(" +
            "_ID" + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            "gongYSMC" + " TEXT NOT NULL, " +
            "gongYSDZ" + " TEXT NOT NULL, " +
            "lianLRXM" + " TEXT NOT NULL, " +
            "lianLRDH" + " TEXT NOT NULL, " +
            "lianLRDH2" + " TEXT NOT NULL, " +
            "lianLRDH3" + " TEXT NOT NULL, " +
            "beiZ" + " TEXT NOT NULL, " +
            "shiFQY" + " INTEGER NOT NULL, " +
            "prgName" + " TEXT NOT NULL, " +
            "crtDay" + " TEXT NOT NULL, " +
            "updDay" + " TEXT NOT NULL" +
            ")";

    public Db(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_fuZXD003);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS fuZXD003");
        onCreate(db);
    }

}
