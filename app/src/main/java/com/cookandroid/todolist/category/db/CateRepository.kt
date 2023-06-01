package com.cookandroid.todolist.category.db

import android.content.Context

class CateRepository(context : Context) {

    private val cateDB = CategoryDatabase.getDatabase(context)

    fun getCateList() = cateDB.categoryDao().getAllData()

    fun getTitle() = cateDB.categoryDao().getTitle()

    fun insertCateData(text : String) = cateDB.categoryDao().insert(CategoryEntity(0, text))

    fun updateData(text : String) = cateDB.categoryDao().update(CategoryEntity(0, text))
}