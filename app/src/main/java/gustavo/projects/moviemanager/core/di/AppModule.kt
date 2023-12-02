package gustavo.projects.moviemanager.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import gustavo.projects.moviemanager.compose.common.MainViewModel
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun providesMainViewModel(): MainViewModel {
        return MainViewModel()
    }
}
