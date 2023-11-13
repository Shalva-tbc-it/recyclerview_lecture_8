package com.example.recyclerview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.Users
import com.example.recyclerview.databinding.RecyclerviewUserBinding

class UsersAdapter(val update : (Users, Int) -> Unit) : RecyclerView.Adapter<UsersAdapter.UsersViewHolder>() {

    private val usersList = ArrayList<Users>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        return UsersViewHolder(
            binding = RecyclerviewUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return usersList.size
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        val currentUser = usersList[position]
        holder.setUp(currentUser, position)
    }

    inner class UsersViewHolder(val binding: RecyclerviewUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun setUp(users: Users, position: Int) {
                binding.tvFirstNameValue.text = users.firstName
                binding.tvLastNameValue.text = users.lastName
                binding.tvEmailValue.text = users.email
                binding.btnDelete.setOnClickListener {
                    deleteList(position)
                }
                binding.btnUpdate.setOnClickListener {
                    update(users, position)
                }
            }
    }

    fun setData(userList: Users) {
        usersList.add(userList)
        notifyItemChanged(usersList.size)
    }

    private fun deleteList(position: Int) {
        usersList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, usersList.size)
    }

    fun updateUser(position: Int, updatedUser: Users) {
        usersList[position] = updatedUser
        notifyItemChanged(position)
    }

}