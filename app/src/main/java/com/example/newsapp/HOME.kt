package com.example.newsapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.databinding.HomeBinding
import newsdata
import recycleviewAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response




class HOME : Fragment(){

    private var binding  : HomeBinding?= null
    lateinit var myAdapter: recycleviewAdapter
    lateinit var linearLayoutManager: LinearLayoutManager


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return HomeBinding.inflate(inflater, container, false).run {
            binding= this
            root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.recyclerView?.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(requireContext())
        binding?.recyclerView?.layoutManager = linearLayoutManager
        getnews()
}

    private fun getnews() {
        val news= NewsService.newsintence.getheadline()
        news.enqueue(object : Callback<newsdata> {
            override fun onResponse(call: Call<newsdata>, response: Response<newsdata>) {
                response.body()?.apply {
                    myAdapter = recycleviewAdapter(requireContext(),articles)
                    binding?.recyclerView?.adapter = myAdapter

                }
            }
            override fun onFailure(call: Call<newsdata>, t: Throwable) {
                Log.d("error", "Error")
            }

        })

    }

}