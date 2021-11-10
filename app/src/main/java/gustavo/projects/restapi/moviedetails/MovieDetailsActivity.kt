package gustavo.projects.restapi.moviedetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.airbnb.epoxy.EpoxyRecyclerView
import gustavo.projects.restapi.Constants
import gustavo.projects.restapi.R
import gustavo.projects.restapi.epoxy.MovieDetailsEpoxyController


class MovieDetailsActivity : AppCompatActivity() {

    private val viewModel: MovieDetailsViewModel by lazy {
        ViewModelProvider(this).get(MovieDetailsViewModel::class.java)
    }

    private val epoxyController = MovieDetailsEpoxyController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.getMovieByIdLiveData.observe(this){ movie ->

            epoxyController.movie = movie

            if(movie == null){
                Toast.makeText(this@MovieDetailsActivity, "Unsuccessful network call!",
                        Toast.LENGTH_SHORT)
                return@observe
            }
        }


        viewModel.refreshMovie(intent.getIntExtra(Constants.INTENT_MOVIE_ID, 1))

        val epoxyRecyclerView = findViewById<EpoxyRecyclerView>(R.id.epoxyRecyclerView)
        epoxyRecyclerView.setControllerAndBuildModels(epoxyController)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
        return false
    }
}