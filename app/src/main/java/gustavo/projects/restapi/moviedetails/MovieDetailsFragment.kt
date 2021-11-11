package gustavo.projects.restapi.moviedetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.airbnb.epoxy.EpoxyRecyclerView
import gustavo.projects.restapi.epoxy.MovieDetailsEpoxyController
import gustavo.projects.restapi.R

class MovieDetailsFragment : Fragment() {

    private val viewModel: MovieDetailsViewModel by lazy {
        ViewModelProvider(this).get(MovieDetailsViewModel::class.java)
    }

    private val epoxyController = MovieDetailsEpoxyController()

    private val safeArgs: MovieDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.getMovieDetailsByIdLiveData.observe(viewLifecycleOwner){ movie ->

            epoxyController.movieDetails = movie

            if(movie == null){
                Toast.makeText(requireActivity(), "Unsuccessful network call!",
                    Toast.LENGTH_SHORT)
                return@observe
            }
        }


        viewModel.fetchMovie(safeArgs.movieId)

        val epoxyRecyclerView = view.findViewById<EpoxyRecyclerView>(R.id.epoxyRecyclerView)
        epoxyRecyclerView.setControllerAndBuildModels(epoxyController)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                //finish()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
        return false
    }
}