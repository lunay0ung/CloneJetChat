package com.luna.clonejetchat.conversation

import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.compose.runtime.*

/**
This Composable can be used with a LocalBackPressedDispatcher to intercept a back press.
Params: onBackPressed - (Event) What to do when back is intercepted
 */
@Composable
fun BackPressHandler(onBackPressed: () -> Unit) {
    // safely update the current 'onBack' lambda when a new one is provided
    val currentOnBackPressed by rememberUpdatedState(onBackPressed)

    // remember in composition a back callback that calls the 'onBackPressed' lambda
    val backCallback = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                currentOnBackPressed()
            }
        }
    }

    val backDispatcher = LocalBackPressedDispatcher.current

    // whenever there's a new dispatcher set up the callback
    DisposableEffect(backDispatcher) {
        backDispatcher.addCallback(backCallback)
        // when the effect leaves the Composision, or there's a new dispatcher, remove the callback
        onDispose {
            backCallback.remove()
        }
    }

}

val LocalBackPressedDispatcher = staticCompositionLocalOf<OnBackPressedDispatcher> {
    error("No Back Dispatcher provided")
}