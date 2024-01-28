package com.projects.moviemanager.common.ui.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun forceKeyboardAction(
    context: Context,
    currentFocusedView: View,
    keyboardAction: Int
) {
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE)
        as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(
        currentFocusedView.windowToken,
        keyboardAction
    )
}
