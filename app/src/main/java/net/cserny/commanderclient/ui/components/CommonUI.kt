package net.cserny.commanderclient.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BoxedButton(text: String, onClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxWidth().padding(15.dp)
    ) {
        Surface(elevation = 10.dp) {
            Button(
                modifier = Modifier.width(250.dp).height(40.dp),
                onClick = onClick
            ) {
                Text(text)
            }
        }
    }
}
