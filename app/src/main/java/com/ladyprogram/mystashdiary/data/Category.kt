package com.ladyprogram.mystashdiary.data

import com.ladyprogram.mystashdiary.R

enum class Category(val title: Int, val icon: Int, val color: Int) {
    BOOK(R.string.category_book, R.drawable.ic_category_book, R.color.black),
    MOVIE(R.string.category_movie, R.drawable.ic_category_movie, R.color.black),
    SERIES(R.string.category_series, R.drawable.ic_category_series, R.color.black),
    ANIME(R.string.category_anime, R.drawable.ic_category_anime, R.color.black)
}