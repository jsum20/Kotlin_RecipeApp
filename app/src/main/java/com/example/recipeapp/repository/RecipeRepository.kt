package com.example.recipeapp.repository

import com.example.recipeapp.domain.model.Recipe

interface RecipeRepository {

    suspend fun searchRecipes(
        token: String,
        query: String,
        page: Int,
        pageSize: Int
    ): List<Recipe>

    suspend fun getRecipe(token: String, id: Int): Recipe

}