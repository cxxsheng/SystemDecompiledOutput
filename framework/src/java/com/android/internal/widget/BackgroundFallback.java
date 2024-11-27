package com.android.internal.widget;

/* loaded from: classes5.dex */
public class BackgroundFallback {
    private android.graphics.drawable.Drawable mBackgroundFallback;

    public void setDrawable(android.graphics.drawable.Drawable drawable) {
        this.mBackgroundFallback = drawable;
    }

    public android.graphics.drawable.Drawable getDrawable() {
        return this.mBackgroundFallback;
    }

    public boolean hasFallback() {
        return this.mBackgroundFallback != null;
    }

    public void draw(android.view.ViewGroup viewGroup, android.view.ViewGroup viewGroup2, android.graphics.Canvas canvas, android.view.View view, android.view.View view2, android.view.View view3) {
        int i;
        if (!hasFallback()) {
            return;
        }
        int width = viewGroup.getWidth();
        int height = viewGroup.getHeight();
        int left = viewGroup2.getLeft();
        int top = viewGroup2.getTop();
        int childCount = viewGroup2.getChildCount();
        int i2 = width;
        int i3 = height;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        while (i4 < childCount) {
            android.view.View childAt = viewGroup2.getChildAt(i4);
            int i7 = childCount;
            android.graphics.drawable.Drawable background = childAt.getBackground();
            if (childAt == view) {
                if (background == null && (childAt instanceof android.view.ViewGroup) && ((android.view.ViewGroup) childAt).getChildCount() == 0) {
                }
                i2 = java.lang.Math.min(i2, childAt.getLeft() + left);
                i3 = java.lang.Math.min(i3, childAt.getTop() + top);
                i5 = java.lang.Math.max(i5, childAt.getRight() + left);
                i6 = java.lang.Math.max(i6, childAt.getBottom() + top);
            } else if (childAt.getVisibility() == 0) {
                if (!isOpaque(background)) {
                }
                i2 = java.lang.Math.min(i2, childAt.getLeft() + left);
                i3 = java.lang.Math.min(i3, childAt.getTop() + top);
                i5 = java.lang.Math.max(i5, childAt.getRight() + left);
                i6 = java.lang.Math.max(i6, childAt.getBottom() + top);
            }
            i4++;
            childCount = i7;
        }
        boolean z = true;
        int i8 = 0;
        while (i8 < 2) {
            android.view.View view4 = i8 == 0 ? view2 : view3;
            if (view4 == null || view4.getVisibility() != 0 || view4.getAlpha() != 1.0f || !isOpaque(view4.getBackground())) {
                z = false;
            } else {
                if (view4.getTop() <= 0 && view4.getBottom() >= height && view4.getLeft() <= 0 && view4.getRight() >= i2) {
                    i2 = 0;
                }
                if (view4.getTop() <= 0 && view4.getBottom() >= height && view4.getLeft() <= i5 && view4.getRight() >= width) {
                    i5 = width;
                }
                if (view4.getTop() <= 0 && view4.getBottom() >= i3 && view4.getLeft() <= 0 && view4.getRight() >= width) {
                    i3 = 0;
                }
                if (view4.getTop() <= i6 && view4.getBottom() >= height && view4.getLeft() <= 0 && view4.getRight() >= width) {
                    i6 = height;
                }
                z &= view4.getTop() <= 0 && view4.getBottom() >= i3;
            }
            i8++;
        }
        if (z && (viewsCoverEntireWidth(view2, view3, width) || viewsCoverEntireWidth(view3, view2, width))) {
            i3 = 0;
        }
        if (i2 >= i5 || i3 >= i6) {
            return;
        }
        if (i3 <= 0) {
            i = 0;
        } else {
            i = 0;
            this.mBackgroundFallback.setBounds(0, 0, width, i3);
            this.mBackgroundFallback.draw(canvas);
        }
        if (i2 > 0) {
            this.mBackgroundFallback.setBounds(i, i3, i2, height);
            this.mBackgroundFallback.draw(canvas);
        }
        if (i5 < width) {
            this.mBackgroundFallback.setBounds(i5, i3, width, height);
            this.mBackgroundFallback.draw(canvas);
        }
        if (i6 < height) {
            this.mBackgroundFallback.setBounds(i2, i6, i5, height);
            this.mBackgroundFallback.draw(canvas);
        }
    }

    private boolean isOpaque(android.graphics.drawable.Drawable drawable) {
        return drawable != null && drawable.getOpacity() == -1;
    }

    private boolean viewsCoverEntireWidth(android.view.View view, android.view.View view2, int i) {
        return view.getLeft() <= 0 && view.getRight() >= view2.getLeft() && view2.getRight() >= i;
    }
}
