package android.widget;

/* loaded from: classes4.dex */
class TimePickerClockDelegate extends android.widget.TimePicker.AbstractTimePickerDelegate {
    private static final int AM = 0;
    private static final long DELAY_COMMIT_MILLIS = 2000;
    private static final int FROM_EXTERNAL_API = 0;
    private static final int FROM_INPUT_PICKER = 2;
    private static final int FROM_RADIAL_PICKER = 1;
    private static final int HOURS_IN_HALF_DAY = 12;
    private static final int HOUR_INDEX = 0;
    private static final int MINUTE_INDEX = 1;
    private static final int PM = 1;
    private boolean mAllowAutoAdvance;
    private final android.widget.RadioButton mAmLabel;
    private final android.view.View mAmPmLayout;
    private final android.view.View.OnClickListener mClickListener;
    private final java.lang.Runnable mCommitHour;
    private final java.lang.Runnable mCommitMinute;
    private int mCurrentHour;
    private int mCurrentMinute;
    private final com.android.internal.widget.NumericTextView.OnValueChangedListener mDigitEnteredListener;
    private final android.view.View.OnFocusChangeListener mFocusListener;
    private boolean mHourFormatShowLeadingZero;
    private boolean mHourFormatStartsAtZero;
    private final com.android.internal.widget.NumericTextView mHourView;
    private boolean mIs24Hour;
    private boolean mIsAmPmAtLeft;
    private boolean mIsAmPmAtTop;
    private boolean mIsEnabled;
    private boolean mLastAnnouncedIsHour;
    private java.lang.CharSequence mLastAnnouncedText;
    private final com.android.internal.widget.NumericTextView mMinuteView;
    private final android.widget.RadialTimePickerView.OnValueSelectedListener mOnValueSelectedListener;
    private final android.widget.TextInputTimePickerView.OnValueTypedListener mOnValueTypedListener;
    private final android.widget.RadioButton mPmLabel;
    private boolean mRadialPickerModeEnabled;
    private final android.view.View mRadialTimePickerHeader;
    private final android.widget.ImageButton mRadialTimePickerModeButton;
    private final java.lang.String mRadialTimePickerModeEnabledDescription;
    private final android.widget.RadialTimePickerView mRadialTimePickerView;
    private final java.lang.String mSelectHours;
    private final java.lang.String mSelectMinutes;
    private final android.widget.TextView mSeparatorView;
    private final java.util.Calendar mTempCalendar;
    private final android.view.View mTextInputPickerHeader;
    private final java.lang.String mTextInputPickerModeEnabledDescription;
    private final android.widget.TextInputTimePickerView mTextInputPickerView;
    private static final int[] ATTRS_TEXT_COLOR = {16842904};
    private static final int[] ATTRS_DISABLED_ALPHA = {16842803};

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    private @interface ChangeSource {
    }

    public TimePickerClockDelegate(android.widget.TimePicker timePicker, android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(timePicker, context);
        this.mRadialPickerModeEnabled = true;
        this.mIsEnabled = true;
        this.mIsAmPmAtLeft = false;
        this.mIsAmPmAtTop = false;
        this.mOnValueSelectedListener = new android.widget.RadialTimePickerView.OnValueSelectedListener() { // from class: android.widget.TimePickerClockDelegate.2
            @Override // android.widget.RadialTimePickerView.OnValueSelectedListener
            public void onValueSelected(int i3, int i4, boolean z) {
                boolean z2;
                switch (i3) {
                    case 0:
                        if (android.widget.TimePickerClockDelegate.this.getHour() == i4) {
                            z2 = false;
                        } else {
                            z2 = true;
                        }
                        boolean z3 = android.widget.TimePickerClockDelegate.this.mAllowAutoAdvance && z;
                        android.widget.TimePickerClockDelegate.this.setHourInternal(i4, 1, !z3, true);
                        if (z3) {
                            android.widget.TimePickerClockDelegate.this.setCurrentItemShowing(1, true, false);
                            android.widget.TimePickerClockDelegate.this.mDelegator.announceForAccessibility(android.widget.TimePickerClockDelegate.this.getLocalizedHour(i4) + ". " + android.widget.TimePickerClockDelegate.this.mSelectMinutes);
                        }
                        r1 = z2;
                        break;
                    case 1:
                        r1 = android.widget.TimePickerClockDelegate.this.getMinute() != i4;
                        android.widget.TimePickerClockDelegate.this.setMinuteInternal(i4, 1, true);
                        break;
                }
                if (android.widget.TimePickerClockDelegate.this.mOnTimeChangedListener != null && r1) {
                    android.widget.TimePickerClockDelegate.this.mOnTimeChangedListener.onTimeChanged(android.widget.TimePickerClockDelegate.this.mDelegator, android.widget.TimePickerClockDelegate.this.getHour(), android.widget.TimePickerClockDelegate.this.getMinute());
                }
            }
        };
        this.mOnValueTypedListener = new android.widget.TextInputTimePickerView.OnValueTypedListener() { // from class: android.widget.TimePickerClockDelegate.3
            @Override // android.widget.TextInputTimePickerView.OnValueTypedListener
            public void onValueChanged(int i3, int i4) {
                switch (i3) {
                    case 0:
                        android.widget.TimePickerClockDelegate.this.setHourInternal(i4, 2, false, true);
                        break;
                    case 1:
                        android.widget.TimePickerClockDelegate.this.setMinuteInternal(i4, 2, true);
                        break;
                    case 2:
                        android.widget.TimePickerClockDelegate.this.setAmOrPm(i4);
                        break;
                }
            }
        };
        this.mDigitEnteredListener = new com.android.internal.widget.NumericTextView.OnValueChangedListener() { // from class: android.widget.TimePickerClockDelegate.4
            @Override // com.android.internal.widget.NumericTextView.OnValueChangedListener
            public void onValueChanged(com.android.internal.widget.NumericTextView numericTextView, int i3, boolean z, boolean z2) {
                java.lang.Runnable runnable;
                com.android.internal.widget.NumericTextView numericTextView2 = null;
                if (numericTextView == android.widget.TimePickerClockDelegate.this.mHourView) {
                    runnable = android.widget.TimePickerClockDelegate.this.mCommitHour;
                    if (numericTextView.isFocused()) {
                        numericTextView2 = android.widget.TimePickerClockDelegate.this.mMinuteView;
                    }
                } else if (numericTextView == android.widget.TimePickerClockDelegate.this.mMinuteView) {
                    runnable = android.widget.TimePickerClockDelegate.this.mCommitMinute;
                } else {
                    return;
                }
                numericTextView.removeCallbacks(runnable);
                if (z) {
                    if (z2) {
                        runnable.run();
                        if (numericTextView2 != null) {
                            numericTextView2.requestFocus();
                            return;
                        }
                        return;
                    }
                    numericTextView.postDelayed(runnable, android.widget.TimePickerClockDelegate.DELAY_COMMIT_MILLIS);
                }
            }
        };
        this.mCommitHour = new java.lang.Runnable() { // from class: android.widget.TimePickerClockDelegate.5
            @Override // java.lang.Runnable
            public void run() {
                android.widget.TimePickerClockDelegate.this.setHour(android.widget.TimePickerClockDelegate.this.mHourView.getValue());
            }
        };
        this.mCommitMinute = new java.lang.Runnable() { // from class: android.widget.TimePickerClockDelegate.6
            @Override // java.lang.Runnable
            public void run() {
                android.widget.TimePickerClockDelegate.this.setMinute(android.widget.TimePickerClockDelegate.this.mMinuteView.getValue());
            }
        };
        this.mFocusListener = new android.view.View.OnFocusChangeListener() { // from class: android.widget.TimePickerClockDelegate.7
            @Override // android.view.View.OnFocusChangeListener
            public void onFocusChange(android.view.View view, boolean z) {
                if (z) {
                    switch (view.getId()) {
                        case com.android.internal.R.id.am_label /* 16908782 */:
                            android.widget.TimePickerClockDelegate.this.setAmOrPm(0);
                            break;
                        case com.android.internal.R.id.hours /* 16909114 */:
                            android.widget.TimePickerClockDelegate.this.setCurrentItemShowing(0, true, true);
                            break;
                        case com.android.internal.R.id.minutes /* 16909264 */:
                            android.widget.TimePickerClockDelegate.this.setCurrentItemShowing(1, true, true);
                            break;
                        case com.android.internal.R.id.pm_label /* 16909397 */:
                            android.widget.TimePickerClockDelegate.this.setAmOrPm(1);
                            break;
                        default:
                            return;
                    }
                    android.widget.TimePickerClockDelegate.this.tryVibrate();
                }
            }
        };
        this.mClickListener = new android.view.View.OnClickListener() { // from class: android.widget.TimePickerClockDelegate.8
            @Override // android.view.View.OnClickListener
            public void onClick(android.view.View view) {
                switch (view.getId()) {
                    case com.android.internal.R.id.am_label /* 16908782 */:
                        android.widget.TimePickerClockDelegate.this.setAmOrPm(0);
                        break;
                    case com.android.internal.R.id.hours /* 16909114 */:
                        android.widget.TimePickerClockDelegate.this.setCurrentItemShowing(0, true, true);
                        break;
                    case com.android.internal.R.id.minutes /* 16909264 */:
                        android.widget.TimePickerClockDelegate.this.setCurrentItemShowing(1, true, true);
                        break;
                    case com.android.internal.R.id.pm_label /* 16909397 */:
                        android.widget.TimePickerClockDelegate.this.setAmOrPm(1);
                        break;
                    default:
                        return;
                }
                android.widget.TimePickerClockDelegate.this.tryVibrate();
            }
        };
        android.content.res.TypedArray obtainStyledAttributes = this.mContext.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.TimePicker, i, i2);
        android.view.LayoutInflater layoutInflater = (android.view.LayoutInflater) this.mContext.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);
        android.content.res.Resources resources = this.mContext.getResources();
        this.mSelectHours = resources.getString(com.android.internal.R.string.select_hours);
        this.mSelectMinutes = resources.getString(com.android.internal.R.string.select_minutes);
        android.view.View inflate = layoutInflater.inflate(obtainStyledAttributes.getResourceId(12, com.android.internal.R.layout.time_picker_material), timePicker);
        inflate.setSaveFromParentEnabled(false);
        this.mRadialTimePickerHeader = inflate.findViewById(com.android.internal.R.id.time_header);
        android.content.res.ColorStateList colorStateList = null;
        this.mRadialTimePickerHeader.setOnTouchListener(new android.widget.TimePickerClockDelegate.NearestTouchDelegate());
        this.mHourView = (com.android.internal.widget.NumericTextView) inflate.findViewById(com.android.internal.R.id.hours);
        this.mHourView.setOnClickListener(this.mClickListener);
        this.mHourView.setOnFocusChangeListener(this.mFocusListener);
        this.mHourView.setOnDigitEnteredListener(this.mDigitEnteredListener);
        this.mHourView.setAccessibilityDelegate(new android.widget.TimePickerClockDelegate.ClickActionDelegate(context, com.android.internal.R.string.select_hours));
        this.mSeparatorView = (android.widget.TextView) inflate.findViewById(com.android.internal.R.id.separator);
        this.mMinuteView = (com.android.internal.widget.NumericTextView) inflate.findViewById(com.android.internal.R.id.minutes);
        this.mMinuteView.setOnClickListener(this.mClickListener);
        this.mMinuteView.setOnFocusChangeListener(this.mFocusListener);
        this.mMinuteView.setOnDigitEnteredListener(this.mDigitEnteredListener);
        this.mMinuteView.setAccessibilityDelegate(new android.widget.TimePickerClockDelegate.ClickActionDelegate(context, com.android.internal.R.string.select_minutes));
        this.mMinuteView.setRange(0, 59);
        this.mAmPmLayout = inflate.findViewById(com.android.internal.R.id.ampm_layout);
        this.mAmPmLayout.setOnTouchListener(new android.widget.TimePickerClockDelegate.NearestTouchDelegate());
        java.lang.String[] amPmStrings = android.widget.TimePicker.getAmPmStrings(context);
        this.mAmLabel = (android.widget.RadioButton) this.mAmPmLayout.findViewById(com.android.internal.R.id.am_label);
        this.mAmLabel.lambda$setTextAsync$0(obtainVerbatim(amPmStrings[0]));
        this.mAmLabel.setOnClickListener(this.mClickListener);
        ensureMinimumTextWidth(this.mAmLabel);
        this.mPmLabel = (android.widget.RadioButton) this.mAmPmLayout.findViewById(com.android.internal.R.id.pm_label);
        this.mPmLabel.lambda$setTextAsync$0(obtainVerbatim(amPmStrings[1]));
        this.mPmLabel.setOnClickListener(this.mClickListener);
        ensureMinimumTextWidth(this.mPmLabel);
        int resourceId = obtainStyledAttributes.getResourceId(1, 0);
        if (resourceId != 0) {
            android.content.res.TypedArray obtainStyledAttributes2 = this.mContext.obtainStyledAttributes(null, ATTRS_TEXT_COLOR, 0, resourceId);
            colorStateList = applyLegacyColorFixes(obtainStyledAttributes2.getColorStateList(0));
            obtainStyledAttributes2.recycle();
        }
        colorStateList = colorStateList == null ? obtainStyledAttributes.getColorStateList(11) : colorStateList;
        this.mTextInputPickerHeader = inflate.findViewById(com.android.internal.R.id.input_header);
        if (colorStateList != null) {
            this.mHourView.setTextColor(colorStateList);
            this.mSeparatorView.setTextColor(colorStateList);
            this.mMinuteView.setTextColor(colorStateList);
            this.mAmLabel.setTextColor(colorStateList);
            this.mPmLabel.setTextColor(colorStateList);
        }
        if (obtainStyledAttributes.hasValueOrEmpty(0)) {
            this.mRadialTimePickerHeader.setBackground(obtainStyledAttributes.getDrawable(0));
            this.mTextInputPickerHeader.setBackground(obtainStyledAttributes.getDrawable(0));
        }
        obtainStyledAttributes.recycle();
        this.mRadialTimePickerView = (android.widget.RadialTimePickerView) inflate.findViewById(com.android.internal.R.id.radial_picker);
        this.mRadialTimePickerView.applyAttributes(attributeSet, i, i2);
        this.mRadialTimePickerView.setOnValueSelectedListener(this.mOnValueSelectedListener);
        this.mTextInputPickerView = (android.widget.TextInputTimePickerView) inflate.findViewById(com.android.internal.R.id.input_mode);
        this.mTextInputPickerView.setListener(this.mOnValueTypedListener);
        this.mRadialTimePickerModeButton = (android.widget.ImageButton) inflate.findViewById(com.android.internal.R.id.toggle_mode);
        this.mRadialTimePickerModeButton.setOnClickListener(new android.view.View.OnClickListener() { // from class: android.widget.TimePickerClockDelegate.1
            @Override // android.view.View.OnClickListener
            public void onClick(android.view.View view) {
                android.widget.TimePickerClockDelegate.this.toggleRadialPickerMode();
            }
        });
        this.mRadialTimePickerModeEnabledDescription = context.getResources().getString(com.android.internal.R.string.time_picker_radial_mode_description);
        this.mTextInputPickerModeEnabledDescription = context.getResources().getString(com.android.internal.R.string.time_picker_text_input_mode_description);
        this.mAllowAutoAdvance = true;
        updateHourFormat();
        this.mTempCalendar = java.util.Calendar.getInstance(this.mLocale);
        initialize(this.mTempCalendar.get(11), this.mTempCalendar.get(12), this.mIs24Hour, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void toggleRadialPickerMode() {
        if (this.mRadialPickerModeEnabled) {
            this.mRadialTimePickerView.setVisibility(8);
            this.mRadialTimePickerHeader.setVisibility(8);
            this.mTextInputPickerHeader.setVisibility(0);
            this.mTextInputPickerView.setVisibility(0);
            this.mRadialTimePickerModeButton.setImageResource(com.android.internal.R.drawable.btn_clock_material);
            this.mRadialTimePickerModeButton.setContentDescription(this.mRadialTimePickerModeEnabledDescription);
            this.mRadialPickerModeEnabled = false;
            return;
        }
        this.mRadialTimePickerView.setVisibility(0);
        this.mRadialTimePickerHeader.setVisibility(0);
        this.mTextInputPickerHeader.setVisibility(8);
        this.mTextInputPickerView.setVisibility(8);
        this.mRadialTimePickerModeButton.setImageResource(com.android.internal.R.drawable.btn_keyboard_key_material);
        this.mRadialTimePickerModeButton.setContentDescription(this.mTextInputPickerModeEnabledDescription);
        updateTextInputPicker();
        android.view.inputmethod.InputMethodManager inputMethodManager = (android.view.inputmethod.InputMethodManager) this.mContext.getSystemService(android.view.inputmethod.InputMethodManager.class);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(this.mDelegator.getWindowToken(), 0);
        }
        this.mRadialPickerModeEnabled = true;
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public boolean validateInput() {
        return this.mTextInputPickerView.validateInput();
    }

    private static void ensureMinimumTextWidth(android.widget.TextView textView) {
        textView.measure(0, 0);
        int measuredWidth = textView.getMeasuredWidth();
        textView.setMinWidth(measuredWidth);
        textView.setMinimumWidth(measuredWidth);
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0055  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0077 A[LOOP:1: B:29:0x0073->B:31:0x0077, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0058  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void updateHourFormat() {
        boolean z;
        char c;
        java.lang.String bestDateTimePattern = android.text.format.DateFormat.getBestDateTimePattern(this.mLocale, this.mIs24Hour ? "Hm" : "hm");
        int length = bestDateTimePattern.length();
        for (int i = 0; i < length; i++) {
            c = bestDateTimePattern.charAt(i);
            if (c == 'H' || c == 'h' || c == 'K' || c == 'k') {
                int i2 = i + 1;
                if (i2 < length && c == bestDateTimePattern.charAt(i2)) {
                    z = true;
                } else {
                    z = false;
                }
                this.mHourFormatShowLeadingZero = z;
                this.mHourFormatStartsAtZero = c != 'K' || c == 'H';
                int i3 = !this.mHourFormatStartsAtZero ? 1 : 0;
                this.mHourView.setRange(i3, (!this.mIs24Hour ? 23 : 11) + i3);
                this.mHourView.setShowLeadingZeroes(this.mHourFormatShowLeadingZero);
                java.lang.String[] digitStrings = android.icu.text.DecimalFormatSymbols.getInstance(this.mLocale).getDigitStrings();
                int i4 = 0;
                for (int i5 = 0; i5 < 10; i5++) {
                    i4 = java.lang.Math.max(i4, digitStrings[i5].length());
                }
                this.mTextInputPickerView.setHourFormat(i4 * 2);
            }
        }
        z = false;
        c = 0;
        this.mHourFormatShowLeadingZero = z;
        this.mHourFormatStartsAtZero = c != 'K' || c == 'H';
        int i32 = !this.mHourFormatStartsAtZero ? 1 : 0;
        if (!this.mIs24Hour) {
        }
        this.mHourView.setRange(i32, (!this.mIs24Hour ? 23 : 11) + i32);
        this.mHourView.setShowLeadingZeroes(this.mHourFormatShowLeadingZero);
        java.lang.String[] digitStrings2 = android.icu.text.DecimalFormatSymbols.getInstance(this.mLocale).getDigitStrings();
        int i42 = 0;
        while (i5 < 10) {
        }
        this.mTextInputPickerView.setHourFormat(i42 * 2);
    }

    static final java.lang.CharSequence obtainVerbatim(java.lang.String str) {
        return new android.text.SpannableStringBuilder().append(str, new android.text.style.TtsSpan.VerbatimBuilder(str).build(), 0);
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

    private static class ClickActionDelegate extends android.view.View.AccessibilityDelegate {
        private final android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction mClickAction;

        public ClickActionDelegate(android.content.Context context, int i) {
            this.mClickAction = new android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction(16, context.getString(i));
        }

        @Override // android.view.View.AccessibilityDelegate
        public void onInitializeAccessibilityNodeInfo(android.view.View view, android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
            accessibilityNodeInfo.addAction(this.mClickAction);
        }
    }

    private void initialize(int i, int i2, boolean z, int i3) {
        this.mCurrentHour = i;
        this.mCurrentMinute = i2;
        this.mIs24Hour = z;
        updateUI(i3);
    }

    private void updateUI(int i) {
        updateHeaderAmPm();
        updateHeaderHour(this.mCurrentHour, false);
        updateHeaderSeparator();
        updateHeaderMinute(this.mCurrentMinute, false);
        updateRadialPicker(i);
        updateTextInputPicker();
        this.mDelegator.invalidate();
    }

    private void updateTextInputPicker() {
        this.mTextInputPickerView.updateTextInputValues(getLocalizedHour(this.mCurrentHour), this.mCurrentMinute, this.mCurrentHour < 12 ? 0 : 1, this.mIs24Hour, this.mHourFormatStartsAtZero);
    }

    private void updateRadialPicker(int i) {
        this.mRadialTimePickerView.initialize(this.mCurrentHour, this.mCurrentMinute, this.mIs24Hour);
        setCurrentItemShowing(i, false, true);
    }

    private void updateHeaderAmPm() {
        if (this.mIs24Hour) {
            this.mAmPmLayout.setVisibility(8);
        } else {
            setAmPmStart(android.text.format.DateFormat.getBestDateTimePattern(this.mLocale, "hm").startsWith(android.app.backup.FullBackup.APK_TREE_TOKEN));
            updateAmPmLabelStates(this.mCurrentHour < 12 ? 0 : 1);
        }
    }

    private void setAmPmStart(boolean z) {
        boolean z2;
        int rule;
        android.widget.RelativeLayout.LayoutParams layoutParams = (android.widget.RelativeLayout.LayoutParams) this.mAmPmLayout.getLayoutParams();
        if (layoutParams.getRule(1) != 0 || layoutParams.getRule(0) != 0) {
            int i = (int) (this.mContext.getResources().getDisplayMetrics().density * 8.0f);
            if (android.text.TextUtils.getLayoutDirectionFromLocale(this.mLocale) == 0) {
                z2 = z;
            } else {
                z2 = !z;
            }
            if (z2) {
                layoutParams.removeRule(1);
                layoutParams.addRule(0, this.mHourView.getId());
            } else {
                layoutParams.removeRule(0);
                layoutParams.addRule(1, this.mMinuteView.getId());
            }
            if (z) {
                layoutParams.setMarginStart(0);
                layoutParams.setMarginEnd(i);
            } else {
                layoutParams.setMarginStart(i);
                layoutParams.setMarginEnd(0);
            }
            this.mIsAmPmAtLeft = z2;
        } else if (layoutParams.getRule(3) != 0 || layoutParams.getRule(2) != 0) {
            if (this.mIsAmPmAtTop == z) {
                return;
            }
            if (z) {
                rule = layoutParams.getRule(3);
                layoutParams.removeRule(3);
                layoutParams.addRule(2, rule);
            } else {
                rule = layoutParams.getRule(2);
                layoutParams.removeRule(2);
                layoutParams.addRule(3, rule);
            }
            android.view.View findViewById = this.mRadialTimePickerHeader.findViewById(rule);
            findViewById.setPadding(findViewById.getPaddingLeft(), findViewById.getPaddingBottom(), findViewById.getPaddingRight(), findViewById.getPaddingTop());
            this.mIsAmPmAtTop = z;
        }
        this.mAmPmLayout.setLayoutParams(layoutParams);
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public void setDate(int i, int i2) {
        setHourInternal(i, 0, true, false);
        setMinuteInternal(i2, 0, false);
        onTimeChanged();
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public void setHour(int i) {
        setHourInternal(i, 0, true, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setHourInternal(int i, int i2, boolean z, boolean z2) {
        if (this.mCurrentHour == i) {
            return;
        }
        resetAutofilledValue();
        this.mCurrentHour = i;
        updateHeaderHour(i, z);
        updateHeaderAmPm();
        if (i2 != 1) {
            this.mRadialTimePickerView.setCurrentHour(i);
            this.mRadialTimePickerView.setAmOrPm(i < 12 ? 0 : 1);
        }
        if (i2 != 2) {
            updateTextInputPicker();
        }
        this.mDelegator.invalidate();
        if (z2) {
            onTimeChanged();
        }
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public int getHour() {
        int currentHour = this.mRadialTimePickerView.getCurrentHour();
        if (this.mIs24Hour) {
            return currentHour;
        }
        if (this.mRadialTimePickerView.getAmOrPm() == 1) {
            return (currentHour % 12) + 12;
        }
        return currentHour % 12;
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public void setMinute(int i) {
        setMinuteInternal(i, 0, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setMinuteInternal(int i, int i2, boolean z) {
        if (this.mCurrentMinute == i) {
            return;
        }
        resetAutofilledValue();
        this.mCurrentMinute = i;
        updateHeaderMinute(i, true);
        if (i2 != 1) {
            this.mRadialTimePickerView.setCurrentMinute(i);
        }
        if (i2 != 2) {
            updateTextInputPicker();
        }
        this.mDelegator.invalidate();
        if (z) {
            onTimeChanged();
        }
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public int getMinute() {
        return this.mRadialTimePickerView.getCurrentMinute();
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public void setIs24Hour(boolean z) {
        if (this.mIs24Hour != z) {
            this.mIs24Hour = z;
            this.mCurrentHour = getHour();
            updateHourFormat();
            updateUI(this.mRadialTimePickerView.getCurrentItemShowing());
        }
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public boolean is24Hour() {
        return this.mIs24Hour;
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public void setEnabled(boolean z) {
        this.mHourView.setEnabled(z);
        this.mMinuteView.setEnabled(z);
        this.mAmLabel.setEnabled(z);
        this.mPmLabel.setEnabled(z);
        this.mRadialTimePickerView.setEnabled(z);
        this.mIsEnabled = z;
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public boolean isEnabled() {
        return this.mIsEnabled;
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public int getBaseline() {
        return -1;
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public android.os.Parcelable onSaveInstanceState(android.os.Parcelable parcelable) {
        return new android.widget.TimePicker.AbstractTimePickerDelegate.SavedState(parcelable, getHour(), getMinute(), is24Hour(), getCurrentItemShowing());
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public void onRestoreInstanceState(android.os.Parcelable parcelable) {
        if (parcelable instanceof android.widget.TimePicker.AbstractTimePickerDelegate.SavedState) {
            android.widget.TimePicker.AbstractTimePickerDelegate.SavedState savedState = (android.widget.TimePicker.AbstractTimePickerDelegate.SavedState) parcelable;
            initialize(savedState.getHour(), savedState.getMinute(), savedState.is24HourMode(), savedState.getCurrentItemShowing());
            this.mRadialTimePickerView.invalidate();
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
        if (this.mIs24Hour) {
            i = 129;
        } else {
            i = 65;
        }
        this.mTempCalendar.set(11, getHour());
        this.mTempCalendar.set(12, getMinute());
        accessibilityEvent.getText().add(android.text.format.DateUtils.formatDateTime(this.mContext, this.mTempCalendar.getTimeInMillis(), i) + " " + (this.mRadialTimePickerView.getCurrentItemShowing() == 0 ? this.mSelectHours : this.mSelectMinutes));
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public android.view.View getHourView() {
        return this.mHourView;
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public android.view.View getMinuteView() {
        return this.mMinuteView;
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public android.view.View getAmView() {
        return this.mAmLabel;
    }

    @Override // android.widget.TimePicker.TimePickerDelegate
    public android.view.View getPmView() {
        return this.mPmLabel;
    }

    private int getCurrentItemShowing() {
        return this.mRadialTimePickerView.getCurrentItemShowing();
    }

    private void onTimeChanged() {
        this.mDelegator.sendAccessibilityEvent(4);
        if (this.mOnTimeChangedListener != null) {
            this.mOnTimeChangedListener.onTimeChanged(this.mDelegator, getHour(), getMinute());
        }
        if (this.mAutoFillChangeListener != null) {
            this.mAutoFillChangeListener.onTimeChanged(this.mDelegator, getHour(), getMinute());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void tryVibrate() {
        this.mDelegator.performHapticFeedback(4);
    }

    private void updateAmPmLabelStates(int i) {
        boolean z = i == 0;
        this.mAmLabel.setActivated(z);
        this.mAmLabel.setChecked(z);
        boolean z2 = i == 1;
        this.mPmLabel.setActivated(z2);
        this.mPmLabel.setChecked(z2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getLocalizedHour(int i) {
        if (!this.mIs24Hour) {
            i %= 12;
        }
        if (this.mHourFormatStartsAtZero || i != 0) {
            return i;
        }
        return this.mIs24Hour ? 24 : 12;
    }

    private void updateHeaderHour(int i, boolean z) {
        this.mHourView.setValue(getLocalizedHour(i));
        if (z) {
            tryAnnounceForAccessibility(this.mHourView.getText(), true);
        }
    }

    private void updateHeaderMinute(int i, boolean z) {
        this.mMinuteView.setValue(i);
        if (z) {
            tryAnnounceForAccessibility(this.mMinuteView.getText(), false);
        }
    }

    private void updateHeaderSeparator() {
        java.lang.String hourMinSeparatorFromPattern = getHourMinSeparatorFromPattern(android.text.format.DateFormat.getBestDateTimePattern(this.mLocale, this.mIs24Hour ? "Hm" : "hm"));
        this.mSeparatorView.lambda$setTextAsync$0(hourMinSeparatorFromPattern);
        this.mTextInputPickerView.updateSeparator(hourMinSeparatorFromPattern);
    }

    private static java.lang.String getHourMinSeparatorFromPattern(java.lang.String str) {
        boolean z = false;
        for (int i = 0; i < str.length(); i++) {
            switch (str.charAt(i)) {
                case ' ':
                    break;
                case '\'':
                    if (z) {
                        android.text.SpannableStringBuilder spannableStringBuilder = new android.text.SpannableStringBuilder(str.substring(i));
                        return spannableStringBuilder.subSequence(0, android.text.format.DateFormat.appendQuotedText(spannableStringBuilder, 0)).toString();
                    }
                    break;
                case 'H':
                case 'K':
                case 'h':
                case 'k':
                    z = true;
                    break;
                default:
                    if (z) {
                        return java.lang.Character.toString(str.charAt(i));
                    }
                    break;
            }
        }
        return ":";
    }

    private static int lastIndexOfAny(java.lang.String str, char[] cArr) {
        if (cArr.length > 0) {
            for (int length = str.length() - 1; length >= 0; length--) {
                char charAt = str.charAt(length);
                for (char c : cArr) {
                    if (charAt == c) {
                        return length;
                    }
                }
            }
            return -1;
        }
        return -1;
    }

    private void tryAnnounceForAccessibility(java.lang.CharSequence charSequence, boolean z) {
        if (this.mLastAnnouncedIsHour != z || !charSequence.equals(this.mLastAnnouncedText)) {
            this.mDelegator.announceForAccessibility(charSequence);
            this.mLastAnnouncedText = charSequence;
            this.mLastAnnouncedIsHour = z;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setCurrentItemShowing(int i, boolean z, boolean z2) {
        this.mRadialTimePickerView.setCurrentItemShowing(i, z);
        if (i == 0) {
            if (z2) {
                this.mDelegator.announceForAccessibility(this.mSelectHours);
            }
        } else if (z2) {
            this.mDelegator.announceForAccessibility(this.mSelectMinutes);
        }
        this.mHourView.setActivated(i == 0);
        this.mMinuteView.setActivated(i == 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setAmOrPm(int i) {
        updateAmPmLabelStates(i);
        if (this.mRadialTimePickerView.setAmOrPm(i)) {
            this.mCurrentHour = getHour();
            updateTextInputPicker();
            if (this.mOnTimeChangedListener != null) {
                this.mOnTimeChangedListener.onTimeChanged(this.mDelegator, getHour(), getMinute());
            }
        }
    }

    private static class NearestTouchDelegate implements android.view.View.OnTouchListener {
        private android.view.View mInitialTouchTarget;

        private NearestTouchDelegate() {
        }

        @Override // android.view.View.OnTouchListener
        public boolean onTouch(android.view.View view, android.view.MotionEvent motionEvent) {
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked == 0) {
                if (view instanceof android.view.ViewGroup) {
                    this.mInitialTouchTarget = findNearestChild((android.view.ViewGroup) view, (int) motionEvent.getX(), (int) motionEvent.getY());
                } else {
                    this.mInitialTouchTarget = null;
                }
            }
            android.view.View view2 = this.mInitialTouchTarget;
            if (view2 == null) {
                return false;
            }
            float scrollX = view.getScrollX() - view2.getLeft();
            float scrollY = view.getScrollY() - view2.getTop();
            motionEvent.offsetLocation(scrollX, scrollY);
            boolean dispatchTouchEvent = view2.dispatchTouchEvent(motionEvent);
            motionEvent.offsetLocation(-scrollX, -scrollY);
            if (actionMasked == 1 || actionMasked == 3) {
                this.mInitialTouchTarget = null;
            }
            return dispatchTouchEvent;
        }

        private android.view.View findNearestChild(android.view.ViewGroup viewGroup, int i, int i2) {
            int childCount = viewGroup.getChildCount();
            android.view.View view = null;
            int i3 = Integer.MAX_VALUE;
            for (int i4 = 0; i4 < childCount; i4++) {
                android.view.View childAt = viewGroup.getChildAt(i4);
                int left = i - (childAt.getLeft() + (childAt.getWidth() / 2));
                int top = i2 - (childAt.getTop() + (childAt.getHeight() / 2));
                int i5 = (left * left) + (top * top);
                if (i3 > i5) {
                    view = childAt;
                    i3 = i5;
                }
            }
            return view;
        }
    }
}
