package gustavo.projects.moviemanager.database

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import gustavo.projects.moviemanager.databinding.FragmentMoviesToWatchListBinding
import gustavo.projects.moviemanager.domain.models.Movie
import gustavo.projects.moviemanager.epoxy.ToWatchEpoxyController
import gustavo.projects.moviemanager.network.ApiClient
import gustavo.projects.moviemanager.util.BaseFragment
import javax.inject.Inject

@AndroidEntryPoint
class ToWatchFragment: BaseFragment() {

    private var _binding: FragmentMoviesToWatchListBinding? = null
    private val binding get() = _binding!!

    private val epoxyController = ToWatchEpoxyController(::onMovieSelected)

    @Inject
    lateinit var apiClient: ApiClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoviesToWatchListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.epoxyRecyclerView.setController(epoxyController)

        sharedViewModel.init(appDatabase, apiClient)


        sharedViewModel.watchlistMoviesLiveData.observe(viewLifecycleOwner) { movieList ->
            epoxyController.movieList = movieList as ArrayList<Movie>
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onMovieSelected(movieId: Int) {
        val directions =
            ToWatchFragmentDirections.actionToWatchFragmentToMovieDetailsFragment(movieId)
        findNavController().navigate(directions)
    }
}