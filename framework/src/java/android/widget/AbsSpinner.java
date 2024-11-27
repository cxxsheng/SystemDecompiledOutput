package android.widget;

/* loaded from: classes4.dex */
public abstract class AbsSpinner extends android.widget.AdapterView<android.widget.SpinnerAdapter> {
    private static final java.lang.String LOG_TAG = android.widget.AbsSpinner.class.getSimpleName();
    android.widget.SpinnerAdapter mAdapter;
    private android.database.DataSetObserver mDataSetObserver;
    int mHeightMeasureSpec;
    final android.widget.AbsSpinner.RecycleBin mRecycler;
    int mSelectionBottomPadding;
    int mSelectionLeftPadding;
    int mSelectionRightPadding;
    int mSelectionTopPadding;
    final android.graphics.Rect mSpinnerPadding;
    private android.graphics.Rect mTouchFrame;
    int mWidthMeasureSpec;

    abstract void layout(int i, boolean z);

    public AbsSpinner(android.content.Context context) {
        super(context);
        this.mSelectionLeftPadding = 0;
        this.mSelectionTopPadding = 0;
        this.mSelectionRightPadding = 0;
        this.mSelectionBottomPadding = 0;
        this.mSpinnerPadding = new android.graphics.Rect();
        this.mRecycler = new android.widget.AbsSpinner.RecycleBin();
        initAbsSpinner();
    }

    public AbsSpinner(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AbsSpinner(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public AbsSpinner(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mSelectionLeftPadding = 0;
        this.mSelectionTopPadding = 0;
        this.mSelectionRightPadding = 0;
        this.mSelectionBottomPadding = 0;
        this.mSpinnerPadding = new android.graphics.Rect();
        this.mRecycler = new android.widget.AbsSpinner.RecycleBin();
        if (getImportantForAutofill() == 0) {
            setImportantForAutofill(1);
        }
        initAbsSpinner();
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.AbsSpinner, i, i2);
        saveAttributeDataForStyleable(context, com.android.internal.R.styleable.AbsSpinner, attributeSet, obtainStyledAttributes, i, i2);
        java.lang.CharSequence[] textArray = obtainStyledAttributes.getTextArray(0);
        if (textArray != null) {
            android.widget.ArrayAdapter arrayAdapter = new android.widget.ArrayAdapter(context, 17367048, textArray);
            arrayAdapter.setDropDownViewResource(17367049);
            setAdapter((android.widget.SpinnerAdapter) arrayAdapter);
        }
        obtainStyledAttributes.recycle();
    }

    private void initAbsSpinner() {
        setFocusable(true);
        setWillNotDraw(false);
    }

    @Override // android.widget.AdapterView
    public void setAdapter(android.widget.SpinnerAdapter spinnerAdapter) {
        if (this.mAdapter != null) {
            this.mAdapter.unregisterDataSetObserver(this.mDataSetObserver);
            resetList();
        }
        this.mAdapter = spinnerAdapter;
        this.mOldSelectedPosition = -1;
        this.mOldSelectedRowId = Long.MIN_VALUE;
        if (this.mAdapter != null) {
            this.mOldItemCount = this.mItemCount;
            this.mItemCount = this.mAdapter.getCount();
            checkFocus();
            this.mDataSetObserver = new android.widget.AdapterView.AdapterDataSetObserver();
            this.mAdapter.registerDataSetObserver(this.mDataSetObserver);
            int i = this.mItemCount > 0 ? 0 : -1;
            setSelectedPositionInt(i);
            setNextSelectedPositionInt(i);
            if (this.mItemCount == 0) {
                checkSelectionChanged();
            }
        } else {
            checkFocus();
            resetList();
            checkSelectionChanged();
        }
        requestLayout();
    }

    void resetList() {
        this.mDataChanged = false;
        this.mNeedSync = false;
        removeAllViewsInLayout();
        this.mOldSelectedPosition = -1;
        this.mOldSelectedRowId = Long.MIN_VALUE;
        setSelectedPositionInt(-1);
        setNextSelectedPositionInt(-1);
        invalidate();
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x00b4  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected void onMeasure(int i, int i2) {
        int i3;
        int i4;
        int mode = android.view.View.MeasureSpec.getMode(i);
        this.mSpinnerPadding.left = this.mPaddingLeft > this.mSelectionLeftPadding ? this.mPaddingLeft : this.mSelectionLeftPadding;
        this.mSpinnerPadding.top = this.mPaddingTop > this.mSelectionTopPadding ? this.mPaddingTop : this.mSelectionTopPadding;
        this.mSpinnerPadding.right = this.mPaddingRight > this.mSelectionRightPadding ? this.mPaddingRight : this.mSelectionRightPadding;
        this.mSpinnerPadding.bottom = this.mPaddingBottom > this.mSelectionBottomPadding ? this.mPaddingBottom : this.mSelectionBottomPadding;
        if (this.mDataChanged) {
            handleDataChanged();
        }
        int selectedItemPosition = getSelectedItemPosition();
        boolean z = true;
        if (selectedItemPosition >= 0 && this.mAdapter != null && selectedItemPosition < this.mAdapter.getCount()) {
            android.view.View view = this.mRecycler.get(selectedItemPosition);
            if (view == null) {
                view = this.mAdapter.getView(selectedItemPosition, null, this);
                if (view.getImportantForAccessibility() == 0) {
                    view.setImportantForAccessibility(1);
                }
            }
            if (view != null) {
                this.mRecycler.put(selectedItemPosition, view);
                if (view.getLayoutParams() == null) {
                    this.mBlockLayoutRequests = true;
                    view.setLayoutParams(generateDefaultLayoutParams());
                    this.mBlockLayoutRequests = false;
                }
                measureChild(view, i, i2);
                i3 = getChildHeight(view) + this.mSpinnerPadding.top + this.mSpinnerPadding.bottom;
                i4 = getChildWidth(view) + this.mSpinnerPadding.left + this.mSpinnerPadding.right;
                z = false;
                if (z) {
                    i3 = this.mSpinnerPadding.top + this.mSpinnerPadding.bottom;
                    if (mode == 0) {
                        i4 = this.mSpinnerPadding.left + this.mSpinnerPadding.right;
                    }
                }
                setMeasuredDimension(resolveSizeAndState(java.lang.Math.max(i4, getSuggestedMinimumWidth()), i, 0), resolveSizeAndState(java.lang.Math.max(i3, getSuggestedMinimumHeight()), i2, 0));
                this.mHeightMeasureSpec = i2;
                this.mWidthMeasureSpec = i;
            }
        }
        i3 = 0;
        i4 = 0;
        if (z) {
        }
        setMeasuredDimension(resolveSizeAndState(java.lang.Math.max(i4, getSuggestedMinimumWidth()), i, 0), resolveSizeAndState(java.lang.Math.max(i3, getSuggestedMinimumHeight()), i2, 0));
        this.mHeightMeasureSpec = i2;
        this.mWidthMeasureSpec = i;
    }

    int getChildHeight(android.view.View view) {
        return view.getMeasuredHeight();
    }

    int getChildWidth(android.view.View view) {
        return view.getMeasuredWidth();
    }

    @Override // android.view.ViewGroup
    protected android.view.ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new android.view.ViewGroup.LayoutParams(-1, -2);
    }

    void recycleAllViews() {
        int childCount = getChildCount();
        android.widget.AbsSpinner.RecycleBin recycleBin = this.mRecycler;
        int i = this.mFirstPosition;
        for (int i2 = 0; i2 < childCount; i2++) {
            recycleBin.put(i + i2, getChildAt(i2));
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:5:0x000f, code lost:
    
        if (r2 <= ((r1.mFirstPosition + getChildCount()) - 1)) goto L9;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void setSelection(int i, boolean z) {
        boolean z2;
        if (z && this.mFirstPosition <= i) {
            z2 = true;
        }
        z2 = false;
        setSelectionInt(i, z2);
    }

    @Override // android.widget.AdapterView
    public void setSelection(int i) {
        setNextSelectedPositionInt(i);
        requestLayout();
        invalidate();
    }

    void setSelectionInt(int i, boolean z) {
        if (i != this.mOldSelectedPosition) {
            this.mBlockLayoutRequests = true;
            int i2 = i - this.mSelectedPosition;
            setNextSelectedPositionInt(i);
            layout(i2, z);
            this.mBlockLayoutRequests = false;
        }
    }

    @Override // android.widget.AdapterView
    public android.view.View getSelectedView() {
        if (this.mItemCount > 0 && this.mSelectedPosition >= 0) {
            return getChildAt(this.mSelectedPosition - this.mFirstPosition);
        }
        return null;
    }

    @Override // android.view.View, android.view.ViewParent
    public void requestLayout() {
        if (!this.mBlockLayoutRequests) {
            super.requestLayout();
        }
    }

    @Override // android.widget.AdapterView
    public android.widget.SpinnerAdapter getAdapter() {
        return this.mAdapter;
    }

    @Override // android.widget.AdapterView
    public int getCount() {
        return this.mItemCount;
    }

    public int pointToPosition(int i, int i2) {
        android.graphics.Rect rect = this.mTouchFrame;
        if (rect == null) {
            this.mTouchFrame = new android.graphics.Rect();
            rect = this.mTouchFrame;
        }
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            android.view.View childAt = getChildAt(childCount);
            if (childAt.getVisibility() == 0) {
                childAt.getHitRect(rect);
                if (rect.contains(i, i2)) {
                    return this.mFirstPosition + childCount;
                }
            }
        }
        return -1;
    }

    @Override // android.widget.AdapterView, android.view.ViewGroup, android.view.View
    protected void dispatchRestoreInstanceState(android.util.SparseArray<android.os.Parcelable> sparseArray) {
        super.dispatchRestoreInstanceState(sparseArray);
        handleDataChanged();
    }

    static class SavedState extends android.view.View.BaseSavedState {
        public static final android.os.Parcelable.Creator<android.widget.AbsSpinner.SavedState> CREATOR = new android.os.Parcelable.Creator<android.widget.AbsSpinner.SavedState>() { // from class: android.widget.AbsSpinner.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.widget.AbsSpinner.SavedState createFromParcel(android.os.Parcel parcel) {
                return new android.widget.AbsSpinner.SavedState(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.widget.AbsSpinner.SavedState[] newArray(int i) {
                return new android.widget.AbsSpinner.SavedState[i];
            }
        };
        int position;
        long selectedId;

        SavedState(android.os.Parcelable parcelable) {
            super(parcelable);
        }

        SavedState(android.os.Parcel parcel) {
            super(parcel);
            this.selectedId = parcel.readLong();
            this.position = parcel.readInt();
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeLong(this.selectedId);
            parcel.writeInt(this.position);
        }

        public java.lang.String toString() {
            return "AbsSpinner.SavedState{" + java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)) + " selectedId=" + this.selectedId + " position=" + this.position + "}";
        }
    }

    @Override // android.view.View
    public android.os.Parcelable onSaveInstanceState() {
        android.widget.AbsSpinner.SavedState savedState = new android.widget.AbsSpinner.SavedState(super.onSaveInstanceState());
        savedState.selectedId = getSelectedItemId();
        if (savedState.selectedId >= 0) {
            savedState.position = getSelectedItemPosition();
        } else {
            savedState.position = -1;
        }
        return savedState;
    }

    @Override // android.view.View
    public void onRestoreInstanceState(android.os.Parcelable parcelable) {
        android.widget.AbsSpinner.SavedState savedState = (android.widget.AbsSpinner.SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        if (savedState.selectedId >= 0) {
            this.mDataChanged = true;
            this.mNeedSync = true;
            this.mSyncRowId = savedState.selectedId;
            this.mSyncPosition = savedState.position;
            this.mSyncMode = 0;
            requestLayout();
        }
    }

    class RecycleBin {
        private final android.util.SparseArray<android.view.View> mScrapHeap = new android.util.SparseArray<>();

        RecycleBin() {
        }

        public void put(int i, android.view.View view) {
            this.mScrapHeap.put(i, view);
        }

        android.view.View get(int i) {
            android.view.View view = this.mScrapHeap.get(i);
            if (view != null) {
                this.mScrapHeap.delete(i);
            }
            return view;
        }

        void clear() {
            android.util.SparseArray<android.view.View> sparseArray = this.mScrapHeap;
            int size = sparseArray.size();
            for (int i = 0; i < size; i++) {
                android.view.View valueAt = sparseArray.valueAt(i);
                if (valueAt != null) {
                    android.widget.AbsSpinner.this.removeDetachedView(valueAt, true);
                }
            }
            sparseArray.clear();
        }
    }

    @Override // android.widget.AdapterView, android.view.ViewGroup, android.view.View
    public java.lang.CharSequence getAccessibilityClassName() {
        return android.widget.AbsSpinner.class.getName();
    }

    @Override // android.view.View
    public void autofill(android.view.autofill.AutofillValue autofillValue) {
        if (isEnabled()) {
            if (!autofillValue.isList()) {
                android.util.Log.w(LOG_TAG, autofillValue + " could not be autofilled into " + this);
            } else {
                setSelection(autofillValue.getListValue());
            }
        }
    }

    @Override // android.view.View
    public int getAutofillType() {
        return isEnabled() ? 3 : 0;
    }

    @Override // android.view.View
    public android.view.autofill.AutofillValue getAutofillValue() {
        if (isEnabled()) {
            return android.view.autofill.AutofillValue.forList(getSelectedItemPosition());
        }
        return null;
    }
}
