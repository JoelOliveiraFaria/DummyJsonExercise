package com.example.dummyjsonexercise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dummyjsonexercise.models.Recipe
import com.example.dummyjsonexercise.ui.theme.DummyJsonExerciseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DummyJsonExerciseTheme {
                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    contentWindowInsets = WindowInsets(0.dp)
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "recipeList",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("recipeList") {
                            RecipeListView(
                                navController = navController
                            )
                        }

                        composable("recipe/{recipeId}") { backStackEntry ->
                            val recipeId = backStackEntry.arguments?.getString("recipeId")?.toIntOrNull() ?: 1
                            RecipeDetailView(
                                recipeId = recipeId,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}