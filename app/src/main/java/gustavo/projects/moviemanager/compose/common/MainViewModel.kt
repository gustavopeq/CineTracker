package gustavo.projects.moviemanager.compose.common

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import gustavo.projects.moviemanager.compose.common.ui.components.SortTypeItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _sortType = MutableStateFlow<SortTypeItem>(SortTypeItem.NowPlaying)
    val sortType: StateFlow<SortTypeItem> get() = _sortType

    fun updateSortType(
        sortTypeItem: SortTypeItem
    ) {
        _sortType.value = sortTypeItem
    }
}
