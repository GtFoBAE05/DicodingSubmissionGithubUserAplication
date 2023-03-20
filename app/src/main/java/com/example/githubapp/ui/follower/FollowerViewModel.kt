package com.example.githubapp.ui.follower


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubapp.data.UserRepository
import com.example.githubapp.data.remote.response.UserList
import com.example.githubapp.utils.Resource
import kotlinx.coroutines.launch

class FollowerViewModel(private val userRepository: UserRepository) : ViewModel() {

    private var _followerList = MutableLiveData<Resource<List<UserList>>>()
    val followerList: LiveData<Resource<List<UserList>>> = _followerList

    fun getFollowerList(username: String) {
        viewModelScope.launch {
            userRepository.getFollowerList(username).collect {
                _followerList.postValue(it)
            }
        }
    }

}