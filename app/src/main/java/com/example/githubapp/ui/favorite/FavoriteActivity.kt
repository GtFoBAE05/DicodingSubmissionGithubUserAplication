package com.example.githubapp.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapp.R
import com.example.githubapp.ViewModelFactory
import com.example.githubapp.adapter.ListFavUserAdapter
import com.example.githubapp.databinding.ActivityFavoriteBinding
import com.example.githubapp.ui.nightmode.NightModeSetActivity

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.favRv.layoutManager = LinearLayoutManager(this)

        val factory = ViewModelFactory.getInstance(this)
        val favViewModel = ViewModelProvider(this, factory).get(FavoriteViewModel::class.java)

        favViewModel.getFavUser().observe(this) {
            val adapter = ListFavUserAdapter(it)
            binding.favRv.adapter = adapter
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        menu?.findItem(R.id.app_bar_search)?.isVisible = false
        menu?.findItem(R.id.fav_user)?.isVisible = false
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.night_mode -> {
                val intent = Intent(this, NightModeSetActivity::class.java)
                startActivity(intent)
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }

}