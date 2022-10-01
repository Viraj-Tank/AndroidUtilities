package com.novuspax.androidutilities.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.novuspax.androidutilities.databinding.InflaterHomeItemBinding

class HomeAdapter(
    val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<HomeAdapter.HomeHolder>() {

    class HomeHolder(
        val binding: InflaterHomeItemBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeHolder {
        return HomeHolder(
            InflaterHomeItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: HomeHolder, position: Int) {
        holder.binding.apply {
            tvTitle.text = homeItems.getOrNull(position)?.second
            root.setOnClickListener {
                onItemClick(homeItems.getOrNull(position)?.first.toString())
            }
        }
    }

    override fun getItemCount(): Int = homeItems.size

}