package com.example.recipeapp.domain.model

enum class FoodCategory(val displayName: String) {
    CHICKEN("Chicken"),
    BEEF("Beef"),
    SOUP("Soup"),
    DESSERT("Dessert"),
    VEGETARIAN("Vegetarian"),
    MILK("Milk"),
    VEGAN("Vegan"),
    PIZZA("Pizza"),
    DONUT("Donut")
}

fun getAllFoodCategories(): List<FoodCategory> {
    return enumValues<FoodCategory>().toList()
}

fun getFoodCategory(value: String): FoodCategory? {
    return FoodCategory.values().find { it.displayName == value }
}