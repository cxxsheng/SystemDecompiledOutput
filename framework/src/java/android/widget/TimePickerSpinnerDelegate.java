package android.widget;

/* loaded from: classes4.dex */
class TimePickerSpinnerDelegate extends android.widget.TimePicker.AbstractTimePickerDelegate {
    private static final boolean DEFAULT_ENABLED_STATE = true;
    private static final int HOURS_IN_HALF_DAY = 12;
    private final android.widget.Button mAmPmButton;
    private final android.widget.NumberPicker mAmPmSpinner;
    private final android.widget.EditText mAmPmSpinnerInput;
    private final java.lang.String[] mAmPmStrings;
    private final android.widget.TextView mDivider;
    private char mHourFormat;
    private final android.widget.NumberPicker mHourSpinner;
    private final android.widget.EditText mHourSpinnerInput;
    private boolean mHourWithTwoDigit;
    private boolean mIs24HourView;
    private boolean mIsAm;
    private boolean mIsEnabled;
    private final android.widget.NumberPicker mMinuteSpinner;
    private final android.widget.EditText mMinuteSpinnerInput;
    private final java.util.Calendar mTempCalendar;

    public TimePickerSpinnerDelegate(android.widget.TimePicker timePicker, android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(timePicker, context);
        this.mIsEnabled = true;
        android.content.res.TypedArray obtainStyledAttributes = this.mContext.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.TimePicker, i, i2);
        int resourceId = obtainStyledAttributes.getResourceId(13, com.android.internal.R.layout.time_picker_legacy);
        obtainStyledAttributes.recycle();
        android.view.LayoutInflater.from(this.mContext).inflate(resourceId, (android.view.ViewGroup) this.mDelegator, true).setSaveFromParentEnabled(false);
        this.mHourSpinner = (android.widget.NumberPicker) timePicker.findViewById(com.android.internal.R.id.hour);
        this.mHourSpinner.setOnValueChangedListener(new android.widget.NumberPicker.OnValueChangeListener() { // from class: android.widget.TimePickerSpinnerDelegate.1
            @Override // android.widget.NumberPicker.OnValueChangeListener
            public void onValueChange(android.widget.NumberPicker numberPicker, int i3, int i4) {
                android.widget.TimePickerSpinnerDelegate.this.updateInputState();
                if (!android.widget.TimePickerSpinnerDelegate.this.is24Hour() && ((i3 == 11 && i4 == 12) || (i3 == 12 && i4 == 11))) {
                    android.widget.TimePickerSpinnerDelegate.this.mIsAm = !android.widget.TimePickerSpinnerDelegate.this.mIsAm;
                    android.widget.TimePickerSpinnerDelegate.this.updateAmPmControl();
                }
                android.widget.TimePickerSpinnerDelegate.this.onTimeChanged();
            }
        });
        this.mHourSpinnerInput = (android.widget.EditText) this.mHourSpinner.findViewById(com.android.internal.R.id.numberpicker_input);
        this.mHourSpinnerInput.setImeOptions(5);
        this.mDivider = (android.widget.TextView) this.mDelegator.findViewById(com.android.internal.R.id.divider);
        if (this.mDivider != null) {
            setDividerText();
        }
        this.mMinuteSpinner = (android.widget.NumberPicker) this.mDelegator.findViewById(com.android.internal.R.id.minute);
        this.mMinuteSpinner.setMinValue(0);
        this.mMinuteSpinner.setMaxValue(59);
        this.mMinuteSpinner.setOnLongPressUpdateInterval(100L);
        this.mMinuteSpinner.setFormatter(android.widget.NumberPicker.getTwoDigitFormatter());
        this.mMinuteSpinner.setOnValueChangedListener(new android.widget.NumberPicker.OnValueChangeListener() { // from class: android.widget.TimePickerSpinnerDelegate.2
            @Override // android.widget.NumberPicker.OnValueChangeListener
            public void onValueChange(android.widget.NumberPicker numberPicker, int i3, int i4) {
                android.widget.TimePickerSpinnerDelegate.this.updateInputState();
                int minValue = android.widget.TimePickerSpinnerDelegate.this.mMinuteSpinner.getMinValue();
                int maxValue = android.widget.TimePickerSpinnerDelegate.this.mMinuteSpinner.getMaxValue();
                if (i3 == maxValue && i4 == minValue) {
                    int value = android.widget.TimePickerSpinnerDelegate.this.mHourSpinner.getValue() + 1;
                    if (!android.widget.TimePickerSpinnerDelegate.this.is24Hour() && value == 12) {
                        android.widget.TimePickerSpinnerDelegate.this.mIsAm = !android.widget.TimePickerSpinnerDelegate.this.mIsAm;
                        android.widget.TimePickerSpinnerDelegate.this.updateAmPmControl();
                    }
                    android.widget.TimePickerSpinnerDelegate.this.mHourSpinner.setValue(value);
                } else if (i3 == minValue && i4 == maxValue) {
                    int value2 = android.widget.TimePickerSpinnerDelegate.this.mHourSpinner.getValue() - 1;
                    if (!android.widget.TimePickerSpinnerDelegate.this.is24Hour() && value2 == 11) {
                        android.widget.TimePickerSpinnerDelegate.this.mIsAm = !android.widget.TimePickerSpinnerDelegate.this.mIsAm;
                        android.widget.TimePickerSpinnerDelegate.this.updateAmPmControl();
                    }
                    android.widget.TimePickerSpinnerDelegate.this.mHourSpinner.setValue(value2);
                }
                android.widget.TimePickerSpinnerDelegate.this.onTimeChanged();
            }
        });
        this.mMinuteSpinnerInput = (android.widget.EditText) this.mMinuteSpinner.findViewById(com.android.internal.R.id.numberpicker_input);
        this.mMinuteSpinnerInput.setImeOptions(5);
        this.mAmPmStrings = android.widget.TimePicker.getAmPmStrings(context);
        android.view.View findViewById = this.mDelegator.findViewById(com.android.internal.R.id.amPm);
        if (findViewById instanceof android.widget.Button) {
            this.mAmPmSpinner = null;
            this.mAmPmSpinnerInput = null;
            this.mAmPmButton = (android.widget.Button) findViewById;
            this.mAmPmButton.setOnClickListener(new android.view.View.OnClickListener() { // from class: android.widget.TimePickerSpinnerDelegate.3
                @Override // android.view.View.OnClickListener
                public void onClick(android.view.View view) {
                    view.requestFocus();
                    android.widget.TimePickerSpinnerDelegate.this.mIsAm = !android.widget.TimePickerSpinnerDelegate.this.mIsAm;
                    android.widget.TimePickerSpinnerDelegate.this.updateAmPmControl();
                    android.widget.TimePickerSpinnerDelegate.this.onTimeChanged();
                }
            });
        } else {
            this.mAmPmButton = null;
            this.mAmPmSpinner = (android.widget.NumberPicker) findViewById;
            this.mAmPmSpinner.setMinValue(0);
            this.mAmPmSpinner.setMaxValue(1);
            this.mAmPmSpinner.setDisplayedValues(this.mAmPmStrings);
            this.mAmPmSpinner.setOnValueChangedListener(new android.widget.NumberPicker.OnValueChangeListener() { // from class: android.widget.TimePickerSpinnerDelegate.4
                @Override // android.widget.NumberPicker.OnValueChangeListener
                public void onValueChange(android.widget.NumberPicker numberPicker, int i3, int i4) {
                    android.widget.TimePickerSpinnerDelegate.this.updateInputState();
                    numberPicker.requestFocus();
                    android.widget.TimePickerSpinnerDelegate.this.mIsAm = !android.widget.TimePickerSpinnerDelegate.this.mIsAm;
                    android.widget.TimePickerSpinnerDelegate.this.updateAmPmControl();
                    android.widget.TimePickerSpinnerDelegate.this.onTimeChanged();
                }
            });
            this.mAmPmSpinnerInput = (android.widget.EditText) this.mAmPmSpinner.findViewById(com.android.internal.R.id.numberpicker_input);
            this.mAmPmSpinnerInput.setImeOptions(6);
        }
        if (isAmPmAtStart()) {
            android.view.ViewGroup viewGroup = (android.view.ViewGroup) timePicker.findViewById(com.android.internal.R.id.timePickerLayout);
            viewGroup.removeView(findViewById);
            viewGroup.addView(findViewById, 0);
            android.view.ViewGroup.MarginLayoutParams marginLayoutParams = (android.view.ViewGroup.MarginLayoutParams) findViewById.getLayoutParams();
            int marginStart = marginLayoutParams.getMarginStart();
            int marginEnd = marginLayoutParams.getMarginEnd();
            if (marginStart != marginEnd) {
                marginLayoutParams.setMarginStart(marginEnd);
                marginLayoutParams.setMarginEnd(marginStart);
            }
        }
        getHourFormatData();
        updateHourControl();
        updateMinuteControl();
        updateAmPmControl();
        this.mTempCalendar = java.util.Calendar.getInstance(this.mLocale);
        setHour(this.mTempCalendar.get(11));
        setMinute(this.mTempCalendar.get(12));
        if (!isEnabled()) {
            setEnabled(false);
        }
        setContentDescriptions();
        if (this.mDelegator.getImportantForAccessibility() == 0) {
            this.mDelegator.setImportantForAccessibility(1);
        }
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public boolean validateInput() {
        return true;
    }

    private void getHourFormatData() {
        java.lang.String bestDateTimePattern = android.text.format.DateFormat.getBestDateTimePattern(this.mLocale, this.mIs24HourView ? "Hm" : "hm");
        int length = bestDateTimePattern.length();
        this.mHourWithTwoDigit = false;
        for (int i = 0; i < length; i++) {
            char charAt = bestDateTimePattern.charAt(i);
            if (charAt == 'H' || charAt == 'h' || charAt == 'K' || charAt == 'k') {
                this.mHourFormat = charAt;
                int i2 = i + 1;
                if (i2 < length && charAt == bestDateTimePattern.charAt(i2)) {
                    this.mHourWithTwoDigit = true;
                    return;
                }
                return;
            }
        }
    }

    private boolean isAmPmAtStart() {
        return android.text.format.DateFormat.getBestDateTimePattern(this.mLocale, "hm").startsWith(android.app.backup.FullBackup.APK_TREE_TOKEN);
    }

    private void setDividerText() {
        java.lang.String substring;
        java.lang.String bestDateTimePattern = android.text.format.DateFormat.getBestDateTimePattern(this.mLocale, this.mIs24HourView ? "Hm" : "hm");
        int lastIndexOf = bestDateTimePattern.lastIndexOf(72);
        if (lastIndexOf == -1) {
            lastIndexOf = bestDateTimePattern.lastIndexOf(104);
        }
        if (lastIndexOf == -1) {
            substring = ":";
        } else {
            int i = lastIndexOf + 1;
            int indexOf = bestDateTimePattern.indexOf(109, i);
            if (indexOf == -1) {
                substring = java.lang.Character.toString(bestDateTimePattern.charAt(i));
            } else {
                substring = bestDateTimePattern.substring(i, indexOf);
            }
        }
        this.mDivider.lambda$setTextAsync$0(substring);
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public void setDate(int i, int i2) {
        setCurrentHour(i, false);
        setCurrentMinute(i2, false);
        onTimeChanged();
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public void setHour(int i) {
        setCurrentHour(i, true);
    }

    private void setCurrentHour(int i, boolean z) {
        if (i == getHour()) {
            return;
        }
        resetAutofilledValue();
        if (!is24Hour()) {
            if (i >= 12) {
                this.mIsAm = false;
                if (i > 12) {
                    i -= 12;
                }
            } else {
                this.mIsAm = true;
                if (i == 0) {
                    i = 12;
                }
            }
            updateAmPmControl();
        }
        this.mHourSpinner.setValue(i);
        if (z) {
            onTimeChanged();
        }
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public int getHour() {
        int value = this.mHourSpinner.getValue();
        if (is24Hour()) {
            return value;
        }
        if (this.mIsAm) {
            return value % 12;
        }
        return (value % 12) + 12;
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public void setMinute(int i) {
        setCurrentMinute(i, true);
    }

    private void setCurrentMinute(int i, boolean z) {
        if (i == getMinute()) {
            return;
        }
        resetAutofilledValue();
        this.mMinuteSpinner.setValue(i);
        if (z) {
            onTimeChanged();
        }
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public int getMinute() {
        return this.mMinuteSpinner.getValue();
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public void setIs24Hour(boolean z) {
        if (this.mIs24HourView == z) {
            return;
        }
        int hour = getHour();
        this.mIs24HourView = z;
        getHourFormatData();
        updateHourControl();
        setCurrentHour(hour, false);
        updateMinuteControl();
        updateAmPmControl();
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public boolean is24Hour() {
        return this.mIs24HourView;
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public void setEnabled(boolean z) {
        this.mMinuteSpinner.setEnabled(z);
        if (this.mDivider != null) {
            this.mDivider.setEnabled(z);
        }
        this.mHourSpinner.setEnabled(z);
        if (this.mAmPmSpinner != null) {
            this.mAmPmSpinner.setEnabled(z);
        } else {
            this.mAmPmButton.setEnabled(z);
        }
        this.mIsEnabled = z;
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public boolean isEnabled() {
        return this.mIsEnabled;
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public int getBaseline() {
        return this.mHourSpinner.getBaseline();
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public android.os.Parcelable onSaveInstanceState(android.os.Parcelable parcelable) {
        return new android.widget.TimePicker.AbstractTimePickerDelegate.SavedState(parcelable, getHour(), getMinute(), is24Hour());
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public void onRestoreInstanceState(android.os.Parcelable parcelable) {
        if (parcelable instanceof android.widget.TimePicker.AbstractTimePickerDelegate.SavedState) {
            android.widget.TimePicker.AbstractTimePickerDelegate.SavedState savedState = (android.widget.TimePicker.AbstractTimePickerDelegate.SavedState) parcelable;
            setHour(savedState.getHour());
            setMinute(savedState.getMinute());
        }
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public boolean dispatchPopulateAccessibilityEvent(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        onPopulateAccessibilityEvent(accessibilityEvent);
        return true;
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public void onPopulateAccessibilityEvent(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        int i;
        if (this.mIs24HourView) {
            i = 129;
        } else {
            i = 65;
        }
        this.mTempCalendar.set(11, getHour());
        this.mTempCalendar.set(12, getMinute());
        accessibilityEvent.getText().add(android.text.format.DateUtils.formatDateTime(this.mContext, this.mTempCalendar.getTimeInMillis(), i));
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public android.view.View getHourView() {
        return this.mHourSpinnerInput;
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public android.view.View getMinuteView() {
        return this.mMinuteSpinnerInput;
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public android.view.View getAmView() {
        return this.mAmPmSpinnerInput;
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public android.view.View getPmView() {
        return this.mAmPmSpinnerInput;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateInputState() {
        android.view.inputmethod.InputMethodManager inputMethodManager = (android.view.inputmethod.InputMethodManager) this.mContext.getSystemService(android.view.inputmethod.InputMethodManager.class);
        if (inputMethodManager != null) {
            if (this.mHourSpinnerInput.hasFocus()) {
                inputMethodManager.hideSoftInputFromView(this.mHourSpinnerInput, 0);
                this.mHourSpinnerInput.clearFocus();
            } else if (this.mMinuteSpinnerInput.hasFocus()) {
                inputMethodManager.hideSoftInputFromView(this.mMinuteSpinnerInput, 0);
                this.mMinuteSpinnerInput.clearFocus();
            } else if (this.mAmPmSpinnerInput.hasFocus()) {
                inputMethodManager.hideSoftInputFromView(this.mAmPmSpinnerInput, 0);
                this.mAmPmSpinnerInput.clearFocus();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateAmPmControl() {
        if (is24Hour()) {
            if (this.mAmPmSpinner != null) {
                this.mAmPmSpinner.setVisibility(8);
            } else {
                this.mAmPmButton.setVisibility(8);
            }
        } else {
            int i = !this.mIsAm ? 1 : 0;
            if (this.mAmPmSpinner != null) {
                this.mAmPmSpinner.setValue(i);
                this.mAmPmSpinner.setVisibility(0);
            } else {
                this.mAmPmButton.lambda$setTextAsync$0(this.mAmPmStrings[i]);
                this.mAmPmButton.setVisibility(0);
            }
        }
        this.mDelegator.sendAccessibilityEvent(4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTimeChanged() {
        this.mDelegator.sendAccessibilityEvent(4);
        if (this.mOnTimeChangedListener != null) {
            this.mOnTimeChangedListener.onTimeChanged(this.mDelegator, getHour(), getMinute());
        }
        if (this.mAutoFillChangeListener != null) {
            this.mAutoFillChangeListener.onTimeChanged(this.mDelegator, getHour(), getMinute());
        }
    }

    private void updateHourControl() {
        if (is24Hour()) {
            if (this.mHourFormat == 'k') {
                this.mHourSpinner.setMinValue(1);
                this.mHourSpinner.setMaxValue(24);
            } else {
                this.mHourSpinner.setMinValue(0);
                this.mHourSpinner.setMaxValue(23);
            }
        } else if (this.mHourFormat == 'K') {
            this.mHourSpinner.setMinValue(0);
            this.mHourSpinner.setMaxValue(11);
        } else {
            this.mHourSpinner.setMinValue(1);
            this.mHourSpinner.setMaxValue(12);
        }
        this.mHourSpinner.setFormatter(this.mHourWithTwoDigit ? android.widget.NumberPicker.getTwoDigitFormatter() : null);
    }

    private void updateMinuteControl() {
        if (is24Hour()) {
            this.mMinuteSpinnerInput.setImeOptions(6);
        } else {
            this.mMinuteSpinnerInput.setImeOptions(5);
        }
    }

    private void setContentDescriptions() {
        trySetContentDescription(this.mMinuteSpinner, com.android.internal.R.id.increment, com.android.internal.R.string.time_picker_increment_minute_button);
        trySetContentDescription(this.mMinuteSpinner, com.android.internal.R.id.decrement, com.android.internal.R.string.time_picker_decrement_minute_button);
        trySetContentDescription(this.mHourSpinner, com.android.internal.R.id.increment, com.android.internal.R.string.time_picker_increment_hour_button);
        trySetContentDescription(this.mHourSpinner, com.android.internal.R.id.decrement, com.android.internal.R.string.time_picker_decrement_hour_button);
        if (this.mAmPmSpinner != null) {
            trySetContentDescription(this.mAmPmSpinner, com.android.internal.R.id.increment, com.android.internal.R.string.time_picker_increment_set_pm_button);
            trySetContentDescription(this.mAmPmSpinner, com.android.internal.R.id.decrement, com.android.internal.R.string.time_picker_decrement_set_am_button);
        }
    }

    private void trySetContentDescription(android.view.View view, int i, int i2) {
        android.view.View findViewById = view.findViewById(i);
        if (findViewById != null) {
            findViewById.setContentDescription(this.mContext.getString(i2));
        }
    }
}
