package com.example.wishlistapproomdb.data

import kotlinx.coroutines.flow.Flow

class WishRepository(private val wishDao: WishDao) {
    suspend fun addWish(wish: Wish) {
        wishDao.addWish(wish)
    }

    fun getWishById(id: Long): Flow<Wish> {
        return wishDao.getWishById(id)
    }

    fun getAllWishes(): Flow<List<Wish>> {
        return wishDao.getAllWishes()
    }

    suspend fun deleteWish(wish: Wish) {
        wishDao.deleteWish(wish)
    }

    suspend fun updateWish(wish: Wish) {
        wishDao.updateWish(wish)
    }
}