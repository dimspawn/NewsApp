package com.imaginatic.newsapp.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.imaginatic.newsapp.MyApplication
import com.imaginatic.newsapp.R
import com.imaginatic.newsapp.WebNewsActivity
import com.imaginatic.newsapp.databinding.ActivityMainBinding
import com.imaginatic.newsapp.read.ReadActivity
import com.imaginatic.newsapp.search.SearchActivity
import com.newsapp.core.data.Resource
import com.newsapp.core.di.ViewModelFactory
import com.newsapp.core.domain.model.NewsData
import com.newsapp.core.ui.NewsAdapter
import com.newsapp.core.ui.NewsAdapterClickListener
import javax.inject.Inject

class MainActivity : AppCompatActivity(), NewsAdapterClickListener {

    @Inject
    lateinit var factory: ViewModelFactory
    private val mainViewModel: MainViewModel by viewModels { factory }

    private lateinit var binding: ActivityMainBinding
    private lateinit var newsAdapter: NewsAdapter

    private fun shimmering(loading: Boolean = true) {
        if (loading) {
            binding.svNews.visibility = View.VISIBLE
            binding.sflNews.startShimmer()
            binding.sflNews2.startShimmer()
            binding.sflNews3.startShimmer()
            binding.sflNews4.startShimmer()
            binding.sflNews5.startShimmer()
        } else {
            binding.svNews.visibility = View.GONE
            binding.sflNews.stopShimmer()
            binding.sflNews2.stopShimmer()
            binding.sflNews3.stopShimmer()
            binding.sflNews4.stopShimmer()
        }
    }

    private val newsObserver: Observer<Resource<List<NewsData>>> = Observer { news ->
        news.let { newsRes ->
            when(newsRes) {
                is Resource.Loading -> {
                    shimmering()
                    binding.rvNews.visibility = View.GONE
                }
                is Resource.Success -> {
                    shimmering(false)
                    newsRes.data?.let {
                        if(it.isNotEmpty()) {
                            newsAdapter.insetAll(it)
                            binding.rvNews.visibility = View.VISIBLE
                        }
                    }
                }
                is Resource.Error -> {
                    shimmering(false)
                    binding.rvNews.visibility = View.GONE
                    Toast.makeText(this, newsRes.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private val readNewsObserver: Observer<Resource<String>> = Observer { reads ->
        reads.let { readRes ->
            when(readRes) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    readRes.data?.let {
                        val intent = Intent(this, WebNewsActivity::class.java)
                        intent.putExtra("url", it)
                        startActivity(intent)
                    }
                }
                is Resource.Error -> {
                    Toast.makeText(this, readRes.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onClickNews(news: NewsData) {
        mainViewModel.setReadNews(news)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MyApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarNews)
        supportActionBar?.title = "News"
        mainViewModel.mainNews.observe(this, newsObserver)
        mainViewModel.readNews.observe(this, readNewsObserver)

        val gridLayoutManager = GridLayoutManager(this, 2)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position % 5 == 0) 2 else 1
            }
        }

        newsAdapter = NewsAdapter(this)
        with(binding.rvNews) {
            layoutManager = gridLayoutManager
            adapter = newsAdapter
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_read -> {
                val intent = Intent(this, ReadActivity::class.java)
                startActivity(intent)
            }
            R.id.menu_search -> {
                val intent = Intent(this, SearchActivity::class.java)
                startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}