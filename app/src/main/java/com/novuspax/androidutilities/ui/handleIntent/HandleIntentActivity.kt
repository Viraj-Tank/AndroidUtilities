package com.novuspax.androidutilities.ui.handleIntent

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.novuspax.androidutilities.R
import com.novuspax.androidutilities.databinding.ActivityHandleIntentBinding
import com.novuspax.androidutilities.utils.getURIsFromList

class HandleIntentActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityHandleIntentBinding.inflate(layoutInflater)
    }
    private val TAG = "HandleIntentActivity"
    private var imagesAdapter:IntentImagesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
        binding.rvMultipleImages.post {
            handleIntentData()
        }
    }

    private fun initView() {
        binding.rvMultipleImages.apply {
            layoutManager = GridLayoutManager(this@HandleIntentActivity,2)
        }
    }

    private fun handleIntentData() {
        if(intent.hasExtra("name")) {
            binding.tvText.text = intent.getStringExtra("name")
        }

        if(intent.hasExtra("image")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                (intent.getParcelableExtra("image",Parcelable::class.java) as? Uri)?.let {
                    Log.e(TAG, "handleIntentData: image", )
                    binding.imgSingle.setImageURI(it)
                }
            } else {
                (intent.getParcelableExtra<Parcelable>("image") as? Uri)?.let {
                    Log.e(TAG, "handleIntentData: image", )
                    binding.imgSingle.setImageURI(it)
                }
            }
        }

        if(intent.hasExtra("images")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableArrayListExtra("images",Parcelable::class.java)?.let {
                    Log.e(TAG, "handleIntentData: list${it.getURIsFromList().size}", )
                    imagesAdapter = IntentImagesAdapter(it.getURIsFromList())
                    binding.rvMultipleImages.adapter = imagesAdapter
                }
            } else {
                intent.getParcelableArrayListExtra<Parcelable>("images")?.let {
                    Log.e(TAG, "handleIntentData: list${it.getURIsFromList().size}", )
                    imagesAdapter = IntentImagesAdapter(it.getURIsFromList())
                    binding.rvMultipleImages.adapter = imagesAdapter
                }
            }
        }
    }
}