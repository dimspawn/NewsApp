package com.imaginatic.newsapp.read

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.imaginatic.newsapp.MyApplication
import com.imaginatic.newsapp.WebNewsActivity
import com.imaginatic.newsapp.databinding.ActivityReadBinding
import com.newsapp.core.data.Resource
import com.newsapp.core.di.ViewModelFactory
import com.newsapp.core.domain.model.NewsData
import com.newsapp.core.ui.NewsAdapter
import com.newsapp.core.ui.NewsAdapterClickListener
import javax.inject.Inject

class ReadActivity : AppCompatActivity(), NewsAdapterClickListener {
    @Inject
    lateinit var factory: ViewModelFactory
    private val readViewModel: ReadViewModel by viewModels { factory }
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var binding: ActivityReadBinding

    private val newsObserver: Observer<Resource<List<NewsData>>> = Observer { news ->
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
        binding = ActivityReadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        readViewModel.readNews.observe(this, newsObserver)

        setSupportActionBar(binding.toolbarNewsRead)
        supportActionBar?.title = "Read"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        newsAdapter = NewsAdapter(this)
        with(binding.rvNewsRead) {
            layoutManager = GridLayoutManager(context, 2)
            adapter = newsAdapter
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