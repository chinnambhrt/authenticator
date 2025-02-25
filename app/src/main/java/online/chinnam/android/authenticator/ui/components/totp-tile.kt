package online.chinnam.android.authenticator.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import online.chinnam.android.authenticator.entity.TotpEntity
import online.chinnam.android.authenticator.totp.TotpGenerator
import online.chinnam.android.authenticator.totp.TotpTimer

@Composable
fun TotpTile(
    totpEntity: TotpEntity,
    generator: TotpGenerator,
    totpTimer: TotpTimer,
    onClick: () -> Unit
) {

    val gState = generator.state.collectAsStateWithLifecycle().value

    val percentage = totpTimer.percentage.collectAsStateWithLifecycle().value

    // animate the percentage
    val animatedPercentage = animateFloatAsState(percentage)

    LaunchedEffect(Unit) {
        generator.init()
    }

    ListItem(
        headlineContent = {
            Text(totpEntity.display, fontSize = 18.sp)
        },
        supportingContent = {
            Text(gState.otp, fontSize = 32.sp, color = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.87f))
        },
        trailingContent = {

            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                CircularProgressIndicator(
                    progress = { animatedPercentage.value },
                    modifier = Modifier.size(28.dp)
                )
            }
        },
        modifier = Modifier.clickable { onClick() }
    )


}