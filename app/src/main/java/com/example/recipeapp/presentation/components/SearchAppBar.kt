package com.example.recipeapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.recipeapp.domain.model.FoodCategory

@Composable
fun SearchAppBar(
    query: String,
    selectedCategory: FoodCategory?,
    onQueryChange: (String) -> Unit,
    onCategorySelected: (FoodCategory) -> Unit,
    onSearch: () -> Unit,
    modifier: Modifier = Modifier
){
    val keyboardController = LocalSoftwareKeyboardController.current
    val scrollState = rememberScrollState()
    val categories = FoodCategory.values().toList()

    Surface(
        modifier = modifier,
        tonalElevation = 8.dp,
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier.background(MaterialTheme.colorScheme.surface)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = query,
                    onValueChange = onQueryChange,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    label = { Text("Search recipes") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search icon"
                        )
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            onSearch()
                            keyboardController?.hide()
                        }
                    ),
                    singleLine = true
                )
            }

            Row(
                modifier = Modifier
                    .horizontalScroll(scrollState)
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                categories.forEach { category ->
                    FoodCategoryChip(
                        category = category,
                        selected = category == selectedCategory,
                        onSelected = {
                            onCategorySelected(category)
                            onSearch()
                        },
                        modifier = Modifier.padding(vertical = 4.dp),
                        onExecuteSearch = onSearch
                    )
                }
            }
        }
    }
}