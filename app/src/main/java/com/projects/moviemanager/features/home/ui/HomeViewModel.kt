package com.projects.moviemanager.features.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projects.moviemanager.domain.models.content.GenericSearchContent
import com.projects.moviemanager.features.home.domain.HomeInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    homeInteractor: HomeInteractor
) : ViewModel() {
    private val _trendingMulti: MutableStateFlow<List<GenericSearchContent>> = MutableStateFlow(
        emptyList()
    )
    val trendingMulti: StateFlow<List<GenericSearchContent>> get() = _trendingMulti

    init {
        viewModelScope.launch {
            _trendingMulti.value = homeInteractor.getTrendingMulti()
        }
    }
}
