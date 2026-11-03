package com.example.carrucel

import com.example.carrucel.data.mapper.toDomain
import com.example.carrucel.data.remote.dto.MoviesResponseDTO
import junit.framework.TestCase.assertEquals
import org.junit.Test

class MovieMapperTest {

    @Test
    fun `toDomain copia datos correctamente`() {
        val dto = MoviesResponseDTO(
            id = 2,
            title = "Jumanji",
            description = "Descipcion...",
            year = 2019,
            imageUrl = "https://google/imagen/2.jpg",
            genre = listOf("Adventure", "Comedy"),
            stars = 3.4
        )

        val movie = dto.toDomain()

        assertEquals(2, movie.id)
        assertEquals("Jumanji", movie.title)
        assertEquals("Descipcion...", movie.description)
        assertEquals(2019, movie.year)
        assertEquals("https://google/imagen/2.jpg", movie.imageUrl)
        assertEquals(listOf("Adventure", "Comedy"), movie.genres)
        assertEquals(3.4, movie.rating, 0.0)
    }

}