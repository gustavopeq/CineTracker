package com.projects.moviemanager.features.details.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.projects.moviemanager.common.ui.components.ComponentPlaceholder
import com.projects.moviemanager.common.util.UiConstants.DEFAULT_MARGIN
import com.projects.moviemanager.common.util.UiConstants.LARGE_MARGIN
import com.projects.moviemanager.common.util.UiConstants.SECTION_PADDING
import com.projects.moviemanager.common.util.UiConstants.SMALL_PADDING

@Composable
fun DetailBodyPlaceholder(posterHeight: Float) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        ComponentPlaceholder(
            modifier = Modifier
                .fillMaxWidth()
                .height((posterHeight * 0.85).dp)
        )
        Spacer(modifier = Modifier.height(SECTION_PADDING.dp))
        ComponentPlaceholder(
            modifier = Modifier
                .padding(start = DEFAULT_MARGIN.dp, end = LARGE_MARGIN.dp)
                .fillMaxWidth()
                .height(18.dp)
        )
        Spacer(modifier = Modifier.height(SMALL_PADDING.dp))
        ComponentPlaceholder(
            modifier = Modifier
                .padding(
                    start = DEFAULT_MARGIN.dp,
                    end = (LARGE_MARGIN * 2).dp
                )
                .fillMaxWidth()
                .height(18.dp)
        )
        Spacer(modifier = Modifier.height(SMALL_PADDING.dp))
        ComponentPlaceholder(
            modifier = Modifier
                .padding(
                    start = DEFAULT_MARGIN.dp,
                    end = (LARGE_MARGIN * 3).dp
                )
                .fillMaxWidth()
                .height(18.dp)
        )
        CategoriesPlaceholder()
        CategoriesPlaceholder()
        CategoriesPlaceholder()
    }
}

@Composable
private fun CategoriesPlaceholder() {
    Spacer(modifier = Modifier.height(SECTION_PADDING.dp))
    ComponentPlaceholder(
        modifier = Modifier
            .padding(start = DEFAULT_MARGIN.dp)
            .width(150.dp)
            .height(18.dp)
    )
    Spacer(modifier = Modifier.height(SMALL_PADDING.dp))
    ComponentPlaceholder(
        modifier = Modifier
            .padding(start = DEFAULT_MARGIN.dp)
            .width(90.dp)
            .height(18.dp)
    )
}
