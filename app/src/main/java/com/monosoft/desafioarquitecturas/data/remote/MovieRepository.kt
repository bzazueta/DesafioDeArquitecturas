package com.monosoft.desafioarquitecturas.data.remote

import com.monosoft.desafioarquitecturas.data.Movie
import com.monosoft.desafioarquitecturas.data.local.LocalDataSource
import com.monosoft.desafioarquitecturas.data.local.MoviesDao
import com.monosoft.desafioarquitecturas.data.local.toMovie
import com.monosoft.desafioarquitecturas.data.toLocalMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory





class MovieRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {

    val movies : Flow<List<Movie>> = localDataSource.getMovie

    suspend fun updateMovie(movie:Movie){
        localDataSource.updateMovie(movie)
    }

    suspend fun requestMovies(){
        val isDbEmpty = localDataSource.count() == 0
        if(isDbEmpty)
        {
            localDataSource.insertAll(remoteDataSource.getMovies())
        }

    }

}