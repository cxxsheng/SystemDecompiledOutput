package android.widget;

/* loaded from: classes4.dex */
public class ListPopupWindow implements com.android.internal.view.menu.ShowableListMenu {
    private static final boolean DEBUG = false;
    private static final int EXPAND_LIST_TIMEOUT = 250;
    public static final int INPUT_METHOD_FROM_FOCUSABLE = 0;
    public static final int INPUT_METHOD_NEEDED = 1;
    public static final int INPUT_METHOD_NOT_NEEDED = 2;
    public static final int MATCH_PARENT = -1;
    public static final int POSITION_PROMPT_ABOVE = 0;
    public static final int POSITION_PROMPT_BELOW = 1;
    private static final java.lang.String TAG = "ListPopupWindow";
    public static final int WRAP_CONTENT = -2;
    private android.widget.ListAdapter mAdapter;
    private android.content.Context mContext;
    private boolean mDropDownAlwaysVisible;
    private android.view.View mDropDownAnchorView;
    private int mDropDownGravity;
    private int mDropDownHeight;
    private int mDropDownHorizontalOffset;
    private android.widget.DropDownListView mDropDownList;
    private android.graphics.drawable.Drawable mDropDownListHighlight;
    private int mDropDownVerticalOffset;
    private boolean mDropDownVerticalOffsetSet;
    private int mDropDownWidth;
    private int mDropDownWindowLayoutType;
    private android.graphics.Rect mEpicenterBounds;
    private boolean mForceIgnoreOutsideTouch;
    private final android.os.Handler mHandler;
    private final android.widget.ListPopupWindow.ListSelectorHider mHideSelector;
    private boolean mIsAnimatedFromAnchor;
    private android.widget.AdapterView.OnItemClickListener mItemClickListener;
    private android.widget.AdapterView.OnItemSelectedListener mItemSelectedListener;
    int mListItemExpandMaximum;
    private boolean mModal;
    private android.database.DataSetObserver mObserver;
    private boolean mOverlapAnchor;
    private boolean mOverlapAnchorSet;
    android.widget.PopupWindow mPopup;
    private int mPromptPosition;
    private android.view.View mPromptView;
    private final android.widget.ListPopupWindow.ResizePopupRunnable mResizePopupRunnable;
    private final android.widget.ListPopupWindow.PopupScrollListener mScrollListener;
    private java.lang.Runnable mShowDropDownRunnable;
    private final android.graphics.Rect mTempRect;
    private final android.widget.ListPopupWindow.PopupTouchInterceptor mTouchInterceptor;

    public ListPopupWindow(android.content.Context context) {
        this(context, null, 16843519, 0);
    }

    public ListPopupWindow(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 16843519, 0);
    }

    public ListPopupWindow(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public ListPopupWindow(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        this.mDropDownHeight = -2;
        this.mDropDownWidth = -2;
        this.mDropDownWindowLayoutType = 1002;
        this.mIsAnimatedFromAnchor = true;
        this.mDropDownGravity = 0;
        this.mDropDownAlwaysVisible = false;
        this.mForceIgnoreOutsideTouch = false;
        this.mListItemExpandMaximum = Integer.MAX_VALUE;
        this.mPromptPosition = 0;
        this.mResizePopupRunnable = new android.widget.ListPopupWindow.ResizePopupRunnable();
        this.mTouchInterceptor = new android.widget.ListPopupWindow.PopupTouchInterceptor();
        this.mScrollListener = new android.widget.ListPopupWindow.PopupScrollListener();
        this.mHideSelector = new android.widget.ListPopupWindow.ListSelectorHider();
        this.mTempRect = new android.graphics.Rect();
        this.mContext = context;
        this.mHandler = new android.os.Handler(context.getMainLooper());
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.ListPopupWindow, i, i2);
        this.mDropDownHorizontalOffset = obtainStyledAttributes.getDimensionPixelOffset(0, 0);
        this.mDropDownVerticalOffset = obtainStyledAttributes.getDimensionPixelOffset(1, 0);
        if (this.mDropDownVerticalOffset != 0) {
            this.mDropDownVerticalOffsetSet = true;
        }
        obtainStyledAttributes.recycle();
        this.mPopup = new android.widget.PopupWindow(context, attributeSet, i, i2);
        this.mPopup.setInputMethodMode(1);
    }

    public void setAdapter(android.widget.ListAdapter listAdapter) {
        if (this.mObserver == null) {
            this.mObserver = new android.widget.ListPopupWindow.PopupDataSetObserver();
        } else if (this.mAdapter != null) {
            this.mAdapter.unregisterDataSetObserver(this.mObserver);
        }
        this.mAdapter = listAdapter;
        if (this.mAdapter != null) {
            listAdapter.registerDataSetObserver(this.mObserver);
        }
        if (this.mDropDownList != null) {
            this.mDropDownList.setAdapter(this.mAdapter);
        }
    }

    public void setPromptPosition(int i) {
        this.mPromptPosition = i;
    }

    public int getPromptPosition() {
        return this.mPromptPosition;
    }

    public void setModal(boolean z) {
        this.mModal = z;
        this.mPopup.setFocusable(z);
    }

    public boolean isModal() {
        return this.mModal;
    }

    public void setForceIgnoreOutsideTouch(boolean z) {
        this.mForceIgnoreOutsideTouch = z;
    }

    public void setDropDownAlwaysVisible(boolean z) {
        this.mDropDownAlwaysVisible = z;
    }

    public boolean isDropDownAlwaysVisible() {
        return this.mDropDownAlwaysVisible;
    }

    public void setSoftInputMode(int i) {
        this.mPopup.setSoftInputMode(i);
    }

    public int getSoftInputMode() {
        return this.mPopup.getSoftInputMode();
    }

    public void setListSelector(android.graphics.drawable.Drawable drawable) {
        this.mDropDownListHighlight = drawable;
    }

    public android.graphics.drawable.Drawable getBackground() {
        return this.mPopup.getBackground();
    }

    public void setBackgroundDrawable(android.graphics.drawable.Drawable drawable) {
        this.mPopup.setBackgroundDrawable(drawable);
    }

    public void setAnimationStyle(int i) {
        this.mPopup.setAnimationStyle(i);
    }

    public int getAnimationStyle() {
        return this.mPopup.getAnimationStyle();
    }

    public android.view.View getAnchorView() {
        return this.mDropDownAnchorView;
    }

    public void setAnchorView(android.view.View view) {
        this.mDropDownAnchorView = view;
    }

    public int getHorizontalOffset() {
        return this.mDropDownHorizontalOffset;
    }

    public void setHorizontalOffset(int i) {
        this.mDropDownHorizontalOffset = i;
    }

    public int getVerticalOffset() {
        if (!this.mDropDownVerticalOffsetSet) {
            return 0;
        }
        return this.mDropDownVerticalOffset;
    }

    public void setVerticalOffset(int i) {
        this.mDropDownVerticalOffset = i;
        this.mDropDownVerticalOffsetSet = true;
    }

    public void setEpicenterBounds(android.graphics.Rect rect) {
        this.mEpicenterBounds = rect != null ? new android.graphics.Rect(rect) : null;
    }

    public android.graphics.Rect getEpicenterBounds() {
        if (this.mEpicenterBounds != null) {
            return new android.graphics.Rect(this.mEpicenterBounds);
        }
        return null;
    }

    public void setDropDownGravity(int i) {
        this.mDropDownGravity = i;
    }

    public int getWidth() {
        return this.mDropDownWidth;
    }

    public void setWidth(int i) {
        this.mDropDownWidth = i;
    }

    public void setContentWidth(int i) {
        android.graphics.drawable.Drawable background = this.mPopup.getBackground();
        if (background != null) {
            background.getPadding(this.mTempRect);
            this.mDropDownWidth = this.mTempRect.left + this.mTempRect.right + i;
        } else {
            setWidth(i);
        }
    }

    public int getHeight() {
        return this.mDropDownHeight;
    }

    public void setHeight(int i) {
        if (i < 0 && -2 != i && -1 != i) {
            if (this.mContext.getApplicationInfo().targetSdkVersion < 26) {
                android.util.Log.e(TAG, "Negative value " + i + " passed to ListPopupWindow#setHeight produces undefined results");
            } else {
                throw new java.lang.IllegalArgumentException("Invalid height. Must be a positive value, MATCH_PARENT, or WRAP_CONTENT.");
            }
        }
        this.mDropDownHeight = i;
    }

    public void setWindowLayoutType(int i) {
        this.mDropDownWindowLayoutType = i;
    }

    public void setOnItemClickListener(android.widget.AdapterView.OnItemClickListener onItemClickListener) {
        this.mItemClickListener = onItemClickListener;
    }

    public void setOnItemSelectedListener(android.widget.AdapterView.OnItemSelectedListener onItemSelectedListener) {
        this.mItemSelectedListener = onItemSelectedListener;
    }

    public void setPromptView(android.view.View view) {
        boolean isShowing = isShowing();
        if (isShowing) {
            removePromptView();
        }
        this.mPromptView = view;
        if (isShowing) {
            show();
        }
    }

    public void postShow() {
        this.mHandler.post(this.mShowDropDownRunnable);
    }

    @Override // com.android.internal.view.menu.ShowableListMenu
    public void show() {
        int i;
        int i2;
        int buildDropDown = buildDropDown();
        boolean isInputMethodNotNeeded = isInputMethodNotNeeded();
        this.mPopup.setAllowScrollingAnchorParent(!isInputMethodNotNeeded);
        this.mPopup.setWindowLayoutType(this.mDropDownWindowLayoutType);
        if (this.mPopup.isShowing()) {
            if (!getAnchorView().isAttachedToWindow()) {
                return;
            }
            if (this.mDropDownWidth == -1) {
                i2 = -1;
            } else if (this.mDropDownWidth == -2) {
                i2 = getAnchorView().getWidth();
            } else {
                i2 = this.mDropDownWidth;
            }
            if (this.mDropDownHeight == -1) {
                if (!isInputMethodNotNeeded) {
                    buildDropDown = -1;
                }
                if (isInputMethodNotNeeded) {
                    this.mPopup.setWidth(this.mDropDownWidth == -1 ? -1 : 0);
                    this.mPopup.setHeight(0);
                } else {
                    this.mPopup.setWidth(this.mDropDownWidth == -1 ? -1 : 0);
                    this.mPopup.setHeight(-1);
                }
            } else if (this.mDropDownHeight != -2) {
                buildDropDown = this.mDropDownHeight;
            }
            this.mPopup.setOutsideTouchable((this.mForceIgnoreOutsideTouch || this.mDropDownAlwaysVisible) ? false : true);
            this.mPopup.update(getAnchorView(), this.mDropDownHorizontalOffset, this.mDropDownVerticalOffset, i2 < 0 ? -1 : i2, buildDropDown < 0 ? -1 : buildDropDown);
            this.mPopup.getContentView().restoreDefaultFocus();
            return;
        }
        if (this.mDropDownWidth == -1) {
            i = -1;
        } else if (this.mDropDownWidth == -2) {
            i = getAnchorView().getWidth();
        } else {
            i = this.mDropDownWidth;
        }
        if (this.mDropDownHeight == -1) {
            buildDropDown = -1;
        } else if (this.mDropDownHeight != -2) {
            buildDropDown = this.mDropDownHeight;
        }
        this.mPopup.setWidth(i);
        this.mPopup.setHeight(buildDropDown);
        this.mPopup.setIsClippedToScreen(true);
        this.mPopup.setOutsideTouchable((this.mForceIgnoreOutsideTouch || this.mDropDownAlwaysVisible) ? false : true);
        this.mPopup.setTouchInterceptor(this.mTouchInterceptor);
        this.mPopup.setEpicenterBounds(this.mEpicenterBounds);
        if (this.mOverlapAnchorSet) {
            this.mPopup.setOverlapAnchor(this.mOverlapAnchor);
        }
        this.mPopup.showAsDropDown(getAnchorView(), this.mDropDownHorizontalOffset, this.mDropDownVerticalOffset, this.mDropDownGravity);
        this.mDropDownList.setSelection(-1);
        this.mPopup.getContentView().restoreDefaultFocus();
        if (!this.mModal || this.mDropDownList.isInTouchMode()) {
            clearListSelection();
        }
        if (!this.mModal) {
            this.mHandler.post(this.mHideSelector);
        }
    }

    @Override // com.android.internal.view.menu.ShowableListMenu
    public void dismiss() {
        this.mPopup.dismiss();
        removePromptView();
        this.mPopup.setContentView(null);
        this.mDropDownList = null;
        this.mHandler.removeCallbacks(this.mResizePopupRunnable);
    }

    public void dismissImmediate() {
        this.mPopup.setExitTransition(null);
        dismiss();
    }

    public void setOnDismissListener(android.widget.PopupWindow.OnDismissListener onDismissListener) {
        this.mPopup.setOnDismissListener(onDismissListener);
    }

    private void removePromptView() {
        if (this.mPromptView != null) {
            android.view.ViewParent parent = this.mPromptView.getParent();
            if (parent instanceof android.view.ViewGroup) {
                ((android.view.ViewGroup) parent).removeView(this.mPromptView);
            }
        }
    }

    public void setInputMethodMode(int i) {
        this.mPopup.setInputMethodMode(i);
    }

    public int getInputMethodMode() {
        return this.mPopup.getInputMethodMode();
    }

    public void setSelection(int i) {
        android.widget.DropDownListView dropDownListView = this.mDropDownList;
        if (isShowing() && dropDownListView != null) {
            dropDownListView.setListSelectionHidden(false);
            dropDownListView.setSelection(i);
            if (dropDownListView.getChoiceMode() != 0) {
                dropDownListView.setItemChecked(i, true);
            }
        }
    }

    public void clearListSelection() {
        android.widget.DropDownListView dropDownListView = this.mDropDownList;
        if (dropDownListView != null) {
            dropDownListView.setListSelectionHidden(true);
            dropDownListView.hideSelector();
            dropDownListView.requestLayout();
        }
    }

    @Override // com.android.internal.view.menu.ShowableListMenu
    public boolean isShowing() {
        return this.mPopup.isShowing();
    }

    public boolean isInputMethodNotNeeded() {
        return this.mPopup.getInputMethodMode() == 2;
    }

    public boolean performItemClick(int i) {
        if (isShowing()) {
            if (this.mItemClickListener != null) {
                android.widget.DropDownListView dropDownListView = this.mDropDownList;
                this.mItemClickListener.onItemClick(dropDownListView, dropDownListView.getChildAt(i - dropDownListView.getFirstVisiblePosition()), i, dropDownListView.getAdapter().getItemId(i));
                return true;
            }
            return true;
        }
        return false;
    }

    public java.lang.Object getSelectedItem() {
        if (!isShowing()) {
            return null;
        }
        return this.mDropDownList.getSelectedItem();
    }

    public int getSelectedItemPosition() {
        if (!isShowing()) {
            return -1;
        }
        return this.mDropDownList.getSelectedItemPosition();
    }

    public long getSelectedItemId() {
        if (!isShowing()) {
            return Long.MIN_VALUE;
        }
        return this.mDropDownList.getSelectedItemId();
    }

    public android.view.View getSelectedView() {
        if (!isShowing()) {
            return null;
        }
        return this.mDropDownList.getSelectedView();
    }

    @Override // com.android.internal.view.menu.ShowableListMenu
    public android.widget.ListView getListView() {
        return this.mDropDownList;
    }

    android.widget.DropDownListView createDropDownListView(android.content.Context context, boolean z) {
        return new android.widget.DropDownListView(context, z);
    }

    void setListItemExpandMax(int i) {
        this.mListItemExpandMaximum = i;
    }

    public boolean onKeyDown(int i, android.view.KeyEvent keyEvent) {
        int i2;
        int i3;
        if (isShowing() && i != 62 && (this.mDropDownList.getSelectedItemPosition() >= 0 || !android.view.KeyEvent.isConfirmKey(i))) {
            int selectedItemPosition = this.mDropDownList.getSelectedItemPosition();
            boolean z = !this.mPopup.isAboveAnchor();
            android.widget.ListAdapter listAdapter = this.mAdapter;
            if (listAdapter == null) {
                i2 = Integer.MAX_VALUE;
                i3 = Integer.MIN_VALUE;
            } else {
                boolean areAllItemsEnabled = listAdapter.areAllItemsEnabled();
                i2 = areAllItemsEnabled ? 0 : this.mDropDownList.lookForSelectablePosition(0, true);
                i3 = areAllItemsEnabled ? listAdapter.getCount() - 1 : this.mDropDownList.lookForSelectablePosition(listAdapter.getCount() - 1, false);
            }
            if ((z && i == 19 && selectedItemPosition <= i2) || (!z && i == 20 && selectedItemPosition >= i3)) {
                clearListSelection();
                this.mPopup.setInputMethodMode(1);
                show();
                return true;
            }
            this.mDropDownList.setListSelectionHidden(false);
            if (this.mDropDownList.onKeyDown(i, keyEvent)) {
                this.mPopup.setInputMethodMode(2);
                this.mDropDownList.requestFocusFromTouch();
                show();
                switch (i) {
                    case 19:
                    case 20:
                    case 23:
                    case 66:
                    case 160:
                        break;
                }
                return true;
            }
            if (z && i == 20) {
                if (selectedItemPosition == i3) {
                    return true;
                }
            } else if (!z && i == 19 && selectedItemPosition == i2) {
                return true;
            }
        }
        return false;
    }

    public boolean onKeyUp(int i, android.view.KeyEvent keyEvent) {
        if (isShowing() && this.mDropDownList.getSelectedItemPosition() >= 0) {
            boolean onKeyUp = this.mDropDownList.onKeyUp(i, keyEvent);
            if (onKeyUp && android.view.KeyEvent.isConfirmKey(i)) {
                dismiss();
            }
            return onKeyUp;
        }
        return false;
    }

    public boolean onKeyPreIme(int i, android.view.KeyEvent keyEvent) {
        if ((i == 4 || i == 111) && isShowing()) {
            android.view.View view = this.mDropDownAnchorView;
            if (keyEvent.getAction() == 0 && keyEvent.getRepeatCount() == 0) {
                android.view.KeyEvent.DispatcherState keyDispatcherState = view.getKeyDispatcherState();
                if (keyDispatcherState != null) {
                    keyDispatcherState.startTracking(keyEvent, this);
                }
                return true;
            }
            if (keyEvent.getAction() == 1) {
                android.view.KeyEvent.DispatcherState keyDispatcherState2 = view.getKeyDispatcherState();
                if (keyDispatcherState2 != null) {
                    keyDispatcherState2.handleUpEvent(keyEvent);
                }
                if (keyEvent.isTracking() && !keyEvent.isCanceled()) {
                    dismiss();
                    return true;
                }
                return false;
            }
            return false;
        }
        return false;
    }

    public android.view.View.OnTouchListener createDragToOpenListener(android.view.View view) {
        return new android.widget.ForwardingListener(view) { // from class: android.widget.ListPopupWindow.1
            @Override // android.widget.ForwardingListener
            public com.android.internal.view.menu.ShowableListMenu getPopup() {
                return android.widget.ListPopupWindow.this;
            }
        };
    }

    private int buildDropDown() {
        int i;
        int i2;
        int makeMeasureSpec;
        int i3;
        int i4;
        if (this.mDropDownList == null) {
            android.content.Context context = this.mContext;
            this.mShowDropDownRunnable = new java.lang.Runnable() { // from class: android.widget.ListPopupWindow.2
                @Override // java.lang.Runnable
                public void run() {
                    android.view.View anchorView = android.widget.ListPopupWindow.this.getAnchorView();
                    if (anchorView != null && anchorView.getWindowToken() != null) {
                        android.widget.ListPopupWindow.this.show();
                    }
                }
            };
            this.mDropDownList = createDropDownListView(context, !this.mModal);
            if (this.mDropDownListHighlight != null) {
                this.mDropDownList.setSelector(this.mDropDownListHighlight);
            }
            this.mDropDownList.setAdapter(this.mAdapter);
            this.mDropDownList.setOnItemClickListener(this.mItemClickListener);
            this.mDropDownList.setFocusable(true);
            this.mDropDownList.setFocusableInTouchMode(true);
            this.mDropDownList.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() { // from class: android.widget.ListPopupWindow.3
                @Override // android.widget.AdapterView.OnItemSelectedListener
                public void onItemSelected(android.widget.AdapterView<?> adapterView, android.view.View view, int i5, long j) {
                    android.widget.DropDownListView dropDownListView;
                    if (i5 != -1 && (dropDownListView = android.widget.ListPopupWindow.this.mDropDownList) != null) {
                        dropDownListView.setListSelectionHidden(false);
                    }
                }

                @Override // android.widget.AdapterView.OnItemSelectedListener
                public void onNothingSelected(android.widget.AdapterView<?> adapterView) {
                }
            });
            this.mDropDownList.setOnScrollListener(this.mScrollListener);
            if (this.mItemSelectedListener != null) {
                this.mDropDownList.setOnItemSelectedListener(this.mItemSelectedListener);
            }
            android.view.View view = this.mDropDownList;
            android.view.View view2 = this.mPromptView;
            if (view2 == null) {
                i = 0;
            } else {
                android.widget.LinearLayout linearLayout = new android.widget.LinearLayout(context);
                linearLayout.setOrientation(1);
                android.view.ViewGroup.LayoutParams layoutParams = new android.widget.LinearLayout.LayoutParams(-1, 0, 1.0f);
                switch (this.mPromptPosition) {
                    case 0:
                        linearLayout.addView(view2);
                        linearLayout.addView(view, layoutParams);
                        break;
                    case 1:
                        linearLayout.addView(view, layoutParams);
                        linearLayout.addView(view2);
                        break;
                    default:
                        android.util.Log.e(TAG, "Invalid hint position " + this.mPromptPosition);
                        break;
                }
                if (this.mDropDownWidth >= 0) {
                    i3 = this.mDropDownWidth;
                    i4 = Integer.MIN_VALUE;
                } else {
                    i3 = 0;
                    i4 = 0;
                }
                view2.measure(android.view.View.MeasureSpec.makeMeasureSpec(i3, i4), 0);
                android.widget.LinearLayout.LayoutParams layoutParams2 = (android.widget.LinearLayout.LayoutParams) view2.getLayoutParams();
                i = view2.getMeasuredHeight() + layoutParams2.topMargin + layoutParams2.bottomMargin;
                view = linearLayout;
            }
            this.mPopup.setContentView(view);
        } else {
            android.view.View view3 = this.mPromptView;
            if (view3 == null) {
                i = 0;
            } else {
                android.widget.LinearLayout.LayoutParams layoutParams3 = (android.widget.LinearLayout.LayoutParams) view3.getLayoutParams();
                i = view3.getMeasuredHeight() + layoutParams3.topMargin + layoutParams3.bottomMargin;
            }
        }
        android.graphics.drawable.Drawable background = this.mPopup.getBackground();
        if (background != null) {
            background.getPadding(this.mTempRect);
            i2 = this.mTempRect.top + this.mTempRect.bottom;
            if (!this.mDropDownVerticalOffsetSet) {
                this.mDropDownVerticalOffset = -this.mTempRect.top;
            }
        } else {
            this.mTempRect.setEmpty();
            i2 = 0;
        }
        int maxAvailableHeight = this.mPopup.getMaxAvailableHeight(getAnchorView(), this.mDropDownVerticalOffset, this.mPopup.getInputMethodMode() == 2);
        if (this.mDropDownAlwaysVisible || this.mDropDownHeight == -1) {
            return maxAvailableHeight + i2;
        }
        switch (this.mDropDownWidth) {
            case -2:
                makeMeasureSpec = android.view.View.MeasureSpec.makeMeasureSpec(this.mContext.getResources().getDisplayMetrics().widthPixels - (this.mTempRect.left + this.mTempRect.right), Integer.MIN_VALUE);
                break;
            case -1:
                makeMeasureSpec = android.view.View.MeasureSpec.makeMeasureSpec(this.mContext.getResources().getDisplayMetrics().widthPixels - (this.mTempRect.left + this.mTempRect.right), 1073741824);
                break;
            default:
                makeMeasureSpec = android.view.View.MeasureSpec.makeMeasureSpec(this.mDropDownWidth, 1073741824);
                break;
        }
        int measureHeightOfChildren = this.mDropDownList.measureHeightOfChildren(makeMeasureSpec, 0, -1, maxAvailableHeight - i, -1);
        if (measureHeightOfChildren > 0) {
            i += i2 + this.mDropDownList.getPaddingTop() + this.mDropDownList.getPaddingBottom();
        }
        return measureHeightOfChildren + i;
    }

    public void setOverlapAnchor(boolean z) {
        this.mOverlapAnchorSet = true;
        this.mOverlapAnchor = z;
    }

    private class PopupDataSetObserver extends android.database.DataSetObserver {
        private PopupDataSetObserver() {
        }

        @Override // android.database.DataSetObserver
        public void onChanged() {
            if (android.widget.ListPopupWindow.this.isShowing()) {
                android.widget.ListPopupWindow.this.show();
            }
        }

        @Override // android.database.DataSetObserver
        public void onInvalidated() {
            android.widget.ListPopupWindow.this.dismiss();
        }
    }

    private class ListSelectorHider implements java.lang.Runnable {
        private ListSelectorHider() {
        }

        @Override // java.lang.Runnable
        public void run() {
            android.widget.ListPopupWindow.this.clearListSelection();
        }
    }

    private class ResizePopupRunnable implements java.lang.Runnable {
        private ResizePopupRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (android.widget.ListPopupWindow.this.mDropDownList != null && android.widget.ListPopupWindow.this.mDropDownList.isAttachedToWindow() && android.widget.ListPopupWindow.this.mDropDownList.getCount() > android.widget.ListPopupWindow.this.mDropDownList.getChildCount() && android.widget.ListPopupWindow.this.mDropDownList.getChildCount() <= android.widget.ListPopupWindow.this.mListItemExpandMaximum) {
                android.widget.ListPopupWindow.this.mPopup.setInputMethodMode(2);
                android.widget.ListPopupWindow.this.show();
            }
        }
    }

    private class PopupTouchInterceptor implements android.view.View.OnTouchListener {
        private PopupTouchInterceptor() {
        }

        @Override // android.view.View.OnTouchListener
        public boolean onTouch(android.view.View view, android.view.MotionEvent motionEvent) {
            int action = motionEvent.getAction();
            int x = (int) motionEvent.getX();
            int y = (int) motionEvent.getY();
            if (action == 0 && android.widget.ListPopupWindow.this.mPopup != null && android.widget.ListPopupWindow.this.mPopup.isShowing() && x >= 0 && x < android.widget.ListPopupWindow.this.mPopup.getWidth() && y >= 0 && y < android.widget.ListPopupWindow.this.mPopup.getHeight()) {
                android.widget.ListPopupWindow.this.mHandler.postDelayed(android.widget.ListPopupWindow.this.mResizePopupRunnable, 250L);
                return false;
            }
            if (action == 1) {
                android.widget.ListPopupWindow.this.mHandler.removeCallbacks(android.widget.ListPopupWindow.this.mResizePopupRunnable);
                return false;
            }
            return false;
        }
    }

    private class PopupScrollListener implements android.widget.AbsListView.OnScrollListener {
        private PopupScrollListener() {
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScroll(android.widget.AbsListView absListView, int i, int i2, int i3) {
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScrollStateChanged(android.widget.AbsListView absListView, int i) {
            if (i == 1 && !android.widget.ListPopupWindow.this.isInputMethodNotNeeded() && android.widget.ListPopupWindow.this.mPopup.getContentView() != null) {
                android.widget.ListPopupWindow.this.mHandler.removeCallbacks(android.widget.ListPopupWindow.this.mResizePopupRunnable);
                android.widget.ListPopupWindow.this.mResizePopupRunnable.run();
            }
        }
    }
}
