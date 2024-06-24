package com.example.recipeapp.presentation.components

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.recipeapp.presentation.ui.recipe_list.FoodCategory
import kotlin.reflect.KSuspendFunction1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchAppBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    onExecuteSearch: () -> Unit,
    categories: List<FoodCategory>,
    onChangeCategoryScrollPosition: (Int) -> Unit,
    selectedCategory: FoodCategory?,
    scrollState: ScrollState,
    onSelectedCategoryChanged: (String) -> Unit,
){
    val keyboardController = LocalSoftwareKeyboardController.current
    Surface(
        modifier = Modifier.fillMaxWidth(),
        tonalElevation = 8.dp,
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth(0.9F)
                            .padding(8.dp),
                        value = query,
                        onValueChange = { newValue ->
                            onQueryChanged(newValue)
                        },
                        label = {
                            Text(
                                text = "Search"
                            )
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Search
                        ),
                        leadingIcon = {
                            Icon(
                                Icons.Filled.Search,
                                contentDescription = "Search Icon"
                            )
                        },
                        keyboardActions = KeyboardActions(
                            onSearch = {
                                onExecuteSearch()
                                keyboardController?.hide()
                            }
                        ),
                        textStyle = TextStyle(color = MaterialTheme.colorScheme.onPrimary),
                    )
                }
            }
            Row(modifier = Modifier.horizontalScroll(scrollState)) {
                for (category in categories) {
                    FoodCategoryChip(
                        category = category.value,
                        selected = selectedCategory == category,
                        onSelectedCategoryChanged = {
                            onChangeCategoryScrollPosition(scrollState.value)
                            onSelectedCategoryChanged(it)
                        },
                        onExecuteSearch = {
                            onExecuteSearch()
                        },
                    )
                }
            }
        }
    }
}