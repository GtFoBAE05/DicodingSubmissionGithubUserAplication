package com.example.githubapp.di

import android.content.Context
import com.example.githubapp.data.UserRepository
import com.example.githubapp.data.local.room.UserDatabase
import com.example.githubapp.data.remote.api.ApiConfig

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val apiService = ApiConfig.getApiService()
        val userDao = UserDatabase.getInstance(context).userDao()
        return UserRepository.getInstance(apiService, userDao)
    }
}