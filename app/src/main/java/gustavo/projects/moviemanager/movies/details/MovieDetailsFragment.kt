package gustavo.projects.moviemanager.movies.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import gustavo.projects.moviemanager.R
import gustavo.projects.moviemanager.database.model.ItemEntity
import gustavo.projects.moviemanager.databinding.FragmentMovieDetailsBinding
import gustavo.projects.moviemanager.epoxy.MovieDetailsEpoxyController
import gustavo.projects.moviemanager.util.BaseFragment
import gustavo.projects.moviemanager.util.Constants


class MovieDetailsFragment : BaseFragment() {

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MovieDetailsViewModel by viewModels()

    private val epoxyController = MovieDetailsEpoxyController(::onVideoSelected)

    private val safeArgs: MovieDetailsFragmentArgs by navArgs()

    private var isInWatchLaterList = false

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMovieDetailsBinding.bind(view)

        viewModel.getMovieDetailsByIdLiveData.observe(viewLifecycleOwner){ movie ->

            epoxyController.movieDetails = movie

            if(movie == null){
                Toast.makeText(requireActivity(), "Unsuccessful network call!",
                        Toast.LENGTH_SHORT)
                return@observe
            }

            sharedViewModel.verifyIfInToWatchList(movie.id!!)
        }

        viewModel.fetchMovie(safeArgs.movieId)

        val epoxyRecyclerView = binding.epoxyRecyclerView
        epoxyRecyclerView.setControllerAndBuildModels(epoxyController)



        binding.addToWatchListBtn.setOnClickListener {
            when(isInWatchLaterList) {
                true -> {
                    sharedViewModel.deleteItem(
                            ItemEntity(viewModel.movieDisplayed.id!!, viewModel.movieDisplayed))
                    binding.addToWatchListBtn.setIconResource(R.drawable.ic_baseline_bookmark_add)
                    isInWatchLaterList = false
                }
                false -> {
                    sharedViewModel.insertItem(
                            ItemEntity(viewModel.movieDisplayed.id!!, viewModel.movieDisplayed))
                    binding.addToWatchListBtn.setIconResource(R.drawable.ic_baseline_bookmark_remove)
                    isInWatchLaterList = true
                }
            }

        }

        sharedViewModel.movieInToWatchList.observe(viewLifecycleOwner) {
            isInWatchLaterList = it

            when(isInWatchLaterList) {
                true -> {
                    binding.addToWatchListBtn.setIconResource(R.drawable.ic_baseline_bookmark_remove)
                    isInWatchLaterList = true
                }
                false -> {
                    binding.addToWatchListBtn.setIconResource(R.drawable.ic_baseline_bookmark_add)
                    isInWatchLaterList = false
                }
            }
        }
    }

    private fun onVideoSelected(videoKey: String) {
        val fullUrl = Constants.BASE_URL_YOUTUBE_VIDEO + videoKey
        val uri: Uri = Uri.parse(fullUrl)

        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }
}