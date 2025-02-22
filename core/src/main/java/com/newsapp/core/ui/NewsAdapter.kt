package com.newsapp.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.newsapp.core.R
import com.newsapp.core.databinding.NewsItemListBinding
import com.newsapp.core.domain.model.NewsData
import com.newsapp.core.utils.Helper

class NewsAdapter(private val listener: NewsAdapterClickListener): RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    private var news = mutableListOf<NewsData>()

    private fun insertOrUpdate(newData: NewsData) {
        val index = news.indexOfFirst { it.url == newData.url }
        if (index != -1) {
            news[index] = newData
            notifyItemChanged(index)
        } else {
            news.add(newData)
            notifyItemInserted(news.size - 1)
        }
    }

    fun insetAll(news: List<NewsData>) {
        for (newData in news) {
            insertOrUpdate(newData)
        }
    }

    inner class NewsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = NewsItemListBinding.bind(itemView)
        fun bind(data: NewsData) {
            with(itemView) {
                if (data.urlToString != null) {
                    Glide.with(context).load(data.urlToString).into(binding.ivImageNews)
                    binding.ivImageNews.scaleType = ImageView.ScaleType.CENTER_CROP
                } else {
                    binding.ivImageNews.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.baseline_broken_image_24))
                    binding.ivImageNews.scaleType = ImageView.ScaleType.CENTER_INSIDE
                }
                binding.tvTitleNews.text = data.title
                binding.tvDescNews.text = data.description
                binding.tvAuthorNews.text = data.author
                binding.tvNameNews.text = data.sourceName
                binding.tvPublishNews.text = Helper.timeConverter(data.publishedAt)
                setOnClickListener {
                    listener.onClickNews(data)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = NewsItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val data = news[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = news.size
}