package com.example.gamesphere_finalproject.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.gamesphere_finalproject.LoginActivity
import com.example.gamesphere_finalproject.adapters.FavoriteAdapter
import com.example.gamesphere_finalproject.databinding.FragmentHomeBinding
import com.example.gamesphere_finalproject.models.FavoriteItem
import com.example.gamesphere_finalproject.models.Game
import com.example.gamesphere_finalproject.models.Event
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    private val favoriteItems = mutableListOf<FavoriteItem>()
    private lateinit var favoriteAdapter: FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        setupRecyclerView()
        loadUserProfile()
        loadFavorites()

        binding.signOutButton.setOnClickListener {
            signOutUser()
        }

        return binding.root
    }

    private fun setupRecyclerView() {
        binding.favoritesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        favoriteAdapter = FavoriteAdapter(favoriteItems)
        binding.favoritesRecyclerView.adapter = favoriteAdapter
    }

    private fun loadUserProfile() {
        val userId = auth.currentUser?.uid ?: return

        database.child("users").child(userId).get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                val username = snapshot.child("username").value as? String ?: "Unknown User"
                val profilePicUrl = snapshot.child("profilePicUrl").value as? String ?: ""

                // Set username
                binding.username.text = username

                // Load profile image
                if (profilePicUrl.isNotEmpty()) {
                    Glide.with(this)
                        .load(profilePicUrl)
                        .into(binding.profileImage)
                }
            }
        }.addOnFailureListener {
            binding.username.text = "Failed to load profile"
        }
    }

    private fun loadFavorites() {
        val userId = auth.currentUser?.uid ?: return

        favoriteItems.clear()

        // Load favorite games (full objects)
        database.child("users").child(userId).child("favoriteGames").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (gameSnapshot in snapshot.children) {
                    val game = gameSnapshot.getValue(Game::class.java) // Get full game object
                    if (game != null) {
                        favoriteItems.add(FavoriteItem.GameItem(game)) // Store full object
                    }
                }
                favoriteAdapter.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {}
        })

        // Load favorite events (full objects)
        database.child("users").child(userId).child("favoriteEvents").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (eventSnapshot in snapshot.children) {
                    val event = eventSnapshot.getValue(Event::class.java) // Get full event object
                    if (event != null) {
                        favoriteItems.add(FavoriteItem.EventItem(event)) // Store full object
                    }
                }
                favoriteAdapter.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }




    private fun signOutUser() {
        auth.signOut()
        startActivity(Intent(requireContext(), LoginActivity::class.java))
        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
