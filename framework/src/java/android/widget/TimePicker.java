package android.widget;

/* loaded from: classes4.dex */
public class TimePicker extends android.widget.FrameLayout {
    private static final java.lang.String LOG_TAG = android.widget.TimePicker.class.getSimpleName();
    public static final int MODE_CLOCK = 2;
    public static final int MODE_SPINNER = 1;
    private final android.widget.TimePicker.TimePickerDelegate mDelegate;
    private final int mMode;

    public interface OnTimeChangedListener {
        void onTimeChanged(android.widget.TimePicker timePicker, int i, int i2);
    }

    interface TimePickerDelegate {
        void autofill(android.view.autofill.AutofillValue autofillValue);

        boolean dispatchPopulateAccessibilityEvent(android.view.accessibility.AccessibilityEvent accessibilityEvent);

        android.view.View getAmView();

        android.view.autofill.AutofillValue getAutofillValue();

        int getBaseline();

        int getHour();

        android.view.View getHourView();

        int getMinute();

        android.view.View getMinuteView();

        android.view.View getPmView();

        boolean is24Hour();

        boolean isEnabled();

        void onPopulateAccessibilityEvent(android.view.accessibility.AccessibilityEvent accessibilityEvent);

        void onRestoreInstanceState(android.os.Parcelable parcelable);

        android.os.Parcelable onSaveInstanceState(android.os.Parcelable parcelable);

        void setAutoFillChangeListener(android.widget.TimePicker.OnTimeChangedListener onTimeChangedListener);

        void setDate(int i, int i2);

        void setEnabled(boolean z);

        void setHour(int i);

        void setIs24Hour(boolean z);

        void setMinute(int i);

        void setOnTimeChangedListener(android.widget.TimePicker.OnTimeChangedListener onTimeChangedListener);

        boolean validateInput();
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface TimePickerMode {
    }

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<android.widget.TimePicker> {
        private int m24HourId;
        private int mHourId;
        private int mMinuteId;
        private boolean mPropertiesMapped = false;
        private int mTimePickerModeId;

        @Override // android.view.inspector.InspectionCompanion
        public void mapProperties(android.view.inspector.PropertyMapper propertyMapper) {
            this.m24HourId = propertyMapper.mapBoolean("24Hour", 0);
            this.mHourId = propertyMapper.mapInt("hour", 0);
            this.mMinuteId = propertyMapper.mapInt("minute", 0);
            android.util.SparseArray sparseArray = new android.util.SparseArray();
            sparseArray.put(1, "spinner");
            sparseArray.put(2, "clock");
            java.util.Objects.requireNonNull(sparseArray);
            this.mTimePickerModeId = propertyMapper.mapIntEnum("timePickerMode", 16843956, new android.view.View$InspectionCompanion$$ExternalSyntheticLambda0(sparseArray));
            this.mPropertiesMapped = true;
        }

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(android.widget.TimePicker timePicker, android.view.inspector.PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new android.view.inspector.InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readBoolean(this.m24HourId, timePicker.is24HourView());
            propertyReader.readInt(this.mHourId, timePicker.getHour());
            propertyReader.readInt(this.mMinuteId, timePicker.getMinute());
            propertyReader.readIntEnum(this.mTimePickerModeId, timePicker.getMode());
        }
    }

    public TimePicker(android.content.Context context) {
        this(context, null);
    }

    public TimePicker(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 16843933);
    }

    public TimePicker(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public TimePicker(final android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        if (getImportantForAutofill() == 0) {
            setImportantForAutofill(1);
        }
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.TimePicker, i, i2);
        saveAttributeDataForStyleable(context, com.android.internal.R.styleable.TimePicker, attributeSet, obtainStyledAttributes, i, i2);
        boolean z = obtainStyledAttributes.getBoolean(10, false);
        int i3 = obtainStyledAttributes.getInt(8, 1);
        obtainStyledAttributes.recycle();
        if (i3 == 2 && z) {
            this.mMode = context.getResources().getInteger(com.android.internal.R.integer.time_picker_mode);
        } else {
            this.mMode = i3;
        }
        switch (this.mMode) {
            case 2:
                this.mDelegate = new android.widget.TimePickerClockDelegate(this, context, attributeSet, i, i2);
                break;
            default:
                this.mDelegate = new android.widget.TimePickerSpinnerDelegate(this, context, attributeSet, i, i2);
                break;
        }
        this.mDelegate.setAutoFillChangeListener(new android.widget.TimePicker.OnTimeChangedListener() { // from class: android.widget.TimePicker$$ExternalSyntheticLambda0
            @Override // android.widget.TimePicker.OnTimeChangedListener
            public final void onTimeChanged(android.widget.TimePicker timePicker, int i4, int i5) {
                android.widget.TimePicker.this.lambda$new$0(context, timePicker, i4, i5);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(android.content.Context context, android.widget.TimePicker timePicker, int i, int i2) {
        android.view.autofill.AutofillManager autofillManager = (android.view.autofill.AutofillManager) context.getSystemService(android.view.autofill.AutofillManager.class);
        if (autofillManager != null) {
            autofillManager.notifyValueChanged(this);
        }
    }

    public int getMode() {
        return this.mMode;
    }

    public void setHour(int i) {
        this.mDelegate.setHour(android.util.MathUtils.constrain(i, 0, 23));
    }

    public int getHour() {
        return this.mDelegate.getHour();
    }

    public void setMinute(int i) {
        this.mDelegate.setMinute(android.util.MathUtils.constrain(i, 0, 59));
    }

    public int getMinute() {
        return this.mDelegate.getMinute();
    }

    @java.lang.Deprecated
    public void setCurrentHour(java.lang.Integer num) {
        setHour(num.intValue());
    }

    @java.lang.Deprecated
    public java.lang.Integer getCurrentHour() {
        return java.lang.Integer.valueOf(getHour());
    }

    @java.lang.Deprecated
    public void setCurrentMinute(java.lang.Integer num) {
        setMinute(num.intValue());
    }

    @java.lang.Deprecated
    public java.lang.Integer getCurrentMinute() {
        return java.lang.Integer.valueOf(getMinute());
    }

    public void setIs24HourView(java.lang.Boolean bool) {
        if (bool == null) {
            return;
        }
        this.mDelegate.setIs24Hour(bool.booleanValue());
    }

    public boolean is24HourView() {
        return this.mDelegate.is24Hour();
    }

    public void setOnTimeChangedListener(android.widget.TimePicker.OnTimeChangedListener onTimeChangedListener) {
        this.mDelegate.setOnTimeChangedListener(onTimeChangedListener);
    }

    @Override // android.view.View
    public void setEnabled(boolean z) {
        super.setEnabled(z);
        this.mDelegate.setEnabled(z);
    }

    @Override // android.view.View
    public boolean isEnabled() {
        return this.mDelegate.isEnabled();
    }

    @Override // android.view.View
    public int getBaseline() {
        return this.mDelegate.getBaseline();
    }

    public boolean validateInput() {
        return this.mDelegate.validateInput();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.View
    public android.os.Parcelable onSaveInstanceState() {
        return this.mDelegate.onSaveInstanceState(super.onSaveInstanceState());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.View
    public void onRestoreInstanceState(android.os.Parcelable parcelable) {
        android.view.View.BaseSavedState baseSavedState = (android.view.View.BaseSavedState) parcelable;
        super.onRestoreInstanceState(baseSavedState.getSuperState());
        this.mDelegate.onRestoreInstanceState(baseSavedState);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public java.lang.CharSequence getAccessibilityClassName() {
        return android.widget.TimePicker.class.getName();
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchPopulateAccessibilityEventInternal(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        return this.mDelegate.dispatchPopulateAccessibilityEvent(accessibilityEvent);
    }

    public android.view.View getHourView() {
        return this.mDelegate.getHourView();
    }

    public android.view.View getMinuteView() {
        return this.mDelegate.getMinuteView();
    }

    public android.view.View getAmView() {
        return this.mDelegate.getAmView();
    }

    public android.view.View getPmView() {
        return this.mDelegate.getPmView();
    }

    static java.lang.String[] getAmPmStrings(android.content.Context context) {
        android.icu.text.DateFormatSymbols icuDateFormatSymbols = android.text.format.DateFormat.getIcuDateFormatSymbols(context.getResources().getConfiguration().locale);
        java.lang.String[] amPmStrings = icuDateFormatSymbols.getAmPmStrings();
        java.lang.String[] ampmNarrowStrings = icuDateFormatSymbols.getAmpmNarrowStrings();
        return new java.lang.String[]{amPmStrings[0].length() > 4 ? ampmNarrowStrings[0] : amPmStrings[0], amPmStrings[1].length() > 4 ? ampmNarrowStrings[1] : amPmStrings[1]};
    }

    static abstract class AbstractTimePickerDelegate implements android.widget.TimePicker.TimePickerDelegate {
        protected android.widget.TimePicker.OnTimeChangedListener mAutoFillChangeListener;
        private long mAutofilledValue;
        protected final android.content.Context mContext;
        protected final android.widget.TimePicker mDelegator;
        protected final java.util.Locale mLocale;
        protected android.widget.TimePicker.OnTimeChangedListener mOnTimeChangedListener;

        public AbstractTimePickerDelegate(android.widget.TimePicker timePicker, android.content.Context context) {
            this.mDelegator = timePicker;
            this.mContext = context;
            this.mLocale = context.getResources().getConfiguration().locale;
        }

        @Override // android.widget.TimePicker.TimePickerDelegate
        public void setOnTimeChangedListener(android.widget.TimePicker.OnTimeChangedListener onTimeChangedListener) {
            this.mOnTimeChangedListener = onTimeChangedListener;
        }

        @Override // android.widget.TimePicker.TimePickerDelegate
        public void setAutoFillChangeListener(android.widget.TimePicker.OnTimeChangedListener onTimeChangedListener) {
            this.mAutoFillChangeListener = onTimeChangedListener;
        }

        @Override // android.widget.TimePicker.TimePickerDelegate
        public final void autofill(android.view.autofill.AutofillValue autofillValue) {
            if (autofillValue == null || !autofillValue.isDate()) {
                android.util.Log.w(android.widget.TimePicker.LOG_TAG, autofillValue + " could not be autofilled into " + this);
                return;
            }
            long dateValue = autofillValue.getDateValue();
            android.icu.util.Calendar calendar = android.icu.util.Calendar.getInstance(this.mLocale);
            calendar.setTimeInMillis(dateValue);
            setDate(calendar.get(11), calendar.get(12));
            this.mAutofilledValue = dateValue;
        }

        @Override // android.widget.TimePicker.TimePickerDelegate
        public final android.view.autofill.AutofillValue getAutofillValue() {
            if (this.mAutofilledValue != 0) {
                return android.view.autofill.AutofillValue.forDate(this.mAutofilledValue);
            }
            android.icu.util.Calendar calendar = android.icu.util.Calendar.getInstance(this.mLocale);
            calendar.set(11, getHour());
            calendar.set(12, getMinute());
            return android.view.autofill.AutofillValue.forDate(calendar.getTimeInMillis());
        }

        protected void resetAutofilledValue() {
            this.mAutofilledValue = 0L;
        }

        protected static class SavedState extends android.view.View.BaseSavedState {
            public static final android.os.Parcelable.Creator<android.widget.TimePicker.AbstractTimePickerDelegate.SavedState> CREATOR = new android.os.Parcelable.Creator<android.widget.TimePicker.AbstractTimePickerDelegate.SavedState>() { // from class: android.widget.TimePicker.AbstractTimePickerDelegate.SavedState.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public android.widget.TimePicker.AbstractTimePickerDelegate.SavedState createFromParcel(android.os.Parcel parcel) {
                    return new android.widget.TimePicker.AbstractTimePickerDelegate.SavedState(parcel);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public android.widget.TimePicker.AbstractTimePickerDelegate.SavedState[] newArray(int i) {
                    return new android.widget.TimePicker.AbstractTimePickerDelegate.SavedState[i];
                }
            };
            private final int mCurrentItemShowing;
            private final int mHour;
            private final boolean mIs24HourMode;
            private final int mMinute;

            public SavedState(android.os.Parcelable parcelable, int i, int i2, boolean z) {
                this(parcelable, i, i2, z, 0);
            }

            public SavedState(android.os.Parcelable parcelable, int i, int i2, boolean z, int i3) {
                super(parcelable);
                this.mHour = i;
                this.mMinute = i2;
                this.mIs24HourMode = z;
                this.mCurrentItemShowing = i3;
            }

            private SavedState(android.os.Parcel parcel) {
                super(parcel);
                this.mHour = parcel.readInt();
                this.mMinute = parcel.readInt();
                this.mIs24HourMode = parcel.readInt() == 1;
                this.mCurrentItemShowing = parcel.readInt();
            }

            public int getHour() {
                return this.mHour;
            }

            public int getMinute() {
                return this.mMinute;
            }

            public boolean is24HourMode() {
                return this.mIs24HourMode;
            }

            public int getCurrentItemShowing() {
                return this.mCurrentItemShowing;
            }

            @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
            public void writeToParcel(android.os.Parcel parcel, int i) {
                super.writeToParcel(parcel, i);
                parcel.writeInt(this.mHour);
                parcel.writeInt(this.mMinute);
                parcel.writeInt(this.mIs24HourMode ? 1 : 0);
                parcel.writeInt(this.mCurrentItemShowing);
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void dispatchProvideAutofillStructure(android.view.ViewStructure viewStructure, int i) {
        viewStructure.setAutofillId(getAutofillId());
        onProvideAutofillStructure(viewStructure, i);
    }

    @Override // android.view.View
    public void autofill(android.view.autofill.AutofillValue autofillValue) {
        if (isEnabled()) {
            this.mDelegate.autofill(autofillValue);
        }
    }

    @Override // android.view.View
    public int getAutofillType() {
        return isEnabled() ? 4 : 0;
    }

    @Override // android.view.View
    public android.view.autofill.AutofillValue getAutofillValue() {
        if (isEnabled()) {
            return this.mDelegate.getAutofillValue();
        }
        return null;
    }
}
