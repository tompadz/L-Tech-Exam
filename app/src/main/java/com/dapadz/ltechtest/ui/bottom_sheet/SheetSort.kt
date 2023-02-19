package com.dapadz.ltechtest.ui.bottom_sheet

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.dapadz.ltechtest.R
import com.dapadz.ltechtest.databinding.SheetSortBinding
import com.dapadz.ltechtest.utils.SortType
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SheetSort(
    private val listener : OnSheetSortClickListener,
) : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "SheetSort"
    }

    private var _binding : SheetSortBinding? = null
    private val binding get() = _binding !!

    private var currentSortType = SortType.DEFAULT
    private var sortType = currentSortType

    override fun onCreateView(
        inflater : LayoutInflater,
        container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View {
        _binding = SheetSortBinding.inflate(inflater, container, false)
        initButtons()
        setToolbarMenuClickListener()
        setRadioGroupCheckListener()
        return binding.root
    }

    private fun initButtons() {
        with(binding) {
            when (currentSortType) {
                SortType.DATE -> radioDate.isChecked = true
                SortType.DEFAULT -> radioDefault.isChecked = true
            }
        }
    }

    private fun setToolbarMenuClickListener() {
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.close -> {
                    dismiss()
                    true
                }
                else -> false
            }
        }
    }

    private fun setRadioGroupCheckListener() {
        with(binding) {
            radioGroup.setOnCheckedChangeListener { _, id ->
                sortType = when (id) {
                    R.id.radioDate -> SortType.DATE
                    else -> SortType.DEFAULT
                }
            }
        }
    }

    fun show(currentSortType : SortType, fm : FragmentManager, tag : String?) {
        this.currentSortType = currentSortType
        super.show(fm, tag)
    }

    override fun onDismiss(dialog : DialogInterface) {
        super.onDismiss(dialog)
        if (currentSortType != sortType) {
            listener.onSortChange(sortType)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}