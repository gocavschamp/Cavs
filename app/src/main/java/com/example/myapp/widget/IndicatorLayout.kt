package com.example.myapp.widget

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import com.moonlight.flyvideo.R

/**
 *@auther yuwenming
 *@createTime：2023/9/25 14:36
 *@desc:
 **/
open class IndicatorLayout constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private var count: Int = 0
    private var indicatorPosition: Int = 0
    private var selectIcon: Int = R.drawable.td_intro_like
    private var unselectIcon: Int = R.drawable.td_intro_likeno
    private var indicatorImages = mutableListOf<ImageView>()

    init {
        initAttrs(attrs)
        initData()
    }

    fun onPageSelect(position: Int) {
        if (position != indicatorPosition && position <= indicatorImages.size - 1) {
            indicatorImages.get(indicatorPosition).setImageResource(unselectIcon)
            indicatorImages.get(position).setImageResource(selectIcon)
            indicatorPosition = position
        }

    }

    fun setCount(idCount: Int) {
        count = idCount
        removeAllViews()
        for (i in 0 until count) {
            val imageView = ImageView(context)
            imageView.scaleType = ImageView.ScaleType.CENTER
            val params = LinearLayout.LayoutParams(mIndicatorWidth, mIndicatorHeight)
            params.marginEnd = mIndicatorMargin
            if (i == 0) {
                imageView.setImageResource(selectIcon)
            } else {
                imageView.setImageResource(unselectIcon)
            }
            imageView.layoutParams = params
            indicatorImages.add(imageView)
            addView(imageView, params)
        }


    }

    private fun initData() {
        orientation = HORIZONTAL
        gravity = Gravity.CENTER_VERTICAL

    }

    private var mIndicatorMargin = 15
    private var mIndicatorWidth = 0
    private var mIndicatorHeight = 0

    private fun initAttrs(attrs: AttributeSet?) {
        attrs?.apply {
            val array = context.obtainStyledAttributes(attrs, R.styleable.YwnIndicator)
            selectIcon =
                array.getResourceId(R.styleable.YwnIndicator_select_icon, R.drawable.rec_blue_indicator_select)
            unselectIcon = array.getResourceId(
                R.styleable.YwnIndicator_unselect_icon,
                R.drawable.rec_blue_indicator_unselect
            )
            mIndicatorWidth =
                array.getDimensionPixelSize(R.styleable.YwnIndicator_indicator_width, 15)
            mIndicatorHeight =
                array.getDimensionPixelSize(R.styleable.YwnIndicator_indicator_height, 5)
            mIndicatorMargin =
                array.getDimensionPixelSize(R.styleable.YwnIndicator_indicator_margin, 15)
            count = array.getInt(R.styleable.YwnIndicator_count, 1)
            array.recycle()
        }
    }


}