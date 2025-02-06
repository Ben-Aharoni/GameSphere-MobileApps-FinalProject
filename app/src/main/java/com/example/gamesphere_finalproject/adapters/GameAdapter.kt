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
        with(holder) {
            with(getItem(position)) {
                binding.gameLBLTitle.text = name
                binding.gameLBLReleaseDate.text = releaseDate.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy"))
                binding.gameLBLGenres.text = genre.joinToString(", ")
                binding.gameLBLOverview.text = overview
                binding.gameRBRating.rating = rating / 2
                ImageLoader.getInstance().loadImage(poster, binding.gameIMGPoster)

                // 🔹 Fetch favorite state from Firebase
                fetchFavoriteState(name) { isFav ->
                    isFavorite = isFav
                    updateFavoriteIcon(isFavorite)
                }

                // 🔹 Expand/collapse animation
                binding.gameCVData.setOnClickListener {
                    val animatorSet = ArrayList<ObjectAnimator>()

                    if (isCollapsed) {
                        animatorSet.add(
                            ObjectAnimator.ofInt(
                                binding.gameLBLOverview, "maxLines",
                                binding.gameLBLOverview.lineCount
                            ).setDuration(
                                (max(
                                    (binding.gameLBLOverview.lineCount - Constants.Data.OVERVIEW_MIN_LINES).toDouble(),
                                    0.0
                                ) * 50L).toLong()
                            )
                        )
                        animatorSet.add(
                            ObjectAnimator.ofInt(
                                binding.gameLBLGenres, "maxLines",
                                binding.gameLBLGenres.lineCount
                            ).setDuration(
                                (max(
                                    (binding.gameLBLGenres.lineCount - Constants.Data.GENRES_MIN_LINES).toDouble(),
                                    0.0
                                ) * 50L).toLong()
                            )
                        )
                        animatorSet.add(
                            ObjectAnimator.ofInt(
                                binding.gameLBLTitle, "maxLines",
                                binding.gameLBLTitle.lineCount
                            ).setDuration(
                                (max(
                                    (binding.gameLBLTitle.lineCount - Constants.Data.GENRES_MIN_LINES).toDouble(),
                                    0.0
                                ) * 50L).toLong()
                            )
                        )

                    } else {
                        animatorSet.add(
                            ObjectAnimator.ofInt(
                                binding.gameLBLOverview, "maxLines",
                                Constants.Data.OVERVIEW_MIN_LINES
                            ).setDuration(
                                (max(
                                    (binding.gameLBLOverview.lineCount - Constants.Data.OVERVIEW_MIN_LINES).toDouble(),
                                    0.0
                                ) * 50L).toLong()
                            )
                        )
                        animatorSet.add(
                            ObjectAnimator.ofInt(
                                binding.gameLBLGenres, "maxLines",
                                Constants.Data.GENRES_MIN_LINES
                            ).setDuration(
                                (max(
                                    (binding.gameLBLGenres.lineCount - Constants.Data.GENRES_MIN_LINES).toDouble(),
                                    0.0
                                ) * 50L).toLong()
                            )
                        )
                        animatorSet.add(
                            ObjectAnimator.ofInt(
                                binding.gameLBLTitle, "maxLines",
                                Constants.Data.GENRES_MIN_LINES
                            ).setDuration(
                                (max(
                                    (binding.gameLBLTitle.lineCount - Constants.Data.GENRES_MIN_LINES).toDouble(),
                                    0.0
                                ) * 50L).toLong()
                            )
                        )
                    }

                    toggleCollapse()
                    animatorSet.forEach { it.start() }
                }
            }
        }
    }

    inner class GameViewHolder(val binding: GameItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.gameIMGFavorite.setOnClickListener {
                val game = getItem(adapterPosition)
                game.isFavorite = !game.isFavorite // Toggle favorite state

                // 🔹 Save favorite state in Firebase
                updateFavoriteState(game.name, game.isFavorite)

                // 🔹 Update UI
                updateFavoriteIcon(game.isFavorite)
                notifyItemChanged(adapterPosition)

                // 🔹 Call callback if needed
                gameCallback?.favoriteButtonClicked(game, adapterPosition)
            }
        }
    }

    // 🔹 Helper function to update the favorite icon
    private fun GameViewHolder.updateFavoriteIcon(isFavorite: Boolean) {
        binding.gameIMGFavorite.setImageResource(
            if (isFavorite) R.drawable.heart else R.drawable.empty_heart
        )
    }

    // 🔹 Function to save favorite state in Firebase
    private fun updateFavoriteState(gameName: String, isFavorite: Boolean) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val gameRef = database.child("users").child(userId).child("favoriteGames").child(gameName)

        if (isFavorite) {
            gameRef.setValue(true) // ✅ Add to Firebase
        } else {
            gameRef.removeValue() // ✅ Remove from Firebase
        }
    }

    // 🔹 Function to fetch favorite state from Firebase
    private fun fetchFavoriteState(gameName: String, callback: (Boolean) -> Unit) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val gameRef = database.child("users").child(userId).child("favoriteGames").child(gameName)

        gameRef.get().addOnSuccessListener { snapshot ->
            callback(snapshot.exists()) // ✅ Returns true if game exists in Firebase
        }.addOnFailureListener {
            callback(false) // ✅ If there's an error, return false
        }
    }
}
