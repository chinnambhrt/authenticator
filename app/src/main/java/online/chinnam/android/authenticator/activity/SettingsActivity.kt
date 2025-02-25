package online.chinnam.android.authenticator.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import online.chinnam.android.authenticator.controllers.SettingsController
import online.chinnam.android.authenticator.iface.IController
import online.chinnam.android.authenticator.ui.components.AuthenticatorAppBar
import online.chinnam.android.authenticator.ui.screens.SettingsScreen
import online.chinnam.android.authenticator.ui.theme.AuthenticatorTheme

class SettingsActivity: ComponentActivity() {

    private lateinit var controller: IController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        controller = ViewModelProvider(this).get(SettingsController::class.java)

        enableEdgeToEdge()
        setContent {
            AuthenticatorTheme {
                Scaffold (
                    topBar = {
                        AuthenticatorAppBar(
                            title = "Settings",
                            showAppIcon = false,
                            showBack = true,
                            showSettings = false,
                            onBackClick = { finish() }
                        )
                    }
                ) { innerPadding ->
                    Column(
                        modifier = Modifier.fillMaxSize().padding(innerPadding)
                    ) {
                        SettingsScreen(controller)
                    }
                }
            }
        }
    }

}