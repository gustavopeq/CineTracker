package com.projects.moviemanager.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.projects.moviemanager.database.model.ListEntity

@Dao
interface ListEntityDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertList(listEntity: ListEntity)

    @Query("SELECT * FROM list_entity")
    fun getAllLists(): List<ListEntity>
}
