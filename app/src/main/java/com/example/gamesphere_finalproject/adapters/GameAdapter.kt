package com.example.gamesphere_finalproject.adapters

import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gamesphere_finalproject.R
import com.example.gamesphere_finalproject.databinding.GameItemBinding
import com.example.gamesphere_finalproject.interfaces.GameCallback
import com.example.gamesphere_finalproject.models.Game
import com.example.gamesphere_finalproject.utilities.Constants
import com.example.gamesphere_finalproject.utilities.ImageLoader
import java.time.format.DateTimeFormatter
import kotlin.math.max

class GameAdapter(private val games: List<Game>) :
    RecyclerView.Adapter<GameAdapter.GameViewHolder>() {

    var gameCallback: GameCallback? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val binding = GameItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return GameViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return games.size
    }

    fun getItem(position: Int) = games[position]

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        with(holder) {
            with(getItem(position)) {
                binding.gameLBLTitle.text = name
                binding.gameLBLReleaseDate.text =
                    releaseDate.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy")).toString()
                binding.gameLBLGenres.text = genre.joinToString(", ")
                binding.gameLBLOverview.text = overview
                binding.gameRBRating.rating = rating / 2
                ImageLoader.getInstance().loadImage(poster, binding.gameIMGPoster)
                if (isFavorite) binding.gameIMGFavorite.setImageResource(R.drawable.heart)
                else
                    binding.gameIMGFavorite.setImageResource(R.drawable.empty_heart)
                binding.gameCVData.setOnClickListener {
                    val animatorSet = ArrayList<ObjectAnimator>()
                    if (isCollapsed) {
                        animatorSet
                            .add(
                                ObjectAnimator
                                    .ofInt(
                                        binding.gameLBLOverview,
                                        "maxLines",
                                        binding.gameLBLOverview.lineCount
                                    ).setDuration(
                                        (max(
                                            (binding.gameLBLOverview.lineCount - Constants.Data.OVERVIEW_MIN_LINES).toDouble(),
                                            0.0
                                        ) * 50L).toLong()
                                    )
                            )
                        animatorSet
                            .add(
                                ObjectAnimator
                                    .ofInt(
                                        binding.gameLBLGenres,
                                        "maxLines",
                                        binding.gameLBLGenres.lineCount
                                    ).setDuration(
                                        (max(
                                            (binding.gameLBLGenres.lineCount - Constants.Data.GENRES_MIN_LINES).toDouble(),
                                            0.0
                                        ) * 50L).toLong()
                                    )
                            )

                        animatorSet
                            .add(
                                ObjectAnimator
                                    .ofInt(
                                        binding.gameLBLTitle,
                                        "maxLines",
                                        binding.gameLBLTitle.lineCount
                                    ).setDuration(
                                        (max(
                                            (binding.gameLBLTitle.lineCount - Constants.Data.GENRES_MIN_LINES).toDouble(),
                                            0.0
                                        ) * 50L).toLong()
                                    )
                            )

                    } else {
                        animatorSet
                            .add(
                                ObjectAnimator
                                    .ofInt(
                                        binding.gameLBLOverview,
                                        "maxLines",
                                        Constants.Data.OVERVIEW_MIN_LINES
                                    ).setDuration(
                                        (max(
                                            (binding.gameLBLOverview.lineCount - Constants.Data.OVERVIEW_MIN_LINES).toDouble(),
                                            0.0
                                        ) * 50L).toLong()
                                    )
                            )

                        animatorSet
                            .add(
                                ObjectAnimator
                                    .ofInt(
                                        binding.gameLBLGenres,
                                        "maxLines",
                                        Constants.Data.GENRES_MIN_LINES
                                    ).setDuration(
                                        (max(
                                            (binding.gameLBLGenres.lineCount - Constants.Data.GENRES_MIN_LINES).toDouble(),
                                            0.0
                                        ) * 50L).toLong()
                                    )
                            )

                        animatorSet
                            .add(
                                ObjectAnimator
                                    .ofInt(
                                        binding.gameLBLTitle,
                                        "maxLines",
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
                    animatorSet.forEach { obj: ObjectAnimator -> obj.start() }
                }
            }
        }
    }


    inner class GameViewHolder(val binding: GameItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.gameIMGFavorite.setOnClickListener {
                gameCallback?.favoriteButtonClicked(
                    getItem(position = adapterPosition),
                    adapterPosition
                )
            }
        }

    }
}