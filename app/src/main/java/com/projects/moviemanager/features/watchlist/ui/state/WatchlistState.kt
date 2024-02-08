package com.projects.moviemanager.features.watchlist.ui.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.projects.moviemanager.common.domain.models.content.GenericContent
import com.projects.moviemanager.common.domain.models.util.LoadStatusState

class WatchlistState(
    var listItems: MutableState<List<GenericContent>> = mutableStateOf(mutableListOf())
) : LoadStatusState()
