package com.example.githubapp.data.remote.api


import com.example.githubapp.data.remote.response.ListUserResponse
import com.example.githubapp.data.remote.response.UserDetailResponse
import com.example.githubapp.data.remote.response.UserList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    suspend fun getUsername(
        @Query("q") username: String
    ): ListUserResponse

    @GET("users/{username}")
    suspend fun getDetailUser(
        @Path("username") username: String
    ): UserDetailResponse

    @GET("users/{username}/followers")
    suspend fun getFollowerList(
        @Path("username") username: String
    ): List<UserList>

    @GET("users/{username}/following")
    suspend fun getFollowingList(
        @Path("username") username: String
    ): List<UserList>

}