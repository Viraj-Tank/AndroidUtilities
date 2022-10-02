package com.novuspax.androidutilities.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.novuspax.androidutilities.R
import com.novuspax.androidutilities.databinding.ActivityHomeBinding
import com.novuspax.androidutilities.databinding.ActivityStorageMainBinding
import com.novuspax.androidutilities.ui.mainActivity.StorageMainActivity
import com.novuspax.androidutilities.ui.qr_creator.QRMainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
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
            else -> {
                print("no id found!")
            }
        }
    }
}