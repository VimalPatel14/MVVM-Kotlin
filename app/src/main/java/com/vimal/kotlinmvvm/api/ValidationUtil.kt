package com.vimal.kotlinmvvm.api

import com.vimal.kotlinmvvm.model.Movie

object ValidationUtil {
    fun validateMovie(movie: Movie) : Boolean {
        if (movie.name.isNotEmpty() && movie.category.isNotEmpty()) {
            return true
        }
        return false
    }
}