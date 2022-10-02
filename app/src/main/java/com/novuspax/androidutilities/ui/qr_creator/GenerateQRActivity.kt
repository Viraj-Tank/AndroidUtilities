package com.novuspax.androidutilities.ui.qr_creator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.novuspax.androidutilities.R
import com.novuspax.androidutilities.databinding.ActivityGenerateQractivityBinding
import com.novuspax.androidutilities.databinding.ActivityQrMainBinding

class GenerateQRActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityGenerateQractivityBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        title = "Generate QR Code"
    }
}