package gustavo.projects.moviemanager.compose.common.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import gustavo.projects.moviemanager.compose.common.MainViewModel
import gustavo.projects.moviemanager.compose.features.browse.ui.components.SortBottomSheet

@Composable
fun ModalComponents(
    mainViewModel: MainViewModel,
    showSortBottomSheet: Boolean,
    displaySortScreen: (Boolean) -> Unit
) {
    val selectedSortType by mainViewModel.sortType.collectAsState()

    if (showSortBottomSheet) {
        SortBottomSheet(
            mainViewModel = mainViewModel,
            selectedSortType = selectedSortType,
            displaySortScreen = displaySortScreen
        )
    }
}
