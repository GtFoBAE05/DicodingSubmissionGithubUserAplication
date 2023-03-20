package com.example.githubapp.ui.following

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubapp.data.UserRepository
import com.example.githubapp.data.remote.response.UserList
import com.example.githubapp.utils.Resource
import kotlinx.coroutines.launch

class FollowingViewModel(private val userRepository: UserRepository) : ViewModel() {

    private var _followingList = MutableLiveData<Resource<List<UserList>>>()
    val followingList: LiveData<Resource<List<UserList>>> = _followingList

    fun getFollowingList(username: String) {
        viewModelScope.launch {
            userRepository.getFollowingList(username).collect {
                _followingList.postValue(it)
            }
        }
    }

}