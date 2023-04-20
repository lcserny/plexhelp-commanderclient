package net.cserny.commanderclient.ui

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import net.cserny.commanderclient.data.Server
import net.cserny.commanderclient.data.ServerStatus
import net.cserny.commanderclient.data.ServersState
import java.util.Collections
import java.util.Date
import kotlin.concurrent.thread

class ServersViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ServersState())
    val uiState: StateFlow<ServersState> = _uiState.asStateFlow()

    fun loadServers(callback: () -> Unit) {
        thread {
            Thread.sleep(5000L)
            _uiState.update { current ->
                current.copy(
                    status = ServerStatus.LOADED,
                    servers = listOf(
                        Server(
                            id = "123",
                            serverName = "winlegion",
                            actionsAvailable = listOf("shutdown"),
                            actionsPending = Collections.emptyList(),
                            lastPingDate = Date().time
                        )
                    )
                )
            }
            callback()
        }
    }
}