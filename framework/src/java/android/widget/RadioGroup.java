package android.widget;

@android.widget.RemoteViews.RemoteView
/* loaded from: classes4.dex */
public class RadioGroup extends android.widget.LinearLayout {
    private static final java.lang.String LOG_TAG = android.widget.RadioGroup.class.getSimpleName();
    private int mCheckedId;
    private android.widget.CompoundButton.OnCheckedChangeListener mChildOnCheckedChangeListener;
    private int mInitialCheckedId;
    private android.widget.RadioGroup.OnCheckedChangeListener mOnCheckedChangeListener;
    private android.widget.RadioGroup.PassThroughHierarchyChangeListener mPassThroughListener;
    private boolean mProtectFromCheckedChange;

    public interface OnCheckedChangeListener {
        void onCheckedChanged(android.widget.RadioGroup radioGroup, int i);
    }

    public RadioGroup(android.content.Context context) {
        super(context);
        this.mCheckedId = -1;
        this.mProtectFromCheckedChange = false;
        this.mInitialCheckedId = -1;
        setOrientation(1);
        init();
    }

    public RadioGroup(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mCheckedId = -1;
        this.mProtectFromCheckedChange = false;
        this.mInitialCheckedId = -1;
        if (getImportantForAutofill() == 0) {
            setImportantForAutofill(1);
        }
        setImportantForAccessibility(1);
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.RadioGroup, 16842878, 0);
        saveAttributeDataForStyleable(context, com.android.internal.R.styleable.RadioGroup, attributeSet, obtainStyledAttributes, 16842878, 0);
        int resourceId = obtainStyledAttributes.getResourceId(1, -1);
        if (resourceId != -1) {
            this.mCheckedId = resourceId;
            this.mInitialCheckedId = resourceId;
        }
        setOrientation(obtainStyledAttributes.getInt(0, 1));
        obtainStyledAttributes.recycle();
        init();
    }

    private void init() {
        this.mChildOnCheckedChangeListener = new android.widget.RadioGroup.CheckedStateTracker();
        this.mPassThroughListener = new android.widget.RadioGroup.PassThroughHierarchyChangeListener();
        super.setOnHierarchyChangeListener(this.mPassThroughListener);
    }

    @Override // android.view.ViewGroup
    public void setOnHierarchyChangeListener(android.view.ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener) {
        this.mPassThroughListener.mOnHierarchyChangeListener = onHierarchyChangeListener;
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (this.mCheckedId != -1) {
            this.mProtectFromCheckedChange = true;
            setCheckedStateForView(this.mCheckedId, true);
            this.mProtectFromCheckedChange = false;
            setCheckedId(this.mCheckedId);
        }
    }

    @Override // android.view.ViewGroup
    public void addView(android.view.View view, int i, android.view.ViewGroup.LayoutParams layoutParams) {
        if (view instanceof android.widget.RadioButton) {
            android.widget.RadioButton radioButton = (android.widget.RadioButton) view;
            if (radioButton.isChecked()) {
                this.mProtectFromCheckedChange = true;
                if (this.mCheckedId != -1) {
                    setCheckedStateForView(this.mCheckedId, false);
                }
                this.mProtectFromCheckedChange = false;
                setCheckedId(radioButton.getId());
            }
        }
        super.addView(view, i, layoutParams);
    }

    public void check(int i) {
        if (i == -1 || i != this.mCheckedId) {
            if (this.mCheckedId != -1) {
                setCheckedStateForView(this.mCheckedId, false);
            }
            if (i != -1) {
                setCheckedStateForView(i, true);
            }
            setCheckedId(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setCheckedId(int i) {
        android.view.autofill.AutofillManager autofillManager;
        boolean z = i != this.mCheckedId;
        this.mCheckedId = i;
        if (this.mOnCheckedChangeListener != null) {
            this.mOnCheckedChangeListener.onCheckedChanged(this, this.mCheckedId);
        }
        if (z && (autofillManager = (android.view.autofill.AutofillManager) this.mContext.getSystemService(android.view.autofill.AutofillManager.class)) != null) {
            autofillManager.notifyValueChanged(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setCheckedStateForView(int i, boolean z) {
        android.view.View findViewById = findViewById(i);
        if (findViewById != null && (findViewById instanceof android.widget.RadioButton)) {
            ((android.widget.RadioButton) findViewById).setChecked(z);
        }
    }

    public int getCheckedRadioButtonId() {
        return this.mCheckedId;
    }

    public void clearCheck() {
        check(-1);
    }

    public void setOnCheckedChangeListener(android.widget.RadioGroup.OnCheckedChangeListener onCheckedChangeListener) {
        this.mOnCheckedChangeListener = onCheckedChangeListener;
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup
    public android.widget.RadioGroup.LayoutParams generateLayoutParams(android.util.AttributeSet attributeSet) {
        return new android.widget.RadioGroup.LayoutParams(getContext(), attributeSet);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup
    protected boolean checkLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof android.widget.RadioGroup.LayoutParams;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.widget.LinearLayout, android.view.ViewGroup
    public android.widget.LinearLayout.LayoutParams generateDefaultLayoutParams() {
        return new android.widget.RadioGroup.LayoutParams(-2, -2);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public java.lang.CharSequence getAccessibilityClassName() {
        return android.widget.RadioGroup.class.getName();
    }

    public static class LayoutParams extends android.widget.LinearLayout.LayoutParams {
        public LayoutParams(android.content.Context context, android.util.AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
        }

        public LayoutParams(int i, int i2, float f) {
            super(i, i2, f);
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(android.view.ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        @Override // android.view.ViewGroup.LayoutParams
        protected void setBaseAttributes(android.content.res.TypedArray typedArray, int i, int i2) {
            if (typedArray.hasValue(i)) {
                this.width = typedArray.getLayoutDimension(i, "layout_width");
            } else {
                this.width = -2;
            }
            if (typedArray.hasValue(i2)) {
                this.height = typedArray.getLayoutDimension(i2, "layout_height");
            } else {
                this.height = -2;
            }
        }
    }

    private class CheckedStateTracker implements android.widget.CompoundButton.OnCheckedChangeListener {
        private CheckedStateTracker() {
        }

        @Override // android.widget.CompoundButton.OnCheckedChangeListener
        public void onCheckedChanged(android.widget.CompoundButton compoundButton, boolean z) {
            if (android.widget.RadioGroup.this.mProtectFromCheckedChange) {
                return;
            }
            android.widget.RadioGroup.this.mProtectFromCheckedChange = true;
            if (android.widget.RadioGroup.this.mCheckedId != -1) {
                android.widget.RadioGroup.this.setCheckedStateForView(android.widget.RadioGroup.this.mCheckedId, false);
            }
            android.widget.RadioGroup.this.mProtectFromCheckedChange = false;
            android.widget.RadioGroup.this.setCheckedId(compoundButton.getId());
        }
    }

    private class PassThroughHierarchyChangeListener implements android.view.ViewGroup.OnHierarchyChangeListener {
        private android.view.ViewGroup.OnHierarchyChangeListener mOnHierarchyChangeListener;

        private PassThroughHierarchyChangeListener() {
        }

        @Override // android.view.ViewGroup.OnHierarchyChangeListener
        public void onChildViewAdded(android.view.View view, android.view.View view2) {
            if (view == android.widget.RadioGroup.this && (view2 instanceof android.widget.RadioButton)) {
                if (view2.getId() == -1) {
                    view2.setId(android.view.View.generateViewId());
                }
                ((android.widget.RadioButton) view2).setOnCheckedChangeWidgetListener(android.widget.RadioGroup.this.mChildOnCheckedChangeListener);
            }
            if (this.mOnHierarchyChangeListener != null) {
                this.mOnHierarchyChangeListener.onChildViewAdded(view, view2);
            }
        }

        @Override // android.view.ViewGroup.OnHierarchyChangeListener
        public void onChildViewRemoved(android.view.View view, android.view.View view2) {
            if (view == android.widget.RadioGroup.this && (view2 instanceof android.widget.RadioButton)) {
                ((android.widget.RadioButton) view2).setOnCheckedChangeWidgetListener(null);
            }
            if (this.mOnHierarchyChangeListener != null) {
                this.mOnHierarchyChangeListener.onChildViewRemoved(view, view2);
            }
        }
    }

    @Override // android.view.View
    protected void onProvideStructure(android.view.ViewStructure viewStructure, int i, int i2) {
        super.onProvideStructure(viewStructure, i, i2);
        if (i == 1) {
            viewStructure.setDataIsSensitive(this.mCheckedId != this.mInitialCheckedId);
        }
    }

    @Override // android.view.View
    public void autofill(android.view.autofill.AutofillValue autofillValue) {
        if (isEnabled()) {
            if (!autofillValue.isList()) {
                android.util.Log.w(LOG_TAG, autofillValue + " could not be autofilled into " + this);
                return;
            }
            int listValue = autofillValue.getListValue();
            android.view.View childAt = getChildAt(listValue);
            if (childAt == null) {
                android.util.Log.w("View", "RadioGroup.autoFill(): no child with index " + listValue);
            } else {
                check(childAt.getId());
            }
        }
    }

    @Override // android.view.View
    public int getAutofillType() {
        return isEnabled() ? 3 : 0;
    }

    @Override // android.view.View
    public android.view.autofill.AutofillValue getAutofillValue() {
        if (!isEnabled()) {
            return null;
        }
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (getChildAt(i).getId() == this.mCheckedId) {
                return android.view.autofill.AutofillValue.forList(i);
            }
        }
        return null;
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        if (getOrientation() == 0) {
            accessibilityNodeInfo.setCollectionInfo(android.view.accessibility.AccessibilityNodeInfo.CollectionInfo.obtain(1, getVisibleChildWithTextCount(), false, 1));
        } else {
            accessibilityNodeInfo.setCollectionInfo(android.view.accessibility.AccessibilityNodeInfo.CollectionInfo.obtain(getVisibleChildWithTextCount(), 1, false, 1));
        }
    }

    private int getVisibleChildWithTextCount() {
        int i = 0;
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            if ((getChildAt(i2) instanceof android.widget.RadioButton) && isVisibleWithText((android.widget.RadioButton) getChildAt(i2))) {
                i++;
            }
        }
        return i;
    }

    int getIndexWithinVisibleButtons(android.view.View view) {
        if (!(view instanceof android.widget.RadioButton)) {
            return -1;
        }
        int i = 0;
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            if (getChildAt(i2) instanceof android.widget.RadioButton) {
                android.widget.RadioButton radioButton = (android.widget.RadioButton) getChildAt(i2);
                if (radioButton == view) {
                    return i;
                }
                if (isVisibleWithText(radioButton)) {
                    i++;
                }
            }
        }
        return -1;
    }

    private boolean isVisibleWithText(android.widget.RadioButton radioButton) {
        return radioButton.getVisibility() == 0 && !android.text.TextUtils.isEmpty(radioButton.getText());
    }
}
