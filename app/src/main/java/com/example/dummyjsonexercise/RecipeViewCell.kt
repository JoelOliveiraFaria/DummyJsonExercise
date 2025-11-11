package com.example.dummyjsonexercise

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.dummyjsonexercise.models.Recipe
import com.example.dummyjsonexercise.ui.theme.DummyJsonExerciseTheme

@Composable
fun RecipeViewCell(recipe: Recipe, onClick: () -> Unit, modifier: Modifier = Modifier){

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        shape = androidx.compose.material3.MaterialTheme.shapes.medium
    ){
        RecipeView(recipe = recipe)
    }
}

@Composable
fun RecipeView(recipe: Recipe) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    )
    {
        AsyncImage(
            model = recipe.image,
            contentDescription = recipe.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(androidx.compose.material3.MaterialTheme.shapes.medium),
            contentScale = androidx.compose.ui.layout.ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = recipe.name ?: "Receita desconhecida",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.weight(1f)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ){
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Rating",
                    tint = Color(0xFFFFD700),
                    modifier = Modifier.padding(end = 4.dp)
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = "${recipe.rating ?: 0.0}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )

            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ){
            Text(
                text = "⏱️ ${recipe.prepTimeMinutes}min prep",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            Text(
                text = "🍳 ${recipe.cookTimeMinutes}min cook",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            Text(
                text = recipe.difficulty ?: "Medium",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            recipe.tags?.take(3)?.forEach { tag ->
                Surface(
                    shape = MaterialTheme.shapes.small,
                    color = MaterialTheme.colorScheme.primary
                ) {
                    Text(
                        text = tag,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeViewCellPreview(){
    DummyJsonExerciseTheme {
        RecipeViewCell(
            recipe = Recipe(
                id = 1,
                name = "Classic Margherita Pizza",
                ingredients = listOf(
                    "Pizza dough",
                    "Tomato sauce",
                    "Fresh mozzarella",
                    "Fresh basil",
                    "Olive oil"
                ),
                instructions = listOf(
                    "Preheat the oven to 475°F (245°C)",
                    "Roll out the pizza dough",
                    "Spread tomato sauce evenly",
                    "Add mozzarella cheese",
                    "Bake for 12-15 minutes",
                    "Garnish with fresh basil"
                ),
                prepTimeMinutes = 20,
                cookTimeMinutes = 15,
                servings = 4,
                difficulty = "Easy",
                cuisine = "Italian",
                caloriesPerServing = 300,
                tags = listOf("Italian", "Vegetarian", "Quick"),
                userId = 166,
                image = "https://cdn.dummyjson.com/recipe-images/1.webp",
                rating = 4.6,
                reviewCount = 98,
                mealType = listOf("Dinner", "Lunch")
            ),
            onClick = { }
        )
    }
}