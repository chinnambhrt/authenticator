package online.chinnam.android.authenticator.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import online.chinnam.android.authenticator.R
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
                    topBar = { AuthenticatorAppBar(showBack = false, showSettings = true) },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                            .padding(16.dp, 5.dp),
                    ) {
                        HomeScreen(controller)
                    }
                }
            }
        }
    }

}