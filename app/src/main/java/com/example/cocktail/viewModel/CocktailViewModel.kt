package com.example.cocktail.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cocktail.model.CocktailApi
import com.example.cocktail.model.CocktailRetrofit
import kotlinx.coroutines.launch
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.cocktail.R
import com.example.cocktail.data.dataclass.CategoryDrink
import com.example.cocktail.data.dataclass.CocktailDrink
import com.example.cocktail.data.dataclass.GlassListCategoryDrink
import com.example.cocktail.data.dataclass.Ingredient
import com.example.cocktail.data.dataclass.LookupFullCocktailDetailsById
import com.example.cocktail.data.dataclass.OrdinaryDrink

class CocktailViewModel(application: Application) : AndroidViewModel(application) {
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

    private val _selectedOrdinaryDrink = MutableLiveData<OrdinaryDrink>()
    val selectedOrdinaryDrink: LiveData<OrdinaryDrink> get() = _selectedOrdinaryDrink

    private val apiService: CocktailApi by lazy {
        CocktailRetrofit.createApiService()
    }

    fun fetchCocktailsByName(name: String) {
        viewModelScope.launch {
            DataFetcher.fetchData(
                apiCall = { apiService.searchCocktailByName(name) },
                onSuccess = { response -> _cocktailList.value = response?.drinks ?: emptyList() }
            )
        }
    }

    fun fetchRandomCocktail() {
        viewModelScope.launch {
            DataFetcher.fetchData(
                apiCall = { apiService.lookupRandomCocktail() },
                onSuccess = { response -> _randomCocktail.value = response?.drinks?.firstOrNull() }
            )
        }
    }

    fun fetchCategories() {
        val listValue = getApplication<Application>().getString(R.string.view_list)
        viewModelScope.launch {
            DataFetcher.fetchData(
                apiCall = { apiService.listCategories(listValue) },
                onSuccess = { response -> _categories.value = response?.drinks ?: emptyList() }
            )
        }
    }

    fun fetchGlassCategories() {
        val listValue = getApplication<Application>().getString(R.string.view_list)
        viewModelScope.launch {
            DataFetcher.fetchData(
                apiCall = { apiService.listGlassCategories(listValue) },
                onSuccess = { response -> _glassCategories.value = response?.drinks ?: emptyList() }
            )
        }
    }

    fun fetchIngredientCategories() {
        val listValue = getApplication<Application>().getString(R.string.view_list)
        viewModelScope.launch {
            DataFetcher.fetchData(
                apiCall = { apiService.listIngredientCategories(listValue) },
                onSuccess = { response ->
                    _ingredientCategories.value = response?.drinks ?: emptyList()
                }
            )
        }
    }

    fun fetchIngredientByName(ingredient: String) {
        viewModelScope.launch {
            DataFetcher.fetchData(
                apiCall = { apiService.searchIngredientByName(ingredient) },
                onSuccess = { response ->
                    _ingredientList.value = response?.ingredients ?: emptyList()
                }
            )
        }
    }

    fun fetchAlcoholicCocktails() {
        val alcoholicValue = getApplication<Application>().getString(R.string.view_alcohol)
        viewModelScope.launch {
            DataFetcher.fetchData(
                apiCall = { apiService.filterByAlcohol(alcoholicValue) },
                onSuccess = { response ->
                    _filteredCocktails.value = response?.drinks ?: emptyList()
                }
            )
        }
    }

    fun fetchNonAlcoholicCocktails() {
        val nonAlcoholicValue = getApplication<Application>().getString(R.string.view_non_alcoholic)
        viewModelScope.launch {
            DataFetcher.fetchData(
                apiCall = { apiService.filterByNonAlcohol(nonAlcoholicValue) },
                onSuccess = { response ->
                    _filteredCocktails.value = response?.drinks ?: emptyList()
                }
            )
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
        val ordinaryValue = getApplication<Application>().getString(R.string.view_ordinary)
        viewModelScope.launch {
            DataFetcher.fetchData(
                apiCall = { apiService.filterByCategory(ordinaryValue) },
                onSuccess = { response -> _ordinaryDrinks.value = response?.drinks ?: emptyList() }
            )
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
                        val cocktail = drinks[0] as? CocktailDrink
                        cocktail?.let {
                            _selectedCocktail.postValue(it)
                        }
                    }
                }
            } catch (e: Exception) {

            }
        }
    }
}