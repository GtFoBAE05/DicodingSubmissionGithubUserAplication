package com.example.githubapp.data

import android.content.ContentValues.TAG
import android.util.Log
import com.example.githubapp.data.local.entity.FavUserEntity
import com.example.githubapp.data.local.room.UserDao
import com.example.githubapp.data.remote.api.ApiService
import com.example.githubapp.data.remote.response.UserDetailResponse
import com.example.githubapp.data.remote.response.UserList
import com.example.githubapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UserRepository private constructor(
    private val apiService: ApiService,
    private val userDao: UserDao
) {

    //API
    suspend fun getAllUser(username: String): Flow<Resource<List<UserList>>> {
        return flow {
            emit(Resource.Loading)
            try {
                val response = apiService.getUsername(username)
                emit(Resource.Success(response.items))
            } catch (e: Exception) {
                emit(Resource.Error(e.message.toString()))
                Log.e(TAG, "getAllUserRepository: " + e.message)
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getDetailUser(username: String): Flow<Resource<UserDetailResponse>> {
        return flow {
            emit(Resource.Loading)
            try {
                val response = apiService.getDetailUser(username)
                emit(Resource.Success(response))
            } catch (e: java.lang.Exception) {
                emit(Resource.Error(e.message.toString()))
                Log.e(TAG, "getDetailUserRepository: " + e.message)
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getFollowerList(username: String): Flow<Resource<List<UserList>>> {
        return flow {
            emit(Resource.Loading)
            try {
                val response = apiService.getFollowerList(username)
                emit(Resource.Success(response))
            } catch (e: Exception) {
                emit(Resource.Error(e.message.toString()))
                Log.e(TAG, "getFollowerListRepository: " + e.message)
            }
        }.flowOn(Dispatchers.IO)

    }

    suspend fun getFollowingList(username: String): Flow<Resource<List<UserList>>> {
        return flow {
            emit(Resource.Loading)
            try {
                val response = apiService.getFollowingList(username)
                emit(Resource.Success(response))
            } catch (e: Exception) {
                emit(Resource.Error(e.message.toString()))
                Log.e(TAG, "getFollowingListRepository: " + e.message)
            }
        }.flowOn(Dispatchers.IO)
    }

    //DAO
    fun getFavUser() = userDao.getFavoriteUser()

    fun getFavUserByUsername(username: String) = userDao.getFavoriteUserByUsername(username)

    suspend fun addFavUser(favUserEntity: FavUserEntity) = userDao.addFavoriteUser(favUserEntity)


    suspend fun deleteFavUser(favUserEntity: FavUserEntity) =
        userDao.deleteFavoriteUser(favUserEntity)


    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            apiService: ApiService,
            userDao: UserDao,
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService, userDao)
            }.also { instance = it }
    }
}