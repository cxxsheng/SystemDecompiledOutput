package android.widget;

/* loaded from: classes4.dex */
class DatePickerCalendarDelegate extends android.widget.DatePicker.AbstractDatePickerDelegate {
    private static final int ANIMATION_DURATION = 300;
    private static final int DEFAULT_END_YEAR = 2100;
    private static final int DEFAULT_START_YEAR = 1900;
    private static final int UNINITIALIZED = -1;
    private static final int USE_LOCALE = 0;
    private static final int VIEW_MONTH_DAY = 0;
    private static final int VIEW_YEAR = 1;
    private android.widget.ViewAnimator mAnimator;
    private android.view.ViewGroup mContainer;
    private int mCurrentView;
    private android.widget.DayPickerView mDayPickerView;
    private int mFirstDayOfWeek;
    private android.widget.TextView mHeaderMonthDay;
    private android.widget.TextView mHeaderYear;
    private final android.icu.util.Calendar mMaxDate;
    private final android.icu.util.Calendar mMinDate;
    private android.icu.text.DateFormat mMonthDayFormat;
    private final android.widget.DayPickerView.OnDaySelectedListener mOnDaySelectedListener;
    private final android.view.View.OnClickListener mOnHeaderClickListener;
    private final android.widget.YearPickerView.OnYearSelectedListener mOnYearSelectedListener;
    private java.lang.String mSelectDay;
    private java.lang.String mSelectYear;
    private final android.icu.util.Calendar mTempDate;
    private android.icu.text.DateFormat mYearFormat;
    private android.widget.YearPickerView mYearPickerView;
    private static final int[] ATTRS_TEXT_COLOR = {16842904};
    private static final int[] ATTRS_DISABLED_ALPHA = {16842803};

    public DatePickerCalendarDelegate(android.widget.DatePicker datePicker, android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(datePicker, context);
        this.mCurrentView = -1;
        this.mFirstDayOfWeek = 0;
        this.mOnDaySelectedListener = new android.widget.DayPickerView.OnDaySelectedListener() { // from class: android.widget.DatePickerCalendarDelegate.1
            @Override // android.widget.DayPickerView.OnDaySelectedListener
            public void onDaySelected(android.widget.DayPickerView dayPickerView, android.icu.util.Calendar calendar) {
                android.widget.DatePickerCalendarDelegate.this.mCurrentDate.setTimeInMillis(calendar.getTimeInMillis());
                android.widget.DatePickerCalendarDelegate.this.onDateChanged(true, true);
            }
        };
        this.mOnYearSelectedListener = new android.widget.YearPickerView.OnYearSelectedListener() { // from class: android.widget.DatePickerCalendarDelegate.2
            @Override // android.widget.YearPickerView.OnYearSelectedListener
            public void onYearChanged(android.widget.YearPickerView yearPickerView, int i3) {
                int i4 = android.widget.DatePickerCalendarDelegate.this.mCurrentDate.get(5);
                int daysInMonth = android.widget.DatePickerCalendarDelegate.getDaysInMonth(android.widget.DatePickerCalendarDelegate.this.mCurrentDate.get(2), i3);
                if (i4 > daysInMonth) {
                    android.widget.DatePickerCalendarDelegate.this.mCurrentDate.set(5, daysInMonth);
                }
                android.widget.DatePickerCalendarDelegate.this.mCurrentDate.set(1, i3);
                if (android.widget.DatePickerCalendarDelegate.this.mCurrentDate.compareTo(android.widget.DatePickerCalendarDelegate.this.mMinDate) < 0) {
                    android.widget.DatePickerCalendarDelegate.this.mCurrentDate.setTimeInMillis(android.widget.DatePickerCalendarDelegate.this.mMinDate.getTimeInMillis());
                } else if (android.widget.DatePickerCalendarDelegate.this.mCurrentDate.compareTo(android.widget.DatePickerCalendarDelegate.this.mMaxDate) > 0) {
                    android.widget.DatePickerCalendarDelegate.this.mCurrentDate.setTimeInMillis(android.widget.DatePickerCalendarDelegate.this.mMaxDate.getTimeInMillis());
                }
                android.widget.DatePickerCalendarDelegate.this.onDateChanged(true, true);
                android.widget.DatePickerCalendarDelegate.this.setCurrentView(0);
                android.widget.DatePickerCalendarDelegate.this.mHeaderYear.requestFocus();
            }
        };
        this.mOnHeaderClickListener = new android.view.View.OnClickListener() { // from class: android.widget.DatePickerCalendarDelegate$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(android.view.View view) {
                android.widget.DatePickerCalendarDelegate.this.lambda$new$0(view);
            }
        };
        java.util.Locale locale = this.mCurrentLocale;
        this.mCurrentDate = android.icu.util.Calendar.getInstance(locale);
        this.mTempDate = android.icu.util.Calendar.getInstance(locale);
        this.mMinDate = android.icu.util.Calendar.getInstance(locale);
        this.mMaxDate = android.icu.util.Calendar.getInstance(locale);
        this.mMinDate.set(1900, 0, 1);
        this.mMaxDate.set(2100, 11, 31);
        android.content.res.Resources resources = this.mDelegator.getResources();
        android.content.res.TypedArray obtainStyledAttributes = this.mContext.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.DatePicker, i, i2);
        this.mContainer = (android.view.ViewGroup) ((android.view.LayoutInflater) this.mContext.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE)).inflate(obtainStyledAttributes.getResourceId(19, com.android.internal.R.layout.date_picker_material), (android.view.ViewGroup) this.mDelegator, false);
        this.mContainer.setSaveFromParentEnabled(false);
        this.mDelegator.addView(this.mContainer);
        android.view.ViewGroup viewGroup = (android.view.ViewGroup) this.mContainer.findViewById(com.android.internal.R.id.date_picker_header);
        this.mHeaderYear = (android.widget.TextView) viewGroup.findViewById(com.android.internal.R.id.date_picker_header_year);
        this.mHeaderYear.setOnClickListener(this.mOnHeaderClickListener);
        this.mHeaderMonthDay = (android.widget.TextView) viewGroup.findViewById(com.android.internal.R.id.date_picker_header_date);
        this.mHeaderMonthDay.setOnClickListener(this.mOnHeaderClickListener);
        int resourceId = obtainStyledAttributes.getResourceId(10, 0);
        android.content.res.ColorStateList colorStateList = null;
        if (resourceId != 0) {
            android.content.res.TypedArray obtainStyledAttributes2 = this.mContext.obtainStyledAttributes(null, ATTRS_TEXT_COLOR, 0, resourceId);
            colorStateList = applyLegacyColorFixes(obtainStyledAttributes2.getColorStateList(0));
            obtainStyledAttributes2.recycle();
        }
        colorStateList = colorStateList == null ? obtainStyledAttributes.getColorStateList(18) : colorStateList;
        if (colorStateList != null) {
            this.mHeaderYear.setTextColor(colorStateList);
            this.mHeaderMonthDay.setTextColor(colorStateList);
        }
        if (obtainStyledAttributes.hasValueOrEmpty(0)) {
            viewGroup.setBackground(obtainStyledAttributes.getDrawable(0));
        }
        obtainStyledAttributes.recycle();
        this.mAnimator = (android.widget.ViewAnimator) this.mContainer.findViewById(com.android.internal.R.id.animator);
        this.mDayPickerView = (android.widget.DayPickerView) this.mAnimator.findViewById(com.android.internal.R.id.date_picker_day_picker);
        this.mDayPickerView.setFirstDayOfWeek(this.mFirstDayOfWeek);
        this.mDayPickerView.setMinDate(this.mMinDate.getTimeInMillis());
        this.mDayPickerView.setMaxDate(this.mMaxDate.getTimeInMillis());
        this.mDayPickerView.setDate(this.mCurrentDate.getTimeInMillis());
        this.mDayPickerView.setOnDaySelectedListener(this.mOnDaySelectedListener);
        this.mYearPickerView = (android.widget.YearPickerView) this.mAnimator.findViewById(com.android.internal.R.id.date_picker_year_picker);
        this.mYearPickerView.setRange(this.mMinDate, this.mMaxDate);
        this.mYearPickerView.setYear(this.mCurrentDate.get(1));
        this.mYearPickerView.setOnYearSelectedListener(this.mOnYearSelectedListener);
        this.mSelectDay = resources.getString(com.android.internal.R.string.select_day);
        this.mSelectYear = resources.getString(com.android.internal.R.string.select_year);
        onLocaleChanged(this.mCurrentLocale);
        setCurrentView(0);
    }

    private android.content.res.ColorStateList applyLegacyColorFixes(android.content.res.ColorStateList colorStateList) {
        int defaultColor;
        int multiplyAlphaComponent;
        if (colorStateList == null || colorStateList.hasState(16843518)) {
            return colorStateList;
        }
        if (colorStateList.hasState(16842913)) {
            defaultColor = colorStateList.getColorForState(android.util.StateSet.get(10), 0);
            multiplyAlphaComponent = colorStateList.getColorForState(android.util.StateSet.get(8), 0);
        } else {
            defaultColor = colorStateList.getDefaultColor();
            android.content.res.TypedArray obtainStyledAttributes = this.mContext.obtainStyledAttributes(ATTRS_DISABLED_ALPHA);
            float f = obtainStyledAttributes.getFloat(0, 0.3f);
            obtainStyledAttributes.recycle();
            multiplyAlphaComponent = multiplyAlphaComponent(defaultColor, f);
        }
        if (defaultColor == 0 || multiplyAlphaComponent == 0) {
            return null;
        }
        return new android.content.res.ColorStateList(new int[][]{new int[]{16843518}, new int[0]}, new int[]{defaultColor, multiplyAlphaComponent});
    }

    private int multiplyAlphaComponent(int i, float f) {
        return (((int) ((((i >> 24) & 255) * f) + 0.5f)) << 24) | (16777215 & i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(android.view.View view) {
        tryVibrate();
        switch (view.getId()) {
            case com.android.internal.R.id.date_picker_header_date /* 16908958 */:
                setCurrentView(0);
                break;
            case com.android.internal.R.id.date_picker_header_year /* 16908959 */:
                setCurrentView(1);
                break;
        }
    }

    @Override // android.widget.DatePicker.AbstractDatePickerDelegate
    protected void onLocaleChanged(java.util.Locale locale) {
        if (this.mHeaderYear == null) {
            return;
        }
        this.mMonthDayFormat = android.icu.text.DateFormat.getInstanceForSkeleton("EMMMd", locale);
        this.mMonthDayFormat.setContext(android.icu.text.DisplayContext.CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE);
        this.mYearFormat = android.icu.text.DateFormat.getInstanceForSkeleton("y", locale);
        onCurrentDateChanged(false);
    }

    private void onCurrentDateChanged(boolean z) {
        if (this.mHeaderYear == null) {
            return;
        }
        this.mHeaderYear.lambda$setTextAsync$0(this.mYearFormat.format(this.mCurrentDate.getTime()));
        this.mHeaderMonthDay.lambda$setTextAsync$0(this.mMonthDayFormat.format(this.mCurrentDate.getTime()));
        if (z) {
            this.mAnimator.announceForAccessibility(getFormattedCurrentDate());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setCurrentView(int i) {
        switch (i) {
            case 0:
                this.mDayPickerView.setDate(this.mCurrentDate.getTimeInMillis());
                if (this.mCurrentView != i) {
                    this.mHeaderMonthDay.setActivated(true);
                    this.mHeaderYear.setActivated(false);
                    this.mAnimator.setDisplayedChild(0);
                    this.mCurrentView = i;
                }
                this.mAnimator.announceForAccessibility(this.mSelectDay);
                break;
            case 1:
                this.mYearPickerView.setYear(this.mCurrentDate.get(1));
                this.mYearPickerView.post(new java.lang.Runnable() { // from class: android.widget.DatePickerCalendarDelegate$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.widget.DatePickerCalendarDelegate.this.lambda$setCurrentView$1();
                    }
                });
                if (this.mCurrentView != i) {
                    this.mHeaderMonthDay.setActivated(false);
                    this.mHeaderYear.setActivated(true);
                    this.mAnimator.setDisplayedChild(1);
                    this.mCurrentView = i;
                }
                this.mAnimator.announceForAccessibility(this.mSelectYear);
                break;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setCurrentView$1() {
        this.mYearPickerView.requestFocus();
        android.view.View selectedView = this.mYearPickerView.getSelectedView();
        if (selectedView != null) {
            selectedView.requestFocus();
        }
    }

    @Override // android.widget.DatePicker.DatePickerDelegate
    public void init(int i, int i2, int i3, android.widget.DatePicker.OnDateChangedListener onDateChangedListener) {
        setDate(i, i2, i3);
        onDateChanged(false, false);
        this.mOnDateChangedListener = onDateChangedListener;
    }

    @Override // android.widget.DatePicker.DatePickerDelegate
    public void updateDate(int i, int i2, int i3) {
        setDate(i, i2, i3);
        onDateChanged(false, true);
    }

    private void setDate(int i, int i2, int i3) {
        this.mCurrentDate.set(1, i);
        this.mCurrentDate.set(2, i2);
        this.mCurrentDate.set(5, i3);
        resetAutofilledValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onDateChanged(boolean z, boolean z2) {
        int i = this.mCurrentDate.get(1);
        if (z2 && (this.mOnDateChangedListener != null || this.mAutoFillChangeListener != null)) {
            int i2 = this.mCurrentDate.get(2);
            int i3 = this.mCurrentDate.get(5);
            if (this.mOnDateChangedListener != null) {
                this.mOnDateChangedListener.onDateChanged(this.mDelegator, i, i2, i3);
            }
            if (this.mAutoFillChangeListener != null) {
                this.mAutoFillChangeListener.onDateChanged(this.mDelegator, i, i2, i3);
            }
        }
        this.mDayPickerView.setDate(this.mCurrentDate.getTimeInMillis());
        this.mYearPickerView.setYear(i);
        onCurrentDateChanged(z);
        if (z) {
            tryVibrate();
        }
    }

    @Override // android.widget.DatePicker.DatePickerDelegate
    public int getYear() {
        return this.mCurrentDate.get(1);
    }

    @Override // android.widget.DatePicker.DatePickerDelegate
    public int getMonth() {
        return this.mCurrentDate.get(2);
    }

    @Override // android.widget.DatePicker.DatePickerDelegate
    public int getDayOfMonth() {
        return this.mCurrentDate.get(5);
    }

    @Override // android.widget.DatePicker.DatePickerDelegate
    public void setMinDate(long j) {
        this.mTempDate.setTimeInMillis(j);
        if (this.mTempDate.get(1) == this.mMinDate.get(1) && this.mTempDate.get(6) == this.mMinDate.get(6)) {
            return;
        }
        if (this.mCurrentDate.before(this.mTempDate)) {
            this.mCurrentDate.setTimeInMillis(j);
            onDateChanged(false, true);
        }
        this.mMinDate.setTimeInMillis(j);
        this.mDayPickerView.setMinDate(j);
        this.mYearPickerView.setRange(this.mMinDate, this.mMaxDate);
    }

    @Override // android.widget.DatePicker.DatePickerDelegate
    public android.icu.util.Calendar getMinDate() {
        return this.mMinDate;
    }

    @Override // android.widget.DatePicker.DatePickerDelegate
    public void setMaxDate(long j) {
        this.mTempDate.setTimeInMillis(j);
        if (this.mTempDate.get(1) == this.mMaxDate.get(1) && this.mTempDate.get(6) == this.mMaxDate.get(6)) {
            return;
        }
        if (this.mCurrentDate.after(this.mTempDate)) {
            this.mCurrentDate.setTimeInMillis(j);
            onDateChanged(false, true);
        }
        this.mMaxDate.setTimeInMillis(j);
        this.mDayPickerView.setMaxDate(j);
        this.mYearPickerView.setRange(this.mMinDate, this.mMaxDate);
    }

    @Override // android.widget.DatePicker.DatePickerDelegate
    public android.icu.util.Calendar getMaxDate() {
        return this.mMaxDate;
    }

    @Override // android.widget.DatePicker.DatePickerDelegate
    public void setFirstDayOfWeek(int i) {
        this.mFirstDayOfWeek = i;
        this.mDayPickerView.setFirstDayOfWeek(i);
    }

    @Override // android.widget.DatePicker.DatePickerDelegate
    public int getFirstDayOfWeek() {
        if (this.mFirstDayOfWeek != 0) {
            return this.mFirstDayOfWeek;
        }
        return this.mCurrentDate.getFirstDayOfWeek();
    }

    @Override // android.widget.DatePicker.DatePickerDelegate
    public void setEnabled(boolean z) {
        this.mContainer.setEnabled(z);
        this.mDayPickerView.setEnabled(z);
        this.mYearPickerView.setEnabled(z);
        this.mHeaderYear.setEnabled(z);
        this.mHeaderMonthDay.setEnabled(z);
    }

    @Override // android.widget.DatePicker.DatePickerDelegate
    public boolean isEnabled() {
        return this.mContainer.isEnabled();
    }

    @Override // android.widget.DatePicker.DatePickerDelegate
    public android.widget.CalendarView getCalendarView() {
        throw new java.lang.UnsupportedOperationException("Not supported by calendar-mode DatePicker");
    }

    @Override // android.widget.DatePicker.DatePickerDelegate
    public void setCalendarViewShown(boolean z) {
    }

    @Override // android.widget.DatePicker.DatePickerDelegate
    public boolean getCalendarViewShown() {
        return false;
    }

    @Override // android.widget.DatePicker.DatePickerDelegate
    public void setSpinnersShown(boolean z) {
    }

    @Override // android.widget.DatePicker.DatePickerDelegate
    public boolean getSpinnersShown() {
        return false;
    }

    @Override // android.widget.DatePicker.DatePickerDelegate
    public void onConfigurationChanged(android.content.res.Configuration configuration) {
        setCurrentLocale(configuration.locale);
    }

    @Override // android.widget.DatePicker.DatePickerDelegate
    public android.os.Parcelable onSaveInstanceState(android.os.Parcelable parcelable) {
        int i;
        int i2;
        int i3 = this.mCurrentDate.get(1);
        int i4 = this.mCurrentDate.get(2);
        int i5 = this.mCurrentDate.get(5);
        if (this.mCurrentView == 0) {
            i = this.mDayPickerView.getMostVisiblePosition();
            i2 = -1;
        } else if (this.mCurrentView != 1) {
            i = -1;
            i2 = -1;
        } else {
            i = this.mYearPickerView.getFirstVisiblePosition();
            i2 = this.mYearPickerView.getFirstPositionOffset();
        }
        return new android.widget.DatePicker.AbstractDatePickerDelegate.SavedState(parcelable, i3, i4, i5, this.mMinDate.getTimeInMillis(), this.mMaxDate.getTimeInMillis(), this.mCurrentView, i, i2);
    }

    @Override // android.widget.DatePicker.DatePickerDelegate
    public void onRestoreInstanceState(android.os.Parcelable parcelable) {
        if (parcelable instanceof android.widget.DatePicker.AbstractDatePickerDelegate.SavedState) {
            android.widget.DatePicker.AbstractDatePickerDelegate.SavedState savedState = (android.widget.DatePicker.AbstractDatePickerDelegate.SavedState) parcelable;
            this.mCurrentDate.set(savedState.getSelectedYear(), savedState.getSelectedMonth(), savedState.getSelectedDay());
            this.mMinDate.setTimeInMillis(savedState.getMinDate());
            this.mMaxDate.setTimeInMillis(savedState.getMaxDate());
            onCurrentDateChanged(false);
            int currentView = savedState.getCurrentView();
            setCurrentView(currentView);
            int listPosition = savedState.getListPosition();
            if (listPosition != -1) {
                if (currentView == 0) {
                    this.mDayPickerView.setPosition(listPosition);
                } else if (currentView == 1) {
                    this.mYearPickerView.setSelectionFromTop(listPosition, savedState.getListPositionOffset());
                }
            }
        }
    }

    @Override // android.widget.DatePicker.DatePickerDelegate
    public boolean dispatchPopulateAccessibilityEvent(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        onPopulateAccessibilityEvent(accessibilityEvent);
        return true;
    }

    public java.lang.CharSequence getAccessibilityClassName() {
        return android.widget.DatePicker.class.getName();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int getDaysInMonth(int i, int i2) {
        switch (i) {
            case 0:
            case 2:
            case 4:
            case 6:
            case 7:
            case 9:
            case 11:
                return 31;
            case 1:
                return (i2 % 4 != 0 || (i2 % 100 == 0 && i2 % 400 != 0)) ? 28 : 29;
            case 3:
            case 5:
            case 8:
            case 10:
                return 30;
            default:
                throw new java.lang.IllegalArgumentException("Invalid Month");
        }
    }

    private void tryVibrate() {
        this.mDelegator.performHapticFeedback(5);
    }
}
