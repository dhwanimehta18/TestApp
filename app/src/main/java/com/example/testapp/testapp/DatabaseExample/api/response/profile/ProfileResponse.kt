package com.example.testapp.testapp.DatabaseExample.api.response.profile

import com.example.testapp.testapp.DatabaseExample.api.response.base.BaseObjectResponse

class ProfileResponse : BaseObjectResponse<ProfileResponse.ProfileAlbumResponse>() {


    data class ProfileAlbumResponse(
            val list: List<ProfileAlbumList>,
            val total_records: Int
    ) {
        data class ProfileAlbumList(
                val _id: String,
                val image_url: String
        )
    }
}
