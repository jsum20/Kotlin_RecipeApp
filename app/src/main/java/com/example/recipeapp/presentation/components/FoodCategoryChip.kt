package com.example.recipeapp.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodCategoryChip(category: String, selected: Boolean, onSelect: (String) -> Unit) {

    ElevatedFilterChip(
        selected = selected,
        onClick = {
                    onSelect(category)
                  },
        colors = FilterChipDefaults.elevatedFilterChipColors(
            selectedContainerColor = Color.LightGray
        ),
        elevation = FilterChipDefaults.elevatedFilterChipElevation(
            defaultElevation = 2.dp,
            pressedElevation = 4.dp,
            focusedElevation = 4.dp
        ),
        label = { Text(text = category, color = Color.Black) },
        modifier = Modifier.padding(end = 8.dp),
        leadingIcon = if (selected) {
            {
                Icon(
                    imageVector = Icons.Filled.Done,
                    tint = Color.Red,
                    contentDescription = "Selected icon",
                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                )
            }
        } else {
            null
        }
    )
}
