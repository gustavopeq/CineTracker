package com.projects.moviemanager.features.details.ui.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.projects.moviemanager.domain.models.content.MediaContentDetails

data class ContentDetailsState(
    val contentDetails: MutableState<MediaContentDetails?> = mutableStateOf(null)
)
