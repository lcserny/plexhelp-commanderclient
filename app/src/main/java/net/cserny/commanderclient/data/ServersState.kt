package net.cserny.commanderclient.data

import java.util.Collections

data class ServersState(
    val status: ServerStatus = ServerStatus.LOADING,
    val servers: List<Server> = Collections.emptyList(),
)
