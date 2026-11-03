package com.example.carrucel

import com.example.carrucel.utils.toSpanishGenre
import junit.framework.TestCase.assertEquals
import org.junit.Test

class UtilsTest {
    @Test
    fun `traduce a espanol`() {
        assertEquals("Acción", "Action".toSpanishGenre())
    }

    @Test
    fun `ignora mayusculas`() {
        assertEquals("Terror", "horror".toSpanishGenre())
    }

    @Test
    fun `genero desconocido se deja igual`() {
        assertEquals("Sci-Fi", "Sci-Fi".toSpanishGenre())
    }
}