package com.example.recipeapp.presentation.ui.recipe_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment;
import androidx.fragment.app.viewModels
import com.example.recipeapp.presentation.components.FoodCategoryChip
import com.example.recipeapp.presentation.components.RecipeCard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeListFragment : Fragment() {
    private val viewModel: RecipeListViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {

                val recipes = viewModel.recipes.value

                val query = viewModel.query.value

                val keyboardController = LocalSoftwareKeyboardController.current

                var selectedCategory by remember {
                    mutableStateOf("")
                }

                Column {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.White,
                        tonalElevation = 8.dp,
                    ) {
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Box(
                                    modifier = Modifier
                                        .background(MaterialTheme.colorScheme.surface)
                                        .fillMaxWidth()
                                ) {
                                    TextField(
                                        modifier = Modifier
                                            .fillMaxWidth(0.9F)
                                            .padding(8.dp),
                                        value = query,
                                        onValueChange = { newValue ->
                                            viewModel.onQueryChanged(newValue)
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
                                                viewModel.newSearch(query = query)
                                                keyboardController?.hide()
                                            }
                                        ),
                                        textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
                                    )
                                }
                            }
                            val scrollState = rememberScrollState()
                            Row(modifier = Modifier.horizontalScroll(scrollState)) {
                                for (category in getAllFoodCategories()) {
                                    FoodCategoryChip(
                                        category = category.value,
                                        selected = selectedCategory == category.value,
                                        onSelect = {
                                            if (selectedCategory == it) {
                                                selectedCategory = ""
                                                viewModel.onQueryChanged("")
                                                viewModel.newSearch("")
                                            } else {
                                                selectedCategory = it
                                                viewModel.onQueryChanged(category.value)
                                                viewModel.newSearch(category.value)
                                                viewModel.onChangeCategoryScrollPosition(scrollState.value)
                                            }
                                        }
                                    )
                                }
                            }
                            LazyColumn {
                                itemsIndexed(
                                    items = recipes
                                ) { index, recipe ->
                                    RecipeCard(recipe = recipe, onClick = {})
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}