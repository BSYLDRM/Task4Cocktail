package com.example.cocktail.model

import com.example.cocktail.data.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailApi {

    @GET("api/json/v1/1/search.php")
    suspend fun searchCocktailByName(@Query("s") name: String): Response<SearchCocktailByName>

    @GET("api/json/v1/1/search.php")
    suspend fun searchCocktailByFirstLetter(@Query("f") letter: String): Response<ListCocktailByFirstLetter>

    @GET("api/json/v1/1/search.php")
    suspend fun searchIngredientByName(@Query("i") ingredient: String): Response<SearchIngredientByName>

    @GET("api/json/v1/1/lookup.php")
    suspend fun lookupCocktailById(@Query("i") id: String): Response<LookupFullCocktailDetailsById>

    @GET("api/json/v1/1/lookup.php")
    suspend fun lookupIngredientById(@Query("iid") id: String): Response<LookupIngredientsById>

    @GET("api/json/v1/1/random.php")
    suspend fun lookupRandomCocktail(): Response<LookupRandomCocktail>

    @GET("api/json/v1/1/filter.php")
    suspend fun filterByAlcohol(@Query("a") type: String): Response<FilterAlcohol>

    @GET("api/json/v1/1/filter.php")
    suspend fun filterByNonAlcohol(@Query("a") type: String): Response<FilterNonAlcohol>

    @GET("api/json/v1/1/filter.php")
    suspend fun filterByCategory(@Query("c") category: String): Response<FilterOrdinaryDrink>

    @GET("api/json/v1/1/filter.php")
    suspend fun filterByGlass(@Query("g") glass: String): Response<FilterCocktailGlass>

    @GET("api/json/v1/1/list.php")
    suspend fun listCategories(@Query("c") listType: String): Response<IngredientsCategoryList>

    @GET("api/json/v1/1/list.php")
    suspend fun listGlassCategories(@Query("g") listType: String): Response<GlassCategoryList>

    @GET("api/json/v1/1/list.php")
    suspend fun listIngredientCategories(@Query("i") listType: String): Response<IngredientsCategoryList>
}


