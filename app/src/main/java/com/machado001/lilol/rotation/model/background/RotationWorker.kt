package com.machado001.lilol.rotation.model.background

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.machado001.lilol.MyNotification
import com.machado001.lilol.common.extensions.TAG
import com.machado001.lilol.common.extensions.toRotations
import com.machado001.lilol.rotation.model.repository.RotationRepository
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.last

class RotationWorker(
    private val appContext: Context,
    workerParams: WorkerParameters,
    private val repository: RotationRepository
) :
    CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            with(repository) {
                val networkSource = fetchRemoteRotations().toRotations().toString()
                val localSource = fetchLocalRotations().last()
                compareLocalAndNetworkData(networkSource, localSource)
                Result.success()
            }
        } catch (e: Exception) {
            Log.d(TAG, "fail: $e")
            currentCoroutineContext().ensureActive()
            Result.failure()
        }
    }

    private fun compareLocalAndNetworkData(
        networkDataSource: String,
        localDataSource: String
    ) {
        if (networkDataSource != localDataSource) {
            MyNotification(appContext).showNotification()
        }
    }
}