package online.chinnam.android.authenticator.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import online.chinnam.android.authenticator.R

@Composable
fun AuthenticatorAppBar(
    title: String = "Authenticator",
    showAppIcon: Boolean = true,
    showBack: Boolean = false,
    onBackClick: () -> Unit = {},
    showSettings: Boolean = false,
    onSettingsClick: () -> Unit = {}
) {
    ListItem(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 38.dp),
        headlineContent = {
            Text(
                title,
                fontSize = 22.sp
            )
        },
        leadingContent = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                if (showBack) {
                    IconButton(
                        onClick = { onBackClick() }
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
                if(showAppIcon) {
                    Image(
                        painterResource(R.drawable.app_icon),
                        contentDescription = "App Icon",
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        },
        trailingContent = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                if (showSettings) {
                    IconButton(
                        onClick = { onSettingsClick() }
                    ) {
                        Icon(
                            Icons.Filled.Settings,
                            contentDescription = null
                        )
                    }
                }
            }

        }
    )
}