package com.doctor.yumyum.data.model

import java.io.Serializable

data class signUpModel(
    val accessToken: String,
    val nickname: String,
    val oauthType: String
) : Serializable