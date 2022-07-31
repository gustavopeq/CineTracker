package gustavo.projects.moviemanager.people


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import gustavo.projects.moviemanager.config.AppConfiguration
import gustavo.projects.moviemanager.databinding.FragmentPersonDetailsBinding
import gustavo.projects.moviemanager.epoxy.PersonDetailsEpoxyController
import gustavo.projects.moviemanager.util.BaseFragment
import gustavo.projects.moviemanager.util.LanguageSupport


class PersonDetailsFragment : BaseFragment() {

    private var _binding: FragmentPersonDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PersonDetailsViewModel by viewModels()

    lateinit var epoxyController: PersonDetailsEpoxyController

    private val safeArgs: PersonDetailsFragmentArgs by navArgs()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPersonDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPersonDetailsBinding.bind(view)
        epoxyController = PersonDetailsEpoxyController(::onMovieSelected, requireContext())

        viewModel.getPersonDetailsByIdLiveData.observe(viewLifecycleOwner){ person ->
            epoxyController.personDetails = person

            if(person == null){
                Toast.makeText(requireActivity(), "Unsuccessful network call!",
                    Toast.LENGTH_SHORT).show()
                return@observe
            }
        }

        viewModel.fetchPerson(safeArgs.personId)

        val epoxyRecyclerView = binding.epoxyRecyclerView
        epoxyRecyclerView.setControllerAndBuildModels(epoxyController)

    }

    fun onMovieSelected(movieId: Int) {
        val directions = PersonDetailsFragmentDirections.actionPersonDetailsFragmentToMovieDetailsFragment(movieId)

        findNavController().navigate(directions)
    }
}