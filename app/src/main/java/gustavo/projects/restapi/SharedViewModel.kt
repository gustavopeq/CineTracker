package gustavo.projects.restapi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gustavo.projects.restapi.network.response.GetMovieByIdResponse
import kotlinx.coroutines.launch

class SharedViewModel: ViewModel() {

    private val repository = SharedRepository()

    private val _getMovieByIdLiveData = MutableLiveData<GetMovieByIdResponse?>()
    val getMovieByIdLiveData: LiveData<GetMovieByIdResponse?> = _getMovieByIdLiveData

    fun refreshMovie(movie_ID: Int){

        viewModelScope.launch {
            val response = repository.getMovieById(movie_ID)

            _getMovieByIdLiveData.postValue(response)
        }
    }
}