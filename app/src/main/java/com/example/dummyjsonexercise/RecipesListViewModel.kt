package com.example.dummyjsonexercise

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.dummyjsonexercise.models.Recipe
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

    fun loadRecipes(source : String) {
        uiState.value = uiState.value.copy(isLoading = true)

        val client = OkHttpClient()

        val request = Request.Builder()
            .url("https://dummyjson.com/recipes")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                uiState.value = uiState.value.copy(error = e.message, isLoading = false)
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if(!response.isSuccessful){
                        uiState.value = uiState.value.copy(
                            error = "Unexpected ${response.code}",
                            isLoading = false)
                    }
                    val result = response.body!!.string()
                    val jsonResult = JSONObject(result)

                    if (jsonResult.getString("status") == "ok") {
                        val jsonRecipes = jsonResult.getJSONArray("recipes")

                        val recipesList = arrayListOf<Recipe>()
                        for (i in 0 until jsonRecipes.length()) {
                            val recipe = Recipe.fromJson(jsonRecipes.getJSONObject(i))
                            recipesList.add(recipe)

                        }
                        uiState.value = uiState.value.copy(
                            recipes = recipesList,
                            isLoading = false,
                            error = null)
                    }
                }
            }
        })
    }
}