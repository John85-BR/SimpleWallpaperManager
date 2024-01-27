package org.hyperskill.simplewallpaper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.hyperskill.simplewallpaper.databinding.ActivityMainBinding
import java.io.Serializable

class MainActivity : AppCompatActivity(), MainFragment.PassingInfoMainFromFragment {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun passingInfoMainFromFragment(): Serializable? = intent.extras?.getSerializable("imageUrlList")
}
