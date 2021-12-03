package gustavo.projects.moviemanager.database

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import gustavo.projects.moviemanager.database.model.ItemEntity
import gustavo.projects.moviemanager.databinding.FragmentMoviesToWatchListBinding
import gustavo.projects.moviemanager.epoxy.ToWatchEpoxyController
import gustavo.projects.moviemanager.util.BaseFragment

class ToWatchFragment: BaseFragment() {

    private var _binding: FragmentMoviesToWatchListBinding? = null
    private val binding get() = _binding!!

    private val epoxyController = ToWatchEpoxyController(::onMovieSelected)

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

        sharedViewModel.init(appDatabase)


        sharedViewModel.itemEntitiesLiveData.observe(viewLifecycleOwner) { itemEntityList ->
            epoxyController.itemEntityList = itemEntityList as ArrayList<ItemEntity>
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