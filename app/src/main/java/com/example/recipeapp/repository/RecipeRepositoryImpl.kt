package com.example.recipeapp.repository

import com.example.recipeapp.domain.model.Recipe
import com.example.recipeapp.network.RetrofitService
import com.example.recipeapp.network.model.RecipeDtoMapper

class RecipeRepositoryImpl(
    private val retrofitService: RetrofitService,
    private val mapper: RecipeDtoMapper
) : RecipeRepository {

    override suspend fun search(token: String, page: Int, query: String): List<Recipe> {
        val result = retrofitService.search(token = token, page = page, query = query).recipes
        return mapper.toDomainList(result)
    }

    override suspend fun get(token: String, id: Int): Recipe {
        return mapper.mapToDomainModel(retrofitService.get(token = token, id = id))
    }
}