package ru.ok.itmo.example

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.datastore.core.Serializer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

class DataChatSerializer: Serializer<ArrayDataChat> {
    override val defaultValue: ArrayDataChat
        get() = ArrayDataChat(null)
    override suspend fun readFrom(input: InputStream): ArrayDataChat {
        return try {
            Json.decodeFromString(
                ArrayDataChat.serializer(),
                input.readBytes().toString()
            )
        } catch (e: SecurityException) {
            defaultValue
        }
    }

    override suspend fun writeTo(t: ArrayDataChat, output: OutputStream) {
        withContext(Dispatchers.IO) {
            output.write(
                Json.encodeToString(ArrayDataChat.serializer(), t)
                    .encodeToByteArray()
            )
        }
    }
}