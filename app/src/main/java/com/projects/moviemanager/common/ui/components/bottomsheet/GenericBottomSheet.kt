package com.projects.moviemanager.common.ui.components.bottomsheet

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.projects.moviemanager.R
import com.projects.moviemanager.common.ui.theme.MainBarGreyColor
import com.projects.moviemanager.common.ui.components.SystemNavBarSpacer
import com.projects.moviemanager.common.util.UiConstants

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun GenericBottomSheet(
    dismissBottomSheet: () -> Unit,
    content: @Composable () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = { dismissBottomSheet() },
        containerColor = MainBarGreyColor
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .offset(y = (-UiConstants.SMALL_MARGIN).dp),
            text = stringResource(id = R.string.sort_by_header),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Divider(
            color = MaterialTheme.colorScheme.inverseSurface,
            modifier = Modifier.padding(top = UiConstants.SMALL_PADDING.dp)
        )
        content()
        Spacer(modifier = Modifier.height(UiConstants.SMALL_PADDING.dp))
        SystemNavBarSpacer()
    }
}
