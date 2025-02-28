package com.example.recipeapp.presentation.recipe

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.domain.model.Recipe
import com.example.recipeapp.presentation.components.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val getRecipeUseCase: GetRecipeUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _state = MutableStateFlow(RecipeState())
    val state: StateFlow<RecipeState> = _state.asStateFlow()

    private val _event = Channel<Event>()
    val event = _event.receiveAsFlow()

    init {
        savedStateHandle.get<Int>("recipeId")?.let { recipeId ->
            loadRecipe(recipeId)
        }
    }

    fun loadRecipe(recipeId: Int) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            try {
                val recipe = getRecipeUseCase(recipeId)

                _state.update { it.copy(
                    isLoading = false,
                    recipe = recipe
                )}
            } catch (error: Exception) {
                _state.update { it.copy(
                    isLoading = false,
                    error = error.message
                )}
                _event.send(Event.ShowErrorMessage("Failed to load recipe"))
            }
        }
    }

}

data class RecipeState(
    val recipe: Recipe? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)