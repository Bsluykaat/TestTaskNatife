package com.kerumitbsl.testtasknatife.ui.fullscreenFragment

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kerumitbsl.core.bean.models.GifObject
import com.kerumitbsl.testtasknatife.ui.fullscreenFragment.item.PageItemFragment

class FullscreenPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private val contentList = mutableListOf<GifObject>()

    @SuppressLint("NotifyDataSetChanged")
    fun setContent(list: List<GifObject>) {
        contentList.clear()
        contentList.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = contentList.size

    override fun createFragment(position: Int): Fragment {
        return PageItemFragment().apply { gifObject = contentList[position] }
    }
}