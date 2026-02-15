package com.example.chessapi1.model

data class Player(
    val username: String,

    val name: String?,

    val avatar: String?,

    val country: String,

    val followers: Int,

    val status: String) {
}