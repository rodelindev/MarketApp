package com.rodelindev.marketapp.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class UploadWorker (appContext: Context, workerParams: WorkerParameters) : Worker(appContext,workerParams) {

    override fun doWork(): Result {
        println("Work 17 minuts")
        return Result.success()
    }
}
