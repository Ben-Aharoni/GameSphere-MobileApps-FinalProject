package com.example.gamesphere_finalproject.adapters

import android.animation.ObjectAnimator
import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gamesphere_finalproject.R
import com.example.gamesphere_finalproject.databinding.EventItemBinding
import com.example.gamesphere_finalproject.interfaces.EventCallback
import com.example.gamesphere_finalproject.models.Event
import com.example.gamesphere_finalproject.utilities.Constants
import com.example.gamesphere_finalproject.utilities.ImageLoader
import java.time.format.DateTimeFormatter
import kotlin.math.max

class EventAdapter(private val events: List<Event>, private val context: Context) :
    RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    var eventCallback: EventCallback? = null

    // Initialize SharedPreferences for favorite storage
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("event_favorites_prefs", Context.MODE_PRIVATE)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = EventItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding)
    }

    override fun getItemCount(): Int = events.size

    fun getItem(position: Int) = events[position]

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        with(holder) {
            with(getItem(position)) {
                binding.eventLBLTitle.text = name
                binding.eventLBLReleaseDate.text = releaseDate.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy"))
                binding.eventLBLGenres.text = genre.joinToString(", ")
                binding.eventLBLOverview.text = overview
                binding.eventLBLLocation.text = location
                ImageLoader.getInstance().loadImage(poster, binding.eventIMGPoster)

                // Load favorite state from SharedPreferences
                isFavorite = sharedPreferences.getBoolean(name, false)
                updateFavoriteIcon(isFavorite)

                // Expand/collapse animation
                binding.eventCVData.setOnClickListener {
                    val animatorSet = ArrayList<ObjectAnimator>()

                    if (isCollapsed) {
                        animatorSet.add(
                            ObjectAnimator.ofInt(
                                binding.eventLBLOverview, "maxLines",
                                binding.eventLBLOverview.lineCount
                            ).setDuration(
                                (max(
                                    (binding.eventLBLOverview.lineCount - Constants.Data.OVERVIEW_MIN_LINES).toDouble(),
                                    0.0
                                ) * 50L).toLong()
                            )
                        )
                        animatorSet.add(
                            ObjectAnimator.ofInt(
                                binding.eventLBLGenres, "maxLines",
                                binding.eventLBLGenres.lineCount
                            ).setDuration(
                                (max(
                                    (binding.eventLBLGenres.lineCount - Constants.Data.GENRES_MIN_LINES).toDouble(),
                                    0.0
                                ) * 50L).toLong()
                            )
                        )
                        animatorSet.add(
                            ObjectAnimator.ofInt(
                                binding.eventLBLTitle, "maxLines",
                                binding.eventLBLTitle.lineCount
                            ).setDuration(
                                (max(
                                    (binding.eventLBLTitle.lineCount - Constants.Data.GENRES_MIN_LINES).toDouble(),
                                    0.0
                                ) * 50L).toLong()
                            )
                        )
                        animatorSet.add(
                            ObjectAnimator.ofInt(
                                binding.eventLBLLocation, "maxLines",
                                binding.eventLBLLocation.lineCount
                            ).setDuration(
                                (max(
                                    (binding.eventLBLLocation.lineCount - Constants.Data.GENRES_MIN_LINES).toDouble(),
                                    0.0
                                ) * 50L).toLong()
                            )
                        )

                    } else {
                        animatorSet.add(
                            ObjectAnimator.ofInt(
                                binding.eventLBLOverview, "maxLines",
                                Constants.Data.OVERVIEW_MIN_LINES
                            ).setDuration(
                                (max(
                                    (binding.eventLBLOverview.lineCount - Constants.Data.OVERVIEW_MIN_LINES).toDouble(),
                                    0.0
                                ) * 50L).toLong()
                            )
                        )
                        animatorSet.add(
                            ObjectAnimator.ofInt(
                                binding.eventLBLGenres, "maxLines",
                                Constants.Data.GENRES_MIN_LINES
                            ).setDuration(
                                (max(
                                    (binding.eventLBLGenres.lineCount - Constants.Data.GENRES_MIN_LINES).toDouble(),
                                    0.0
                                ) * 50L).toLong()
                            )
                        )
                        animatorSet.add(
                            ObjectAnimator.ofInt(
                                binding.eventLBLTitle, "maxLines",
                                Constants.Data.GENRES_MIN_LINES
                            ).setDuration(
                                (max(
                                    (binding.eventLBLTitle.lineCount - Constants.Data.GENRES_MIN_LINES).toDouble(),
                                    0.0
                                ) * 50L).toLong()
                            )
                        )
                        animatorSet.add(
                            ObjectAnimator.ofInt(
                                binding.eventLBLLocation, "maxLines",
                                Constants.Data.GENRES_MIN_LINES
                            ).setDuration(
                                (max(
                                    (binding.eventLBLLocation.lineCount - Constants.Data.GENRES_MIN_LINES).toDouble(),
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

    inner class EventViewHolder(val binding: EventItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.eventIMGFavorite.setOnClickListener {
                val event = getItem(adapterPosition)
                event.isFavorite = !event.isFavorite // Toggle favorite state

                // Save favorite state in SharedPreferences
                saveFavoriteState(event.name, event.isFavorite)

                // Update UI
                updateFavoriteIcon(event.isFavorite)
                notifyItemChanged(adapterPosition)

                // Call callback if needed
                eventCallback?.favoriteButtonClicked(event, adapterPosition)
            }
        }
    }

    // Helper function to update the favorite icon
    private fun EventViewHolder.updateFavoriteIcon(isFavorite: Boolean) {
        binding.eventIMGFavorite.setImageResource(
            if (isFavorite) R.drawable.heart else R.drawable.empty_heart
        )
    }

    // Function to save favorite state in SharedPreferences
    private fun saveFavoriteState(eventName: String, isFavorite: Boolean) {
        sharedPreferences.edit().putBoolean(eventName, isFavorite).apply()
    }
}
