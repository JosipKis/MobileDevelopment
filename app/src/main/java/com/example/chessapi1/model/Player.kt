package com.example.chessapi1.model

data class Player(
    val username: String,

    val name: String?,

    val avatar: String?,

    val country: String,

    val followers: Int,

    val status: String
)

data class PlayerStats(
    val chess_rapid: ChessRapid? //vrsta saha, rapid je 'klasicni' traje izmedu 10 minuta i 60 minuta
)

data class ChessRapid(
    val last: LastRating?
)

data class LastRating(
    val rating: Int
)