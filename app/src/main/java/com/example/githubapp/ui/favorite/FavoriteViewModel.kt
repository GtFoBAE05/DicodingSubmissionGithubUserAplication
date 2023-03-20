package com.example.githubapp.ui.favorite

import androidx.lifecycle.ViewModel
import com.example.githubapp.data.UserRepository

class FavoriteViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun getFavUser() = userRepository.getFavUser()

}