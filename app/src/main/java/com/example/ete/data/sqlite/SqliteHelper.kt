package com.example.ete.data.sqlite

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.ete.data.bean.UserBean
import com.example.ete.data.sqlite.SqliteConstants.Companion.CREATE_USER_TABLE
import com.example.ete.data.sqlite.SqliteConstants.Companion.DATABASE_NAME
import com.example.ete.data.sqlite.SqliteConstants.Companion.DATABASE_VERSION
import com.example.ete.data.sqlite.SqliteConstants.Companion.EMAIL
import com.example.ete.data.sqlite.SqliteConstants.Companion.ID
import com.example.ete.data.sqlite.SqliteConstants.Companion.NAME
import com.example.ete.data.sqlite.SqliteConstants.Companion.UPGRADE_USER_TABLE
import com.example.ete.data.sqlite.SqliteConstants.Companion.USER_TABLE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SqliteHelper(ctx: Context) : SQLiteOpenHelper(ctx, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(p0: SQLiteDatabase?) {
        Log.e("DATABASE", "DATABASE CREATED")
        p0?.execSQL(CREATE_USER_TABLE)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            Log.e("DATABASE", "DATABASE UPDATED")
            p0?.execSQL(UPGRADE_USER_TABLE)
            createTables()
        }
    }

    companion object {
        private lateinit var INSTANCE: SqliteHelper
        private lateinit var database: SQLiteDatabase
        private var databaseOpen: Boolean = false

        fun closeDatabase() {
            if (database.isOpen && databaseOpen) {
                database.close()
                databaseOpen = false

                Log.i("Database", "Database close")
            }
        }

        fun initDatabaseInstance(ctx: Context): SqliteHelper {
            INSTANCE = SqliteHelper(ctx)
            return INSTANCE
        }


        /** CREATE TABLES **/
        fun createTables() {
            try {
                if (!databaseOpen) {
                    database = INSTANCE.writableDatabase
                    databaseOpen = true

                    Log.i("Database", "Database Open")
                }

                database.execSQL(CREATE_USER_TABLE)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }


        /** USERS TABLE **/
        fun insertUser(userBean: UserBean) {
            try {
                if (!databaseOpen) {
                    database = INSTANCE.writableDatabase
                    databaseOpen = true

                    Log.i("Database", "Database Open")
                }

                val values = ContentValues()
                values.put(NAME, userBean.name)
                values.put(EMAIL, userBean.email)

                database.insert(USER_TABLE, null, values)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun updateUser(localId: Int, userBean: UserBean) {
            try {
                if (!databaseOpen) {
                    database = INSTANCE.writableDatabase
                    databaseOpen = true

                    Log.i("Database", "Database Open")
                }

                val values = ContentValues()
                values.put(NAME, userBean.name)
                values.put(EMAIL, userBean.email)

                database.update(USER_TABLE, values, "$ID=?", arrayOf(localId.toString()))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        @SuppressLint("Recycle")
        fun getUserData(): ArrayList<UserBean> {
            val data: ArrayList<UserBean> = ArrayList()
            try {
                if (!databaseOpen) {
                    database = INSTANCE.writableDatabase
                    databaseOpen = true

                    Log.i("Database", "Database Open")
                }

                val cursor = database.query(USER_TABLE, null, null, null, null, null, null)

                while (cursor.moveToNext()) {
                    val userBean = UserBean()
                    userBean.id = cursor.getLong(cursor.getColumnIndexOrThrow(ID))
                    userBean.name = cursor.getString(cursor.getColumnIndexOrThrow(NAME))
                    userBean.email = cursor.getString(cursor.getColumnIndexOrThrow(EMAIL))
                    data.add(userBean)
                }

                return data
            } catch (e: Exception) {
                e.printStackTrace()
                return data
            }
        }

        fun deleteUserData(arrGarmentBean: List<UserBean>) {
            try {
                if (!databaseOpen) {
                    database = INSTANCE.writableDatabase
                    databaseOpen = true

                    Log.i("Database", "Database Open")
                }

                arrGarmentBean.forEach {
                    database.delete(USER_TABLE, "$ID=?", arrayOf(it.id.toString()))
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun deleteAllUserData() {
            try {
                if (!databaseOpen) {
                    database = INSTANCE.writableDatabase
                    databaseOpen = true

                    Log.i("Database", "Database Open")
                }

                database.delete(USER_TABLE, null, null)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}