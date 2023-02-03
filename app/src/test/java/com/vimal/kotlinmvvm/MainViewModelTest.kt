package com.vimal.kotlinmvvm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.vimal.kotlinmvvm.api.MainRepository
import com.vimal.kotlinmvvm.api.NetworkState
import com.vimal.kotlinmvvm.api.RetrofitService
import com.vimal.kotlinmvvm.model.Movie
import com.vimal.kotlinmvvm.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class MainViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()
    lateinit var mainViewModel: MainViewModel
    lateinit var mainRepository: MainRepository

    @get:Rule
    val instantTaskExecutionRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(testDispatcher)
        mainRepository = mock(MainRepository::class.java)
        mainViewModel = MainViewModel(mainRepository)
    }

    @Test
    fun getAllMoviesTest() {
        runBlocking {
            Mockito.`when`(mainRepository.getAllMovies())
                .thenReturn(NetworkState.Success(listOf<Movie>(Movie("movie", "", "new"))))
            mainViewModel.getAllMovies()
            val result = mainViewModel.movieList.getOrAwaitValue()
            assertEquals(listOf<Movie>(Movie("movie", "", "new")), result)
        }
    }

    @Test
    fun `empty movie list test`() {
        runBlocking {
            Mockito.`when`(mainRepository.getAllMovies())
                .thenReturn(NetworkState.Success(listOf<Movie>()))
            mainViewModel.getAllMovies()
            val result = mainViewModel.movieList.getOrAwaitValue()
            assertEquals(listOf<Movie>(), result)
        }
    }
}