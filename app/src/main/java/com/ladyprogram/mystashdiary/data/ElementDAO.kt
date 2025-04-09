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

    fun update(element: Element) {
        // Gets the data repository in write mode
        val db = databaseManager.writableDatabase

        // Create a new map of values, where column names are the keys
        val values = ContentValues().apply {
            put(Element.COLUMN_NAME_NAME, element.name)
            put(Element.COLUMN_NAME_CREATOR, element.creator)
        }

        try {
            val updatedRows = db.update(Element.TABLE_NAME, values, "${Element.COLUMN_NAME_ID} = ${element.id}", null)

            Log.i("DATABASE", "Updated element with id: ${element.id}")
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.close()
        }
    }

    fun delete(element: Element) {
        val db = databaseManager.writableDatabase

        try {
            val deletedRows = db.delete(Element.TABLE_NAME, "${Element.COLUMN_NAME_ID} = ${element.id}", null)

            Log.i("DATABASE", "Deleted element with id: ${element.id}")
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.close()
        }
    }

    fun findById(id: Long): Element? {
        val db = databaseManager.readableDatabase

        val projection = arrayOf(
            Element.COLUMN_NAME_ID,
            Element.COLUMN_NAME_NAME,
            Element.COLUMN_NAME_CREATOR
        )

        val selection = "${Element.COLUMN_NAME_ID} = $id"

        var element: Element? = null

        try {
            val cursor = db.query(
                Element.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
            )

            if (cursor.moveToNext()) {
                val id = cursor.getLong(cursor.getColumnIndexOrThrow(Element.COLUMN_NAME_ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(Element.COLUMN_NAME_NAME))
                val creator = cursor.getString(cursor.getColumnIndexOrThrow(Element.COLUMN_NAME_CREATOR))
                element = Element(id, name, creator)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.close()
        }

        return element
    }

    fun findAll(): List<Element> {
        val db = databaseManager.readableDatabase

        val projection = arrayOf(
            Element.COLUMN_NAME_ID,
            Element.COLUMN_NAME_NAME,
            Element.COLUMN_NAME_CREATOR
        )

        var elementList: MutableList<Element> = mutableListOf()

        try {
            val cursor = db.query(
                Element.TABLE_NAME,  // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
            )

            while (cursor.moveToNext()) {
                val id = cursor.getLong(cursor.getColumnIndexOrThrow(Element.COLUMN_NAME_ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(Element.COLUMN_NAME_NAME))
                val creator = cursor.getString(cursor.getColumnIndexOrThrow(Element.COLUMN_NAME_CREATOR))
                val element = Element(id, name, creator)

                elementList.add(element)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.close()
        }

        return elementList
    }

   /* fun findAllByCategory(category: Category): List<Element> {
        val db = databaseManager.readableDatabase

        val projection = arrayOf(
            Element.COLUMN_NAME_ID,
            Element.COLUMN_NAME_TITLE,
            Element.COLUMN_NAME_DONE,
            Element.COLUMN_NAME_CATEGORY
        )

        val selection = "${Element.COLUMN_NAME_CATEGORY} = ${category.id}"

        var elementList: MutableList<Element> = mutableListOf()

        try {
            val cursor = db.query(
                Element.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                Element.COLUMN_NAME_DONE               // The sort order
            )

            while (cursor.moveToNext()) {
                val id = cursor.getLong(cursor.getColumnIndexOrThrow(Element.COLUMN_NAME_ID))
                val title = cursor.getString(cursor.getColumnIndexOrThrow(Element.COLUMN_NAME_TITLE))
                val done = cursor.getInt(cursor.getColumnIndexOrThrow(Element.COLUMN_NAME_DONE)) != 0
                val categoryId = cursor.getLong(cursor.getColumnIndexOrThrow(Element.COLUMN_NAME_CATEGORY))
                val category = categoryDAO.findById(categoryId)!!
                val element = Element(id, title, done, category)

                elementList.add(element)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.close()
        }

        return elementList
    }*/

}

