package gustavo.projects.moviemanager.people

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gustavo.projects.moviemanager.domain.models.person.PersonDetails
import gustavo.projects.moviemanager.network.ApiClient
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonDetailsViewModel @Inject constructor(
    private val apiClient: ApiClient
): ViewModel() {

    private val repository = PersonDetailsRepository(apiClient)

    private val _getPersonByIdLiveData = MutableLiveData<PersonDetails?>()
    val getPersonDetailsByIdLiveData: LiveData<PersonDetails?> = _getPersonByIdLiveData


    fun fetchPerson(person_ID: Int){

        viewModelScope.launch {
            val response = repository.getPersonDetailsById(person_ID)
            _getPersonByIdLiveData.postValue(response)
        }
    }
}