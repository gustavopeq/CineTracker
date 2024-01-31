package com.projects.moviemanager.features.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projects.moviemanager.domain.models.content.GenericContent
import com.projects.moviemanager.domain.models.person.PersonDetails
import com.projects.moviemanager.features.home.domain.HomeInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class HomeViewModel @Inject constructor(
    homeInteractor: HomeInteractor
) : ViewModel() {
    private val _trendingMulti: MutableStateFlow<List<GenericContent>> = MutableStateFlow(
        emptyList()
    )
    val trendingMulti: StateFlow<List<GenericContent>> get() = _trendingMulti

    private val _myWatchlist: MutableStateFlow<List<GenericContent>> = MutableStateFlow(
        emptyList()
    )
    val myWatchlist: StateFlow<List<GenericContent>> get() = _myWatchlist

    private val _trendingPerson: MutableStateFlow<List<PersonDetails>> = MutableStateFlow(
        emptyList()
    )
    val trendingPerson: StateFlow<List<PersonDetails>> get() = _trendingPerson

    init {
        viewModelScope.launch {
            _trendingMulti.value = homeInteractor.getTrendingMulti()
            _trendingPerson.value = homeInteractor.getTrendingPerson()
        }

        viewModelScope.launch(Dispatchers.IO) {
            _myWatchlist.value = homeInteractor.getAllWatchlist()
        }
    }
}
