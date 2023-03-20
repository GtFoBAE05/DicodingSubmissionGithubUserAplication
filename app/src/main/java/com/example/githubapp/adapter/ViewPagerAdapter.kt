package com.example.githubapp.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.githubapp.ui.follower.FollowerFragment
import com.example.githubapp.ui.following.FollowingFragment

class ViewPagerAdapter (activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment : Fragment? = null

        when(position){
            0 -> fragment = FollowingFragment()
            1 -> fragment = FollowerFragment()

        }
        return fragment as Fragment
    }

}