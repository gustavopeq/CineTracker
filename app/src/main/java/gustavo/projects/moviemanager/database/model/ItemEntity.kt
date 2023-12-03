package gustavo.projects.moviemanager.database.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import gustavo.projects.moviemanager.domain.models.content.Movie

@Entity(tableName = "item_entity")
data class ItemEntity(
    @PrimaryKey val dbId: Int,
    @Embedded val movie: Movie
)
