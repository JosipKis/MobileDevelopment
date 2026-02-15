package com.example.chessapi1.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chessapi1.R

class PlayerAdapter(
    private var players: MutableList<String>,
    private val onClick: (String) -> Unit
) : RecyclerView.Adapter<PlayerAdapter.PlayerVH>() {

    fun update(newList: List<String>) {
        players.clear()
        players.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerVH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.player_preview, parent, false)
        return PlayerVH(view)
    }

    override fun getItemCount() = players.size

    override fun onBindViewHolder(holder: PlayerVH, position: Int) {
        holder.bind(players[position])
    }

    inner class PlayerVH(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(username: String) {
            itemView.setOnClickListener {
                onClick(username)
            }
        }
    }
}