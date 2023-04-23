package net.cserny.commanderclient.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val mongoDbService: MongoDbService = MongoDbService()

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

    fun executeShutdown() {
        CoroutineScope(context = Dispatchers.IO).launch {
            mongoDbService.sendShutdown().collect { _ ->
                _uiState.update { current ->
                    current.copy(
                        actionExecuted = true
                    )
                }
            }
        }
    }
}