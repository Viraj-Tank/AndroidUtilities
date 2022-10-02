package com.novuspax.androidutilities.ui.qr_creator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.novuspax.androidutilities.R
import com.novuspax.androidutilities.databinding.ActivityQrMainBinding

class QRMainActivity : AppCompatActivity(), View.OnClickListener {
    private val binding by lazy {
        ActivityQrMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        title = "QR Code"
        binding.apply {
            btnGenerateQR.setOnClickListener(this@QRMainActivity)
            btnScanQR.setOnClickListener(this@QRMainActivity)
        }
    }

    override fun onClick(view: View?) {
        when(view){
            binding.btnGenerateQR -> {
                startActivity(Intent(this@QRMainActivity,GenerateQRActivity::class.java))
            }
            binding.btnScanQR -> {
                startActivity(Intent(this@QRMainActivity,ScanQRActivity::class.java))
            }
        }
    }

}