package gustavo.projects.moviemanager.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import gustavo.projects.moviemanager.database.model.ItemEntity

@Dao
interface ItemEntityDao {
    @Query("SELECT * FROM item_entity")
    suspend fun getAllItems(): List<ItemEntity>

    @Insert
    fun insert(itemEntity: ItemEntity)

    @Delete
    fun delete(itemEntity: ItemEntity)
}