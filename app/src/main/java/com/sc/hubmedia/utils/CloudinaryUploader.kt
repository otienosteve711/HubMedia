package com.sc.hubmedia.utils
// context allows us to access services within screens
import android.content.Context
// URI: this will allow us to generate/receive Url addresses
import android.net.Uri
// Cloudinary classes
// MediaManager : Provides methods for uploads
import com.cloudinary.android.MediaManager
// ErrorInfo : logs any error related to the cloudinary process
import com.cloudinary.android.callback.ErrorInfo
// Upload callback
import com.cloudinary.android.callback.UploadCallback
// Background processing i.e. when user uploads items do not stop over other tasks - coroutines
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object CloudinaryUploader {
    // a suspend function is a function that delays up until it is to be used
    suspend fun uploadImage(
        context: Context,
        imageUri: Uri, // Image path to be uploaded
        preset: String="mediahub_preset",// folder in cloudinary
        onProgress: (Float) -> Unit = {}
    ): String = suspendCoroutine {continuation ->
        // get the media path that user wants to upload
        // set up the methods to show progress, error or interruptions
        MediaManager.get()
            .upload(imageUri)
            .option("resource_type","auto")
            .unsigned(preset)
            .callback(object: UploadCallback{
                override fun onStart(requestId: String?) {}
                override fun onProgress(requestId: String?, bytes: Long, totalBytes: Long) {onProgress(bytes.toFloat())

                }

                override fun onSuccess(requestId: String?, resultData: Map<*, *>?) {
                    // after upload cloudinary returns a url
                    // to the access mediaItem
                    val url = resultData?.get("secure_url") as String
                    if (url != null) {
                        continuation.resume(url)
                    } else {
                        continuation.resumeWithException(
                            Exception("No URL returned")
                        )
                    }

                }

                override fun onError(requestId: String?, error: ErrorInfo?) {
                    continuation.resumeWithException(Exception(error?.description))
                }

                override fun onReschedule(requestId: String?, error: ErrorInfo?) {
                    continuation.resumeWithException(Exception(error?.description))
                }

            }).dispatch(context)

    }
}