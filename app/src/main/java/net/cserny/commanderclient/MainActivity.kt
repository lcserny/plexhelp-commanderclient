package net.cserny.commanderclient

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.cserny.commanderclient.ui.CommanderApp
import net.cserny.commanderclient.ui.theme.CommanderClientTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CommanderClientTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
//                    Conversation(listOf(Message("Android", "Jetpack Compose!")))
                    CommanderApp()
                }
            }
        }
    }
}

//data class Message(val author: String, val body: String)
//
//@Composable
//fun Conversation(messages: List<Message>) {
//    LazyColumn {
//        items(messages) { message ->
//            MessageCard(message = message)
//        }
//    }
//}
//
//@Composable
//fun MessageCard(message: Message) {
//    // Add padding around our message
//    Row(modifier = Modifier.padding(all = 8.dp)) {
//        Image(
//            painter = painterResource(R.drawable.ic_launcher_foreground),
//            contentDescription = "An image",
//            modifier = Modifier
//                // Set image size to 40 dp
//                .size(40.dp)
//                // Clip image to be shaped as a circle
//                .clip(CircleShape)
//                .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
//        )
//
//        // Add a horizontal space between the image and the column
//        Spacer(modifier = Modifier.width(8.dp))
//
//        // We keep track if the message is expanded or not in this
//        // variable
//        var isExpanded by remember { mutableStateOf(false) }
//
//        // surfaceColor will be updated gradually from one color to the other
//        val surfaceColor by animateColorAsState(
//            if (isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface,
//        )
//
//        // We toggle the isExpanded variable when we click on this Column
//        Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
//            Text(
//                text = message.author,
//                color = MaterialTheme.colors.secondaryVariant,
//                style = MaterialTheme.typography.subtitle2
//            )
//
//            // Add a vertical space between the author and message texts
//            Spacer(modifier = Modifier.height(4.dp))
//
//            Surface(
//                shape = MaterialTheme.shapes.medium,
//                elevation = 1.dp,
//                // surfaceColor color will be changing gradually from primary to surface
//                color = surfaceColor,
//                // animateContentSize will change the Surface size gradually
//                modifier = Modifier.animateContentSize().padding(1.dp)
//            ) {
//                Text(
//                    text = message.body,
//                    modifier = Modifier.padding(all = 4.dp),
//                    // If the message is expanded, we display all its content
//                    // otherwise we only display the first line
//                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
//                    style = MaterialTheme.typography.body2
//                )
//
//            }
//        }
//    }
//}
//
//@Preview
//@Composable
//fun PreviewConversation() {
//    CommanderClientTheme(darkTheme = true) {
//        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
//            Conversation(listOf(
//                Message("Colleague", "Hey, take a look here!Hey, take a look here!Hey, take a look here!Hey, take a look here!Hey, take a look here!"),
//                Message("Leo", "No thanks!"),
//                Message("Sabina", "This seems ok to me"),
//                Message("Leo", "I dont have anything to add hereI dont have anything to add hereI dont have anything to add hereI dont have anything to add hereI dont have anything to add hereI dont have anything to add hereI dont have anything to add here"),
//                Message("Colleague", "Are you sure???"),
//            ))
//        }
//    }
//}
