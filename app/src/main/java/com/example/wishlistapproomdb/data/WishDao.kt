package com.example.wishlistapproomdb.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
abstract class WishDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun addWish(wish: Wish)

    @Update
    abstract suspend fun updateWish(wishEntity: Wish)

    @Query("SELECT * FROM wish_table WHERE id = :id")
    abstract fun getWishById(id: Long): Flow<Wish>

    @Query("SELECT * FROM wish_table")
    abstract fun getAllWishes(): Flow<List<Wish>>

    @Delete
    abstract suspend fun deleteWish(wishEntity: Wish)
}