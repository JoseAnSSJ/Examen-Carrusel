package com.example.carrucel

import com.example.carrucel.core.Resource
import com.example.carrucel.data.remote.MovieApiService
import com.example.carrucel.data.remote.dto.MoviesResponseDTO
import com.example.carrucel.data.repository.MoviesRepositoryImpl
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class MovieRepositoryImplTest {
    private val api = mockk<MovieApiService>()
    private val repository = MoviesRepositoryImpl(api)

    val dto = MoviesResponseDTO(
        id = 2,
        title = "Jumanji",
        description = "Descipcion...",
        year = 2019,
        imageUrl = "https://google/imagen/2.jpg",
        genre = listOf("Adventure", "Comedy"),
        stars = 3.4
    )

    @Test
    fun `emite Loading y luego Success`() = runTest {
        coEvery { api.getMovies() } returns listOf(dto)

        val states = repository.getMovies().toList()

        assertEquals(2, states.size)
        assertTrue(states[0] is Resource.Loading)
        val success = states[1] as Resource.Success
        assertEquals("Jumanji", success.data[0].title)
    }

    @Test
    fun `sin internet emite Error`() = runTest {
        coEvery { api.getMovies() } throws IOException()

        val states = repository.getMovies().toList()

        assertTrue(states[0] is Resource.Loading)
        assertEquals("Sin conexión. Revisa tu internet.", (states[1] as Resource.Error).message)
    }

    @Test
    fun `error HTTP emite Error con el codigo`() = runTest {
        val httpError = HttpException(
            Response.error<Any>(500, "".toResponseBody("application/json".toMediaType()))
        )
        coEvery { api.getMovies() } throws httpError

        val states = repository.getMovies().toList()

        assertEquals("Error del servidor (500).", (states[1] as Resource.Error).message)
    }
}