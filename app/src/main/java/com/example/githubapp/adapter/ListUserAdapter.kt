package com.example.githubapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubapp.ui.profile.ProfileActivity
import com.example.githubapp.data.remote.response.UserList
import com.example.githubapp.databinding.ListUserLayoutBinding

class ListUserAdapter(var data : MutableList<UserList>) : RecyclerView.Adapter<ListUserAdapter.listUserViewHolder>() {
    class listUserViewHolder(binding: ListUserLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        val img : ImageView = binding.githubProfilePicture
        val username : TextView = binding.githubUsernameTv
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): listUserViewHolder {
        val binding = ListUserLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return listUserViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: listUserViewHolder, position: Int) {
        Glide.with(holder.itemView).load(data[position].avatarUrl).into(holder.img)
        holder.username.text = data[position].login

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, ProfileActivity::class.java)
            intent.putExtra("login", data[position].login)
            holder.itemView.context.startActivity(intent)

        }
    }

    fun addData(newData:List<UserList> ){
        data.clear()
        newData.forEach {
            data.add(it)
        }
        notifyDataSetChanged()
    }

}