package com.doctor.yumyum.data.model

import java.io.Serializable

data class WriteRecipe(
    val categoryName: String,
    val flavors: List<String>,
    val foodStatus: String,
    val price: Int,
    val reviewMsg: String,
    val tags: List<TagItem>,
    val title: String
): Serializable
