package com.example.myfood

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.example.myfood.databinding.ActivityMainBinding
import com.example.myfood.injection.getMainViewModel
import com.example.myfood.ui.BaseActivity
import com.example.myfood.ui.HomeActivity
import com.example.myfood.ui.adapter.MainCategoryAdapter
import com.example.myfood.ui.adapter.SubCategoryAdapter
import com.example.myfood.ui.viewdata.CategoryItemsState
import com.example.myfood.ui.viewdata.MealState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class MainActivity : BaseActivity(), EasyPermissions.RationaleCallbacks,
    EasyPermissions.PermissionCallbacks {

    private val mainViewModel by lazy { getMainViewModel(this) }
    private val categoriesAdapter = MainCategoryAdapter()
    private val mealsAdapter = SubCategoryAdapter()
    private var READ_STORAGE = 123

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater).apply { setContentView(root) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupObservers()
        setupUI()
        readStorageTask()
    }

    private fun setupObservers() {
        mainViewModel.categoryListLiveData.observe(this) { state ->
            when (state) {
                is CategoryItemsState.Success -> categoriesAdapter.setData(state.data)
                is CategoryItemsState.Failure -> handleFailure("Falha ao Buscar Categorias")
            }
        }

        mainViewModel.mealListLiveData.observe(this) { state ->
            when (state) {
                is MealState.Success -> mealsAdapter.setData(state.data)
                is MealState.Failure -> handleFailure("Falha ao buscar refeições")
            }
        }
    }

    private fun setupUI() {
        binding.btnGetStarted.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun handleFailure(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun hasReadStoragePermission(): Boolean {
        return EasyPermissions.hasPermissions(
            this,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }

    private fun readStorageTask() {
        if (hasReadStoragePermission()) {
            onPermissionsGranted(
                READ_STORAGE,
                mutableListOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            )
        } else {
            EasyPermissions.requestPermissions(
                this,
                "This app needs access to your storage",
                READ_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onRationaleAccepted(p0: Int) {}

    override fun onRationaleDenied(p0: Int) {}

    override fun onPermissionsGranted(p0: Int, p1: MutableList<String>) {}

    override fun onPermissionsDenied(p0: Int, p1: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, p1)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }
}
