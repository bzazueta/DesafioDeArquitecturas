package com.monosoft.desafioarquitecturas.data.local

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.Update
import com.monosoft.desafioarquitecturas.data.Movie
import com.monosoft.desafioarquitecturas.data.remote.Result
import kotlinx.coroutines.flow.Flow

/**
 * Room DataBase,Dao,Entity,
 * se crea la base de datos atravez de la entidad
 * Dao es la definición de como acceder a nuestra BD insert,delete etc
 * se recomienda hacer un modelo de datos aparte del la respuesta de nuestro servidor o api por si esta cambia(API)
 */

@Database(entities = [LocalMovie::class], version = 2)
abstract class MovieDataBase : RoomDatabase(){
   abstract fun movieDao() : MoviesDao
}



@Dao
interface MoviesDao{

     @Query("SELECT * FROM LocalMovie")
     fun getMovies(): Flow<List<LocalMovie>>

    @Insert
     suspend fun insert(movies: List<LocalMovie>)

     @Update
     suspend fun  updateMovies(movie: LocalMovie)

     @Query("SELECT COUNT(*) FROM LocalMovie")
     suspend fun  countMovies():Int
}

@Entity
data class LocalMovie(
    @PrimaryKey (autoGenerate = true)val id : Int,
    val title : String,
    val overView  :String,
    val posterPath : String,
    val favorite : Boolean = false
)

fun LocalMovie.toMovie() = Movie(
    id = id,
   title = title,
   overView = overView,
   posterPath = posterPath,
   favorite =  favorite
)

fun Movie.toLocalMovie() = LocalMovie(
    id = id,
    title = title,
    overView = overView,
    posterPath = posterPath,
    favorite =  favorite
)