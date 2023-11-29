package com.example.myfood

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.core.view.WindowInsetsControllerCompat
import com.example.myfood.databinding.ActivityMainBinding
import com.example.myfood.ui.BaseActivity
import com.example.myfood.ui.HomeActivity
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class MainActivity : BaseActivity(), EasyPermissions.RationaleCallbacks,
    EasyPermissions.PermissionCallbacks {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater).apply { setContentView(root) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupUI()
        readStorageTask()
    }


    private fun setupStatusBar() {
        window.statusBarColor = Color.TRANSPARENT
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = true
    }

    private fun setupUI() {
        setupStatusBar()
        binding.btnGetStarted.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
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

    companion object {
        private const val READ_STORAGE = 123
    }
}
