package com.monosoft.desafioarquitecturas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.room.Room
import com.monosoft.desafioarquitecturas.data.local.LocalDataSource
import com.monosoft.desafioarquitecturas.data.local.MovieDataBase
import com.monosoft.desafioarquitecturas.data.remote.MovieRepository
import com.monosoft.desafioarquitecturas.data.remote.RemoteDataSource
import com.monosoft.desafioarquitecturas.ui.screens.home.Home

class MainActivity : ComponentActivity() {

    lateinit var db : MovieDataBase

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //para poder pasar el contexto
        db =  Room.databaseBuilder(
            applicationContext, // contexto
            MovieDataBase::class.java, //Clase RoomDataBase
            "movies-db"  //nombre BD
        ).build()

        val repository = MovieRepository(
            localDataSource = LocalDataSource(db.movieDao()),
            remoteDataSource = RemoteDataSource()
        )

        //MVVM nos ayuda a separar el código de la vista
        //VIEW es la vista(Activity)
        //VIEWMODEL Es que interactua con la vista y le informa de cualquier cambio. además recoge los datos del MODEL
        //MODEL interactuar con datos,librerias, hardware etc
        setContent {
            Home(repository)
        }
    }
}

