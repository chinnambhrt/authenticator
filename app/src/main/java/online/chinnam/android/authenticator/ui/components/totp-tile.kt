package online.chinnam.android.authenticator.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import online.chinnam.android.authenticator.entity.TotpEntity
import online.chinnam.android.authenticator.totp.TotpGenerator
import online.chinnam.android.authenticator.totp.TotpTimer
import online.chinnam.android.authenticator.utils.ellipsis

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TotpTile(
    totpEntity: TotpEntity,
    generator: TotpGenerator,
    totpTimer: TotpTimer,
    showMenu: Boolean = false,
    onClick: (Boolean) -> Unit,
    onMenuSelect: (String) -> Unit
) {

    val gState = generator.state.collectAsStateWithLifecycle().value

    val percentage = totpTimer.percentage.collectAsStateWithLifecycle().value

    // animate the percentage
    val animatedPercentage = animateFloatAsState(percentage)

    var showOptions by remember { mutableStateOf(showMenu) }

    LaunchedEffect(Unit) {
        generator.init()
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {

        ListItem(
            headlineContent = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    if (totpEntity.pinned) {
                        Icon(
                            Icons.Filled.PushPin,
                            null,
                            modifier = Modifier.size(18.dp).padding(end = 5.dp),
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }
                    Text(totpEntity.display.ellipsis(30), fontSize = 18.sp)
                }
            },
            supportingContent = {
                Text(
                    gState.otp,
                    fontSize = 32.sp,
                    color = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.87f)
                )
            },
            trailingContent = {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(
                        progress = { animatedPercentage.value },
                        modifier = Modifier.size(28.dp)
                    )
                }
            },

            modifier = Modifier.combinedClickable(
                enabled = true,
                onClick = {
                    onClick(false)
                },
                onLongClick = {
                    onClick(true)
                }
            )
        )

        if (showMenu) {

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                if (totpEntity.pinned) {
                    TotpTileOption(title = "Unpin", icon = Icons.Filled.PushPin) {
                        onMenuSelect("unpin")
                    }
                } else {
                    TotpTileOption(title = "Pin", icon = Icons.Outlined.PushPin) {
                        onMenuSelect("pin")
                    }
                }
                TotpTileOption(title = "Copy", icon = Icons.Filled.ContentCopy) {
                    onMenuSelect("copy")
                }
                TotpTileOption(title = "Delete", icon = Icons.Filled.Delete) {
                    onMenuSelect("delete")
                }
            }

        }


    }


}


@Composable
fun TotpTileOption(
    title: String,
    icon: ImageVector? = null,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp, 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
        ) {
            if (icon != null) {
                Icon(
                    icon,
                    null,
                    modifier = Modifier
//                    .fillMaxWidth(0.2f)
                        .size(28.dp)
                        .padding(end = 10.dp),
                    tint = MaterialTheme.colorScheme.tertiary,
                )
            }
            Text(
                title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.W400,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}