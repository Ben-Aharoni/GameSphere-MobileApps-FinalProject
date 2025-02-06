package com.example.gamesphere_finalproject

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult

class LoginActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        database = FirebaseDatabase.getInstance().reference

        if (FirebaseAuth.getInstance().currentUser == null) {
            signIn()
        } else {
            saveUserToDatabase()
        }
    }

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract(),
    ) { res ->
        this.onSignInResult(res)
    }

    private fun signIn() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.PhoneBuilder().build(),
        )

        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setLogo(R.drawable.worldwide)
            .build()
        signInLauncher.launch(signInIntent)
    }

    private fun saveUserToDatabase() {
        val user = FirebaseAuth.getInstance().currentUser

        if (user != null) {
            val userId = user.uid
            val userData = mapOf(
                "userId" to userId,
                "email" to user.email,
                "username" to (user.displayName ?: user.email?.substringBefore("@")),
                "profilePicUrl" to (user.photoUrl?.toString() ?: "https://www.example.com/default-profile.png"),
                "favoriteGames" to mapOf<String, Boolean>(),  // 🔹 Empty Map to Store Favorites
                "favoriteEvents" to mapOf<String, Boolean>()  // 🔹 Empty Map to Store Favorites
            )

            database.child("users").child(userId).get().addOnSuccessListener { snapshot ->
                if (!snapshot.exists()) {
                    database.child("users").child(userId).setValue(userData)
                        .addOnSuccessListener {
                            transactToNextScreen()
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Failed to save user info", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    transactToNextScreen()
                }
            }.addOnFailureListener {
                Toast.makeText(this, "Error checking user data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun transactToNextScreen() {
        intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        if (result.resultCode == RESULT_OK) {
            saveUserToDatabase()
        } else {
            Toast.makeText(this, "Error: Failed logging in.", Toast.LENGTH_LONG).show()
            signIn()
        }
    }
}
