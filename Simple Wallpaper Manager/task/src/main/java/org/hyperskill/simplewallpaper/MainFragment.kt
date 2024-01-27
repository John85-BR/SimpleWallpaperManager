package org.hyperskill.simplewallpaper

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.Serializable

class MainFragment : Fragment() {

    private var recyclerView : RecyclerView? = null
    private var textView : TextView? = null

    private var callback: MainFragment.PassingInfoMainFromFragment? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = context as MainFragment.PassingInfoMainFromFragment
    }
    override fun onDetach() {
        super.onDetach()
        callback = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)
        textView = view.findViewById(R.id.emptyListTv)

        val defaultUrlList = listOf(
            "https://ucarecdn.com/26d28a62-ced1-437c-82b2-faae9cb65920/",
            "https://ucarecdn.com/ce2e77eb-553b-4e4a-82b7-cbd2f6ce0ac4/",
            "https://ucarecdn.com/f4dce147-bf2a-4852-8064-a4bdb766ca4e/",
            "https://ucarecdn.com/8b1c0fbf-3c07-425a-943e-d81219d12440/",
            "https://ucarecdn.com/b8d0e783-afaa-46b5-973d-cd433edf59ef/",
        )



        val imageList = callback?.passingInfoMainFromFragment()?:defaultUrlList

        val recyclerAdapter = RecyclerAdapter(imageList as List<String>)

        recyclerView?.apply {
            adapter = recyclerAdapter
            layoutManager = GridLayoutManager(context,2)
        }



        if(!imageList.isNullOrEmpty()){
            recyclerView?.visibility = VISIBLE
            textView?.visibility = GONE
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        recyclerView = null
        textView = null
    }

    interface PassingInfoMainFromFragment {
        fun passingInfoMainFromFragment() : Serializable?
    }


}