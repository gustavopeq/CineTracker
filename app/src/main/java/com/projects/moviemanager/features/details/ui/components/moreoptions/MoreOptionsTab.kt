package com.projects.moviemanager.features.details.ui.components.moreoptions

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.projects.moviemanager.common.domain.models.content.GenericContent
import com.projects.moviemanager.common.domain.models.content.Videos
import com.projects.moviemanager.common.domain.models.util.MediaType
import com.projects.moviemanager.common.ui.components.GridContentList
import com.projects.moviemanager.common.ui.components.tab.GenericTabRow
import com.projects.moviemanager.common.ui.components.tab.setupTabs
import com.projects.moviemanager.common.util.UiConstants.MAX_COUNT_MORE_LIKE_THIS_CARDS
import com.projects.moviemanager.features.details.ui.components.moreoptions.MoreOptionsTabItem.MoreLikeThisTab
import com.projects.moviemanager.features.details.ui.components.moreoptions.MoreOptionsTabItem.VideosTab

@Composable
fun MoreOptionsTab(
    videoList: List<Videos>,
    contentSimilarList: List<GenericContent>,
    goToDetails: (Int, MediaType) -> Unit
) {
    val availableTabs = mutableListOf<MoreOptionsTabItem>()
    if (videoList.isNotEmpty()) {
        availableTabs.add(VideosTab)
    }
    if (contentSimilarList.isNotEmpty()) {
        availableTabs.add(MoreLikeThisTab)
    }

    val (tabList, selectedTabIndex, updateSelectedTab) = setupTabs(availableTabs)

    if (tabList.isNotEmpty()) {
        Column {
            GenericTabRow(selectedTabIndex.value, tabList, updateSelectedTab)

            when (tabList[selectedTabIndex.value].tabIndex) {
                VideosTab.tabIndex -> {
                    VideoList(videoList)
                }

                MoreLikeThisTab.tabIndex -> {
                    GridContentList(
                        mediaContentList = contentSimilarList,
                        maxCardsNumber = MAX_COUNT_MORE_LIKE_THIS_CARDS,
                        openContentDetails = goToDetails
                    )
                }
            }
        }
    }
}
