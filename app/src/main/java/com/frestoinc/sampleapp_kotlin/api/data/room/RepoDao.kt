package com.frestoinc.sampleapp_kotlin.api.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.frestoinc.sampleapp_kotlin.api.data.model.Repo

/**
 * Created by frestoinc on 28,February,2020 for SampleApp_Kotlin.
 */
@Dao
interface RepoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(list: List<Repo>)

    @Query("DELETE FROM repo")
    suspend fun deleteAll()

    @Query("SELECT * from repo ORDER BY author ASC")
    suspend fun getAll(): List<Repo>

}