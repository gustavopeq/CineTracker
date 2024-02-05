package com.projects.moviemanager.features.home.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieClipSpec
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.projects.moviemanager.R
import com.projects.moviemanager.common.util.UiConstants.HOME_LOGO_SIZE

@Composable
fun HomeLogoAnimation() {
    val preloaderLottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            R.raw.homelogoanimation
        )
    )

    val preloaderProgress by animateLottieCompositionAsState(
        preloaderLottieComposition,
        speed = 0.75f,
        iterations = 1,
        isPlaying = true,
        restartOnPlay = false,
        clipSpec = LottieClipSpec.Frame(
            17
        )
    )

    LottieAnimation(
        composition = preloaderLottieComposition,
        progress = preloaderProgress,
        modifier = Modifier.size(HOME_LOGO_SIZE.dp)
    )
}
