package ssd.util

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by 0_o on 2014/12/2.
 */
public class Dao {

    private SQLiteDatabase db
    

    public Dao(cxt) {
        DataHelper dh = new DataHelper(cxt.getActivity())
        db = dh.getReadableDatabase()
    }

    public SqlList getGongYSMC() {
        String[] cols = ["_id", "gongYSMC"]
        String[] args = ["1"]
        Cursor cursor = db.query("fuZXD003", cols, "shiFQY = ?", args, null, null, "gongYSMC", null)
        return Sql.parseCursor(cursor)
    }


}
