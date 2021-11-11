package gustavo.projects.moviemanager.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item_entity")
data class ItemEntity(
    @PrimaryKey val id: Int
)
