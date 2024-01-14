package com.projects.moviemanager.features.details.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.projects.moviemanager.common.domain.MediaType
import com.projects.moviemanager.common.ui.components.GridContentList
import com.projects.moviemanager.common.ui.components.MoreOptionsTabRow
import com.projects.moviemanager.domain.models.content.MediaContent
import com.projects.moviemanager.domain.models.person.PersonImage

@Composable
fun PersonMoreOptionsTab(
    contentList: List<MediaContent>,
    personImageList: List<PersonImage>,
    openContentDetails: (Int, MediaType) -> Unit
) {
    val moviesList = contentList.filter { it.mediaType == MediaType.MOVIE }
    val showList = contentList.filter { it.mediaType == MediaType.SHOW }

    val tabList = mutableListOf<MoreOptionsTabItem>()
    if (moviesList.isNotEmpty()) {
        tabList.add(MoreOptionsTabItem.MoviesTab)
    }
    if (showList.isNotEmpty()) {
        tabList.add(MoreOptionsTabItem.ShowsTab)
    }
    if (personImageList.isNotEmpty()) {
        tabList.add(MoreOptionsTabItem.ImagesTab)
    }

    tabList.forEachIndexed { index, tabItem ->
        tabItem.tabIndex = index
    }

    var selectedTabIndex by rememberSaveable {
        mutableIntStateOf(tabList.firstOrNull()?.tabIndex ?: 0)
    }

    val updateSelectedTab: (Int) -> Unit = { index ->
        selectedTabIndex = index
    }

    if (tabList.isNotEmpty()) {
        Column {
            MoreOptionsTabRow(selectedTabIndex, tabList, updateSelectedTab)

            when (tabList.getOrNull(selectedTabIndex)?.tabIndex) {
                MoreOptionsTabItem.MoviesTab.tabIndex -> {
                    GridContentList(
                        mediaContentList = moviesList,
                        maxCardsNumber = moviesList.size,
                        openContentDetails = openContentDetails
                    )
                }

                MoreOptionsTabItem.ShowsTab.tabIndex -> {
                    GridContentList(
                        mediaContentList = showList,
                        maxCardsNumber = showList.size,
                        openContentDetails = openContentDetails
                    )
                }

                MoreOptionsTabItem.ImagesTab.tabIndex -> {
                    GridContentList(
                        mediaContentList = showList,
                        maxCardsNumber = showList.size,
                        openContentDetails = openContentDetails
                    )
                }
            }
        }
    }
}
