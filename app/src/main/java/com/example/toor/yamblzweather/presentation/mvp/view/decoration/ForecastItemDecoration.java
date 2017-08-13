package com.example.toor.yamblzweather.presentation.mvp.view.decoration;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by igor on 8/11/17.
 */

public class ForecastItemDecoration extends RecyclerView.ItemDecoration {

    private int selectedPosition;

    private int selectedColor;
    private int frameColor;

    public ForecastItemDecoration(int selectedColor, int frameColor) {
        this.selectedColor = selectedColor;
        this.frameColor = frameColor;
    }

    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent,
                       RecyclerView.State state) {
        int childCount = parent.getChildCount();
        Paint paint = new Paint();
        paint.setStrokeWidth(2.f);
        for(int i = 0; i < childCount; ++i) {
            final View child = parent.getChildAt(i);
            RecyclerView.ViewHolder holder =
                    parent.getChildViewHolder(child);
            if(selectedPosition != holder.getAdapterPosition()) {
                paint.setStyle(Paint.Style.STROKE);
                paint.setColor(frameColor);
                c.drawLine(child.getLeft(), child.getTop(), child.getRight(), child.getTop(), paint);
            }
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent,
                           RecyclerView.State state) {
        int childCount = parent.getChildCount();
        Paint paint = new Paint();
        paint.setStrokeWidth(2.f);
        for(int i = 0; i < childCount; ++i) {
            final View child = parent.getChildAt(i);
            RecyclerView.ViewHolder holder =
                    parent.getChildViewHolder(child);
            if(selectedPosition == holder.getAdapterPosition()) {
                Path path = new Path();
                path.moveTo(child.getLeft(), child.getTop());
                path.lineTo(child.getLeft() +
                                child.getLeft() + (child.getRight() - child.getLeft()) * 2 / 3,
                        (child.getTop() + child.getBottom()) / 2);
                path.lineTo(child.getLeft(), child.getBottom());
                path.close();
                paint.setStrokeWidth(4);
                paint.setColor(selectedColor);
                paint.setStyle(Paint.Style.FILL_AND_STROKE);
                paint.setAntiAlias(true);
                c.drawPath(path, paint);
            }
        }
    }
}
