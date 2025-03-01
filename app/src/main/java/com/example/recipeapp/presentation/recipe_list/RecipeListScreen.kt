import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.recipeapp.domain.model.Recipe
import com.example.recipeapp.presentation.components.SearchAppBar
import com.example.recipeapp.presentation.recipe_list.RecipeListViewModel
import com.example.recipeapp.util.RecipeImage

@Composable
fun RecipeListScreen(
    viewModel: RecipeListViewModel = hiltViewModel(),
    onRecipeClick: (Int) -> Unit
) {
    val state by viewModel.state.collectAsState()
    val pagingItems = viewModel.pagingData.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            SearchAppBar(
                query = state.searchQuery,
                selectedCategory = state.selectedCategory,
                onQueryChange = viewModel::onSearchQueryChanged,
                onCategorySelected = viewModel::onCategorySelected,
                onSearch = viewModel::performSearch,
                modifier = Modifier.statusBarsPadding()
            )
        }
    ){ paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            state = state.listState
        ) {
            items(pagingItems.itemCount) { index ->
                val recipe = pagingItems[index]
                recipe?.let {
                    Log.d("RecipeListScreen", "Displaying recipe: ${recipe.title}")
                    RecipeItem(
                        recipe = it,
                        onClick = { onRecipeClick(it.id) }
                    )
                }
            }
        }

    }
}

@Composable
private fun RecipeItem(recipe: Recipe, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            RecipeImage(
                url = recipe.featuredImage,
                contentDescription = "Recipe Image"
            )
            Text(recipe.title, style = MaterialTheme.typography.headlineSmall)
            Text(recipe.description, style = MaterialTheme.typography.bodyMedium)
        }
    }
}