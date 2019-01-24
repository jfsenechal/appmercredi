package be.marche.mercredi.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import okhttp3.Response

//https://medium.com/androiddevelopers/workmanager-basics-beba51e94048?linkId=62685880
/*class UploadWorker(appContext: Context, workerParams: WorkerParameters)
    : Worker(appContext, workerParams) {

    override fun doWork(): Result {
        try {
            // Get the input
            val imageUriInput = inputData.getString("KEY_IMAGE_URI")

            // Do the work
            val response = upload(imageUriInput)

            // Create the output of the work
            val imageResponse = response.body()
            val imgLink = imageResponse.data.link
            // workDataOf (part of KTX) converts a list of pairs to a [Data] object.
            val outputData = workDataOf("Constants.KEY_IMAGE_URI to imgLink")

            return Result.success(outputData)

        } catch (e: Exception) {
            return Result.failure()
        }
    }

    fun upload(imageUri: String?): Response {
        TODO("Webservice request code here")
        // Webservice request code here; note this would need to be run
        // synchronously for reasons explained below.
    }

}*/