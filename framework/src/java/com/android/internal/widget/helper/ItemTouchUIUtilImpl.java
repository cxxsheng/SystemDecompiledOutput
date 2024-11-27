package com.android.internal.widget.helper;

/* loaded from: classes5.dex */
class ItemTouchUIUtilImpl implements com.android.internal.widget.helper.ItemTouchUIUtil {
    ItemTouchUIUtilImpl() {
    }

    @Override // com.android.internal.widget.helper.ItemTouchUIUtil
    public void onDraw(android.graphics.Canvas canvas, com.android.internal.widget.RecyclerView recyclerView, android.view.View view, float f, float f2, int i, boolean z) {
        if (z && view.getTag(com.android.internal.R.id.item_touch_helper_previous_elevation) == null) {
            java.lang.Float valueOf = java.lang.Float.valueOf(view.getElevation());
            view.setElevation(findMaxElevation(recyclerView, view) + 1.0f);
            view.setTag(com.android.internal.R.id.item_touch_helper_previous_elevation, valueOf);
        }
        view.setTranslationX(f);
        view.setTranslationY(f2);
    }

    private float findMaxElevation(com.android.internal.widget.RecyclerView recyclerView, android.view.View view) {
        int childCount = recyclerView.getChildCount();
        float f = 0.0f;
        for (int i = 0; i < childCount; i++) {
            android.view.View childAt = recyclerView.getChildAt(i);
            if (childAt != view) {
                float elevation = childAt.getElevation();
                if (elevation > f) {
                    f = elevation;
                }
            }
        }
        return f;
    }

    @Override // com.android.internal.widget.helper.ItemTouchUIUtil
    public void clearView(android.view.View view) {
        java.lang.Object tag = view.getTag(com.android.internal.R.id.item_touch_helper_previous_elevation);
        if (tag != null && (tag instanceof java.lang.Float)) {
            view.setElevation(((java.lang.Float) tag).floatValue());
        }
        view.setTag(com.android.internal.R.id.item_touch_helper_previous_elevation, null);
        view.setTranslationX(0.0f);
        view.setTranslationY(0.0f);
    }

    @Override // com.android.internal.widget.helper.ItemTouchUIUtil
    public void onSelected(android.view.View view) {
    }

    @Override // com.android.internal.widget.helper.ItemTouchUIUtil
    public void onDrawOver(android.graphics.Canvas canvas, com.android.internal.widget.RecyclerView recyclerView, android.view.View view, float f, float f2, int i, boolean z) {
    }
}
