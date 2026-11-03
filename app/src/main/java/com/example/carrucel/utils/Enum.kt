package com.example.carrucel.utils

enum class GenreEnum(val english: String, val spanish: String) {
    DRAMA("Drama", "Drama"),
    ACTION("Action", "Acción"),
    COMEDY("Comedy", "Comedia"),
    ADVENTURE("Adventure", "Aventura"),
    FANTASY("Fantasy", "Fantasía"),
    FAMILY("Family", "Familiar"),
    ANIMATION("Animation", "Animación"),
    HORROR("Horror", "Terror"),
    ROMANCE("Romance", "Romance");


    companion object {
        fun fromEnglish(name: String): GenreEnum? =
            entries.firstOrNull { it.english.equals(name, ignoreCase = true) }
    }
}