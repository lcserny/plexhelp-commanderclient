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
import net.cserny.commanderclient.ui.CommanderScreen
import net.cserny.commanderclient.ui.ServersViewModel

@Composable
fun ShutdownScreen(viewModel: ServersViewModel, navController: NavHostController, actionExecuted: Boolean) {
    if (actionExecuted) {
        val text = stringResource(R.string.shutdown_executed)
        Toast.makeText(LocalContext.current, text, Toast.LENGTH_LONG).show()
        viewModel.resetState()
        navController.navigate(CommanderScreen.Servers.name)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .then(Modifier.fillMaxWidth())
            .then(Modifier.padding(20.dp))
    ) {
        Button(
            modifier = Modifier.width(250.dp),
            onClick = {
                viewModel.executeShutdown()
            }
        ) {
            Text(stringResource(R.string.execute))
        }
    }
}

@Preview
@Composable
fun ShutdownScreenPreview() {
    ShutdownScreen(ServersViewModel(),
        NavHostController(PreviewActivity()),
        false)
}