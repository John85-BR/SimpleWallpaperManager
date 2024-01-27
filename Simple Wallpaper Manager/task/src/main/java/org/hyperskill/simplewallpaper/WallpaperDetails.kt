package org.hyperskill.simplewallpaper

import android.app.WallpaperManager
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.core.graphics.drawable.toBitmap
import com.squareup.picasso.Picasso

class WallpaperDetails : Fragment() {

    private var imageView : ImageView? = null
    private var lockButton : ImageButton? = null
    private var mainScreenButton : ImageButton? = null
    private var lockMainScreenButton : ImageButton? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wallpaper_details, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageView = view.findViewById(R.id.wallpaperImageView)
        lockButton = view.findViewById(R.id.lockScreenSetBtn)
        mainScreenButton = view.findViewById(R.id.screenSetBtn)
        lockMainScreenButton = view.findViewById(R.id.lockAndMainScreenSetBtn)

        val wallpaperManager = WallpaperManager.getInstance(requireContext())

        val image = arguments?.getString("imageClick")

        Picasso.get()
            .load(image)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.error)
            .into(imageView)

        lockButton?.setOnClickListener {

            Picasso.get()
                .load(image)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .into(imageView)

            val drawable = imageView?.drawable
            val bitmap = drawable?.toBitmap()

            wallpaperManager.setBitmap(bitmap,null,false,WallpaperManager.FLAG_LOCK)

        }

        mainScreenButton?.setOnClickListener {
            Picasso.get()
                .load(image)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .into(imageView)

            val drawable = imageView?.drawable
            val bitmap = drawable?.toBitmap()

            wallpaperManager.setBitmap(bitmap,null,false,WallpaperManager.FLAG_SYSTEM)
        }

        lockMainScreenButton?.setOnClickListener {
            Picasso.get()
                .load(image)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .into(imageView)

            val drawable = imageView?.drawable
            val bitmap = drawable?.toBitmap()

            wallpaperManager.setBitmap(bitmap,null,false,WallpaperManager.FLAG_LOCK)
            wallpaperManager.setBitmap(bitmap,null,false,WallpaperManager.FLAG_SYSTEM)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        imageView = null
        lockButton = null
        mainScreenButton = null
        lockMainScreenButton = null
    }
}