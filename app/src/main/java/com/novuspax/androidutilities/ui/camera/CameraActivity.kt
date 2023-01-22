package com.novuspax.androidutilities.ui.camera

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.novuspax.androidutilities.R
import com.novuspax.androidutilities.databinding.ActivityCameraBinding
import com.novuspax.androidutilities.utils.annotations.PermissionCheck
import com.novuspax.androidutilities.utils.annotations.withPermissionCheck

class CameraActivity : AppCompatActivity() {
    private val binding: ActivityCameraBinding by lazy {
        ActivityCameraBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        withPermissionCheck(this, this) { takePicture() }
    }

    @PermissionCheck(Manifest.permission.CAMERA)
    fun takePicture() {
        Toast.makeText(this, "run", Toast.LENGTH_SHORT).show()
    }
}