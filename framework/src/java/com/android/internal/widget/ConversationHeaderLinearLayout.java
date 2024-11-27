package com.android.internal.widget;

@android.widget.RemoteViews.RemoteView
/* loaded from: classes5.dex */
public class ConversationHeaderLinearLayout extends android.widget.LinearLayout {
    public ConversationHeaderLinearLayout(android.content.Context context) {
        super(context);
    }

    public ConversationHeaderLinearLayout(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ConversationHeaderLinearLayout(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    private int calculateTotalChildLength() {
        int childCount = getChildCount();
        int i = 0;
        for (int i2 = 0; i2 < childCount; i2++) {
            android.view.View childAt = getChildAt(i2);
            if (childAt != null && childAt.getVisibility() != 8) {
                android.widget.LinearLayout.LayoutParams layoutParams = (android.widget.LinearLayout.LayoutParams) childAt.getLayoutParams();
                i += childAt.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
            }
        }
        return i + getPaddingLeft() + getPaddingRight();
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int calculateTotalChildLength = calculateTotalChildLength() - getMeasuredWidth();
        if (calculateTotalChildLength <= 0) {
            return;
        }
        int childCount = getChildCount();
        java.util.ArrayList arrayList = null;
        float f = 0.0f;
        for (int i3 = 0; i3 < childCount; i3++) {
            android.view.View childAt = getChildAt(i3);
            if (childAt != null && childAt.getVisibility() != 8) {
                float f2 = ((android.widget.LinearLayout.LayoutParams) childAt.getLayoutParams()).weight;
                if (f2 != 0.0f && childAt.getMeasuredWidth() != 0) {
                    if (arrayList == null) {
                        arrayList = new java.util.ArrayList(childCount);
                    }
                    arrayList.add(new com.android.internal.widget.ConversationHeaderLinearLayout.ViewInfo(childAt));
                    f += java.lang.Math.max(0.0f, f2);
                }
            }
        }
        if (arrayList == null || arrayList.isEmpty()) {
            return;
        }
        balanceViewWidths(arrayList, f, calculateTotalChildLength);
        remeasureChangedChildren(arrayList);
    }

    private void remeasureChangedChildren(java.util.List<com.android.internal.widget.ConversationHeaderLinearLayout.ViewInfo> list) {
        for (com.android.internal.widget.ConversationHeaderLinearLayout.ViewInfo viewInfo : list) {
            if (viewInfo.mWidth != viewInfo.mStartWidth) {
                viewInfo.mView.measure(android.view.View.MeasureSpec.makeMeasureSpec(java.lang.Math.max(0, viewInfo.mWidth), 1073741824), android.view.View.MeasureSpec.makeMeasureSpec(viewInfo.mView.getMeasuredHeight(), 1073741824));
            }
        }
    }

    void balanceViewWidths(java.util.List<com.android.internal.widget.ConversationHeaderLinearLayout.ViewInfo> list, float f, int i) {
        boolean z;
        for (boolean z2 = true; z2 && i > 0 && f > 0.0f; z2 = z) {
            float f2 = 0.0f;
            z = false;
            int i2 = 0;
            for (com.android.internal.widget.ConversationHeaderLinearLayout.ViewInfo viewInfo : list) {
                if (viewInfo.mWeight > 0.0f && viewInfo.mWidth > 0) {
                    int i3 = (int) (viewInfo.mWidth - (i * (viewInfo.mWeight / f)));
                    if (i3 < 0) {
                        z = true;
                        i3 = 0;
                    }
                    i2 += viewInfo.mWidth - i3;
                    viewInfo.mWidth = i3;
                    if (viewInfo.mWidth > 0) {
                        f2 += viewInfo.mWeight;
                    }
                }
            }
            i -= i2;
            f = f2;
        }
    }

    static class ViewInfo {
        final int mStartWidth;
        final android.view.View mView;
        final float mWeight;
        int mWidth;

        ViewInfo(android.view.View view) {
            this.mView = view;
            this.mWeight = ((android.widget.LinearLayout.LayoutParams) view.getLayoutParams()).weight;
            int measuredWidth = view.getMeasuredWidth();
            this.mWidth = measuredWidth;
            this.mStartWidth = measuredWidth;
        }
    }
}
