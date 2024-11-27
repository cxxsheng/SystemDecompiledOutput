package android.widget;

/* loaded from: classes4.dex */
class CalendarViewMaterialDelegate extends android.widget.CalendarView.AbstractCalendarViewDelegate {
    private final android.widget.DayPickerView mDayPickerView;
    private android.widget.CalendarView.OnDateChangeListener mOnDateChangeListener;
    private final android.widget.DayPickerView.OnDaySelectedListener mOnDaySelectedListener;

    public CalendarViewMaterialDelegate(android.widget.CalendarView calendarView, android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(calendarView, context);
        this.mOnDaySelectedListener = new android.widget.DayPickerView.OnDaySelectedListener() { // from class: android.widget.CalendarViewMaterialDelegate.1
            @Override // android.widget.DayPickerView.OnDaySelectedListener
            public void onDaySelected(android.widget.DayPickerView dayPickerView, android.icu.util.Calendar calendar) {
                if (android.widget.CalendarViewMaterialDelegate.this.mOnDateChangeListener != null) {
                    android.widget.CalendarViewMaterialDelegate.this.mOnDateChangeListener.onSelectedDayChange(android.widget.CalendarViewMaterialDelegate.this.mDelegator, calendar.get(1), calendar.get(2), calendar.get(5));
                }
            }
        };
        this.mDayPickerView = new android.widget.DayPickerView(context, attributeSet, i, i2);
        this.mDayPickerView.setOnDaySelectedListener(this.mOnDaySelectedListener);
        calendarView.addView(this.mDayPickerView);
    }

    @Override // android.widget.CalendarView.CalendarViewDelegate
    public void setWeekDayTextAppearance(int i) {
        this.mDayPickerView.setDayOfWeekTextAppearance(i);
    }

    @Override // android.widget.CalendarView.CalendarViewDelegate
    public int getWeekDayTextAppearance() {
        return this.mDayPickerView.getDayOfWeekTextAppearance();
    }

    @Override // android.widget.CalendarView.CalendarViewDelegate
    public void setDateTextAppearance(int i) {
        this.mDayPickerView.setDayTextAppearance(i);
    }

    @Override // android.widget.CalendarView.CalendarViewDelegate
    public int getDateTextAppearance() {
        return this.mDayPickerView.getDayTextAppearance();
    }

    @Override // android.widget.CalendarView.CalendarViewDelegate
    public void setMinDate(long j) {
        this.mDayPickerView.setMinDate(j);
    }

    @Override // android.widget.CalendarView.CalendarViewDelegate
    public long getMinDate() {
        return this.mDayPickerView.getMinDate();
    }

    @Override // android.widget.CalendarView.CalendarViewDelegate
    public void setMaxDate(long j) {
        this.mDayPickerView.setMaxDate(j);
    }

    @Override // android.widget.CalendarView.CalendarViewDelegate
    public long getMaxDate() {
        return this.mDayPickerView.getMaxDate();
    }

    @Override // android.widget.CalendarView.CalendarViewDelegate
    public void setFirstDayOfWeek(int i) {
        this.mDayPickerView.setFirstDayOfWeek(i);
    }

    @Override // android.widget.CalendarView.CalendarViewDelegate
    public int getFirstDayOfWeek() {
        return this.mDayPickerView.getFirstDayOfWeek();
    }

    @Override // android.widget.CalendarView.CalendarViewDelegate
    public void setDate(long j) {
        this.mDayPickerView.setDate(j, true);
    }

    @Override // android.widget.CalendarView.CalendarViewDelegate
    public void setDate(long j, boolean z, boolean z2) {
        this.mDayPickerView.setDate(j, z);
    }

    @Override // android.widget.CalendarView.CalendarViewDelegate
    public long getDate() {
        return this.mDayPickerView.getDate();
    }

    @Override // android.widget.CalendarView.CalendarViewDelegate
    public void setOnDateChangeListener(android.widget.CalendarView.OnDateChangeListener onDateChangeListener) {
        this.mOnDateChangeListener = onDateChangeListener;
    }

    @Override // android.widget.CalendarView.CalendarViewDelegate
    public boolean getBoundsForDate(long j, android.graphics.Rect rect) {
        if (this.mDayPickerView.getBoundsForDate(j, rect)) {
            int[] iArr = new int[2];
            int[] iArr2 = new int[2];
            this.mDayPickerView.getLocationOnScreen(iArr);
            this.mDelegator.getLocationOnScreen(iArr2);
            int i = iArr[1] - iArr2[1];
            rect.top += i;
            rect.bottom += i;
            return true;
        }
        return false;
    }
}
