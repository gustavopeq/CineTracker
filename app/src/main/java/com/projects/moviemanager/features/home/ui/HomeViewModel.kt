package com.projects.moviemanager.features.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projects.moviemanager.features.home.domain.HomeInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    homeInteractor: HomeInteractor
) : ViewModel() {
    init {
        viewModelScope.launch {
            homeInteractor.getTrendingMulti()
        }
    }
}
