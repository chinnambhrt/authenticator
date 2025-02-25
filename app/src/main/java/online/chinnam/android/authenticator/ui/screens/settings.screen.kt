package online.chinnam.android.authenticator.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import online.chinnam.android.authenticator.BuildConfig
import online.chinnam.android.authenticator.controllers.SettingsController
import online.chinnam.android.authenticator.iface.IController
import online.chinnam.android.authenticator.ui.components.SettingGroupTile
import online.chinnam.android.authenticator.ui.components.SettingTile
import online.chinnam.android.authenticator.ui.components.SwitchSettingTile

@Composable
fun SettingsScreen(c: IController) {

    val controller = c as SettingsController
    val state = controller.getState().collectAsState().value

    LazyColumn(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.fillMaxSize()
    ) {

        item {
            Column {
                SettingGroupTile(title = "General")
                SettingTile(
                    title = "Add an account",
                    subtitle = "Use this option if you can't scan the QR code.",
                    icon = Icons.Filled.PersonAdd,
                    onClick = { },
                )
                SwitchSettingTile(
                    title = "Tap to copy",
                    subtitle = "Copy the code to clipboard when you tap on it",
                    checked = state.preference.tapToCopy,
                    onCheckedChange = {
                        controller.updatePreferences(
                            state.preference.copy(tapToCopy = it)
                        )
                    }
                )
                SwitchSettingTile(
                    title = "Allow Edits",
                    subtitle = "Allow edits to the account details",
                    checked = state.preference.allowEdits,
                    proFeature = true,
                    enabled = state.proEnabled,
                    onCheckedChange = {
                        controller.proFeature("allowEdits", state.preference.copy(allowEdits = it))
                    }
                )
                SettingTile(
                    title = "Version",
                    subtitle = "Version ${BuildConfig.VERSION_NAME} ${if (BuildConfig.DEBUG) "(debug)" else ""}",
                    onClick = { }
                )
            }
        }

        item {
            Column {
                SettingGroupTile(title = "Security")
                SwitchSettingTile(
                    title = "Lock app",
                    subtitle = "Lock the app when you switch to another app",
                    checked = state.preference.unlockToOpen,
                    onCheckedChange = {
                        controller.updatePreferences(
                            state.preference.copy(unlockToOpen = it)
                        )
                    }
                )
            }
        }

        item {
            Column {
                SettingGroupTile(title = "Backup & Restore")
                SettingTile(
                    title = "Export your accounts",
                    subtitle = "Export your accounts to a file. You can import them later.",
                    proFeature = true,
                    enabled = state.proEnabled,
                    onClick = {
                        controller.proFeature("backup", state.preference.copy(exportData = true))
                    }
                )
                SettingTile(
                    title = "Import accounts",
                    subtitle = "Import your accounts from a file.",
                    proFeature = true,
                    enabled = state.proEnabled,
                    onClick = {
                        controller.proFeature("import", state.preference.copy(importData = true))
                    }
                )
            }
        }

        item {
            Column {
                SettingGroupTile(title = "About")
                SettingTile(
                    title = "Privacy Policy",
                    subtitle = "Read our privacy policy",
                    onClick = { }
                )
                SettingTile(
                    title = "Terms of Service",
                    subtitle = "Read our terms of service",
                    onClick = { }
                )
                SettingTile(
                    title = "Open source licenses",
                    subtitle = "View the open source licenses",
                    onClick = { }
                )
            }
        }

    }

}