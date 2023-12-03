package gustavo.projects.moviemanager.network.repository.show

import gustavo.projects.moviemanager.network.models.ApiError
import gustavo.projects.moviemanager.network.response.content.ContentListPageResponse
import gustavo.projects.moviemanager.network.response.content.ShowApiResponse
import gustavo.projects.moviemanager.network.services.show.ShowService
import gustavo.projects.moviemanager.network.util.Either
import gustavo.projects.moviemanager.network.util.asFlow
import gustavo.projects.moviemanager.network.util.toApiResult
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class ShowRepositoryImpl @Inject constructor(
    private val showService: ShowService
) : ShowRepository {
    override suspend fun getShowList(
        contentListType: String,
        pageIndex: Int
    ): Flow<Either<ContentListPageResponse<ShowApiResponse>, ApiError>> {
        return toApiResult {
            showService.getShowList(
                contentListType = contentListType,
                pageIndex = pageIndex,
                language = "en-US"
            )
        }.asFlow()
    }
}
