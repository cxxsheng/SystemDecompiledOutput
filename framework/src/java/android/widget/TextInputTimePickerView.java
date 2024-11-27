package android.widget;

/* loaded from: classes4.dex */
public class TextInputTimePickerView extends android.widget.RelativeLayout {
    private static final int AM = 0;
    public static final int AMPM = 2;
    public static final int HOURS = 0;
    public static final int MINUTES = 1;
    private static final int PM = 1;
    private final android.widget.Spinner mAmPmSpinner;
    private final android.widget.TextView mErrorLabel;
    private boolean mErrorShowing;
    private final android.widget.EditText mHourEditText;
    private boolean mHourFormatStartsAtZero;
    private final android.widget.TextView mHourLabel;
    private final android.widget.TextView mInputSeparatorView;
    private boolean mIs24Hour;
    private android.widget.TextInputTimePickerView.OnValueTypedListener mListener;
    private final android.widget.EditText mMinuteEditText;
    private final android.widget.TextView mMinuteLabel;
    private boolean mTimeSet;

    interface OnValueTypedListener {
        void onValueChanged(int i, int i2);
    }

    public TextInputTimePickerView(android.content.Context context) {
        this(context, null);
    }

    public TextInputTimePickerView(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TextInputTimePickerView(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public TextInputTimePickerView(final android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        inflate(context, com.android.internal.R.layout.time_picker_text_input_material, this);
        this.mHourEditText = (android.widget.EditText) findViewById(com.android.internal.R.id.input_hour);
        this.mMinuteEditText = (android.widget.EditText) findViewById(com.android.internal.R.id.input_minute);
        this.mInputSeparatorView = (android.widget.TextView) findViewById(com.android.internal.R.id.input_separator);
        this.mErrorLabel = (android.widget.TextView) findViewById(com.android.internal.R.id.label_error);
        this.mHourLabel = (android.widget.TextView) findViewById(com.android.internal.R.id.label_hour);
        this.mMinuteLabel = (android.widget.TextView) findViewById(com.android.internal.R.id.label_minute);
        this.mHourEditText.addTextChangedListener(new android.text.TextWatcher() { // from class: android.widget.TextInputTimePickerView.1
            @Override // android.text.TextWatcher
            public void beforeTextChanged(java.lang.CharSequence charSequence, int i3, int i4, int i5) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(java.lang.CharSequence charSequence, int i3, int i4, int i5) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(android.text.Editable editable) {
                if (android.widget.TextInputTimePickerView.this.parseAndSetHourInternal(editable.toString()) && editable.length() > 1 && !((android.view.accessibility.AccessibilityManager) context.getSystemService(android.content.Context.ACCESSIBILITY_SERVICE)).isEnabled()) {
                    android.widget.TextInputTimePickerView.this.mMinuteEditText.requestFocus();
                }
            }
        });
        this.mMinuteEditText.addTextChangedListener(new android.text.TextWatcher() { // from class: android.widget.TextInputTimePickerView.2
            @Override // android.text.TextWatcher
            public void beforeTextChanged(java.lang.CharSequence charSequence, int i3, int i4, int i5) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(java.lang.CharSequence charSequence, int i3, int i4, int i5) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(android.text.Editable editable) {
                android.widget.TextInputTimePickerView.this.parseAndSetMinuteInternal(editable.toString());
            }
        });
        this.mAmPmSpinner = (android.widget.Spinner) findViewById(com.android.internal.R.id.am_pm_spinner);
        java.lang.String[] amPmStrings = android.widget.TimePicker.getAmPmStrings(context);
        android.widget.ArrayAdapter arrayAdapter = new android.widget.ArrayAdapter(context, 17367049);
        arrayAdapter.add(android.widget.TimePickerClockDelegate.obtainVerbatim(amPmStrings[0]));
        arrayAdapter.add(android.widget.TimePickerClockDelegate.obtainVerbatim(amPmStrings[1]));
        this.mAmPmSpinner.setAdapter((android.widget.SpinnerAdapter) arrayAdapter);
        this.mAmPmSpinner.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() { // from class: android.widget.TextInputTimePickerView.3
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(android.widget.AdapterView<?> adapterView, android.view.View view, int i3, long j) {
                if (i3 == 0) {
                    android.widget.TextInputTimePickerView.this.mListener.onValueChanged(2, 0);
                } else {
                    android.widget.TextInputTimePickerView.this.mListener.onValueChanged(2, 1);
                }
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(android.widget.AdapterView<?> adapterView) {
            }
        });
    }

    void setListener(android.widget.TextInputTimePickerView.OnValueTypedListener onValueTypedListener) {
        this.mListener = onValueTypedListener;
    }

    void setHourFormat(int i) {
        this.mHourEditText.setFilters(new android.text.InputFilter[]{new android.text.InputFilter.LengthFilter(i)});
        this.mMinuteEditText.setFilters(new android.text.InputFilter[]{new android.text.InputFilter.LengthFilter(i)});
        android.os.LocaleList locales = this.mContext.getResources().getConfiguration().getLocales();
        this.mHourEditText.setImeHintLocales(locales);
        this.mMinuteEditText.setImeHintLocales(locales);
    }

    boolean validateInput() {
        java.lang.String obj;
        java.lang.String obj2;
        if (android.text.TextUtils.isEmpty(this.mHourEditText.getText())) {
            obj = this.mHourEditText.getHint().toString();
        } else {
            obj = this.mHourEditText.getText().toString();
        }
        if (android.text.TextUtils.isEmpty(this.mMinuteEditText.getText())) {
            obj2 = this.mMinuteEditText.getHint().toString();
        } else {
            obj2 = this.mMinuteEditText.getText().toString();
        }
        boolean z = parseAndSetHourInternal(obj) && parseAndSetMinuteInternal(obj2);
        setError(!z);
        return z;
    }

    void updateSeparator(java.lang.String str) {
        this.mInputSeparatorView.lambda$setTextAsync$0(str);
    }

    private void setError(boolean z) {
        this.mErrorShowing = z;
        this.mErrorLabel.setVisibility(z ? 0 : 4);
        this.mHourLabel.setVisibility(z ? 4 : 0);
        this.mMinuteLabel.setVisibility(z ? 4 : 0);
    }

    private void setTimeSet(boolean z) {
        this.mTimeSet = this.mTimeSet || z;
    }

    private boolean isTimeSet() {
        return this.mTimeSet;
    }

    void updateTextInputValues(int i, int i2, int i3, boolean z, boolean z2) {
        this.mIs24Hour = z;
        this.mHourFormatStartsAtZero = z2;
        this.mAmPmSpinner.setVisibility(z ? 4 : 0);
        if (i3 == 0) {
            this.mAmPmSpinner.setSelection(0);
        } else {
            this.mAmPmSpinner.setSelection(1);
        }
        if (isTimeSet()) {
            this.mHourEditText.lambda$setTextAsync$0(java.lang.String.format("%d", java.lang.Integer.valueOf(i)));
            this.mMinuteEditText.lambda$setTextAsync$0(java.lang.String.format("%02d", java.lang.Integer.valueOf(i2)));
        } else {
            this.mHourEditText.setHint(java.lang.String.format("%d", java.lang.Integer.valueOf(i)));
            this.mMinuteEditText.setHint(java.lang.String.format("%02d", java.lang.Integer.valueOf(i2)));
        }
        if (this.mErrorShowing) {
            validateInput();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean parseAndSetHourInternal(java.lang.String str) {
        try {
            int parseInt = java.lang.Integer.parseInt(str);
            if (isValidLocalizedHour(parseInt)) {
                this.mListener.onValueChanged(0, getHourOfDayFromLocalizedHour(parseInt));
                setTimeSet(true);
                return true;
            }
            int i = this.mHourFormatStartsAtZero ? 0 : 1;
            this.mListener.onValueChanged(0, getHourOfDayFromLocalizedHour(android.util.MathUtils.constrain(parseInt, i, this.mIs24Hour ? 23 : i + 11)));
            return false;
        } catch (java.lang.NumberFormatException e) {
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean parseAndSetMinuteInternal(java.lang.String str) {
        try {
            int parseInt = java.lang.Integer.parseInt(str);
            if (parseInt >= 0 && parseInt <= 59) {
                this.mListener.onValueChanged(1, parseInt);
                setTimeSet(true);
                return true;
            }
            this.mListener.onValueChanged(1, android.util.MathUtils.constrain(parseInt, 0, 59));
            return false;
        } catch (java.lang.NumberFormatException e) {
            return false;
        }
    }

    private boolean isValidLocalizedHour(int i) {
        int i2 = !this.mHourFormatStartsAtZero ? 1 : 0;
        return i >= i2 && i <= (this.mIs24Hour ? 23 : 11) + i2;
    }

    private int getHourOfDayFromLocalizedHour(int i) {
        if (this.mIs24Hour) {
            if (this.mHourFormatStartsAtZero || i != 24) {
                return i;
            }
            return 0;
        }
        if (!this.mHourFormatStartsAtZero && i == 12) {
            i = 0;
        }
        if (this.mAmPmSpinner.getSelectedItemPosition() == 1) {
            return i + 12;
        }
        return i;
    }
}
