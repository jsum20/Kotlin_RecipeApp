package com.example.recipeapp.presentation.recipe_list

import com.example.recipeapp.domain.model.FoodCategory
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class RecipeListViewModelTest {

    private lateinit var viewModel: RecipeListViewModel
    private lateinit var searchRecipesUseCase: SearchRecipesUseCase

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        // Setup the Mock search recipes use case
        searchRecipesUseCase = mockk()

        // Create the viewModel with the mock use case
        viewModel = RecipeListViewModel(searchRecipesUseCase)

        // Setup the test dispatcher to control coroutine execution
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `onSearchQueryChanged updates search query in state`() = runTest {
        // When search query changes
        val query = "Pizza"
        viewModel.onSearchQueryChanged(query)

        // Collect the state to assert changes
        val currentState = viewModel.state.first()

        // Then assert that the state has updated the search query
        assert(currentState.searchQuery == query)
    }

    @Test
    fun `onCategorySelected updates selected category in state`() = runTest {
        // Given a category
        val category = FoodCategory.BEEF

        // When a category is selected
        viewModel.onCategorySelected(category)

        // Collect the state to assert changes
        val currentState = viewModel.state.first()

        // Then assert that the state has updated the selected category
        assert(currentState.selectedCategory == category)
    }

    @Test
    fun `onCategorySelected toggles selected category when the same category is selected again`() = runTest {
        // Given a category
        val category = FoodCategory.BEEF

        // When a category is selected and then the same category is selected again
        viewModel.onCategorySelected(category)
        viewModel.onCategorySelected(category)

        // Collect the state to assert changes
        val currentState = viewModel.state.first()

        // Then assert that the state has updated the selected category
        assert(currentState.selectedCategory == null)
    }

    @Test
    fun `viewModel should start with empty state`() = runTest {
        // Collect the state to assert initial values
        val initialState = viewModel.state.first()

        // Then assert the initial state
        assert(initialState.searchQuery == "")
        assert(initialState.selectedCategory == null)
    }
}