package gustavo.projects.moviemanager.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gustavo.projects.moviemanager.database.model.ItemEntity
import kotlinx.coroutines.launch

class ToWatchViewModel: ViewModel() {

    private lateinit var repository: DatabaseRepository

    private val _itemEntitiesLiveData = MutableLiveData<List<ItemEntity>>()
    val itemEntitiesLiveData: LiveData<List<ItemEntity>> = _itemEntitiesLiveData

    fun init(appDatabase: AppDatabase) {
        repository = DatabaseRepository(appDatabase)

        viewModelScope.launch {
            val items = repository.getAllItems()
            _itemEntitiesLiveData.postValue(items)
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
}