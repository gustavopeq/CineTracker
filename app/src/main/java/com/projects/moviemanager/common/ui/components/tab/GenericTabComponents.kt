package com.projects.moviemanager.common.ui.components.tab

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.projects.moviemanager.common.ui.theme.MainBarGreyColor
import com.projects.moviemanager.common.util.UiConstants
import com.projects.moviemanager.common.util.removeParentPadding

@Composable
fun setupTabs(
    tabList: List<TabItem>,
    onTabSelected: (Int) -> Unit = {}
): Triple<List<TabItem>, State<Int>, (Int) -> Unit> {
    tabList.forEachIndexed { index, tabItem ->
        tabItem.tabIndex = index
    }

    val selectedTabIndex = rememberSaveable {
        mutableIntStateOf(tabList.firstOrNull()?.tabIndex ?: 0)
    }

    val updateSelectedTab: (Int) -> Unit = { index ->
        selectedTabIndex.intValue = index
        onTabSelected(index)
    }

    return Triple(tabList, selectedTabIndex, updateSelectedTab)
}

@Composable
fun GenericTabRow(
    selectedTabIndex: Int,
    tabList: List<TabItem>,
    updateSelectedTab: (Int) -> Unit
) {
    ScrollableTabRow(
        modifier = Modifier.fillMaxWidth(),
        selectedTabIndex = selectedTabIndex,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                color = MaterialTheme.colorScheme.secondary
            )
        },
        divider = { },
        containerColor = Color.Transparent,
        edgePadding = 0.dp
    ) {
        tabList.forEachIndexed { index, mediaTypeTabItem ->
            GenericTab(
                text = stringResource(id = mediaTypeTabItem.tabResId),
                tabIndex = index,
                isSelected = selectedTabIndex == index,
                onClick = {
                    updateSelectedTab(index)
                }
            )
        }
    }
    Divider(
        color = MainBarGreyColor,
        modifier = Modifier
            .fillMaxWidth()
            .offset(y = (-1).dp)
            .zIndex(UiConstants.BACKGROUND_INDEX)
            .removeParentPadding(UiConstants.DEFAULT_MARGIN.dp)
    )
}

@Composable
fun GenericTab(
    text: String,
    tabIndex: Int,
    isSelected: Boolean,
    onClick: (Int) -> Unit
) {
    Tab(
        modifier = Modifier.padding(horizontal = UiConstants.DEFAULT_PADDING.dp),
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
        Spacer(modifier = Modifier.height(UiConstants.LARGE_PADDING.dp))
    }
}

