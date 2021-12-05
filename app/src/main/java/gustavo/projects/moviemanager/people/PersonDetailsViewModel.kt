package gustavo.projects.moviemanager.people

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gustavo.projects.moviemanager.domain.models.person.PersonDetails
import kotlinx.coroutines.launch

class PersonDetailsViewModel: ViewModel() {

    private val repository = PersonDetailsRepository()

    private val _getPersonByIdLiveData = MutableLiveData<PersonDetails?>()
    val getPersonDetailsByIdLiveData: LiveData<PersonDetails?> = _getPersonByIdLiveData


    fun fetchPerson(person_ID: Int){

        viewModelScope.launch {
            val response = repository.getPersonDetailsById(person_ID)
            _getPersonByIdLiveData.postValue(response)
        }
    }
}