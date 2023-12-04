package com.projects.moviemanager.compose.features.details.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projects.moviemanager.compose.features.details.domain.DetailsInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val detailsInteractor: DetailsInteractor
) : ViewModel() {

    fun printDetails(contentId: Int) {
        viewModelScope.launch {
            val details = detailsInteractor.getMovieDetailsById(contentId)
            Timber.tag("print").d("details: $details")
        }
    }
}
