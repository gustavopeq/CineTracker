package gustavo.projects.moviemanager.compose.features.browse.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import gustavo.projects.moviemanager.compose.features.browse.domain.BrowseInteractor
import gustavo.projects.moviemanager.network.repository.movie.MovieRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InteractorModule {
    @Singleton
    @Provides
    fun provideBrowseInteractor(
        movieRepository: MovieRepository
    ): BrowseInteractor {
        return BrowseInteractor(movieRepository = movieRepository)
    }
}
