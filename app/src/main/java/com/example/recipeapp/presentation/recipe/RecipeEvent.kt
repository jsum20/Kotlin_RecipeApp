package com.example.recipeapp.presentation.recipe

sealed class RecipeEvent {
    data class GetRecipeEvent(
        val id: Int
    ) : RecipeEvent()

    object None : RecipeEvent()
}