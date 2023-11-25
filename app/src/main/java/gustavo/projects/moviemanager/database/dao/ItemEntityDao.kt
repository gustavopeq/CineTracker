package gustavo.projects.moviemanager.database.dao

import androidx.room.*
import gustavo.projects.moviemanager.database.model.ItemEntity

@Dao
interface ItemEntityDao {
    @Query("SELECT * FROM item_entity")
    fun getAllItems(): List<ItemEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(itemEntity: ItemEntity)

    @Delete()
    fun delete(itemEntity: ItemEntity)

    @Query("SELECT * FROM item_entity WHERE dbId=:movieId")
    fun searchItem(movieId: Int): ItemEntity?
}