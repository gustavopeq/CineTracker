package gustavo.projects.moviemanager.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import gustavo.projects.moviemanager.network.repository.movie.MovieRepository
import gustavo.projects.moviemanager.network.repository.movie.MovieRepositoryImpl
import gustavo.projects.moviemanager.network.services.movie.MovieService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMovieRepository(
        movieService: MovieService
    ): MovieRepository {
        return MovieRepositoryImpl(movieService)
    }
}
