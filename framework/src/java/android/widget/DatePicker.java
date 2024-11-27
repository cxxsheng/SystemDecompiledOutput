package android.widget;

/* loaded from: classes4.dex */
public class DatePicker extends android.widget.FrameLayout {
    private static final java.lang.String LOG_TAG = android.widget.DatePicker.class.getSimpleName();
    public static final int MODE_CALENDAR = 2;
    public static final int MODE_SPINNER = 1;
    private final android.widget.DatePicker.DatePickerDelegate mDelegate;
    private final int mMode;

    interface DatePickerDelegate {
        void autofill(android.view.autofill.AutofillValue autofillValue);

        boolean dispatchPopulateAccessibilityEvent(android.view.accessibility.AccessibilityEvent accessibilityEvent);

        android.view.autofill.AutofillValue getAutofillValue();

        android.widget.CalendarView getCalendarView();

        boolean getCalendarViewShown();

        int getDayOfMonth();

        int getFirstDayOfWeek();

        android.icu.util.Calendar getMaxDate();

        android.icu.util.Calendar getMinDate();

        int getMonth();

        boolean getSpinnersShown();

        int getYear();

        void init(int i, int i2, int i3, android.widget.DatePicker.OnDateChangedListener onDateChangedListener);

        boolean isEnabled();

        void onConfigurationChanged(android.content.res.Configuration configuration);

        void onPopulateAccessibilityEvent(android.view.accessibility.AccessibilityEvent accessibilityEvent);

        void onRestoreInstanceState(android.os.Parcelable parcelable);

        android.os.Parcelable onSaveInstanceState(android.os.Parcelable parcelable);

        void setAutoFillChangeListener(android.widget.DatePicker.OnDateChangedListener onDateChangedListener);

        void setCalendarViewShown(boolean z);

        void setEnabled(boolean z);

        void setFirstDayOfWeek(int i);

        void setMaxDate(long j);

        void setMinDate(long j);

        void setOnDateChangedListener(android.widget.DatePicker.OnDateChangedListener onDateChangedListener);

        void setSpinnersShown(boolean z);

        void setValidationCallback(android.widget.DatePicker.ValidationCallback validationCallback);

        void updateDate(int i, int i2, int i3);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DatePickerMode {
    }

    public interface OnDateChangedListener {
        void onDateChanged(android.widget.DatePicker datePicker, int i, int i2, int i3);
    }

    public interface ValidationCallback {
        void onValidationChanged(boolean z);
    }

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<android.widget.DatePicker> {
        private int mCalendarViewShownId;
        private int mDatePickerModeId;
        private int mDayOfMonthId;
        private int mFirstDayOfWeekId;
        private int mMaxDateId;
        private int mMinDateId;
        private int mMonthId;
        private boolean mPropertiesMapped = false;
        private int mSpinnersShownId;
        private int mYearId;

        @Override // android.view.inspector.InspectionCompanion
        public void mapProperties(android.view.inspector.PropertyMapper propertyMapper) {
            this.mCalendarViewShownId = propertyMapper.mapBoolean("calendarViewShown", 16843596);
            android.util.SparseArray sparseArray = new android.util.SparseArray();
            sparseArray.put(1, "spinner");
            sparseArray.put(2, "calendar");
            java.util.Objects.requireNonNull(sparseArray);
            this.mDatePickerModeId = propertyMapper.mapIntEnum("datePickerMode", 16843955, new android.view.View$InspectionCompanion$$ExternalSyntheticLambda0(sparseArray));
            this.mDayOfMonthId = propertyMapper.mapInt("dayOfMonth", 0);
            this.mFirstDayOfWeekId = propertyMapper.mapInt("firstDayOfWeek", 16843581);
            this.mMaxDateId = propertyMapper.mapLong("maxDate", 16843584);
            this.mMinDateId = propertyMapper.mapLong("minDate", 16843583);
            this.mMonthId = propertyMapper.mapInt("month", 0);
            this.mSpinnersShownId = propertyMapper.mapBoolean("spinnersShown", 16843595);
            this.mYearId = propertyMapper.mapInt("year", 0);
            this.mPropertiesMapped = true;
        }

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(android.widget.DatePicker datePicker, android.view.inspector.PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new android.view.inspector.InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readBoolean(this.mCalendarViewShownId, datePicker.getCalendarViewShown());
            propertyReader.readIntEnum(this.mDatePickerModeId, datePicker.getMode());
            propertyReader.readInt(this.mDayOfMonthId, datePicker.getDayOfMonth());
            propertyReader.readInt(this.mFirstDayOfWeekId, datePicker.getFirstDayOfWeek());
            propertyReader.readLong(this.mMaxDateId, datePicker.getMaxDate());
            propertyReader.readLong(this.mMinDateId, datePicker.getMinDate());
            propertyReader.readInt(this.mMonthId, datePicker.getMonth());
            propertyReader.readBoolean(this.mSpinnersShownId, datePicker.getSpinnersShown());
            propertyReader.readInt(this.mYearId, datePicker.getYear());
        }
    }

    public DatePicker(android.content.Context context) {
        this(context, null);
    }

    public DatePicker(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 16843612);
    }

    public DatePicker(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public DatePicker(final android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        if (getImportantForAutofill() == 0) {
            setImportantForAutofill(1);
        }
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.DatePicker, i, i2);
        saveAttributeDataForStyleable(context, com.android.internal.R.styleable.DatePicker, attributeSet, obtainStyledAttributes, i, i2);
        boolean z = obtainStyledAttributes.getBoolean(17, false);
        int i3 = obtainStyledAttributes.getInt(16, 1);
        int i4 = obtainStyledAttributes.getInt(3, 0);
        obtainStyledAttributes.recycle();
        if (i3 == 2 && z) {
            this.mMode = context.getResources().getInteger(com.android.internal.R.integer.date_picker_mode);
        } else {
            this.mMode = i3;
        }
        switch (this.mMode) {
            case 2:
                this.mDelegate = createCalendarUIDelegate(context, attributeSet, i, i2);
                break;
            default:
                this.mDelegate = createSpinnerUIDelegate(context, attributeSet, i, i2);
                break;
        }
        if (i4 != 0) {
            setFirstDayOfWeek(i4);
        }
        this.mDelegate.setAutoFillChangeListener(new android.widget.DatePicker.OnDateChangedListener() { // from class: android.widget.DatePicker$$ExternalSyntheticLambda0
            @Override // android.widget.DatePicker.OnDateChangedListener
            public final void onDateChanged(android.widget.DatePicker datePicker, int i5, int i6, int i7) {
                android.widget.DatePicker.this.lambda$new$0(context, datePicker, i5, i6, i7);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(android.content.Context context, android.widget.DatePicker datePicker, int i, int i2, int i3) {
        android.view.autofill.AutofillManager autofillManager = (android.view.autofill.AutofillManager) context.getSystemService(android.view.autofill.AutofillManager.class);
        if (autofillManager != null) {
            autofillManager.notifyValueChanged(this);
        }
    }

    private android.widget.DatePicker.DatePickerDelegate createSpinnerUIDelegate(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        return new android.widget.DatePickerSpinnerDelegate(this, context, attributeSet, i, i2);
    }

    private android.widget.DatePicker.DatePickerDelegate createCalendarUIDelegate(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        return new android.widget.DatePickerCalendarDelegate(this, context, attributeSet, i, i2);
    }

    public int getMode() {
        return this.mMode;
    }

    public void init(int i, int i2, int i3, android.widget.DatePicker.OnDateChangedListener onDateChangedListener) {
        this.mDelegate.init(i, i2, i3, onDateChangedListener);
    }

    public void setOnDateChangedListener(android.widget.DatePicker.OnDateChangedListener onDateChangedListener) {
        this.mDelegate.setOnDateChangedListener(onDateChangedListener);
    }

    public void updateDate(int i, int i2, int i3) {
        this.mDelegate.updateDate(i, i2, i3);
    }

    public int getYear() {
        return this.mDelegate.getYear();
    }

    public int getMonth() {
        return this.mDelegate.getMonth();
    }

    public int getDayOfMonth() {
        return this.mDelegate.getDayOfMonth();
    }

    public long getMinDate() {
        return this.mDelegate.getMinDate().getTimeInMillis();
    }

    public void setMinDate(long j) {
        this.mDelegate.setMinDate(j);
    }

    public long getMaxDate() {
        return this.mDelegate.getMaxDate().getTimeInMillis();
    }

    public void setMaxDate(long j) {
        this.mDelegate.setMaxDate(j);
    }

    public void setValidationCallback(android.widget.DatePicker.ValidationCallback validationCallback) {
        this.mDelegate.setValidationCallback(validationCallback);
    }

    @Override // android.view.View
    public void setEnabled(boolean z) {
        if (this.mDelegate.isEnabled() == z) {
            return;
        }
        super.setEnabled(z);
        this.mDelegate.setEnabled(z);
    }

    @Override // android.view.View
    public boolean isEnabled() {
        return this.mDelegate.isEnabled();
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchPopulateAccessibilityEventInternal(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        return this.mDelegate.dispatchPopulateAccessibilityEvent(accessibilityEvent);
    }

    @Override // android.view.View
    public void onPopulateAccessibilityEventInternal(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        super.onPopulateAccessibilityEventInternal(accessibilityEvent);
        this.mDelegate.onPopulateAccessibilityEvent(accessibilityEvent);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public java.lang.CharSequence getAccessibilityClassName() {
        return android.widget.DatePicker.class.getName();
    }

    @Override // android.view.View
    protected void onConfigurationChanged(android.content.res.Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mDelegate.onConfigurationChanged(configuration);
    }

    public void setFirstDayOfWeek(int i) {
        if (i < 1 || i > 7) {
            throw new java.lang.IllegalArgumentException("firstDayOfWeek must be between 1 and 7");
        }
        this.mDelegate.setFirstDayOfWeek(i);
    }

    public int getFirstDayOfWeek() {
        return this.mDelegate.getFirstDayOfWeek();
    }

    @java.lang.Deprecated
    public boolean getCalendarViewShown() {
        return this.mDelegate.getCalendarViewShown();
    }

    @java.lang.Deprecated
    public android.widget.CalendarView getCalendarView() {
        return this.mDelegate.getCalendarView();
    }

    @java.lang.Deprecated
    public void setCalendarViewShown(boolean z) {
        this.mDelegate.setCalendarViewShown(z);
    }

    @java.lang.Deprecated
    public boolean getSpinnersShown() {
        return this.mDelegate.getSpinnersShown();
    }

    @java.lang.Deprecated
    public void setSpinnersShown(boolean z) {
        this.mDelegate.setSpinnersShown(z);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchRestoreInstanceState(android.util.SparseArray<android.os.Parcelable> sparseArray) {
        dispatchThawSelfOnly(sparseArray);
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

    static abstract class AbstractDatePickerDelegate implements android.widget.DatePicker.DatePickerDelegate {
        protected android.widget.DatePicker.OnDateChangedListener mAutoFillChangeListener;
        private long mAutofilledValue;
        protected android.content.Context mContext;
        protected android.icu.util.Calendar mCurrentDate;
        protected java.util.Locale mCurrentLocale;
        protected android.widget.DatePicker mDelegator;
        protected android.widget.DatePicker.OnDateChangedListener mOnDateChangedListener;
        protected android.widget.DatePicker.ValidationCallback mValidationCallback;

        public AbstractDatePickerDelegate(android.widget.DatePicker datePicker, android.content.Context context) {
            this.mDelegator = datePicker;
            this.mContext = context;
            setCurrentLocale(java.util.Locale.getDefault());
        }

        protected void setCurrentLocale(java.util.Locale locale) {
            if (!locale.equals(this.mCurrentLocale)) {
                this.mCurrentLocale = locale;
                onLocaleChanged(locale);
            }
        }

        @Override // android.widget.DatePicker.DatePickerDelegate
        public void setOnDateChangedListener(android.widget.DatePicker.OnDateChangedListener onDateChangedListener) {
            this.mOnDateChangedListener = onDateChangedListener;
        }

        @Override // android.widget.DatePicker.DatePickerDelegate
        public void setAutoFillChangeListener(android.widget.DatePicker.OnDateChangedListener onDateChangedListener) {
            this.mAutoFillChangeListener = onDateChangedListener;
        }

        @Override // android.widget.DatePicker.DatePickerDelegate
        public void setValidationCallback(android.widget.DatePicker.ValidationCallback validationCallback) {
            this.mValidationCallback = validationCallback;
        }

        @Override // android.widget.DatePicker.DatePickerDelegate
        public final void autofill(android.view.autofill.AutofillValue autofillValue) {
            if (autofillValue == null || !autofillValue.isDate()) {
                android.util.Log.w(android.widget.DatePicker.LOG_TAG, autofillValue + " could not be autofilled into " + this);
                return;
            }
            long dateValue = autofillValue.getDateValue();
            android.icu.util.Calendar calendar = android.icu.util.Calendar.getInstance(this.mCurrentLocale);
            calendar.setTimeInMillis(dateValue);
            updateDate(calendar.get(1), calendar.get(2), calendar.get(5));
            this.mAutofilledValue = dateValue;
        }

        @Override // android.widget.DatePicker.DatePickerDelegate
        public final android.view.autofill.AutofillValue getAutofillValue() {
            long timeInMillis;
            if (this.mAutofilledValue != 0) {
                timeInMillis = this.mAutofilledValue;
            } else {
                timeInMillis = this.mCurrentDate.getTimeInMillis();
            }
            return android.view.autofill.AutofillValue.forDate(timeInMillis);
        }

        protected void resetAutofilledValue() {
            this.mAutofilledValue = 0L;
        }

        protected void onValidationChanged(boolean z) {
            if (this.mValidationCallback != null) {
                this.mValidationCallback.onValidationChanged(z);
            }
        }

        protected void onLocaleChanged(java.util.Locale locale) {
        }

        @Override // android.widget.DatePicker.DatePickerDelegate
        public void onPopulateAccessibilityEvent(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
            accessibilityEvent.getText().add(getFormattedCurrentDate());
        }

        protected java.lang.String getFormattedCurrentDate() {
            return android.text.format.DateUtils.formatDateTime(this.mContext, this.mCurrentDate.getTimeInMillis(), 22);
        }

        static class SavedState extends android.view.View.BaseSavedState {
            public static final android.os.Parcelable.Creator<android.widget.DatePicker.AbstractDatePickerDelegate.SavedState> CREATOR = new android.os.Parcelable.Creator<android.widget.DatePicker.AbstractDatePickerDelegate.SavedState>() { // from class: android.widget.DatePicker.AbstractDatePickerDelegate.SavedState.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public android.widget.DatePicker.AbstractDatePickerDelegate.SavedState createFromParcel(android.os.Parcel parcel) {
                    return new android.widget.DatePicker.AbstractDatePickerDelegate.SavedState(parcel);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public android.widget.DatePicker.AbstractDatePickerDelegate.SavedState[] newArray(int i) {
                    return new android.widget.DatePicker.AbstractDatePickerDelegate.SavedState[i];
                }
            };
            private final int mCurrentView;
            private final int mListPosition;
            private final int mListPositionOffset;
            private final long mMaxDate;
            private final long mMinDate;
            private final int mSelectedDay;
            private final int mSelectedMonth;
            private final int mSelectedYear;

            public SavedState(android.os.Parcelable parcelable, int i, int i2, int i3, long j, long j2) {
                this(parcelable, i, i2, i3, j, j2, 0, 0, 0);
            }

            public SavedState(android.os.Parcelable parcelable, int i, int i2, int i3, long j, long j2, int i4, int i5, int i6) {
                super(parcelable);
                this.mSelectedYear = i;
                this.mSelectedMonth = i2;
                this.mSelectedDay = i3;
                this.mMinDate = j;
                this.mMaxDate = j2;
                this.mCurrentView = i4;
                this.mListPosition = i5;
                this.mListPositionOffset = i6;
            }

            private SavedState(android.os.Parcel parcel) {
                super(parcel);
                this.mSelectedYear = parcel.readInt();
                this.mSelectedMonth = parcel.readInt();
                this.mSelectedDay = parcel.readInt();
                this.mMinDate = parcel.readLong();
                this.mMaxDate = parcel.readLong();
                this.mCurrentView = parcel.readInt();
                this.mListPosition = parcel.readInt();
                this.mListPositionOffset = parcel.readInt();
            }

            @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
            public void writeToParcel(android.os.Parcel parcel, int i) {
                super.writeToParcel(parcel, i);
                parcel.writeInt(this.mSelectedYear);
                parcel.writeInt(this.mSelectedMonth);
                parcel.writeInt(this.mSelectedDay);
                parcel.writeLong(this.mMinDate);
                parcel.writeLong(this.mMaxDate);
                parcel.writeInt(this.mCurrentView);
                parcel.writeInt(this.mListPosition);
                parcel.writeInt(this.mListPositionOffset);
            }

            public int getSelectedDay() {
                return this.mSelectedDay;
            }

            public int getSelectedMonth() {
                return this.mSelectedMonth;
            }

            public int getSelectedYear() {
                return this.mSelectedYear;
            }

            public long getMinDate() {
                return this.mMinDate;
            }

            public long getMaxDate() {
                return this.mMaxDate;
            }

            public int getCurrentView() {
                return this.mCurrentView;
            }

            public int getListPosition() {
                return this.mListPosition;
            }

            public int getListPositionOffset() {
                return this.mListPositionOffset;
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
