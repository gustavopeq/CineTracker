package com.projects.moviemanager.features.details.ui.components.otherlists

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.projects.moviemanager.R
import com.projects.moviemanager.common.ui.components.bottomsheet.GenericBottomSheet
import com.projects.moviemanager.common.util.UiConstants.LARGE_PADDING
import com.projects.moviemanager.common.util.capitalized
import com.projects.moviemanager.database.model.ListEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtherListsPanel(
    allLists: List<ListEntity>,
    contentInListStatus: Map<Int, Boolean>,
    onClosePanel: () -> Unit
) {
    BackHandler {
        onClosePanel()
    }

    GenericBottomSheet(
        dismissBottomSheet = {
            onClosePanel()
        },
        headerText = stringResource(id = R.string.manage_other_lists_header)
    ) {
        contentInListStatus.forEach { mapItem ->
            val listName = allLists.find {
                it.listId == mapItem.key
            }?.listName

            val contentInList = mapItem.value

            if (listName != null) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = contentInList,
                        onCheckedChange = {},
                        colors = CheckboxDefaults.colors(
                            checkedColor = MaterialTheme.colorScheme.secondary
                        )
                    )
                    Text(
                        text = listName.capitalized(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(LARGE_PADDING.dp))
    }
}
