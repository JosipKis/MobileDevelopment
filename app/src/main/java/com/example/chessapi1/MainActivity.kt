package com.example.chessapi1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.chessapi1.databinding.ActivityMainBinding
import com.example.chessapi1.network.ChessComApiClient
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSearch.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            if (username.isNotEmpty()) {
                loadPlayer(username)
            }
        }
    }

    private fun loadPlayer(username: String) {
        lifecycleScope.launch {
            try {
                val player = ChessComApiClient.api.getPlayer(username)

                binding.tvResult.text = """
                    Username: ${player.username}
                    Name: ${player.name ?: "N/A"}
                    Country: ${player.country}
                    Followers: ${player.followers}
                    Status: ${player.status}
                """.trimIndent()

                player.avatar?.let {
                    binding.imgAvatar.load(it)
                }

            } catch (e: Exception) {
                binding.tvResult.text = "com.example.chessapi1.model.Player not found 😢"
                binding.imgAvatar.setImageDrawable(null)
            }
        }
    }
}