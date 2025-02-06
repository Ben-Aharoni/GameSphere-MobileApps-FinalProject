package com.example.gamesphere_finalproject.models

data class User(
    var userId: String = "",
    var username: String = "",
    var profilePicUrl: String = ""
) {
    // ✅ No-argument constructor required for Firebase
    constructor() : this("", "", "")
}
