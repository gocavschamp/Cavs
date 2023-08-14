package com.example.myapp.homepage.homedemo.videolist

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.annotation.ColorInt
import androidx.core.view.isVisible
import com.afollestad.materialdialogs.DialogBehavior
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.WhichButton
import com.afollestad.materialdialogs.actions.getActionButton
import com.afollestad.materialdialogs.internal.main.DialogLayout
import com.afollestad.materialdialogs.utils.MDUtil.getWidthAndHeight
import com.moonlight.flyvideo.R
import kotlin.math.min

/**
 *@auther yuwenming
 *@createTime：2023/8/7 19:08
 *@desc:
 **/
object VideoTheme :DialogBehavior{
//    override fun createView(
//        creatingContext: Context,
//        dialogWindow: Window,
//        layoutInflater: LayoutInflater,
//        dialog: MaterialDialog
//    ): ViewGroup {
//       return R.style.AppTheme
//    }
//
//    override fun getDialogLayout(root: ViewGroup): DialogLayout {
//        TODO("Not yet implemented")
//    }

    override fun getThemeRes(isDark: Boolean): Int {
        return R.style.AppTheme
    }

    @SuppressLint("InflateParams")
    override fun createView(
        creatingContext: Context,
        dialogWindow: Window,
        layoutInflater: LayoutInflater,
        dialog: MaterialDialog
    ): ViewGroup {
        return layoutInflater.inflate(
            R.layout.md_dialog_base,
            null,
            false
        ) as ViewGroup
    }

    override fun getDialogLayout(root: ViewGroup): DialogLayout {
        return root as DialogLayout
    }

    override fun setWindowConstraints(
        context: Context,
        window: Window,
        view: DialogLayout,
        maxWidth: Int?
    ) {
        if (maxWidth == 0) {
            // Postpone
            return
        }

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        val wm = window.windowManager ?: return
        val res = context.resources
        val (windowWidth, windowHeight) = wm.getWidthAndHeight()

        val windowVerticalPadding =
            res.getDimensionPixelSize(R.dimen.md_dialog_vertical_margin)
        view.maxHeight = windowHeight - windowVerticalPadding * 2

        val lp = WindowManager.LayoutParams().apply {
            copyFrom(window.attributes)

            val windowHorizontalPadding =
                res.getDimensionPixelSize(R.dimen.md_dialog_horizontal_margin)
            val calculatedWidth = windowWidth - windowHorizontalPadding * 2
            val actualMaxWidth =
                maxWidth ?: res.getDimensionPixelSize(R.dimen.md_dialog_max_width)
            width = min(actualMaxWidth, calculatedWidth)
        }
        window.attributes = lp
    }

    override fun setBackgroundColor(
        view: DialogLayout,
        @ColorInt color: Int,
        cornerRounding: Float
    ) {
        view.background = GradientDrawable().apply {
            cornerRadius = cornerRounding
            setColor(color)
        }
    }

    override fun onPreShow(dialog: MaterialDialog) = Unit

    override fun onPostShow(dialog: MaterialDialog) {
        val negativeBtn = dialog.getActionButton(WhichButton.NEGATIVE)
//        if (negativeBtn.isVisible) {
//            negativeBtn.post { negativeBtn.requestFocus() }
//            return
//        }
        val positiveBtn = dialog.getActionButton(WhichButton.POSITIVE)
//        if (positiveBtn.isVisible) {
//            positiveBtn.post { positiveBtn.requestFocus() }
//        }
    }

    override fun onDismiss(): Boolean = false
}