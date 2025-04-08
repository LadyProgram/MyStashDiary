package com.ladyprogram.mystashdiary.data

class Element (
    var id: Long,
    val category: Int,
    var name: String,
    var creator: String,
    var state: Int,
    var note: String,
    var dateConsumed: Long? = null,
    var calification: Int? = null,
    var repetition: Int? = null
) {

    companion object {
        const val TABLE_NAME = "Elements"

        const val COLUMN_NAME_ID = "id"
        const val COLUMN_NAME_CATEGORY = "category"
        const val COLUMN_NAME_NAME = "name"
        const val COLUMN_NAME_CREATOR = "creator"
        const val COLUMN_NAME_STATE = "state"
    }
}









/*class State (
    var dateconsumed: Long,
    var calification: Long,
    var repetition: Long
)*/