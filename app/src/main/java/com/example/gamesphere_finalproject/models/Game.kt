package com.example.gamesphere_finalproject.models

import java.time.LocalDate

data class Game private constructor(
    val poster: String,
    val genre: List<String>,
    val name: String,
    val overview: String,
    val releaseDate: LocalDate,
    val rating: Float,
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
        var rating: Float = 0.0F,
        var isFavorite: Boolean = false
    ) {
        fun poster(poster: String) = apply { this.poster = poster }
        fun genre(genre: List<String>) = apply { this.genre = genre }
        fun name(name: String) = apply { this.name = name }
        fun overview(overview: String) = apply { this.overview = overview }
        fun releaseDate(releaseDate: LocalDate) = apply { this.releaseDate = releaseDate }
        fun rating(rating: Float) = apply { this.rating = rating }
        fun isFavorite(isFavorite: Boolean) = apply { this.isFavorite = isFavorite }
        fun build() = Game(
            poster,
            genre,
            name,
            overview,
            releaseDate,
            rating,
            isFavorite
        )
    }
}