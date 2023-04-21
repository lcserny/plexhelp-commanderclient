package net.cserny.commanderclient.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import net.cserny.commanderclient.data.ServerAction
import net.cserny.commanderclient.data.ServerDto
import net.cserny.commanderclient.data.ServerStatus
import net.cserny.commanderclient.data.ServersState
import java.util.Collections
import java.util.Date
import kotlin.concurrent.thread

class ServersViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ServersState())
    val uiState: StateFlow<ServersState> = _uiState.asStateFlow()

    fun loadServers() {
        thread {
            // TODO: mongo
            Thread.sleep(5000L)
            _uiState.update { current ->
                current.copy(
                    status = ServerStatus.LOADED,
                    serverDtos = listOf(
                        ServerDto(
                            serverName = "winlegion",
                            actionsAvailable = listOf(ServerAction.SHUTDOWN),
                            actionsPending = Collections.emptyList(),
                            lastPingDate = Date().time
                        )
                    )
                )
            }
        }
    }

    fun setCurrentServer(serverDto: ServerDto) {
        _uiState.update { current ->
            current.copy(
                currentServer = serverDto
            )
        }
    }
}