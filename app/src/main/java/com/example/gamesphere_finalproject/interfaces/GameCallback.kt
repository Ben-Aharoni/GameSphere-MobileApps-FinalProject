package com.example.gamesphere_finalproject.interfaces

import com.example.gamesphere_finalproject.models.Game

interface GameCallback {
    fun favoriteButtonClicked(game: Game, position: Int)
}