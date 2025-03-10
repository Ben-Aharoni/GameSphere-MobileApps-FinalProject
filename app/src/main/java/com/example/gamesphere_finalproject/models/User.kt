package com.example.gamesphere_finalproject.models

data class User(
    val userId: String = "",
    val username: String = "",
    val profilePicUrl: String = "",
    val favoriteGames: List<String> = emptyList(),
    val favoriteEvents: List<String> = emptyList(),
    var isCollapsed: Boolean = true
) {
    constructor() : this("", "", "", emptyList(), emptyList(), true)
}
