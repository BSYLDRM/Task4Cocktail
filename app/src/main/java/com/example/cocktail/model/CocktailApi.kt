package com.example.cocktail.model

import com.example.cocktail.data.dataclass.FilterAlcohol
import com.example.cocktail.data.dataclass.FilterOrdinaryDrink
import com.example.cocktail.data.dataclass.GlassCategoryList
import com.example.cocktail.data.dataclass.LookupFullCocktailDetailsById
import com.example.cocktail.data.dataclass.LookupRandomCocktail
import com.example.cocktail.data.dataclass.SearchCocktailByName
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailApi {
    @GET("api/json/v1/1/search.php")
    suspend fun searchCocktailByName(@Query("s") name: String): Response<SearchCocktailByName>
    @GET("api/json/v1/1/lookup.php")
    suspend fun lookupCocktailById(@Query("i") id: String): Response<LookupFullCocktailDetailsById>
    @GET("api/json/v1/1/random.php")
    suspend fun lookupRandomCocktail(): Response<LookupRandomCocktail>

    @GET("api/json/v1/1/filter.php")
    suspend fun filterByAlcohol(@Query("a") type: String): Response<FilterAlcohol>

    @GET("api/json/v1/1/filter.php")
    suspend fun filterByCategory(@Query("c") category: String): Response<FilterOrdinaryDrink>

    @GET("api/json/v1/1/list.php")
    suspend fun listGlassCategories(@Query("g") listType: String): Response<GlassCategoryList>
}