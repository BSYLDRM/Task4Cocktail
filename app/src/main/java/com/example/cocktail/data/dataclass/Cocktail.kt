package com.example.cocktail.data.dataclass

import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SearchCocktailByName(
    @SerializedName("drinks") val drinks: List<CocktailDrink>
) : Serializable

data class ListCocktailByFirstLetter(
    @SerializedName("drinks") val drinks: List<CocktailDrink>
) : Serializable

data class SearchIngredientByName(
    @SerializedName("ingredients") val ingredients: List<Ingredient>
) : Serializable

data class LookupFullCocktailDetailsById(
    @SerializedName("drinks") val drinks: List<CocktailDrink>
) : Serializable

data class LookupIngredientsById(
    @SerializedName("ingredients") val ingredients: List<Ingredient>
) : Serializable

data class Ingredient(
    @SerializedName("idIngredient") val idIngredient: String,
    @SerializedName("strIngredient") val strIngredient: String,
    @SerializedName("strDescription") val strDescription: String,
    @SerializedName("strType") val strType: String,
    @SerializedName("strAlcohol") val strAlcohol: String,
    @SerializedName("strAbv") val  strABV: JsonElement? = null
) : Serializable

data class LookupRandomCocktail(
    @SerializedName("drinks") val drinks: List<CocktailDrink>
) : Serializable

data class SearchIngredientGin(
    @SerializedName("drinks") val drinks: List<GinDrink>
) : Serializable

data class GinDrink(
    @SerializedName("strDrink") val strDrink: String,
    @SerializedName("strDrinkThumb") val strDrinkThumb: String,
    @SerializedName("idDrink") val idDrink: String
) : Serializable

data class SearchIngredientVodka(
    @SerializedName("drinks") val drinks: List<VodkaDrink>
) : Serializable

data class VodkaDrink(
    @SerializedName("strDrink") val strDrink: String,
    @SerializedName("strDrinkThumb") val strDrinkThumb: String,
    @SerializedName("idDrink") val idDrink: String
) : Serializable

data class FilterAlcohol(
    @SerializedName("drinks") val drinks: List<CocktailDrink>
) : Serializable

data class AlcoholDrink(
    @SerializedName("strDrink") val strDrink: String,
    @SerializedName("strDrinkThumb") val strDrinkThumb: String,
    @SerializedName("idDrink") val idDrink: String
) : Serializable
data class FilterOrdinaryDrink(
    @SerializedName("drinks") val drinks: List<OrdinaryDrink>
) : Serializable

data class OrdinaryDrink(
    @SerializedName("strDrink") val strDrink: String,
    @SerializedName("strDrinkThumb") val strDrinkThumb: String,
    @SerializedName("idDrink") val idDrink: String
) : Serializable

data class FilterCocktail(
    @SerializedName("drinks") val drinks: List<CocktailDrink>
) : Serializable

data class CocktailDrink(
    @SerializedName("strDrink") val strDrink: String,
    @SerializedName("strDrinkThumb") val strDrinkThumb: String,
    @SerializedName("idDrink") val idDrink: String,
    @SerializedName("strInstructions") val strInstructions: String,
    @SerializedName("strIngredient1") val strIngredient1: String?,
    @SerializedName("strIngredient2") val strIngredient2: String?,
    @SerializedName("strIngredient3") val strIngredient3: String?,
    @SerializedName("strIngredient4") val strIngredient4: String?,
    @SerializedName("strIngredient5") val strIngredient5: String?,
    @SerializedName("strIngredient6") val strIngredient6: String?,
    @SerializedName("strIngredient7") val strIngredient7: String?,
    @SerializedName("strIngredient8") val strIngredient8: String?,
    @SerializedName("strIngredient9") val strIngredient9: String?,
    @SerializedName("strIngredient10") val strIngredient10: String?,
    @SerializedName("strIngredient11") val strIngredient11: String?,
    @SerializedName("strIngredient12") val strIngredient12: String?,
    @SerializedName("strMeasure1") val strMeasure1: String?,
    @SerializedName("strMeasure2") val strMeasure2: String?,
    @SerializedName("strMeasure3") val strMeasure3: String?,
    @SerializedName("strMeasure4") val strMeasure4: String?,
    @SerializedName("strMeasure5") val strMeasure5: String?,
    @SerializedName("strMeasure6") val strMeasure6: String?,
    @SerializedName("strMeasure7") val strMeasure7: String?,
    @SerializedName("strMeasure8") val strMeasure8: String?,
    @SerializedName("strMeasure9") val strMeasure9: String?,
    @SerializedName("strMeasure10") val strMeasure10: String?,
    @SerializedName("strMeasure11") val strMeasure11: String?,
    @SerializedName("strMeasure12") val strMeasure12: String?
) : Serializable {
    fun getIngredientsWithMeasurements(): List<String> {
        val ingredients = listOf(
            strIngredient1 to strMeasure1,
            strIngredient2 to strMeasure2,
            strIngredient3 to strMeasure3,
            strIngredient4 to strMeasure4,
            strIngredient5 to strMeasure5,
            strIngredient6 to strMeasure6,
            strIngredient7 to strMeasure7,
            strIngredient8 to strMeasure8,
            strIngredient9 to strMeasure9,
            strIngredient10 to strMeasure10,
            strIngredient11 to strMeasure11,
            strIngredient12 to strMeasure12
        )

        return ingredients.filter { it.first != null && it.second != null }
            .map { "${it.second} ${it.first}" }
    }
}
data class Category(
    @SerializedName("drinks") val drinks: List<CategoryDrink>
) : Serializable

data class CategoryDrink(
    @SerializedName("strCategory") val strCategory: String
) : Serializable

data class GlassCategoryList(
    @SerializedName("drinks") val drinks: List<GlassListCategoryDrink>
) : Serializable

data class GlassListCategoryDrink(
    @SerializedName("strGlass") val strGlass: String
) : Serializable