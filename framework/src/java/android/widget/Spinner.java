package android.widget;

/* loaded from: classes4.dex */
public class Spinner extends android.widget.AbsSpinner implements android.content.DialogInterface.OnClickListener {
    private static final int MAX_ITEMS_MEASURED = 15;
    public static final int MODE_DIALOG = 0;
    public static final int MODE_DROPDOWN = 1;
    private static final int MODE_THEME = -1;
    private static final java.lang.String TAG = "Spinner";
    private boolean mDisableChildrenWhenDisabled;
    int mDropDownWidth;
    private android.widget.ForwardingListener mForwardingListener;
    private int mGravity;
    private android.widget.Spinner.SpinnerPopup mPopup;
    private final android.content.Context mPopupContext;
    private android.widget.SpinnerAdapter mTempAdapter;
    private final android.graphics.Rect mTempRect;

    private interface SpinnerPopup {
        void dismiss();

        android.graphics.drawable.Drawable getBackground();

        java.lang.CharSequence getHintText();

        int getHorizontalOffset();

        int getVerticalOffset();

        boolean isShowing();

        void setAdapter(android.widget.ListAdapter listAdapter);

        void setBackgroundDrawable(android.graphics.drawable.Drawable drawable);

        void setHorizontalOffset(int i);

        void setPromptText(java.lang.CharSequence charSequence);

        void setVerticalOffset(int i);

        void show(int i, int i2);
    }

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<android.widget.Spinner> {
        private int mDropDownHorizontalOffsetId;
        private int mDropDownVerticalOffsetId;
        private int mDropDownWidthId;
        private int mGravityId;
        private int mPopupBackgroundId;
        private int mPromptId;
        private boolean mPropertiesMapped = false;

        @Override // android.view.inspector.InspectionCompanion
        public void mapProperties(android.view.inspector.PropertyMapper propertyMapper) {
            this.mDropDownHorizontalOffsetId = propertyMapper.mapInt("dropDownHorizontalOffset", 16843436);
            this.mDropDownVerticalOffsetId = propertyMapper.mapInt("dropDownVerticalOffset", 16843437);
            this.mDropDownWidthId = propertyMapper.mapInt("dropDownWidth", 16843362);
            this.mGravityId = propertyMapper.mapGravity("gravity", 16842927);
            this.mPopupBackgroundId = propertyMapper.mapObject("popupBackground", 16843126);
            this.mPromptId = propertyMapper.mapObject("prompt", 16843131);
            this.mPropertiesMapped = true;
        }

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(android.widget.Spinner spinner, android.view.inspector.PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new android.view.inspector.InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readInt(this.mDropDownHorizontalOffsetId, spinner.getDropDownHorizontalOffset());
            propertyReader.readInt(this.mDropDownVerticalOffsetId, spinner.getDropDownVerticalOffset());
            propertyReader.readInt(this.mDropDownWidthId, spinner.getDropDownWidth());
            propertyReader.readGravity(this.mGravityId, spinner.getGravity());
            propertyReader.readObject(this.mPopupBackgroundId, spinner.getPopupBackground());
            propertyReader.readObject(this.mPromptId, spinner.getPrompt());
        }
    }

    public Spinner(android.content.Context context) {
        this(context, (android.util.AttributeSet) null);
    }

    public Spinner(android.content.Context context, int i) {
        this(context, null, 16842881, i);
    }

    public Spinner(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 16842881);
    }

    public Spinner(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0, -1);
    }

    public Spinner(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        this(context, attributeSet, i, 0, i2);
    }

    public Spinner(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2, int i3) {
        this(context, attributeSet, i, i2, i3, null);
    }

    public Spinner(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2, int i3, android.content.res.Resources.Theme theme) {
        super(context, attributeSet, i, i2);
        this.mTempRect = new android.graphics.Rect();
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.Spinner, i, i2);
        saveAttributeDataForStyleable(context, com.android.internal.R.styleable.Spinner, attributeSet, obtainStyledAttributes, i, i2);
        if (theme == null) {
            int resourceId = obtainStyledAttributes.getResourceId(7, 0);
            if (resourceId != 0) {
                this.mPopupContext = new android.view.ContextThemeWrapper(context, resourceId);
            } else {
                this.mPopupContext = context;
            }
        } else {
            this.mPopupContext = new android.view.ContextThemeWrapper(context, theme);
        }
        switch (i3 == -1 ? obtainStyledAttributes.getInt(5, 0) : i3) {
            case 0:
                this.mPopup = new android.widget.Spinner.DialogPopup();
                this.mPopup.setPromptText(obtainStyledAttributes.getString(3));
                break;
            case 1:
                final android.widget.Spinner.DropdownPopup dropdownPopup = new android.widget.Spinner.DropdownPopup(this.mPopupContext, attributeSet, i, i2);
                android.content.res.TypedArray obtainStyledAttributes2 = this.mPopupContext.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.Spinner, i, i2);
                this.mDropDownWidth = obtainStyledAttributes2.getLayoutDimension(4, -2);
                if (obtainStyledAttributes2.hasValueOrEmpty(1)) {
                    dropdownPopup.setListSelector(obtainStyledAttributes2.getDrawable(1));
                }
                dropdownPopup.setBackgroundDrawable(obtainStyledAttributes2.getDrawable(2));
                dropdownPopup.setPromptText(obtainStyledAttributes.getString(3));
                obtainStyledAttributes2.recycle();
                this.mPopup = dropdownPopup;
                this.mForwardingListener = new android.widget.ForwardingListener(this) { // from class: android.widget.Spinner.1
                    @Override // android.widget.ForwardingListener
                    public com.android.internal.view.menu.ShowableListMenu getPopup() {
                        return dropdownPopup;
                    }

                    @Override // android.widget.ForwardingListener
                    public boolean onForwardingStarted() {
                        if (!android.widget.Spinner.this.mPopup.isShowing()) {
                            android.widget.Spinner.this.mPopup.show(android.widget.Spinner.this.getTextDirection(), android.widget.Spinner.this.getTextAlignment());
                            return true;
                        }
                        return true;
                    }
                };
                break;
        }
        this.mGravity = obtainStyledAttributes.getInt(0, 17);
        this.mDisableChildrenWhenDisabled = obtainStyledAttributes.getBoolean(8, false);
        obtainStyledAttributes.recycle();
        if (this.mTempAdapter != null) {
            setAdapter(this.mTempAdapter);
            this.mTempAdapter = null;
        }
    }

    public android.content.Context getPopupContext() {
        return this.mPopupContext;
    }

    public void setPopupBackgroundDrawable(android.graphics.drawable.Drawable drawable) {
        if (!(this.mPopup instanceof android.widget.Spinner.DropdownPopup)) {
            android.util.Log.e(TAG, "setPopupBackgroundDrawable: incompatible spinner mode; ignoring...");
        } else {
            this.mPopup.setBackgroundDrawable(drawable);
        }
    }

    public void setPopupBackgroundResource(int i) {
        setPopupBackgroundDrawable(getPopupContext().getDrawable(i));
    }

    public android.graphics.drawable.Drawable getPopupBackground() {
        return this.mPopup.getBackground();
    }

    public boolean isPopupShowing() {
        return this.mPopup != null && this.mPopup.isShowing();
    }

    public void setDropDownVerticalOffset(int i) {
        this.mPopup.setVerticalOffset(i);
    }

    public int getDropDownVerticalOffset() {
        return this.mPopup.getVerticalOffset();
    }

    public void setDropDownHorizontalOffset(int i) {
        this.mPopup.setHorizontalOffset(i);
    }

    public int getDropDownHorizontalOffset() {
        return this.mPopup.getHorizontalOffset();
    }

    public void setDropDownWidth(int i) {
        if (!(this.mPopup instanceof android.widget.Spinner.DropdownPopup)) {
            android.util.Log.e(TAG, "Cannot set dropdown width for MODE_DIALOG, ignoring");
        } else {
            this.mDropDownWidth = i;
        }
    }

    public int getDropDownWidth() {
        return this.mDropDownWidth;
    }

    @Override // android.view.View
    public void setEnabled(boolean z) {
        super.setEnabled(z);
        if (this.mDisableChildrenWhenDisabled) {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                getChildAt(i).setEnabled(z);
            }
        }
    }

    public void setGravity(int i) {
        if (this.mGravity != i) {
            if ((i & 7) == 0) {
                i |= android.view.Gravity.START;
            }
            this.mGravity = i;
            requestLayout();
        }
    }

    public int getGravity() {
        return this.mGravity;
    }

    @Override // android.widget.AbsSpinner, android.widget.AdapterView
    public void setAdapter(android.widget.SpinnerAdapter spinnerAdapter) {
        if (this.mPopup == null) {
            this.mTempAdapter = spinnerAdapter;
            return;
        }
        super.setAdapter(spinnerAdapter);
        this.mRecycler.clear();
        if (this.mContext.getApplicationInfo().targetSdkVersion >= 21 && spinnerAdapter != null && spinnerAdapter.getViewTypeCount() != 1) {
            throw new java.lang.IllegalArgumentException("Spinner adapter view type count must be 1");
        }
        this.mPopup.setAdapter(new android.widget.Spinner.DropDownAdapter(spinnerAdapter, (this.mPopupContext == null ? this.mContext : this.mPopupContext).getTheme()));
    }

    @Override // android.view.View
    public int getBaseline() {
        android.view.View view;
        int baseline;
        if (getChildCount() > 0) {
            view = getChildAt(0);
        } else if (this.mAdapter != null && this.mAdapter.getCount() > 0) {
            view = makeView(0, false);
            this.mRecycler.put(0, view);
        } else {
            view = null;
        }
        if (view == null || (baseline = view.getBaseline()) < 0) {
            return -1;
        }
        return view.getTop() + baseline;
    }

    @Override // android.widget.AdapterView, android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mPopup != null && this.mPopup.isShowing()) {
            this.mPopup.dismiss();
        }
    }

    @Override // android.widget.AdapterView
    public void setOnItemClickListener(android.widget.AdapterView.OnItemClickListener onItemClickListener) {
        throw new java.lang.RuntimeException("setOnItemClickListener cannot be used with a spinner.");
    }

    public void setOnItemClickListenerInt(android.widget.AdapterView.OnItemClickListener onItemClickListener) {
        super.setOnItemClickListener(onItemClickListener);
    }

    @Override // android.view.View
    public boolean onTouchEvent(android.view.MotionEvent motionEvent) {
        if (this.mForwardingListener != null && this.mForwardingListener.onTouch(this, motionEvent)) {
            return true;
        }
        return super.onTouchEvent(motionEvent);
    }

    @Override // android.widget.AbsSpinner, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (this.mPopup == null || android.view.View.MeasureSpec.getMode(i) != Integer.MIN_VALUE) {
            return;
        }
        setMeasuredDimension(java.lang.Math.min(java.lang.Math.max(getMeasuredWidth(), measureContentWidth(getAdapter(), getBackground())), android.view.View.MeasureSpec.getSize(i)), getMeasuredHeight());
    }

    @Override // android.widget.AdapterView, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mInLayout = true;
        layout(0, false);
        this.mInLayout = false;
    }

    @Override // android.widget.AbsSpinner
    void layout(int i, boolean z) {
        int i2 = this.mSpinnerPadding.left;
        int i3 = ((this.mRight - this.mLeft) - this.mSpinnerPadding.left) - this.mSpinnerPadding.right;
        if (this.mDataChanged) {
            handleDataChanged();
        }
        if (this.mItemCount == 0) {
            resetList();
            return;
        }
        if (this.mNextSelectedPosition >= 0) {
            setSelectedPositionInt(this.mNextSelectedPosition);
        }
        recycleAllViews();
        removeAllViewsInLayout();
        this.mFirstPosition = this.mSelectedPosition;
        if (this.mAdapter != null) {
            android.view.View makeView = makeView(this.mSelectedPosition, true);
            int measuredWidth = makeView.getMeasuredWidth();
            switch (android.view.Gravity.getAbsoluteGravity(this.mGravity, getLayoutDirection()) & 7) {
                case 1:
                    i2 = (i2 + (i3 / 2)) - (measuredWidth / 2);
                    break;
                case 5:
                    i2 = (i2 + i3) - measuredWidth;
                    break;
            }
            makeView.offsetLeftAndRight(i2);
        }
        this.mRecycler.clear();
        invalidate();
        checkSelectionChanged();
        this.mDataChanged = false;
        this.mNeedSync = false;
        setNextSelectedPositionInt(this.mSelectedPosition);
    }

    private android.view.View makeView(int i, boolean z) {
        android.view.View view;
        if (!this.mDataChanged && (view = this.mRecycler.get(i)) != null) {
            setUpChild(view, z);
            return view;
        }
        android.view.View view2 = this.mAdapter.getView(i, null, this);
        setUpChild(view2, z);
        return view2;
    }

    private void setUpChild(android.view.View view, boolean z) {
        android.view.ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = generateDefaultLayoutParams();
        }
        addViewInLayout(view, 0, layoutParams);
        view.setSelected(hasFocus());
        if (this.mDisableChildrenWhenDisabled) {
            view.setEnabled(isEnabled());
        }
        view.measure(android.view.ViewGroup.getChildMeasureSpec(this.mWidthMeasureSpec, this.mSpinnerPadding.left + this.mSpinnerPadding.right, layoutParams.width), android.view.ViewGroup.getChildMeasureSpec(this.mHeightMeasureSpec, this.mSpinnerPadding.top + this.mSpinnerPadding.bottom, layoutParams.height));
        int measuredHeight = this.mSpinnerPadding.top + ((((getMeasuredHeight() - this.mSpinnerPadding.bottom) - this.mSpinnerPadding.top) - view.getMeasuredHeight()) / 2);
        view.layout(0, measuredHeight, view.getMeasuredWidth() + 0, view.getMeasuredHeight() + measuredHeight);
        if (!z) {
            removeViewInLayout(view);
        }
    }

    @Override // android.view.View
    public boolean performClick() {
        boolean performClick = super.performClick();
        if (performClick) {
            return performClick;
        }
        if (!this.mPopup.isShowing()) {
            this.mPopup.show(getTextDirection(), getTextAlignment());
        }
        return true;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(android.content.DialogInterface dialogInterface, int i) {
        setSelection(i);
        dialogInterface.dismiss();
    }

    public void onClick(int i) {
        setSelection(i);
        if (this.mPopup != null && this.mPopup.isShowing()) {
            this.mPopup.dismiss();
        }
    }

    @Override // android.widget.AbsSpinner, android.widget.AdapterView, android.view.ViewGroup, android.view.View
    public java.lang.CharSequence getAccessibilityClassName() {
        return android.widget.Spinner.class.getName();
    }

    @Override // android.widget.AdapterView, android.view.ViewGroup, android.view.View
    public void onInitializeAccessibilityNodeInfoInternal(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        if (this.mAdapter != null) {
            accessibilityNodeInfo.setCanOpenPopup(true);
        }
    }

    public void setPrompt(java.lang.CharSequence charSequence) {
        this.mPopup.setPromptText(charSequence);
    }

    public void setPromptId(int i) {
        setPrompt(getContext().getText(i));
    }

    public java.lang.CharSequence getPrompt() {
        return this.mPopup.getHintText();
    }

    int measureContentWidth(android.widget.SpinnerAdapter spinnerAdapter, android.graphics.drawable.Drawable drawable) {
        int i = 0;
        if (spinnerAdapter == null) {
            return 0;
        }
        int makeSafeMeasureSpec = android.view.View.MeasureSpec.makeSafeMeasureSpec(getMeasuredWidth(), 0);
        int makeSafeMeasureSpec2 = android.view.View.MeasureSpec.makeSafeMeasureSpec(getMeasuredHeight(), 0);
        int max = java.lang.Math.max(0, getSelectedItemPosition());
        int min = java.lang.Math.min(spinnerAdapter.getCount(), max + 15);
        android.view.View view = null;
        int i2 = 0;
        for (int max2 = java.lang.Math.max(0, max - (15 - (min - max))); max2 < min; max2++) {
            int itemViewType = spinnerAdapter.getItemViewType(max2);
            if (itemViewType != i) {
                view = null;
                i = itemViewType;
            }
            view = spinnerAdapter.getView(max2, view, this);
            if (view.getLayoutParams() == null) {
                view.setLayoutParams(new android.view.ViewGroup.LayoutParams(-2, -2));
            }
            view.measure(makeSafeMeasureSpec, makeSafeMeasureSpec2);
            i2 = java.lang.Math.max(i2, view.getMeasuredWidth());
        }
        if (drawable != null) {
            drawable.getPadding(this.mTempRect);
            return i2 + this.mTempRect.left + this.mTempRect.right;
        }
        return i2;
    }

    @Override // android.widget.AbsSpinner, android.view.View
    public android.os.Parcelable onSaveInstanceState() {
        android.widget.Spinner.SavedState savedState = new android.widget.Spinner.SavedState(super.onSaveInstanceState());
        savedState.showDropdown = this.mPopup != null && this.mPopup.isShowing();
        return savedState;
    }

    @Override // android.widget.AbsSpinner, android.view.View
    public void onRestoreInstanceState(android.os.Parcelable parcelable) {
        android.view.ViewTreeObserver viewTreeObserver;
        android.widget.Spinner.SavedState savedState = (android.widget.Spinner.SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        if (savedState.showDropdown && (viewTreeObserver = getViewTreeObserver()) != null) {
            viewTreeObserver.addOnGlobalLayoutListener(new android.view.ViewTreeObserver.OnGlobalLayoutListener() { // from class: android.widget.Spinner.2
                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                public void onGlobalLayout() {
                    if (!android.widget.Spinner.this.mPopup.isShowing()) {
                        android.widget.Spinner.this.mPopup.show(android.widget.Spinner.this.getTextDirection(), android.widget.Spinner.this.getTextAlignment());
                    }
                    android.view.ViewTreeObserver viewTreeObserver2 = android.widget.Spinner.this.getViewTreeObserver();
                    if (viewTreeObserver2 != null) {
                        viewTreeObserver2.removeOnGlobalLayoutListener(this);
                    }
                }
            });
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public android.view.PointerIcon onResolvePointerIcon(android.view.MotionEvent motionEvent, int i) {
        int i2;
        if (getPointerIcon() == null && isClickable() && isEnabled() && motionEvent.isFromSource(8194)) {
            if (android.view.flags.Flags.enableArrowIconOnHoverWhenClickable()) {
                i2 = 1000;
            } else {
                i2 = 1002;
            }
            return android.view.PointerIcon.getSystemIcon(getContext(), i2);
        }
        return super.onResolvePointerIcon(motionEvent, i);
    }

    static class SavedState extends android.widget.AbsSpinner.SavedState {
        public static final android.os.Parcelable.Creator<android.widget.Spinner.SavedState> CREATOR = new android.os.Parcelable.Creator<android.widget.Spinner.SavedState>() { // from class: android.widget.Spinner.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.widget.Spinner.SavedState createFromParcel(android.os.Parcel parcel) {
                return new android.widget.Spinner.SavedState(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.widget.Spinner.SavedState[] newArray(int i) {
                return new android.widget.Spinner.SavedState[i];
            }
        };
        boolean showDropdown;

        SavedState(android.os.Parcelable parcelable) {
            super(parcelable);
        }

        private SavedState(android.os.Parcel parcel) {
            super(parcel);
            this.showDropdown = parcel.readByte() != 0;
        }

        @Override // android.widget.AbsSpinner.SavedState, android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeByte(this.showDropdown ? (byte) 1 : (byte) 0);
        }
    }

    private static class DropDownAdapter implements android.widget.ListAdapter, android.widget.SpinnerAdapter {
        private android.widget.SpinnerAdapter mAdapter;
        private android.widget.ListAdapter mListAdapter;

        public DropDownAdapter(android.widget.SpinnerAdapter spinnerAdapter, android.content.res.Resources.Theme theme) {
            this.mAdapter = spinnerAdapter;
            if (spinnerAdapter instanceof android.widget.ListAdapter) {
                this.mListAdapter = (android.widget.ListAdapter) spinnerAdapter;
            }
            if (theme != null && (spinnerAdapter instanceof android.widget.ThemedSpinnerAdapter)) {
                android.widget.ThemedSpinnerAdapter themedSpinnerAdapter = (android.widget.ThemedSpinnerAdapter) spinnerAdapter;
                if (themedSpinnerAdapter.getDropDownViewTheme() == null) {
                    themedSpinnerAdapter.setDropDownViewTheme(theme);
                }
            }
        }

        @Override // android.widget.Adapter
        public int getCount() {
            if (this.mAdapter == null) {
                return 0;
            }
            return this.mAdapter.getCount();
        }

        @Override // android.widget.Adapter
        public java.lang.Object getItem(int i) {
            if (this.mAdapter == null) {
                return null;
            }
            return this.mAdapter.getItem(i);
        }

        @Override // android.widget.Adapter
        public long getItemId(int i) {
            if (this.mAdapter == null) {
                return -1L;
            }
            return this.mAdapter.getItemId(i);
        }

        @Override // android.widget.Adapter
        public android.view.View getView(int i, android.view.View view, android.view.ViewGroup viewGroup) {
            return getDropDownView(i, view, viewGroup);
        }

        @Override // android.widget.SpinnerAdapter
        public android.view.View getDropDownView(int i, android.view.View view, android.view.ViewGroup viewGroup) {
            if (this.mAdapter == null) {
                return null;
            }
            return this.mAdapter.getDropDownView(i, view, viewGroup);
        }

        @Override // android.widget.Adapter
        public boolean hasStableIds() {
            return this.mAdapter != null && this.mAdapter.hasStableIds();
        }

        @Override // android.widget.Adapter
        public void registerDataSetObserver(android.database.DataSetObserver dataSetObserver) {
            if (this.mAdapter != null) {
                this.mAdapter.registerDataSetObserver(dataSetObserver);
            }
        }

        @Override // android.widget.Adapter
        public void unregisterDataSetObserver(android.database.DataSetObserver dataSetObserver) {
            if (this.mAdapter != null) {
                this.mAdapter.unregisterDataSetObserver(dataSetObserver);
            }
        }

        @Override // android.widget.ListAdapter
        public boolean areAllItemsEnabled() {
            android.widget.ListAdapter listAdapter = this.mListAdapter;
            if (listAdapter != null) {
                return listAdapter.areAllItemsEnabled();
            }
            return true;
        }

        @Override // android.widget.ListAdapter
        public boolean isEnabled(int i) {
            android.widget.ListAdapter listAdapter = this.mListAdapter;
            if (listAdapter != null) {
                return listAdapter.isEnabled(i);
            }
            return true;
        }

        @Override // android.widget.Adapter
        public int getItemViewType(int i) {
            return 0;
        }

        @Override // android.widget.Adapter
        public int getViewTypeCount() {
            return 1;
        }

        @Override // android.widget.Adapter
        public boolean isEmpty() {
            return getCount() == 0;
        }
    }

    private class DialogPopup implements android.widget.Spinner.SpinnerPopup, android.content.DialogInterface.OnClickListener {
        private android.widget.ListAdapter mListAdapter;
        private android.app.AlertDialog mPopup;
        private java.lang.CharSequence mPrompt;

        private DialogPopup() {
        }

        @Override // android.widget.Spinner.SpinnerPopup
        public void dismiss() {
            if (this.mPopup != null) {
                this.mPopup.dismiss();
                this.mPopup = null;
            }
        }

        @Override // android.widget.Spinner.SpinnerPopup
        public boolean isShowing() {
            if (this.mPopup != null) {
                return this.mPopup.isShowing();
            }
            return false;
        }

        @Override // android.widget.Spinner.SpinnerPopup
        public void setAdapter(android.widget.ListAdapter listAdapter) {
            this.mListAdapter = listAdapter;
        }

        @Override // android.widget.Spinner.SpinnerPopup
        public void setPromptText(java.lang.CharSequence charSequence) {
            this.mPrompt = charSequence;
        }

        @Override // android.widget.Spinner.SpinnerPopup
        public java.lang.CharSequence getHintText() {
            return this.mPrompt;
        }

        @Override // android.widget.Spinner.SpinnerPopup
        public void show(int i, int i2) {
            if (this.mListAdapter == null) {
                return;
            }
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(android.widget.Spinner.this.getPopupContext());
            if (this.mPrompt != null) {
                builder.setTitle(this.mPrompt);
            }
            this.mPopup = builder.setSingleChoiceItems(this.mListAdapter, android.widget.Spinner.this.getSelectedItemPosition(), this).create();
            android.widget.ListView listView = this.mPopup.getListView();
            listView.setTextDirection(i);
            listView.setTextAlignment(i2);
            this.mPopup.show();
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(android.content.DialogInterface dialogInterface, int i) {
            android.widget.Spinner.this.setSelection(i);
            if (android.widget.Spinner.this.mOnItemClickListener != null) {
                android.widget.Spinner.this.performItemClick(null, i, this.mListAdapter.getItemId(i));
            }
            dismiss();
        }

        @Override // android.widget.Spinner.SpinnerPopup
        public void setBackgroundDrawable(android.graphics.drawable.Drawable drawable) {
            android.util.Log.e(android.widget.Spinner.TAG, "Cannot set popup background for MODE_DIALOG, ignoring");
        }

        @Override // android.widget.Spinner.SpinnerPopup
        public void setVerticalOffset(int i) {
            android.util.Log.e(android.widget.Spinner.TAG, "Cannot set vertical offset for MODE_DIALOG, ignoring");
        }

        @Override // android.widget.Spinner.SpinnerPopup
        public void setHorizontalOffset(int i) {
            android.util.Log.e(android.widget.Spinner.TAG, "Cannot set horizontal offset for MODE_DIALOG, ignoring");
        }

        @Override // android.widget.Spinner.SpinnerPopup
        public android.graphics.drawable.Drawable getBackground() {
            return null;
        }

        @Override // android.widget.Spinner.SpinnerPopup
        public int getVerticalOffset() {
            return 0;
        }

        @Override // android.widget.Spinner.SpinnerPopup
        public int getHorizontalOffset() {
            return 0;
        }
    }

    private class DropdownPopup extends android.widget.ListPopupWindow implements android.widget.Spinner.SpinnerPopup {
        private android.widget.ListAdapter mAdapter;
        private java.lang.CharSequence mHintText;

        public DropdownPopup(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
            super(context, attributeSet, i, i2);
            setAnchorView(android.widget.Spinner.this);
            setModal(true);
            setPromptPosition(0);
            setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() { // from class: android.widget.Spinner.DropdownPopup.1
                @Override // android.widget.AdapterView.OnItemClickListener
                public void onItemClick(android.widget.AdapterView adapterView, android.view.View view, int i3, long j) {
                    android.widget.Spinner.this.setSelection(i3);
                    if (android.widget.Spinner.this.mOnItemClickListener != null) {
                        android.widget.Spinner.this.performItemClick(view, i3, android.widget.Spinner.DropdownPopup.this.mAdapter.getItemId(i3));
                    }
                    android.widget.Spinner.DropdownPopup.this.dismiss();
                }
            });
        }

        @Override // android.widget.ListPopupWindow, android.widget.Spinner.SpinnerPopup
        public void setAdapter(android.widget.ListAdapter listAdapter) {
            super.setAdapter(listAdapter);
            this.mAdapter = listAdapter;
        }

        @Override // android.widget.Spinner.SpinnerPopup
        public java.lang.CharSequence getHintText() {
            return this.mHintText;
        }

        @Override // android.widget.Spinner.SpinnerPopup
        public void setPromptText(java.lang.CharSequence charSequence) {
            this.mHintText = charSequence;
        }

        void computeContentWidth() {
            int i;
            int i2;
            android.graphics.drawable.Drawable background = getBackground();
            if (background != null) {
                background.getPadding(android.widget.Spinner.this.mTempRect);
                i = android.widget.Spinner.this.isLayoutRtl() ? android.widget.Spinner.this.mTempRect.right : -android.widget.Spinner.this.mTempRect.left;
            } else {
                android.graphics.Rect rect = android.widget.Spinner.this.mTempRect;
                android.widget.Spinner.this.mTempRect.right = 0;
                rect.left = 0;
                i = 0;
            }
            int paddingLeft = android.widget.Spinner.this.getPaddingLeft();
            int paddingRight = android.widget.Spinner.this.getPaddingRight();
            int width = android.widget.Spinner.this.getWidth();
            if (android.widget.Spinner.this.mDropDownWidth == -2) {
                int measureContentWidth = android.widget.Spinner.this.measureContentWidth((android.widget.SpinnerAdapter) this.mAdapter, getBackground());
                int i3 = (android.widget.Spinner.this.mContext.getResources().getDisplayMetrics().widthPixels - android.widget.Spinner.this.mTempRect.left) - android.widget.Spinner.this.mTempRect.right;
                if (measureContentWidth > i3) {
                    measureContentWidth = i3;
                }
                setContentWidth(java.lang.Math.max(measureContentWidth, (width - paddingLeft) - paddingRight));
            } else if (android.widget.Spinner.this.mDropDownWidth == -1) {
                setContentWidth((width - paddingLeft) - paddingRight);
            } else {
                setContentWidth(android.widget.Spinner.this.mDropDownWidth);
            }
            if (android.widget.Spinner.this.isLayoutRtl()) {
                i2 = i + ((width - paddingRight) - getWidth());
            } else {
                i2 = i + paddingLeft;
            }
            setHorizontalOffset(i2);
        }

        @Override // android.widget.Spinner.SpinnerPopup
        public void show(int i, int i2) {
            android.view.ViewTreeObserver viewTreeObserver;
            boolean isShowing = isShowing();
            computeContentWidth();
            setInputMethodMode(2);
            super.show();
            android.widget.ListView listView = getListView();
            listView.setChoiceMode(1);
            listView.setTextDirection(i);
            listView.setTextAlignment(i2);
            setSelection(android.widget.Spinner.this.getSelectedItemPosition());
            if (!isShowing && (viewTreeObserver = android.widget.Spinner.this.getViewTreeObserver()) != null) {
                final android.view.ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = new android.view.ViewTreeObserver.OnGlobalLayoutListener() { // from class: android.widget.Spinner.DropdownPopup.2
                    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                    public void onGlobalLayout() {
                        if (!android.widget.Spinner.this.isVisibleToUser()) {
                            android.widget.Spinner.DropdownPopup.this.dismiss();
                        } else {
                            android.widget.Spinner.DropdownPopup.this.computeContentWidth();
                            android.widget.Spinner.DropdownPopup.super.show();
                        }
                    }
                };
                viewTreeObserver.addOnGlobalLayoutListener(onGlobalLayoutListener);
                setOnDismissListener(new android.widget.PopupWindow.OnDismissListener() { // from class: android.widget.Spinner.DropdownPopup.3
                    @Override // android.widget.PopupWindow.OnDismissListener
                    public void onDismiss() {
                        android.view.ViewTreeObserver viewTreeObserver2 = android.widget.Spinner.this.getViewTreeObserver();
                        if (viewTreeObserver2 != null) {
                            viewTreeObserver2.removeOnGlobalLayoutListener(onGlobalLayoutListener);
                        }
                    }
                });
            }
        }
    }
}
