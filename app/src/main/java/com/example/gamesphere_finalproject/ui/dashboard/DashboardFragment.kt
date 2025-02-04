package com.example.gamesphere_finalproject.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gamesphere_finalproject.adapters.EventAdapter
import com.example.gamesphere_finalproject.databinding.FragmentDashboardBinding
import com.example.gamesphere_finalproject.models.EventDataManager

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up the RecyclerView with a LinearLayoutManager
        binding.mainRVList.layoutManager = LinearLayoutManager(requireContext())

        // Retrieve the event list from EventDataManager in the models package
        val eventList = EventDataManager.generateEventList()

        // Initialize the EventAdapter with the event list
        val eventAdapter = EventAdapter(eventList, requireContext())

        // Set the adapter to the RecyclerView
        binding.mainRVList.adapter = eventAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}