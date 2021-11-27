package com.doctor.yumyum.data.repository

import com.doctor.yumyum.data.local.LocalDataSourceImpl
import com.doctor.yumyum.domain.repository.MainRepository

class MainRepositoryImpl : MainRepository {

    private val localDataSource: LocalDataSourceImpl
        get() = LocalDataSourceImpl()

    override fun getMode(): Int? = localDataSource.getMode()

    override fun setMode(mode: Int) = localDataSource.setMode(mode)
}