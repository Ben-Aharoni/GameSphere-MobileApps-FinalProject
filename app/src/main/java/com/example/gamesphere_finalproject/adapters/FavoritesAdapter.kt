package com.example.gamesphere_finalproject.adapters

import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gamesphere_finalproject.R
import com.example.gamesphere_finalproject.databinding.GameItemBinding
import com.example.gamesphere_finalproject.databinding.EventItemBinding
import com.example.gamesphere_finalproject.models.FavoriteItem
import com.example.gamesphere_finalproject.models.Game
import com.example.gamesphere_finalproject.models.Event
import com.example.gamesphere_finalproject.utilities.ImageLoader
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlin.math.max

class FavoriteAdapter(private val favoriteItems: MutableList<FavoriteItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference
    private val userId = FirebaseAuth.getInstance().currentUser?.uid

    override fun getItemViewType(position: Int): Int {
        return when (favoriteItems[position]) {
            is FavoriteItem.GameItem -> 0
            is FavoriteItem.EventItem -> 1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> GameViewHolder(
                GameItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            else -> EventViewHolder(
                EventItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemCount(): Int = favoriteItems.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = favoriteItems[position]) {
            is FavoriteItem.GameItem -> (holder as GameViewHolder).bind(item.game)
            is FavoriteItem.EventItem -> (holder as EventViewHolder).bind(item.event)
        }
    }

    // 🔹 Game ViewHolder
    inner class GameViewHolder(private val binding: GameItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(game: Game) {
            binding.gameLBLTitle.text = game.name
            binding.gameLBLGenres.text = game.genre.joinToString(", ")
            binding.gameLBLOverview.text = game.overview
            binding.gameLBLReleaseDate.text = game.releaseDate
            binding.gameRBRating.rating = game.rating / 2  // ✅ Set correct rating
            ImageLoader.getInstance()
                .loadImage(game.poster, binding.gameIMGPoster)  // ✅ Load poster

            // ✅ Set the heart icon correctly based on isFavorite
            updateFavoriteIcon(game.isFavorite)

            binding.gameIMGFavorite.setOnClickListener {
                toggleFavorite(game)
            }

            binding.gameCVData.setOnClickListener { toggleExpandCollapse(game) }
        }

        private fun toggleFavorite(game: Game) {
            game.isFavorite = !game.isFavorite
            updateFavoriteIcon(game.isFavorite)

            userId?.let { uid ->
                val gameRef =
                    database.child("users").child(uid).child("favoriteGames").child(game.name)

                if (game.isFavorite) {
                    gameRef.setValue(game) // ✅ Save full game object
                } else {
                    gameRef.removeValue().addOnSuccessListener {
                        // ✅ Remove from list and update RecyclerView
                        val position =
                            favoriteItems.indexOfFirst { it is FavoriteItem.GameItem && it.game.name == game.name }
                        if (position != -1) {
                            favoriteItems.removeAt(position)
                            notifyItemRemoved(position)
                        }
                    }
                }
            }
        }


        private fun updateFavoriteIcon(isFavorite: Boolean) {
            binding.gameIMGFavorite.setImageResource(
                if (isFavorite) R.drawable.heart else R.drawable.empty_heart
            )
        }

        private fun toggleExpandCollapse(game: Game) {
            val animatorSet = ArrayList<ObjectAnimator>()

            if (game.isCollapsed) {
                animatorSet.add(
                    ObjectAnimator.ofInt(
                        binding.gameLBLOverview,
                        "maxLines",
                        binding.gameLBLOverview.lineCount
                    )
                        .setDuration(
                            (max(
                                (binding.gameLBLOverview.lineCount - 3).toDouble(),
                                0.0
                            ) * 50L).toLong()
                        )
                )
            } else {
                animatorSet.add(
                    ObjectAnimator.ofInt(binding.gameLBLOverview, "maxLines", 3)
                        .setDuration(
                            (max(
                                (binding.gameLBLOverview.lineCount - 3).toDouble(),
                                0.0
                            ) * 50L).toLong()
                        )
                )
            }

            game.isCollapsed = !game.isCollapsed
            animatorSet.forEach { it.start() }
        }
    }

    // 🔹 Event ViewHolder
    inner class EventViewHolder(private val binding: EventItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(event: Event) {
            binding.eventLBLTitle.text = event.name
            binding.eventLBLGenres.text = event.genre.joinToString(", ")
            binding.eventLBLOverview.text = event.overview
            binding.eventLBLReleaseDate.text = event.releaseDate
            binding.eventLBLLocation.text = event.location
            ImageLoader.getInstance()
                .loadImage(event.poster, binding.eventIMGPoster)  // ✅ Load poster

            // ✅ Set the heart icon correctly based on isFavorite
            updateFavoriteIcon(event.isFavorite)

            binding.eventIMGFavorite.setOnClickListener {
                toggleFavorite(event)
            }

            binding.eventCVData.setOnClickListener { toggleExpandCollapse(event) }
        }

        private fun toggleFavorite(event: Event) {
            event.isFavorite = !event.isFavorite
            updateFavoriteIcon(event.isFavorite)

            userId?.let { uid ->
                val eventRef =
                    database.child("users").child(uid).child("favoriteEvents").child(event.name)

                if (event.isFavorite) {
                    eventRef.setValue(event) // ✅ Save full event object
                } else {
                    eventRef.removeValue().addOnSuccessListener {
                        // ✅ Remove from list and update RecyclerView
                        val position =
                            favoriteItems.indexOfFirst { it is FavoriteItem.EventItem && it.event.name == event.name }
                        if (position != -1) {
                            favoriteItems.removeAt(position)
                            notifyItemRemoved(position)
                        }
                    }
                }
            }
        }


        private fun updateFavoriteIcon(isFavorite: Boolean) {
            binding.eventIMGFavorite.setImageResource(
                if (isFavorite) R.drawable.heart else R.drawable.empty_heart
            )
        }

        private fun toggleExpandCollapse(event: Event) {
            val animatorSet = ArrayList<ObjectAnimator>()

            if (event.isCollapsed) {
                animatorSet.add(
                    ObjectAnimator.ofInt(
                        binding.eventLBLOverview,
                        "maxLines",
                        binding.eventLBLOverview.lineCount
                    )
                        .setDuration(
                            (max(
                                (binding.eventLBLOverview.lineCount - 3).toDouble(),
                                0.0
                            ) * 50L).toLong()
                        )
                )
            } else {
                animatorSet.add(
                    ObjectAnimator.ofInt(binding.eventLBLOverview, "maxLines", 3)
                        .setDuration(
                            (max(
                                (binding.eventLBLOverview.lineCount - 3).toDouble(),
                                0.0
                            ) * 50L).toLong()
                        )
                )
            }

            event.isCollapsed = !event.isCollapsed
            animatorSet.forEach { it.start() }
        }
    }
}
