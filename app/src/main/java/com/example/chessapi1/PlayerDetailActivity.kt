package com.example.chessapi1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.chessapi1.databinding.ActivityPlayerDetailsBinding
import com.example.chessapi1.model.Player
import com.google.gson.Gson

class PlayerDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlayerDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }

        val playerJson = intent.getStringExtra("player")
        val rank = intent.getIntExtra("rank", 0)

        val player = Gson().fromJson(playerJson, Player::class.java)

        binding.tvName.text = "Name: ${player.name ?: "N/A"}"
        binding.tvUsername.text = "Username: ${player.username}"
        binding.tvRank.text = "Rapid rating: $rank"
        binding.tvCountry.text = "Location: ${player.location}"
        binding.tvFollowers.text = "Followers: ${player.followers}"
        binding.tvStatus.text = "Status: ${player.status}"

        player.avatar?.let {
            binding.imgAvatar.load(it)
        }
    }
}