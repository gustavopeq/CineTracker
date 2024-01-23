package com.projects.moviemanager.features.search.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.projects.moviemanager.R
import com.projects.moviemanager.common.ui.theme.MainBarGreyColor
import com.projects.moviemanager.common.ui.theme.PrimaryYellowColor_90
import com.projects.moviemanager.common.ui.theme.SecondaryGreyColor
import com.projects.moviemanager.common.ui.theme.placeholderGrey2
import com.projects.moviemanager.features.search.events.SearchEvent
import com.projects.moviemanager.features.search.ui.SearchViewModel

@Composable
fun SearchBar(
    viewModel: SearchViewModel
) {
    val searchBarValue by viewModel.searchQuery

    TextField(
        modifier = Modifier.fillMaxWidth().background(color = Color.Black),
        value = searchBarValue,
        onValueChange = { query ->
            viewModel.onEvent(SearchEvent.SearchQuery(query))
        },
        placeholder = {
            Text(
                text = stringResource(id = R.string.search_bar_placeholder),
                style = MaterialTheme.typography.labelMedium,
                color = placeholderGrey2,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_nav_search),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary
            )
        },
        trailingIcon = {
            if (searchBarValue.isNotEmpty()) {
                IconButton(
                    onClick = {
                        viewModel.onEvent(SearchEvent.ClearSearchBar)
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_close),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        },
        colors = textFieldColors(),
        textStyle = MaterialTheme.typography.labelMedium,
        singleLine = true
    )
}

@Composable
private fun textFieldColors() = TextFieldDefaults.colors(
    cursorColor = MaterialTheme.colorScheme.secondary,
    selectionColors = TextSelectionColors(
        backgroundColor = SecondaryGreyColor,
        handleColor = PrimaryYellowColor_90
    ),
    focusedContainerColor = MainBarGreyColor,
    unfocusedContainerColor = MainBarGreyColor
)
