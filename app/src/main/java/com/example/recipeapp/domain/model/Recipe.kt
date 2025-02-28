package com.example.recipeapp.domain.model

data class Recipe(
    val id: Int,
    val title: String,
    val publisher: String?= null,
    val featuredImage: String?= null,
    val rating: Int?= 0,
    val sourceUrl: String?= null,
    val description: String,
    val cookingInstructions: String?= null,
    val ingredients: List<String> = listOf(),
    val dateAdded: String?= null,
    val dateUpdated: String?= null
)