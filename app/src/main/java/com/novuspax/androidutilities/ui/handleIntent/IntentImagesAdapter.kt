package com.novuspax.androidutilities.ui.handleIntent

import android.net.Uri
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.novuspax.androidutilities.databinding.InflaterIntentImageBinding

class IntentImagesAdapter(
    private val imagesList: ArrayList<Uri?>
) : RecyclerView.Adapter<IntentImagesAdapter.ImageVH>() {

    class ImageVH(val binding: InflaterIntentImageBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageVH {
        return ImageVH(
            InflaterIntentImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ImageVH, position: Int) {
        holder.binding.apply {
            image.setImageURI(imagesList[position])
        }
    }

    override fun getItemCount(): Int = imagesList.size

}