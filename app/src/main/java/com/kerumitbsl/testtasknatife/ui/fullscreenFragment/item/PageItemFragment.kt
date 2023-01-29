package com.kerumitbsl.testtasknatife.ui.fullscreenFragment.item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.kerumitbsl.core.bean.models.GifObject
import com.kerumitbsl.testtasknatife.R
import com.kerumitbsl.testtasknatife.base.BaseFragment
import com.kerumitbsl.testtasknatife.databinding.FragmentPageItemBinding
import com.kerumitbsl.testtasknatife.extensions.FORBIDDEN_IDS_LIST_KEY
import com.kerumitbsl.testtasknatife.extensions.loadGifFromGIPHY
import com.orhanobut.hawk.Hawk
import org.koin.androidx.viewmodel.ext.android.viewModel


class PageItemFragment : BaseFragment() {

    private val binder: FragmentPageItemBinding by lazy {
        FragmentPageItemBinding.inflate(
            layoutInflater
        )
    }
    private val viewModel: PageItemViewModel by viewModel()

    var gifObject: GifObject? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        if (viewModel.gifObject == null) {
            viewModel.gifObject = gifObject
        }

        loadGifFromGIPHY(
            viewModel.gifObject?.id ?: "",
            binder.gifRepresentationImageView
        )
        setupListeners()


        return binder.root
    }

    private fun setupListeners() {
        binder.removeButton.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setMessage(R.string.gif_deletion_question)
                .setPositiveButton(R.string.okay) { dialog, id ->
                    Hawk.put(
                        FORBIDDEN_IDS_LIST_KEY,
                        Hawk.get<MutableList<GifObject>>(FORBIDDEN_IDS_LIST_KEY)
                            .apply { add(gifObject!!) })
                    binder.deletedStateImageView.visibility = View.VISIBLE
                    binder.gifRepresentationImageView.visibility = View.GONE
                }
                .setNegativeButton(R.string.cancel) { dialog, id -> dialog.dismiss() }
                .create()
            builder.show()
        }
    }
}