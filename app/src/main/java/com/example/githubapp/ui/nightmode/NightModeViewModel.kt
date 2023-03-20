package com.example.githubapp.ui.nightmode

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.githubapp.data.SettingPreferences
import kotlinx.coroutines.launch

class NightModeViewModel(private val settingPref: SettingPreferences) : ViewModel() {

    fun getThemeSettings(): LiveData<Boolean> = settingPref.getThemeSetting().asLiveData()

    fun saveThemeSetting(status: Boolean) {
        viewModelScope.launch {
            settingPref.saveThemeSetting(status)
        }
    }

}