package net.cserny.commanderclient.data

enum class ServerStatus {
    LOADING,
    LOADED;
}

enum class ServerAction {
    SHUTDOWN,
}

data class ServerDto(
    val serverName: String,
    val actionsAvailable: List<ServerAction>,
    val actionsPending: List<ServerAction>,
    val lastPingDate: Long,
)