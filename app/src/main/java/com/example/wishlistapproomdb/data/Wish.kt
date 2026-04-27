package com.example.wishlistapproomdb.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wish_table")
data class Wish(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo(name = "wish_title")
    val title: String,
    @ColumnInfo(name = "wish_description")
    val description: String
)

object DummyWish {
    val wishList = listOf(
        Wish(title = "Google watch 2", description = "Watch 2"),
        Wish(title = "Item 2", description = "Description 2"),
        Wish(title = "Item 3", description = "Description 3")
    )
}
