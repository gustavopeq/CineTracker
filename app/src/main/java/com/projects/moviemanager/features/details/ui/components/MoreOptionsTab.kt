package com.projects.moviemanager.features.details.ui.components

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.projects.moviemanager.common.domain.MediaType
import com.projects.moviemanager.common.theme.MainBarGreyColor
import com.projects.moviemanager.common.ui.util.UiConstants.BACKGROUND_INDEX
import com.projects.moviemanager.common.ui.util.UiConstants.DEFAULT_MARGIN
import com.projects.moviemanager.common.ui.util.UiConstants.DEFAULT_PADDING
import com.projects.moviemanager.common.ui.util.UiConstants.FOREGROUND_INDEX
import com.projects.moviemanager.common.ui.util.UiConstants.LARGE_PADDING
import com.projects.moviemanager.domain.models.content.MediaContent
import com.projects.moviemanager.domain.models.content.Videos
import com.projects.moviemanager.features.details.ui.components.MoreOptionsTabItem.MoreLikeThisTab
import com.projects.moviemanager.features.details.ui.components.MoreOptionsTabItem.VideosTab
import com.projects.moviemanager.util.Constants.BASE_URL_YOUTUBE_VIDEO
import com.projects.moviemanager.util.removeParentPadding

@Composable
fun MoreOptionsTabRow(
    videoList: List<Videos>,
    contentSimilarList: List<MediaContent>,
    openSimilarContent: (Int, MediaType) -> Unit
) {
    val tabList = when {
        videoList.isNotEmpty() && contentSimilarList.isNotEmpty() -> {
            listOf(VideosTab, MoreLikeThisTab)
        }
        videoList.isNotEmpty() -> listOf(VideosTab)
        contentSimilarList.isNotEmpty() -> listOf(MoreLikeThisTab)
        else -> emptyList()
    }

    var selectedTabIndex by rememberSaveable { mutableIntStateOf(VideosTab.tabIndex) }
    val activity = LocalContext.current as Activity

    val launchVideo: (String) -> Unit = { videoKey ->
        val fullUrl = BASE_URL_YOUTUBE_VIDEO + videoKey
        val uri = Uri.parse(fullUrl)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        activity.startActivity(intent)
    }

    if (tabList.isNotEmpty()) {
        Column {
            ScrollableTabRow(
                modifier = Modifier.fillMaxWidth(),
                selectedTabIndex = selectedTabIndex,
                indicator = { tabPositions ->
                    TabIndicator(
                        width = tabPositions[selectedTabIndex].width,
                        left = tabPositions[selectedTabIndex].left
                    )
                },
                divider = { },
                containerColor = Color.Transparent,
                edgePadding = 0.dp
            ) {
                tabList.forEachIndexed { index, mediaTypeTabItem ->
                    MoreOptionsTab(
                        text = stringResource(id = mediaTypeTabItem.tabResId),
                        tabIndex = index,
                        isSelected = selectedTabIndex == index,
                        onClick = {
                            selectedTabIndex = index
                        }
                    )
                }
            }
            Divider(
                color = MainBarGreyColor,
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-1).dp)
                    .zIndex(BACKGROUND_INDEX)
                    .removeParentPadding(DEFAULT_MARGIN.dp)
            )

            when (tabList[selectedTabIndex].tabIndex) {
                VideosTab.tabIndex -> {
                    VideoList(videoList, launchVideo)
                }

                MoreLikeThisTab.tabIndex -> {
                    MoreLikeThisList(contentSimilarList, openSimilarContent)
                }
            }
        }
    }
}

@Composable
private fun MoreOptionsTab(
    text: String,
    tabIndex: Int,
    isSelected: Boolean,
    onClick: (Int) -> Unit
) {
    Tab(
        modifier = Modifier.padding(horizontal = DEFAULT_PADDING.dp),
        selected = isSelected,
        onClick = { onClick(tabIndex) }
    ) {
        Text(
            text = text.uppercase(),
            color = if (isSelected) {
                MaterialTheme.colorScheme.onPrimary
            } else {
                MaterialTheme.colorScheme.tertiary
            },
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(LARGE_PADDING.dp))
    }
}

@Composable
private fun TabIndicator(
    width: Dp,
    left: Dp
) {
    val animateIndicatorOffset by animateIntOffsetAsState(
        targetValue = IntOffset(x = left.value.toInt(), 0),
        animationSpec = tween(100),
        label = "indicatorAnimation"
    )

    Box(
        modifier = Modifier
            .wrapContentSize(Alignment.BottomStart)
            .offset(x = animateIndicatorOffset.x.dp)
            .width(width)
            .height(2.dp)
            .background(color = MaterialTheme.colorScheme.secondary)
            .zIndex(FOREGROUND_INDEX)
    )
}
