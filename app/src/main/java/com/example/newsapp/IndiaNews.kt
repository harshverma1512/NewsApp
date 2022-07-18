package com.example.newsapp


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.DATA.indiannewsdata
import com.example.newsapp.IndiaNews.IndianNewsAdapter.*
import com.example.newsapp.databinding.IndianewsBinding
import com.example.newsapp.databinding.ItemdataBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class IndiaNews : Fragment() {
    private var binding: IndianewsBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return IndianewsBinding.inflate(inflater, container, false).run {
            binding = this
            root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.recyclerView?.setHasFixedSize(true)
        getdata()

    }

    private fun getdata(){
        val instance = IndianNewsService.indiannews.getnews()
        instance.enqueue(object : Callback<indiannewsdata?> {
            override fun onResponse(
                call: Call<indiannewsdata?>,
                response: Response<indiannewsdata?>,
            ) {
                response.body().apply {
                    val adapter = IndianNewsAdapter(this?.articles as List<indiannewsdata.Article>)
                    binding?.recyclerView?.adapter = adapter
                }
            }

            override fun onFailure(call: Call<indiannewsdata?>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })

    }

    inner class IndianNewsAdapter(
        val article: List<indiannewsdata.Article>,
    ) : RecyclerView.Adapter<ViewHolder>() {

        inner class ViewHolder( AD: ItemdataBinding? = null) :
            RecyclerView.ViewHolder(AD?.root!!) {

                var title = AD?.title
                var description = AD?.description
                val clickID = AD?.listItem
                var image = AD?.imageView
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {


            return ViewHolder(ItemdataBinding.inflate(layoutInflater))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {


            article[position].title.also { holder.title?.text = it.toString() }
            article[position].description.also { holder.description?.text = it.toString() }
            holder.image?.let {
                Glide.with(requireContext()).load(article[position].urlToImage).into(it)
            }
            holder.clickID?.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(article[position].url)))
            }
        }

        override fun getItemCount(): Int {
          return  article.size
        }

    }


}




