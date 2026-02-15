package com.example.chessapi1.network

import com.example.chessapi1.model.Player
import com.example.chessapi1.model.PlayerStats
import retrofit2.http.GET
import retrofit2.http.Path

interface ChessApi {

    @GET("player/{username}")
    suspend fun getPlayer(
        @Path("username") username: String
    ): Player

    @GET("player/{username}/stats")
    suspend fun getStats(
        @Path("username") username: String
    ): PlayerStats
}