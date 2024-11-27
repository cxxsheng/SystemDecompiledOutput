package com.android.internal.app;

/* loaded from: classes4.dex */
public class ChooserGridLayoutManager extends com.android.internal.widget.GridLayoutManager {
    private boolean mVerticalScrollEnabled;

    public ChooserGridLayoutManager(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mVerticalScrollEnabled = true;
    }

    public ChooserGridLayoutManager(android.content.Context context, int i) {
        super(context, i);
        this.mVerticalScrollEnabled = true;
    }

    public ChooserGridLayoutManager(android.content.Context context, int i, int i2, boolean z) {
        super(context, i, i2, z);
        this.mVerticalScrollEnabled = true;
    }

    @Override // com.android.internal.widget.GridLayoutManager, com.android.internal.widget.RecyclerView.LayoutManager
    public int getRowCountForAccessibility(com.android.internal.widget.RecyclerView.Recycler recycler, com.android.internal.widget.RecyclerView.State state) {
        return super.getRowCountForAccessibility(recycler, state) - 1;
    }

    void setVerticalScrollEnabled(boolean z) {
        this.mVerticalScrollEnabled = z;
    }

    @Override // com.android.internal.widget.LinearLayoutManager, com.android.internal.widget.RecyclerView.LayoutManager
    public boolean canScrollVertically() {
        return this.mVerticalScrollEnabled && super.canScrollVertically();
    }
}
