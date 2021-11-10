package gustavo.projects.restapi.moviedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gustavo.projects.restapi.domain.models.MovieDetails
import kotlinx.coroutines.launch

class MovieDetailsViewModel: ViewModel() {

    private val repository = MovieDetailsRepository()

    private val _getMovieByIdLiveData = MutableLiveData<MovieDetails?>()
    val getMovieDetailsByIdLiveData: LiveData<MovieDetails?> = _getMovieByIdLiveData

    fun refreshMovie(movie_ID: Int){

        viewModelScope.launch {
            val response = repository.getMovieById(movie_ID)

            _getMovieByIdLiveData.postValue(response)
        }
    }
}