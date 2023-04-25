package net.cserny.commanderclient.data

enum class ServerStatus {
    LOADING,
    LOADED;
}

enum class ServerAction {
    SHUTDOWN,
}

data class ServerDto(
    val id: String,
    val serverName: String,
    val actionsAvailable: List<ServerAction>,
    var actionsPending: MutableList<ServerAction>,
    val lastPingDate: Long,
)

val emptyServerDto = ServerDto( "", "", listOf(), mutableListOf(), 0L )