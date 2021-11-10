package gustavo.projects.restapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.airbnb.epoxy.EpoxyRecyclerView
import gustavo.projects.restapi.epoxy.MovieDetailsEpoxyController


class MovieDetails : AppCompatActivity() {

    private val viewModel: SharedViewModel by lazy {
        ViewModelProvider(this).get(SharedViewModel::class.java)
    }

    private val epoxyController = MovieDetailsEpoxyController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.getMovieByIdLiveData.observe(this){ response ->

            epoxyController.movieResponse = response

            if(response == null){
                Toast.makeText(this@MovieDetails, "Unsuccessful network call!",
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