package com.kerumitbsl.testtasknatife.other

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class ScrolledHelper(
    private var isLastPage: Boolean,
    private val callback: OnScrollCallback
) : RecyclerView.OnScrollListener() {

    private var isLoadingMoreSeries: Boolean = false

    fun loadMore(loadMore: Boolean) {
        isLoadingMoreSeries = loadMore
    }

    fun setLastPage(lastPage: Boolean) {
        isLastPage = lastPage
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val layoutManager: LinearLayoutManager = Objects
            .requireNonNull<RecyclerView.LayoutManager>(recyclerView.layoutManager) as LinearLayoutManager

        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItems = layoutManager.findFirstVisibleItemPosition()

        if (!isLoadingMoreSeries) {
            if (visibleItemCount + firstVisibleItems >= totalItemCount && !isLastPage) {
                isLoadingMoreSeries = true
                callback.onScrolledToEnd()
            }
        }
    }

    interface OnScrollCallback {

        fun onScrolledToEnd()
    }
}
