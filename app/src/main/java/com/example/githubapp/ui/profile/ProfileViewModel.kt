package com.example.githubapp.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubapp.data.UserRepository
import com.example.githubapp.data.local.entity.FavUserEntity
import com.example.githubapp.data.remote.response.UserDetailResponse
import com.example.githubapp.utils.Resource
import kotlinx.coroutines.launch

class ProfileViewModel(private val userRepository: UserRepository) : ViewModel() {

    private var _userDetail = MutableLiveData<Resource<UserDetailResponse>>()
    val userDetail: LiveData<Resource<UserDetailResponse>> = _userDetail

    fun getDetailUser(username: String) {
        viewModelScope.launch {
            userRepository.getDetailUser(username).collect {
                _userDetail.postValue(it)
            }
        }
    }

    fun getFavByUsername(username: String) = userRepository.getFavUserByUsername(username)

    fun addFavUser(favUserEntity: FavUserEntity) = viewModelScope.launch {
        userRepository.addFavUser(favUserEntity)
    }

    fun deleteFavUser(favUserEntity: FavUserEntity) = viewModelScope.launch {
        userRepository.deleteFavUser(favUserEntity)
    }

}