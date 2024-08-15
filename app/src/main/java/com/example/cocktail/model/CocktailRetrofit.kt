package com.example.cocktail.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CocktailRetrofit {
    private const val BASE_URL = "https://www.thecocktaildb.com/"

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
