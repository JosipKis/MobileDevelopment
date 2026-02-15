package com.example.chessapi1

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import coil.load
import com.example.chessapi1.databinding.ActivityPlayerDetailsBinding
import com.example.chessapi1.model.Player
import com.google.gson.Gson

class PlayerDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlayerDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val playerJson = intent.getStringExtra("player")
        val rank = intent.getIntExtra("rank", 0)

        val player = Gson().fromJson(playerJson, Player::class.java)

        binding.tvName.text = "Name: ${player.name ?: "N/A"}"
        binding.tvUsername.text = "Username: ${player.username}"
        binding.tvRank.text = "Rapid rating: $rank"
        binding.tvCountry.text = "Country: ${player.country}"
        binding.tvFollowers.text = "Followers: ${player.followers}"
        binding.tvStatus.text = "Status: ${player.status}"

        player.avatar?.let {
            binding.imgAvatar.load(it)
        }
    }
}