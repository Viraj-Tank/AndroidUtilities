package com.novuspax.androidutilities.ui.qr_creator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.novuspax.androidutilities.R
import com.novuspax.androidutilities.databinding.ActivityQrMainBinding
import com.novuspax.androidutilities.databinding.ActivityScanQractivityBinding

class ScanQRActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityScanQractivityBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        title = "Scan QR Code"
    }
}