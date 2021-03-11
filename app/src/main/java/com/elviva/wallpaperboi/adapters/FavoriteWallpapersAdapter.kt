package com.elviva.wallpaperboi.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.elviva.wallpaperboi.R
import com.elviva.wallpaperboi.databinding.ItemWallpaperBinding
import com.elviva.wallpaperboi.models.Wallpaper
import com.elviva.wallpaperboi.util.Constants.mFavoritesList

open class FavoriteWallpapersAdapter (
    private val context: Context
) : RecyclerView.Adapter<FavoriteWallpapersAdapter.MyViewHolder>() {

    private var onClickListener : OnClickListener? = null

    private val diffCallback = object : DiffUtil.ItemCallback<Wallpaper>() {
        override fun areItemsTheSame(oldItem: Wallpaper, newItem: Wallpaper): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Wallpaper, newItem: Wallpaper): Boolean {
            return oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var wallpapers: List<Wallpaper>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemWallpaperBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FavoriteWallpapersAdapter.MyViewHolder, position: Int) {
        holder.binding.apply {
            val wallpaper = mFavoritesList[position]

            Glide
                .with(context)
                .load(wallpaper.urls.regular)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(ivWallpaper)

            cvWallpaper.setOnClickListener {
                if(onClickListener != null){
                    onClickListener!!.onClick(position)
                }
            }
        }
    }


    override fun getItemCount(): Int {
        return mFavoritesList.size
    }

    fun setOnClickListener(onClickListener: OnClickListener){
        this.onClickListener = onClickListener
    }

    interface OnClickListener{
        fun onClick(position: Int)
    }

    inner class MyViewHolder(val binding: ItemWallpaperBinding) : RecyclerView.ViewHolder(binding.root)


}