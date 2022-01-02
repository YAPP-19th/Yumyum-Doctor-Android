package com.doctor.yumyum.data.model

import java.io.Serializable

data class UserInfo(
    val id: Int,
    val nickname: String,
    val grade: String,
    val userGradePoint: Int
) : Serializable
