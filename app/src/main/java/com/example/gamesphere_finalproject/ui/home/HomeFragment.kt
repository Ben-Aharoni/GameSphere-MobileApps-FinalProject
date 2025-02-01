package com.example.gamesphere_finalproject.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.gamesphere_finalproject.LoginActivity
import com.example.gamesphere_finalproject.databinding.FragmentHomeBinding
import com.firebase.ui.auth.AuthUI

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Example: observe a text LiveData from your ViewModel.
        homeViewModel.text.observe(viewLifecycleOwner) { text ->
            binding.textHome.text = text
        }

        // Set up the sign-out button click listener.
        binding.signOutButton.setOnClickListener {
            signOutUser()
        }

        return root
    }

    private fun signOutUser() {
        AuthUI.getInstance()
            .signOut(requireContext())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Successfully signed out, navigate to LoginActivity.
                    val intent = Intent(requireContext(), LoginActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                } else {
                    Toast.makeText(requireContext(), "Sign out failed", Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
