package com.example.animenation.app.util

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.util.concurrent.CancellationException

@Suppress("RedundantSuspendModifier")
suspend inline fun <T, R> T.coRunCatching(block: T.() -> R): Result<R> {
    return try {
        Result.success(block())
    } catch (c: CancellationException) {
        throw c
    } catch (e: Throwable) {
        Result.failure(e)
    }
}

fun Job?.safeRunWithDelay(
    context: AppCompatActivity,
    durationMilliseconds: Long,
    dispatcher: CoroutineDispatcher = Dispatchers.Main,
    block: () -> Unit) : Job = run {
    return@run context.lifecycleScope.launch(dispatcher) {
        while (this.isActive) {
            delay(durationMilliseconds)
            block()
        }
    }
}