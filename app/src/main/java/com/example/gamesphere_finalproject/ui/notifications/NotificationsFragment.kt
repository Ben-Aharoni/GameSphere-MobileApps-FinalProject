package com.example.gamesphere_finalproject.ui.notifications

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gamesphere_finalproject.adapters.GameAdapter
import com.example.gamesphere_finalproject.databinding.FragmentNotificationsBinding
import com.example.gamesphere_finalproject.models.Game
import com.google.firebase.database.*

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!

    private lateinit var database: DatabaseReference
    private lateinit var gameAdapter: GameAdapter
    private val gameList = mutableListOf<Game>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)

        //  Run this ONCE, then REMOVE IT after successful upload!
        //FirebaseGameUploader.uploadGames()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize Firebase reference
        database = FirebaseDatabase.getInstance().getReference("games")

        // Set up RecyclerView
        gameAdapter = GameAdapter(gameList, requireContext())
        binding.mainRVList.layoutManager = LinearLayoutManager(requireContext())
        binding.mainRVList.adapter = gameAdapter

        // Fetch games from Firebase
        fetchGamesFromFirebase()
    }

    private fun fetchGamesFromFirebase() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                gameList.clear() // Clear old data
                for (gameSnapshot in snapshot.children) {
                    val game = gameSnapshot.getValue(Game::class.java)
                    game?.let { gameList.add(it) }
                }
                gameAdapter.notifyDataSetChanged() // Refresh RecyclerView
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("Data Error", "Failed to read value.", error.toException())            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
