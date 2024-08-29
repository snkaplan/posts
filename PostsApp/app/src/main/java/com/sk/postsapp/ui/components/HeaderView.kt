package com.sk.postsapp.ui.components

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.sk.postsapp.R
import com.sk.postsapp.databinding.ViewHeaderBinding

class HeaderView : ConstraintLayout {

    private lateinit var binding: ViewHeaderBinding

    private var leftIconRes: Drawable? = null
        set(value) {
            field = value
            field?.let {
                binding.ivHeaderLeftIcon.visibility = VISIBLE
                binding.ivHeaderLeftIcon.setImageDrawable(it)
            } ?: run {
                binding.ivHeaderLeftIcon.visibility = GONE
            }
        }

    private var centerTitleText: String? = null
        set(value) {
            field = value
            field?.let {
                binding.tvHeaderTitle.visibility = VISIBLE
                binding.tvHeaderTitle.text = it
            } ?: run {
                binding.tvHeaderTitle.visibility = GONE
            }
        }

    constructor(context: Context) : super(context) {
        initialize()
        obtainParameters(null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initialize()
        obtainParameters(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initialize()
        obtainParameters(attrs)
    }

    private fun initialize() {
        binding = ViewHeaderBinding.inflate(LayoutInflater.from(context), this, true)
    }

    private fun obtainParameters(attrs: AttributeSet?) {
        context.theme.obtainStyledAttributes(attrs, R.styleable.HeaderView, 0, 0).apply {
            try {
                leftIconRes = getDrawable(R.styleable.HeaderView_hv_leftIconRes)
                centerTitleText = getString(R.styleable.HeaderView_hv_centerTitleText)
            } finally {
                recycle()
            }
        }
    }
}