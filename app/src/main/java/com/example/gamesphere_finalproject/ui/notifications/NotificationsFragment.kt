package com.example.gamesphere_finalproject.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gamesphere_finalproject.adapters.GameAdapter
import com.example.gamesphere_finalproject.databinding.FragmentNotificationsBinding
import com.example.gamesphere_finalproject.models.DataManager

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up the RecyclerView with a LinearLayoutManager
        binding.mainRVList.layoutManager = LinearLayoutManager(requireContext())

        // Retrieve the game list from DataManager in the models package
        val gameList = DataManager.generateGameList()

        // Initialize the GameAdapter with the game list
        val gameAdapter = GameAdapter(gameList)

        // Set the adapter to the RecyclerView
        binding.mainRVList.adapter = gameAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
