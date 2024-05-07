package com.projects.moviemanager.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.projects.moviemanager.database.model.ListEntity

@Dao
interface ListEntityDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertList(listEntity: ListEntity)
}
