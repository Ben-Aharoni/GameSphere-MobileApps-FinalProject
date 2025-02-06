package com.example.gamesphere_finalproject.models

sealed class FavoriteItem {
    data class GameItem(val game: Game) : FavoriteItem()
    data class EventItem(val event: Event) : FavoriteItem()
}
