package com.example.recipeapp.presentation.navigation

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import com.example.recipeapp.presentation.recipe_list.RecipeListViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class RecipeNavGraphTest() {

    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    private lateinit var navController: TestNavHostController

    @Inject
    lateinit var recipeListViewModel: RecipeListViewModel  // Inject the ViewModel here

    @Before
    fun setup() {
        hiltRule.inject()

        navController = TestNavHostController(ApplicationProvider.getApplicationContext())

        composeTestRule.setContent {
            RecipeNavGraph()
        }
    }

    @Test
    fun initial_route_shouldBe_RecipeList() {
        val currentRoute = navController.currentBackStackEntry?.destination?.route
        assert(currentRoute == Screen.RecipeList.route)
    }


}