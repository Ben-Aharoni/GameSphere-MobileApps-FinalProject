package com.example.gamesphere_finalproject.models

data class Event(
    var name: String = "",
    var location: String = "",
    var releaseDate: String = "",
    var genre: List<String> = emptyList(),
    var overview: String = "",
    var poster: String = "",
    var isFavorite: Boolean = false,
    var isCollapsed: Boolean = true
) {
    // 🔹 Required no-argument constructor for Firebase
    constructor() : this("", "", "", emptyList(), "", "", false, true)

    // 🔹 Toggle expand/collapse state
    fun toggleCollapse() = apply { this.isCollapsed = !this.isCollapsed }
}
