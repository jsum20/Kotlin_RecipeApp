package com.example.recipeapp.presentation.components

import DefaultSnackbar
import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.recipeapp.R
import com.example.recipeapp.domain.model.Recipe
import com.example.recipeapp.presentation.components.util.SnackbarController
import com.example.recipeapp.presentation.ui.recipe_list.PAGE_SIZE
import com.example.recipeapp.presentation.ui.recipe_list.RecipeListEvent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeList(
    loading: Boolean,
    recipes: List<Recipe>,
    paddingValues: PaddingValues,
    changeRecipeScrollPosition: (Int) -> Unit,
    page: Int,
    onNextPage: (RecipeListEvent) -> Unit,
    snackbarHostState: SnackbarHostState,
    snackbarController: SnackbarController,
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        if (loading && recipes.isEmpty()) {
            LoadingRecipeListShimmer(imageHeight = 250.dp)
        } else {
            LazyColumn {
                itemsIndexed(
                    items = recipes
                ) { index, recipe ->
                    changeRecipeScrollPosition(index)
                    if ((index + 1) >= (page * PAGE_SIZE) && !loading) {
                        onNextPage(RecipeListEvent.NextPageEvent)
                    }
                    RecipeCard(
                        recipe = recipe,
                        onClick = {
                            if(recipe.id != null) {
                                val bundle = Bundle()
                                bundle.putInt("recipeId", recipe.id)
                                navController.navigate(R.id.viewRecipe, bundle)
                            } else {
                                snackbarController.getScope().launch {
                                    snackbarController.showSnackbar(
                                        snackbarHostState = snackbarHostState,
                                        message = "Recipe not available",
                                        actionLabel = "OK"
                                    )
                                }
                            }
                        }
                    )
                }
            }
        }
        CircularProgressBar(isDisplayed = loading)
        DefaultSnackbar(
            snackbarHostState = snackbarHostState,
            onDismiss = {
                snackbarHostState.currentSnackbarData?.dismiss()
            },
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}