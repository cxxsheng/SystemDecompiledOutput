package com.android.internal.widget;

/* loaded from: classes5.dex */
public abstract class SimpleItemAnimator extends com.android.internal.widget.RecyclerView.ItemAnimator {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "SimpleItemAnimator";
    boolean mSupportsChangeAnimations = true;

    public abstract boolean animateAdd(com.android.internal.widget.RecyclerView.ViewHolder viewHolder);

    public abstract boolean animateChange(com.android.internal.widget.RecyclerView.ViewHolder viewHolder, com.android.internal.widget.RecyclerView.ViewHolder viewHolder2, int i, int i2, int i3, int i4);

    public abstract boolean animateMove(com.android.internal.widget.RecyclerView.ViewHolder viewHolder, int i, int i2, int i3, int i4);

    public abstract boolean animateRemove(com.android.internal.widget.RecyclerView.ViewHolder viewHolder);

    public boolean getSupportsChangeAnimations() {
        return this.mSupportsChangeAnimations;
    }

    public void setSupportsChangeAnimations(boolean z) {
        this.mSupportsChangeAnimations = z;
    }

    @Override // com.android.internal.widget.RecyclerView.ItemAnimator
    public boolean canReuseUpdatedViewHolder(com.android.internal.widget.RecyclerView.ViewHolder viewHolder) {
        return !this.mSupportsChangeAnimations || viewHolder.isInvalid();
    }

    @Override // com.android.internal.widget.RecyclerView.ItemAnimator
    public boolean animateDisappearance(com.android.internal.widget.RecyclerView.ViewHolder viewHolder, com.android.internal.widget.RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo, com.android.internal.widget.RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo2) {
        int i = itemHolderInfo.left;
        int i2 = itemHolderInfo.top;
        android.view.View view = viewHolder.itemView;
        int left = itemHolderInfo2 == null ? view.getLeft() : itemHolderInfo2.left;
        int top = itemHolderInfo2 == null ? view.getTop() : itemHolderInfo2.top;
        if (!viewHolder.isRemoved() && (i != left || i2 != top)) {
            view.layout(left, top, view.getWidth() + left, view.getHeight() + top);
            return animateMove(viewHolder, i, i2, left, top);
        }
        return animateRemove(viewHolder);
    }

    @Override // com.android.internal.widget.RecyclerView.ItemAnimator
    public boolean animateAppearance(com.android.internal.widget.RecyclerView.ViewHolder viewHolder, com.android.internal.widget.RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo, com.android.internal.widget.RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo2) {
        if (itemHolderInfo != null && (itemHolderInfo.left != itemHolderInfo2.left || itemHolderInfo.top != itemHolderInfo2.top)) {
            return animateMove(viewHolder, itemHolderInfo.left, itemHolderInfo.top, itemHolderInfo2.left, itemHolderInfo2.top);
        }
        return animateAdd(viewHolder);
    }

    @Override // com.android.internal.widget.RecyclerView.ItemAnimator
    public boolean animatePersistence(com.android.internal.widget.RecyclerView.ViewHolder viewHolder, com.android.internal.widget.RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo, com.android.internal.widget.RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo2) {
        if (itemHolderInfo.left != itemHolderInfo2.left || itemHolderInfo.top != itemHolderInfo2.top) {
            return animateMove(viewHolder, itemHolderInfo.left, itemHolderInfo.top, itemHolderInfo2.left, itemHolderInfo2.top);
        }
        dispatchMoveFinished(viewHolder);
        return false;
    }

    @Override // com.android.internal.widget.RecyclerView.ItemAnimator
    public boolean animateChange(com.android.internal.widget.RecyclerView.ViewHolder viewHolder, com.android.internal.widget.RecyclerView.ViewHolder viewHolder2, com.android.internal.widget.RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo, com.android.internal.widget.RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo2) {
        int i;
        int i2;
        int i3 = itemHolderInfo.left;
        int i4 = itemHolderInfo.top;
        if (viewHolder2.shouldIgnore()) {
            int i5 = itemHolderInfo.left;
            i2 = itemHolderInfo.top;
            i = i5;
        } else {
            i = itemHolderInfo2.left;
            i2 = itemHolderInfo2.top;
        }
        return animateChange(viewHolder, viewHolder2, i3, i4, i, i2);
    }

    public final void dispatchRemoveFinished(com.android.internal.widget.RecyclerView.ViewHolder viewHolder) {
        onRemoveFinished(viewHolder);
        dispatchAnimationFinished(viewHolder);
    }

    public final void dispatchMoveFinished(com.android.internal.widget.RecyclerView.ViewHolder viewHolder) {
        onMoveFinished(viewHolder);
        dispatchAnimationFinished(viewHolder);
    }

    public final void dispatchAddFinished(com.android.internal.widget.RecyclerView.ViewHolder viewHolder) {
        onAddFinished(viewHolder);
        dispatchAnimationFinished(viewHolder);
    }

    public final void dispatchChangeFinished(com.android.internal.widget.RecyclerView.ViewHolder viewHolder, boolean z) {
        onChangeFinished(viewHolder, z);
        dispatchAnimationFinished(viewHolder);
    }

    public final void dispatchRemoveStarting(com.android.internal.widget.RecyclerView.ViewHolder viewHolder) {
        onRemoveStarting(viewHolder);
    }

    public final void dispatchMoveStarting(com.android.internal.widget.RecyclerView.ViewHolder viewHolder) {
        onMoveStarting(viewHolder);
    }

    public final void dispatchAddStarting(com.android.internal.widget.RecyclerView.ViewHolder viewHolder) {
        onAddStarting(viewHolder);
    }

    public final void dispatchChangeStarting(com.android.internal.widget.RecyclerView.ViewHolder viewHolder, boolean z) {
        onChangeStarting(viewHolder, z);
    }

    public void onRemoveStarting(com.android.internal.widget.RecyclerView.ViewHolder viewHolder) {
    }

    public void onRemoveFinished(com.android.internal.widget.RecyclerView.ViewHolder viewHolder) {
    }

    public void onAddStarting(com.android.internal.widget.RecyclerView.ViewHolder viewHolder) {
    }

    public void onAddFinished(com.android.internal.widget.RecyclerView.ViewHolder viewHolder) {
    }

    public void onMoveStarting(com.android.internal.widget.RecyclerView.ViewHolder viewHolder) {
    }

    public void onMoveFinished(com.android.internal.widget.RecyclerView.ViewHolder viewHolder) {
    }

    public void onChangeStarting(com.android.internal.widget.RecyclerView.ViewHolder viewHolder, boolean z) {
    }

    public void onChangeFinished(com.android.internal.widget.RecyclerView.ViewHolder viewHolder, boolean z) {
    }
}
