package gustavo.projects.moviemanager.compose.features.browse.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import gustavo.projects.moviemanager.R
import gustavo.projects.moviemanager.compose.theme.Shapes
import gustavo.projects.moviemanager.compose.ui.NetworkImage
import gustavo.projects.moviemanager.compose.util.UiConstants.BROWSE_CARD_DEFAULT_ELEVATION
import gustavo.projects.moviemanager.compose.util.UiConstants.DEFAULT_MARGIN
import gustavo.projects.moviemanager.compose.util.UiConstants.DEFAULT_PADDING
import gustavo.projects.moviemanager.domain.models.Movie
import gustavo.projects.moviemanager.util.Constants.BASE_IMAGE_URL
import gustavo.projects.moviemanager.util.formatRating

@Composable
fun Browse() {
    Browse(
        viewModel = hiltViewModel()
    )
}

@Composable
internal fun Browse(
    viewModel: BrowseViewModel
) {
    val browseState = viewModel.browseState
    val listOfMovies by browseState.listOfMovies
    Box {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(horizontal = DEFAULT_MARGIN.dp)
        ) {
            itemsIndexed(listOfMovies) { index: Int, item: Movie ->
                BrowseCard(
                    imageUrl = item.poster_path,
                    title = item.title,
                    rating = item.vote_average
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BrowseCard(
    modifier: Modifier = Modifier,
    imageUrl: String?,
    title: String?,
    rating: Double?
) {
    val fullImageUrl = BASE_IMAGE_URL + imageUrl

    Card(
        modifier = modifier
            .padding(horizontal = 4.dp, vertical = 8.dp),
        onClick = { /*TODO*/ },
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFFFFF)
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = BROWSE_CARD_DEFAULT_ELEVATION.dp
        )
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(horizontal = DEFAULT_PADDING.dp)
        ) {
            Spacer(modifier = Modifier.height(4.dp))
            NetworkImage(
                imageUrl = fullImageUrl,
                modifier = Modifier.fillMaxWidth().clip(Shapes.small)
            )
            Spacer(modifier = Modifier.width(DEFAULT_PADDING.dp))
            Text(
                text = title.orEmpty(),
                color = Color.Black,
                style = MaterialTheme.typography.titleMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.offset(x = (-0.5).dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_star),
                    contentDescription = null
                )
                Text(
                    text = rating.formatRating(),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}
