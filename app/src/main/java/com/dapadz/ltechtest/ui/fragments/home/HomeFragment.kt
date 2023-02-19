package com.dapadz.ltechtest.ui.fragments.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout.VERTICAL
import android.widget.Toast
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.transition.TransitionManager
import com.dapadz.ltechtest.R
import com.dapadz.ltechtest.adapters.OnPostAdapterItemClickListener
import com.dapadz.ltechtest.adapters.OnPostHeaderItemClickListener
import com.dapadz.ltechtest.adapters.PostAdapter
import com.dapadz.ltechtest.adapters.PostHeaderAdapter
import com.dapadz.ltechtest.data.models.PostModel
import com.dapadz.ltechtest.databinding.FragmentHomeBinding
import com.dapadz.ltechtest.ui.bottom_sheet.OnSheetSortClickListener
import com.dapadz.ltechtest.ui.bottom_sheet.SheetSort
import com.dapadz.ltechtest.utils.SortType
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.MaterialSharedAxis
import com.google.android.material.transition.MaterialSharedAxis.Y

class HomeFragment : Fragment(),
    OnPostAdapterItemClickListener,
    OnPostHeaderItemClickListener,
    OnSheetSortClickListener
{

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding !!

    private val viewModel by viewModels<HomeViewModel>()

    private lateinit var adapter : ConcatAdapter
    private lateinit var adapterPost : PostAdapter
    private lateinit var adapterHeader : PostHeaderAdapter

    private lateinit var filterSheet : SheetSort

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = MaterialElevationScale(false)
        reenterTransition = MaterialElevationScale(true)
    }

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        postponeEnterTransition()
        binding.root.doOnPreDraw { startPostponedEnterTransition() }
        initBottomSheet()
        initAdapter()
        initRecyclerView()
        observeViewModel()
        setToolbarMenuClickListener()
        setSortFromViewModel()
        return binding.root
    }

    private fun initBottomSheet() {
        filterSheet = SheetSort(this)
    }

    private fun initAdapter() {
        adapterHeader = PostHeaderAdapter(this)
        adapterPost = PostAdapter(this )
        adapter = ConcatAdapter(
            adapterHeader,
            adapterPost
        )
    }

    private fun initRecyclerView() {
        with(binding) {
            recyclerView.adapter = adapter
            recyclerView.addItemDecoration(
                DividerItemDecoration(requireContext(), VERTICAL)
            )
        }
    }

    private fun setToolbarMenuClickListener() {
        with(binding) {
            toolbar.setOnMenuItemClickListener {
                when(it.itemId) {
                    R.id.refresh -> {
                        progressBaseShow(true)
                        viewModel.onRefresh()
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun setSortFromViewModel() {
        adapterHeader.setSort(viewModel.sortType)
    }

    private fun observeViewModel() {
        viewModel.posts.observe(viewLifecycleOwner) {
            it?.let { data ->
                adapterPost.setData(data)
            }
            progressBaseShow(false)
        }
        viewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it.message?:"error", Toast.LENGTH_SHORT).show()
            progressBaseShow(false)
        }
    }

    private fun progressBaseShow(show:Boolean) {
        with(binding) {
            val sharedAxis = MaterialSharedAxis(Y, true)
            TransitionManager.beginDelayedTransition(root, sharedAxis)
            progressBar.isVisible = show
            recyclerView.isVisible = !show
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onPostClick(post : PostModel, view : View) {
        val extras = FragmentNavigatorExtras(view to post.id)
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(post)
        findNavController().navigate(action, extras)
    }

    override fun onFilterClick() {
        if (! filterSheet.isResumed) {
            filterSheet.show(viewModel.sortType, parentFragmentManager, SheetSort.TAG)
        }
    }

    override fun onSortChange(sort : SortType) {
        viewModel.sortType = sort
        adapterHeader.setSort(sort)
        progressBaseShow(true)
    }
}