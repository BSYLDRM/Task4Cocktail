package com.example.cocktail.data.dataclass

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Categories(
    @SerializedName("buttonString") val buttonString: String,
    @SerializedName("imageUrl") val imageUrl: String
) : Serializable