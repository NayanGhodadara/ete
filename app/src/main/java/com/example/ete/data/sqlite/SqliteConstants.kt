package com.example.ete.data.sqlite

class SqliteConstants {

    companion object {

        /** Database **/
        const val DATABASE_NAME = "ete"
        const val DATABASE_VERSION = 1

        /** Common **/
        const val ID = "id"

        /** User Table **/
        const val USER_TABLE = "user_table"
        const val NAME = "name"
        const val EMAIL = "email"


        const val CREATE_USER_TABLE =
            "CREATE TABLE IF NOT EXISTS $USER_TABLE (" +
                    "$ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$NAME VARCHAR, " +
                    "$EMAIL VARCHAR)"

        const val UPGRADE_USER_TABLE = "DROP TABLE IF EXISTS $USER_TABLE"

    }
}