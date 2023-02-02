package com.vimal.kotlinmvvm.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vimal.kotlinmvvm.R
import com.vimal.kotlinmvvm.api.ValidationUtil
import com.vimal.kotlinmvvm.interfaces.ItemClickListener
import com.vimal.kotlinmvvm.model.Movie

class MovieAdapter(val context: Context, private val itemClickListener: ItemClickListener) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    var movieList = mutableListOf<Movie>()

    fun setMovies(movies: List<Movie>) {
        this.movieList = movies.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_movie, parent, false)
        return ViewHolder(inflater)
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageview)
        val mainlay: CardView = itemView.findViewById(R.id.mainlay)
        val name: TextView = itemView.findViewById(R.id.name)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        movieList[position].let {
            if (ValidationUtil.validateMovie(it)) {
                holder.name.text = it.name
                Glide.with(holder.itemView.context).load(it.imageUrl)
                    .placeholder(R.drawable.ic_launcher_background).into(holder.imageView)
                holder.mainlay.setOnClickListener() {
                    var pos = position
                    pos++
                    itemClickListener.onItemClick(pos)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return movieList.size
    }
}
