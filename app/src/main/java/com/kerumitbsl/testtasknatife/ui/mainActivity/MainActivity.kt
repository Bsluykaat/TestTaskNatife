package com.kerumitbsl.testtasknatife.ui.mainActivity

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.SearchView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.kerumitbsl.core.bean.models.GifObject
import com.kerumitbsl.testtasknatife.R
import com.kerumitbsl.testtasknatife.base.BaseActivity
import com.kerumitbsl.testtasknatife.databinding.ActivityMainBinding
import com.kerumitbsl.testtasknatife.other.ActivityCommunicator
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity(), ActivityCommunicator {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel: MainActivityViewModel by viewModel()

    private val navController: NavController by lazy { findNavController(R.id.nav_host_fragment_content_main) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            if (destination.id == R.id.nav_fullscreen_fragment) {
                hideSystemBars()
            } else {
                showSystemBars()
            }
        }
    }

    private fun hideSystemBars() {
        window?.setBackgroundDrawableResource(R.color.black)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, binding.root).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
        supportActionBar?.hide()
    }

    private fun showSystemBars() {
        window?.setBackgroundDrawableResource(R.color.white)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        WindowInsetsControllerCompat(window, binding.root).show(WindowInsetsCompat.Type.systemBars())
        supportActionBar?.show()
    }

    override fun setContent(list: List<GifObject>) {
        viewModel.content.clear()
        viewModel.content.addAll(list)
    }

    override fun getContent(): List<GifObject> = viewModel.content

    override fun getAppBarSearchView(): SearchView = binding.appBarSearchView

    override fun getMutableActionBar(): ActionBar? = supportActionBar
}