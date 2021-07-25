package com.ufonaut.test.ui.activity

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.ufonaut.test.R
import com.ufonaut.test.databinding.ActivityMainBinding
import com.ufonaut.test.ui.adapter.MainViewPagerAdapter

abstract class BasePermissionsActivity : AppCompatActivity() {

    override fun onResume() {
        super.onResume()
        askPermissions()
    }

    private fun hasPermissions(): Boolean {
        return permissions.all {
            ContextCompat.checkSelfPermission(
                this,
                it.key
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun askPermissions() {
        if (hasPermissions()) {
            onPermissionsPersist()
        } else {
            requestPermissions(permissions.keys.toTypedArray(), PERMISSIONS_REQUEST_ID)
        }
    }

    abstract fun onPermissionsPersist()

    private fun onPermissionsDenied() {
        AlertDialog.Builder(this)
            .setMessage("Please enable this permission")
            .setPositiveButton(getString(R.string.ok)) { dialogInterface, _ ->
                dialogInterface.dismiss()
                finish()
            }.show()
    }

    private fun allPermissionsGranted(grantResults: IntArray): Boolean {
        for (result in grantResults)
            if (result != PackageManager.PERMISSION_GRANTED)
                return false

        return grantResults.size == permissions.size
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSIONS_REQUEST_ID -> {
                if (allPermissionsGranted(grantResults)) {
                    onPermissionsPersist()
                } else {
                    onPermissionsDenied()
                }
                return
            }
        }
    }

    companion object {
        const val PERMISSIONS_REQUEST_ID = 1234
        val permissions = hashMapOf(Pair(Manifest.permission.ACCESS_FINE_LOCATION, "Coarse location required"))
    }
}