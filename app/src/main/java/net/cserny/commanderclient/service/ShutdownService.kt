package net.cserny.commanderclient.service

import android.util.Log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ShutdownService {

    fun shutdown(): Flow<Unit> {
        return flow {
            // TODO: execute shutdown http request
            emit(Unit)
        }
    }
}