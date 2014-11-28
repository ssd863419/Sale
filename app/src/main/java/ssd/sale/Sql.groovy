package ssd.sale

import android.database.Cursor

public class Sql {

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


    public static List parseCursor(Cursor cursor) {
        def result = []
        def cols = cursor.getColumnNames()
        while (cursor.moveToNext()) {
            def row = [:]
            cols.eachWithIndex { col, i ->
                def t = cursor.getType(i)
                if (t == 4) { // FIELD_TYPE_BLOB
                    row[col] = cursor.getBlob(i)
                } else if (t == 2) { // FIELD_TYPE_FLOAT
                    row[col] = cursor.getFloat(i)
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