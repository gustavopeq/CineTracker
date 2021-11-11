package gustavo.projects.moviemanager.database

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import gustavo.projects.moviemanager.databinding.FragmentMoviesToWatchListBinding
import gustavo.projects.moviemanager.util.BaseFragment

class ToWatchFragment: BaseFragment() {

    private val viewModel: ToWatchViewModel by lazy {
        ViewModelProvider(this).get(ToWatchViewModel::class.java)
    }

    private var _binding: FragmentMoviesToWatchListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoviesToWatchListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.itemEntitiesLiveData.observe(viewLifecycleOwner) { itemEntityList ->

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}