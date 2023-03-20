package com.example.githubapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubapp.data.UserRepository
import com.example.githubapp.data.remote.response.UserList
import com.example.githubapp.utils.Resource
import kotlinx.coroutines.launch

class MainViewModel constructor(private val userRepository: UserRepository) : ViewModel() {

    private var _userList = MutableLiveData<Resource<List<UserList>>>()
    val userList: LiveData<Resource<List<UserList>>> = _userList

    init {
        getListUser("arif")
    }

    fun getListUser(username: String) {
        viewModelScope.launch {
            userRepository.getAllUser(username).collect {
                _userList.postValue(it)
            }
        }
    }

}