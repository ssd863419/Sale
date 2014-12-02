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

    // TODO: 考慮要不要把是否啟用放到參數上
    public SqlList getGongYSMC() {
        String[] args = ["1"]
        Cursor cursor = db.query("fuZXD003", null, "shiFQY = ?", args, null, null, "gongYSMC", null)
        return Sql.parseCursor(cursor)
    }


}
