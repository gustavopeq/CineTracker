package gustavo.projects.moviemanager

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import gustavo.projects.moviemanager.database.AppDatabase
import gustavo.projects.moviemanager.database.ToWatchViewModel

class NavGraphActivity: AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav_graph)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)

        // Action bar to create back arrow and page title
        appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf(R.id.popularMoviesFragment, R.id.searchMoviesFragment, R.id.toWatchFragment),
            drawerLayout = drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)

        findViewById<NavigationView>(R.id.nav_view).setupWithNavController(navController)

        val viewModel: ToWatchViewModel by viewModels()
        viewModel.init(AppDatabase.getDatabase(this))
    }

    //Back arrow to return stack
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}