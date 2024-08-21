package com.example.cocktail.data.`object`

import android.content.Context
import com.example.cocktail.R
import com.example.cocktail.data.dataclass.Categories

object CategoriesObject {
    fun getCategoriesList(context: Context): List<Categories> {
        return listOf(
            Categories(
                context.getString(R.string.main_object_cock),
                context.getString(R.string.cocktail_url)
            ),
            Categories(
                context.getString(R.string.main_object_alcohol),
                context.getString(R.string.alcohol_url)
            ),
            Categories(
                context.getString(R.string.main_object_original),
                context.getString(R.string.original_url)
            ),
            Categories(
                context.getString(R.string.main_object_popular),
                context.getString(R.string.popular_url)
            ),
            Categories(
                context.getString(R.string.main_object_glass),
                context.getString(R.string.glass_url)
            )
        )
    }
}