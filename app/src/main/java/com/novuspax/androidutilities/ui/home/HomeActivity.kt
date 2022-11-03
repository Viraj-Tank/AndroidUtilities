package com.novuspax.androidutilities.ui.home

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.novuspax.androidutilities.R
import com.novuspax.androidutilities.databinding.ActivityHomeBinding
import com.novuspax.androidutilities.databinding.ActivityStorageMainBinding
import com.novuspax.androidutilities.ui.mainActivity.StorageMainActivity
import com.novuspax.androidutilities.ui.qr_creator.QRMainActivity
import com.novuspax.androidutilities.utils.sdkAbove12
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }
    private val notificationPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if(isGranted) {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        askNotificationPermissionIfAndroidTiramisu()
        initView()
    }

    private fun initView() {
        binding.rvHome.apply {
            layoutManager = LinearLayoutManager(this@HomeActivity)
            adapter = HomeAdapter {
                handleClick(it)
            }
        }
    }

    private fun handleClick(it: String) {
        when (it) {
            "storage" -> {
                startActivity(Intent(this@HomeActivity, StorageMainActivity::class.java))
            }
            "qr" -> {
                startActivity(Intent(this@HomeActivity, QRMainActivity::class.java))
            }
            "location" -> {
                startActivity(Intent(this@HomeActivity, LocationMainActivity::class.java))
            }
            else -> {
                print("no id found!")
            }
        }
    }

    private fun askNotificationPermissionIfAndroidTiramisu() {
        sdkAbove12 {
            notificationPermission.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        } ?: {
            print("No Action Required")
        }
    }
}