package com.kerumitbsl.testtasknatife.ui.fullscreenFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kerumitbsl.testtasknatife.R
import com.kerumitbsl.testtasknatife.base.BaseFragment

class FullscreenFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fullscreen, container, false)
    }
}