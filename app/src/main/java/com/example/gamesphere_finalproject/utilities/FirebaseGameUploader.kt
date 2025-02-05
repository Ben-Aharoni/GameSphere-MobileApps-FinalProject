package com.example.gamesphere_finalproject.utilities

import com.example.gamesphere_finalproject.models.Game
import com.google.firebase.database.FirebaseDatabase

object FirebaseGameUploader {
    private val database = FirebaseDatabase.getInstance().getReference("games")

    fun uploadGames() {
        val games = listOf(
            Game(
                name = "Halo Infinite",
                genre = listOf("Genres: Action", "Shooter", "Sci-Fi"),
                poster = "https://upload.wikimedia.org/wikipedia/en/1/14/Halo_Infinite.png",
                overview = "A new chapter in the Halo saga, blending an engaging campaign with intense multiplayer action.",
                releaseDate = "15/12/2021",
                rating = 8.5f,
                isFavorite = false
            ),
            Game(
                name = "God of War Ragnarök",
                genre = listOf("Action", "Adventure", "Mythology"),
                poster = "https://upload.wikimedia.org/wikipedia/en/e/ee/God_of_War_Ragnar%C3%B6k_cover.jpg",
                overview = "Kratos and Atreus embark on a mythic journey through Norse realms as ancient prophecies come to life.",
                releaseDate = "09/11/2022",
                rating = 9.0f,
                isFavorite = false
            ),
            Game(
                name = "Elden Ring",
                genre = listOf("Action", "RPG", "Fantasy"),
                poster = "https://upload.wikimedia.org/wikipedia/en/b/b9/Elden_Ring_Box_art.jpg",
                overview = "Explore a vast and mystical world filled with challenging combat and hidden secrets.",
                releaseDate = "25/02/2022",
                rating = 9.5f,
                isFavorite = false
            ),
            Game(
                name = "Forza Horizon 5",
                genre = listOf("Racing", "Simulation", "Open World"),
                poster = "https://upload.wikimedia.org/wikipedia/en/8/86/Forza_Horizon_5_cover_art.jpg",
                overview = "An exhilarating open-world racing experience set in a vibrant version of Mexico.",
                releaseDate = "09/11/2021",
                rating = 9.2f,
                isFavorite = false
            ),
            Game(
                name = "The Last of Us Part II",
                genre = listOf("Action", "Adventure", "Survival"),
                poster = "https://upload.wikimedia.org/wikipedia/en/4/4f/TLOU_P2_Box_Art_2.png",
                overview = "A gripping story of survival and revenge in a post-apocalyptic world.",
                releaseDate = "19/06/2020",
                rating = 9.3f,
                isFavorite = false
            ),
            Game(
                name = "Assassin's Creed Odyssey",
                genre = listOf("Action", "RPG", "Adventure", "Historical"),
                poster = "https://upload.wikimedia.org/wikipedia/en/9/99/ACOdysseyCoverArt.png",
                overview = "An expansive open-world adventure set in ancient Greece.",
                releaseDate = "05/10/2018",
                rating = 8.3f,
                isFavorite = false
            ),
            Game(
                name = "FC 25",
                genre = listOf("Sports", "Football", "Simulation"),
                poster = "https://upload.wikimedia.org/wikipedia/en/0/09/EA_FC_25_Cover.jpg",
                overview = "The latest installment in the world's most popular football simulation.",
                releaseDate = "29/09/2024",
                rating = 8.7f,
                isFavorite = false
            ),
            Game(
                name = "Marvel's Spider-Man 2",
                genre = listOf("Action", "Adventure", "Superhero"),
                poster = "https://upload.wikimedia.org/wikipedia/en/0/0f/SpiderMan2PS5BoxArt.jpeg",
                overview = "Swing through New York City as both Peter Parker and Miles Morales.",
                releaseDate = "20/10/2023",
                rating = 9.4f,
                isFavorite = false
            ),
            Game(
                name = "Cyberpunk 2077",
                genre = listOf("Action", "RPG", "Sci-Fi"),
                poster = "https://upload.wikimedia.org/wikipedia/en/9/9f/Cyberpunk_2077_box_art.jpg",
                overview = "A sprawling open-world RPG set in the dystopian Night City.",
                releaseDate = "10/12/2020",
                rating = 7.5f,
                isFavorite = false
            ),
            Game(
                name = "The Witcher 3: Wild Hunt",
                genre = listOf("Action", "RPG", "Fantasy"),
                poster = "https://upload.wikimedia.org/wikipedia/en/0/0c/Witcher_3_cover_art.jpg",
                overview = "Embark on a dark fantasy adventure as Geralt of Rivia.",
                releaseDate = "19/05/2015",
                rating = 9.7f,
                isFavorite = false
            ),
            Game(
                name = "Red Dead Redemption 2",
                genre = listOf("Action", "Adventure", "Western"),
                poster = "https://upload.wikimedia.org/wikipedia/en/4/44/Red_Dead_Redemption_II.jpg",
                overview = "A sprawling open-world adventure set in the American frontier.",
                releaseDate = "26/10/2018",
                rating = 9.8f,
                isFavorite = false
            ),
            Game(
                name = "Death Stranding",
                genre = listOf("Action", "Adventure", "Sci-Fi"),
                poster = "https://upload.wikimedia.org/wikipedia/en/2/22/Death_Stranding.jpg",
                overview = "Embark on a journey across a post-apocalyptic America as Sam Bridges.",
                releaseDate = "08/11/2019",
                rating = 8.9f,
                isFavorite = false
            ),
            Game(
                name = "Ghost of Tsushima",
                genre = listOf("Action", "Adventure", "Historical"),
                poster = "https://upload.wikimedia.org/wikipedia/en/b/b6/Ghost_of_Tsushima.jpg",
                overview = "Jin Sakai embarks on a quest for vengeance in feudal Japan.",
                releaseDate = "17/07/2020",
                rating = 9.6f,
                isFavorite = false
            ),
            Game(
                name = "Demon's Souls",
                genre = listOf("Action", "RPG", "Fantasy"),
                poster = "https://upload.wikimedia.org/wikipedia/en/9/91/Demon%27s_Souls_Cover.jpg",
                overview = "Overview: Return to the dark and foreboding world of Boletaria in this remake of the classic action RPG, featuring enhanced visuals and gameplay.",
                releaseDate = "12/11/2020",
                rating = 9.6f,
                isFavorite = false
            ),
            Game(
                name = "Ratchet & Clank: Rift Apart",
                genre = listOf("Action", "Adventure", "Sci-Fi"),
                poster = "https://upload.wikimedia.org/wikipedia/en/a/a3/Ratchet_%26_Clank_-_Rift_Apart.png",
                overview = "Ratchet and Clank embark on a dimension-hopping adventure to save the multiverse from the evil Dr. Nefarious.",
                releaseDate = "11/06/2021",
                rating = 9.3f,
                isFavorite = false
            ),
            Game(
                name = "Returnal",
                genre = listOf("Action", "Adventure", "Sci-Fi"),
                poster = "https://upload.wikimedia.org/wikipedia/en/9/91/Returnal_cover_art.jpg",
                overview = "Crash-land on an alien planet and fight to survive in this roguelike shooter with a mysterious time loop.",
                releaseDate = "30/04/2021",
                rating = 8.7f,
                isFavorite = false
            ),
            Game(
                name = "Resident Evil Village",
                genre = listOf("Action", "Horror", "Survival"),
                poster = "https://upload.wikimedia.org/wikipedia/en/2/2c/Resident_Evil_Village.png",
                overview = "Ethan Winters faces new horrors in a mysterious village filled with monsters, cults, and dark secrets.",
                releaseDate = "07/05/2021",
                rating = 8.8f,
                isFavorite = false
            ),
            Game(
                name = "Hitman 3",
                genre = listOf("Action", "Stealth", "Assassination"),
                poster = "https://upload.wikimedia.org/wikipedia/en/4/4b/Hitman_3_Packart.jpg",
                overview = "Agent 47 returns for the final installment in the World of Assassination trilogy, featuring new locations, targets, and gameplay mechanics.",
                releaseDate = "20/01/2021",
                rating = 8.9f,
                isFavorite = false
            ),
            Game(
                name = "Marvel's Guardians of the Galaxy",
                genre = listOf("Action", "Adventure", "Superhero"),
                poster = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQVF-XOtGR6xrdNa3-B1Aejx1dXB8684fqP-g&s",
                overview = "Star-Lord and the Guardians of the Galaxy embark on an epic adventure to save the galaxy from cosmic threats and interstellar villains.",
                releaseDate = "26/10/2021",
                rating = 8.6f,
                isFavorite = false
            ),
            Game(
                name = "Assassin's Creed Origins",
                genre = listOf("Action", "RPG", "Adventure", "Historical"),
                poster = "https://upload.wikimedia.org/wikipedia/en/4/4a/Assassin%27s_Creed_Origins_Cover_Art.png",
                overview = "Uncover the origins of the Assassin Brotherhood in ancient Egypt, experiencing a vast open world filled with mysteries, quests, and legendary battles.",
                releaseDate = "27/10/2017",
                rating = 8.9f,
                isFavorite = false
            )

        )

        // Upload each game to Firebase
        for (game in games) {
            database.child(game.name).setValue(game)
        }
    }
}
