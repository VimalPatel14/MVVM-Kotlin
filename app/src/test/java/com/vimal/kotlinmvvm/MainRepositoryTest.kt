package com.vimal.kotlinmvvm

import com.vimal.kotlinmvvm.api.MainRepository
import com.vimal.kotlinmvvm.api.RetrofitService
import com.vimal.kotlinmvvm.model.Movie
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

@RunWith(JUnit4::class)
class MainRepositoryTest {

    lateinit var mainRepository: MainRepository

    @Mock
    lateinit var apiService: RetrofitService

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        mainRepository = MainRepository(apiService)
    }

    @Test
    fun `get all movie test`() {
        runBlocking {
            Mockito.`when`(apiService.getAllMovies()).thenReturn(Response.success(listOf<Movie>()))
            val response = mainRepository.getAllMovies()
            assertNotNull(response)
        }
    }
}