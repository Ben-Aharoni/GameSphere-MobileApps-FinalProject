package com.example.gamesphere_finalproject.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gamesphere_finalproject.adapters.EventAdapter
import com.example.gamesphere_finalproject.databinding.FragmentDashboardBinding
import com.example.gamesphere_finalproject.models.Event
import com.example.gamesphere_finalproject.utilities.FirebaseUploader
import com.google.firebase.database.*

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private lateinit var database: DatabaseReference
    private lateinit var eventAdapter: EventAdapter
    private val eventList = mutableListOf<Event>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

        //  Run this ONCE, then REMOVE IT after successful upload!
       // FirebaseUploader.uploadEvents()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize Firebase reference
        database = FirebaseDatabase.getInstance().getReference("events")

        // Set up RecyclerView
        eventAdapter = EventAdapter(eventList, requireContext())
        binding.mainRVList.layoutManager = LinearLayoutManager(requireContext())
        binding.mainRVList.adapter = eventAdapter

        // Fetch events from Firebase (instead of using EventDataManager)
        fetchEventsFromFirebase()
    }

    private fun fetchEventsFromFirebase() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                eventList.clear() // Clear old data to avoid duplicates

                for (eventSnapshot in snapshot.children) {
                    val event = eventSnapshot.getValue(Event::class.java)
                    event?.let { eventList.add(it) }
                }

                eventAdapter.notifyDataSetChanged() // Refresh UI
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
