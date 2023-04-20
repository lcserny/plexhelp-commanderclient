package net.cserny.commanderclient.data

enum class ServerStatus {
    LOADING,
    LOADED;
}

data class Server(
    val id: Any,
    val serverName: String,
    val actionsAvailable: List<String>,
    val actionsPending: List<String>,
    val lastPingDate: Long,
)