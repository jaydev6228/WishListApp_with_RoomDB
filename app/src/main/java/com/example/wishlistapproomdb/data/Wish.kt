package com.example.wishlistapproomdb.data

data class Wish(
    val id: Long = 0L,
    val title: String,
    val description: String
)

object DummyWish {
    val wishList = listOf(
        Wish(title = "Google watch 2", description = "Watch 2"),
        Wish(title = "Item 2", description = "Description 2"),
        Wish(title = "Item 3", description = "Description 3")
    )
}
