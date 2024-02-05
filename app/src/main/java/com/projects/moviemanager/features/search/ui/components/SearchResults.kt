package com.projects.moviemanager.features.search.ui.components

import android.view.inputmethod.InputMethodManager.HIDE_NOT_ALWAYS
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.paging.compose.LazyPagingItems
import com.projects.moviemanager.R
import com.projects.moviemanager.common.domain.models.util.MediaType
import com.projects.moviemanager.common.ui.components.card.ImageContentCard
import com.projects.moviemanager.common.util.forceKeyboardAction
import com.projects.moviemanager.common.util.rememberNestedScrollConnection
import com.projects.moviemanager.common.domain.models.content.GenericContent

@Composable
fun SearchResultsGrid(
    numCardsPerRow: Int,
    searchResults: LazyPagingItems<GenericContent>,
    adjustedCardSize: Dp,
    goToDetails: (Int, MediaType) -> Unit
) {
    val context = LocalContext.current
    val currentFocusedView = LocalView.current

    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(
                rememberNestedScrollConnection {
                    forceKeyboardAction(
                        context = context,
                        currentFocusedView = currentFocusedView,
                        keyboardAction = HIDE_NOT_ALWAYS
                    )
                }
            ),
        columns = GridCells.Fixed(numCardsPerRow),
        horizontalArrangement = Arrangement.Center
    ) {
        items(searchResults.itemCount) { index ->
            val item = searchResults[index]
            item?.let {
                ImageContentCard(
                    item = item,
                    adjustedCardSize = adjustedCardSize,
                    goToDetails = goToDetails
                )
            }
        }
    }
}

@Composable
fun NoResultsFound() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(0.3f))
        Text(
            text = stringResource(id = R.string.search_error_title_message),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Text(
            text = stringResource(id = R.string.search_error_description_message),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.surface
        )
        Spacer(modifier = Modifier.weight(0.7f))
    }
}
