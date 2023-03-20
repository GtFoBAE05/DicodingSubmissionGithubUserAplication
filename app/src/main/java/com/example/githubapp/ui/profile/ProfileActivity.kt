package com.example.githubapp.ui.profile

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.githubapp.R
import com.example.githubapp.ViewModelFactory
import com.example.githubapp.adapter.ViewPagerAdapter
import com.example.githubapp.data.local.entity.FavUserEntity
import com.example.githubapp.data.remote.response.UserDetailResponse
import com.example.githubapp.databinding.ActivityProfileBinding
import com.example.githubapp.utils.Resource
import com.google.android.material.tabs.TabLayoutMediator

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val url = intent.getStringExtra("login")

        val profileViewModelFactory = ViewModelFactory.getInstance(this)
        profileViewModel =
            ViewModelProvider(this, profileViewModelFactory).get(ProfileViewModel::class.java)

        val viewPagerAdapter = ViewPagerAdapter(this)
        binding.viewPager.adapter = viewPagerAdapter

        binding.viewPager.scrollTo(0, 0)

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, pos ->
            tab.text = if (pos == 0) {
                "Following"
            } else {
                "follower"
            }
        }.attach()

        profileViewModel.getDetailUser(url.toString())

        profileViewModel.userDetail.observe(this) {
            when (it) {
                is Resource.Loading -> setLoading(false)

                is Resource.Error -> {
                    setLoading(false)
                    Toast.makeText(this, it.error, Toast.LENGTH_LONG).show()
                }

                is Resource.Success -> {
                    setLoading(false)
                    setFab(it.data)
                    setUserData(it.data)
                }
            }
        }

    }

    fun setUserData(data: UserDetailResponse) {
        Glide.with(this@ProfileActivity).load(data.avatarUrl).into(binding.imageView)
        binding.name.text = data.name
        binding.name.text = data.login
        binding.followerTv.text = "${data.followers} followers"
        binding.followingTv.text = "${data.following} following"
        binding.locationTv.text = data.location
        binding.repoTv.text = "Public repo: ${data.publicRepos} "
    }

    fun setLoading(bool: Boolean) {
        binding.profileProgressBar.visibility = if (bool) View.VISIBLE else View.GONE
    }

    fun setFab(data: UserDetailResponse) {
        profileViewModel.getFavByUsername(data.login.toString()).observe(this) {
            if (it == null) {
                binding.favFab.setImageDrawable(getDrawable(R.drawable.baseline_favorite_border_24))
                binding.favFab.setOnClickListener {
                    addFav(data)
                }
            } else {
                binding.favFab.setImageDrawable(getDrawable(R.drawable.baseline_favorite_24))
                binding.favFab.setOnClickListener {
                    deleteFav(data)
                }
            }
        }
    }

    fun addFav(data: UserDetailResponse) {
        val favUserEntity = FavUserEntity(
            data.login.toString(),
            data.avatarUrl,
            data.url
        )
        profileViewModel.addFavUser(favUserEntity)
    }

    fun deleteFav(data: UserDetailResponse) {
        val favUserEntity = FavUserEntity(
            data.login.toString(),
            data.avatarUrl,
            data.url
        )
        profileViewModel.deleteFavUser(favUserEntity)
    }

}