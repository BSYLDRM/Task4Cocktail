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
import com.example.cocktail.data.dataclass.CocktailDrink
import com.example.cocktail.data.dataclass.GlassListCategoryDrink
import com.example.cocktail.data.dataclass.LookupFullCocktailDetailsById
import com.example.cocktail.data.dataclass.OrdinaryDrink

class CocktailViewModel(application: Application) : AndroidViewModel(application) {
    private val _cocktailList = MutableLiveData<List<CocktailDrink>>()
    val cocktailList: LiveData<List<CocktailDrink>> get() = _cocktailList
    private val _glassCategories = MutableLiveData<List<GlassListCategoryDrink>>()
    val glassCategories: LiveData<List<GlassListCategoryDrink>> get() = _glassCategories
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

    fun fetchGlassCategories() {
        val listValue = getApplication<Application>().getString(R.string.view_list)
        viewModelScope.launch {
            DataFetcher.fetchData(
                apiCall = { apiService.listGlassCategories(listValue) },
                onSuccess = { response -> _glassCategories.value = response?.drinks ?: emptyList() }
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
    fun fetchRandomCocktails() {
        viewModelScope.launch {
            try {
                val response = apiService.lookupRandomCocktail()
                if (response.isSuccessful && response.body() != null) {
                    val allCocktails = response.body()?.drinks ?: emptyList()

                    val cocktails = mutableListOf<CocktailDrink>()
                    val limit = if (allCocktails.size > 10) 10 else allCocktails.size

                    for (i in 0 until limit) {
                        cocktails.add(allCocktails[i])
                    }
                    _randomCocktails.value = cocktails
                }
            } catch (e: Exception) {
                Log.e("CocktailViewModel", "Error fetching random cocktails", e)
            }
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