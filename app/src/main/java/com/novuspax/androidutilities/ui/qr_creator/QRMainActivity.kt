package com.novuspax.androidutilities.ui.qr_creator

import android.content.Intent
import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import com.novuspax.androidutilities.R
import com.novuspax.androidutilities.databinding.ActivityQrMainBinding
import com.novuspax.androidutilities.utils.QRGenerator.QRGContents
import com.novuspax.androidutilities.utils.QRGenerator.QRGEncoder
import com.novuspax.androidutilities.utils.sdkAboveR

class QRMainActivity : AppCompatActivity(), View.OnClickListener {
    private val binding by lazy {
        ActivityQrMainBinding.inflate(layoutInflater)
    }
    private var width = 0
    private var height = 0
    private var dimen = 0

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
        when (view) {
            binding.btnGenerateQR -> {
                sdkAboveR {
                    val windowMetrics = windowManager.currentWindowMetrics
                    val insets = windowMetrics.windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
                    width = windowMetrics.bounds.width()
                    height = windowMetrics.bounds.height()
                    dimen = if (width < height) width else height

                } ?: run {
                    val manager = getSystemService(WINDOW_SERVICE) as WindowManager
                    val display = manager.defaultDisplay
                    val point = Point()
                    display.getSize(point)
                    width = point.x
                    height = point.y
                    dimen = if (width < height) width else height
                }

                QRGEncoder(
                    binding.etTextQR.text.toString(),
                    null,
                    QRGContents.Type.TEXT,
                    dimen
                )
            }
            binding.btnScanQR -> {
                startActivity(Intent(this@QRMainActivity, ScanQRActivity::class.java))
            }
        }
    }

}