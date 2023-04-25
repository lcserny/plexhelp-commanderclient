package net.cserny.commanderclient.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
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
import net.cserny.commanderclient.ui.components.BoxedButton
import java.util.*

@Composable
fun CommandsScreen(serverDto: ServerDto, navController: NavHostController) {
    LazyColumn(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .then(Modifier.fillMaxWidth())
            .then(Modifier.padding(20.dp))) {
        items(serverDto.actionsAvailable) { action ->
            BoxedButton(action.name) {
                navController.navigate(CommanderScreen.convert(action).name)
            }
        }
    }
}

@Preview
@Composable
fun CommandsScreenPreview() {
    CommandsScreen(
        ServerDto(
            id = "1",
            serverName = "winlegion",
            actionsAvailable = listOf(ServerAction.SHUTDOWN),
            actionsPending = Collections.emptyList(),
            lastPingDate = Date().time
        ),
        NavHostController(PreviewActivity())
    )
}
