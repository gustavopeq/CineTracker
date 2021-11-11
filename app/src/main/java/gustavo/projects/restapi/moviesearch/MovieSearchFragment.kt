package gustavo.projects.restapi.moviesearch

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import gustavo.projects.restapi.R
import gustavo.projects.restapi.databinding.FragmentMovieSearchBinding
import gustavo.projects.restapi.epoxy.MovieSearchEpoxyController
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MovieSearchFragment : Fragment(R.layout.fragment_movie_search) {

    private var _binding: FragmentMovieSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MovieSearchViewModel by lazy {
        ViewModelProvider(this).get(MovieSearchViewModel::class.java)
    }

    private var currentSearchText = ""
    private val handler = Handler(Looper.getMainLooper())
    private val searchRunnable = Runnable {
        viewModel.submitSearchQuery(currentSearchText)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMovieSearchBinding.bind(view)

        val epoxyController = MovieSearchEpoxyController { movieId ->
            TODO()
        }
        binding.epoxyRecyclerView.setControllerAndBuildModels(epoxyController)

        // Handler responsible for giving a brief delay to get what's being
        // searched and send the API request
        binding.searchEditText.doAfterTextChanged {
            currentSearchText = it?.toString() ?: ""

            handler.removeCallbacks(searchRunnable)
            handler.postDelayed(searchRunnable, 500L)
        }

        lifecycleScope.launch {
            viewModel.flow.collectLatest { pagingData ->
                epoxyController.submitData(pagingData)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}