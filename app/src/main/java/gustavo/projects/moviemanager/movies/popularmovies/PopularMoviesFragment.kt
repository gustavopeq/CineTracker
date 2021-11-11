package gustavo.projects.moviemanager.movies.popularmovies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.airbnb.epoxy.EpoxyRecyclerView
import gustavo.projects.moviemanager.R
import gustavo.projects.moviemanager.epoxy.PopularMoviesPagingEpoxyController

class PopularMoviesFragment : Fragment() {

    private val epoxyController = PopularMoviesPagingEpoxyController(::onMovieSelected)

    private val viewModel: PopularMoviesViewModel by lazy {
        ViewModelProvider(this).get(PopularMoviesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_popular_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.moviesPagedListLiveData.observe(viewLifecycleOwner) { pagedList ->
            epoxyController.submitList(pagedList)
        }

        view.findViewById<EpoxyRecyclerView>(R.id.epoxyRecyclerView).setController(epoxyController)
    }


    private fun onMovieSelected(movieId: Int) {
        val directions =
            PopularMoviesFragmentDirections.actionPopularMoviesFragmentToMovieDetailsFragment(
                movieId
            )
        findNavController().navigate(directions)
    }


}