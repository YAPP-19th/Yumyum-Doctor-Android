package com.doctor.yumyum.data.model

import java.io.Serializable

data class SignInModel(
    val oauthType: String,
    val accessToken: String
) : Serializable
