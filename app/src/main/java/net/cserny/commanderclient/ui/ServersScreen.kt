package net.cserny.commanderclient.ui

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
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
import net.cserny.commanderclient.ui.components.BoxedButton
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
            BoxedButton(server.serverName) {
                viewModel.setCurrentServer(server)
                navController.navigate(CommanderScreen.Commands.name)
            }
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
                id = "1",
                serverName = "winlegion",
                actionsAvailable = listOf(ServerAction.SHUTDOWN),
                actionsPending = mutableListOf(),
                lastPingDate = Date().time
            )
        ),
        viewModel = ServersViewModel(),
        navController = NavHostController(PreviewActivity())
    )
}