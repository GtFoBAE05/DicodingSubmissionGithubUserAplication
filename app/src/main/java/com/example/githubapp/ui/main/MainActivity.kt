package com.example.githubapp.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubapp.PreferencesModelFactory
import com.example.githubapp.R
import com.example.githubapp.ViewModelFactory
import com.example.githubapp.adapter.ListUserAdapter
import com.example.githubapp.data.SettingPreferences
import com.example.githubapp.data.UserRepository
import com.example.githubapp.data.remote.response.UserList
import com.example.githubapp.databinding.ActivityMainBinding
import com.example.githubapp.ui.favorite.FavoriteActivity
import com.example.githubapp.ui.nightmode.NightModeSetActivity
import com.example.githubapp.ui.nightmode.NightModeViewModel
import com.example.githubapp.utils.Resource

class MainActivity : AppCompatActivity() {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: ListUserAdapter

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.listUserRv.layoutManager = LinearLayoutManager(this)
        adapter = ListUserAdapter(mutableListOf())
        binding.listUserRv.adapter = adapter

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val mainViewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)

        val pref = SettingPreferences.getInstance(dataStore)
        val prefFactory = PreferencesModelFactory(pref)
        val prevViewModel = ViewModelProvider(this, prefFactory).get(NightModeViewModel::class.java)

        prevViewModel.getThemeSettings().observe(this) {
            setNightMode(it)
        }

        mainViewModel.userList.observe(this) {
            when (it) {
                is Resource.Loading -> setLoading(true)

                is Resource.Error -> {
                    setLoading(false)
                    Toast.makeText(this, it.error, Toast.LENGTH_LONG).show()
                }

                is Resource.Success -> {
                    setLoading(false)
                    setListUser(it.data)
                }
            }
        }

    }

    fun setListUser(data: List<UserList>) {
        adapter.addData(data)
    }

    fun setLoading(bool: Boolean) {
        binding.progressBar.visibility = if (bool) View.VISIBLE else View.GONE
    }

    fun setNightMode(status: Boolean) {
        if (status) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        val searchView = menu?.findItem(R.id.app_bar_search)?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.getListUser(query.toString())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.toString().isNotBlank()) viewModel.getListUser(newText.toString())
                return true
            }

        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.fav_user -> {
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
                return true
            }

            R.id.night_mode -> {
                val intent = Intent(this, NightModeSetActivity::class.java)
                startActivity(intent)
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }

}