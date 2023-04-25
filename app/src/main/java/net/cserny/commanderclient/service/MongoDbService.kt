package net.cserny.commanderclient.service

import android.util.Log
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import io.github.cdimascio.dotenv.Dotenv
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import net.cserny.commanderclient.data.ServerAction
import net.cserny.commanderclient.data.ServerDto
import okhttp3.Headers
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.nio.charset.StandardCharsets

const val API_KEY = "MONGODB_API_KEY"
const val DATA_SOURCE = "MONGODB_DATA_SOURCE"

const val apiUrl = "https://eu-central-1.aws.data.mongodb-api.com/app/data-hehst/endpoint/data/v1"
const val database = "videosmover"
const val collection = "server_commands"
const val timeOffset = 60000; // 1 minute in millis

data class ObjectId(@SerializedName("\$oid") val oid: String)
data class FilterById(val _id: ObjectId)
data class FilterData(val dataSource: String, val database: String, val collection: String, val filter: Any)
data class UpdateActionsPending(val actionsPending: List<String>)
data class UpdateSet(@SerializedName("\$set") val set: Any)
data class UpdateData(val dataSource: String, val database: String, val collection: String, val filter: Any, val update: UpdateSet)
data class ResponseDocuments(val documents: List<ServerCommands>)
data class ServerCommands(val _id: String, val actionsAvailable: List<String>, val actionsPending: List<String>, val lastPingDate: Long, val serverName: String)

class MongoDbService(
    private val httpClient: OkHttpClient = OkHttpClient(),
    private val gson: Gson = Gson(),
    private val dotenv: Dotenv,
) {
    private val headers = Headers.Builder().add("apiKey", dotenv[API_KEY]).build()
    private val json = "application/json".toMediaType()

    fun getServers(): Flow<List<ServerDto>> {
        return flow {
            var serverDtos = listOf<ServerDto>()

            try {
                val filterData = FilterData(dotenv[DATA_SOURCE], database, collection, Unit)
                val reqBody = gson.toJson(filterData).toRequestBody(json)
                val req = Request.Builder()
                    .url("$apiUrl/action/find")
                    .headers(headers)
                    .post(reqBody)
                    .build()

                val rsp = httpClient.newCall(req).execute()

                val respBody = rsp.body!!.string()
                val responseDocs = gson.fromJson(respBody, ResponseDocuments::class.java)

                serverDtos = convert(responseDocs.documents)
            } catch (err: Error) {
                Log.e(MongoDbService::javaClass.name, err.toString())
            }

            emit(serverDtos)
        }
    }

    fun sendShutdown(serverDto: ServerDto): Flow<Unit> {
        return flow {
            serverDto.actionsPending.add(ServerAction.SHUTDOWN)

            try {
                val updateData = UpdateData(dotenv[DATA_SOURCE], database, collection, FilterById(ObjectId(serverDto.id)),
                    UpdateSet(UpdateActionsPending(serverDto.actionsPending.map { a -> a.toString().lowercase() }))
                )
                val reqBody = gson.toJson(updateData).toByteArray(StandardCharsets.UTF_8).toRequestBody(json)
                val req = Request.Builder()
                    .url("$apiUrl/action/updateOne")
                    .headers(headers)
                    .post(reqBody)
                    .build()

                httpClient.newCall(req).execute()
            } catch (err: Error) {
                Log.e(MongoDbService::javaClass.name, err.toString())
            }

            emit(Unit)
        }
    }

    private fun convert(serverCommands: List<ServerCommands>): List<ServerDto> {
        val currentTime = System.currentTimeMillis()
        return serverCommands
            .filter { sc -> currentTime <= sc.lastPingDate + timeOffset }
            .map { sc -> convert(sc) }
    }

    private fun convert(sc: ServerCommands): ServerDto {
        return ServerDto(
            id = sc._id,
            serverName = sc.serverName,
            actionsAvailable = sc.actionsAvailable.map { a -> ServerAction.valueOf(a.uppercase()) },
            actionsPending = sc.actionsPending.map { a -> ServerAction.valueOf(a.uppercase()) }.toMutableList(),
            lastPingDate = sc.lastPingDate
        )
    }
}