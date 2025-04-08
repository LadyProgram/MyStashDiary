package com.ladyprogram.mystashdiary.data

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.ladyprogram.mystashdiary.utils.DatabaseManager

class ElementDAO  (context: Context) {

    val databaseManager = DatabaseManager(context)
    

    fun insert(element: Element) {
        // Gets the data repository in write mode
        val db = databaseManager.writableDatabase

        // Create a new map of values, where column names are the keys
        val values = ContentValues().apply {
            put(Element.COLUMN_NAME_NAME, element.name)
            put(Element.COLUMN_NAME_CREATOR, element.creator)

        }

        try {
            // Insert the new row, returning the primary key value of the new row
            val newRowId = db.insert(Element.TABLE_NAME, null, values)

            Log.i("DATABASE", "Inserted element with id: $newRowId")
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.close()
        }
    }

}