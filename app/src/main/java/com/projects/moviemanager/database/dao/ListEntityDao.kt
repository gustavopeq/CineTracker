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

    @Query("DELETE FROM list_entity WHERE listId = :listId")
    fun deleteList(listId: Int)

    @Query("SELECT * FROM list_entity")
    fun getAllLists(): List<ListEntity>

    @Query("SELECT COUNT(*) FROM list_entity WHERE listName = :listName COLLATE NOCASE")
    fun getListCountByName(listName: String): Int
}
