package gustavo.projects.restapi.popularmovies

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.airbnb.epoxy.EpoxyRecyclerView
import gustavo.projects.restapi.Constants
import gustavo.projects.restapi.MovieDetails
import gustavo.projects.restapi.R
import gustavo.projects.restapi.epoxy.PopularMoviesPagingEpoxyController

class PopularMoviesActivity: AppCompatActivity() {

    private val epoxyController = PopularMoviesPagingEpoxyController(::onMovieSelected)

    private val viewModel: PopularMoviesViewModel by lazy {
        ViewModelProvider(this).get(PopularMoviesViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popular_movies_list)

        viewModel.moviesPagedListLiveData.observe(this) { pagedList ->
            epoxyController.submitList(pagedList)
        }

        findViewById<EpoxyRecyclerView>(R.id.epoxyRecyclerView).setController(epoxyController)
    }

    private fun onMovieSelected(movieId: Int) {
        val intent = Intent(this, MovieDetails::class.java)
        intent.putExtra(Constants.INTENT_MOVIE_ID, movieId)
        startActivity(intent)
    }
}