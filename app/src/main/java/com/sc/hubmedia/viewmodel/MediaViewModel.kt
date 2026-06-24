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

    fun loadPublicMedia(){
        viewModelScope.launch {
            try {
                // first we retrieve firestore collection
                // filter the data via the isPublic field = true
                // order our data by the latest i.e. uploadedAt field
                val snapshot = db.collection("media")
                    .whereEqualTo("isPublic",true)
                    .orderBy("uploadedAt",Query.Direction.DESCENDING).get().await()
                //now populate the viewmodel reference for public media
                //via capturing the snapshot and mapping each record in the collection to our MediaItem model
                _publicMedia.value = snapshot.documents.map { doc ->
                    doc.toObject(MediaItem::class.java)!!.copy(id = doc.id)
                }

            }catch (e: Exception){
                _mediaState.value = MediaState.Error(e.message ?: "Failed to load media items.")
            }
        }
    }
    // load users private media items
    fun loadMyMedia(){
        val uid = auth.currentUser?.uid ?: return
        viewModelScope.launch {
            try {
                val snapshot = db.collection("media")
                    .whereEqualTo("ownerId",uid)
                    .orderBy("uploadedAt",Query.Direction.DESCENDING).get().await()
                _myMedia.value = snapshot.documents.map{doc ->
                    doc.toObject(MediaItem::class.java)!!.copy(id=doc.id)
                }

            }catch (e: Exception){
                _mediaState.value = MediaState.Error(e.message ?: "Failed to load media items.")

            }
        }
    }
    // load all media for teachers access / view
    fun loadAllMedia(){
        viewModelScope.launch {
            try {
                val snapshot = db.collection("media")

                    .orderBy("uploadedAt",Query.Direction.DESCENDING).get().await()
                _allMedia.value = snapshot.documents.map{doc ->
                    doc.toObject(MediaItem::class.java)!!.copy(id=doc.id)
                }

            }catch (e: Exception){
                _mediaState.value = MediaState.Error(e.message ?: "Failed to load media items.")


            }
        }
    }
    fun uploadMedia(
        context: Context,
        title : String,
        description: String,
        category: String,
        isPublic: Boolean,
        ownerName: String,
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
                // push our item to firebase for storage
                db.collection("media").add(mediaItem.toMap()).await()
                _uploadProgress.value=0f
                // Change the progress value
                _mediaState.value= MediaState.Success


            }catch (e: Exception){_mediaState.value= MediaState.Error(e.message?: "Upload Fail")
            }
        }
    }
    // update existing media
    fun updateMedia(
        mediaId : String,
        title: String,
        description: String,
        isPublic: Boolean
    ){
        viewModelScope.launch { _mediaState.value = MediaState.Loading
            try {
                db.collection("media").document(mediaId).update(mapOf(
                    "title" to title,
                    "description" to description,
                    "isPublic" to isPublic
                )).await()
                _mediaState.value = MediaState.Success

            }catch (e: Exception){
                _mediaState.value = MediaState.Error(e.message ?: "Update Failed")
            }

        }
    }
    // delete existing media
    fun deleteMedia(item: MediaItem){
        viewModelScope.launch {
            try {
                db.collection("media").document(item.id).delete().await()
                _mediaState.value = MediaState.Success
            }catch (e: Exception){
                _mediaState.value = MediaState.Error(e.message ?: "Delete Failed!!")
            }
        }
    }
    fun clearState(){
        _mediaState.value = MediaState.Idle
    }




}