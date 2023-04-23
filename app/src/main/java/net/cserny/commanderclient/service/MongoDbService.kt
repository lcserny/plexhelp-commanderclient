package net.cserny.commanderclient.service

import android.util.Log
import androidx.lifecycle.liveData
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import net.cserny.commanderclient.data.ServerAction
import net.cserny.commanderclient.data.ServerDto
import okhttp3.OkHttpClient
import okhttp3.Request
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

            try {
                val client: OkHttpClient = OkHttpClient()
                val req = Request.Builder().url("https://jsonplaceholder.typicode.com/todos/1").build()
                val rsp = client.newCall(req).execute()

                val body = rsp.body?.string()
                val todo = Gson().fromJson(body, Todo::class.java)

                Log.i("LEO", todo.toString())
            } catch (err: Error) {
                Log.e("LEO", err.toString())
            }

            emit(Unit)
        }
    }

    data class Todo(val userId: Int, val id: Int, val title: String, val completed: Boolean)
}