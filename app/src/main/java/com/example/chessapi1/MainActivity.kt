package com.example.chessapi1

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.example.chessapi1.databinding.ActivityMainBinding
import com.example.chessapi1.model.Player
import com.example.chessapi1.network.ChessComApiClient
import com.google.gson.Gson
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val defaultPlayers = listOf(
        "magnuscarlsen",
        "hikaru",
        "alireza2003",
        "lachesisq",
        "wesley_so"
    )
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

        loadDefaultPlayers()

        binding.etUsername.addTextChangedListener {
            if (it.isNullOrBlank()) {
                loadDefaultPlayers()
            }
        }
    }

    private fun loadPlayer(username: String) {
        lifecycleScope.launch {
            try {
                val player = ChessComApiClient.api.getPlayer(username)
                val stats = ChessComApiClient.api.getStats(username)

                val rank = stats.chess_rapid?.last?.rating ?: 0

                binding.defaultContainer.visibility = View.GONE
                binding.cardPreview.visibility = View.VISIBLE

                binding.tvPreviewName.text = player.name ?: "No name"
                binding.tvPreviewUsername.text = "@${player.username}"
                binding.tvPreviewRank.text = "Rapid rating: $rank"

                binding.cardPreview.setOnClickListener {
                    openDetails(player, rank)
                }

            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Player not found", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadDefaultPlayers() {
        binding.defaultContainer.visibility = View.VISIBLE
        binding.cardPreview.visibility = View.GONE

        val cards = listOf(
            binding.card1,
            binding.card2,
            binding.card3,
            binding.card4,
            binding.card5
        )

        defaultPlayers.forEachIndexed { index, username ->
            lifecycleScope.launch {
                try {
                    val player = ChessComApiClient.api.getPlayer(username)
                    val stats = ChessComApiClient.api.getStats(username)
                    val rank = stats.chess_rapid?.last?.rating ?: 0

                    val card = cards[index]

                    card.tvPreviewName.text = player.name ?: "No name"
                    card.tvPreviewUsername.text = "@${player.username}"
                    card.tvPreviewRank.text = "Rapid rating: $rank"

                    card.root.setOnClickListener {
                        openDetails(player, rank)
                    }

                } catch (_: Exception) {}
            }
        }
    }

    private fun openDetails(player: Player, rank: Int) {
        val intent = Intent(this, PlayerDetailActivity::class.java)
        intent.putExtra("player", Gson().toJson(player))
        intent.putExtra("rank", rank)
        startActivity(intent)
    }
}