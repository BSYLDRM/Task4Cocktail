package com.example.cocktail.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktail.data.*
import com.example.cocktail.model.CocktailApi
import com.example.cocktail.model.CocktailRetrofit
import kotlinx.coroutines.launch
import android.util.Log

class CocktailViewModel : ViewModel() {
    private val _cocktailList = MutableLiveData<List<CocktailDrink>>()
    val cocktailList: LiveData<List<CocktailDrink>> get() = _cocktailList

    private val _ingredientList = MutableLiveData<List<Ingredient>?>()
    val ingredientList: LiveData<List<Ingredient>?> get() = _ingredientList

    private val _randomCocktail = MutableLiveData<CocktailDrink>()
    val randomCocktail: LiveData<CocktailDrink> get() = _randomCocktail

    private val _categories = MutableLiveData<List<CategoryDrink>>()
    val categories: LiveData<List<CategoryDrink>> get() = _categories

    private val _glassCategories = MutableLiveData<List<GlassListCategoryDrink>>()
    val glassCategories: LiveData<List<GlassListCategoryDrink>> get() = _glassCategories

    private val _ingredientCategories = MutableLiveData<List<CategoryDrink>>()
    val ingredientCategories: LiveData<List<CategoryDrink>> get() = _ingredientCategories

    private val _filteredCocktails = MutableLiveData<List<CocktailDrink>>()
    val filteredCocktails: LiveData<List<CocktailDrink>> get() = _filteredCocktails

    private val _randomCocktails = MutableLiveData<List<CocktailDrink>>()
    val randomCocktails: LiveData<List<CocktailDrink>> get() = _randomCocktails

    private val _selectedCocktail = MutableLiveData<CocktailDrink>()
    val selectedCocktail: LiveData<CocktailDrink> get() = _selectedCocktail

    private val _ordinaryDrinks = MutableLiveData<List<OrdinaryDrink>>()
    val ordinaryDrinks: LiveData<List<OrdinaryDrink>> get() = _ordinaryDrinks

    private val _cocktailDetails = MutableLiveData<LookupFullCocktailDetailsById>()
    val cocktailDetails: LiveData<LookupFullCocktailDetailsById> get() = _cocktailDetails

    private val apiService: CocktailApi by lazy {
        CocktailRetrofit.retrofit.create(CocktailApi::class.java)
    }

    fun fetchCocktailsByName(name: String) {
        fetchData({ apiService.searchCocktailByName(name) }) {
            _cocktailList.value = it?.drinks ?: emptyList()
        }
    }

    fun fetchRandomCocktail() {
        fetchData({ apiService.lookupRandomCocktail() }) {
            _randomCocktail.value = it?.drinks?.firstOrNull()
        }
    }

    fun fetchCategories() {
        fetchData({ apiService.listCategories("list") }) {
            _categories.value = it?.drinks ?: emptyList()
        }
    }

    fun fetchGlassCategories() {
        fetchData({ apiService.listGlassCategories("list") }) {
            _glassCategories.value = it?.drinks ?: emptyList()
        }
    }

    fun fetchIngredientCategories() {
        fetchData({ apiService.listIngredientCategories("list") }) {
            _ingredientCategories.value = it?.drinks ?: emptyList()
        }
    }

    fun fetchIngredientByName(ingredient: String) {
        fetchData({ apiService.searchIngredientByName(ingredient) }) {
            _ingredientList.value = it?.ingredients ?: emptyList()
        }
    }

    fun fetchAlcoholicCocktails() {
        fetchData({ apiService.filterByAlcohol("Alcoholic") }) {
            _filteredCocktails.value = it?.drinks ?: emptyList()
        }
    }

    fun fetchNonAlcoholicCocktails() {
        fetchData({ apiService.filterByNonAlcohol("Non_Alcoholic") }) {
            _filteredCocktails.value = it?.drinks ?: emptyList()
        }
    }

    fun fetchRandomCocktails() {
        viewModelScope.launch {
            val cocktails = mutableListOf<CocktailDrink>()
            repeat(10) {
                val response = apiService.lookupRandomCocktail()
                if (response.isSuccessful) {
                    response.body()?.drinks?.firstOrNull()?.let { cocktails.add(it) }
                } else {
                    Log.e(
                        "CocktailViewModel",
                        "Error fetching random cocktail: ${response.errorBody()?.string()}"
                    )
                }
            }
            _randomCocktails.value = cocktails
        }
    }

    fun fetchOrdinaryDrinks() {
        fetchData({ apiService.filterByCategory("Ordinary_Drink") }) {
            _ordinaryDrinks.value = it?.drinks ?: emptyList()
        }
    }

    fun selectCocktail(cocktail: CocktailDrink) {
        _selectedCocktail.value = cocktail
    }

    fun fetchCocktailDetails(cocktailId: String) {
        viewModelScope.launch {
            try {
                val response = apiService.lookupCocktailById(cocktailId)
                if (response.isSuccessful && response.body() != null) {
                    val drinks = response.body()?.drinks
                    if (!drinks.isNullOrEmpty()) {
                        _selectedCocktail.postValue(drinks[0])
                    }
                } else {
                }
            } catch (e: Exception) {
            }

        }

    }

    private fun <T> fetchData(
        apiCall: suspend () -> retrofit2.Response<T>,
        onSuccess: (T?) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = apiCall()
                if (response.isSuccessful) {
                    onSuccess(response.body())
                } else {
                    Log.e(
                        "CocktailViewModel",
                        "Error fetching data: ${response.errorBody()?.string()}"
                    )
                }
            } catch (e: Exception) {
                Log.e("CocktailViewModel", "Exception: ${e.message}")
            }
        }
    }
}
