package com.luna.clonejetchat

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.google.accompanist.insets.ProvideWindowInsets
import com.luna.clonejetchat.conversation.BackPressHandler
import com.luna.clonejetchat.conversation.LocalBackPressedDispatcher
import kotlinx.coroutines.launch

class NavActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            ProvideWindowInsets(consumeWindowInsets = false) {
                CompositionLocalProvider(
                    LocalBackPressedDispatcher provides this.onBackPressedDispatcher
                ) {
                    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

                    val drawerOpen by viewModel.drawerShouldBeOpened.collectAsState()
                    if (drawerOpen) {
                        LaunchedEffect(Unit) {
                            drawerState.open()
                            viewModel.resetOpenDrawerAction()
                        }
                    }

                    val scope = rememberCoroutineScope()
                    if(drawerState.isOpen) {
                        BackPressHandler {
                            scope.launch {
                                drawerState.close()
                            }
                        }
                    }
                }
            }
        }
    }
}