package com.example.gamesphere_finalproject.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gamesphere_finalproject.R
import com.example.gamesphere_finalproject.databinding.GameItemBinding
import com.example.gamesphere_finalproject.databinding.EventItemBinding
import com.example.gamesphere_finalproject.models.FavoriteItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class FavoriteAdapter(
    private val favorites: MutableList<FavoriteItem>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_GAME = 1
        private const val TYPE_EVENT = 2
    }

    override fun getItemViewType(position: Int): Int {
        return when (favorites[position]) {
            is FavoriteItem.GameItem -> TYPE_GAME
            is FavoriteItem.EventItem -> TYPE_EVENT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_GAME) {
            val binding = GameItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            GameViewHolder(binding)
        } else {
            val binding = EventItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            EventViewHolder(binding)
        }
    }

    override fun getItemCount(): Int = favorites.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = favorites[position]) {
            is FavoriteItem.GameItem -> (holder as GameViewHolder).bind(item.game)
            is FavoriteItem.EventItem -> (holder as EventViewHolder).bind(item.event)
        }
    }

    inner class GameViewHolder(private val binding: GameItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(game: com.example.gamesphere_finalproject.models.Game) {
            binding.gameLBLTitle.text = game.name
            binding.gameLBLOverview.text = game.overview
            if (game.isFavorite) binding.gameIMGFavorite.setImageResource(R.drawable.heart)
            else binding.gameIMGFavorite.setImageResource(R.drawable.empty_heart)

            binding.gameIMGFavorite.setOnClickListener { removeFavorite(game.name, "favoriteGames") }
        }
    }

    inner class EventViewHolder(private val binding: EventItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(event: com.example.gamesphere_finalproject.models.Event) {
            binding.eventLBLTitle.text = event.name
            binding.eventLBLOverview.text = event.overview
            if (event.isFavorite) binding.eventIMGFavorite.setImageResource(R.drawable.heart)
            else binding.eventIMGFavorite.setImageResource(R.drawable.empty_heart)

            binding.eventIMGFavorite.setOnClickListener { removeFavorite(event.name, "favoriteEvents") }
        }
    }

    private fun removeFavorite(itemId: String, category: String) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val dbRef = FirebaseDatabase.getInstance().reference.child("users").child(userId).child(category).child(itemId)

        dbRef.removeValue().addOnSuccessListener {
            favorites.removeIf {
                when (it) {
                    is FavoriteItem.GameItem -> it.game.name == itemId
                    is FavoriteItem.EventItem -> it.event.name == itemId
                }
            }
            notifyDataSetChanged()
        }
    }
}
