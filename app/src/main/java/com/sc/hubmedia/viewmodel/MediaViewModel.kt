package com.sc.hubmedia.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.sc.hubmedia.model.MediaItem
import com.sc.hubmedia.navigation.Screen
import com.sc.hubmedia.utils.CloudinaryUploader
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.UUID // generate random IDs

sealed class MediaState{
    object Idle : MediaState()
    object Loading: MediaState()
    object Success: MediaState()
    data class Error(val message: String) : MediaState()
}
class MediaViewModel: ViewModel(){
    // reference variables for processes
    // we need to know our logged-in user
    private val auth = FirebaseAuth.getInstance()
    // initialize firestore
    private val db = FirebaseFirestore.getInstance()
    // access to public media items in viewmodel
    private val _publicMedia= MutableStateFlow<List<MediaItem>>(emptyList())
    // access to public media items in screens using this vm
    val publicMedia: StateFlow<List<MediaItem>> = _publicMedia
    // access to public media items in viewmodel
    private val _myMedia= MutableStateFlow<List<MediaItem>>(emptyList())
    val myMedia: StateFlow<List<MediaItem>> = _myMedia
    private val _allMedia= MutableStateFlow<List<MediaItem>>(emptyList())
    val allMedia: StateFlow<List<MediaItem>> = _allMedia
    private val _mediaState=MutableStateFlow<MediaState>(MediaState.Idle)
    val mediaState: StateFlow<MediaState> = _mediaState
    private val _uploadProgress = MutableStateFlow(0f)
    val uploadProgress: StateFlow<Float> = _uploadProgress

    fun loadPublicMedia(){}
    fun loadMyMedia(){}
    fun loadAllMedia(){}
    fun uploadMedia(
        context: Context,
        title : String,
        description: String,
        category: String,
        isPublic: Boolean,
        mediaUri: Uri
    ){
        val uid = auth.currentUser?.uid?: return
        viewModelScope.launch {
            _mediaState.value = MediaState.Loading
            try {
                //1. upload to cloudinary and get access url
                val mediaUrl = CloudinaryUploader.uploadImage(
                    context = context,
                    imageUri = mediaUri,
                    onProgress = {progress -> _uploadProgress.value=progress}
                )
                //2. Save media asset to firestore with the correct url
                val mediaItem = MediaItem(
                    title= title,
                    description = description,
                    imageUrl = mediaUrl,
                    ownerId = uid,
                    ownerName = ownerName,
                    category = category,
                    isPublic = isPublic
                )

            }catch (e: Exception){_mediaState.value= MediaState.Error(e.message?: "Upload Fail")
            }
        }
    }
    fun updateMedia(){}
    fun deleteMedia(){}
    fun clearState(){
        _mediaState.value = MediaState.Idle
    }



    // access to public media items in screens using this vm
}