package ssd.util

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by 0_o on 2014/12/2.
 */
public class Dao {

    private SQLiteDatabase db
    

    public Dao(cxt) {
        DataHelper dh = new DataHelper(cxt.getActivity());
        db = dh.getReadableDatabase()
    }

    public SqlList getGongYSMC() {
        Cursor cursor = db.query("fuZXD003", ["_id", "gongYSMC"], "shiFQY = ?", ["1"], null, null, "gongYSMC", null)
        return Sql.parseCursor(cursor)
    }


}
