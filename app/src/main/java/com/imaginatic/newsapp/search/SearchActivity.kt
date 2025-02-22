package com.imaginatic.newsapp.search

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.imaginatic.newsapp.MyApplication
import com.imaginatic.newsapp.WebNewsActivity
import com.imaginatic.newsapp.databinding.ActivitySearchBinding
import com.newsapp.core.data.Resource
import com.newsapp.core.di.ViewModelFactory
import com.newsapp.core.domain.model.NewsData
import com.newsapp.core.ui.NewsAdapter
import com.newsapp.core.ui.NewsAdapterClickListener
import javax.inject.Inject

class SearchActivity : AppCompatActivity(), NewsAdapterClickListener {
    @Inject
    lateinit var factory: ViewModelFactory
    private val searchViewModel: SearchViewModel by viewModels { factory }
    private lateinit var binding: ActivitySearchBinding
    private lateinit var newsAdapter: NewsAdapter

    private val searchObserver: Observer<Resource<List<NewsData>>> = Observer { news ->
        news.let { newsRes ->
            when (newsRes) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    newsRes.data?.let {
                        if (it.isNotEmpty()) {
                            newsAdapter.insetAll(it)
                        }
                    }
                }
                is Resource.Error -> {
                    Toast.makeText(this, newsRes.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MyApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarNewsSearch)
        supportActionBar?.title = "Search"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        searchViewModel.searchNews.observe(this, searchObserver)
        newsAdapter = NewsAdapter(this)
        with(binding.rvNewsSearch) {
            layoutManager = GridLayoutManager(context, 2)
            setHasFixedSize(true)
            adapter = newsAdapter
        }

        binding.btnSearchNews.setOnClickListener {
            if(!binding.etSearchNews.text.isNullOrEmpty()) {
                searchViewModel.setQuery(binding.etSearchNews.text.toString())
            }
        }
    }

    override fun onClickNews(news: NewsData) {
        val intent = Intent(this, WebNewsActivity::class.java)
        intent.putExtra("url", news.url)
        startActivity(intent)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }
}