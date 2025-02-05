package com.example.gamesphere_finalproject.utilities

import com.example.gamesphere_finalproject.models.Event
import com.google.firebase.database.FirebaseDatabase
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object FirebaseUploader {
    private val database = FirebaseDatabase.getInstance().getReference("events")

    fun uploadEvents() {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

        val events = listOf(
            Event(
                name = "E3 Expo",
                genre = listOf("Gaming", "Tech", "Conventions"),
                poster = "https://i0.wp.com/mynintendonews.com/wp-content/uploads/2020/03/e3_logo.jpg?resize=930%2C620&ssl=1",
                overview = "The biggest annual gaming event showcasing upcoming games, tech, and innovations in the gaming industry.",
                releaseDate = LocalDate.parse("11/06/2025", formatter).toString(),
                location = "Los Angeles, USA",
                isFavorite = false
            ),
            Event(
                name = "Tokyo Game Show",
                genre = listOf("Gaming", "Japanese Games", "Esports"),
                poster = "https://cdn.cheapoguides.com/wp-content/uploads/sites/2/2023/09/R5_8957-1024x600.jpg",
                overview = "An exciting event showcasing the latest Japanese video game releases and upcoming gaming technology.",
                releaseDate = LocalDate.parse("25/09/2025", formatter).toString(),
                location = "Tokyo, Japan",
                isFavorite = false
            ),
            Event(
                name = "EA FC Pro Open",
                genre = listOf("Esports", "Football", "EA Sports"),
                poster = "https://coludi.com/wp-content/uploads/2024/11/bf3b9c7a-ab43-4d64-a3c2-725e45552b6f-profile_banner-480.png",
                overview = "The biggest EA Sports FC esports tournament where top players compete to become world champions.",
                releaseDate = LocalDate.parse("25/11/2024", formatter).toString(),
                location = "London, UK",
                isFavorite = false
            ),
            Event(
                name = "Fortnite Championship Series",
                genre = listOf("Esports", "Battle Royale", "Epic Games"),
                poster = "https://esportbet.com/wp-content/uploads/2023/02/fortnite-champs-series.jpg",
                overview = "Fortnite's premier esports competition, featuring the best players and massive prize pools.",
                releaseDate = LocalDate.parse("29/01/2025", formatter).toString(),
                location = "New York, USA",
                isFavorite = false
            ),
            Event(
                name = "Call of Duty League Championship",
                genre = listOf("Esports", "Shooter", "Activision"),
                poster = "https://esportsinsider.com/wp-content/uploads/2024/11/Call-of-Duty-League-2025-large.png",
                overview = "The biggest Call of Duty esports event where top teams battle for the championship title.",
                releaseDate = LocalDate.parse("30/01/2025", formatter).toString(),
                location = "Madrid, Spain",
                isFavorite = false
            ),
            Event(
                name = "League of Legends World Championship",
                genre = listOf("Esports", "MOBA", "Riot Games"),
                poster = "https://cdn1.epicgames.com/offer/24b9b5e323bc40eea252a10cdd3b2f10/EGS_LeagueofLegends_RiotGames_S2_1200x1600-905a96cea329205358868f5871393042",
                overview = "The ultimate League of Legends tournament featuring the best teams from around the world.",
                releaseDate = LocalDate.parse("25/10/2025", formatter).toString(),
                location = "Shanghai, China",
                isFavorite = false
            ),
            Event(
                name = "Dota 2 The International",
                genre = listOf("Esports", "MOBA", "Valve"),
                poster = "https://www.pcgamesn.com/wp-content/sites/pcgamesn/2021/07/dota-2-ti10-date-romania.jpg",
                overview = "The most prestigious Dota 2 tournament with multi-million dollar prize pools.",
                releaseDate = LocalDate.parse("11/09/2025", formatter).toString(),
                location = "Hamburg, Germany",
                isFavorite = false
            ),
            Event(
                name = "Rainbow Six Siege Invitational",
                genre = listOf("Esports", "Shooter", "Ubisoft"),
                poster = "https://i.ytimg.com/vi/CTmeTGm_aGk/maxresdefault.jpg",
                overview = "The world championship for Rainbow Six Siege featuring top teams from all regions.",
                releaseDate = LocalDate.parse("13/02/2025", formatter).toString(),
                location = "Boston, USA",
                isFavorite = false
            ),
            Event(
                name = "Overwatch League Grand Finals",
                genre = listOf("Esports", "Shooter", "Blizzard"),
                poster = "https://esportsinsider.com/wp-content/uploads/2023/11/Overwatch-League-logo-min-large.png",
                overview = "The grand finals of the Overwatch League where teams fight for the championship.",
                releaseDate = LocalDate.parse("31/01/2025", formatter).toString(),
                location = "Los Angeles, USA",
                isFavorite = false
            ),
            Event(
                name = "Valorant Champions",
                genre = listOf("Esports", "Shooter", "Riot Games"),
                poster = "https://i1.sndcdn.com/artworks-qIvOudzSXyGOuLz5-S1Hf8g-t500x500.jpg",
                overview = "The premier Valorant esports event bringing the best teams to compete for the world title.",
                releaseDate = LocalDate.parse("12/09/2025", formatter).toString(),
                location = "Paris, France",
                isFavorite = false
            )
        )

        // Upload each event to Firebase
        for (event in events) {
            database.child(event.name).setValue(event)
        }
    }
}
