package com.example.gamesphere_finalproject.adapters

import android.animation.ObjectAnimator
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gamesphere_finalproject.R
import com.example.gamesphere_finalproject.databinding.GameItemBinding
import com.example.gamesphere_finalproject.interfaces.GameCallback
import com.example.gamesphere_finalproject.models.Game
import com.example.gamesphere_finalproject.utilities.Constants
import com.example.gamesphere_finalproject.utilities.ImageLoader
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.time.format.DateTimeFormatter
import kotlin.math.max

class GameAdapter(private val games: List<Game>, private val context: Context) :
    RecyclerView.Adapter<GameAdapter.GameViewHolder>() {

    var gameCallback: GameCallback? = null
    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val binding = GameItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GameViewHolder(binding)
    }

    override fun getItemCount(): Int = games.size

    fun getItem(position: Int) = games[position]

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game = getItem(position)

        // Fetch full game object if it's in favorites
        fetchFavoriteGame(game.name) { fetchedGame ->
            val displayGame = fetchedGame ?: game  // Use full object if found, else use local
            holder.bind(displayGame)
        }
    }

    inner class GameViewHolder(val binding: GameItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(game: Game) {
            binding.gameLBLTitle.text = game.name
            binding.gameLBLReleaseDate.text = game.releaseDate
            binding.gameLBLGenres.text = game.genre.joinToString(", ")
            binding.gameLBLOverview.text = game.overview
            binding.gameRBRating.rating = game.rating / 2
            ImageLoader.getInstance().loadImage(game.poster, binding.gameIMGPoster)

            // Fetch and update favorite state
            fetchFavoriteGame(game.name) { favoriteGame ->
                game.isFavorite = favoriteGame != null
                updateFavoriteIcon(game.isFavorite)
            }

            // Expand/collapse animation
            binding.gameCVData.setOnClickListener {
                toggleExpandCollapse(game)
            }

            // Favorite button click listener
            binding.gameIMGFavorite.setOnClickListener {
                game.isFavorite = !game.isFavorite
                updateFavoriteState(game, game.isFavorite)
                updateFavoriteIcon(game.isFavorite)
                notifyItemChanged(adapterPosition)
                gameCallback?.favoriteButtonClicked(game, adapterPosition)
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
                animatorSet.add(ObjectAnimator.ofInt(binding.gameLBLOverview, "maxLines", binding.gameLBLOverview.lineCount)
                    .setDuration((max((binding.gameLBLOverview.lineCount - Constants.Data.OVERVIEW_MIN_LINES).toDouble(), 0.0) * 50L).toLong()))
                animatorSet.add(ObjectAnimator.ofInt(binding.gameLBLGenres, "maxLines", binding.gameLBLGenres.lineCount)
                    .setDuration((max((binding.gameLBLGenres.lineCount - Constants.Data.GENRES_MIN_LINES).toDouble(), 0.0) * 50L).toLong()))
                animatorSet.add(ObjectAnimator.ofInt(binding.gameLBLTitle, "maxLines", binding.gameLBLTitle.lineCount)
                    .setDuration((max((binding.gameLBLTitle.lineCount - Constants.Data.GENRES_MIN_LINES).toDouble(), 0.0) * 50L).toLong()))
            } else {
                animatorSet.add(ObjectAnimator.ofInt(binding.gameLBLOverview, "maxLines", Constants.Data.OVERVIEW_MIN_LINES)
                    .setDuration((max((binding.gameLBLOverview.lineCount - Constants.Data.OVERVIEW_MIN_LINES).toDouble(), 0.0) * 50L).toLong()))
                animatorSet.add(ObjectAnimator.ofInt(binding.gameLBLGenres, "maxLines", Constants.Data.GENRES_MIN_LINES)
                    .setDuration((max((binding.gameLBLGenres.lineCount - Constants.Data.GENRES_MIN_LINES).toDouble(), 0.0) * 50L).toLong()))
                animatorSet.add(ObjectAnimator.ofInt(binding.gameLBLTitle, "maxLines", Constants.Data.GENRES_MIN_LINES)
                    .setDuration((max((binding.gameLBLTitle.lineCount - Constants.Data.GENRES_MIN_LINES).toDouble(), 0.0) * 50L).toLong()))
            }

            game.isCollapsed = !game.isCollapsed
            animatorSet.forEach { it.start() }
        }
    }

    // 🔹 Store or remove full game object in Firebase
    private fun updateFavoriteState(game: Game, isFavorite: Boolean) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val gameRef = database.child("users").child(userId).child("favoriteGames").child(game.name)

        if (isFavorite) {
            gameRef.setValue(game) // ✅ Save full object
        } else {
            gameRef.removeValue() // ❌ Remove game object if unliked
        }
    }

    // 🔹 Fetch full game object from Firebase
    private fun fetchFavoriteGame(gameName: String, callback: (Game?) -> Unit) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val gameRef = database.child("users").child(userId).child("favoriteGames").child(gameName)

        gameRef.get().addOnSuccessListener { snapshot ->
            val game = snapshot.getValue(Game::class.java)
            callback(game)
        }.addOnFailureListener {
            callback(null)
        }
    }
}
