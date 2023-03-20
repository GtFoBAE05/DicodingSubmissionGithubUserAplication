package com.example.githubapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubapp.data.SettingPreferences
import com.example.githubapp.ui.nightmode.NightModeViewModel

class PreferencesModelFactory(private val settingPref:SettingPreferences):ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NightModeViewModel::class.java)) {
            return NightModeViewModel(settingPref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}