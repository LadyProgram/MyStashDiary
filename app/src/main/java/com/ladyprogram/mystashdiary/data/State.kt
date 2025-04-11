package com.ladyprogram.mystashdiary.data

import com.ladyprogram.mystashdiary.R

enum class State(val title: Int) {
    CONSUMING(R.string.state_consuming),
    PLANNING(R.string.state_planning),
    COMPLETED(R.string.state_completed)
}