package com.novuspax.androidutilities.ui.mainActivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.novuspax.androidutilities.databinding.InflaterRamItemBinding
import com.novuspax.androidutilities.network.data.RAMResponse

class RAMAdapter(
    val onItemClick: (String, View) -> Unit
) : PagingDataAdapter<RAMResponse.Result, RAMAdapter.MyView>(DiffUTIL) {
    class MyView(val binding: InflaterRamItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: MyView, position: Int) {
        val model: RAMResponse.Result? = getItem(position)
        Glide.with(holder.binding.root.context).load(model?.image)
            .into(holder.binding.imgRAM)
        holder.binding.imgRAM.setOnClickListener {
            onItemClick(model?.image.toString(),it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView {
        return MyView(
            InflaterRamItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    companion object {
        val DiffUTIL = object : DiffUtil.ItemCallback<RAMResponse.Result>() {
            override fun areItemsTheSame(
                o: RAMResponse.Result,
                n: RAMResponse.Result
            ): Boolean = o == n

            override fun areContentsTheSame(
                o: RAMResponse.Result,
                n: RAMResponse.Result
            ): Boolean = o == n

        }
    }
}