package com.example.recipeapp.presentation.ui.recipe_list

import DefaultSnackbar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush.Companion.linearGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment;
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.recipeapp.presentation.components.CircularProgressBar
import com.example.recipeapp.presentation.components.LoadingRecipeListShimmer
import com.example.recipeapp.presentation.components.RecipeCard
import com.example.recipeapp.presentation.components.RecipeList
import com.example.recipeapp.presentation.components.SearchAppBar
import com.example.recipeapp.ui.theme.RecipeAppTheme
import com.example.recipeapp.presentation.components.util.SnackbarController
import com.example.recipeapp.presentation.ui.recipe_list.RecipeListEvent.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipeListFragment : Fragment() {
    private val viewModel: RecipeListViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    private val snackbarController = SnackbarController(lifecycleScope)

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {

                RecipeAppTheme(darkTheme = false) {
                    val recipes = viewModel.recipes.value

                    val query = viewModel.query.value

                    var selectedCategory = viewModel.selectedCategory.value

                    val loading = viewModel.loading.value

                    val scrollState = rememberScrollState()

                    val snackbarHostState = remember { SnackbarHostState() }

                    val page = viewModel.page.value

                    Scaffold(
                        topBar = {
                            SearchAppBar(
                                query = query,
                                onQueryChanged = viewModel::onQueryChanged,
                                onExecuteSearch = {
                                    if (viewModel.selectedCategory.value?.value == "Milk") {
                                        snackbarController.getScope().launch {
                                            snackbarController.showSnackbar(
                                                snackbarHostState = snackbarHostState,
                                                message = "Invalid category: Milk",
                                                actionLabel = "Hide")
                                        }
                                    } else {
                                        viewModel.onTriggerEvent(NewSearchEvent)
                                    }
                                },
                                onChangeCategoryScrollPosition = viewModel::onChangeCategoryScrollPosition,
                                selectedCategory = selectedCategory,
                                scrollState = scrollState,
                                categories = getAllFoodCategories(),
                                onSelectedCategoryChanged = viewModel::onSelectedCategoryChanged,
                            )
                        },
                        bottomBar = {
                            // bottomBar
                        },
                        snackbarHost = {
                            SnackbarHost(hostState = snackbarHostState)
                        }
                    ) {
                        RecipeList(
                            loading = loading,
                            paddingValues = it,
                            recipes = recipes,
                            changeRecipeScrollPosition = viewModel::changeRecipeScrollPosition ,
                            page = page,
                            onNextPage = {
                                viewModel.onTriggerEvent(NextPageEvent)
                            },
                            snackbarHostState = snackbarHostState,
                            snackbarController = snackbarController,
                            navController = findNavController()
                        )
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