package net.cserny.commanderclient.ui

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.PreviewActivity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import net.cserny.commanderclient.data.ServerAction
import net.cserny.commanderclient.data.ServerDto
import net.cserny.commanderclient.data.ServerStatus
import java.util.*

@Composable
fun ServersScreen(
    status: ServerStatus,
    serverDtos: List<ServerDto>,
    viewModel: ServersViewModel,
    navController: NavHostController
) {
    when (status) {
        ServerStatus.LOADING -> LoadingScreen()
        ServerStatus.LOADED -> Servers(serverDtos, viewModel, navController)
    }
}

@Composable
fun Servers(serverDtos: List<ServerDto>, viewModel: ServersViewModel, navController: NavHostController) {
    LazyColumn(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .then(Modifier.fillMaxWidth())
            .then(Modifier.padding(20.dp))) {
        items(serverDtos) { server ->
            Button(
                modifier = Modifier.width(250.dp),
                onClick = {
                    viewModel.setCurrentServer(server)
                    navController.navigate(CommanderScreen.Commands.name)
                }
            ) {
                Text(server.serverName)
            }
            Divider(thickness = 1.dp)
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .then(Modifier.padding(10.dp))
                .then(Modifier.size(150.dp))
        )
    }
}

@Preview
@Composable
fun ServersScreenPreview() {
    ServersScreen(
        ServerStatus.LOADED, listOf(
            ServerDto(
                serverName = "winlegion",
                actionsAvailable = listOf(ServerAction.SHUTDOWN),
                actionsPending = Collections.emptyList(),
                lastPingDate = Date().time
            )
        ),
        viewModel = ServersViewModel(),
        navController = NavHostController(PreviewActivity())
    )
}