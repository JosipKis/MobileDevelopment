package com.example.chessapi1.network

import com.example.chessapi1.model.Player
import retrofit2.http.GET
import retrofit2.http.Path

interface ChessApi {

    @GET("player/{username}")
    suspend fun getPlayer(
        @Path("username") username: String
    ): Player
}