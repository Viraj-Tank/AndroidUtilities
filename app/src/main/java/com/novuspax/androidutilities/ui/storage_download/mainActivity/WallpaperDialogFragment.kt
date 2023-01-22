package com.novuspax.androidutilities.ui.storage_download.mainActivity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.novuspax.androidutilities.databinding.InflaterWallpaperDialogLayoutBinding

class WallpaperDialogFragment: DialogFragment() {

    private var binding: InflaterWallpaperDialogLayoutBinding? = null
    private var imageUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            imageUrl = it.getString("url")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = InflaterWallpaperDialogLayoutBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Glide.with(view.context).load(imageUrl).into(binding?.wallImage!!)
    }

    companion object {
        fun newInstance(imageUrl:String): WallpaperDialogFragment {
            //putting imageUrl to bundle
            val args = Bundle()
            args.putString("url",imageUrl)
            // and passing bundle as argument in fragment so we can retrieve it in onCreate method
            val fragment = WallpaperDialogFragment()
            fragment.arguments = args
            return fragment
        }
    }

}