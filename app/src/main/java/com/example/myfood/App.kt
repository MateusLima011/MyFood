package com.example.myfood

import android.app.Application
import com.example.myfood.interfaces.GetDataService

class App : Application() {
    object AppInitializer {


        private var dataService: GetDataService? = null
        fun getDataService(): GetDataService {
            return dataService ?: throw IllegalStateException("GetDataService não inicializado")
        }
    }
}