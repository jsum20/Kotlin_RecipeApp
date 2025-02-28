package com.example.recipeapp.presentation.components.util

sealed class Event {
    data class ShowErrorMessage(val message: String) : Event()
}