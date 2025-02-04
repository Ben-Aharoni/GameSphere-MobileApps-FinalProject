package com.example.gamesphere_finalproject.interfaces

import com.example.gamesphere_finalproject.models.Event

interface EventCallback {
    fun favoriteButtonClicked(event: Event, position: Int)
}