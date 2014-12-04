package ssd.util

import android.database.Cursor

public class Sql {

    /* 所有資料格式
     * NULL：空值。
　　 * INTEGER：带符号的整型，具体取决有存入数字的范围大小。
　　 * REAL：浮点数字，存储为8-byte IEEE浮点数。
　　 * TEXT：字符串文本。
　　 * BLOB：二进制对象。
     * */

    public static final String CREATE_TABLE_fuZXD003 = """
            CREATE TABLE IF NOT EXISTS fuZXD003 (
                _id INTEGER PRIMARY KEY AUTOINCREMENT,
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

    public static final String CREATE_TABLE_fuZXD002 = """
            CREATE TABLE IF NOT EXISTS fuZXD002 (
                _id INTEGER PRIMARY KEY AUTOINCREMENT,
                jinHJ REAL NOT NULL,
                biaoZSJ REAL NOT NULL,
                jinHR TEXT NOT NULL,
                jianS INTEGER NOT NULL,
                huoPBZ TEXT NOT NULL,
                huoPTP BLOB,
                fuZXD003_id INTEGER NOT NULL,
                gongYSXH TEXT NOT NULL,
                shiFQY INTEGER NOT NULL,
                prgName TEXT NOT NULL,
                crtDay TEXT NOT NULL,
                updDay TEXT NOT NULL
            )
    """;

    public static SqlList parseCursor(Cursor cursor) {
        def result = new SqlList()
        def cols = cursor.getColumnNames()

        while (cursor.moveToNext()) {
            def row = new SqlMap()
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