package com.example.recipeapp.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.recipeapp.domain.model.FoodCategory

@Composable
fun FoodCategoryChip(
    category: FoodCategory,
    selected: Boolean,
    onSelected: (FoodCategory) -> Unit,
    onExecuteSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedFilterChip(
        selected = selected,
        onClick = {
            onSelected(category)
            onExecuteSearch()
        },
        label = {
            Text(
                text = category.displayName,
                style = MaterialTheme.typography.labelMedium
            )
        },
        leadingIcon = {
            if (selected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Selected",
                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                )
            }
        },
        colors = FilterChipDefaults.filterChipColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            labelColor = MaterialTheme.colorScheme.onSurfaceVariant,
            selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer,
            iconColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        border = FilterChipDefaults.filterChipBorder(
            borderColor = MaterialTheme.colorScheme.outline,
            selectedBorderColor = MaterialTheme.colorScheme.primary,
            disabledBorderColor = Color.Transparent,
            disabledSelectedBorderColor = Color.Transparent,
            borderWidth = 1.dp,
            selectedBorderWidth = 1.5.dp,
            enabled = true,
            selected = true
        ),
        elevation = FilterChipDefaults.elevatedFilterChipElevation(
            elevation = 2.dp
        ),
        modifier = modifier.padding(end = 8.dp)
    )
}
