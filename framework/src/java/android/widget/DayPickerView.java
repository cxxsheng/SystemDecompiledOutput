package android.widget;

/* loaded from: classes4.dex */
class DayPickerView extends android.view.ViewGroup {
    private static final int[] ATTRS_TEXT_COLOR = {16842904};
    private static final int DEFAULT_END_YEAR = 2100;
    private static final int DEFAULT_LAYOUT = 17367152;
    private static final int DEFAULT_START_YEAR = 1900;
    private final android.view.accessibility.AccessibilityManager mAccessibilityManager;
    private final android.widget.DayPickerPagerAdapter mAdapter;
    private final android.icu.util.Calendar mMaxDate;
    private final android.icu.util.Calendar mMinDate;
    private final android.widget.ImageButton mNextButton;
    private final android.view.View.OnClickListener mOnClickListener;
    private android.widget.DayPickerView.OnDaySelectedListener mOnDaySelectedListener;
    private final com.android.internal.widget.ViewPager.OnPageChangeListener mOnPageChangedListener;
    private final android.widget.ImageButton mPrevButton;
    private final android.icu.util.Calendar mSelectedDay;
    private android.icu.util.Calendar mTempCalendar;
    private final com.android.internal.widget.ViewPager mViewPager;

    public interface OnDaySelectedListener {
        void onDaySelected(android.widget.DayPickerView dayPickerView, android.icu.util.Calendar calendar);
    }

    public DayPickerView(android.content.Context context) {
        this(context, null);
    }

    public DayPickerView(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 16843613);
    }

    public DayPickerView(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public DayPickerView(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mSelectedDay = android.icu.util.Calendar.getInstance();
        this.mMinDate = android.icu.util.Calendar.getInstance();
        this.mMaxDate = android.icu.util.Calendar.getInstance();
        this.mOnPageChangedListener = new com.android.internal.widget.ViewPager.OnPageChangeListener() { // from class: android.widget.DayPickerView.2
            @Override // com.android.internal.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i3, float f, int i4) {
                float abs = java.lang.Math.abs(0.5f - f) * 2.0f;
                android.widget.DayPickerView.this.mPrevButton.setAlpha(abs);
                android.widget.DayPickerView.this.mNextButton.setAlpha(abs);
            }

            @Override // com.android.internal.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i3) {
            }

            @Override // com.android.internal.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int i3) {
                android.widget.DayPickerView.this.updateButtonVisibility(i3);
            }
        };
        this.mOnClickListener = new android.view.View.OnClickListener() { // from class: android.widget.DayPickerView.3
            @Override // android.view.View.OnClickListener
            public void onClick(android.view.View view) {
                int i3;
                if (view == android.widget.DayPickerView.this.mPrevButton) {
                    i3 = -1;
                } else if (view == android.widget.DayPickerView.this.mNextButton) {
                    i3 = 1;
                } else {
                    return;
                }
                android.widget.DayPickerView.this.mViewPager.setCurrentItem(android.widget.DayPickerView.this.mViewPager.getCurrentItem() + i3, !android.widget.DayPickerView.this.mAccessibilityManager.isEnabled());
            }
        };
        this.mAccessibilityManager = (android.view.accessibility.AccessibilityManager) context.getSystemService(android.content.Context.ACCESSIBILITY_SERVICE);
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.CalendarView, i, i2);
        saveAttributeDataForStyleable(context, com.android.internal.R.styleable.CalendarView, attributeSet, obtainStyledAttributes, i, i2);
        int i3 = obtainStyledAttributes.getInt(0, android.icu.util.Calendar.getInstance().getFirstDayOfWeek());
        java.lang.String string = obtainStyledAttributes.getString(2);
        java.lang.String string2 = obtainStyledAttributes.getString(3);
        int resourceId = obtainStyledAttributes.getResourceId(16, com.android.internal.R.style.TextAppearance_Material_Widget_Calendar_Month);
        int resourceId2 = obtainStyledAttributes.getResourceId(11, com.android.internal.R.style.TextAppearance_Material_Widget_Calendar_DayOfWeek);
        int resourceId3 = obtainStyledAttributes.getResourceId(12, com.android.internal.R.style.TextAppearance_Material_Widget_Calendar_Day);
        android.content.res.ColorStateList colorStateList = obtainStyledAttributes.getColorStateList(15);
        obtainStyledAttributes.recycle();
        this.mAdapter = new android.widget.DayPickerPagerAdapter(context, com.android.internal.R.layout.date_picker_month_item_material, com.android.internal.R.id.month_view);
        this.mAdapter.setMonthTextAppearance(resourceId);
        this.mAdapter.setDayOfWeekTextAppearance(resourceId2);
        this.mAdapter.setDayTextAppearance(resourceId3);
        this.mAdapter.setDaySelectorColor(colorStateList);
        android.view.ViewGroup viewGroup = (android.view.ViewGroup) android.view.LayoutInflater.from(context).inflate(17367152, (android.view.ViewGroup) this, false);
        while (viewGroup.getChildCount() > 0) {
            android.view.View childAt = viewGroup.getChildAt(0);
            viewGroup.removeViewAt(0);
            addView(childAt);
        }
        this.mPrevButton = (android.widget.ImageButton) findViewById(com.android.internal.R.id.prev);
        this.mPrevButton.setOnClickListener(this.mOnClickListener);
        this.mNextButton = (android.widget.ImageButton) findViewById(com.android.internal.R.id.next);
        this.mNextButton.setOnClickListener(this.mOnClickListener);
        this.mViewPager = (com.android.internal.widget.ViewPager) findViewById(com.android.internal.R.id.day_picker_view_pager);
        this.mViewPager.setAdapter(this.mAdapter);
        this.mViewPager.setOnPageChangeListener(this.mOnPageChangedListener);
        if (resourceId != 0) {
            android.content.res.TypedArray obtainStyledAttributes2 = this.mContext.obtainStyledAttributes(null, ATTRS_TEXT_COLOR, 0, resourceId);
            android.content.res.ColorStateList colorStateList2 = obtainStyledAttributes2.getColorStateList(0);
            if (colorStateList2 != null) {
                this.mPrevButton.setImageTintList(colorStateList2);
                this.mNextButton.setImageTintList(colorStateList2);
            }
            obtainStyledAttributes2.recycle();
        }
        android.icu.util.Calendar calendar = android.icu.util.Calendar.getInstance();
        if (!android.widget.CalendarView.parseDate(string, calendar)) {
            calendar.set(1900, 0, 1);
        }
        long timeInMillis = calendar.getTimeInMillis();
        if (!android.widget.CalendarView.parseDate(string2, calendar)) {
            calendar.set(2100, 11, 31);
        }
        long timeInMillis2 = calendar.getTimeInMillis();
        if (timeInMillis2 < timeInMillis) {
            throw new java.lang.IllegalArgumentException("maxDate must be >= minDate");
        }
        long constrain = android.util.MathUtils.constrain(java.lang.System.currentTimeMillis(), timeInMillis, timeInMillis2);
        setFirstDayOfWeek(i3);
        setMinDate(timeInMillis);
        setMaxDate(timeInMillis2);
        setDate(constrain, false);
        this.mAdapter.setOnDaySelectedListener(new android.widget.DayPickerPagerAdapter.OnDaySelectedListener() { // from class: android.widget.DayPickerView.1
            @Override // android.widget.DayPickerPagerAdapter.OnDaySelectedListener
            public void onDaySelected(android.widget.DayPickerPagerAdapter dayPickerPagerAdapter, android.icu.util.Calendar calendar2) {
                if (android.widget.DayPickerView.this.mOnDaySelectedListener != null) {
                    android.widget.DayPickerView.this.mOnDaySelectedListener.onDaySelected(android.widget.DayPickerView.this, calendar2);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateButtonVisibility(int i) {
        boolean z = i > 0;
        boolean z2 = i < this.mAdapter.getCount() - 1;
        this.mPrevButton.setVisibility(z ? 0 : 4);
        this.mNextButton.setVisibility(z2 ? 0 : 4);
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        com.android.internal.widget.ViewPager viewPager = this.mViewPager;
        measureChild(viewPager, i, i2);
        setMeasuredDimension(viewPager.getMeasuredWidthAndState(), viewPager.getMeasuredHeightAndState());
        int measuredWidth = viewPager.getMeasuredWidth();
        int measuredHeight = viewPager.getMeasuredHeight();
        int makeMeasureSpec = android.view.View.MeasureSpec.makeMeasureSpec(measuredWidth, Integer.MIN_VALUE);
        int makeMeasureSpec2 = android.view.View.MeasureSpec.makeMeasureSpec(measuredHeight, Integer.MIN_VALUE);
        this.mPrevButton.measure(makeMeasureSpec, makeMeasureSpec2);
        this.mNextButton.measure(makeMeasureSpec, makeMeasureSpec2);
    }

    @Override // android.view.View
    public void onRtlPropertiesChanged(int i) {
        super.onRtlPropertiesChanged(i);
        requestLayout();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        android.widget.ImageButton imageButton;
        android.widget.ImageButton imageButton2;
        if (isLayoutRtl()) {
            imageButton = this.mNextButton;
            imageButton2 = this.mPrevButton;
        } else {
            imageButton = this.mPrevButton;
            imageButton2 = this.mNextButton;
        }
        int i5 = i3 - i;
        this.mViewPager.layout(0, 0, i5, i4 - i2);
        android.widget.SimpleMonthView simpleMonthView = (android.widget.SimpleMonthView) this.mViewPager.getChildAt(0);
        int monthHeight = simpleMonthView.getMonthHeight();
        int cellWidth = simpleMonthView.getCellWidth();
        int measuredWidth = imageButton.getMeasuredWidth();
        int measuredHeight = imageButton.getMeasuredHeight();
        int paddingTop = simpleMonthView.getPaddingTop() + ((monthHeight - measuredHeight) / 2);
        int paddingLeft = simpleMonthView.getPaddingLeft() + ((cellWidth - measuredWidth) / 2);
        imageButton.layout(paddingLeft, paddingTop, measuredWidth + paddingLeft, measuredHeight + paddingTop);
        int measuredWidth2 = imageButton2.getMeasuredWidth();
        int measuredHeight2 = imageButton2.getMeasuredHeight();
        int paddingTop2 = simpleMonthView.getPaddingTop() + ((monthHeight - measuredHeight2) / 2);
        int paddingRight = (i5 - simpleMonthView.getPaddingRight()) - ((cellWidth - measuredWidth2) / 2);
        imageButton2.layout(paddingRight - measuredWidth2, paddingTop2, paddingRight, measuredHeight2 + paddingTop2);
    }

    public void setDayOfWeekTextAppearance(int i) {
        this.mAdapter.setDayOfWeekTextAppearance(i);
    }

    public int getDayOfWeekTextAppearance() {
        return this.mAdapter.getDayOfWeekTextAppearance();
    }

    public void setDayTextAppearance(int i) {
        this.mAdapter.setDayTextAppearance(i);
    }

    public int getDayTextAppearance() {
        return this.mAdapter.getDayTextAppearance();
    }

    public void setDate(long j) {
        setDate(j, false);
    }

    public void setDate(long j, boolean z) {
        setDate(j, z, true);
    }

    private void setDate(long j, boolean z, boolean z2) {
        boolean z3 = true;
        if (j < this.mMinDate.getTimeInMillis()) {
            j = this.mMinDate.getTimeInMillis();
        } else if (j <= this.mMaxDate.getTimeInMillis()) {
            z3 = false;
        } else {
            j = this.mMaxDate.getTimeInMillis();
        }
        getTempCalendarForTime(j);
        if (z2 || z3) {
            this.mSelectedDay.setTimeInMillis(j);
        }
        int positionFromDay = getPositionFromDay(j);
        if (positionFromDay != this.mViewPager.getCurrentItem()) {
            this.mViewPager.setCurrentItem(positionFromDay, z);
        }
        this.mAdapter.setSelectedDay(this.mTempCalendar);
    }

    public long getDate() {
        return this.mSelectedDay.getTimeInMillis();
    }

    public boolean getBoundsForDate(long j, android.graphics.Rect rect) {
        if (getPositionFromDay(j) != this.mViewPager.getCurrentItem()) {
            return false;
        }
        this.mTempCalendar.setTimeInMillis(j);
        return this.mAdapter.getBoundsForDate(this.mTempCalendar, rect);
    }

    public void setFirstDayOfWeek(int i) {
        this.mAdapter.setFirstDayOfWeek(i);
    }

    public int getFirstDayOfWeek() {
        return this.mAdapter.getFirstDayOfWeek();
    }

    public void setMinDate(long j) {
        this.mMinDate.setTimeInMillis(j);
        onRangeChanged();
    }

    public long getMinDate() {
        return this.mMinDate.getTimeInMillis();
    }

    public void setMaxDate(long j) {
        this.mMaxDate.setTimeInMillis(j);
        onRangeChanged();
    }

    public long getMaxDate() {
        return this.mMaxDate.getTimeInMillis();
    }

    public void onRangeChanged() {
        this.mAdapter.setRange(this.mMinDate, this.mMaxDate);
        setDate(this.mSelectedDay.getTimeInMillis(), false, false);
        updateButtonVisibility(this.mViewPager.getCurrentItem());
    }

    public void setOnDaySelectedListener(android.widget.DayPickerView.OnDaySelectedListener onDaySelectedListener) {
        this.mOnDaySelectedListener = onDaySelectedListener;
    }

    private int getDiffMonths(android.icu.util.Calendar calendar, android.icu.util.Calendar calendar2) {
        return (calendar2.get(2) - calendar.get(2)) + ((calendar2.get(1) - calendar.get(1)) * 12);
    }

    private int getPositionFromDay(long j) {
        return android.util.MathUtils.constrain(getDiffMonths(this.mMinDate, getTempCalendarForTime(j)), 0, getDiffMonths(this.mMinDate, this.mMaxDate));
    }

    private android.icu.util.Calendar getTempCalendarForTime(long j) {
        if (this.mTempCalendar == null) {
            this.mTempCalendar = android.icu.util.Calendar.getInstance();
        }
        this.mTempCalendar.setTimeInMillis(j);
        return this.mTempCalendar;
    }

    public int getMostVisiblePosition() {
        return this.mViewPager.getCurrentItem();
    }

    public void setPosition(int i) {
        this.mViewPager.setCurrentItem(i, false);
    }
}
