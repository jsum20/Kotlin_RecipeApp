package com.example.recipeapp.presentation.ui.recipe

import DefaultSnackbar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.recipeapp.presentation.BaseApplication
import com.example.recipeapp.presentation.components.CircularProgressBar
import com.example.recipeapp.presentation.components.RecipeView
import com.example.recipeapp.presentation.components.util.SnackbarController
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RecipeFragment : Fragment() {

    @Inject
    lateinit var application: BaseApplication

    @OptIn(ExperimentalMaterial3Api::class)
    private val snackbarController = SnackbarController(lifecycleScope)

    private val viewModel: RecipeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getInt("recipeId")?.let {recipeId ->
            viewModel.onTriggerEvent(RecipeEvent.GetRecipeEvent(recipeId))
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                val loading = viewModel.loading.value
                val recipe = viewModel.recipe.value
                val snackbarHostState = remember { SnackbarHostState() }

                Scaffold(
                    snackbarHost = { snackbarHostState },
                ) { it ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it)
                    ) {
                        if (loading && recipe == null) {
                            Text(text = "Loading...")
                        } else {
                            recipe?.let {
                                if(it.id == 1) {
                                    snackbarController.showSnackbar(
                                        snackbarHostState = snackbarHostState,
                                        message = "An error occurred with this recipe",
                                        actionLabel = "Ok"
                                    )
                                } else {
                                    RecipeView(recipe = it)
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
            }
        }
    }
}