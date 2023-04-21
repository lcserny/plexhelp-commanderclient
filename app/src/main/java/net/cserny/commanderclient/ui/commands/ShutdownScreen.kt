package net.cserny.commanderclient.ui.commands

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.cserny.commanderclient.R
import net.cserny.commanderclient.data.ServerAction
import net.cserny.commanderclient.service.ShutdownService
import net.cserny.commanderclient.ui.CommanderScreen
import net.cserny.commanderclient.ui.ServersViewModel
import kotlin.concurrent.thread

@Composable
fun ShutdownScreen(viewModel: ServersViewModel) {
    val context = LocalContext.current
    val text = stringResource(R.string.shutdown_executed)

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
                Toast.makeText(context, text, Toast.LENGTH_LONG).show()
            }
        ) {
            Text(stringResource(R.string.execute))
        }
    }
}

@Preview
@Composable
fun ShutdownScreenPreview() {
    ShutdownScreen(ServersViewModel())
}