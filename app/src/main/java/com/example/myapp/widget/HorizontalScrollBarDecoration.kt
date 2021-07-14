package com.example.myapp.widget

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.recyclerview.widget.RecyclerView
import com.nucarf.base.utils.ScreenUtil

/**
 * @Description TODO
 * @Author ${ yuwenming }
 * @Date 2021/7/14 10:27
 */
class HorizontalScrollBarDecoration : RecyclerView.ItemDecoration {
    var barheitht:Float=0f
    var barwidth:Float=0f
    var barColor:String="#FF327BF9"
    var bgColor:String="#FFEAF1FE"
    constructor(barheitht: Float, barwidth: Float,barColor:String,bgColor:String) {
        this.barheitht = barheitht
        this.barwidth = barwidth
        this.barColor = barColor
        this.bgColor = bgColor
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)

        val barHeight = ScreenUtil.dip2px(barheitht)
        val scrollWidth = ScreenUtil.dip2px(barwidth)
        val indicatorWidth = ScreenUtil.dip2px(barwidth/2)
        val paddingBottom = -ScreenUtil.dip2px(2f)
        val barX = (parent.width / 2 - scrollWidth / 2).toFloat()
        val barY = (parent.height - paddingBottom - barHeight).toFloat()

        val paint = Paint()
        paint.isAntiAlias = true
        paint.color = Color.parseColor(bgColor)
        paint.strokeCap = Paint.Cap.ROUND
        paint.strokeWidth = barHeight.toFloat()
        c.drawLine(barX, barY, barX + scrollWidth.toFloat(), barY, paint)

        val extent = parent.computeHorizontalScrollExtent()
        val range = parent.computeHorizontalScrollRange()
        val offset = parent.computeHorizontalScrollOffset()
        val maxEndX = (range - extent).toFloat()
        //可滑动
        if (maxEndX > 0) {
            val proportion = offset / maxEndX

            val scrollableDistance = scrollWidth - indicatorWidth

            val offsetX = scrollableDistance * proportion
            paint.color = Color.parseColor(barColor)
            c.drawLine(barX + offsetX, barY, barX + indicatorWidth.toFloat() + offsetX, barY, paint)
        } else {
            paint.color = Color.parseColor(barColor)
            c.drawLine(barX, barY, barX + scrollWidth.toFloat(), barY, paint)
        }
    }
}