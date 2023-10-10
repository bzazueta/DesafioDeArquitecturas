package com.monosoft.desafioarquitecturas.data

import androidx.room.Entity
import com.monosoft.desafioarquitecturas.data.local.LocalMovie
import com.monosoft.desafioarquitecturas.data.remote.Result

@Entity
data class Movie(
    val id : Int,
    val title : String,
    val overView  :String,
    val posterPath : String,
    val favorite : Boolean = false
)

fun Movie.toLocalMovie()= LocalMovie(
    id = 0,
    title = title,
    overView = overView,
    posterPath = posterPath,
    favorite =  favorite
)
