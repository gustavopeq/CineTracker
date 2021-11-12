package gustavo.projects.moviemanager.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import gustavo.projects.moviemanager.database.dao.ItemEntityDao
import gustavo.projects.moviemanager.database.model.ItemEntity

@Database(
    entities = arrayOf(ItemEntity::class),
    version = 1
)

abstract class AppDatabase: RoomDatabase() {

    companion object {
        private var appDatabase: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            if (appDatabase != null) {
                return appDatabase!!
            }

            appDatabase = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "movie_manager_database").fallbackToDestructiveMigration().build()
            return appDatabase!!
        }
    }

    abstract fun itemEntityDao(): ItemEntityDao
}