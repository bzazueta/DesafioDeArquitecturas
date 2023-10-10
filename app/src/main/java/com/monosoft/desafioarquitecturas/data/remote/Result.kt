package com.monosoft.desafioarquitecturas.data.remote

import com.monosoft.desafioarquitecturas.data.Movie
import com.monosoft.desafioarquitecturas.data.local.LocalMovie

/**data class para convertir a objeto la respuesta la lista<Result> que regresa en json */
data class Result(
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: List<Int>,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int,
    val favorite: Boolean
)

fun Result.toMovie()= Movie(
    id = 0,
    title = title,
    overView = overview,
    posterPath = poster_path,
    favorite =  favorite
)