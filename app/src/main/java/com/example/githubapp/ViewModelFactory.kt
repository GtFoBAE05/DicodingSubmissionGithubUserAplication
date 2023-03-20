package com.example.githubapp

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubapp.data.UserRepository
import com.example.githubapp.di.Injection
import com.example.githubapp.ui.favorite.FavoriteViewModel
import com.example.githubapp.ui.follower.FollowerViewModel
import com.example.githubapp.ui.following.FollowingViewModel
import com.example.githubapp.ui.main.MainViewModel
import com.example.githubapp.ui.profile.ProfileViewModel

class ViewModelFactory private constructor(private val userRepository: UserRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(userRepository) as T
        }

        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(userRepository) as T
        }

        if (modelClass.isAssignableFrom(FollowerViewModel::class.java)) {
            return FollowerViewModel(userRepository) as T
        }

        if (modelClass.isAssignableFrom(FollowingViewModel::class.java)) {
            return FollowingViewModel(userRepository) as T
        }

        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}