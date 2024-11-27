package android.inputmethodservice.navigationbar;

/* loaded from: classes2.dex */
class ReverseLinearLayout extends android.widget.LinearLayout {
    private boolean mIsAlternativeOrder;
    private boolean mIsLayoutReverse;

    interface Reversible {
        void reverse(boolean z);
    }

    ReverseLinearLayout(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        updateOrder();
    }

    @Override // android.view.ViewGroup
    public void addView(android.view.View view) {
        reverseParams(view.getLayoutParams(), view, this.mIsLayoutReverse);
        if (this.mIsLayoutReverse) {
            super.addView(view, 0);
        } else {
            super.addView(view);
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewManager
    public void addView(android.view.View view, android.view.ViewGroup.LayoutParams layoutParams) {
        reverseParams(layoutParams, view, this.mIsLayoutReverse);
        if (this.mIsLayoutReverse) {
            super.addView(view, 0, layoutParams);
        } else {
            super.addView(view, layoutParams);
        }
    }

    @Override // android.widget.LinearLayout, android.view.View
    public void onRtlPropertiesChanged(int i) {
        super.onRtlPropertiesChanged(i);
        updateOrder();
    }

    public void setAlternativeOrder(boolean z) {
        this.mIsAlternativeOrder = z;
        updateOrder();
    }

    private void updateOrder() {
        boolean z = (getLayoutDirection() == 1) ^ this.mIsAlternativeOrder;
        if (this.mIsLayoutReverse != z) {
            int childCount = getChildCount();
            java.util.ArrayList arrayList = new java.util.ArrayList(childCount);
            for (int i = 0; i < childCount; i++) {
                arrayList.add(getChildAt(i));
            }
            removeAllViews();
            for (int i2 = childCount - 1; i2 >= 0; i2--) {
                super.addView((android.view.View) arrayList.get(i2));
            }
            this.mIsLayoutReverse = z;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static void reverseParams(android.view.ViewGroup.LayoutParams layoutParams, android.view.View view, boolean z) {
        if (view instanceof android.inputmethodservice.navigationbar.ReverseLinearLayout.Reversible) {
            ((android.inputmethodservice.navigationbar.ReverseLinearLayout.Reversible) view).reverse(z);
        }
        if (view.getPaddingLeft() == view.getPaddingRight() && view.getPaddingTop() == view.getPaddingBottom()) {
            view.setPadding(view.getPaddingTop(), view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingLeft());
        }
        if (layoutParams == null) {
            return;
        }
        int i = layoutParams.width;
        layoutParams.width = layoutParams.height;
        layoutParams.height = i;
    }

    public static class ReverseRelativeLayout extends android.widget.RelativeLayout implements android.inputmethodservice.navigationbar.ReverseLinearLayout.Reversible {
        private int mDefaultGravity;

        ReverseRelativeLayout(android.content.Context context) {
            super(context);
            this.mDefaultGravity = 0;
        }

        @Override // android.inputmethodservice.navigationbar.ReverseLinearLayout.Reversible
        public void reverse(boolean z) {
            updateGravity(z);
            android.inputmethodservice.navigationbar.ReverseLinearLayout.reverseGroup(this, z);
        }

        public void setDefaultGravity(int i) {
            this.mDefaultGravity = i;
        }

        public void updateGravity(boolean z) {
            if (this.mDefaultGravity == 48 || this.mDefaultGravity == 80) {
                int i = this.mDefaultGravity;
                if (z) {
                    i = this.mDefaultGravity != 48 ? 48 : 80;
                }
                if (getGravity() != i) {
                    setGravity(i);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void reverseGroup(android.view.ViewGroup viewGroup, boolean z) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            android.view.View childAt = viewGroup.getChildAt(i);
            reverseParams(childAt.getLayoutParams(), childAt, z);
            if (childAt instanceof android.view.ViewGroup) {
                reverseGroup((android.view.ViewGroup) childAt, z);
            }
        }
    }
}
