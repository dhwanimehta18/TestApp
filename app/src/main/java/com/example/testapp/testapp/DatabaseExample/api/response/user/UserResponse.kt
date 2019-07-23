package com.example.testapp.testapp.DatabaseExample.api.response.user

import com.example.testapp.testapp.DatabaseExample.api.response.base.BaseObjectResponse

class UserResponse : BaseObjectResponse<UserResponse.User>() {
    override fun toString(): String {
        return "LoginSignUpResponse{} " + super.toString()
    }

    data class User(
            val _id: String,
            val about: String,
            val birth_date: String,
            val created_at: String,
            val documents: List<String>,
            val email: String,
            val full_name: String,
            val gender: String,
            val isActive: Boolean,
            val is_favourite: Boolean,
            val is_online: Boolean,
            val last_requested_date: String,
            val mobile_number: String,
            val nick_name: String,
            val profile_picture: String,
            val updated_at: String
}