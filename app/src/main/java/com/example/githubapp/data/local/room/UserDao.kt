package com.example.githubapp.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.githubapp.data.local.entity.FavUserEntity


@Dao
interface UserDao {
    @Query("select * from favuserentity")
    fun getFavoriteUser(): LiveData<List<FavUserEntity>>

    @Query("SELECT * FROM favuserentity where username = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<FavUserEntity>

    @Insert
    suspend fun addFavoriteUser(favUserEntity: FavUserEntity)

    @Delete
    suspend fun deleteFavoriteUser(favUserEntity: FavUserEntity)
}