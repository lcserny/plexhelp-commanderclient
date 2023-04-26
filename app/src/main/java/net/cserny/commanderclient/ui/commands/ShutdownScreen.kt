package net.cserny.commanderclient.ui.commands

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.PreviewActivity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import net.cserny.commanderclient.R
import net.cserny.commanderclient.data.ServerDto
import net.cserny.commanderclient.data.emptyServerDto
import net.cserny.commanderclient.ui.CommanderScreen
import net.cserny.commanderclient.ui.ServersViewModel
import net.cserny.commanderclient.ui.components.BoxedButton

@Composable
fun ShutdownScreen(
    serverDto: ServerDto,
    viewModel: ServersViewModel,
    actionExecuted: Boolean
) {
    if (actionExecuted) {
        val text = stringResource(R.string.shutdown_executed)
        Toast.makeText(LocalContext.current, text, Toast.LENGTH_LONG).show()
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .then(Modifier.fillMaxWidth())
            .then(Modifier.padding(20.dp))
    ) {
        BoxedButton(stringResource(R.string.execute)) {
            viewModel.executeShutdown(serverDto)
        }
    }
}

@Preview
@Composable
fun ShutdownScreenPreview() {
    ShutdownScreen(
        emptyServerDto,
        ServersViewModel(),
        false
    )
}