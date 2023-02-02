package com.vimal.kotlinmvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.vimal.kotlinmvvm.adapter.MovieAdapter
import com.vimal.kotlinmvvm.api.MainRepository
import com.vimal.kotlinmvvm.api.RetrofitService
import com.vimal.kotlinmvvm.interfaces.ItemClickListener
import com.vimal.kotlinmvvm.viewmodel.MainViewModel
import com.vimal.kotlinmvvm.viewmodel.MyViewModelFactory

class MainActivity : AppCompatActivity(), ItemClickListener {

    lateinit var viewModel: MainViewModel
    lateinit var progressDialog : ProgressBar
    lateinit var recyclerview : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = MovieAdapter(this@MainActivity,this@MainActivity)
        progressDialog = findViewById(R.id.progressDialog)
        recyclerview = findViewById(R.id.recyclerview)

        val retrofitService = RetrofitService.getInstance()
        val mainRepository = MainRepository(retrofitService)
        recyclerview.adapter = adapter

        viewModel = ViewModelProvider(this, MyViewModelFactory(mainRepository)).get(MainViewModel::class.java)
        viewModel.movieList.observe(this) {
            progressDialog.visibility = View.GONE
            adapter.setMovies(it)
        }
        viewModel.errorMessage.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
        viewModel.loading.observe(this, Observer {
            if (it) {
                progressDialog.visibility = View.VISIBLE
            } else {
                progressDialog.visibility = View.GONE
            }
        })
        viewModel.getAllMovies()
    }

    override fun onItemClick(position: Int) {
        Toast.makeText(this@MainActivity, "Position Clicked: " + position, Toast.LENGTH_SHORT).show()
    }

    override fun onLongClick(position: Int) {
        //do long click here
    }
}