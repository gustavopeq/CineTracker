package gustavo.projects.moviemanager.compose.features.browse.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import gustavo.projects.moviemanager.compose.util.UiConstants.BROWSE_TAB_ROW_OFFSET_HEIGHT

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollapsingTabRow(
    scrollBehavior: TopAppBarScrollBehavior
) {
    TopAppBar(
        title = {
            BrowseTypeTabRow()
        },
        scrollBehavior = scrollBehavior
    )
}

@Composable
private fun BrowseTypeTabRow() {
    val tabList = listOf(MediaTypeTabItem.Movies, MediaTypeTabItem.Shows)
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    TabRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 4.dp)
            .offset(y = BROWSE_TAB_ROW_OFFSET_HEIGHT.dp),
        selectedTabIndex = selectedTabIndex,
        indicator = { tabPositions ->
            TabIndicator(
                width = tabPositions[selectedTabIndex].width,
                left = tabPositions[selectedTabIndex].left
            )
        },
        divider = { }
    ) {
        tabList.forEachIndexed { index, mediaTypeTabItem ->
            BrowseTypeTab(
                text = stringResource(id = mediaTypeTabItem.tabResId),
                tabIndex = index,
                isSelected = selectedTabIndex == index,
                onClick = {
                    selectedTabIndex = it
                }
            )
        }
    }
}

@Composable
private fun BrowseTypeTab(
    text: String,
    tabIndex: Int,
    isSelected: Boolean,
    onClick: (Int) -> Unit
) {
    Tab(
        selected = isSelected,
        onClick = { onClick(tabIndex) }
    ) {
        Text(
            text = text,
            color = if (isSelected) {
                MaterialTheme.colorScheme.onPrimary
            } else {
                MaterialTheme.colorScheme.tertiary
            }
        )
    }
}

@Composable
private fun TabIndicator(
    width: Dp,
    left: Dp,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.BottomStart)
            .offset(x = left - 10.dp)
            .width(width)
            .height(2.dp)
            .background(color = Color.Black)
            .zIndex(-1f)
    )
}
