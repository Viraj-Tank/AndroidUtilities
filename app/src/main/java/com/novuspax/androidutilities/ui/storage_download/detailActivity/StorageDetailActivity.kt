package com.novuspax.androidutilities.ui.detailActivity

import android.app.DownloadManager
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.transition.Fade
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.graphics.drawable.toBitmap
import com.bumptech.glide.Glide
import com.novuspax.androidutilities.databinding.ActivityStorageDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream
import java.util.*


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
        binding.btnShareImage.setOnClickListener(this)

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
                saveToPrivateFilesFolder(binding.imgRAM.drawable.toBitmap())
            }
            binding.btnDownloadManager -> {
                requestStoragePermission.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
            binding.btnShareImage -> {

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

    private fun saveToPrivateFilesFolder(bitmap: Bitmap) {
        val imageStoreFolder = File(getExternalFilesDir(null), "images")
        try {
            imageStoreFolder.mkdirs()
            val file = File(imageStoreFolder, "${Calendar.getInstance().time.time}.png")
            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, outputStream)
            outputStream.flush()
            outputStream.close()
            Toast.makeText(this, "success", Toast.LENGTH_LONG).show()
        } catch (e: java.lang.Exception) {
            Toast.makeText(this, "cant save image something went wrong" + e.message, Toast.LENGTH_LONG).show()
        }
    }

    private var broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            if (id == myDownloadId) {
                Toast.makeText(this@StorageDetailActivity, "Download Complete", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun shareImageWithTextIfExist(bitmap: Bitmap) {
        val uri = saveToPrivateFilesFolder(bitmap)
        val intent = Intent(Intent.ACTION_SEND)
//        intent.putExtra(Intent.EXTRA_STREAM, uri)
        intent.putExtra(Intent.EXTRA_TEXT, "Sharing Image")
        intent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here")
        intent.type = "image/*"
        startActivity(Intent.createChooser(intent, "Share Via"))
    }

}