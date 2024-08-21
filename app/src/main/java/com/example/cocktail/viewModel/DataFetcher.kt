package com.example.cocktail.viewModel

import android.util.Log
import retrofit2.Response
class DataFetcher {
    companion object {
        private const val TAG = "DataFetcher"

        suspend fun <T> fetchData(
            apiCall: suspend () -> Response<T>,
            onSuccess: (T?) -> Unit,
            onError: (String) -> Unit = {}
        ) {
            try {
                val response = apiCall()
                if (response.isSuccessful) {
                    onSuccess(response.body())
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                    Log.e(TAG, "Error fetching data: $errorMessage")
                    onError(errorMessage)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Exception: ${e.message}")
                onError(e.message ?: "Exception occurred")
            }
        }
    }
}