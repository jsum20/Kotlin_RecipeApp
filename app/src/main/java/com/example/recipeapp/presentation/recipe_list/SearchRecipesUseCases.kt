package com.example.recipeapp.presentation.recipe_list

import com.example.recipeapp.domain.model.Recipe
import com.example.recipeapp.repository.RecipeRepository
import javax.inject.Inject
import javax.inject.Named

class SearchRecipesUseCase @Inject constructor(
    private val repository: RecipeRepository,
    @Named("auth_token") private val token: String
) {
    suspend operator fun invoke(
        query: String,
        page: Int,
        pageSize: Int
    ): List<Recipe> {
        return repository.searchRecipes(
            query = query,
            page = page,
            pageSize = pageSize,
            token = token
        )
    }
}