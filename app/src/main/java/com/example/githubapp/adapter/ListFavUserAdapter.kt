package com.example.githubapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubapp.data.local.entity.FavUserEntity
import com.example.githubapp.databinding.ListUserLayoutBinding
import com.example.githubapp.ui.profile.ProfileActivity


class ListFavUserAdapter (val data : List<FavUserEntity>) : RecyclerView.Adapter<ListFavUserAdapter.listFavUserViewHolder>() {
    class listFavUserViewHolder(binding: ListUserLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val img: ImageView = binding.githubProfilePicture
        val username: TextView = binding.githubUsernameTv
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): listFavUserViewHolder {
        val binding =
            ListUserLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return listFavUserViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: listFavUserViewHolder, position: Int) {
        Glide.with(holder.itemView).load(data[position].avatar_url).into(holder.img)
        holder.username.text = data[position].username

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, ProfileActivity::class.java)
            intent.putExtra("login", data[position].username)
            holder.itemView.context.startActivity(intent)

        }

    }

}