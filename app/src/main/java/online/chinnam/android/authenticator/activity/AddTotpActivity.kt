package online.chinnam.android.authenticator.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import online.chinnam.android.authenticator.controllers.AddTotpController
import online.chinnam.android.authenticator.ui.components.AuthenticatorAppBar
import online.chinnam.android.authenticator.ui.theme.AuthenticatorTheme

class AddTotpActivity : ComponentActivity() {

    private lateinit var controller: AddTotpController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        controller = ViewModelProvider(this)[AddTotpController::class.java]
        enableEdgeToEdge()
        setContent {
            AuthenticatorTheme {
                Scaffold(
                    topBar = {
                        AuthenticatorAppBar(
                            title = "Add New Totp",
                            showAppIcon = false,
                            showBack = true,
                            onBackClick = { finish() }
                        )
                    }
                ) { innerPadding ->
                    Column(
                        modifier = Modifier.padding(innerPadding)
                    ) {

                    }
                }
            }
        }
    }

}