package com.monosoft.desafioarquitecturas.data.remote

import com.monosoft.desafioarquitecturas.data.Movie
import com.monosoft.desafioarquitecturas.data.local.LocalDataSource
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verifyBlocking

class MovieRepositoryTest{
    @Test
    fun `comprobar si la BD esta vacia`() {

        /**
         * GIVEN
         * */
        //setea el count a 0
        val localDataSource = mock<LocalDataSource>{
            onBlocking { count() } doReturn 0
        }
        val remoteDataSource = mock<RemoteDataSource>()
        val repository = MovieRepository(localDataSource,remoteDataSource)

        /**
         *  se usa para probar metodos suspend corrutinas
         *  bloquea el hilo principal hasta terminar la prueba o bloque
         *  Testea si se ejecuto al menos una vez ESTOS METODOS O FUNCIONES
         *  times(2) Verifica si se ejecuto 2 veces
         */
        runBlocking { repository.requestMovies() }
        runBlocking { repository.requestMovies() }
        verifyBlocking(remoteDataSource, times(2)){getMovies()}
    }

    @Test
    fun `si BD es vacia Guarde en BD`() {

        val expetedMovies = listOf(Movie(1,"title","overview","posterpath",true))
        val localDataSource = mock<LocalDataSource>{
            onBlocking { count() } doReturn 0
        }
        val remoteDataSource = mock<RemoteDataSource>{
            onBlocking { getMovies() } doReturn expetedMovies
        }
        val repository = MovieRepository(localDataSource,remoteDataSource)

        runBlocking { repository.requestMovies() }

        verifyBlocking(localDataSource){insertAll(expetedMovies)}
    }

    @Test
    fun `cuando la Bd no es vacia No se llama a Retrofit`() {
        val localDataSource = mock<LocalDataSource>{
            onBlocking { count() } doReturn 1
        }
        val remoteDataSource = mock<RemoteDataSource>()
        val repository = MovieRepository(localDataSource,remoteDataSource)
        verifyBlocking(remoteDataSource, times(0)){getMovies()}

    }
}