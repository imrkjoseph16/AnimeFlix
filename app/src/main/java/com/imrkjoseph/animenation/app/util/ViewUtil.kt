package com.imrkjoseph.animenation.app.util

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import com.imrkjoseph.animenation.app.util.Default.Companion.MONTH_DAY_YEAR_TIME
import java.lang.String.format
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.random.Random

class ViewUtil @Inject constructor() {
    fun generateRandomCharacters() : String {
        // Descriptive alphabet using three CharRange objects, concatenated
        val alphabet: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

        // Build list from 20 random samples from the alphabet,
        // and convert it to a string using "" as element separator
        return List(20) { alphabet.random() }.joinToString("")
    }

    suspend fun getVideoDuration(context: Context, uri: String): String {
        try {
            val timeInMillis = suspendCoroutine { coroutine ->
                MediaPlayer.create(context, Uri.parse(uri)).also {
                    val millis = it.duration.toLong()

                    it.reset()
                    it.release()

                    coroutine.resume(millis)
                }
            }
            return convertMillieToHhMmSs(timeInMillis)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return "00:00:00"
    }

    private fun convertMillieToHhMmSs(millis: Long): String {
        val seconds = millis / 1000
        val second = seconds % 60
        val minute = seconds / 60 % 60
        val hour = seconds / (60 * 60) % 24
        return if (hour > 0) String.format("%02d:%02d:%02d", hour, minute, second)
        else String.format("%02d:%02d", minute, second)
    }

    @SuppressLint("DefaultLocale")
    fun msToTimeConverter(millis: Int): String = format(
        "%02d:%02d",
        TimeUnit.MILLISECONDS.toMinutes(millis.toLong()) - TimeUnit.HOURS.toMinutes(
            TimeUnit.MILLISECONDS.toHours(millis.toLong())
        ),
        TimeUnit.MILLISECONDS.toSeconds(millis.toLong()) - TimeUnit.MINUTES.toSeconds(
            TimeUnit.MILLISECONDS.toMinutes(
                millis.toLong()
            )
        )
    )

    companion object {
        fun getRandomRatings() = Random.nextInt(0, 100)

        @SuppressLint("SimpleDateFormat")
        fun convertStringDate(formatDate: String, inputDate: String?): String? {
            return inputDate?.let {
                val convertedDate: Date
                val dateFormat = SimpleDateFormat(formatDate)
                return try {
                    convertedDate = dateFormat.parse(it) as Date
                    val dfOutput = SimpleDateFormat(MONTH_DAY_YEAR_TIME)
                    dfOutput.format(convertedDate)
                } catch (ignored: ParseException) {
                    it
                }
            } ?: " "
        }
    }
}