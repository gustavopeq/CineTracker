package gustavo.projects.moviemanager.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import gustavo.projects.moviemanager.NavGraphActivity
import gustavo.projects.moviemanager.database.AppDatabase
import gustavo.projects.moviemanager.database.ToWatchViewModel

abstract class BaseFragment: Fragment() {

    protected val mainActivity: NavGraphActivity
        get() = activity as NavGraphActivity

    protected val appDatabase: AppDatabase
        get() = AppDatabase.getDatabase(requireActivity())

    protected val sharedViewModel: ToWatchViewModel by activityViewModels()
}