package com.example.recipeapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.recipeapp.R
import com.example.recipeapp.domain.model.Recipe

@Composable
fun RecipeCard(
    recipe: Recipe,
    onClick: () -> Unit,
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .padding(
                bottom = 6.dp,
                top = 6.dp,
            )
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column {
            recipe.featuredImage?.let { url ->
                Image(
                    painter = painterResource(id = R.drawable.empty_plate),
                    contentDescription = "Empty Plate Loader",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(225.dp),
                    contentScale = ContentScale.Crop
                )
            }

            recipe.title?.let {title ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 12.dp,
                            bottom = 12.dp,
                            start = 8.dp,
                            end = 8.dp,
                        )
                ) {
                    Text(text = title,
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .wrapContentWidth(align = Alignment.Start),
                        style = MaterialTheme.typography.titleSmall,
                    )
                    Text(
                        text = recipe.rating.toString(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(align = Alignment.End)
                            .align(Alignment.CenterVertically),
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            }
        }
    }
}