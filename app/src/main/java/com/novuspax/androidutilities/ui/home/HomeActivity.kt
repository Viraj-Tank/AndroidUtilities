package com.novuspax.androidutilities.ui.home

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.novuspax.androidutilities.databinding.ActivityHomeBinding
import com.novuspax.androidutilities.ui.camera.CameraActivity
import com.novuspax.androidutilities.ui.handleIntent.HandleIntentActivity
import com.novuspax.androidutilities.ui.mainActivity.StorageMainActivity
import com.novuspax.androidutilities.ui.qr_creator.QRMainActivity
import com.novuspax.androidutilities.utils.utility.sdkAbove12
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
        handleIntentData()
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
            "cam" -> {
                startActivity(Intent(this@HomeActivity, CameraActivity::class.java))
            }
            else -> {
                print("no id found!")
            }
        }
    }

    private fun askNotificationPermissionIfAndroidTiramisu() {
        sdkAbove12 {
            notificationPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
        } ?: run {
            print("No Action Required")
        }
    }

    private fun handleIntentData() {
        when {
            intent?.action == Intent.ACTION_SEND -> {
                if ("text/plain" == intent.type) {
                    handleSendText(intent)
                } else if (intent.type?.startsWith("image/") == true) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        handleSendImage(intent)
                    }
                }
            }
            intent?.action == Intent.ACTION_SEND_MULTIPLE
                    && intent.type?.startsWith("image/") == true -> {
                handleSendMultipleImages(intent)
            }
        }
    }

    private fun handleSendText(intent: Intent) {
        intent.getStringExtra(Intent.EXTRA_TEXT)?.let {
            startActivity(Intent(this@HomeActivity,HandleIntentActivity::class.java).putExtra(
                "text",it
            ))
        }
    }

    private fun handleSendImage(intent: Intent) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                (intent.getParcelableExtra(Intent.EXTRA_STREAM,Parcelable::class.java) as? Uri)?.let {
                    startActivity(Intent(this@HomeActivity,HandleIntentActivity::class.java).putExtra(
                        "image",it
                    ))
                }
            } else {
                (intent.getParcelableExtra<Parcelable>(Intent.EXTRA_STREAM) as? Uri)?.let {
                    startActivity(Intent(this@HomeActivity,HandleIntentActivity::class.java).putExtra(
                        "image",it
                    ))
                }
            }
    }

    private fun handleSendMultipleImages(intent: Intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM,Parcelable::class.java)?.let {
                startActivity(Intent(this@HomeActivity,HandleIntentActivity::class.java).putExtra(
                    "images",  it
                ))
            }
        } else {
            intent.getParcelableArrayListExtra<Parcelable>(Intent.EXTRA_STREAM)?.let {
                startActivity(Intent(this@HomeActivity,HandleIntentActivity::class.java).putExtra(
                    "images",  it
                ))
            }
        }
    }
}