package online.chinnam.android.authenticator.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import online.chinnam.android.authenticator.controllers.HomeController
import online.chinnam.android.authenticator.ui.components.AuthenticatorAppBar
import online.chinnam.android.authenticator.ui.screens.HomeScreen
import online.chinnam.android.authenticator.ui.theme.AuthenticatorTheme

class HomeActivity : ComponentActivity() {

    private lateinit var controller: HomeController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        controller = ViewModelProvider(this)[HomeController::class.java]
        enableEdgeToEdge()
        setContent {
            AuthenticatorTheme {
                Scaffold(
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = { controller.addNewAccount() }
                        ) {
                            Icon(
                                Icons.Filled.QrCodeScanner,
                                contentDescription = "Add",
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    },
                    topBar = {
                        AuthenticatorAppBar(
                            showBack = false,
                            showSettings = true,
                            onSettingsClick = { controller.onSettingsClicked() })
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                            .padding(10.dp, 5.dp),
                    ) {
                        HomeScreen(controller)
                    }
                }
            }
        }
    }


    override fun onResume() {
        super.onResume()
        controller.loadSettings()
        controller.fetchTotp()
    }

    override fun onStart() {
        super.onStart()
        controller.fetchTotp()
        controller.loadSettings()
    }

}