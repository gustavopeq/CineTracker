package gustavo.projects.moviemanager.compose.features.browse.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import gustavo.projects.moviemanager.R
import gustavo.projects.moviemanager.compose.common.MainViewModel
import gustavo.projects.moviemanager.compose.common.ui.components.SortTypeItem
import gustavo.projects.moviemanager.compose.common.ui.components.SystemNavBarSpacer
import gustavo.projects.moviemanager.compose.common.ui.util.UiConstants.DEFAULT_MARGIN
import gustavo.projects.moviemanager.compose.common.ui.util.UiConstants.DEFAULT_PADDING
import gustavo.projects.moviemanager.compose.theme.dividerGrey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortBottomSheet(
    mainViewModel: MainViewModel,
    selectedSortType: SortTypeItem,
    displaySortScreen: (Boolean) -> Unit
) {
    val sortTypeList = listOf(
        SortTypeItem.NowPlaying,
        SortTypeItem.Popular,
        SortTypeItem.TopRated,
        SortTypeItem.Upcoming
    )

    var selectedIndex by remember { mutableIntStateOf(selectedSortType.itemIndex) }

    val dismissBottomSheet: () -> Unit = {
        displaySortScreen(false)
    }

    BackHandler { dismissBottomSheet() }

    ModalBottomSheet(
        onDismissRequest = dismissBottomSheet,
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .offset(y = (-DEFAULT_MARGIN).dp),
            text = stringResource(id = R.string.sort_by_header),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.SemiBold
        )
        Divider(
            color = Color(dividerGrey),
            modifier = Modifier.padding(top = DEFAULT_PADDING.dp)
        )
        sortTypeList.forEachIndexed { index, sortTypeItem ->
            SortButton(
                isSelected = selectedIndex == index,
                text = stringResource(id = sortTypeItem.sortTypeRes),
                onClick = {
                    mainViewModel.updateSortType(sortTypeItem)
                    selectedIndex = index
                    dismissBottomSheet()
                }
            )
        }
        Spacer(modifier = Modifier.height(DEFAULT_PADDING.dp))
        SystemNavBarSpacer()
    }
}

@Composable
fun SortButton(
    text: String,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    Button(
        contentPadding = PaddingValues(horizontal = DEFAULT_MARGIN.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        onClick = onClick
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.weight(1f))
        if (isSelected) {
            Icon(
                painter = painterResource(id = R.drawable.ic_check),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}
