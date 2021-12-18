package com.doctor.yumyum.data.model

import java.io.Serializable

data class SignUpModel(
    val accessToken: String,
    val nickname: String,
    val oauthType: String
) : Serializable