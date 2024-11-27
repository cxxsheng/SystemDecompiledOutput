package com.android.internal.widget;

/* loaded from: classes5.dex */
class ScrollbarHelper {
    ScrollbarHelper() {
    }

    static int computeScrollOffset(com.android.internal.widget.RecyclerView.State state, com.android.internal.widget.OrientationHelper orientationHelper, android.view.View view, android.view.View view2, com.android.internal.widget.RecyclerView.LayoutManager layoutManager, boolean z, boolean z2) {
        int max;
        if (layoutManager.getChildCount() == 0 || state.getItemCount() == 0 || view == null || view2 == null) {
            return 0;
        }
        int min = java.lang.Math.min(layoutManager.getPosition(view), layoutManager.getPosition(view2));
        int max2 = java.lang.Math.max(layoutManager.getPosition(view), layoutManager.getPosition(view2));
        if (z2) {
            max = java.lang.Math.max(0, (state.getItemCount() - max2) - 1);
        } else {
            max = java.lang.Math.max(0, min);
        }
        if (!z) {
            return max;
        }
        return java.lang.Math.round((max * (java.lang.Math.abs(orientationHelper.getDecoratedEnd(view2) - orientationHelper.getDecoratedStart(view)) / (java.lang.Math.abs(layoutManager.getPosition(view) - layoutManager.getPosition(view2)) + 1))) + (orientationHelper.getStartAfterPadding() - orientationHelper.getDecoratedStart(view)));
    }

    static int computeScrollExtent(com.android.internal.widget.RecyclerView.State state, com.android.internal.widget.OrientationHelper orientationHelper, android.view.View view, android.view.View view2, com.android.internal.widget.RecyclerView.LayoutManager layoutManager, boolean z) {
        if (layoutManager.getChildCount() == 0 || state.getItemCount() == 0 || view == null || view2 == null) {
            return 0;
        }
        if (!z) {
            return java.lang.Math.abs(layoutManager.getPosition(view) - layoutManager.getPosition(view2)) + 1;
        }
        return java.lang.Math.min(orientationHelper.getTotalSpace(), orientationHelper.getDecoratedEnd(view2) - orientationHelper.getDecoratedStart(view));
    }

    static int computeScrollRange(com.android.internal.widget.RecyclerView.State state, com.android.internal.widget.OrientationHelper orientationHelper, android.view.View view, android.view.View view2, com.android.internal.widget.RecyclerView.LayoutManager layoutManager, boolean z) {
        if (layoutManager.getChildCount() == 0 || state.getItemCount() == 0 || view == null || view2 == null) {
            return 0;
        }
        if (!z) {
            return state.getItemCount();
        }
        return (int) (((orientationHelper.getDecoratedEnd(view2) - orientationHelper.getDecoratedStart(view)) / (java.lang.Math.abs(layoutManager.getPosition(view) - layoutManager.getPosition(view2)) + 1)) * state.getItemCount());
    }
}
