package org.hyperskill.simplewallpaper

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.squareup.picasso.Picasso
import org.hyperskill.simplewallpaper.databinding.RecyclerItemBinding

class RecyclerAdapter(val imageList : List<String>) : RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val holder = RecyclerViewHolder(
            RecyclerItemBinding.inflate(
                LayoutInflater.from(parent.context)
                , parent,false))

        holder.binding.root.setOnClickListener {
            val pos = holder.adapterPosition
            val bundle = bundleOf("imageClick" to imageList[pos])
            findNavController(parent).navigate(R.id.action_mainFragment_to_wallpaperDetails,bundle)
        }

        return holder
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val item = imageList[position]

        Picasso.get()
            .load(item)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.error)
            .into(holder.binding.imageView)

    }

    class RecyclerViewHolder(val binding: RecyclerItemBinding) : ViewHolder(binding.root)
}