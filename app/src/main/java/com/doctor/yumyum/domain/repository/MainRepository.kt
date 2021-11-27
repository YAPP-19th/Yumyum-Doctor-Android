package com.doctor.yumyum.domain.repository

interface MainRepository {

    fun getMode(): Int?

    fun setMode(mode: Int)
}