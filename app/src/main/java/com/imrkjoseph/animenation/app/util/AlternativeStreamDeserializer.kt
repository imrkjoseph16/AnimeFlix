package com.imrkjoseph.animenation.app.util

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.imrkjoseph.animenation.dashboard.shared.presentation.video.dto.FileMoonStreamLink
import com.imrkjoseph.animenation.dashboard.shared.presentation.video.dto.StreamData
import com.imrkjoseph.animenation.dashboard.shared.presentation.video.dto.Subtitles
import java.lang.reflect.Type

class AlternativeStreamDeserializer: JsonDeserializer<FileMoonStreamLink> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): FileMoonStreamLink {
        val dataObject = json?.asJsonArray?.get(0)?.asJsonObject
        val data = dataObject?.get("data")?.asJsonObject

        return FileMoonStreamLink(
            data = StreamData(
                file = data?.get("file")?.asString,
                sub = data?.get("sub")?.asJsonArray?.map { sub ->
                    Subtitles(
                        file = sub.asJsonObject.get("file").asString,
                        lang = sub.asJsonObject.get("lang").asString
                    )
                }
            ),
            name = dataObject?.get("name")?.asString,
        )
    }
}