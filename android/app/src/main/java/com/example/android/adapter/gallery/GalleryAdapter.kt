package com.example.android.adapter.gallery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android.R
import com.example.android.GalleryViewModel
import com.example.android.databinding.GalleryItemBinding

class GalleryAdapter(private val galleryViewModel: GalleryViewModel, private val lifecycleOwner: LifecycleOwner):
    // 컨트롤 ( 버튼 클릭 등 )
    RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {
        inner class ViewHolder(view: View,
                               private val galleryViewModel: GalleryViewModel,
                               private val lifecycleOwner: LifecycleOwner): RecyclerView.ViewHolder(view) {
            private val galleryItem = GalleryItemBinding.bind(view).apply {
                viewModel = galleryViewModel
                lifecycleOwner = this@ViewHolder.lifecycleOwner //이거 안해주면 databinding에서 안바뀜;
            }
            fun bind(position: Int){
                galleryItem.checkPhotoData = galleryViewModel.checkCurrentPhoto[position]
            }
        }

    var galleryList = ArrayList<String>()
    fun setGalleryPath(list: ArrayList<String>){
        galleryList = list
        galleryViewModel.selectPhoto(list[0])
        notifyDataSetChanged()
        galleryViewModel.photoList(galleryList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.gallery_item, parent, false),
            galleryViewModel,
            lifecycleOwner
        )
    }

    // Set
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide
            .with(holder.itemView.context)
            .load(galleryList[position])
            .centerCrop()
            .into(holder.itemView.findViewById(R.id.iv))

        holder.itemView.setOnClickListener{
            galleryViewModel.selectPhoto(galleryList[position])
        }

        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return galleryList.size
    }

}