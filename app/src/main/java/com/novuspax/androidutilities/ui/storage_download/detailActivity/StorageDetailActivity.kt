package com.novuspax.androidutilities.ui.detailActivity

import android.app.DownloadManager
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.transition.Fade
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.novuspax.androidutilities.databinding.ActivityStorageDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class StorageDetailActivity : AppCompatActivity(), View.OnClickListener {

    private val TAG = "DetailActivity"
    private var imageUrl = ""
    var myDownloadId: Long = 0
    private val binding by lazy {
        ActivityStorageDetailBinding.inflate(layoutInflater)
    }
    private val requestStoragePermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        if (it) {
            saveFileUsingDownloadManager()
        } else {
            Toast.makeText(this@StorageDetailActivity, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initSetup()
        imageUrl = intent?.getStringExtra("imageUrl").toString()
        Glide.with(this).load(imageUrl).centerCrop().into(binding.imgRAM)
        binding.btnToFiles.setOnClickListener(this)
        binding.btnDownloadManager.setOnClickListener(this)

        /***
         *  using broadcast to fire notification when download completes
         */
        registerReceiver(broadcastReceiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))

    }

    private fun initSetup() {
        val fade: Fade = Fade()
        val decor: View = window.decorView

        fade.excludeTarget(android.R.id.statusBarBackground, true)
        fade.excludeTarget(android.R.id.navigationBarBackground, true)

        window?.enterTransition = fade
        window?.exitTransition = fade
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.btnToFiles -> {
                saveToPrivateFilesFolder()
            }
            binding.btnDownloadManager -> {
                requestStoragePermission.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }
    }

    private fun saveFileUsingDownloadManager() {
        try {
            val imageToBeDownload = DownloadManager.Request(Uri.parse(imageUrl))
                .setTitle(imageUrl.substring(0..10))
                .setDescription("Downloading Image...")
                .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI)
                .setMimeType("image/jpeg")
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, File.separator + "image.jpeg")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setAllowedOverMetered(true)

            val downloadManager = getSystemService(Service.DOWNLOAD_SERVICE) as DownloadManager
            myDownloadId = downloadManager.enqueue(imageToBeDownload)

        } catch (e: Exception) {
            Toast.makeText(this@StorageDetailActivity, "something went wrong", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveToPrivateCacheFolder() {
//        val file = File(filesDir, "my folder")
//        if (file.exists().not()) {
//            file.mkdir()
//        }
//        Log.e(TAG, "saveToPrivateCacheFolder: $filesDir")

    }

    private fun saveToPrivateFilesFolder() {

    }

    private var broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            if (id == myDownloadId) {
                Toast.makeText(this@StorageDetailActivity, "Download Complete", Toast.LENGTH_SHORT).show()
            }
        }
    }

}