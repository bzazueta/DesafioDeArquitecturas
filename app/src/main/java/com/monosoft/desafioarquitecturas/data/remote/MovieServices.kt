package com.monosoft.desafioarquitecturas.data.remote

import retrofit2.http.GET

interface MovieServices {

    //get metodo para recuperar la petición regresa un modelo de MovieResult
    @GET("discover/movie?api_key=933dc8e875378a25bceedc51ed51bc42")
    suspend fun getMovies(): MovieResult
}