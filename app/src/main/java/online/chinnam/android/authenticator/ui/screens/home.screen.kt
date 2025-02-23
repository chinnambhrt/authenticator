package online.chinnam.android.authenticator.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import online.chinnam.android.authenticator.R
import online.chinnam.android.authenticator.controllers.HomeController
import online.chinnam.android.authenticator.iface.IController


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun HomeScreen(controller: IController) {

    val homeController = controller as HomeController
    val state = homeController.getState().collectAsState().value

    val pullToRefreshState = rememberPullToRefreshState()


    PullToRefreshBox(
        modifier = Modifier.fillMaxSize(),
        isRefreshing = state.isLoading,
        state = pullToRefreshState,
        onRefresh = { homeController.fetchTotp() }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = if (state.totpList.isEmpty()) Arrangement.Center else Arrangement.Top
        ) {
            if (state.totpList.isEmpty()) {
                item {
                    Column(
                        modifier = Modifier.fillParentMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(R.drawable.empty_illustration),
                            contentDescription = "Empty Illustration",
                            modifier = Modifier.fillMaxWidth(1f)
                        )
                        Text(
                            text = "Add an account to get started",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        )
                    }
                }
            } else {
                items(state.totpList.size) { index ->
                    Box {
                        Text(text = state.totpList[index].display)
                    }
                }
            }
        }
    }
}