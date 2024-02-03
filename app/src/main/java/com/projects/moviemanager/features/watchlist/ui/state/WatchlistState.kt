package com.projects.moviemanager.features.watchlist.ui.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.projects.moviemanager.common.domain.models.content.DetailedMediaInfo
import com.projects.moviemanager.common.domain.models.util.LoadStatusState

class WatchlistState(
    var listItems: MutableState<List<DetailedMediaInfo>> = mutableStateOf(mutableListOf())
) : LoadStatusState()
