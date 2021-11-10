package gustavo.projects.restapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.airbnb.epoxy.EpoxyRecyclerView
import gustavo.projects.restapi.epoxy.MovieDetailsEpoxyController


class MainActivity : AppCompatActivity() {

    private val viewModel: SharedViewModel by lazy {
        ViewModelProvider(this).get(SharedViewModel::class.java)
    }

    private val epoxyController = MovieDetailsEpoxyController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        viewModel.getMovieByIdLiveData.observe(this){ response ->

            epoxyController.movieResponse = response

            if(response == null){
                Toast.makeText(this@MainActivity, "Unsuccessful network call!", Toast.LENGTH_SHORT)
                return@observe
            }
        }


        viewModel.refreshMovie(155)

        val epoxyRecyclerView = findViewById<EpoxyRecyclerView>(R.id.epoxyRecyclerView)
        epoxyRecyclerView.setControllerAndBuildModels(epoxyController)
    }
}