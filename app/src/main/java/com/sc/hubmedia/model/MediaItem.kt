package com.sc.hubmedia.model

import com.google.firebase.Timestamp

// enum class : static values that should not change
enum class UserRole {STUDENT,TEACHER}
data class UserProfile(
    val uid: String = "",
    val fullname: String = "",
    val email: String = "",
    val role: String="student"

){
    // to map will reference live values from firebase for credential checkup
    fun toMap(): Map<String,Any> =mapOf(
        "fullname" to fullname ,
        "email" to email,
        "role" to role
    )
    fun userRole(): UserRole =
        if (role.equals("teacher",
            ignoreCase = true)) UserRole.TEACHER else
            UserRole.STUDENT
}

//model for the media assets
data class MediaItem(
    val id: String = "",
    val title: String = "",
    val description: String="",
    val imageUrl: String = "",
    val ownerName: String = "",
    val ownerId: String = "",
    val isPublic: Boolean= false,
    val category : String = "",
    val uploadedAt: Timestamp = Timestamp.now(), // captures current time
){
    // firestore needs what we call a non-org constructor which is something to map the values to the firestore collection
    fun toMap(): Map<String,Any> = mapOf(
        "title" to title,
        "description" to description,
        "imageUrl" to imageUrl,
        "ownerName" to ownerName,
        "ownerId" to ownerId,
        "isPublic" to isPublic,
        "category" to category,
        "uploadedAt" to uploadedAt
    )
}

