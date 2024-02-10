package com.projects.moviemanager.features.details.ui.components.moreoptions

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.projects.moviemanager.common.domain.models.content.GenericContent
import com.projects.moviemanager.common.domain.models.person.PersonImage
import com.projects.moviemanager.common.domain.models.util.MediaType
import com.projects.moviemanager.common.ui.components.GridContentList
import com.projects.moviemanager.common.ui.components.GridImageList
import com.projects.moviemanager.common.ui.components.tab.GenericTabRow
import com.projects.moviemanager.common.ui.components.tab.setupTabs
import com.projects.moviemanager.common.util.UiConstants.MAX_COUNT_PERSON_ADDITIONAL_CONTENT

@Composable
fun PersonMoreOptionsTab(
    contentList: List<GenericContent>,
    personImageList: List<PersonImage>,
    goToDetails: (Int, MediaType) -> Unit,
    updateShowAllFlag: (Boolean, MediaType) -> Unit
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
                        maxCardsNumber = MAX_COUNT_PERSON_ADDITIONAL_CONTENT,
                        showSeeAllButton = true,
                        openContentDetails = goToDetails,
                        onSeeAll = {
                            updateShowAllFlag(true, MediaType.MOVIE)
                        }
                    )
                }

                MoreOptionsTabItem.ShowsTab.tabIndex -> {
                    GridContentList(
                        mediaContentList = showList,
                        maxCardsNumber = MAX_COUNT_PERSON_ADDITIONAL_CONTENT,
                        showSeeAllButton = true,
                        openContentDetails = goToDetails,
                        onSeeAll = {
                            updateShowAllFlag(true, MediaType.SHOW)
                        }
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
