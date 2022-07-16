package com.example.newsapp

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.databinding.HomeBinding
import newsdata
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide





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

        var progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage("Fetching News....")
        progressDialog.setCancelable(false)
        progressDialog.show()
        binding?.recyclerView?.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(requireContext())
        binding?.recyclerView?.layoutManager = linearLayoutManager
        getnews()
        if (progressDialog.isShowing) progressDialog.dismiss()
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

   inner class recycleviewAdapter(private val context: Context, val articles: List<newsdata.Article>) : RecyclerView.Adapter<recycleviewAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.itemdata, parent, false)
            return ViewHolder(view)
        }
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.title.text = articles[position].title
            holder.description.text = articles[position].description
            Glide.with(context).load(articles[position].urlToImage).into(holder.imageView)
        }
        override fun getItemCount(): Int {
            return articles.size
        }

       inner class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
            val imageView: ImageView = itemView.findViewById(R.id.imageView)
            val title: TextView = itemView.findViewById(R.id.title)
            val description: TextView = itemView.findViewById(R.id.description)

        }
    }

}