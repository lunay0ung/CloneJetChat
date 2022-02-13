package com.luna.clonejetchat.componenets

import androidx.compose.material.DrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.material.rememberDrawerState
import androidx.compose.material3.NavigationDrawer
import androidx.compose.runtime.Composable
import com.luna.clonejetchat.theme.JetchatTheme

@Composable
fun JetchatScaffold(
    drawerstate: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    onProfileClicked: (String) -> Unit,
    onChatClicked: (String) -> Unit,
    content: @Composable () -> Unit
) {
    JetchatTheme {
        NavigationDrawer(
            drawerState = drawerstate,
            drawerContent = {
                JetchatDrawer(
                    onProfileClicked = onProfileClicked,
                    onChatClicked = onChatClicked
                )
            },
            content = content
        )
    }
}