package android.widget;

/* loaded from: classes4.dex */
public class NumberPicker extends android.widget.LinearLayout {
    private static final int DEFAULT_LAYOUT_RESOURCE_ID = 17367252;
    private static final long DEFAULT_LONG_PRESS_UPDATE_INTERVAL = 300;
    private static final int SELECTOR_ADJUSTMENT_DURATION_MILLIS = 800;
    private static final int SELECTOR_MAX_FLING_VELOCITY_ADJUSTMENT = 8;
    private static final int SELECTOR_MIDDLE_ITEM_INDEX = 1;
    private static final int SELECTOR_WHEEL_ITEM_COUNT = 3;
    private static final int SIZE_UNSPECIFIED = -1;
    private static final int SNAP_SCROLL_DURATION = 300;
    private static final float TOP_AND_BOTTOM_FADING_EDGE_STRENGTH = 0.9f;
    private static final int UNSCALED_DEFAULT_SELECTION_DIVIDERS_DISTANCE = 48;
    private static final int UNSCALED_DEFAULT_SELECTION_DIVIDER_HEIGHT = 2;
    private android.widget.NumberPicker.AccessibilityNodeProviderImpl mAccessibilityNodeProvider;
    private final android.widget.Scroller mAdjustScroller;
    private android.widget.NumberPicker.BeginSoftInputOnLongPressCommand mBeginSoftInputOnLongPressCommand;
    private int mBottomSelectionDividerBottom;
    private android.widget.NumberPicker.ChangeCurrentByOneFromLongPressCommand mChangeCurrentByOneFromLongPressCommand;
    private final boolean mComputeMaxWidth;
    private int mCurrentScrollOffset;
    private final android.widget.ImageButton mDecrementButton;
    private boolean mDecrementVirtualButtonPressed;
    private java.lang.String[] mDisplayedValues;
    private final android.widget.Scroller mFlingScroller;
    private android.widget.NumberPicker.Formatter mFormatter;
    private final boolean mHasSelectorWheel;
    private boolean mHideWheelUntilFocused;
    private boolean mIgnoreMoveEvents;
    private final android.widget.ImageButton mIncrementButton;
    private boolean mIncrementVirtualButtonPressed;
    private int mInitialScrollOffset;
    private final android.widget.EditText mInputText;
    private long mLastDownEventTime;
    private float mLastDownEventY;
    private float mLastDownOrMoveEventY;
    private int mLastHandledDownDpadKeyCode;
    private int mLastHoveredChildVirtualViewId;
    private long mLongPressUpdateInterval;
    private final int mMaxHeight;
    private int mMaxValue;
    private int mMaxWidth;
    private int mMaximumFlingVelocity;
    private final int mMinHeight;
    private int mMinValue;
    private final int mMinWidth;
    private int mMinimumFlingVelocity;
    private android.widget.NumberPicker.OnScrollListener mOnScrollListener;
    private android.widget.NumberPicker.OnValueChangeListener mOnValueChangeListener;
    private boolean mPerformClickOnTap;
    private final android.widget.NumberPicker.PressedStateHelper mPressedStateHelper;
    private int mPreviousScrollerY;
    private int mScrollState;
    private final android.graphics.drawable.Drawable mSelectionDivider;
    private int mSelectionDividerHeight;
    private final int mSelectionDividersDistance;
    private int mSelectorElementHeight;
    private final android.util.SparseArray<java.lang.String> mSelectorIndexToStringCache;
    private final int[] mSelectorIndices;
    private int mSelectorTextGapHeight;
    private final android.graphics.Paint mSelectorWheelPaint;
    private android.widget.NumberPicker.SetSelectionCommand mSetSelectionCommand;
    private final int mSolidColor;
    private final int mTextSize;
    private int mTopSelectionDividerTop;
    private int mTouchSlop;
    private int mValue;
    private android.view.VelocityTracker mVelocityTracker;
    private final android.graphics.drawable.Drawable mVirtualButtonPressedDrawable;
    private boolean mWrapSelectorWheel;
    private boolean mWrapSelectorWheelPreferred;
    private static final android.widget.NumberPicker.TwoDigitFormatter sTwoDigitFormatter = new android.widget.NumberPicker.TwoDigitFormatter();
    private static final char[] DIGIT_CHARACTERS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 1632, 1633, 1634, 1635, 1636, 1637, 1638, 1639, 1640, 1641, 1776, 1777, 1778, 1779, 1780, 1781, 1782, 1783, 1784, 1785, 2406, 2407, 2408, 2409, 2410, 2411, 2412, 2413, 2414, 2415, 2534, 2535, 2536, 2537, 2538, 2539, 2540, 2541, 2542, 2543, 3302, 3303, 3304, 3305, 3306, 3307, 3308, 3309, 3310, 3311};

    public interface Formatter {
        java.lang.String format(int i);
    }

    public interface OnScrollListener {
        public static final int SCROLL_STATE_FLING = 2;
        public static final int SCROLL_STATE_IDLE = 0;
        public static final int SCROLL_STATE_TOUCH_SCROLL = 1;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface ScrollState {
        }

        void onScrollStateChange(android.widget.NumberPicker numberPicker, int i);
    }

    public interface OnValueChangeListener {
        void onValueChange(android.widget.NumberPicker numberPicker, int i, int i2);
    }

    private static class TwoDigitFormatter implements android.widget.NumberPicker.Formatter {
        java.util.Formatter mFmt;
        char mZeroDigit;
        final java.lang.StringBuilder mBuilder = new java.lang.StringBuilder();
        final java.lang.Object[] mArgs = new java.lang.Object[1];

        TwoDigitFormatter() {
            init(java.util.Locale.getDefault());
        }

        private void init(java.util.Locale locale) {
            this.mFmt = createFormatter(locale);
            this.mZeroDigit = getZeroDigit(locale);
        }

        @Override // android.widget.NumberPicker.Formatter
        public java.lang.String format(int i) {
            java.util.Locale locale = java.util.Locale.getDefault();
            if (this.mZeroDigit != getZeroDigit(locale)) {
                init(locale);
            }
            this.mArgs[0] = java.lang.Integer.valueOf(i);
            this.mBuilder.delete(0, this.mBuilder.length());
            this.mFmt.format("%02d", this.mArgs);
            return this.mFmt.toString();
        }

        private static char getZeroDigit(java.util.Locale locale) {
            return android.icu.text.DecimalFormatSymbols.getInstance(locale).getZeroDigit();
        }

        private java.util.Formatter createFormatter(java.util.Locale locale) {
            return new java.util.Formatter(this.mBuilder, locale);
        }
    }

    public static final android.widget.NumberPicker.Formatter getTwoDigitFormatter() {
        return sTwoDigitFormatter;
    }

    public NumberPicker(android.content.Context context) {
        this(context, null);
    }

    public NumberPicker(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 16844068);
    }

    public NumberPicker(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public NumberPicker(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        boolean z;
        boolean z2;
        this.mWrapSelectorWheelPreferred = true;
        this.mLongPressUpdateInterval = DEFAULT_LONG_PRESS_UPDATE_INTERVAL;
        this.mSelectorIndexToStringCache = new android.util.SparseArray<>();
        this.mSelectorIndices = new int[3];
        this.mInitialScrollOffset = Integer.MIN_VALUE;
        this.mScrollState = 0;
        this.mLastHandledDownDpadKeyCode = -1;
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.NumberPicker, i, i2);
        saveAttributeDataForStyleable(context, com.android.internal.R.styleable.NumberPicker, attributeSet, obtainStyledAttributes, i, i2);
        int resourceId = obtainStyledAttributes.getResourceId(3, 17367252);
        if (resourceId != 17367252) {
            z = true;
        } else {
            z = false;
        }
        this.mHasSelectorWheel = z;
        this.mHideWheelUntilFocused = obtainStyledAttributes.getBoolean(2, false);
        this.mSolidColor = obtainStyledAttributes.getColor(0, 0);
        android.graphics.drawable.Drawable drawable = obtainStyledAttributes.getDrawable(8);
        if (drawable != null) {
            drawable.setCallback(this);
            drawable.setLayoutDirection(getLayoutDirection());
            if (drawable.isStateful()) {
                drawable.setState(getDrawableState());
            }
        }
        this.mSelectionDivider = drawable;
        this.mSelectionDividerHeight = obtainStyledAttributes.getDimensionPixelSize(1, (int) android.util.TypedValue.applyDimension(1, 2.0f, getResources().getDisplayMetrics()));
        this.mSelectionDividersDistance = obtainStyledAttributes.getDimensionPixelSize(9, (int) android.util.TypedValue.applyDimension(1, 48.0f, getResources().getDisplayMetrics()));
        this.mMinHeight = obtainStyledAttributes.getDimensionPixelSize(6, -1);
        this.mMaxHeight = obtainStyledAttributes.getDimensionPixelSize(4, -1);
        if (this.mMinHeight == -1 || this.mMaxHeight == -1 || this.mMinHeight <= this.mMaxHeight) {
            this.mMinWidth = obtainStyledAttributes.getDimensionPixelSize(7, -1);
            this.mMaxWidth = obtainStyledAttributes.getDimensionPixelSize(5, -1);
            if (this.mMinWidth == -1 || this.mMaxWidth == -1 || this.mMinWidth <= this.mMaxWidth) {
                if (this.mMaxWidth == -1) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                this.mComputeMaxWidth = z2;
                this.mVirtualButtonPressedDrawable = obtainStyledAttributes.getDrawable(10);
                obtainStyledAttributes.recycle();
                this.mPressedStateHelper = new android.widget.NumberPicker.PressedStateHelper();
                setWillNotDraw(!this.mHasSelectorWheel);
                ((android.view.LayoutInflater) getContext().getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE)).inflate(resourceId, (android.view.ViewGroup) this, true);
                android.view.View.OnClickListener onClickListener = new android.view.View.OnClickListener() { // from class: android.widget.NumberPicker.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(android.view.View view) {
                        android.widget.NumberPicker.this.hideSoftInput();
                        android.widget.NumberPicker.this.mInputText.clearFocus();
                        if (view.getId() == 16909139) {
                            android.widget.NumberPicker.this.changeValueByOne(true);
                        } else {
                            android.widget.NumberPicker.this.changeValueByOne(false);
                        }
                    }
                };
                android.view.View.OnLongClickListener onLongClickListener = new android.view.View.OnLongClickListener() { // from class: android.widget.NumberPicker.2
                    @Override // android.view.View.OnLongClickListener
                    public boolean onLongClick(android.view.View view) {
                        android.widget.NumberPicker.this.hideSoftInput();
                        android.widget.NumberPicker.this.mInputText.clearFocus();
                        if (view.getId() == 16909139) {
                            android.widget.NumberPicker.this.postChangeCurrentByOneFromLongPress(true, 0L);
                        } else {
                            android.widget.NumberPicker.this.postChangeCurrentByOneFromLongPress(false, 0L);
                        }
                        return true;
                    }
                };
                if (!this.mHasSelectorWheel) {
                    this.mIncrementButton = (android.widget.ImageButton) findViewById(com.android.internal.R.id.increment);
                    this.mIncrementButton.setOnClickListener(onClickListener);
                    this.mIncrementButton.setOnLongClickListener(onLongClickListener);
                } else {
                    this.mIncrementButton = null;
                }
                if (!this.mHasSelectorWheel) {
                    this.mDecrementButton = (android.widget.ImageButton) findViewById(com.android.internal.R.id.decrement);
                    this.mDecrementButton.setOnClickListener(onClickListener);
                    this.mDecrementButton.setOnLongClickListener(onLongClickListener);
                } else {
                    this.mDecrementButton = null;
                }
                this.mInputText = (android.widget.EditText) findViewById(com.android.internal.R.id.numberpicker_input);
                this.mInputText.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() { // from class: android.widget.NumberPicker.3
                    @Override // android.view.View.OnFocusChangeListener
                    public void onFocusChange(android.view.View view, boolean z3) {
                        if (z3) {
                            android.widget.NumberPicker.this.mInputText.selectAll();
                        } else {
                            android.widget.NumberPicker.this.mInputText.setSelection(0, 0);
                            android.widget.NumberPicker.this.validateInputTextView(view);
                        }
                    }
                });
                this.mInputText.setFilters(new android.text.InputFilter[]{new android.widget.NumberPicker.InputTextFilter()});
                this.mInputText.setRawInputType(2);
                this.mInputText.setImeOptions(6);
                android.view.ViewConfiguration viewConfiguration = android.view.ViewConfiguration.get(context);
                this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
                this.mMinimumFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
                this.mMaximumFlingVelocity = viewConfiguration.getScaledMaximumFlingVelocity() / 8;
                this.mTextSize = (int) this.mInputText.getTextSize();
                android.graphics.Paint paint = new android.graphics.Paint();
                paint.setAntiAlias(true);
                paint.setTextAlign(android.graphics.Paint.Align.CENTER);
                paint.setTextSize(this.mTextSize);
                paint.setTypeface(this.mInputText.getTypeface());
                paint.setColor(this.mInputText.getTextColors().getColorForState(ENABLED_STATE_SET, -1));
                this.mSelectorWheelPaint = paint;
                this.mFlingScroller = new android.widget.Scroller(getContext(), null, true);
                this.mAdjustScroller = new android.widget.Scroller(getContext(), new android.view.animation.DecelerateInterpolator(2.5f));
                updateInputTextView();
                if (getImportantForAccessibility() == 0) {
                    setImportantForAccessibility(1);
                }
                if (getFocusable() == 16) {
                    setFocusable(1);
                    setFocusableInTouchMode(true);
                    return;
                }
                return;
            }
            throw new java.lang.IllegalArgumentException("minWidth > maxWidth");
        }
        throw new java.lang.IllegalArgumentException("minHeight > maxHeight");
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (!this.mHasSelectorWheel) {
            super.onLayout(z, i, i2, i3, i4);
            return;
        }
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        int measuredWidth2 = this.mInputText.getMeasuredWidth();
        int measuredHeight2 = this.mInputText.getMeasuredHeight();
        int i5 = (measuredWidth - measuredWidth2) / 2;
        int i6 = (measuredHeight - measuredHeight2) / 2;
        this.mInputText.layout(i5, i6, measuredWidth2 + i5, measuredHeight2 + i6);
        if (z) {
            initializeSelectorWheel();
            initializeFadingEdges();
            this.mTopSelectionDividerTop = ((getHeight() - this.mSelectionDividersDistance) / 2) - this.mSelectionDividerHeight;
            this.mBottomSelectionDividerBottom = this.mTopSelectionDividerTop + (this.mSelectionDividerHeight * 2) + this.mSelectionDividersDistance;
        }
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        if (!this.mHasSelectorWheel) {
            super.onMeasure(i, i2);
        } else {
            super.onMeasure(makeMeasureSpec(i, this.mMaxWidth), makeMeasureSpec(i2, this.mMaxHeight));
            setMeasuredDimension(resolveSizeAndStateRespectingMinSize(this.mMinWidth, getMeasuredWidth(), i), resolveSizeAndStateRespectingMinSize(this.mMinHeight, getMeasuredHeight(), i2));
        }
    }

    private boolean moveToFinalScrollerPosition(android.widget.Scroller scroller) {
        scroller.forceFinished(true);
        int finalY = scroller.getFinalY() - scroller.getCurrY();
        int i = this.mInitialScrollOffset - ((this.mCurrentScrollOffset + finalY) % this.mSelectorElementHeight);
        if (i == 0) {
            return false;
        }
        if (java.lang.Math.abs(i) > this.mSelectorElementHeight / 2) {
            if (i > 0) {
                i -= this.mSelectorElementHeight;
            } else {
                i += this.mSelectorElementHeight;
            }
        }
        scrollBy(0, finalY + i);
        return true;
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(android.view.MotionEvent motionEvent) {
        if (!this.mHasSelectorWheel || !isEnabled()) {
            return false;
        }
        switch (motionEvent.getActionMasked()) {
            case 0:
                removeAllCallbacks();
                hideSoftInput();
                float y = motionEvent.getY();
                this.mLastDownEventY = y;
                this.mLastDownOrMoveEventY = y;
                this.mLastDownEventTime = motionEvent.getEventTime();
                this.mIgnoreMoveEvents = false;
                this.mPerformClickOnTap = false;
                if (this.mLastDownEventY < this.mTopSelectionDividerTop) {
                    if (this.mScrollState == 0) {
                        this.mPressedStateHelper.buttonPressDelayed(2);
                    }
                } else if (this.mLastDownEventY > this.mBottomSelectionDividerBottom && this.mScrollState == 0) {
                    this.mPressedStateHelper.buttonPressDelayed(1);
                }
                getParent().requestDisallowInterceptTouchEvent(true);
                if (!this.mFlingScroller.isFinished()) {
                    this.mFlingScroller.forceFinished(true);
                    this.mAdjustScroller.forceFinished(true);
                    onScrollerFinished(this.mFlingScroller);
                    onScrollStateChange(0);
                } else if (!this.mAdjustScroller.isFinished()) {
                    this.mFlingScroller.forceFinished(true);
                    this.mAdjustScroller.forceFinished(true);
                    onScrollerFinished(this.mAdjustScroller);
                } else if (this.mLastDownEventY < this.mTopSelectionDividerTop) {
                    postChangeCurrentByOneFromLongPress(false, android.view.ViewConfiguration.getLongPressTimeout());
                } else if (this.mLastDownEventY > this.mBottomSelectionDividerBottom) {
                    postChangeCurrentByOneFromLongPress(true, android.view.ViewConfiguration.getLongPressTimeout());
                } else {
                    this.mPerformClickOnTap = true;
                    postBeginSoftInputOnLongPressCommand();
                }
                return true;
            default:
                return false;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00d5, code lost:
    
        return true;
     */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean onTouchEvent(android.view.MotionEvent motionEvent) {
        if (!isEnabled() || !this.mHasSelectorWheel) {
            return false;
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = android.view.VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(motionEvent);
        switch (motionEvent.getActionMasked()) {
            case 1:
                removeBeginSoftInputCommand();
                removeChangeCurrentByOneFromLongPress();
                this.mPressedStateHelper.cancel();
                android.view.VelocityTracker velocityTracker = this.mVelocityTracker;
                velocityTracker.computeCurrentVelocity(1000, this.mMaximumFlingVelocity);
                int yVelocity = (int) velocityTracker.getYVelocity();
                if (java.lang.Math.abs(yVelocity) > this.mMinimumFlingVelocity) {
                    fling(yVelocity);
                    onScrollStateChange(2);
                } else {
                    int y = (int) motionEvent.getY();
                    int abs = (int) java.lang.Math.abs(y - this.mLastDownEventY);
                    long eventTime = motionEvent.getEventTime() - this.mLastDownEventTime;
                    if (abs <= this.mTouchSlop && eventTime < android.view.ViewConfiguration.getTapTimeout()) {
                        if (!this.mPerformClickOnTap) {
                            int i = (y / this.mSelectorElementHeight) - 1;
                            if (i > 0) {
                                changeValueByOne(true);
                                this.mPressedStateHelper.buttonTapped(1);
                            } else if (i < 0) {
                                changeValueByOne(false);
                                this.mPressedStateHelper.buttonTapped(2);
                            }
                        } else {
                            this.mPerformClickOnTap = false;
                            performClick();
                        }
                    } else {
                        ensureScrollWheelAdjusted();
                    }
                    onScrollStateChange(0);
                }
                this.mVelocityTracker.recycle();
                this.mVelocityTracker = null;
                break;
            case 2:
                if (!this.mIgnoreMoveEvents) {
                    float y2 = motionEvent.getY();
                    if (this.mScrollState != 1) {
                        if (((int) java.lang.Math.abs(y2 - this.mLastDownEventY)) > this.mTouchSlop) {
                            removeAllCallbacks();
                            onScrollStateChange(1);
                        }
                    } else {
                        scrollBy(0, (int) (y2 - this.mLastDownOrMoveEventY));
                        invalidate();
                    }
                    this.mLastDownOrMoveEventY = y2;
                    break;
                }
                break;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(android.view.MotionEvent motionEvent) {
        switch (motionEvent.getActionMasked()) {
            case 1:
            case 3:
                removeAllCallbacks();
                break;
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(android.view.KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        switch (keyCode) {
            case 19:
            case 20:
                if (this.mHasSelectorWheel) {
                    switch (keyEvent.getAction()) {
                        case 0:
                            if (this.mWrapSelectorWheel || (keyCode != 20 ? getValue() > getMinValue() : getValue() < getMaxValue())) {
                                requestFocus();
                                this.mLastHandledDownDpadKeyCode = keyCode;
                                removeAllCallbacks();
                                if (this.mFlingScroller.isFinished()) {
                                    changeValueByOne(keyCode == 20);
                                }
                                return true;
                            }
                        case 1:
                            if (this.mLastHandledDownDpadKeyCode == keyCode) {
                                this.mLastHandledDownDpadKeyCode = -1;
                                return true;
                            }
                            break;
                    }
                }
                break;
            case 23:
            case 66:
            case 160:
                removeAllCallbacks();
                break;
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTrackballEvent(android.view.MotionEvent motionEvent) {
        switch (motionEvent.getActionMasked()) {
            case 1:
            case 3:
                removeAllCallbacks();
                break;
        }
        return super.dispatchTrackballEvent(motionEvent);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected boolean dispatchHoverEvent(android.view.MotionEvent motionEvent) {
        int i;
        if (!this.mHasSelectorWheel) {
            return super.dispatchHoverEvent(motionEvent);
        }
        if (android.view.accessibility.AccessibilityManager.getInstance(this.mContext).isEnabled()) {
            int y = (int) motionEvent.getY();
            if (y < this.mTopSelectionDividerTop) {
                i = 3;
            } else if (y > this.mBottomSelectionDividerBottom) {
                i = 1;
            } else {
                i = 2;
            }
            int actionMasked = motionEvent.getActionMasked();
            android.widget.NumberPicker.AccessibilityNodeProviderImpl accessibilityNodeProviderImpl = (android.widget.NumberPicker.AccessibilityNodeProviderImpl) getAccessibilityNodeProvider();
            switch (actionMasked) {
                case 7:
                    if (this.mLastHoveredChildVirtualViewId != i && this.mLastHoveredChildVirtualViewId != -1) {
                        accessibilityNodeProviderImpl.sendAccessibilityEventForVirtualView(this.mLastHoveredChildVirtualViewId, 256);
                        accessibilityNodeProviderImpl.sendAccessibilityEventForVirtualView(i, 128);
                        this.mLastHoveredChildVirtualViewId = i;
                        accessibilityNodeProviderImpl.performAction(i, 64, null);
                        return false;
                    }
                    return false;
                case 8:
                default:
                    return false;
                case 9:
                    accessibilityNodeProviderImpl.sendAccessibilityEventForVirtualView(i, 128);
                    this.mLastHoveredChildVirtualViewId = i;
                    accessibilityNodeProviderImpl.performAction(i, 64, null);
                    return false;
                case 10:
                    accessibilityNodeProviderImpl.sendAccessibilityEventForVirtualView(i, 256);
                    this.mLastHoveredChildVirtualViewId = -1;
                    return false;
            }
        }
        return false;
    }

    @Override // android.view.View
    public void computeScroll() {
        android.widget.Scroller scroller = this.mFlingScroller;
        if (scroller.isFinished()) {
            scroller = this.mAdjustScroller;
            if (scroller.isFinished()) {
                return;
            }
        }
        scroller.computeScrollOffset();
        int currY = scroller.getCurrY();
        if (this.mPreviousScrollerY == 0) {
            this.mPreviousScrollerY = scroller.getStartY();
        }
        scrollBy(0, currY - this.mPreviousScrollerY);
        this.mPreviousScrollerY = currY;
        if (scroller.isFinished()) {
            onScrollerFinished(scroller);
        } else {
            invalidate();
        }
    }

    @Override // android.view.View
    public void setEnabled(boolean z) {
        super.setEnabled(z);
        if (!this.mHasSelectorWheel) {
            this.mIncrementButton.setEnabled(z);
        }
        if (!this.mHasSelectorWheel) {
            this.mDecrementButton.setEnabled(z);
        }
        this.mInputText.setEnabled(z);
    }

    @Override // android.view.View
    public void scrollBy(int i, int i2) {
        int[] iArr = this.mSelectorIndices;
        int i3 = this.mCurrentScrollOffset;
        if (!this.mWrapSelectorWheel && i2 > 0 && iArr[1] <= this.mMinValue) {
            this.mCurrentScrollOffset = this.mInitialScrollOffset;
            return;
        }
        if (!this.mWrapSelectorWheel && i2 < 0 && iArr[1] >= this.mMaxValue) {
            this.mCurrentScrollOffset = this.mInitialScrollOffset;
            return;
        }
        this.mCurrentScrollOffset += i2;
        while (this.mCurrentScrollOffset - this.mInitialScrollOffset > this.mSelectorTextGapHeight) {
            this.mCurrentScrollOffset -= this.mSelectorElementHeight;
            decrementSelectorIndices(iArr);
            setValueInternal(iArr[1], true);
            if (!this.mWrapSelectorWheel && iArr[1] <= this.mMinValue) {
                this.mCurrentScrollOffset = this.mInitialScrollOffset;
            }
        }
        while (this.mCurrentScrollOffset - this.mInitialScrollOffset < (-this.mSelectorTextGapHeight)) {
            this.mCurrentScrollOffset += this.mSelectorElementHeight;
            incrementSelectorIndices(iArr);
            setValueInternal(iArr[1], true);
            if (!this.mWrapSelectorWheel && iArr[1] >= this.mMaxValue) {
                this.mCurrentScrollOffset = this.mInitialScrollOffset;
            }
        }
        if (i3 != this.mCurrentScrollOffset) {
            onScrollChanged(0, this.mCurrentScrollOffset, 0, i3);
        }
    }

    @Override // android.view.View
    protected int computeVerticalScrollOffset() {
        return this.mCurrentScrollOffset;
    }

    @Override // android.view.View
    protected int computeVerticalScrollRange() {
        return ((this.mMaxValue - this.mMinValue) + 1) * this.mSelectorElementHeight;
    }

    @Override // android.view.View
    protected int computeVerticalScrollExtent() {
        return getHeight();
    }

    @Override // android.view.View
    public int getSolidColor() {
        return this.mSolidColor;
    }

    public void setOnValueChangedListener(android.widget.NumberPicker.OnValueChangeListener onValueChangeListener) {
        this.mOnValueChangeListener = onValueChangeListener;
    }

    public void setOnScrollListener(android.widget.NumberPicker.OnScrollListener onScrollListener) {
        this.mOnScrollListener = onScrollListener;
    }

    public void setFormatter(android.widget.NumberPicker.Formatter formatter) {
        if (formatter == this.mFormatter) {
            return;
        }
        this.mFormatter = formatter;
        initializeSelectorWheelIndices();
        updateInputTextView();
    }

    public void setValue(int i) {
        setValueInternal(i, false);
    }

    @Override // android.view.View
    public boolean performClick() {
        if (!this.mHasSelectorWheel) {
            return super.performClick();
        }
        if (!super.performClick()) {
            showSoftInput();
            return true;
        }
        return true;
    }

    @Override // android.view.View
    public boolean performLongClick() {
        if (!this.mHasSelectorWheel) {
            return super.performLongClick();
        }
        if (!super.performLongClick()) {
            showSoftInput();
            this.mIgnoreMoveEvents = true;
        }
        return true;
    }

    private void showSoftInput() {
        android.view.inputmethod.InputMethodManager inputMethodManager = (android.view.inputmethod.InputMethodManager) getContext().getSystemService(android.view.inputmethod.InputMethodManager.class);
        if (inputMethodManager != null) {
            if (this.mHasSelectorWheel) {
                this.mInputText.setVisibility(0);
            }
            this.mInputText.requestFocus();
            inputMethodManager.showSoftInput(this.mInputText, 0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideSoftInput() {
        android.view.inputmethod.InputMethodManager inputMethodManager = (android.view.inputmethod.InputMethodManager) getContext().getSystemService(android.view.inputmethod.InputMethodManager.class);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromView(this.mInputText, 0);
        }
        if (this.mHasSelectorWheel) {
            this.mInputText.setVisibility(4);
        }
    }

    private void tryComputeMaxWidth() {
        int i;
        if (!this.mComputeMaxWidth) {
            return;
        }
        int i2 = 0;
        if (this.mDisplayedValues == null) {
            float f = 0.0f;
            for (int i3 = 0; i3 <= 9; i3++) {
                float measureText = this.mSelectorWheelPaint.measureText(formatNumberWithLocale(i3));
                if (measureText > f) {
                    f = measureText;
                }
            }
            for (int i4 = this.mMaxValue; i4 > 0; i4 /= 10) {
                i2++;
            }
            i = (int) (i2 * f);
        } else {
            int length = this.mDisplayedValues.length;
            int i5 = 0;
            while (i2 < length) {
                float measureText2 = this.mSelectorWheelPaint.measureText(this.mDisplayedValues[i2]);
                if (measureText2 > i5) {
                    i5 = (int) measureText2;
                }
                i2++;
            }
            i = i5;
        }
        int paddingLeft = i + this.mInputText.getPaddingLeft() + this.mInputText.getPaddingRight();
        if (this.mMaxWidth != paddingLeft) {
            if (paddingLeft > this.mMinWidth) {
                this.mMaxWidth = paddingLeft;
            } else {
                this.mMaxWidth = this.mMinWidth;
            }
            invalidate();
        }
    }

    public boolean getWrapSelectorWheel() {
        return this.mWrapSelectorWheel;
    }

    public void setWrapSelectorWheel(boolean z) {
        this.mWrapSelectorWheelPreferred = z;
        updateWrapSelectorWheel();
    }

    private void updateWrapSelectorWheel() {
        this.mWrapSelectorWheel = (this.mMaxValue - this.mMinValue >= this.mSelectorIndices.length) && this.mWrapSelectorWheelPreferred;
    }

    public void setOnLongPressUpdateInterval(long j) {
        this.mLongPressUpdateInterval = j;
    }

    public int getValue() {
        return this.mValue;
    }

    public int getMinValue() {
        return this.mMinValue;
    }

    public void setMinValue(int i) {
        if (this.mMinValue == i) {
            return;
        }
        if (i < 0) {
            throw new java.lang.IllegalArgumentException("minValue must be >= 0");
        }
        this.mMinValue = i;
        if (this.mMinValue > this.mValue) {
            this.mValue = this.mMinValue;
        }
        updateWrapSelectorWheel();
        initializeSelectorWheelIndices();
        updateInputTextView();
        tryComputeMaxWidth();
        invalidate();
    }

    public int getMaxValue() {
        return this.mMaxValue;
    }

    public void setMaxValue(int i) {
        if (this.mMaxValue == i) {
            return;
        }
        if (i < 0) {
            throw new java.lang.IllegalArgumentException("maxValue must be >= 0");
        }
        this.mMaxValue = i;
        if (this.mMaxValue < this.mValue) {
            this.mValue = this.mMaxValue;
        }
        updateWrapSelectorWheel();
        initializeSelectorWheelIndices();
        updateInputTextView();
        tryComputeMaxWidth();
        invalidate();
    }

    public java.lang.String[] getDisplayedValues() {
        return this.mDisplayedValues;
    }

    public void setDisplayedValues(java.lang.String[] strArr) {
        if (this.mDisplayedValues == strArr) {
            return;
        }
        this.mDisplayedValues = strArr;
        if (this.mDisplayedValues != null) {
            this.mInputText.setRawInputType(524289);
        } else {
            this.mInputText.setRawInputType(2);
        }
        updateInputTextView();
        initializeSelectorWheelIndices();
        tryComputeMaxWidth();
    }

    public java.lang.CharSequence getDisplayedValueForCurrentSelection() {
        return this.mSelectorIndexToStringCache.get(getValue());
    }

    public void setSelectionDividerHeight(int i) {
        this.mSelectionDividerHeight = i;
        invalidate();
    }

    public int getSelectionDividerHeight() {
        return this.mSelectionDividerHeight;
    }

    @Override // android.view.View
    protected float getTopFadingEdgeStrength() {
        return TOP_AND_BOTTOM_FADING_EDGE_STRENGTH;
    }

    @Override // android.view.View
    protected float getBottomFadingEdgeStrength() {
        return TOP_AND_BOTTOM_FADING_EDGE_STRENGTH;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeAllCallbacks();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        android.graphics.drawable.Drawable drawable = this.mSelectionDivider;
        if (drawable != null && drawable.isStateful() && drawable.setState(getDrawableState())) {
            invalidateDrawable(drawable);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        if (this.mSelectionDivider != null) {
            this.mSelectionDivider.jumpToCurrentState();
        }
    }

    @Override // android.view.View
    public void onResolveDrawables(int i) {
        super.onResolveDrawables(i);
        if (this.mSelectionDivider != null) {
            this.mSelectionDivider.setLayoutDirection(i);
        }
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onDraw(android.graphics.Canvas canvas) {
        if (!this.mHasSelectorWheel) {
            super.onDraw(canvas);
            return;
        }
        boolean hasFocus = this.mHideWheelUntilFocused ? hasFocus() : true;
        float f = (this.mRight - this.mLeft) / 2;
        float f2 = this.mCurrentScrollOffset;
        if (hasFocus && this.mVirtualButtonPressedDrawable != null && this.mScrollState == 0) {
            if (this.mDecrementVirtualButtonPressed) {
                this.mVirtualButtonPressedDrawable.setState(PRESSED_STATE_SET);
                this.mVirtualButtonPressedDrawable.setBounds(0, 0, this.mRight, this.mTopSelectionDividerTop);
                this.mVirtualButtonPressedDrawable.draw(canvas);
            }
            if (this.mIncrementVirtualButtonPressed) {
                this.mVirtualButtonPressedDrawable.setState(PRESSED_STATE_SET);
                this.mVirtualButtonPressedDrawable.setBounds(0, this.mBottomSelectionDividerBottom, this.mRight, this.mBottom);
                this.mVirtualButtonPressedDrawable.draw(canvas);
            }
        }
        int[] iArr = this.mSelectorIndices;
        for (int i = 0; i < iArr.length; i++) {
            java.lang.String str = this.mSelectorIndexToStringCache.get(iArr[i]);
            if ((hasFocus && i != 1) || (i == 1 && this.mInputText.getVisibility() != 0)) {
                canvas.drawText(str, f, f2, this.mSelectorWheelPaint);
            }
            f2 += this.mSelectorElementHeight;
        }
        if (hasFocus && this.mSelectionDivider != null) {
            int i2 = this.mTopSelectionDividerTop;
            this.mSelectionDivider.setBounds(0, i2, this.mRight, this.mSelectionDividerHeight + i2);
            this.mSelectionDivider.draw(canvas);
            int i3 = this.mBottomSelectionDividerBottom;
            this.mSelectionDivider.setBounds(0, i3 - this.mSelectionDividerHeight, this.mRight, i3);
            this.mSelectionDivider.draw(canvas);
        }
    }

    @Override // android.view.View
    public void onInitializeAccessibilityEventInternal(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEventInternal(accessibilityEvent);
        accessibilityEvent.setClassName(android.widget.NumberPicker.class.getName());
        accessibilityEvent.setScrollable(true);
        accessibilityEvent.setScrollY((this.mMinValue + this.mValue) * this.mSelectorElementHeight);
        accessibilityEvent.setMaxScrollY((this.mMaxValue - this.mMinValue) * this.mSelectorElementHeight);
    }

    @Override // android.view.View
    public android.view.accessibility.AccessibilityNodeProvider getAccessibilityNodeProvider() {
        if (!this.mHasSelectorWheel) {
            return super.getAccessibilityNodeProvider();
        }
        if (this.mAccessibilityNodeProvider == null) {
            this.mAccessibilityNodeProvider = new android.widget.NumberPicker.AccessibilityNodeProviderImpl();
        }
        return this.mAccessibilityNodeProvider;
    }

    public void setTextColor(int i) {
        this.mSelectorWheelPaint.setColor(i);
        this.mInputText.setTextColor(i);
        invalidate();
    }

    public int getTextColor() {
        return this.mSelectorWheelPaint.getColor();
    }

    public void setTextSize(float f) {
        this.mSelectorWheelPaint.setTextSize(f);
        this.mInputText.setTextSize(0, f);
        invalidate();
    }

    public float getTextSize() {
        return this.mSelectorWheelPaint.getTextSize();
    }

    private int makeMeasureSpec(int i, int i2) {
        if (i2 == -1) {
            return i;
        }
        int size = android.view.View.MeasureSpec.getSize(i);
        int mode = android.view.View.MeasureSpec.getMode(i);
        switch (mode) {
            case Integer.MIN_VALUE:
                return android.view.View.MeasureSpec.makeMeasureSpec(java.lang.Math.min(size, i2), 1073741824);
            case 0:
                return android.view.View.MeasureSpec.makeMeasureSpec(i2, 1073741824);
            case 1073741824:
                return i;
            default:
                throw new java.lang.IllegalArgumentException("Unknown measure mode: " + mode);
        }
    }

    private int resolveSizeAndStateRespectingMinSize(int i, int i2, int i3) {
        if (i != -1) {
            return resolveSizeAndState(java.lang.Math.max(i, i2), i3, 0);
        }
        return i2;
    }

    private void initializeSelectorWheelIndices() {
        this.mSelectorIndexToStringCache.clear();
        int[] iArr = this.mSelectorIndices;
        int value = getValue();
        for (int i = 0; i < this.mSelectorIndices.length; i++) {
            int i2 = (i - 1) + value;
            if (this.mWrapSelectorWheel) {
                i2 = getWrappedSelectorIndex(i2);
            }
            iArr[i] = i2;
            ensureCachedScrollSelectorValue(iArr[i]);
        }
    }

    private void setValueInternal(int i, boolean z) {
        int min;
        if (this.mValue == i) {
            return;
        }
        if (this.mWrapSelectorWheel) {
            min = getWrappedSelectorIndex(i);
        } else {
            min = java.lang.Math.min(java.lang.Math.max(i, this.mMinValue), this.mMaxValue);
        }
        int i2 = this.mValue;
        this.mValue = min;
        if (this.mScrollState != 2) {
            updateInputTextView();
        }
        if (z) {
            notifyChange(i2, min);
        }
        initializeSelectorWheelIndices();
        invalidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeValueByOne(boolean z) {
        if (!this.mHasSelectorWheel) {
            if (z) {
                setValueInternal(this.mValue + 1, true);
                return;
            } else {
                setValueInternal(this.mValue - 1, true);
                return;
            }
        }
        hideSoftInput();
        if (!moveToFinalScrollerPosition(this.mFlingScroller)) {
            moveToFinalScrollerPosition(this.mAdjustScroller);
        }
        this.mPreviousScrollerY = 0;
        if (z) {
            this.mFlingScroller.startScroll(0, 0, 0, -this.mSelectorElementHeight, 300);
        } else {
            this.mFlingScroller.startScroll(0, 0, 0, this.mSelectorElementHeight, 300);
        }
        invalidate();
    }

    private void initializeSelectorWheel() {
        initializeSelectorWheelIndices();
        int[] iArr = this.mSelectorIndices;
        this.mSelectorTextGapHeight = (int) ((((this.mBottom - this.mTop) - (iArr.length * this.mTextSize)) / iArr.length) + 0.5f);
        this.mSelectorElementHeight = this.mTextSize + this.mSelectorTextGapHeight;
        this.mInitialScrollOffset = (this.mInputText.getBaseline() + this.mInputText.getTop()) - (this.mSelectorElementHeight * 1);
        this.mCurrentScrollOffset = this.mInitialScrollOffset;
        updateInputTextView();
    }

    private void initializeFadingEdges() {
        setVerticalFadingEdgeEnabled(true);
        setFadingEdgeLength(((this.mBottom - this.mTop) - this.mTextSize) / 2);
    }

    private void onScrollerFinished(android.widget.Scroller scroller) {
        if (scroller == this.mFlingScroller) {
            ensureScrollWheelAdjusted();
            updateInputTextView();
            onScrollStateChange(0);
        } else if (this.mScrollState != 1) {
            updateInputTextView();
        }
    }

    private void onScrollStateChange(int i) {
        if (this.mScrollState == i) {
            return;
        }
        this.mScrollState = i;
        if (this.mOnScrollListener != null) {
            this.mOnScrollListener.onScrollStateChange(this, i);
        }
    }

    private void fling(int i) {
        this.mPreviousScrollerY = 0;
        if (i > 0) {
            this.mFlingScroller.fling(0, 0, 0, i, 0, 0, 0, Integer.MAX_VALUE);
        } else {
            this.mFlingScroller.fling(0, Integer.MAX_VALUE, 0, i, 0, 0, 0, Integer.MAX_VALUE);
        }
        invalidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getWrappedSelectorIndex(int i) {
        if (i > this.mMaxValue) {
            return (this.mMinValue + ((i - this.mMaxValue) % (this.mMaxValue - this.mMinValue))) - 1;
        }
        if (i < this.mMinValue) {
            return (this.mMaxValue - ((this.mMinValue - i) % (this.mMaxValue - this.mMinValue))) + 1;
        }
        return i;
    }

    private void incrementSelectorIndices(int[] iArr) {
        int i = 0;
        while (i < iArr.length - 1) {
            int i2 = i + 1;
            iArr[i] = iArr[i2];
            i = i2;
        }
        int i3 = iArr[iArr.length - 2] + 1;
        if (this.mWrapSelectorWheel && i3 > this.mMaxValue) {
            i3 = this.mMinValue;
        }
        iArr[iArr.length - 1] = i3;
        ensureCachedScrollSelectorValue(i3);
    }

    private void decrementSelectorIndices(int[] iArr) {
        for (int length = iArr.length - 1; length > 0; length--) {
            iArr[length] = iArr[length - 1];
        }
        int i = iArr[1] - 1;
        if (this.mWrapSelectorWheel && i < this.mMinValue) {
            i = this.mMaxValue;
        }
        iArr[0] = i;
        ensureCachedScrollSelectorValue(i);
    }

    private void ensureCachedScrollSelectorValue(int i) {
        java.lang.String str;
        android.util.SparseArray<java.lang.String> sparseArray = this.mSelectorIndexToStringCache;
        if (sparseArray.get(i) != null) {
            return;
        }
        if (i < this.mMinValue || i > this.mMaxValue) {
            str = "";
        } else if (this.mDisplayedValues != null) {
            str = this.mDisplayedValues[i - this.mMinValue];
        } else {
            str = formatNumber(i);
        }
        sparseArray.put(i, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.lang.String formatNumber(int i) {
        return this.mFormatter != null ? this.mFormatter.format(i) : formatNumberWithLocale(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void validateInputTextView(android.view.View view) {
        java.lang.String valueOf = java.lang.String.valueOf(((android.widget.TextView) view).getText());
        if (android.text.TextUtils.isEmpty(valueOf)) {
            updateInputTextView();
        } else {
            setValueInternal(getSelectedPos(valueOf.toString()), true);
        }
    }

    private boolean updateInputTextView() {
        java.lang.String formatNumber = this.mDisplayedValues == null ? formatNumber(this.mValue) : this.mDisplayedValues[this.mValue - this.mMinValue];
        if (!android.text.TextUtils.isEmpty(formatNumber)) {
            android.text.Editable text = this.mInputText.getText();
            if (!formatNumber.equals(text.toString())) {
                this.mInputText.lambda$setTextAsync$0(formatNumber);
                if (android.view.accessibility.AccessibilityManager.getInstance(this.mContext).isEnabled()) {
                    android.view.accessibility.AccessibilityEvent obtain = android.view.accessibility.AccessibilityEvent.obtain(16);
                    this.mInputText.onInitializeAccessibilityEvent(obtain);
                    this.mInputText.onPopulateAccessibilityEvent(obtain);
                    obtain.setFromIndex(0);
                    obtain.setRemovedCount(text.length());
                    obtain.setAddedCount(formatNumber.length());
                    obtain.setBeforeText(text);
                    obtain.setSource(this, 2);
                    requestSendAccessibilityEvent(this, obtain);
                    return true;
                }
                return true;
            }
        }
        return false;
    }

    private void notifyChange(int i, int i2) {
        if (this.mOnValueChangeListener != null) {
            this.mOnValueChangeListener.onValueChange(this, i, this.mValue);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void postChangeCurrentByOneFromLongPress(boolean z, long j) {
        if (this.mChangeCurrentByOneFromLongPressCommand == null) {
            this.mChangeCurrentByOneFromLongPressCommand = new android.widget.NumberPicker.ChangeCurrentByOneFromLongPressCommand();
        } else {
            removeCallbacks(this.mChangeCurrentByOneFromLongPressCommand);
        }
        this.mChangeCurrentByOneFromLongPressCommand.setStep(z);
        postDelayed(this.mChangeCurrentByOneFromLongPressCommand, j);
    }

    private void removeChangeCurrentByOneFromLongPress() {
        if (this.mChangeCurrentByOneFromLongPressCommand != null) {
            removeCallbacks(this.mChangeCurrentByOneFromLongPressCommand);
        }
    }

    private void postBeginSoftInputOnLongPressCommand() {
        if (this.mBeginSoftInputOnLongPressCommand == null) {
            this.mBeginSoftInputOnLongPressCommand = new android.widget.NumberPicker.BeginSoftInputOnLongPressCommand();
        } else {
            removeCallbacks(this.mBeginSoftInputOnLongPressCommand);
        }
        postDelayed(this.mBeginSoftInputOnLongPressCommand, android.view.ViewConfiguration.getLongPressTimeout());
    }

    private void removeBeginSoftInputCommand() {
        if (this.mBeginSoftInputOnLongPressCommand != null) {
            removeCallbacks(this.mBeginSoftInputOnLongPressCommand);
        }
    }

    private void removeAllCallbacks() {
        if (this.mChangeCurrentByOneFromLongPressCommand != null) {
            removeCallbacks(this.mChangeCurrentByOneFromLongPressCommand);
        }
        if (this.mSetSelectionCommand != null) {
            this.mSetSelectionCommand.cancel();
        }
        if (this.mBeginSoftInputOnLongPressCommand != null) {
            removeCallbacks(this.mBeginSoftInputOnLongPressCommand);
        }
        this.mPressedStateHelper.cancel();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getSelectedPos(java.lang.String str) {
        if (this.mDisplayedValues == null) {
            try {
                return java.lang.Integer.parseInt(str);
            } catch (java.lang.NumberFormatException e) {
            }
        } else {
            for (int i = 0; i < this.mDisplayedValues.length; i++) {
                str = str.toLowerCase();
                if (this.mDisplayedValues[i].toLowerCase().startsWith(str)) {
                    return this.mMinValue + i;
                }
            }
            try {
                return java.lang.Integer.parseInt(str);
            } catch (java.lang.NumberFormatException e2) {
            }
        }
        return this.mMinValue;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void postSetSelectionCommand(int i, int i2) {
        if (this.mSetSelectionCommand == null) {
            this.mSetSelectionCommand = new android.widget.NumberPicker.SetSelectionCommand(this.mInputText);
        }
        this.mSetSelectionCommand.post(i, i2);
    }

    class InputTextFilter extends android.text.method.NumberKeyListener {
        InputTextFilter() {
        }

        @Override // android.text.method.KeyListener
        public int getInputType() {
            return 1;
        }

        @Override // android.text.method.NumberKeyListener
        protected char[] getAcceptedChars() {
            return android.widget.NumberPicker.DIGIT_CHARACTERS;
        }

        @Override // android.text.method.NumberKeyListener, android.text.InputFilter
        public java.lang.CharSequence filter(java.lang.CharSequence charSequence, int i, int i2, android.text.Spanned spanned, int i3, int i4) {
            if (android.widget.NumberPicker.this.mSetSelectionCommand != null) {
                android.widget.NumberPicker.this.mSetSelectionCommand.cancel();
            }
            if (android.widget.NumberPicker.this.mDisplayedValues == null) {
                java.lang.CharSequence filter = super.filter(charSequence, i, i2, spanned, i3, i4);
                if (filter == null) {
                    filter = charSequence.subSequence(i, i2);
                }
                java.lang.String str = java.lang.String.valueOf(spanned.subSequence(0, i3)) + ((java.lang.Object) filter) + ((java.lang.Object) spanned.subSequence(i4, spanned.length()));
                if ("".equals(str)) {
                    return str;
                }
                return (android.widget.NumberPicker.this.getSelectedPos(str) > android.widget.NumberPicker.this.mMaxValue || str.length() > java.lang.String.valueOf(android.widget.NumberPicker.this.mMaxValue).length()) ? "" : filter;
            }
            java.lang.String valueOf = java.lang.String.valueOf(charSequence.subSequence(i, i2));
            if (android.text.TextUtils.isEmpty(valueOf)) {
                return "";
            }
            java.lang.String str2 = java.lang.String.valueOf(spanned.subSequence(0, i3)) + ((java.lang.Object) valueOf) + ((java.lang.Object) spanned.subSequence(i4, spanned.length()));
            java.lang.String lowerCase = java.lang.String.valueOf(str2).toLowerCase();
            for (java.lang.String str3 : android.widget.NumberPicker.this.mDisplayedValues) {
                if (str3.toLowerCase().startsWith(lowerCase)) {
                    android.widget.NumberPicker.this.postSetSelectionCommand(str2.length(), str3.length());
                    return str3.subSequence(i3, str3.length());
                }
            }
            return "";
        }
    }

    private boolean ensureScrollWheelAdjusted() {
        int i;
        int i2 = this.mInitialScrollOffset - this.mCurrentScrollOffset;
        if (i2 == 0) {
            return false;
        }
        this.mPreviousScrollerY = 0;
        if (java.lang.Math.abs(i2) <= this.mSelectorElementHeight / 2) {
            i = i2;
        } else {
            int i3 = this.mSelectorElementHeight;
            if (i2 > 0) {
                i3 = -i3;
            }
            i = i2 + i3;
        }
        this.mAdjustScroller.startScroll(0, 0, 0, i, 800);
        invalidate();
        return true;
    }

    class PressedStateHelper implements java.lang.Runnable {
        public static final int BUTTON_DECREMENT = 2;
        public static final int BUTTON_INCREMENT = 1;
        private final int MODE_PRESS = 1;
        private final int MODE_TAPPED = 2;
        private int mManagedButton;
        private int mMode;

        PressedStateHelper() {
        }

        public void cancel() {
            this.mMode = 0;
            this.mManagedButton = 0;
            android.widget.NumberPicker.this.removeCallbacks(this);
            if (android.widget.NumberPicker.this.mIncrementVirtualButtonPressed) {
                android.widget.NumberPicker.this.mIncrementVirtualButtonPressed = false;
                android.widget.NumberPicker.this.invalidate(0, android.widget.NumberPicker.this.mBottomSelectionDividerBottom, android.widget.NumberPicker.this.mRight, android.widget.NumberPicker.this.mBottom);
            }
            android.widget.NumberPicker.this.mDecrementVirtualButtonPressed = false;
            if (android.widget.NumberPicker.this.mDecrementVirtualButtonPressed) {
                android.widget.NumberPicker.this.invalidate(0, 0, android.widget.NumberPicker.this.mRight, android.widget.NumberPicker.this.mTopSelectionDividerTop);
            }
        }

        public void buttonPressDelayed(int i) {
            cancel();
            this.mMode = 1;
            this.mManagedButton = i;
            android.widget.NumberPicker.this.postDelayed(this, android.view.ViewConfiguration.getTapTimeout());
        }

        public void buttonTapped(int i) {
            cancel();
            this.mMode = 2;
            this.mManagedButton = i;
            android.widget.NumberPicker.this.post(this);
        }

        @Override // java.lang.Runnable
        public void run() {
            switch (this.mMode) {
                case 1:
                    switch (this.mManagedButton) {
                        case 1:
                            android.widget.NumberPicker.this.mIncrementVirtualButtonPressed = true;
                            android.widget.NumberPicker.this.invalidate(0, android.widget.NumberPicker.this.mBottomSelectionDividerBottom, android.widget.NumberPicker.this.mRight, android.widget.NumberPicker.this.mBottom);
                            break;
                        case 2:
                            android.widget.NumberPicker.this.mDecrementVirtualButtonPressed = true;
                            android.widget.NumberPicker.this.invalidate(0, 0, android.widget.NumberPicker.this.mRight, android.widget.NumberPicker.this.mTopSelectionDividerTop);
                            break;
                    }
                case 2:
                    switch (this.mManagedButton) {
                        case 1:
                            if (!android.widget.NumberPicker.this.mIncrementVirtualButtonPressed) {
                                android.widget.NumberPicker.this.postDelayed(this, android.view.ViewConfiguration.getPressedStateDuration());
                            }
                            android.widget.NumberPicker numberPicker = android.widget.NumberPicker.this;
                            numberPicker.mIncrementVirtualButtonPressed = true ^ numberPicker.mIncrementVirtualButtonPressed;
                            android.widget.NumberPicker.this.invalidate(0, android.widget.NumberPicker.this.mBottomSelectionDividerBottom, android.widget.NumberPicker.this.mRight, android.widget.NumberPicker.this.mBottom);
                            break;
                        case 2:
                            if (!android.widget.NumberPicker.this.mDecrementVirtualButtonPressed) {
                                android.widget.NumberPicker.this.postDelayed(this, android.view.ViewConfiguration.getPressedStateDuration());
                            }
                            android.widget.NumberPicker numberPicker2 = android.widget.NumberPicker.this;
                            numberPicker2.mDecrementVirtualButtonPressed = true ^ numberPicker2.mDecrementVirtualButtonPressed;
                            android.widget.NumberPicker.this.invalidate(0, 0, android.widget.NumberPicker.this.mRight, android.widget.NumberPicker.this.mTopSelectionDividerTop);
                            break;
                    }
            }
        }
    }

    private static class SetSelectionCommand implements java.lang.Runnable {
        private final android.widget.EditText mInputText;
        private boolean mPosted;
        private int mSelectionEnd;
        private int mSelectionStart;

        public SetSelectionCommand(android.widget.EditText editText) {
            this.mInputText = editText;
        }

        public void post(int i, int i2) {
            this.mSelectionStart = i;
            this.mSelectionEnd = i2;
            if (!this.mPosted) {
                this.mInputText.post(this);
                this.mPosted = true;
            }
        }

        public void cancel() {
            if (this.mPosted) {
                this.mInputText.removeCallbacks(this);
                this.mPosted = false;
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            this.mPosted = false;
            this.mInputText.setSelection(this.mSelectionStart, this.mSelectionEnd);
        }
    }

    class ChangeCurrentByOneFromLongPressCommand implements java.lang.Runnable {
        private boolean mIncrement;

        ChangeCurrentByOneFromLongPressCommand() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setStep(boolean z) {
            this.mIncrement = z;
        }

        @Override // java.lang.Runnable
        public void run() {
            android.widget.NumberPicker.this.changeValueByOne(this.mIncrement);
            android.widget.NumberPicker.this.postDelayed(this, android.widget.NumberPicker.this.mLongPressUpdateInterval);
        }
    }

    public static class CustomEditText extends android.widget.EditText {
        public CustomEditText(android.content.Context context, android.util.AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        @Override // android.widget.TextView
        public void onEditorAction(int i) {
            super.onEditorAction(i);
            if (i == 6) {
                clearFocus();
            }
        }
    }

    class BeginSoftInputOnLongPressCommand implements java.lang.Runnable {
        BeginSoftInputOnLongPressCommand() {
        }

        @Override // java.lang.Runnable
        public void run() {
            android.widget.NumberPicker.this.performLongClick();
        }
    }

    class AccessibilityNodeProviderImpl extends android.view.accessibility.AccessibilityNodeProvider {
        private static final int UNDEFINED = Integer.MIN_VALUE;
        private static final int VIRTUAL_VIEW_ID_DECREMENT = 3;
        private static final int VIRTUAL_VIEW_ID_INCREMENT = 1;
        private static final int VIRTUAL_VIEW_ID_INPUT = 2;
        private final android.graphics.Rect mTempRect = new android.graphics.Rect();
        private final int[] mTempArray = new int[2];
        private int mAccessibilityFocusedView = Integer.MIN_VALUE;

        AccessibilityNodeProviderImpl() {
        }

        @Override // android.view.accessibility.AccessibilityNodeProvider
        public android.view.accessibility.AccessibilityNodeInfo createAccessibilityNodeInfo(int i) {
            switch (i) {
                case -1:
                    return createAccessibilityNodeInfoForNumberPicker(android.widget.NumberPicker.this.mScrollX, android.widget.NumberPicker.this.mScrollY, android.widget.NumberPicker.this.mScrollX + (android.widget.NumberPicker.this.mRight - android.widget.NumberPicker.this.mLeft), android.widget.NumberPicker.this.mScrollY + (android.widget.NumberPicker.this.mBottom - android.widget.NumberPicker.this.mTop));
                case 0:
                default:
                    return super.createAccessibilityNodeInfo(i);
                case 1:
                    return createAccessibilityNodeInfoForVirtualButton(1, getVirtualIncrementButtonText(), android.widget.NumberPicker.this.mScrollX, android.widget.NumberPicker.this.mBottomSelectionDividerBottom - android.widget.NumberPicker.this.mSelectionDividerHeight, android.widget.NumberPicker.this.mScrollX + (android.widget.NumberPicker.this.mRight - android.widget.NumberPicker.this.mLeft), android.widget.NumberPicker.this.mScrollY + (android.widget.NumberPicker.this.mBottom - android.widget.NumberPicker.this.mTop));
                case 2:
                    return createAccessibiltyNodeInfoForInputText(android.widget.NumberPicker.this.mScrollX, android.widget.NumberPicker.this.mTopSelectionDividerTop + android.widget.NumberPicker.this.mSelectionDividerHeight, android.widget.NumberPicker.this.mScrollX + (android.widget.NumberPicker.this.mRight - android.widget.NumberPicker.this.mLeft), android.widget.NumberPicker.this.mBottomSelectionDividerBottom - android.widget.NumberPicker.this.mSelectionDividerHeight);
                case 3:
                    return createAccessibilityNodeInfoForVirtualButton(3, getVirtualDecrementButtonText(), android.widget.NumberPicker.this.mScrollX, android.widget.NumberPicker.this.mScrollY, android.widget.NumberPicker.this.mScrollX + (android.widget.NumberPicker.this.mRight - android.widget.NumberPicker.this.mLeft), android.widget.NumberPicker.this.mTopSelectionDividerTop + android.widget.NumberPicker.this.mSelectionDividerHeight);
            }
        }

        @Override // android.view.accessibility.AccessibilityNodeProvider
        public java.util.List<android.view.accessibility.AccessibilityNodeInfo> findAccessibilityNodeInfosByText(java.lang.String str, int i) {
            if (android.text.TextUtils.isEmpty(str)) {
                return java.util.Collections.emptyList();
            }
            java.lang.String lowerCase = str.toLowerCase();
            java.util.ArrayList arrayList = new java.util.ArrayList();
            switch (i) {
                case -1:
                    findAccessibilityNodeInfosByTextInChild(lowerCase, 3, arrayList);
                    findAccessibilityNodeInfosByTextInChild(lowerCase, 2, arrayList);
                    findAccessibilityNodeInfosByTextInChild(lowerCase, 1, arrayList);
                    return arrayList;
                case 0:
                default:
                    return super.findAccessibilityNodeInfosByText(str, i);
                case 1:
                case 2:
                case 3:
                    findAccessibilityNodeInfosByTextInChild(lowerCase, i, arrayList);
                    return arrayList;
            }
        }

        @Override // android.view.accessibility.AccessibilityNodeProvider
        public boolean performAction(int i, int i2, android.os.Bundle bundle) {
            switch (i) {
                case -1:
                    switch (i2) {
                        case 64:
                            if (this.mAccessibilityFocusedView == i) {
                                return false;
                            }
                            this.mAccessibilityFocusedView = i;
                            android.widget.NumberPicker.this.requestAccessibilityFocus();
                            return true;
                        case 128:
                            if (this.mAccessibilityFocusedView != i) {
                                return false;
                            }
                            this.mAccessibilityFocusedView = Integer.MIN_VALUE;
                            android.widget.NumberPicker.this.clearAccessibilityFocus();
                            return true;
                        case 4096:
                        case 16908346:
                            if (!android.widget.NumberPicker.this.isEnabled() || (!android.widget.NumberPicker.this.getWrapSelectorWheel() && android.widget.NumberPicker.this.getValue() >= android.widget.NumberPicker.this.getMaxValue())) {
                                return false;
                            }
                            android.widget.NumberPicker.this.changeValueByOne(true);
                            return true;
                        case 8192:
                        case 16908344:
                            if (!android.widget.NumberPicker.this.isEnabled() || (!android.widget.NumberPicker.this.getWrapSelectorWheel() && android.widget.NumberPicker.this.getValue() <= android.widget.NumberPicker.this.getMinValue())) {
                                return false;
                            }
                            android.widget.NumberPicker.this.changeValueByOne(false);
                            return true;
                    }
                case 1:
                    switch (i2) {
                        case 16:
                            if (!android.widget.NumberPicker.this.isEnabled()) {
                                return false;
                            }
                            android.widget.NumberPicker.this.changeValueByOne(true);
                            sendAccessibilityEventForVirtualView(i, 1);
                            return true;
                        case 64:
                            if (this.mAccessibilityFocusedView == i) {
                                return false;
                            }
                            this.mAccessibilityFocusedView = i;
                            sendAccessibilityEventForVirtualView(i, 32768);
                            android.widget.NumberPicker.this.invalidate(0, android.widget.NumberPicker.this.mBottomSelectionDividerBottom, android.widget.NumberPicker.this.mRight, android.widget.NumberPicker.this.mBottom);
                            return true;
                        case 128:
                            if (this.mAccessibilityFocusedView != i) {
                                return false;
                            }
                            this.mAccessibilityFocusedView = Integer.MIN_VALUE;
                            sendAccessibilityEventForVirtualView(i, 65536);
                            android.widget.NumberPicker.this.invalidate(0, android.widget.NumberPicker.this.mBottomSelectionDividerBottom, android.widget.NumberPicker.this.mRight, android.widget.NumberPicker.this.mBottom);
                            return true;
                        default:
                            return false;
                    }
                case 2:
                    switch (i2) {
                        case 1:
                            if (!android.widget.NumberPicker.this.isEnabled() || android.widget.NumberPicker.this.mInputText.isFocused()) {
                                return false;
                            }
                            return android.widget.NumberPicker.this.mInputText.requestFocus();
                        case 2:
                            if (!android.widget.NumberPicker.this.isEnabled() || !android.widget.NumberPicker.this.mInputText.isFocused()) {
                                return false;
                            }
                            android.widget.NumberPicker.this.mInputText.clearFocus();
                            return true;
                        case 16:
                            if (!android.widget.NumberPicker.this.isEnabled()) {
                                return false;
                            }
                            android.widget.NumberPicker.this.performClick();
                            return true;
                        case 32:
                            if (!android.widget.NumberPicker.this.isEnabled()) {
                                return false;
                            }
                            android.widget.NumberPicker.this.performLongClick();
                            return true;
                        case 64:
                            if (this.mAccessibilityFocusedView == i) {
                                return false;
                            }
                            this.mAccessibilityFocusedView = i;
                            sendAccessibilityEventForVirtualView(i, 32768);
                            android.widget.NumberPicker.this.mInputText.invalidate();
                            return true;
                        case 128:
                            if (this.mAccessibilityFocusedView != i) {
                                return false;
                            }
                            this.mAccessibilityFocusedView = Integer.MIN_VALUE;
                            sendAccessibilityEventForVirtualView(i, 65536);
                            android.widget.NumberPicker.this.mInputText.invalidate();
                            return true;
                        default:
                            return android.widget.NumberPicker.this.mInputText.performAccessibilityAction(i2, bundle);
                    }
                case 3:
                    switch (i2) {
                        case 16:
                            if (!android.widget.NumberPicker.this.isEnabled()) {
                                return false;
                            }
                            android.widget.NumberPicker.this.changeValueByOne(i == 1);
                            sendAccessibilityEventForVirtualView(i, 1);
                            return true;
                        case 64:
                            if (this.mAccessibilityFocusedView == i) {
                                return false;
                            }
                            this.mAccessibilityFocusedView = i;
                            sendAccessibilityEventForVirtualView(i, 32768);
                            android.widget.NumberPicker.this.invalidate(0, 0, android.widget.NumberPicker.this.mRight, android.widget.NumberPicker.this.mTopSelectionDividerTop);
                            return true;
                        case 128:
                            if (this.mAccessibilityFocusedView != i) {
                                return false;
                            }
                            this.mAccessibilityFocusedView = Integer.MIN_VALUE;
                            sendAccessibilityEventForVirtualView(i, 65536);
                            android.widget.NumberPicker.this.invalidate(0, 0, android.widget.NumberPicker.this.mRight, android.widget.NumberPicker.this.mTopSelectionDividerTop);
                            return true;
                        default:
                            return false;
                    }
            }
            return super.performAction(i, i2, bundle);
        }

        public void sendAccessibilityEventForVirtualView(int i, int i2) {
            switch (i) {
                case 1:
                    if (hasVirtualIncrementButton()) {
                        sendAccessibilityEventForVirtualButton(i, i2, getVirtualIncrementButtonText());
                        break;
                    }
                    break;
                case 2:
                    sendAccessibilityEventForVirtualText(i2);
                    break;
                case 3:
                    if (hasVirtualDecrementButton()) {
                        sendAccessibilityEventForVirtualButton(i, i2, getVirtualDecrementButtonText());
                        break;
                    }
                    break;
            }
        }

        private void sendAccessibilityEventForVirtualText(int i) {
            if (android.view.accessibility.AccessibilityManager.getInstance(android.widget.NumberPicker.this.mContext).isEnabled()) {
                android.view.accessibility.AccessibilityEvent obtain = android.view.accessibility.AccessibilityEvent.obtain(i);
                android.widget.NumberPicker.this.mInputText.onInitializeAccessibilityEvent(obtain);
                android.widget.NumberPicker.this.mInputText.onPopulateAccessibilityEvent(obtain);
                obtain.setSource(android.widget.NumberPicker.this, 2);
                android.widget.NumberPicker.this.requestSendAccessibilityEvent(android.widget.NumberPicker.this, obtain);
            }
        }

        private void sendAccessibilityEventForVirtualButton(int i, int i2, java.lang.String str) {
            if (android.view.accessibility.AccessibilityManager.getInstance(android.widget.NumberPicker.this.mContext).isEnabled()) {
                android.view.accessibility.AccessibilityEvent obtain = android.view.accessibility.AccessibilityEvent.obtain(i2);
                obtain.setClassName(android.widget.Button.class.getName());
                obtain.setPackageName(android.widget.NumberPicker.this.mContext.getPackageName());
                obtain.getText().add(str);
                obtain.setEnabled(android.widget.NumberPicker.this.isEnabled());
                obtain.setSource(android.widget.NumberPicker.this, i);
                android.widget.NumberPicker.this.requestSendAccessibilityEvent(android.widget.NumberPicker.this, obtain);
            }
        }

        private void findAccessibilityNodeInfosByTextInChild(java.lang.String str, int i, java.util.List<android.view.accessibility.AccessibilityNodeInfo> list) {
            switch (i) {
                case 1:
                    java.lang.String virtualIncrementButtonText = getVirtualIncrementButtonText();
                    if (!android.text.TextUtils.isEmpty(virtualIncrementButtonText) && virtualIncrementButtonText.toString().toLowerCase().contains(str)) {
                        list.add(createAccessibilityNodeInfo(1));
                        break;
                    }
                    break;
                case 2:
                    android.text.Editable text = android.widget.NumberPicker.this.mInputText.getText();
                    if (!android.text.TextUtils.isEmpty(text) && text.toString().toLowerCase().contains(str)) {
                        list.add(createAccessibilityNodeInfo(2));
                        break;
                    } else {
                        android.text.Editable text2 = android.widget.NumberPicker.this.mInputText.getText();
                        if (!android.text.TextUtils.isEmpty(text2) && text2.toString().toLowerCase().contains(str)) {
                            list.add(createAccessibilityNodeInfo(2));
                            break;
                        }
                    }
                    break;
                case 3:
                    java.lang.String virtualDecrementButtonText = getVirtualDecrementButtonText();
                    if (!android.text.TextUtils.isEmpty(virtualDecrementButtonText) && virtualDecrementButtonText.toString().toLowerCase().contains(str)) {
                        list.add(createAccessibilityNodeInfo(3));
                        break;
                    }
                    break;
            }
        }

        private android.view.accessibility.AccessibilityNodeInfo createAccessibiltyNodeInfoForInputText(int i, int i2, int i3, int i4) {
            android.view.accessibility.AccessibilityNodeInfo createAccessibilityNodeInfo = android.widget.NumberPicker.this.mInputText.createAccessibilityNodeInfo();
            createAccessibilityNodeInfo.setSource(android.widget.NumberPicker.this, 2);
            createAccessibilityNodeInfo.setAccessibilityFocused(this.mAccessibilityFocusedView == 2);
            if (this.mAccessibilityFocusedView != 2) {
                createAccessibilityNodeInfo.addAction(64);
            }
            if (this.mAccessibilityFocusedView == 2) {
                createAccessibilityNodeInfo.addAction(128);
            }
            android.graphics.Rect rect = this.mTempRect;
            rect.set(i, i2, i3, i4);
            createAccessibilityNodeInfo.setVisibleToUser(android.widget.NumberPicker.this.isVisibleToUser(rect));
            createAccessibilityNodeInfo.setBoundsInParent(rect);
            int[] iArr = this.mTempArray;
            android.widget.NumberPicker.this.getLocationOnScreen(iArr);
            rect.offset(iArr[0], iArr[1]);
            createAccessibilityNodeInfo.setBoundsInScreen(rect);
            return createAccessibilityNodeInfo;
        }

        private android.view.accessibility.AccessibilityNodeInfo createAccessibilityNodeInfoForVirtualButton(int i, java.lang.String str, int i2, int i3, int i4, int i5) {
            android.view.accessibility.AccessibilityNodeInfo obtain = android.view.accessibility.AccessibilityNodeInfo.obtain();
            obtain.setClassName(android.widget.Button.class.getName());
            obtain.setPackageName(android.widget.NumberPicker.this.mContext.getPackageName());
            obtain.setSource(android.widget.NumberPicker.this, i);
            obtain.setParent(android.widget.NumberPicker.this);
            obtain.setText(str);
            obtain.setClickable(true);
            obtain.setLongClickable(true);
            obtain.setEnabled(android.widget.NumberPicker.this.isEnabled());
            obtain.setAccessibilityFocused(this.mAccessibilityFocusedView == i);
            android.graphics.Rect rect = this.mTempRect;
            rect.set(i2, i3, i4, i5);
            obtain.setVisibleToUser(android.widget.NumberPicker.this.isVisibleToUser(rect));
            obtain.setBoundsInParent(rect);
            int[] iArr = this.mTempArray;
            android.widget.NumberPicker.this.getLocationOnScreen(iArr);
            rect.offset(iArr[0], iArr[1]);
            obtain.setBoundsInScreen(rect);
            if (this.mAccessibilityFocusedView != i) {
                obtain.addAction(64);
            }
            if (this.mAccessibilityFocusedView == i) {
                obtain.addAction(128);
            }
            if (android.widget.NumberPicker.this.isEnabled()) {
                obtain.addAction(16);
            }
            return obtain;
        }

        private android.view.accessibility.AccessibilityNodeInfo createAccessibilityNodeInfoForNumberPicker(int i, int i2, int i3, int i4) {
            android.view.accessibility.AccessibilityNodeInfo obtain = android.view.accessibility.AccessibilityNodeInfo.obtain();
            obtain.setClassName(android.widget.NumberPicker.class.getName());
            obtain.setPackageName(android.widget.NumberPicker.this.mContext.getPackageName());
            obtain.setSource(android.widget.NumberPicker.this);
            if (hasVirtualDecrementButton()) {
                obtain.addChild(android.widget.NumberPicker.this, 3);
            }
            obtain.addChild(android.widget.NumberPicker.this, 2);
            if (hasVirtualIncrementButton()) {
                obtain.addChild(android.widget.NumberPicker.this, 1);
            }
            obtain.setParent((android.view.View) android.widget.NumberPicker.this.getParentForAccessibility());
            obtain.setEnabled(android.widget.NumberPicker.this.isEnabled());
            obtain.setScrollable(true);
            obtain.setAccessibilityFocused(this.mAccessibilityFocusedView == -1);
            float f = android.widget.NumberPicker.this.getContext().getResources().getCompatibilityInfo().applicationScale;
            android.graphics.Rect rect = this.mTempRect;
            rect.set(i, i2, i3, i4);
            rect.scale(f);
            obtain.setBoundsInParent(rect);
            obtain.setVisibleToUser(android.widget.NumberPicker.this.isVisibleToUser());
            int[] iArr = this.mTempArray;
            android.widget.NumberPicker.this.getLocationOnScreen(iArr);
            rect.offset(iArr[0], iArr[1]);
            rect.scale(f);
            obtain.setBoundsInScreen(rect);
            if (this.mAccessibilityFocusedView != -1) {
                obtain.addAction(64);
            }
            if (this.mAccessibilityFocusedView == -1) {
                obtain.addAction(128);
            }
            if (android.widget.NumberPicker.this.isEnabled()) {
                if (android.widget.NumberPicker.this.getWrapSelectorWheel() || android.widget.NumberPicker.this.getValue() < android.widget.NumberPicker.this.getMaxValue()) {
                    obtain.addAction(android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_FORWARD);
                    obtain.addAction(android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_DOWN);
                }
                if (android.widget.NumberPicker.this.getWrapSelectorWheel() || android.widget.NumberPicker.this.getValue() > android.widget.NumberPicker.this.getMinValue()) {
                    obtain.addAction(android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_BACKWARD);
                    obtain.addAction(android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_UP);
                }
            }
            return obtain;
        }

        private boolean hasVirtualDecrementButton() {
            return android.widget.NumberPicker.this.getWrapSelectorWheel() || android.widget.NumberPicker.this.getValue() > android.widget.NumberPicker.this.getMinValue();
        }

        private boolean hasVirtualIncrementButton() {
            return android.widget.NumberPicker.this.getWrapSelectorWheel() || android.widget.NumberPicker.this.getValue() < android.widget.NumberPicker.this.getMaxValue();
        }

        private java.lang.String getVirtualDecrementButtonText() {
            int i = android.widget.NumberPicker.this.mValue - 1;
            if (android.widget.NumberPicker.this.mWrapSelectorWheel) {
                i = android.widget.NumberPicker.this.getWrappedSelectorIndex(i);
            }
            if (i >= android.widget.NumberPicker.this.mMinValue) {
                return android.widget.NumberPicker.this.mDisplayedValues == null ? android.widget.NumberPicker.this.formatNumber(i) : android.widget.NumberPicker.this.mDisplayedValues[i - android.widget.NumberPicker.this.mMinValue];
            }
            return null;
        }

        private java.lang.String getVirtualIncrementButtonText() {
            int i = android.widget.NumberPicker.this.mValue + 1;
            if (android.widget.NumberPicker.this.mWrapSelectorWheel) {
                i = android.widget.NumberPicker.this.getWrappedSelectorIndex(i);
            }
            if (i <= android.widget.NumberPicker.this.mMaxValue) {
                return android.widget.NumberPicker.this.mDisplayedValues == null ? android.widget.NumberPicker.this.formatNumber(i) : android.widget.NumberPicker.this.mDisplayedValues[i - android.widget.NumberPicker.this.mMinValue];
            }
            return null;
        }
    }

    private static java.lang.String formatNumberWithLocale(int i) {
        return java.lang.String.format(java.util.Locale.getDefault(), "%d", java.lang.Integer.valueOf(i));
    }
}
