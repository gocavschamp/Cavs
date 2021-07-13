package com.example.myapp.homepage.mine;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GridItemDecoration extends RecyclerView.ItemDecoration {
    private final int horizontalSpace;
    private final int verticalSpace;

    /**
     *
     * @param space 水平垂直间隔 dp
     */
    public GridItemDecoration(int space) {
        this(space, space);
    }

    /**
     *
     * @param horizontalSpace 水平间隔 dp
     * @param verticalSpace 垂直间隔 dp
     */
    public GridItemDecoration(int horizontalSpace, int verticalSpace) {
        this.horizontalSpace = horizontalSpace;
        this.verticalSpace = verticalSpace;
    }

    @Override
    public void getItemOffsets(Rect outRect, @NonNull View view, RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.left = horizontalSpace;
        outRect.right = horizontalSpace;
        outRect.bottom = verticalSpace;
        outRect.top = verticalSpace;

        // Add top margin only for the first item to avoid double space between items
//        if (parent.getChildPosition(view) == 0) {
//            outRect.top = space;
//        }
    }
}