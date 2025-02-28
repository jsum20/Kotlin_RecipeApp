package com.example.recipeapp.presentation.recipe

import com.example.recipeapp.domain.model.Recipe
import com.example.recipeapp.repository.RecipeRepository
import javax.inject.Inject
import javax.inject.Named

class GetRecipeUseCase @Inject constructor(
    private val repository: RecipeRepository,
    @Named("auth_token") private val token: String,
) {
    suspend operator fun invoke(id: Int): Recipe {
        return repository.getRecipe(token, id)
    }
}