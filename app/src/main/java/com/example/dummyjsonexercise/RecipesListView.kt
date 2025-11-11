package com.example.dummyjsonexercise

import androidx.compose.ui.tooling.preview.Preview
import com.example.dummyjsonexercise.ui.theme.DummyJsonExerciseTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun RecipeListView(
    modifier: Modifier = Modifier,
    navController : NavController,
    source : String
) {
    val viewModel : RecipesListViewModel = viewModel()
    val uiState by viewModel.uiState

    RecipesListViewContent(
        modifier = modifier,
        uiState = uiState,
        navController = navController
    )
}

@Composable
fun RecipesListViewContent(
    modifier: Modifier = Modifier,
    uiState: RecipesListState,
    navController: NavController
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        if (uiState.isLoading) {
            CircularProgressIndicator()
        } else if (uiState.error != null) {
            Text(
                text = uiState.error,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = TextAlign.Center
            )
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                itemsIndexed(
                    items = uiState.recipes,
                ) { index, item ->
                    RecipeViewCell(
                        recipe = item,
                        onClick = {
                            navController.navigate("recipe/${item.id}")
                        }
                    )
                }
            }
        }
    }
}

fun String.encodeUrl(): String {
    return URLEncoder.encode(this, StandardCharsets.UTF_8.toString())
}

@Preview(showBackground = true)
@Composable
fun RecipesListViewContentPreview() {
    DummyJsonExerciseTheme {
        RecipesListViewContent(
            navController = rememberNavController(),
            uiState = RecipesListState(
                isLoading = true,
                error = "No internet connection!",
                recipes = emptyList()
            )
        )

    }
}

