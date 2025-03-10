package com.example.gamesphere_finalproject.ui.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gamesphere_finalproject.adapters.UserAdapter
import com.example.gamesphere_finalproject.databinding.FragmentUsersBinding
import com.example.gamesphere_finalproject.models.User
import com.google.firebase.database.*

class UsersFragment : Fragment() {

    private var _binding: FragmentUsersBinding? = null
    private val binding get() = _binding!!
    private lateinit var database: DatabaseReference
    private lateinit var userAdapter: UserAdapter
    private val usersList = mutableListOf<User>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUsersBinding.inflate(inflater, container, false)
        database = FirebaseDatabase.getInstance().reference

        setupRecyclerView()
        loadUsers()

        return binding.root
    }

    private fun setupRecyclerView() {
        binding.usersRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        userAdapter = UserAdapter(usersList)
        binding.usersRecyclerView.adapter = userAdapter
    }

    private fun loadUsers() {
        database.child("users").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                usersList.clear()

                for (userSnapshot in snapshot.children) {
                    val userId = userSnapshot.child("userId").getValue(String::class.java) ?: ""
                    val username = userSnapshot.child("username").getValue(String::class.java) ?: "Unknown User"
                    val profilePicUrl = userSnapshot.child("profilePicUrl").getValue(String::class.java) ?: ""

                    // ✅ Extract only the names of favorite games
                    val favoriteGames = userSnapshot.child("favoriteGames").children.mapNotNull { it.key }

                    // ✅ Extract only the names of favorite events
                    val favoriteEvents = userSnapshot.child("favoriteEvents").children.mapNotNull { it.key }

                    // ✅ Create a new user object with extracted data
                    val user = User(userId, username, profilePicUrl, favoriteGames, favoriteEvents)

                    usersList.add(user)
                }
                userAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
