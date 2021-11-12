package gustavo.projects.moviemanager.database.dao

import androidx.room.*
import gustavo.projects.moviemanager.database.model.ItemEntity

@Dao
interface ItemEntityDao {
    @Query("SELECT * FROM item_entity")
    suspend fun getAllItems(): List<ItemEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(itemEntity: ItemEntity)

    @Delete
    suspend fun delete(itemEntity: ItemEntity)
}