package com.projects.moviemanager.features.watchlist.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.projects.moviemanager.R
import com.projects.moviemanager.common.ui.MainViewModel
import com.projects.moviemanager.common.ui.components.SystemNavBarSpacer
import com.projects.moviemanager.common.ui.components.button.GenericButton
import com.projects.moviemanager.common.ui.theme.MainBarGreyColor
import com.projects.moviemanager.common.util.UiConstants.DEFAULT_MARGIN
import com.projects.moviemanager.common.util.UiConstants.LARGE_MARGIN
import com.projects.moviemanager.common.util.UiConstants.SMALL_MARGIN
import com.projects.moviemanager.common.util.UiConstants.SMALL_PADDING
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun CreateListBottomSheet(
    mainViewModel: MainViewModel
) {
    val showBottomSheet by mainViewModel.displayCreateNewList.collectAsState()

    if (showBottomSheet) {
        ShowBottomSheet(
            mainViewModel = mainViewModel,
            dismissBottomSheet = {
                mainViewModel.updateDisplayCreateNewList(false)
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
private fun ShowBottomSheet(
    mainViewModel: MainViewModel,
    dismissBottomSheet: () -> Unit
) {
    val listName = mainViewModel.newListTextFieldValue.value
    val isDuplicatedName by mainViewModel.isDuplicatedListName.collectAsState()
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    val modalColor = MainBarGreyColor

    ModalBottomSheet(
        onDismissRequest = { dismissBottomSheet() },
        containerColor = modalColor,
        sheetState = sheetState
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .offset(y = (-SMALL_MARGIN).dp),
            text = stringResource(id = R.string.create_new_list_header),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Divider(
            color = MaterialTheme.colorScheme.inverseSurface,
            modifier = Modifier.padding(top = SMALL_PADDING.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = LARGE_MARGIN.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(DEFAULT_MARGIN.dp)
            ) {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = listName,
                    onValueChange = {
                        mainViewModel.updateCreateNewListTextField(it.uppercase().trim())
                    },
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.create_new_list_placeholder)
                                .uppercase(),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.surface,
                            textAlign = TextAlign.Center
                        )
                    },
                    textStyle = MaterialTheme.typography.bodyMedium,
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = modalColor,
                        unfocusedContainerColor = modalColor,
                        focusedIndicatorColor = MaterialTheme.colorScheme.secondary,
                        cursorColor = MaterialTheme.colorScheme.secondary,
                        selectionColors = TextSelectionColors(
                            handleColor = MaterialTheme.colorScheme.secondary,
                            backgroundColor = MaterialTheme.colorScheme.secondary
                        ),
                        errorIndicatorColor = Color.Red
                    ),
                    isError = isDuplicatedName
                )

                if (isDuplicatedName) {
                    Text(
                        text = "List name already exists!",
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = SMALL_PADDING.dp)
                    )
                }
            }

            GenericButton(
                buttonText = stringResource(id = R.string.create_new_list_button),
                onClick = {
                    scope.launch(Dispatchers.IO) {
                        mainViewModel.createNewList(
                            closeSheet = {
                                sheetState.hide()
                            }
                        )
                    }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            dismissBottomSheet()
                        }
                    }
                },
                enabled = listName.isNotEmpty()
            )
        }
        Spacer(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        )
        SystemNavBarSpacer()
    }
}
