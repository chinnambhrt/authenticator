package online.chinnam.android.authenticator.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import online.chinnam.android.authenticator.R


@Composable
fun SettingGroupTile(
    title: String = "Group Title",
    icon: ImageVector? = null
) {
    ListItem(
        headlineContent = {
            Text(
                title,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.tertiary
            )
        },
        leadingContent = {
            if (icon != null) {
                Icon(icon, contentDescription = null)
            } else {
                Box(modifier = Modifier.size(24.dp))
            }
        }
    )

}

@Composable
fun SettingTile(
    title: String = "Setting Title",
    subtitle: String = "Subtitle of the setting",
    icon: ImageVector? = null,
    proFeature: Boolean = false,
    enabled: Boolean = true,
    onClick: (() -> Unit)? = null
) {

    ListItem(
        modifier = Modifier.clickable { onClick?.invoke() },
        headlineContent = { Text(title, fontSize = 18.sp) },
        supportingContent = { Text(subtitle) },
        trailingContent = {},
        leadingContent = {
            if (proFeature && enabled.not()) {
                Image(
                    painterResource(R.drawable.pro_icon),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                if (icon != null) {
                    Icon(icon, contentDescription = null)
                } else {
                    Box(modifier = Modifier.size(24.dp))
                }
            }
        }

    )

}

@Composable
fun SwitchSettingTile(
    title: String = "Setting Title",
    subtitle: String = "Subtitle of the setting",
    checked: Boolean = false,
    icon: ImageVector? = null,
    proFeature: Boolean = false,
    enabled: Boolean = true,
    onCheckedChange: (Boolean) -> Unit
) {

    ListItem(
        headlineContent = { Text(title, fontSize = 18.sp) },
        supportingContent = { Text(subtitle) },
        trailingContent = {
            Switch(
                checked = checked,
                onCheckedChange = {
                    onCheckedChange(!checked)
                }
            )
        },
        leadingContent = {
            if (proFeature && enabled.not()) {
                Image(
                    painterResource(R.drawable.pro_icon),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                if (icon != null) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.primary)
                    ) {
                        Icon(icon, contentDescription = null, modifier = Modifier.size(20.dp))
                    }
                } else {
                    Box(modifier = Modifier.size(24.dp))
                }
            }
        },
        modifier = Modifier.clickable { onCheckedChange(!checked) }
    )

}