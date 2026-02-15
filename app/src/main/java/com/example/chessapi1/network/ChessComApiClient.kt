package com.example.chessapi1.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ChessComApiClient {

    private const val BASE_URL = "https://api.chess.com/pub/"

    val api: ChessApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ChessApi::class.java)
    }
}