package android.widget;

/* loaded from: classes4.dex */
class DatePickerSpinnerDelegate extends android.widget.DatePicker.AbstractDatePickerDelegate {
    private static final java.lang.String DATE_FORMAT = "MM/dd/yyyy";
    private static final boolean DEFAULT_CALENDAR_VIEW_SHOWN = true;
    private static final boolean DEFAULT_ENABLED_STATE = true;
    private static final int DEFAULT_END_YEAR = 2100;
    private static final boolean DEFAULT_SPINNERS_SHOWN = true;
    private static final int DEFAULT_START_YEAR = 1900;
    private final android.widget.CalendarView mCalendarView;
    private final java.text.DateFormat mDateFormat;
    private final android.widget.NumberPicker mDaySpinner;
    private final android.widget.EditText mDaySpinnerInput;
    private boolean mIsEnabled;
    private android.icu.util.Calendar mMaxDate;
    private android.icu.util.Calendar mMinDate;
    private final android.widget.NumberPicker mMonthSpinner;
    private final android.widget.EditText mMonthSpinnerInput;
    private int mNumberOfMonths;
    private java.lang.String[] mShortMonths;
    private final android.widget.LinearLayout mSpinners;
    private android.icu.util.Calendar mTempDate;
    private final android.widget.NumberPicker mYearSpinner;
    private final android.widget.EditText mYearSpinnerInput;

    DatePickerSpinnerDelegate(android.widget.DatePicker datePicker, android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(datePicker, context);
        this.mDateFormat = new java.text.SimpleDateFormat(DATE_FORMAT);
        this.mIsEnabled = true;
        this.mDelegator = datePicker;
        this.mContext = context;
        setCurrentLocale(java.util.Locale.getDefault());
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.DatePicker, i, i2);
        boolean z = obtainStyledAttributes.getBoolean(6, true);
        boolean z2 = obtainStyledAttributes.getBoolean(7, true);
        int i3 = obtainStyledAttributes.getInt(1, 1900);
        int i4 = obtainStyledAttributes.getInt(2, 2100);
        java.lang.String string = obtainStyledAttributes.getString(4);
        java.lang.String string2 = obtainStyledAttributes.getString(5);
        int resourceId = obtainStyledAttributes.getResourceId(20, com.android.internal.R.layout.date_picker_legacy);
        obtainStyledAttributes.recycle();
        ((android.view.LayoutInflater) context.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE)).inflate(resourceId, (android.view.ViewGroup) this.mDelegator, true).setSaveFromParentEnabled(false);
        android.widget.NumberPicker.OnValueChangeListener onValueChangeListener = new android.widget.NumberPicker.OnValueChangeListener() { // from class: android.widget.DatePickerSpinnerDelegate.1
            @Override // android.widget.NumberPicker.OnValueChangeListener
            public void onValueChange(android.widget.NumberPicker numberPicker, int i5, int i6) {
                android.widget.DatePickerSpinnerDelegate.this.updateInputState();
                android.widget.DatePickerSpinnerDelegate.this.mTempDate.setTimeInMillis(android.widget.DatePickerSpinnerDelegate.this.mCurrentDate.getTimeInMillis());
                if (numberPicker == android.widget.DatePickerSpinnerDelegate.this.mDaySpinner) {
                    int actualMaximum = android.widget.DatePickerSpinnerDelegate.this.mTempDate.getActualMaximum(5);
                    if (i5 == actualMaximum && i6 == 1) {
                        android.widget.DatePickerSpinnerDelegate.this.mTempDate.add(5, 1);
                    } else if (i5 == 1 && i6 == actualMaximum) {
                        android.widget.DatePickerSpinnerDelegate.this.mTempDate.add(5, -1);
                    } else {
                        android.widget.DatePickerSpinnerDelegate.this.mTempDate.add(5, i6 - i5);
                    }
                } else if (numberPicker == android.widget.DatePickerSpinnerDelegate.this.mMonthSpinner) {
                    if (i5 == 11 && i6 == 0) {
                        android.widget.DatePickerSpinnerDelegate.this.mTempDate.add(2, 1);
                    } else if (i5 == 0 && i6 == 11) {
                        android.widget.DatePickerSpinnerDelegate.this.mTempDate.add(2, -1);
                    } else {
                        android.widget.DatePickerSpinnerDelegate.this.mTempDate.add(2, i6 - i5);
                    }
                } else if (numberPicker == android.widget.DatePickerSpinnerDelegate.this.mYearSpinner) {
                    android.widget.DatePickerSpinnerDelegate.this.mTempDate.set(1, i6);
                } else {
                    throw new java.lang.IllegalArgumentException();
                }
                android.widget.DatePickerSpinnerDelegate.this.setDate(android.widget.DatePickerSpinnerDelegate.this.mTempDate.get(1), android.widget.DatePickerSpinnerDelegate.this.mTempDate.get(2), android.widget.DatePickerSpinnerDelegate.this.mTempDate.get(5));
                android.widget.DatePickerSpinnerDelegate.this.updateSpinners();
                android.widget.DatePickerSpinnerDelegate.this.updateCalendarView();
                android.widget.DatePickerSpinnerDelegate.this.notifyDateChanged();
            }
        };
        this.mSpinners = (android.widget.LinearLayout) this.mDelegator.findViewById(com.android.internal.R.id.pickers);
        this.mCalendarView = (android.widget.CalendarView) this.mDelegator.findViewById(com.android.internal.R.id.calendar_view);
        this.mCalendarView.setOnDateChangeListener(new android.widget.CalendarView.OnDateChangeListener() { // from class: android.widget.DatePickerSpinnerDelegate.2
            @Override // android.widget.CalendarView.OnDateChangeListener
            public void onSelectedDayChange(android.widget.CalendarView calendarView, int i5, int i6, int i7) {
                android.widget.DatePickerSpinnerDelegate.this.setDate(i5, i6, i7);
                android.widget.DatePickerSpinnerDelegate.this.updateSpinners();
                android.widget.DatePickerSpinnerDelegate.this.notifyDateChanged();
            }
        });
        this.mDaySpinner = (android.widget.NumberPicker) this.mDelegator.findViewById(com.android.internal.R.id.day);
        this.mDaySpinner.setFormatter(android.widget.NumberPicker.getTwoDigitFormatter());
        this.mDaySpinner.setOnLongPressUpdateInterval(100L);
        this.mDaySpinner.setOnValueChangedListener(onValueChangeListener);
        this.mDaySpinnerInput = (android.widget.EditText) this.mDaySpinner.findViewById(com.android.internal.R.id.numberpicker_input);
        this.mMonthSpinner = (android.widget.NumberPicker) this.mDelegator.findViewById(com.android.internal.R.id.month);
        this.mMonthSpinner.setMinValue(0);
        this.mMonthSpinner.setMaxValue(this.mNumberOfMonths - 1);
        this.mMonthSpinner.setDisplayedValues(this.mShortMonths);
        this.mMonthSpinner.setOnLongPressUpdateInterval(200L);
        this.mMonthSpinner.setOnValueChangedListener(onValueChangeListener);
        this.mMonthSpinnerInput = (android.widget.EditText) this.mMonthSpinner.findViewById(com.android.internal.R.id.numberpicker_input);
        this.mYearSpinner = (android.widget.NumberPicker) this.mDelegator.findViewById(com.android.internal.R.id.year);
        this.mYearSpinner.setOnLongPressUpdateInterval(100L);
        this.mYearSpinner.setOnValueChangedListener(onValueChangeListener);
        this.mYearSpinnerInput = (android.widget.EditText) this.mYearSpinner.findViewById(com.android.internal.R.id.numberpicker_input);
        if (!z && !z2) {
            setSpinnersShown(true);
        } else {
            setSpinnersShown(z);
            setCalendarViewShown(z2);
        }
        this.mTempDate.clear();
        if (android.text.TextUtils.isEmpty(string)) {
            this.mTempDate.set(i3, 0, 1);
        } else if (!parseDate(string, this.mTempDate)) {
            this.mTempDate.set(i3, 0, 1);
        }
        setMinDate(this.mTempDate.getTimeInMillis());
        this.mTempDate.clear();
        if (!android.text.TextUtils.isEmpty(string2)) {
            if (!parseDate(string2, this.mTempDate)) {
                this.mTempDate.set(i4, 11, 31);
            }
        } else {
            this.mTempDate.set(i4, 11, 31);
        }
        setMaxDate(this.mTempDate.getTimeInMillis());
        this.mCurrentDate.setTimeInMillis(java.lang.System.currentTimeMillis());
        init(this.mCurrentDate.get(1), this.mCurrentDate.get(2), this.mCurrentDate.get(5), null);
        reorderSpinners();
        setContentDescriptions();
        if (this.mDelegator.getImportantForAccessibility() == 0) {
            this.mDelegator.setImportantForAccessibility(1);
        }
    }

    @Override // android.widget.DatePicker.DatePickerDelegate
    public void init(int i, int i2, int i3, android.widget.DatePicker.OnDateChangedListener onDateChangedListener) {
        setDate(i, i2, i3);
        updateSpinners();
        updateCalendarView();
        this.mOnDateChangedListener = onDateChangedListener;
    }

    @Override // android.widget.DatePicker.DatePickerDelegate
    public void updateDate(int i, int i2, int i3) {
        if (!isNewDate(i, i2, i3)) {
            return;
        }
        setDate(i, i2, i3);
        updateSpinners();
        updateCalendarView();
        notifyDateChanged();
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
    public void setFirstDayOfWeek(int i) {
        this.mCalendarView.setFirstDayOfWeek(i);
    }

    @Override // android.widget.DatePicker.DatePickerDelegate
    public int getFirstDayOfWeek() {
        return this.mCalendarView.getFirstDayOfWeek();
    }

    @Override // android.widget.DatePicker.DatePickerDelegate
    public void setMinDate(long j) {
        this.mTempDate.setTimeInMillis(j);
        if (this.mTempDate.get(1) == this.mMinDate.get(1) && this.mTempDate.get(6) == this.mMinDate.get(6)) {
            return;
        }
        this.mMinDate.setTimeInMillis(j);
        this.mCalendarView.setMinDate(j);
        if (this.mCurrentDate.before(this.mMinDate)) {
            this.mCurrentDate.setTimeInMillis(this.mMinDate.getTimeInMillis());
            updateCalendarView();
        }
        updateSpinners();
    }

    @Override // android.widget.DatePicker.DatePickerDelegate
    public android.icu.util.Calendar getMinDate() {
        android.icu.util.Calendar calendar = android.icu.util.Calendar.getInstance();
        calendar.setTimeInMillis(this.mCalendarView.getMinDate());
        return calendar;
    }

    @Override // android.widget.DatePicker.DatePickerDelegate
    public void setMaxDate(long j) {
        this.mTempDate.setTimeInMillis(j);
        if (this.mTempDate.get(1) == this.mMaxDate.get(1) && this.mTempDate.get(6) == this.mMaxDate.get(6)) {
            return;
        }
        this.mMaxDate.setTimeInMillis(j);
        this.mCalendarView.setMaxDate(j);
        if (this.mCurrentDate.after(this.mMaxDate)) {
            this.mCurrentDate.setTimeInMillis(this.mMaxDate.getTimeInMillis());
            updateCalendarView();
        }
        updateSpinners();
    }

    @Override // android.widget.DatePicker.DatePickerDelegate
    public android.icu.util.Calendar getMaxDate() {
        android.icu.util.Calendar calendar = android.icu.util.Calendar.getInstance();
        calendar.setTimeInMillis(this.mCalendarView.getMaxDate());
        return calendar;
    }

    @Override // android.widget.DatePicker.DatePickerDelegate
    public void setEnabled(boolean z) {
        this.mDaySpinner.setEnabled(z);
        this.mMonthSpinner.setEnabled(z);
        this.mYearSpinner.setEnabled(z);
        this.mCalendarView.setEnabled(z);
        this.mIsEnabled = z;
    }

    @Override // android.widget.DatePicker.DatePickerDelegate
    public boolean isEnabled() {
        return this.mIsEnabled;
    }

    @Override // android.widget.DatePicker.DatePickerDelegate
    public android.widget.CalendarView getCalendarView() {
        return this.mCalendarView;
    }

    @Override // android.widget.DatePicker.DatePickerDelegate
    public void setCalendarViewShown(boolean z) {
        this.mCalendarView.setVisibility(z ? 0 : 8);
    }

    @Override // android.widget.DatePicker.DatePickerDelegate
    public boolean getCalendarViewShown() {
        return this.mCalendarView.getVisibility() == 0;
    }

    @Override // android.widget.DatePicker.DatePickerDelegate
    public void setSpinnersShown(boolean z) {
        this.mSpinners.setVisibility(z ? 0 : 8);
    }

    @Override // android.widget.DatePicker.DatePickerDelegate
    public boolean getSpinnersShown() {
        return this.mSpinners.isShown();
    }

    @Override // android.widget.DatePicker.DatePickerDelegate
    public void onConfigurationChanged(android.content.res.Configuration configuration) {
        setCurrentLocale(configuration.locale);
    }

    @Override // android.widget.DatePicker.DatePickerDelegate
    public android.os.Parcelable onSaveInstanceState(android.os.Parcelable parcelable) {
        return new android.widget.DatePicker.AbstractDatePickerDelegate.SavedState(parcelable, getYear(), getMonth(), getDayOfMonth(), getMinDate().getTimeInMillis(), getMaxDate().getTimeInMillis());
    }

    @Override // android.widget.DatePicker.DatePickerDelegate
    public void onRestoreInstanceState(android.os.Parcelable parcelable) {
        if (parcelable instanceof android.widget.DatePicker.AbstractDatePickerDelegate.SavedState) {
            android.widget.DatePicker.AbstractDatePickerDelegate.SavedState savedState = (android.widget.DatePicker.AbstractDatePickerDelegate.SavedState) parcelable;
            setDate(savedState.getSelectedYear(), savedState.getSelectedMonth(), savedState.getSelectedDay());
            updateSpinners();
            updateCalendarView();
        }
    }

    @Override // android.widget.DatePicker.DatePickerDelegate
    public boolean dispatchPopulateAccessibilityEvent(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        onPopulateAccessibilityEvent(accessibilityEvent);
        return true;
    }

    @Override // android.widget.DatePicker.AbstractDatePickerDelegate
    protected void setCurrentLocale(java.util.Locale locale) {
        super.setCurrentLocale(locale);
        this.mTempDate = getCalendarForLocale(this.mTempDate, locale);
        this.mMinDate = getCalendarForLocale(this.mMinDate, locale);
        this.mMaxDate = getCalendarForLocale(this.mMaxDate, locale);
        this.mCurrentDate = getCalendarForLocale(this.mCurrentDate, locale);
        this.mNumberOfMonths = this.mTempDate.getActualMaximum(2) + 1;
        this.mShortMonths = new java.text.DateFormatSymbols().getShortMonths();
        if (usingNumericMonths()) {
            this.mShortMonths = new java.lang.String[this.mNumberOfMonths];
            int i = 0;
            while (i < this.mNumberOfMonths) {
                int i2 = i + 1;
                this.mShortMonths[i] = java.lang.String.format("%d", java.lang.Integer.valueOf(i2));
                i = i2;
            }
        }
    }

    private boolean usingNumericMonths() {
        return java.lang.Character.isDigit(this.mShortMonths[0].charAt(0));
    }

    private android.icu.util.Calendar getCalendarForLocale(android.icu.util.Calendar calendar, java.util.Locale locale) {
        if (calendar == null) {
            return android.icu.util.Calendar.getInstance(locale);
        }
        long timeInMillis = calendar.getTimeInMillis();
        android.icu.util.Calendar calendar2 = android.icu.util.Calendar.getInstance(locale);
        calendar2.setTimeInMillis(timeInMillis);
        return calendar2;
    }

    private void reorderSpinners() {
        this.mSpinners.removeAllViews();
        char[] dateFormatOrder = android.text.format.DateFormat.getDateFormatOrder(android.text.format.DateFormat.getBestDateTimePattern(java.util.Locale.getDefault(), "yyyyMMMdd"));
        int length = dateFormatOrder.length;
        for (int i = 0; i < length; i++) {
            switch (dateFormatOrder[i]) {
                case 'M':
                    this.mSpinners.addView(this.mMonthSpinner);
                    setImeOptions(this.mMonthSpinner, length, i);
                    break;
                case 'd':
                    this.mSpinners.addView(this.mDaySpinner);
                    setImeOptions(this.mDaySpinner, length, i);
                    break;
                case 'y':
                    this.mSpinners.addView(this.mYearSpinner);
                    setImeOptions(this.mYearSpinner, length, i);
                    break;
                default:
                    throw new java.lang.IllegalArgumentException(java.util.Arrays.toString(dateFormatOrder));
            }
        }
    }

    private boolean parseDate(java.lang.String str, android.icu.util.Calendar calendar) {
        try {
            calendar.setTime(this.mDateFormat.parse(str));
            return true;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isNewDate(int i, int i2, int i3) {
        return (this.mCurrentDate.get(1) == i && this.mCurrentDate.get(2) == i2 && this.mCurrentDate.get(5) == i3) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDate(int i, int i2, int i3) {
        this.mCurrentDate.set(i, i2, i3);
        resetAutofilledValue();
        if (this.mCurrentDate.before(this.mMinDate)) {
            this.mCurrentDate.setTimeInMillis(this.mMinDate.getTimeInMillis());
        } else if (this.mCurrentDate.after(this.mMaxDate)) {
            this.mCurrentDate.setTimeInMillis(this.mMaxDate.getTimeInMillis());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSpinners() {
        if (this.mCurrentDate.equals(this.mMinDate)) {
            this.mDaySpinner.setMinValue(this.mCurrentDate.get(5));
            this.mDaySpinner.setMaxValue(this.mCurrentDate.getActualMaximum(5));
            this.mDaySpinner.setWrapSelectorWheel(false);
            this.mMonthSpinner.setDisplayedValues(null);
            this.mMonthSpinner.setMinValue(this.mCurrentDate.get(2));
            this.mMonthSpinner.setMaxValue(this.mCurrentDate.getActualMaximum(2));
            this.mMonthSpinner.setWrapSelectorWheel(false);
        } else if (this.mCurrentDate.equals(this.mMaxDate)) {
            this.mDaySpinner.setMinValue(this.mCurrentDate.getActualMinimum(5));
            this.mDaySpinner.setMaxValue(this.mCurrentDate.get(5));
            this.mDaySpinner.setWrapSelectorWheel(false);
            this.mMonthSpinner.setDisplayedValues(null);
            this.mMonthSpinner.setMinValue(this.mCurrentDate.getActualMinimum(2));
            this.mMonthSpinner.setMaxValue(this.mCurrentDate.get(2));
            this.mMonthSpinner.setWrapSelectorWheel(false);
        } else {
            this.mDaySpinner.setMinValue(1);
            this.mDaySpinner.setMaxValue(this.mCurrentDate.getActualMaximum(5));
            this.mDaySpinner.setWrapSelectorWheel(true);
            this.mMonthSpinner.setDisplayedValues(null);
            this.mMonthSpinner.setMinValue(0);
            this.mMonthSpinner.setMaxValue(11);
            this.mMonthSpinner.setWrapSelectorWheel(true);
        }
        this.mMonthSpinner.setDisplayedValues((java.lang.String[]) java.util.Arrays.copyOfRange(this.mShortMonths, this.mMonthSpinner.getMinValue(), this.mMonthSpinner.getMaxValue() + 1));
        this.mYearSpinner.setMinValue(this.mMinDate.get(1));
        this.mYearSpinner.setMaxValue(this.mMaxDate.get(1));
        this.mYearSpinner.setWrapSelectorWheel(false);
        this.mYearSpinner.setValue(this.mCurrentDate.get(1));
        this.mMonthSpinner.setValue(this.mCurrentDate.get(2));
        this.mDaySpinner.setValue(this.mCurrentDate.get(5));
        if (usingNumericMonths()) {
            this.mMonthSpinnerInput.setRawInputType(2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateCalendarView() {
        this.mCalendarView.setDate(this.mCurrentDate.getTimeInMillis(), false, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyDateChanged() {
        this.mDelegator.sendAccessibilityEvent(4);
        if (this.mOnDateChangedListener != null) {
            this.mOnDateChangedListener.onDateChanged(this.mDelegator, getYear(), getMonth(), getDayOfMonth());
        }
        if (this.mAutoFillChangeListener != null) {
            this.mAutoFillChangeListener.onDateChanged(this.mDelegator, getYear(), getMonth(), getDayOfMonth());
        }
    }

    private void setImeOptions(android.widget.NumberPicker numberPicker, int i, int i2) {
        int i3;
        if (i2 < i - 1) {
            i3 = 5;
        } else {
            i3 = 6;
        }
        ((android.widget.TextView) numberPicker.findViewById(com.android.internal.R.id.numberpicker_input)).setImeOptions(i3);
    }

    private void setContentDescriptions() {
        trySetContentDescription(this.mDaySpinner, com.android.internal.R.id.increment, com.android.internal.R.string.date_picker_increment_day_button);
        trySetContentDescription(this.mDaySpinner, com.android.internal.R.id.decrement, com.android.internal.R.string.date_picker_decrement_day_button);
        trySetContentDescription(this.mMonthSpinner, com.android.internal.R.id.increment, com.android.internal.R.string.date_picker_increment_month_button);
        trySetContentDescription(this.mMonthSpinner, com.android.internal.R.id.decrement, com.android.internal.R.string.date_picker_decrement_month_button);
        trySetContentDescription(this.mYearSpinner, com.android.internal.R.id.increment, com.android.internal.R.string.date_picker_increment_year_button);
        trySetContentDescription(this.mYearSpinner, com.android.internal.R.id.decrement, com.android.internal.R.string.date_picker_decrement_year_button);
    }

    private void trySetContentDescription(android.view.View view, int i, int i2) {
        android.view.View findViewById = view.findViewById(i);
        if (findViewById != null) {
            findViewById.setContentDescription(this.mContext.getString(i2));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateInputState() {
        android.view.inputmethod.InputMethodManager inputMethodManager = (android.view.inputmethod.InputMethodManager) this.mContext.getSystemService(android.view.inputmethod.InputMethodManager.class);
        if (inputMethodManager != null) {
            if (this.mYearSpinnerInput.hasFocus()) {
                inputMethodManager.hideSoftInputFromView(this.mYearSpinnerInput, 0);
                this.mYearSpinnerInput.clearFocus();
            } else if (this.mMonthSpinnerInput.hasFocus()) {
                inputMethodManager.hideSoftInputFromView(this.mMonthSpinnerInput, 0);
                this.mMonthSpinnerInput.clearFocus();
            } else if (this.mDaySpinnerInput.hasFocus()) {
                inputMethodManager.hideSoftInputFromView(this.mDaySpinnerInput, 0);
                this.mDaySpinnerInput.clearFocus();
            }
        }
    }
}
