package com.example.gamesphere_finalproject.models

data class Game(
    var name: String = "",
    var genre: List<String> = emptyList(),
    var poster: String = "",
    var overview: String = "",
    var releaseDate: String = "",
    var rating: Float = 0.0f,
    var isFavorite: Boolean = false,
    var isCollapsed: Boolean = true
) {
    // 🔹 Required no-argument constructor for Firebase
    constructor() : this("", emptyList(), "", "", "", 0.0f, false)

    // 🔹 Toggle expand/collapse state
    fun toggleCollapse() = apply { this.isCollapsed = !this.isCollapsed }
}
