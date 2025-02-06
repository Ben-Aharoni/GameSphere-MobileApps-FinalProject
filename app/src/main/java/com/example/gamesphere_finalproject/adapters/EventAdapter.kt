package com.example.gamesphere_finalproject.adapters

import android.animation.ObjectAnimator
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gamesphere_finalproject.R
import com.example.gamesphere_finalproject.databinding.EventItemBinding
import com.example.gamesphere_finalproject.interfaces.EventCallback
import com.example.gamesphere_finalproject.models.Event
import com.example.gamesphere_finalproject.utilities.Constants
import com.example.gamesphere_finalproject.utilities.ImageLoader
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.time.format.DateTimeFormatter
import kotlin.math.max

class EventAdapter(private val events: List<Event>, private val context: Context) :
    RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    var eventCallback: EventCallback? = null
    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = EventItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding)
    }

    override fun getItemCount(): Int = events.size

    fun getItem(position: Int) = events[position]

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = getItem(position)

        // Fetch full event object if it's in favorites
        fetchFavoriteEvent(event.name) { fetchedEvent ->
            val displayEvent = fetchedEvent ?: event  // Use full object if found, else use local
            holder.bind(displayEvent)
        }
    }

    inner class EventViewHolder(val binding: EventItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(event: Event) {
            binding.eventLBLTitle.text = event.name
            binding.eventLBLReleaseDate.text = event.releaseDate
            binding.eventLBLGenres.text = event.genre.joinToString(", ")
            binding.eventLBLOverview.text = event.overview
            binding.eventLBLLocation.text = event.location
            ImageLoader.getInstance().loadImage(event.poster, binding.eventIMGPoster)

            // Fetch and update favorite state
            fetchFavoriteEvent(event.name) { favoriteEvent ->
                event.isFavorite = favoriteEvent != null
                updateFavoriteIcon(event.isFavorite)
            }

            // Expand/collapse animation
            binding.eventCVData.setOnClickListener {
                toggleExpandCollapse(event)
            }

            // Favorite button click listener
            binding.eventIMGFavorite.setOnClickListener {
                event.isFavorite = !event.isFavorite
                updateFavoriteState(event, event.isFavorite)
                updateFavoriteIcon(event.isFavorite)
                notifyItemChanged(adapterPosition)
                eventCallback?.favoriteButtonClicked(event, adapterPosition)
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
                animatorSet.add(ObjectAnimator.ofInt(binding.eventLBLOverview, "maxLines", binding.eventLBLOverview.lineCount)
                    .setDuration((max((binding.eventLBLOverview.lineCount - Constants.Data.OVERVIEW_MIN_LINES).toDouble(), 0.0) * 50L).toLong()))
                animatorSet.add(ObjectAnimator.ofInt(binding.eventLBLGenres, "maxLines", binding.eventLBLGenres.lineCount)
                    .setDuration((max((binding.eventLBLGenres.lineCount - Constants.Data.GENRES_MIN_LINES).toDouble(), 0.0) * 50L).toLong()))
                animatorSet.add(ObjectAnimator.ofInt(binding.eventLBLTitle, "maxLines", binding.eventLBLTitle.lineCount)
                    .setDuration((max((binding.eventLBLTitle.lineCount - Constants.Data.GENRES_MIN_LINES).toDouble(), 0.0) * 50L).toLong()))
                animatorSet.add(ObjectAnimator.ofInt(binding.eventLBLLocation, "maxLines", binding.eventLBLLocation.lineCount)
                    .setDuration((max((binding.eventLBLLocation.lineCount - Constants.Data.GENRES_MIN_LINES).toDouble(), 0.0) * 50L).toLong()))
            } else {
                animatorSet.add(ObjectAnimator.ofInt(binding.eventLBLOverview, "maxLines", Constants.Data.OVERVIEW_MIN_LINES)
                    .setDuration((max((binding.eventLBLOverview.lineCount - Constants.Data.OVERVIEW_MIN_LINES).toDouble(), 0.0) * 50L).toLong()))
                animatorSet.add(ObjectAnimator.ofInt(binding.eventLBLGenres, "maxLines", Constants.Data.GENRES_MIN_LINES)
                    .setDuration((max((binding.eventLBLGenres.lineCount - Constants.Data.GENRES_MIN_LINES).toDouble(), 0.0) * 50L).toLong()))
                animatorSet.add(ObjectAnimator.ofInt(binding.eventLBLTitle, "maxLines", Constants.Data.GENRES_MIN_LINES)
                    .setDuration((max((binding.eventLBLTitle.lineCount - Constants.Data.GENRES_MIN_LINES).toDouble(), 0.0) * 50L).toLong()))
                animatorSet.add(ObjectAnimator.ofInt(binding.eventLBLLocation, "maxLines", Constants.Data.GENRES_MIN_LINES)
                    .setDuration((max((binding.eventLBLLocation.lineCount - Constants.Data.GENRES_MIN_LINES).toDouble(), 0.0) * 50L).toLong()))
            }

            event.isCollapsed = !event.isCollapsed
            animatorSet.forEach { it.start() }
        }
    }

    // 🔹 Store or remove full event object in Firebase
    private fun updateFavoriteState(event: Event, isFavorite: Boolean) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val eventRef = database.child("users").child(userId).child("favoriteEvents").child(event.name)

        if (isFavorite) {
            eventRef.setValue(event) // ✅ Save full object
        } else {
            eventRef.removeValue() // ❌ Remove event object if unliked
        }
    }

    // 🔹 Fetch full event object from Firebase
    private fun fetchFavoriteEvent(eventName: String, callback: (Event?) -> Unit) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val eventRef = database.child("users").child(userId).child("favoriteEvents").child(eventName)

        eventRef.get().addOnSuccessListener { snapshot ->
            val event = snapshot.getValue(Event::class.java)
            callback(event)
        }.addOnFailureListener {
            callback(null)
        }
    }
}
