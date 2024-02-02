package com.projects.moviemanager.common.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.projects.moviemanager.R
import com.projects.moviemanager.common.ui.components.button.ClassicButton
import com.projects.moviemanager.common.ui.util.UiConstants.DEFAULT_PADDING
import com.projects.moviemanager.common.ui.util.UiConstants.ERROR_ANIMATION_SIZE
import com.projects.moviemanager.common.ui.util.UiConstants.SECTION_PADDING

@Composable
fun ErrorScreen(
    onTryAgain: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(0.35f))
        ErrorIconAnimation()
        Spacer(modifier = Modifier.height(DEFAULT_PADDING.dp))
        Text(
            text = stringResource(id = R.string.generic_error_message),
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Spacer(modifier = Modifier.height(SECTION_PADDING.dp))

        ClassicButton(
            buttonText = stringResource(id = R.string.try_again_button),
            onClick = onTryAgain
        )
        Spacer(modifier = Modifier.weight(0.65f))
    }
}

@Composable
fun ErrorIconAnimation() {
    val preloaderLottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            R.raw.erroranimation
        )
    )

    val preloaderProgress by animateLottieCompositionAsState(
        preloaderLottieComposition,
        iterations = 2,
        isPlaying = true
    )

    LottieAnimation(
        composition = preloaderLottieComposition,
        progress = preloaderProgress,
        modifier = Modifier.size(ERROR_ANIMATION_SIZE.dp)
    )
}
