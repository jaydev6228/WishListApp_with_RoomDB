package com.example.wishlistapproomdb

import android.content.Context
import androidx.room.Room
import com.example.wishlistapproomdb.data.WishDataBase
import com.example.wishlistapproomdb.data.WishRepository

object Graph {
    lateinit var database: WishDataBase

    val wishRepository by lazy {
        WishRepository(
            wishDao = database.wishDao()
        )
    }

    fun provide(context: Context) {
        database = Room.databaseBuilder(context, WishDataBase::class.java, "wish_database")
            .build()
    }
}