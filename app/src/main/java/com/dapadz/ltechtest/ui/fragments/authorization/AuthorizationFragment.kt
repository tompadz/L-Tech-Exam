package com.dapadz.ltechtest.ui.fragments.authorization

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dapadz.ltechtest.R
import com.dapadz.ltechtest.databinding.FragmentAuthorizationBinding
import com.dapadz.ltechtest.ui.fragments.BaseFragment
import com.dapadz.ltechtest.utils.AndroidUtils
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser
import ru.tinkoff.decoro.watchers.FormatWatcher
import ru.tinkoff.decoro.watchers.MaskFormatWatcher

class AuthorizationFragment : BaseFragment() {

    private var _binding : FragmentAuthorizationBinding? = null
    private val binding get() = _binding !!

    private val viewModel by viewModels<AuthorizationViewModel>()

    private var phoneNumber = ""
    private var password = ""

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View {
        _binding = FragmentAuthorizationBinding.inflate(inflater, container, false)
        setFieldChangeListener()
        observeViewModel()
        setLoginButtonClickListener()
        return binding.root
    }

    private fun setPhoneFieldMask(maskPattern:String) {
        with(binding) {
            val slots = UnderscoreDigitSlotsParser().parseSlots(convertMask(maskPattern))
            val mask = MaskImpl.createTerminated(slots)
            val watcher : FormatWatcher = MaskFormatWatcher(mask)
            fieldPhone.addTextChangedListener(watcher)
        }
    }

    private fun convertMask(maskPattern : String) :String {
        val result = maskPattern.replace('Х', '_') //Ru char
        return result.replace('X', '_') //Eng char
    }

    private fun setFieldChangeListener() {
        with(binding) {
            fieldPhone.addTextChangedListener {
                phoneNumber = it.toString()
                checkCanButtonBeEnabled()
            }
            fieldPassword.addTextChangedListener {
                password = it.toString()
                checkCanButtonBeEnabled()
            }
        }
    }

    private fun checkCanButtonBeEnabled() {
        with(binding) {
            button.isEnabled = fieldPhone.text !!.isNotEmpty() && fieldPassword.text !!.isNotEmpty()
        }
    }

    private fun setLoginButtonClickListener() {
        binding.button.setOnClickListener {
            val convertedPhone = phoneNumber.replace(Regex("[^0-9]"),"")
            viewModel.login(convertedPhone, password)
            showLoadingDialog()
        }
    }

    private fun observeViewModel() {
        viewModel.isAuthorized.observe(viewLifecycleOwner) {
            hideLoadingDialog()
            if (it) {
                findNavController().navigate(R.id.homeFragment)
            }else {
                binding.inputLayout.error = "error"
            }
        }
        viewModel.error.observe(viewLifecycleOwner) {
            binding.inputLayout.error = it.message?:"error"
            hideLoadingDialog()
        }
        viewModel.mask.observe(viewLifecycleOwner) {
            if (it != null) {
                setPhoneFieldMask(it)
            }
        }
    }

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            AndroidUtils().showKeyboard(fieldPhone)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}