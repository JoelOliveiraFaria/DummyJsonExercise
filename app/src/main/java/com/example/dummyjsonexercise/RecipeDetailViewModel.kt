package com.example.dummyjsonexercise

import android.os.Handler
import android.os.Looper
import android.util.Log
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

data class RecipeDetailState (
    val recipe: Recipe? = null,
    val isLoading: Boolean = false,
    val error: String? = null,

    )

class RecipeDetailViewModel : ViewModel(){
    var uiState = mutableStateOf(RecipeDetailState())
        private set

    private val mainHandler = Handler(Looper.getMainLooper())

    fun loadRecipe(id: Int) {
        Log.d("RecipeDetailViewModel", "Loading recipe with ID: $id")

        mainHandler.post {
            uiState.value = uiState.value.copy(isLoading = true, error = null)

        }

        val client = OkHttpClient()

        val request = Request.Builder()
            .url("https://dummyjson.com/recipes/$id")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("RecipeDetailViewModel", "Failed to load recipe", e)
                mainHandler.post {
                    uiState.value = uiState.value.copy(
                        error = e.message ?: "Network error",
                        isLoading = false
                    )
                }
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) {
                        mainHandler.post {
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
                        val recipe = Recipe.fromJson(jsonResult)

                        Log.d("RecipeDetailViewModel", "Recipe loaded: ${recipe.name}")

                        mainHandler.post {
                            uiState.value = uiState.value.copy(
                                recipe = recipe,
                                isLoading = false,
                                error = null
                            )
                        }

                    } catch (e : Exception){

                        Log.e("RecipeDetailViewModel", "Parse error", e)
                        mainHandler.post {
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