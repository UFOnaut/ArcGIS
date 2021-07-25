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
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BasePermissionsActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setupUI(binding: ActivityMainBinding) {
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        binding.viewPager.adapter = MainViewPagerAdapter(supportFragmentManager)
    }

    override fun onPermissionsPersist() {
        setupUI(binding)
    }
}