package ssd.util

import android.database.Cursor

public class Sql {

    // TODO: 應該把所有 _ID 改成 _id 感覺順眼一點...
    public static final String CREATE_TABLE_fuZXD003 = """
            CREATE TABLE IF NOT EXISTS fuZXD003 (
            _ID INTEGER PRIMARY KEY AUTOINCREMENT,
            gongYSMC TEXT NOT NULL,
            gongYSDZ TEXT NOT NULL,
            lianLRXM TEXT NOT NULL,
            lianLRDH TEXT NOT NULL,
            lianLRDH2 TEXT NOT NULL,
            lianLRDH3 TEXT NOT NULL,
            beiZ TEXT NOT NULL,
            shiFQY INTEGER NOT NULL,
            prgName TEXT NOT NULL,
            crtDay TEXT NOT NULL,
            updDay TEXT NOT NULL
            )
    """;


    public static MyList parseCursor(Cursor cursor) {
        def result = new MyList()
        def cols = cursor.getColumnNames()

        while (cursor.moveToNext()) {
            // TODO: 改一下 不要用標準的 Map, 用個自己寫的方便 getString getInt getFloat...
            def row = new MyMap()
            cols.eachWithIndex { col, i ->
                def t = cursor.getType(i)
                if (t == 4) { // FIELD_TYPE_BLOB
                    row[col] = cursor.getBlob(i)
                } else if (t == 2) { // FIELD_TYPE_FLOAT
                    row[col] = cursor.getDouble(i)
                } else if (t == 1) { // FIELD_TYPE_INTEGER
                    row[col] = cursor.getInt(i)
                } else if (t == 0) { // FIELD_TYPE_NULL
                    row[col] = null
                } else if (t == 3) { // FIELD_TYPE_STRING
                    row[col] = cursor.getString(i)
                } else {
                    row[col] = 'huh?'
                }
            }
            result << row
        }
        return result.asList()
    }
}