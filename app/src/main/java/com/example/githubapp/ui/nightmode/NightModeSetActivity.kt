package com.example.githubapp.ui.nightmode

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.githubapp.PreferencesModelFactory
import com.example.githubapp.data.SettingPreferences
import com.example.githubapp.databinding.ActivityNightModeSetBinding

class NightModeSetActivity : AppCompatActivity() {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    private lateinit var binding: ActivityNightModeSetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNightModeSetBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val pref = SettingPreferences.getInstance(dataStore)
        val factory = PreferencesModelFactory(pref)
        val prevViewModel = ViewModelProvider(this, factory).get(NightModeViewModel::class.java)

        prevViewModel.getThemeSettings().observe(this) {
            setNightMode(it)
        }

        binding.lightSwitch.setOnCheckedChangeListener { _, isChecked ->
            prevViewModel.saveThemeSetting(isChecked)
        }

    }

    fun setNightMode(status: Boolean) {
        if (status) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            binding.lightSwitch.isChecked = true
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            binding.lightSwitch.isChecked = false
        }
    }

}