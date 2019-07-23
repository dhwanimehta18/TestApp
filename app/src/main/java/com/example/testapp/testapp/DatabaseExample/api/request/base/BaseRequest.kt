package com.example.testapp.testapp.DatabaseExample.api.request.base

object BaseRequest {
    data class LoginRequest(
            val device_type: Int,
            val email: String,
            val password: String,
            val push_token: String
    )
}