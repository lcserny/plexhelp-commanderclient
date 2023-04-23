package net.cserny.commanderclient.service

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import net.cserny.commanderclient.data.ServerAction
import net.cserny.commanderclient.data.ServerDto
import java.util.*

class MongoDbService {

    fun getServers(): Flow<List<ServerDto>> {
        return flow {
            // TODO
            delay(5000L)
            emit(
                listOf(
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

    fun sendShutdown(): Flow<Unit> {
        return flow {
            // TODO: execute shutdown http request
            delay(5000L)
            emit(Unit)
        }
    }
}