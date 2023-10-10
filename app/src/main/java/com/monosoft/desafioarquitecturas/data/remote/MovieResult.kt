package com.monosoft.desafioarquitecturas.data.remote

/**data class para convertir a objeto la respuesta en json */
data class MovieResult(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)

