package com.example.recipeapp.di

import com.example.recipeapp.network.RetrofitService
import com.example.recipeapp.network.model.RecipeDtoMapper
import com.example.recipeapp.repository.RecipeRepository
import com.example.recipeapp.repository.RecipeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideRecipeRepository(
        retrofitService: RetrofitService,
        recipeDtoMapper: RecipeDtoMapper
    ): RecipeRepository {
        return RecipeRepositoryImpl(retrofitService, recipeDtoMapper)
    }
}