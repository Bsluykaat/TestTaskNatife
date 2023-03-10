package com.kerumitbsl.testtasknatife.ui.fullscreenFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kerumitbsl.core.bean.models.GifObject
import com.kerumitbsl.testtasknatife.adapters.FullscreenPagerAdapter
import com.kerumitbsl.testtasknatife.base.BaseFragment
import com.kerumitbsl.testtasknatife.databinding.FragmentFullscreenBinding
import com.kerumitbsl.testtasknatife.other.ActivityCommunicator

class FullscreenFragment : BaseFragment() {

    private val binder: FragmentFullscreenBinding by lazy { FragmentFullscreenBinding.inflate(layoutInflater) }
    private val args: FullscreenFragmentArgs by navArgs()

    private val activityCommunicator: ActivityCommunicator by lazy { activity as ActivityCommunicator }

    private val adapter: FullscreenPagerAdapter by lazy { FullscreenPagerAdapter(childFragmentManager, lifecycle) }

    private val onDeleteAction: (GifObject) -> Unit = {

        activityCommunicator.setContent(activityCommunicator.getContent().toMutableList().apply { remove(it) })
        adapter.setContent(activityCommunicator.getContent())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        setupListeners()

        adapter.setContent(activityCommunicator.getContent())
        binder.fullscreenViewPager.adapter = adapter
        binder.fullscreenViewPager.currentItem = activityCommunicator.getContent().indexOf(args.gifObject)

        return binder.root
    }

    private fun setupListeners() {
        binder.backArrowButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    //old way to control system bars

    /*private fun hideSystemBars() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            activity?.window?.insetsController?.hide(WindowInsets.Type.systemBars())
            activity?.window?.insetsController?.systemBarsBehavior =
                WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        } else {
            val windowInsetsController =
                WindowCompat.getInsetsController(activity?.window!!, activity?.window?.decorView!!)
            // Configure the behavior of the hidden system bars
            windowInsetsController.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            // Hide both the status bar and the navigation bar
            windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
        }
        activityCommunicator.getMutableActionBar()?.hide()
        activity?.window?.setBackgroundDrawableResource(R.color.black)
    }

    private fun showSystemBars() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                activity?.window?.insetsController?.systemBarsBehavior =
                    WindowInsetsController.BEHAVIOR_DEFAULT
            }
            activity?.window?.insetsController?.show(WindowInsets.Type.systemBars())
        } else {
            val windowInsetsController =
                WindowCompat.getInsetsController(activity?.window!!, activity?.window?.decorView!!)
            // Show both the status bar and the navigation bar
            windowInsetsController.show(WindowInsetsCompat.Type.systemBars())
        }
        activityCommunicator.getMutableActionBar()?.show()
        activity?.window?.setBackgroundDrawableResource(R.color.white)
    }*/
}