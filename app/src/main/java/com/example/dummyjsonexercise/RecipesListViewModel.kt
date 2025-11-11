package com.example.dummyjsonexercise

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dummyjsonexercise.models.Recipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

data class RecipesListState(
    val recipes: List<Recipe> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)

class RecipesListViewModel : ViewModel() {
    var uiState = mutableStateOf(RecipesListState())
        private set

    init {
        loadRecipes("https://dummyjson.com/recipes")
    }

    fun loadRecipes(source: String) {
        uiState.value = uiState.value.copy(isLoading = true)

        val client = OkHttpClient()

        val request = Request.Builder()
            .url(source)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                viewModelScope.launch(Dispatchers.Main) {
                    uiState.value = uiState.value.copy(
                        error = e.message ?: "Network error",
                        isLoading = false
                    )
                }
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) {
                        viewModelScope.launch(Dispatchers.Main) {
                            uiState.value = uiState.value.copy(
                                error = "Error: ${response.code}",
                                isLoading = false
                            )
                        }
                        return
                    }

                    try {
                        val result = response.body!!.string()
                        val jsonResult = JSONObject(result)

                        // DummyJSON retorna "recipes" array diretamente
                        val jsonRecipes = jsonResult.getJSONArray("recipes")

                        val recipesList = arrayListOf<Recipe>()
                        for (i in 0 until jsonRecipes.length()) {
                            val recipe = Recipe.fromJson(jsonRecipes.getJSONObject(i))
                            recipesList.add(recipe)
                        }

                        viewModelScope.launch(Dispatchers.Main) {
                            uiState.value = uiState.value.copy(
                                recipes = recipesList,
                                isLoading = false,
                                error = null
                            )
                        }
                    } catch (e: Exception) {
                        viewModelScope.launch(Dispatchers.Main) {
                            uiState.value = uiState.value.copy(
                                error = "Parse error: ${e.message}",
                                isLoading = false
                            )
                        }
                    }
                }
            }
        })
    }
}
