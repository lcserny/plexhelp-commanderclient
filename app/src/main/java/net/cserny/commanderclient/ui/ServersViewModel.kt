package net.cserny.commanderclient.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import net.cserny.commanderclient.data.ServersState

class ServersViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ServersState())
    val uiState: StateFlow<ServersState> = _uiState.asStateFlow()
}