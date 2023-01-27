package com.kerumitbsl.testtasknatife.ui.fullscreenFragment.item

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kerumitbsl.core.bean.models.GifObject
import com.kerumitbsl.testtasknatife.R
import com.kerumitbsl.testtasknatife.base.BaseFragment
import com.kerumitbsl.testtasknatife.databinding.FragmentPageItemBinding
import com.kerumitbsl.testtasknatife.extensions.loadGifFromGIPHY


class PageItemFragment : BaseFragment() {

    private val binder: FragmentPageItemBinding by lazy { FragmentPageItemBinding.inflate(layoutInflater) }

    var gifObject: GifObject? = null
    private var id: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        id = savedInstanceState?.getString("id").toString()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        loadGifFromGIPHY(gifObject?.id ?: id, binder.gifRepresentationImageView)

        return binder.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("id", gifObject?.id)
        super.onSaveInstanceState(outState)
    }
}