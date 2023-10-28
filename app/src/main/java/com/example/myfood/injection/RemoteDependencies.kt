package com.example.myfood.injection

import android.content.Context
import com.example.myfood.dao.RecipeDao
import com.example.myfood.database.RecipeDatabase
import com.example.myfood.interfaces.GetDataService
import com.example.myfood.remote.response.LocalDataSource
import com.example.myfood.remote.response.RemoteDataSource
import com.example.myfood.repository.Repository
import com.example.myfood.retrofit.RetrofitClientInstance
import com.example.myfood.ui.viewmodel.DetailsViewModel
import com.example.myfood.ui.viewmodel.HomeViewModel
import com.example.myfood.ui.viewmodel.MainViewModel

private fun getRetrofitService(): GetDataService {
    return RetrofitClientInstance.retrofitInstance.create(GetDataService::class.java)
}

private fun getRecipeDao(context: Context): RecipeDao {
    val recipeDataBase = RecipeDatabase.getDataBase(context.applicationContext)
    return recipeDataBase.recipeDao()
}


fun getRepository(context: Context): Repository {
    val remoteDataSource = RemoteDataSource(getRetrofitService())
    val localDataSource = LocalDataSource(getRecipeDao(context))
    return Repository(remoteDataSource, localDataSource)
}

fun getMainViewModel(context: Context): MainViewModel {
    val repository = getRepository(context)
    return MainViewModel(repository)
}

fun getHomeViewModel(context: Context): HomeViewModel {
    val repository = getRepository(context)
    return HomeViewModel(repository)
}

fun getDetailsViewModel(context: Context): DetailsViewModel {
    val repository = getRepository(context)
    return DetailsViewModel(repository)
}