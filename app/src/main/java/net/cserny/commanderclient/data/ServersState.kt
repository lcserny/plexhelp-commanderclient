package net.cserny.commanderclient.data

import java.util.Collections

data class ServersState(
    val status: ServerStatus = ServerStatus.LOADING,
    val serverDtos: List<ServerDto> = Collections.emptyList(),
    var currentServer: ServerDto = emptyServerDto,
    var actionExecuted: Boolean = false,
)
