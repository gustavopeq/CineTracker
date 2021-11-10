package gustavo.projects.restapi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gustavo.projects.restapi.domain.models.Movie
import kotlinx.coroutines.launch

class SharedViewModel: ViewModel() {

    private val repository = SharedRepository()

    private val _getMovieByIdLiveData = MutableLiveData<Movie?>()
    val getMovieByIdLiveData: LiveData<Movie?> = _getMovieByIdLiveData

    fun refreshMovie(movie_ID: Int){

        viewModelScope.launch {
            val response = repository.getMovieById(movie_ID)

            _getMovieByIdLiveData.postValue(response)
        }
    }
}