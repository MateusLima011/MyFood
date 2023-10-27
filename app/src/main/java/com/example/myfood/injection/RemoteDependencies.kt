package com.example.myfood.injection

import com.example.myfood.interfaces.GetDataService
import com.example.myfood.remote.response.RemoteDataSource
import com.example.myfood.repository.Repository
import com.example.myfood.retrofit.RetrofitClientInstance
import com.example.myfood.ui.viewmodel.DetailsViewModel
import com.example.myfood.ui.viewmodel.HomeViewModel
import com.example.myfood.ui.viewmodel.MainViewModel

private fun getRetrofitService(): GetDataService {
    return RetrofitClientInstance.retrofitInstance.create(GetDataService::class.java)
}

private val remoteDataSource = RemoteDataSource(getRetrofitService())
fun getRepository() = Repository(remoteDataSource)

fun getMainViewModel() = MainViewModel(getRepository())
fun getHomeViewModel() = HomeViewModel(getRepository())

fun getDetailsViewModel() = DetailsViewModel(getRepository())