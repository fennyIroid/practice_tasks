package com.example.practice_tasks.task3Ui

import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.example.practice_tasks.databinding.ItemUserBinding
import android.view.ViewGroup

class UserAdapter(
    private val users: MutableList<userModel>): RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    inner class UserViewHolder(val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindUser(user: userModel) {
            binding.txtName.text = user.name
            binding.txtAge.text = "Age: ${user.age}"
            binding.txtCity.text = "City: ${user.city}"
            binding.txtGender.text = "Gender: ${user.gender}"
            binding.txtHobbies.text = "Hobbies: ${user.hobbies.joinToString()}"
            binding.txtActive.text = if (user.isActive) "ACTIVE" else "INACTIVE"
            binding.ratingBar.rating = user.rating
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= UserViewHolder (
        ItemUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun getItemCount() = users.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bindUser(users[position])

    }

    fun addUser(user: userModel) {
        users.add(user)
        notifyItemInserted(users.size - 1)
    }
}