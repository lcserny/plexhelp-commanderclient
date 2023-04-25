package net.cserny.commanderclient.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.cdimascio.dotenv.dotenv
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import net.cserny.commanderclient.data.ServerDto
import net.cserny.commanderclient.data.ServerStatus
import net.cserny.commanderclient.data.ServersState
import net.cserny.commanderclient.service.MongoDbService
import kotlin.concurrent.thread

class ServersViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ServersState())
    private val mongoDbService: MongoDbService = MongoDbService(dotenv = dotenv {
        directory = "/assets"
        filename = "env"
    })

    val uiState: StateFlow<ServersState> = _uiState.asStateFlow()

    fun resetState() {
        _uiState.value = ServersState()
    }

    fun loadServers() {
        CoroutineScope(context = Dispatchers.IO).launch {
            mongoDbService.getServers().collect { servers ->
                _uiState.update { current ->
                    current.copy(
                        status = ServerStatus.LOADED,
                        serverDtos = servers
                    )
                }
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

    fun executeShutdown(serverDto: ServerDto) {
        CoroutineScope(context = Dispatchers.IO).launch {
            mongoDbService.sendShutdown(serverDto).collect { _ ->
                _uiState.update { current ->
                    current.copy(
                        actionExecuted = true
                    )
                }
            }
        }
    }
}