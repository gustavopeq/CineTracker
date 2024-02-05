package com.projects.moviemanager.features.home.ui.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.projects.moviemanager.common.domain.models.content.GenericContent
import com.projects.moviemanager.common.domain.models.util.LoadStatusState

data class HomeState(
    val trendingList: MutableState<List<GenericContent>> = mutableStateOf(emptyList())
) : LoadStatusState()
