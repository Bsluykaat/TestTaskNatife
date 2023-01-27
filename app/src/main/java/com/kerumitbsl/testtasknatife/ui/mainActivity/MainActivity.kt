package com.kerumitbsl.testtasknatife.ui.mainActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.kerumitbsl.core.bean.models.GifObject
import com.kerumitbsl.testtasknatife.R
import com.kerumitbsl.testtasknatife.base.BaseActivity
import com.kerumitbsl.testtasknatife.databinding.ActivityMainBinding
import com.kerumitbsl.testtasknatife.other.ActivityCommunicator
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity(), ActivityCommunicator {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel: MainActivityViewModel by viewModel()

    private var navController: NavController? = null

    private var appBarConfiguration: AppBarConfiguration? = null

    private val content = mutableListOf<GifObject>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        navController = findNavController(R.id.nav_host_fragment_content_main)

        /*appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_balance,
                R.id.nav_incoming_actions,
                R.id.nav_i_will_do,
                R.id.nav_i_will_watch,
                R.id.nav_messages,
                R.id.nav_my_goals,
                R.id.nav_my_competition,
                R.id.nav_settings,
                R.id.bottom_nav_lets_do_it,
                R.id.bottom_nav_live_now,
                R.id.bottom_nav_add_task,
                R.id.bottom_nav_schedule,
                R.id.ownCreatedTasks,
                R.id.nav_users
            ), binding.drawerLayout
        )
        setupActionBarWithNavController(navController!!, appBarConfiguration!!)*/
    }

    override fun setContent(list: List<GifObject>) {
        content.clear()
        content.addAll(list)
    }

    override fun getContent(): List<GifObject> = content

    override fun getAppBarSearchView(): SearchView = binding.appBarSearchView
}