package com.example.gamesphere_finalproject.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gamesphere_finalproject.databinding.UserItemBinding
import com.example.gamesphere_finalproject.models.User
import com.example.gamesphere_finalproject.utilities.ImageLoader

class UserAdapter(private val users: List<User>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(users[position])
    }

    inner class UserViewHolder(private val binding: UserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            binding.userLBLUsername.text = user.username
            ImageLoader.getInstance().loadImage(user.profilePicUrl, binding.userIMGProfile)

            // ✅ Format favorite games (each on a new line)
            val favoriteGamesText = if (user.favoriteGames.isNotEmpty()) {
                if (user.isCollapsed) {
                    "🎮 Games:\n" + user.favoriteGames.take(2).joinToString("\n")  // ✅ Show only 2 in collapsed state
                } else {
                    "🎮 Games:\n" + user.favoriteGames.joinToString("\n") // ✅ Show all when expanded
                }
            } else {
                "🎮 No favorite games"
            }

            // ✅ Format favorite events (each on a new line)
            val favoriteEventsText = if (user.favoriteEvents.isNotEmpty()) {
                if (user.isCollapsed) {
                    "🎟️ Events:\n" + user.favoriteEvents.take(2).joinToString("\n")  // ✅ Show only 2 in collapsed state
                } else {
                    "🎟️ Events:\n" + user.favoriteEvents.joinToString("\n") // ✅ Show all when expanded
                }
            } else {
                "🎟️ No favorite events"
            }

            binding.userLBLFavorites.text = "$favoriteGamesText\n\n$favoriteEventsText"

            // ✅ Handle expand/collapse on click
            binding.root.setOnClickListener {
                user.isCollapsed = !user.isCollapsed // Toggle state
                notifyItemChanged(adapterPosition)  // Refresh the item
            }
        }
    }
}
