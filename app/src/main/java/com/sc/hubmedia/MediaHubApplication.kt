package com.sc.hubmedia

// Application classes control access to external services by creating a central initialization point
import com.cloudinary.android.MediaManager
import android.app.Application

class MediaHubApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        // config cloudinary while pointing to the cloudname
        val config = mapOf(
            "cloud_name" to "dpnad2itu" // get cloud name from cloudinary
        )
        MediaManager.init(this, config)
    }

}