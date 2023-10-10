package com.monosoft.desafioarquitecturas.data.local

import com.monosoft.desafioarquitecturas.data.Movie
import com.monosoft.desafioarquitecturas.data.toLocalMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map



    class LocalDataSource(private val dao: MoviesDao){

        val getMovie : Flow<List<Movie>> = dao.getMovies().map {
                movie->
            movie.map { it.toMovie() }
        }

        suspend fun updateMovie(movie: Movie){
            dao.updateMovies(movie.toLocalMovie())
        }

        suspend fun insertAll(movies : List<Movie>){
            dao.insert(movies.map { it.toLocalMovie() })
        }

        suspend fun count():Int{
            return dao.countMovies()
        }

    }
