package com.vimal.kotlinmvvm.api

import com.vimal.kotlinmvvm.model.Movie

class MainRepository constructor(private val retrofitService: RetrofitService) {

    suspend fun getAllMovies() : NetworkState<List<Movie>> {
            val response = retrofitService.getAllMovies()
            return if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    NetworkState.Success(responseBody)
                } else {
                    NetworkState.Error(response)
                }
            } else {
                NetworkState.Error(response)
            }
        }

}