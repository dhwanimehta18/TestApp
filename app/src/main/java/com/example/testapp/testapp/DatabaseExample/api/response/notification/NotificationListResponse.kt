package com.example.testapp.testapp.DatabaseExample.api.response.notification

import com.example.testapp.testapp.DatabaseExample.api.response.base.BaseObjectResponse

class NotificationListResponse : BaseObjectResponse<NotificationListResponse.NotificationResponse>() {

    data class NotificationResponse(
            val list: List<NotificationList>,
            val total_records: Int
    ) {

        data class NotificationList(
                val __v: Int,
                val _id: String,
                val created_at: String,
                val message: String,
                val title: String,
                val user_id: String
        )
    }
}
