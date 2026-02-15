package com.example.chessapi1

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.chessapi1.databinding.ActivityMainBinding
import com.example.chessapi1.network.ChessComApiClient
import com.google.gson.Gson
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
                val stats = ChessComApiClient.api.getStats(username)

                val rank = stats.chess_rapid?.last?.rating ?: 0

                binding.cardPreview.visibility = View.VISIBLE
                binding.tvPreviewName.text = player.name ?: "No name"
                binding.tvPreviewUsername.text = "@${player.username}"
                binding.tvPreviewRank.text = "Rapid rating: $rank"

                binding.cardPreview.setOnClickListener {
                    val intent = Intent(this@MainActivity, PlayerDetailsActivity::class.java)
                    intent.putExtra("player", Gson().toJson(player))
                    intent.putExtra("rank", rank)
                    startActivity(intent)
                }

            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Player not found", Toast.LENGTH_SHORT).show()
                binding.cardPreview.visibility = View.GONE
            }
        }
    }
}