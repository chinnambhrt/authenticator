package online.chinnam.android.authenticator.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import online.chinnam.android.authenticator.controllers.AddTotpController
import online.chinnam.android.authenticator.iface.IController
import online.chinnam.android.authenticator.ui.components.Banner
import online.chinnam.android.authenticator.ui.components.TextInputComponent


@Composable
fun AddTotpScreen(iController: IController) {

    val controller = iController as AddTotpController

    val state = iController.getState().collectAsState().value

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Column {


            // rest of the UI

            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {


                // Error banner
                if (state.error.isNotEmpty()) {
                    Banner(
                        content = state.error,
                    )
                }


                // Disclaimer text
                Text(
                    text = "Enter the details of the account",
                    modifier = Modifier.padding(8.dp),
                    fontSize = 16.sp
                )


                TextInputComponent(
                    title = "Account Name",
                    value = state.name,
                    onChange = { controller.update(state.copy(name = it)) },
                    modifier = Modifier.fillMaxWidth()
                )

                TextInputComponent(
                    title = "Issuer",
                    value = state.issuer,
                    onChange = { controller.update(state.copy(issuer = it)) },
                    modifier = Modifier.fillMaxWidth()
                )

                TextInputComponent(
                    title = "Base32 Secret",
                    value = state.secret,
                    onChange = { controller.update(state.copy(secret = it)) },
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    modifier = Modifier
                        .padding(8.dp, 5.dp)
                        .fillMaxWidth()
                ) {
                    TextInputComponent(
                        title = "Algorithm",
                        value = state.algorithm,
                        onChange = { controller.update(state.copy(algorithm = it)) }
                    )

                    TextInputComponent(
                        title = "Digits",
                        value = state.digits.toString(),
                        onChange = { controller.update(state.copy(digits = it.toInt())) },
                        numericInput = true
                    )
                }

                Row(
                    modifier = Modifier
                        .padding(8.dp, 5.dp)
                        .fillMaxWidth()
                ) {
                    TextInputComponent(
                        title = "Period",
                        value = state.period.toString(),
                        onChange = { controller.update(state.copy(period = it.toInt())) },
                        numericInput = true
                    )
                }

            }
        }

        // Create button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    controller.save()
                },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Text("Create")
            }
        }

    }
}