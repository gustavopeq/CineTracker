package gustavo.projects.moviemanager.database

import gustavo.projects.moviemanager.database.model.ItemEntity

class DatabaseRepository(
    private val appDatabase: AppDatabase
) {

    fun insertItem(itemEntity: ItemEntity) {
        appDatabase.itemEntityDao().insert(itemEntity)
    }

    fun deleteItem(itemEntity: ItemEntity) {
        appDatabase.itemEntityDao().delete(itemEntity)
    }

    suspend fun getAllItems(): List<ItemEntity> {
        return appDatabase.itemEntityDao().getAllItems()
    }
}