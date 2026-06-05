package com.sc.hubmedia.model

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
        "fullName" to fullname ,
        "email" to email,
        "role" to role
    )
    fun userRole(): UserRole =
        if (role == "teacher") UserRole.TEACHER else
            UserRole.STUDENT
}

