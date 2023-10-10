package com.monosoft.desafioarquitecturas.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.monosoft.desafioarquitecturas.data.Movie
import com.monosoft.desafioarquitecturas.data.local.MoviesDao
import com.monosoft.desafioarquitecturas.data.local.toLocalMovie
import com.monosoft.desafioarquitecturas.data.remote.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//viewmodel no pierde el estado ejemplo cuando rota la pantalla sigue teniendo el mismo valor

class HomeViewModel(private val repository: MovieRepository) : ViewModel(){

    //livedata
    //private var _state = MutableLiveData(UIState()) //
    //val state : LiveData<UIState> = _state

    //estados que puede se observable desde  compose
    //var state  by mutableStateOf(UIState())
    //private set // sirve para que solo se modifique dese adentro y no desde fuera

    //MutableStateFlow
    private val _state = MutableStateFlow(UIState())
    var state: StateFlow<UIState> = _state

    init {
        viewModelScope.launch {

            _state.value = UIState(loading = true)
            repository.requestMovies()
            repository.movies.collect{
                _state.value = UIState(movies = it)
            }

        }

    }


    fun onMovieClick(movie: Movie) {
//        val movies = _state.value.movies.toMutableList()
//        movies.replaceAll { if (it.id == movie.id) movie.copy(favorite = !movie.favorite) else it }
//        _state.value = _state.value.copy(movies = movies)
        viewModelScope.launch {
           // movieDao.updateMovies(movie.copy(favorite = !movie.favorite).toLocalMovie())
            repository.updateMovie(movie.copy(favorite = !movie.favorite))
        }
    }


    //representa el estado en la pantalla
    data class UIState(
        val loading: Boolean = false,
        val movies: List<Movie> = emptyList()

    )




//estados
//es el primer metodo que se ejecuta
//    init {
//        // viewModelScope.launch -> corrutinas para
//        viewModelScope.launch {
//            //le pasamo el valor a state de la petición
//            state = UIState(loading = true)
//            state = UIState(
//                movies = Retrofit.Builder().
//            baseUrl("https://api.themoviedb.org/3/").
//            addConverterFactory(GsonConverterFactory.create()).
//            build().
//            create(MovieServices::class.java).getMovies().results)
//        }
//    }
//    fun onMovieClick(movie: Result){
//        val movies = state.movies.toMutableList()
//        //no se puede alterar el orden de los items ->state.movies.results.toMutableList()
//        movies.replaceAll{if(it.id == movie.id) movie.copy(favorite = !movie.favorite) else it }
//        state = UIState(movies = movies)
//    }

//    fun loadingVisible(visible :Boolean,movies: Result){
//        state = UIState(loading = visible)
//        //state = UIState(movies = movies)
//    }

//    movieDao.insert(
//    Retrofit.Builder()
//    .baseUrl("https://api.themoviedb.org/3/")
//    .addConverterFactory(GsonConverterFactory.create())
//    .build()
//    .create(MovieServices::class.java)
//    .getMovies()
//
//    )

}