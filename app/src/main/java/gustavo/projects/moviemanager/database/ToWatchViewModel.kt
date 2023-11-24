package gustavo.projects.moviemanager.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gustavo.projects.moviemanager.config.AppConfiguration
import gustavo.projects.moviemanager.database.model.ItemEntity
import gustavo.projects.moviemanager.domain.models.Movie
import gustavo.projects.moviemanager.movies.details.MovieDetailsRepository
import gustavo.projects.moviemanager.network.ApiClient
import kotlinx.coroutines.launch
import javax.inject.Inject

class ToWatchViewModel: ViewModel() {

    private lateinit var repository: DatabaseRepository
    private lateinit var movieDetailsRepository: MovieDetailsRepository

    private val _watchlistMoviesLiveData = MutableLiveData<List<Movie>>()
    val watchlistMoviesLiveData: LiveData<List<Movie>> = _watchlistMoviesLiveData

    private val _movieInToWatchList = MutableLiveData<Boolean>()
    val movieInToWatchList: LiveData<Boolean>
        get() = _movieInToWatchList

    fun init(
        appDatabase: AppDatabase,
        apiClient: ApiClient
    ) {
        repository = DatabaseRepository(appDatabase)
        movieDetailsRepository = MovieDetailsRepository(apiClient)

        viewModelScope.launch {
            val items = repository.getAllItems()

            val movies = updateItemsRating(items)

            _watchlistMoviesLiveData.postValue(movies)
        }
    }

    fun insertItem(itemEntity: ItemEntity) {
        viewModelScope.launch {
            repository.insertItem(itemEntity)
        }
    }

    fun deleteItem(itemEntity: ItemEntity) {
        viewModelScope.launch {
            repository.deleteItem(itemEntity)
        }
    }

    fun verifyIfInToWatchList(movieId: Int) {
        viewModelScope.launch {
            val result = repository.searchItem(movieId)


            result?.let {
                _movieInToWatchList.postValue(true)
                return@launch
            }
            _movieInToWatchList.postValue(false)

        }
    }

    private suspend fun updateItemsRating(items: List<ItemEntity>): List<Movie>{
        val listOfMovies = mutableListOf<Movie>()
        items.forEach { itemEntity ->
            itemEntity.movie.id?.let { movieId ->
                val movieDetails = movieDetailsRepository.getMovieDetailsById(movieId, AppConfiguration.appLanguage)
                listOfMovies.add(
                    Movie(
                        vote_average = movieDetails?.vote_average,
                        id = movieDetails?.id,
                        poster_path = movieDetails?.poster_path,
                        title = movieDetails?.title
                    )
                )
            }
        }

        return listOfMovies
    }
}