package com.ladyprogram.mystashdiary.data


class Element (
    var id: Long,
    var name: String,
    var creator: String,
    var category: Category,
    var state: State,
    /*var note: String,
    var dateCompleted: Long? = null,
    var calification: Int? = null,
    var repetition: Int? = null*/
) {

    companion object {
        const val TABLE_NAME = "Elements"

        const val COLUMN_NAME_ID = "id"
        const val COLUMN_NAME_NAME = "name"
        const val COLUMN_NAME_CREATOR = "creator"
        const val COLUMN_NAME_CATEGORY = "category"
        const val COLUMN_NAME_STATE = "state"
    }
}
