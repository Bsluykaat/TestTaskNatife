package com.kerumitbsl.testtasknatife.ui.fullscreenFragment.item

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kerumitbsl.core.bean.models.GifObject
import com.kerumitbsl.testtasknatife.R
import com.kerumitbsl.testtasknatife.base.BaseFragment
import com.kerumitbsl.testtasknatife.databinding.FragmentPageItemBinding
import com.kerumitbsl.testtasknatife.extensions.FORBIDDEN_IDS_LIST_KEY
import com.kerumitbsl.testtasknatife.extensions.loadGifFromGIPHY
import com.orhanobut.hawk.Hawk


class PageItemFragment : BaseFragment() {

    private val binder: FragmentPageItemBinding by lazy { FragmentPageItemBinding.inflate(layoutInflater) }

    var gifObject: GifObject? = null
    private var id: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        id = savedInstanceState?.getString("id").toString()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            gifObject = savedInstanceState?.getParcelable("gifObject", GifObject::class.java)
        }
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        loadGifFromGIPHY(gifObject?.id ?: id, binder.gifRepresentationImageView)
        setupListeners()

        return binder.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("id", gifObject?.id)
        outState.putParcelable("gifObject", gifObject)
        super.onSaveInstanceState(outState)
    }

    private fun setupListeners() {
        binder.removeButton.setOnClickListener {
            Hawk.put(FORBIDDEN_IDS_LIST_KEY, Hawk.get<MutableList<GifObject>>(FORBIDDEN_IDS_LIST_KEY).apply { add(gifObject!!) })
        }
    }
}