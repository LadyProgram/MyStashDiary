package com.ladyprogram.mystashdiary.utils

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.ladyprogram.mystashdiary.data.Element

class DatabaseManager (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "reminders.db"
        const val DATABASE_VERSION = 1

        private const val SQL_CREATE_TABLE_ELEMENT =
            "CREATE TABLE ${Element.TABLE_NAME} (" +
                    "${Element.COLUMN_NAME_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "${Element.COLUMN_NAME_NAME} TEXT," +
                    "${Element.COLUMN_NAME_CREATOR} TEXT)"

        private const val SQL_DROP_TABLE_TASK = "DROP TABLE IF EXISTS ${Element.TABLE_NAME}"

    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_ELEMENT)
        Log.i("DATABASE", "Created table Tasks")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onDestroy(db)
        onCreate(db)
    }

    private fun onDestroy(db: SQLiteDatabase) {
        db.execSQL(SQL_DROP_TABLE_TASK)
    }
}