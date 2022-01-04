package com.doctor.yumyum.common.base

import android.app.Dialog
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import android.R
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.*


abstract class BaseDialog<T : ViewDataBinding>(@LayoutRes private val layoutResId: Int) :
    DialogFragment() {
    protected lateinit var binding: T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.apply {
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            requestWindowFeature(Window.FEATURE_NO_TITLE)
        }
        binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner
    }

    override fun onResume() {
        super.onResume()
        val display = resources.displayMetrics

        val window: Window = dialog?.window ?: return
        val params: WindowManager.LayoutParams = window.attributes
        params.width = (display.widthPixels * 0.8).toInt()

        dialog?.window!!.attributes = params
    }
}