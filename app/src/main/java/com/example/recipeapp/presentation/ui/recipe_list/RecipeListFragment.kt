package com.example.recipeapp.presentation.ui.recipe_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush.Companion.linearGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment;
import androidx.fragment.app.viewModels
import com.example.recipeapp.presentation.components.CircularProgressBar
import com.example.recipeapp.presentation.components.LoadingRecipeListShimmer
import com.example.recipeapp.presentation.components.RecipeCard
import com.example.recipeapp.presentation.components.SearchAppBar
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

                var selectedCategory = viewModel.selectedCategory.value

                val loading = viewModel.loading.value

                val scrollState = rememberScrollState()

                Column {
                    SearchAppBar(
                        query = query,
                        onQueryChanged = viewModel::onQueryChanged,
                        newSearch = viewModel::newSearch,
                        onChangeCategoryScrollPosition = viewModel::onChangeCategoryScrollPosition,
                        selectedCategory = selectedCategory,
                        scrollState = scrollState,
                        onSelectedCategoryChanged = viewModel::onSelectedCategoryChanged,
                    )

                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        if (loading) {
                            LoadingRecipeListShimmer(imageHeight = 250.dp)
                        } else {
                            LazyColumn {
                                itemsIndexed(
                                    items = recipes
                                ) { index, recipe ->
                                    RecipeCard(recipe = recipe, onClick = {})
                                }
                            }
                        }
                        CircularProgressBar(isDisplayed = loading)
                    }
                }
            }
        }
    }
}

@Composable
fun gradientDemo() {
    val colors = listOf(
        Color.Blue,
        Color.Red,
        Color.Blue
    )

    val brush = linearGradient(
        colors,
        start = Offset(200f, 200f),
        end = Offset(400f, 400f)
    )

    Surface(shape = MaterialTheme.shapes.small) {
        Spacer(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = brush)
        )
    }
}