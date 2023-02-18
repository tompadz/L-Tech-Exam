package com.dapadz.ltechtest.ui.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dapadz.ltechtest.R
import com.dapadz.ltechtest.databinding.FragmentDetailBinding
import com.dapadz.ltechtest.utils.GlideLoader
import com.google.android.material.transition.MaterialContainerTransform

class DetailFragment : Fragment() {

    private var _binging : FragmentDetailBinding? = null
    private val binding get() = _binging !!

    private val args : DetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.nav_host
            scrimColor = Color.TRANSPARENT
        }
    }

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View {
       _binging = FragmentDetailBinding.inflate(inflater, container, false)
        initOnBackPress()
        initPageData()
        return binding.root
    }

    private fun initOnBackPress() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initPageData() {
        with(binding) {
            root.transitionName = args.post.id
            textDate.text = args.post.getDateWithFormat()
            textTitle.text = args.post.title
            textDescription.text = args.post.text
            GlideLoader().baseLoadImage(image, args.post.image)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binging = null
    }
}