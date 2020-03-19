package com.frestoinc.sampleapp_kotlin.api.data.manager

import com.frestoinc.sampleapp_kotlin.api.data.model.Repo
import com.frestoinc.sampleapp_kotlin.api.data.remote.RemoteRepository
import com.frestoinc.sampleapp_kotlin.api.data.room.RoomRepository
import com.frestoinc.sampleapp_kotlin.api.resourcehandler.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 * Created by frestoinc on 29,February,2020 for SampleApp_Kotlin.
 */

class DataManager : KoinComponent {

    private val remoteRepository: RemoteRepository by inject()
    private val roomRepository: RoomRepository by inject()

    suspend fun getRemoteRepository(): State<List<Repo>> =
        withContext(Dispatchers.Main) {
            return@withContext remoteRepository.getRemoteRepository()
        }

    suspend fun getRoomRepo(): State<List<Repo>> =
        withContext(Dispatchers.Main) {
            return@withContext roomRepository.getRoomRepo()
        }

    suspend fun insert(data: List<Repo>): State<Unit> =
        withContext(Dispatchers.Main) {
            return@withContext roomRepository.insert(data)
        }
}