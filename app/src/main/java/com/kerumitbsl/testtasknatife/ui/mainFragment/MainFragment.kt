package com.kerumitbsl.testtasknatife.ui.mainFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.lifecycle.Observer
import com.kerumitbsl.core.bean.models.GifObject
import com.kerumitbsl.core.bean.models.MetaObject
import com.kerumitbsl.core.bean.models.PaginationObject
import com.kerumitbsl.core.bean.response.GetGifsResponse
import com.kerumitbsl.core.bean.response.TestTaskResponse
import com.kerumitbsl.core.extensions.LIMIT_ON_PAGE
import com.kerumitbsl.testtasknatife.adapters.MainAdapter
import com.kerumitbsl.testtasknatife.base.BaseFragment
import com.kerumitbsl.testtasknatife.databinding.FragmentMainBinding
import com.kerumitbsl.testtasknatife.extensions.CACHED_IDS_LIST_KEY
import com.kerumitbsl.testtasknatife.extensions.GIFS_REPRESENTATION_COLUMNS
import com.kerumitbsl.testtasknatife.extensions.isNetworkConnected
import com.kerumitbsl.testtasknatife.other.ActivityCommunicator
import com.kerumitbsl.testtasknatife.other.ScrolledHelper
import com.orhanobut.hawk.Hawk
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class MainFragment : BaseFragment() {

    private val viewModel: MainViewModel by viewModel()
    private val binder: FragmentMainBinding by lazy { FragmentMainBinding.inflate(layoutInflater) }

    private val activityCommunicator: ActivityCommunicator by lazy { activity as ActivityCommunicator }

    private val adapter = MainAdapter()

    private val id = MainFragment::class.toString()

    private val contentList = mutableListOf<GifObject>()

    private val onQueryTextListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            viewModel.q = query ?: ""
            reloadContent()
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            adapter.filter.filter(newText?.lowercase(Locale.getDefault()))
            return true
        }
    }

    private val observer = Observer<TestTaskResponse<GetGifsResponse>> { updateContent(it) }

    private val scrolledHelper = ScrolledHelper(false, object : ScrolledHelper.OnScrollCallback {
        override fun onScrolledToEnd() {
            requestContent()
            Log.e("set content", "set")
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        initialize()
        observeData()

        return binder.root
    }

    private fun observeData() {
        viewModel.trendingLiveData.observe(viewLifecycleOwner, observer)
        viewModel.searchingLiveData.observe(viewLifecycleOwner, observer)
    }

    private fun initialize() {
        binder.mainSwipeRefreshLayout.setOnRefreshListener { reloadContent() }

        adapter.itemClick = {
            findNavController().navigate(MainFragmentDirections.actionNavMainFragmentToNavFullscreenFragment(it))
        }
        binder.mainRecyclerView.adapter = adapter
        binder.mainRecyclerView.addOnScrollListener(scrolledHelper)
        binder.mainRecyclerView.layoutManager = GridLayoutManager(context, GIFS_REPRESENTATION_COLUMNS)
        binder.mainRecyclerView.itemAnimator = null
    }

    private fun requestContent() {
        binder.mainSwipeRefreshLayout.isRefreshing = true
        if (isNetworkConnected(requireContext())) {
            val q = activityCommunicator.getAppBarSearchView().query
            if (q.isNotEmpty()) {
                viewModel.requestSearchingGifs(q = q.toString(), id = id)
            } else {
                viewModel.requestTrendingGifs(id = id)
            }
        } else if (adapter.contentList.isEmpty()) {
            updateContent(TestTaskResponse.Success(GetGifsResponse(
                Hawk.get<MutableList<GifObject>>(CACHED_IDS_LIST_KEY).toTypedArray(),
                PaginationObject(0, 0, 0),
                MetaObject("OK", "200", null)
            )))
        } else {
            binder.mainSwipeRefreshLayout.isRefreshing = false
        }
    }

    private fun reloadContent() {
        viewModel.refreshPagination()
        contentList.clear()
        adapter.clearContent()
        requestContent()
    }

    private fun updateContent(response: TestTaskResponse<GetGifsResponse>) {
        when(response) {
            is TestTaskResponse.Success -> {
                contentList.addAll(viewModel.filterContent(response.data.data.toList()))
                adapter.setContent(contentList)
                activityCommunicator.setContent(contentList)
                scrolledHelper.setLastPage(response.data.data.size < LIMIT_ON_PAGE)
                scrolledHelper.loadMore(false)
            }
            is TestTaskResponse.Error -> Toast.makeText(context, response.meta.msg, Toast.LENGTH_SHORT).show()
        }
        binder.mainSwipeRefreshLayout.isRefreshing = false

    }

    private fun setupSearchView() {
        activityCommunicator.getAppBarSearchView().setOnQueryTextListener(onQueryTextListener)
        activityCommunicator.getAppBarSearchView().setOnCloseListener {
            reloadContent()
            return@setOnCloseListener false
        }
    }

    override fun onResume() {
        setupSearchView()
        reloadContent()
        super.onResume()
    }

}