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
import com.projects.moviemanager.domain.models.content.Videos
import com.projects.moviemanager.features.details.ui.components.MoreOptionsTabItem.MoreLikeThisTab
import com.projects.moviemanager.features.details.ui.components.MoreOptionsTabItem.VideosTab

@Composable
fun MoreOptionsTab(
    videoList: List<Videos>,
    contentSimilarList: List<MediaContent>,
    openSimilarContent: (Int, MediaType) -> Unit
) {
    val tabList = mutableListOf<MoreOptionsTabItem>()
    if (videoList.isNotEmpty()) {
        tabList.add(VideosTab)
    }
    if (contentSimilarList.isNotEmpty()) {
        tabList.add(MoreLikeThisTab)
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

            when (tabList[selectedTabIndex].tabIndex) {
                VideosTab.tabIndex -> {
                    VideoList(videoList)
                }

                MoreLikeThisTab.tabIndex -> {
                    GridContentList(
                        mediaContentList = contentSimilarList,
                        openContentDetails = openSimilarContent
                    )
                }
            }
        }
    }
}
