package com.projects.moviemanager.common.ui.components.popup

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.projects.moviemanager.common.util.UiConstants.CARD_ROUND_CORNER
import com.projects.moviemanager.common.util.UiConstants.DEFAULT_PADDING

@Composable
fun ClassicSnackbar(
    snackbarHostState: SnackbarHostState,
    screenContent: @Composable () -> Unit
) {
    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                snackbar = { snackbarData ->
                    Snackbar(
                        modifier = Modifier.padding(DEFAULT_PADDING.dp),
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        shape = RoundedCornerShape(CARD_ROUND_CORNER.dp)
                    ) {
                        Text(
                            text = snackbarData.visuals.message,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    ) { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding)) {
            screenContent()
        }
    }
}
