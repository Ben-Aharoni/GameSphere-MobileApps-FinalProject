package com.example.gamesphere_finalproject.models

import java.time.LocalDate

data class Event private constructor(
    val poster: String,
    val genre: List<String>,
    val name: String,
    val overview: String,
    val releaseDate: LocalDate,
    val location: String,
    var isFavorite: Boolean,
    var isCollapsed: Boolean = true
) {

    fun toggleCollapse() = apply { this.isCollapsed = !this.isCollapsed }

    class Builder(
        var poster: String = "",
        var genre: List<String> = emptyList(),
        var name: String = "",
        var overview: String = "",
        var releaseDate: LocalDate = LocalDate.now(),
        var location: String = "",
        var isFavorite: Boolean = false
    ) {
        fun poster(poster: String) = apply { this.poster = poster }
        fun genre(genre: List<String>) = apply { this.genre = genre }
        fun name(name: String) = apply { this.name = name }
        fun overview(overview: String) = apply { this.overview = overview }
        fun releaseDate(releaseDate: LocalDate) = apply { this.releaseDate = releaseDate }
        fun location(location: String) = apply { this.location = location }
        fun isFavorite(isFavorite: Boolean) = apply { this.isFavorite = isFavorite }
        fun build() = Event(
            poster,
            genre,
            name,
            overview,
            releaseDate,
            location,
            isFavorite
        )
    }
}

