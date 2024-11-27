package com.android.internal.widget;

/* loaded from: classes5.dex */
public abstract class OrientationHelper {
    public static final int HORIZONTAL = 0;
    private static final int INVALID_SIZE = Integer.MIN_VALUE;
    public static final int VERTICAL = 1;
    private int mLastTotalSpace;
    protected final com.android.internal.widget.RecyclerView.LayoutManager mLayoutManager;
    final android.graphics.Rect mTmpRect;

    public abstract int getDecoratedEnd(android.view.View view);

    public abstract int getDecoratedMeasurement(android.view.View view);

    public abstract int getDecoratedMeasurementInOther(android.view.View view);

    public abstract int getDecoratedStart(android.view.View view);

    public abstract int getEnd();

    public abstract int getEndAfterPadding();

    public abstract int getEndPadding();

    public abstract int getMode();

    public abstract int getModeInOther();

    public abstract int getStartAfterPadding();

    public abstract int getTotalSpace();

    public abstract int getTransformedEndWithDecoration(android.view.View view);

    public abstract int getTransformedStartWithDecoration(android.view.View view);

    public abstract void offsetChild(android.view.View view, int i);

    public abstract void offsetChildren(int i);

    private OrientationHelper(com.android.internal.widget.RecyclerView.LayoutManager layoutManager) {
        this.mLastTotalSpace = Integer.MIN_VALUE;
        this.mTmpRect = new android.graphics.Rect();
        this.mLayoutManager = layoutManager;
    }

    public void onLayoutComplete() {
        this.mLastTotalSpace = getTotalSpace();
    }

    public int getTotalSpaceChange() {
        if (Integer.MIN_VALUE == this.mLastTotalSpace) {
            return 0;
        }
        return getTotalSpace() - this.mLastTotalSpace;
    }

    public static com.android.internal.widget.OrientationHelper createOrientationHelper(com.android.internal.widget.RecyclerView.LayoutManager layoutManager, int i) {
        switch (i) {
            case 0:
                return createHorizontalHelper(layoutManager);
            case 1:
                return createVerticalHelper(layoutManager);
            default:
                throw new java.lang.IllegalArgumentException("invalid orientation");
        }
    }

    public static com.android.internal.widget.OrientationHelper createHorizontalHelper(com.android.internal.widget.RecyclerView.LayoutManager layoutManager) {
        return new com.android.internal.widget.OrientationHelper(layoutManager) { // from class: com.android.internal.widget.OrientationHelper.1
            @Override // com.android.internal.widget.OrientationHelper
            public int getEndAfterPadding() {
                return this.mLayoutManager.getWidth() - this.mLayoutManager.getPaddingRight();
            }

            @Override // com.android.internal.widget.OrientationHelper
            public int getEnd() {
                return this.mLayoutManager.getWidth();
            }

            @Override // com.android.internal.widget.OrientationHelper
            public void offsetChildren(int i) {
                this.mLayoutManager.offsetChildrenHorizontal(i);
            }

            @Override // com.android.internal.widget.OrientationHelper
            public int getStartAfterPadding() {
                return this.mLayoutManager.getPaddingLeft();
            }

            @Override // com.android.internal.widget.OrientationHelper
            public int getDecoratedMeasurement(android.view.View view) {
                com.android.internal.widget.RecyclerView.LayoutParams layoutParams = (com.android.internal.widget.RecyclerView.LayoutParams) view.getLayoutParams();
                return this.mLayoutManager.getDecoratedMeasuredWidth(view) + layoutParams.leftMargin + layoutParams.rightMargin;
            }

            @Override // com.android.internal.widget.OrientationHelper
            public int getDecoratedMeasurementInOther(android.view.View view) {
                com.android.internal.widget.RecyclerView.LayoutParams layoutParams = (com.android.internal.widget.RecyclerView.LayoutParams) view.getLayoutParams();
                return this.mLayoutManager.getDecoratedMeasuredHeight(view) + layoutParams.topMargin + layoutParams.bottomMargin;
            }

            @Override // com.android.internal.widget.OrientationHelper
            public int getDecoratedEnd(android.view.View view) {
                return this.mLayoutManager.getDecoratedRight(view) + ((com.android.internal.widget.RecyclerView.LayoutParams) view.getLayoutParams()).rightMargin;
            }

            @Override // com.android.internal.widget.OrientationHelper
            public int getDecoratedStart(android.view.View view) {
                return this.mLayoutManager.getDecoratedLeft(view) - ((com.android.internal.widget.RecyclerView.LayoutParams) view.getLayoutParams()).leftMargin;
            }

            @Override // com.android.internal.widget.OrientationHelper
            public int getTransformedEndWithDecoration(android.view.View view) {
                this.mLayoutManager.getTransformedBoundingBox(view, true, this.mTmpRect);
                return this.mTmpRect.right;
            }

            @Override // com.android.internal.widget.OrientationHelper
            public int getTransformedStartWithDecoration(android.view.View view) {
                this.mLayoutManager.getTransformedBoundingBox(view, true, this.mTmpRect);
                return this.mTmpRect.left;
            }

            @Override // com.android.internal.widget.OrientationHelper
            public int getTotalSpace() {
                return (this.mLayoutManager.getWidth() - this.mLayoutManager.getPaddingLeft()) - this.mLayoutManager.getPaddingRight();
            }

            @Override // com.android.internal.widget.OrientationHelper
            public void offsetChild(android.view.View view, int i) {
                view.offsetLeftAndRight(i);
            }

            @Override // com.android.internal.widget.OrientationHelper
            public int getEndPadding() {
                return this.mLayoutManager.getPaddingRight();
            }

            @Override // com.android.internal.widget.OrientationHelper
            public int getMode() {
                return this.mLayoutManager.getWidthMode();
            }

            @Override // com.android.internal.widget.OrientationHelper
            public int getModeInOther() {
                return this.mLayoutManager.getHeightMode();
            }
        };
    }

    public static com.android.internal.widget.OrientationHelper createVerticalHelper(com.android.internal.widget.RecyclerView.LayoutManager layoutManager) {
        return new com.android.internal.widget.OrientationHelper(layoutManager) { // from class: com.android.internal.widget.OrientationHelper.2
            @Override // com.android.internal.widget.OrientationHelper
            public int getEndAfterPadding() {
                return this.mLayoutManager.getHeight() - this.mLayoutManager.getPaddingBottom();
            }

            @Override // com.android.internal.widget.OrientationHelper
            public int getEnd() {
                return this.mLayoutManager.getHeight();
            }

            @Override // com.android.internal.widget.OrientationHelper
            public void offsetChildren(int i) {
                this.mLayoutManager.offsetChildrenVertical(i);
            }

            @Override // com.android.internal.widget.OrientationHelper
            public int getStartAfterPadding() {
                return this.mLayoutManager.getPaddingTop();
            }

            @Override // com.android.internal.widget.OrientationHelper
            public int getDecoratedMeasurement(android.view.View view) {
                com.android.internal.widget.RecyclerView.LayoutParams layoutParams = (com.android.internal.widget.RecyclerView.LayoutParams) view.getLayoutParams();
                return this.mLayoutManager.getDecoratedMeasuredHeight(view) + layoutParams.topMargin + layoutParams.bottomMargin;
            }

            @Override // com.android.internal.widget.OrientationHelper
            public int getDecoratedMeasurementInOther(android.view.View view) {
                com.android.internal.widget.RecyclerView.LayoutParams layoutParams = (com.android.internal.widget.RecyclerView.LayoutParams) view.getLayoutParams();
                return this.mLayoutManager.getDecoratedMeasuredWidth(view) + layoutParams.leftMargin + layoutParams.rightMargin;
            }

            @Override // com.android.internal.widget.OrientationHelper
            public int getDecoratedEnd(android.view.View view) {
                return this.mLayoutManager.getDecoratedBottom(view) + ((com.android.internal.widget.RecyclerView.LayoutParams) view.getLayoutParams()).bottomMargin;
            }

            @Override // com.android.internal.widget.OrientationHelper
            public int getDecoratedStart(android.view.View view) {
                return this.mLayoutManager.getDecoratedTop(view) - ((com.android.internal.widget.RecyclerView.LayoutParams) view.getLayoutParams()).topMargin;
            }

            @Override // com.android.internal.widget.OrientationHelper
            public int getTransformedEndWithDecoration(android.view.View view) {
                this.mLayoutManager.getTransformedBoundingBox(view, true, this.mTmpRect);
                return this.mTmpRect.bottom;
            }

            @Override // com.android.internal.widget.OrientationHelper
            public int getTransformedStartWithDecoration(android.view.View view) {
                this.mLayoutManager.getTransformedBoundingBox(view, true, this.mTmpRect);
                return this.mTmpRect.top;
            }

            @Override // com.android.internal.widget.OrientationHelper
            public int getTotalSpace() {
                return (this.mLayoutManager.getHeight() - this.mLayoutManager.getPaddingTop()) - this.mLayoutManager.getPaddingBottom();
            }

            @Override // com.android.internal.widget.OrientationHelper
            public void offsetChild(android.view.View view, int i) {
                view.offsetTopAndBottom(i);
            }

            @Override // com.android.internal.widget.OrientationHelper
            public int getEndPadding() {
                return this.mLayoutManager.getPaddingBottom();
            }

            @Override // com.android.internal.widget.OrientationHelper
            public int getMode() {
                return this.mLayoutManager.getHeightMode();
            }

            @Override // com.android.internal.widget.OrientationHelper
            public int getModeInOther() {
                return this.mLayoutManager.getWidthMode();
            }
        };
    }
}
