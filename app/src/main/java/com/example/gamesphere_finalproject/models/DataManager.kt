package com.example.gamesphere_finalproject.models

import java.time.LocalDate
import java.time.format.DateTimeFormatter

object DataManager {
    fun generateGameList(): List<Game> {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val games = mutableListOf<Game>()

        games.add(
            Game.Builder()
                .name("Halo Infinite")
                .genre(listOf("Genres: Action", "Shooter", "Sci-Fi"))
                .poster("https://upload.wikimedia.org/wikipedia/en/1/14/Halo_Infinite.png")
                .overview("Overview: A new chapter in the Halo saga, blending an engaging campaign with intense multiplayer action.")
                .rating(8.5f)
                .releaseDate(LocalDate.parse("15/12/2021", formatter))
                .isFavorite(false)
                .build()
        )

        games.add(
            Game.Builder()
                .name("God of War Ragnarök")
                .genre(listOf("Genres: Action", "Adventure", "Mythology"))
                .poster("https://upload.wikimedia.org/wikipedia/en/e/ee/God_of_War_Ragnar%C3%B6k_cover.jpg")
                .overview("Overview: Kratos and Atreus embark on a mythic journey through Norse realms as ancient prophecies come to life.")
                .rating(9.0f)
                .releaseDate(LocalDate.parse("09/11/2022", formatter))
                .isFavorite(false)
                .build()
        )

        games.add(
            Game.Builder()
                .name("Elden Ring")
                .genre(listOf("Genres: Action", "RPG", "Fantasy"))
                .poster("https://upload.wikimedia.org/wikipedia/en/thumb/b/b9/Elden_Ring_Box_art.jpg/220px-Elden_Ring_Box_art.jpg")
                .overview("Overview: Explore a vast and mystical world filled with challenging combat and hidden secrets.")
                .rating(9.5f)
                .releaseDate(LocalDate.parse("25/02/2022", formatter))
                .isFavorite(false)
                .build()
        )

        games.add(
            Game.Builder()
                .name("Forza Horizon 5")
                .genre(listOf("Genres: Racing", "Simulation", "Open World"))
                .poster("https://upload.wikimedia.org/wikipedia/en/8/86/Forza_Horizon_5_cover_art.jpg")
                .overview("Overview: An exhilarating open-world racing experience set in a vibrant version of Mexico.")
                .rating(9.2f)
                .releaseDate(LocalDate.parse("09/11/2021", formatter))
                .isFavorite(false)
                .build()
        )

        games.add(
            Game.Builder()
                .name("The Last of Us Part II")
                .genre(listOf("Genres: Action", "Adventure", "Survival"))
                .poster("https://upload.wikimedia.org/wikipedia/en/4/4f/TLOU_P2_Box_Art_2.png")
                .overview("Overview: A gripping story of survival and revenge in a post-apocalyptic world.")
                .rating(9.3f)
                .releaseDate(LocalDate.parse("19/06/2020", formatter))
                .isFavorite(false)
                .build()
        )

        games.add(
            Game.Builder()
                .name("Assassin's Creed Odyssey")
                .genre(listOf("Genres: Action", "RPG", "Adventure", "Historical"))
                .poster("https://upload.wikimedia.org/wikipedia/en/9/99/ACOdysseyCoverArt.png")
                .overview("Overview: An expansive open-world adventure set in ancient Greece, where you play as a mercenary navigating a turbulent era filled with political intrigue, mythological wonders, and epic battles.")
                .rating(8.3f)
                .releaseDate(LocalDate.parse("05/10/2018", formatter))
                .isFavorite(false)
                .build()
        )

        games.add(
            Game.Builder()
                .name("FC 25")
                .genre(listOf("Genres: Sports", "Football", "Simulation"))
                .poster("https://upload.wikimedia.org/wikipedia/en/0/09/EA_FC_25_Cover.jpg")
                .overview("Overview: The latest installment in the world's most popular football simulation, featuring improved gameplay, realistic physics, and immersive career modes.")
                .rating(8.7f)
                .releaseDate(LocalDate.parse("29/09/2024", formatter))
                .isFavorite(false)
                .build()
        )

        games.add(
            Game.Builder()
                .name("Marvel's Spider-Man 2")
                .genre(listOf("Genres: Action", "Adventure", "Superhero"))
                .poster("https://upload.wikimedia.org/wikipedia/en/0/0f/SpiderMan2PS5BoxArt.jpeg")
                .overview("Overview: Swing through New York City as both Peter Parker and Miles Morales in an action-packed superhero adventure with new villains and abilities.")
                .rating(9.4f)
                .releaseDate(LocalDate.parse("20/10/2023", formatter)) // Adjust release date if needed
                .isFavorite(false)
                .build()
        )

        games.add(
            Game.Builder()
                .name("Cyberpunk 2077")
                .genre(listOf("Genres: Action", "RPG", "Sci-Fi"))
                .poster("https://upload.wikimedia.org/wikipedia/en/9/9f/Cyberpunk_2077_box_art.jpg")
                .overview("Overview: A sprawling open-world RPG set in the dystopian Night City, where you play as a mercenary navigating a world of cybernetic enhancements, corporate intrigue, and urban decay.")
                .rating(7.5f)
                .releaseDate(LocalDate.parse("10/12/2020", formatter))
                .isFavorite(false)
                .build()
        )

        games.add(
            Game.Builder()
                .name("The Witcher 3: Wild Hunt")
                .genre(listOf("Genres: Action", "RPG", "Fantasy"))
                .poster("https://upload.wikimedia.org/wikipedia/en/0/0c/Witcher_3_cover_art.jpg")
                .overview("Overview: Embark on a dark fantasy adventure as Geralt of Rivia, a monster hunter for hire, in a world filled with political intrigue, mythical creatures, and moral dilemmas.")
                .rating(9.7f)
                .releaseDate(LocalDate.parse("19/05/2015", formatter))
                .isFavorite(false)
                .build()
        )

        games.add(
            Game.Builder()
                .name("Red Dead Redemption 2")
                .genre(listOf("Genres: Action", "Adventure", "Western"))
                .poster("https://upload.wikimedia.org/wikipedia/en/4/44/Red_Dead_Redemption_II.jpg")
                .overview("Overview: A sprawling open-world adventure set in the American frontier, where you play as Arthur Morgan, an outlaw navigating a world of gunslingers, outlaws, and lawmen.")
                .rating(9.8f)
                .releaseDate(LocalDate.parse("26/10/2018", formatter))
                .isFavorite(false)
                .build()
        )

        games.add(
            Game.Builder()
                .name("Death Stranding")
                .genre(listOf("Genres: Action", "Adventure", "Sci-Fi"))
                .poster("https://upload.wikimedia.org/wikipedia/en/2/22/Death_Stranding.jpg")
                .overview("Overview: Embark on a journey across a post-apocalyptic America as Sam Bridges, a courier tasked with reconnecting a fractured society.")
                .rating(8.9f)
                .releaseDate(LocalDate.parse("08/11/2019", formatter))
                .isFavorite(false)
                .build()
        )

        games.add(
            Game.Builder()
                .name("Horizon Forbidden West")
                .genre(listOf("Genres: Action", "Adventure", "Sci-Fi"))
                .poster("https://upload.wikimedia.org/wikipedia/en/6/69/Horizon_Forbidden_West_cover_art.jpg")
                .overview("Overview: Aloy returns in a new adventure set in a post-apocalyptic world filled with robotic creatures, ancient mysteries, and dangerous factions.")
                .rating(9.1f)
                .releaseDate(LocalDate.parse("18/02/2022", formatter))
                .isFavorite(false)
                .build()
        )

        games.add(
            Game.Builder()
                .name("Ghost of Tsushima")
                .genre(listOf("Genres: Action", "Adventure", "Historical"))
                .poster("https://upload.wikimedia.org/wikipedia/en/b/b6/Ghost_of_Tsushima.jpg")
                .overview("Overview: Jin Sakai embarks on a quest for vengeance in feudal Japan, battling Mongol invaders and embracing the way of the Ghost.")
                .rating(9.6f)
                .releaseDate(LocalDate.parse("17/07/2020", formatter))
                .isFavorite(false)
                .build()
        )

        games.add(
            Game.Builder()
                .name("Demon's Souls")
                .genre(listOf("Genres: Action", "RPG", "Fantasy"))
                .poster("https://upload.wikimedia.org/wikipedia/en/9/91/Demon%27s_Souls_Cover.jpg")
                .overview("Overview: Return to the dark and foreboding world of Boletaria in this remake of the classic action RPG, featuring enhanced visuals and gameplay.")
                .rating(9.2f)
                .releaseDate(LocalDate.parse("12/11/2020", formatter))
                .isFavorite(false)
                .build()
        )

        games.add(
            Game.Builder()
                .name("Ratchet & Clank: Rift Apart")
                .genre(listOf("Genres: Action", "Adventure", "Sci-Fi"))
                .poster("https://upload.wikimedia.org/wikipedia/en/a/a3/Ratchet_%26_Clank_-_Rift_Apart.png")
                .overview("Overview: Ratchet and Clank embark on a dimension-hopping adventure to save the multiverse from the evil Dr. Nefarious.")
                .rating(9.3f)
                .releaseDate(LocalDate.parse("11/06/2021", formatter))
                .isFavorite(false)
                .build()
        )

        games.add(
            Game.Builder()
                .name("Returnal")
                .genre(listOf("Genres: Action", "Adventure", "Sci-Fi"))
                .poster("https://upload.wikimedia.org/wikipedia/en/9/91/Returnal_cover_art.jpg")
                .overview("Overview: Crash-land on an alien planet and fight to survive in this roguelike shooter with a mysterious time loop.")
                .rating(8.7f)
                .releaseDate(LocalDate.parse("30/04/2021", formatter))
                .isFavorite(false)
                .build()
        )

        games.add(
            Game.Builder()
                .name("Resident Evil Village")
                .genre(listOf("Genres: Action", "Horror", "Survival"))
                .poster("https://upload.wikimedia.org/wikipedia/en/2/2c/Resident_Evil_Village.png")
                .overview("Overview: Ethan Winters faces new horrors in a mysterious village filled with monsters, cults, and dark secrets.")
                .rating(8.8f)
                .releaseDate(LocalDate.parse("07/05/2021", formatter))
                .isFavorite(false)
                .build()
        )

        games.add(
            Game.Builder()
                .name("Hitman 3")
                .genre(listOf("Genres: Action", "Stealth", "Assassination"))
                .poster("https://upload.wikimedia.org/wikipedia/en/4/4b/Hitman_3_Packart.jpg")
                .overview("Overview: Agent 47 returns for the final installment in the World of Assassination trilogy, featuring new locations, targets, and gameplay mechanics.")
                .rating(8.9f)
                .releaseDate(LocalDate.parse("20/01/2021", formatter))
                .isFavorite(false)
                .build()
        )

        games.add(
            Game.Builder()
                .name("Marvel's Guardians of the Galaxy")
                .genre(listOf("Genres: Action", "Adventure", "Superhero"))
                .poster("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQVF-XOtGR6xrdNa3-B1Aejx1dXB8684fqP-g&s")
                .overview("Overview: Star-Lord and the Guardians of the Galaxy embark on an epic adventure to save the galaxy from cosmic threats and interstellar villains.")
                .rating(8.6f)
                .releaseDate(LocalDate.parse("26/10/2021", formatter))
                .isFavorite(false)
                .build()
        )

        games.add(
            Game.Builder()
                .name("Assassin's Creed Origins")
                .genre(listOf("Genres: Action", "RPG", "Adventure", "Historical"))
                .poster("https://upload.wikimedia.org/wikipedia/en/4/4a/Assassin%27s_Creed_Origins_Cover_Art.png")
                .overview("Overview: Uncover the origins of the Assassin Brotherhood in ancient Egypt, experiencing a vast open world filled with mysteries, quests, and legendary battles.")
                .rating(8.9f)
                .releaseDate(LocalDate.parse("27/10/2017", formatter))
                .isFavorite(false)
                .build()
        )
        return games
    }

}