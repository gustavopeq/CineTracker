package com.projects.moviemanager.features.details.ui.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.projects.moviemanager.common.domain.models.content.ContentCast
import com.projects.moviemanager.common.domain.models.content.DetailedMediaInfo
import com.projects.moviemanager.common.domain.models.content.Videos
import com.projects.moviemanager.common.domain.models.util.LoadStatusState

data class DetailsState(
    var detailsInfo: MutableState<DetailedMediaInfo?> = mutableStateOf(null),
    var detailsCast: MutableState<List<ContentCast>> = mutableStateOf(listOf()),
    var detailsVideos: MutableState<List<Videos>> = mutableStateOf(listOf())
) : LoadStatusState()
