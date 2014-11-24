package ssd.sale

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


}