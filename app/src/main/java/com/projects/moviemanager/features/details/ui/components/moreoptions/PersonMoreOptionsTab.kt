package com.projects.moviemanager.features.details.ui.components.moreoptions

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.projects.moviemanager.common.domain.MediaType
import com.projects.moviemanager.common.ui.components.GridContentList
import com.projects.moviemanager.common.ui.components.GridImageList
import com.projects.moviemanager.common.ui.components.tab.GenericTabRow
import com.projects.moviemanager.common.ui.components.tab.setupTabs
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

    val availableTabs = mutableListOf<MoreOptionsTabItem>()
    if (moviesList.isNotEmpty()) {
        availableTabs.add(MoreOptionsTabItem.MoviesTab)
    }
    if (showList.isNotEmpty()) {
        availableTabs.add(MoreOptionsTabItem.ShowsTab)
    }
    if (personImageList.isNotEmpty()) {
        availableTabs.add(MoreOptionsTabItem.ImagesTab)
    }

    val (tabList, selectedTabIndex, updateSelectedTab) = setupTabs(availableTabs)

    if (tabList.isNotEmpty()) {
        Column {
            GenericTabRow(selectedTabIndex.value, availableTabs, updateSelectedTab)

            when (availableTabs.getOrNull(selectedTabIndex.value)?.tabIndex) {
                MoreOptionsTabItem.MoviesTab.tabIndex -> {
                    GridContentList(
                        mediaContentList = moviesList,
                        openContentDetails = openContentDetails
                    )
                }

                MoreOptionsTabItem.ShowsTab.tabIndex -> {
                    GridContentList(
                        mediaContentList = showList,
                        openContentDetails = openContentDetails
                    )
                }

                MoreOptionsTabItem.ImagesTab.tabIndex -> {
                    GridImageList(
                        personImageList = personImageList
                    )
                }
            }
        }
    }
}
