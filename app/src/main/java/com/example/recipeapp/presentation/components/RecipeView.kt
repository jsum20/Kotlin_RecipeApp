package com.example.recipeapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.recipeapp.domain.model.Recipe
import com.example.recipeapp.util.DEFAULT_RECIPE_IMAGE
import com.example.recipeapp.util.loadPicture

const val IMAGE_HEIGHT = 260

@Composable
fun RecipeView(
    recipe: Recipe,
) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        item {
            recipe.featuredImage?.let { url ->
                val image = loadPicture(url = url, defaultImage = DEFAULT_RECIPE_IMAGE).value
                image?.let { img ->
                    Image(
                        bitmap = img.asImageBitmap(),
                        contentDescription = "Recipe Card Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IMAGE_HEIGHT.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }

        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                recipe.title.let { title ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Text(
                            text = title,
                            modifier = Modifier
                                .fillMaxWidth(0.85f)
                                .wrapContentWidth(Alignment.Start),
                            style = MaterialTheme.typography.headlineMedium
                        )
                        val rank = recipe.rating.toString()
                        Text(
                            text = rank,
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(Alignment.End)
                                .align(Alignment.CenterVertically),
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }

                    recipe.publisher?.let { publisher ->
                        val updated = recipe.dateUpdated
                        Text(
                            text = if (updated != null) {
                                "Updated $updated by $publisher"
                            } else {
                                "By $publisher"
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    for (ingredient in recipe.ingredients) {
                        println("Ingredient: $ingredient")
                        Text(
                            text = ingredient,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}