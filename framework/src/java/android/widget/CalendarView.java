package android.widget;

/* loaded from: classes4.dex */
public class CalendarView extends android.widget.FrameLayout {
    private static final java.lang.String DATE_FORMAT = "MM/dd/yyyy";
    private static final java.text.DateFormat DATE_FORMATTER = new java.text.SimpleDateFormat(DATE_FORMAT);
    private static final java.lang.String LOG_TAG = "CalendarView";
    private static final int MODE_HOLO = 0;
    private static final int MODE_MATERIAL = 1;
    private final android.widget.CalendarView.CalendarViewDelegate mDelegate;

    private interface CalendarViewDelegate {
        boolean getBoundsForDate(long j, android.graphics.Rect rect);

        long getDate();

        int getDateTextAppearance();

        int getFirstDayOfWeek();

        int getFocusedMonthDateColor();

        long getMaxDate();

        long getMinDate();

        android.graphics.drawable.Drawable getSelectedDateVerticalBar();

        int getSelectedWeekBackgroundColor();

        boolean getShowWeekNumber();

        int getShownWeekCount();

        int getUnfocusedMonthDateColor();

        int getWeekDayTextAppearance();

        int getWeekNumberColor();

        int getWeekSeparatorLineColor();

        void onConfigurationChanged(android.content.res.Configuration configuration);

        void setDate(long j);

        void setDate(long j, boolean z, boolean z2);

        void setDateTextAppearance(int i);

        void setFirstDayOfWeek(int i);

        void setFocusedMonthDateColor(int i);

        void setMaxDate(long j);

        void setMinDate(long j);

        void setOnDateChangeListener(android.widget.CalendarView.OnDateChangeListener onDateChangeListener);

        void setSelectedDateVerticalBar(int i);

        void setSelectedDateVerticalBar(android.graphics.drawable.Drawable drawable);

        void setSelectedWeekBackgroundColor(int i);

        void setShowWeekNumber(boolean z);

        void setShownWeekCount(int i);

        void setUnfocusedMonthDateColor(int i);

        void setWeekDayTextAppearance(int i);

        void setWeekNumberColor(int i);

        void setWeekSeparatorLineColor(int i);
    }

    public interface OnDateChangeListener {
        void onSelectedDayChange(android.widget.CalendarView calendarView, int i, int i2, int i3);
    }

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<android.widget.CalendarView> {
        private int mDateTextAppearanceId;
        private int mFirstDayOfWeekId;
        private int mFocusedMonthDateColorId;
        private int mMaxDateId;
        private int mMinDateId;
        private boolean mPropertiesMapped = false;
        private int mSelectedDateVerticalBarId;
        private int mSelectedWeekBackgroundColorId;
        private int mShowWeekNumberId;
        private int mShownWeekCountId;
        private int mUnfocusedMonthDateColorId;
        private int mWeekDayTextAppearanceId;
        private int mWeekNumberColorId;
        private int mWeekSeparatorLineColorId;

        @Override // android.view.inspector.InspectionCompanion
        public void mapProperties(android.view.inspector.PropertyMapper propertyMapper) {
            this.mDateTextAppearanceId = propertyMapper.mapResourceId("dateTextAppearance", 16843593);
            this.mFirstDayOfWeekId = propertyMapper.mapInt("firstDayOfWeek", 16843581);
            this.mFocusedMonthDateColorId = propertyMapper.mapColor("focusedMonthDateColor", 16843587);
            this.mMaxDateId = propertyMapper.mapLong("maxDate", 16843584);
            this.mMinDateId = propertyMapper.mapLong("minDate", 16843583);
            this.mSelectedDateVerticalBarId = propertyMapper.mapObject("selectedDateVerticalBar", 16843591);
            this.mSelectedWeekBackgroundColorId = propertyMapper.mapColor("selectedWeekBackgroundColor", 16843586);
            this.mShowWeekNumberId = propertyMapper.mapBoolean("showWeekNumber", 16843582);
            this.mShownWeekCountId = propertyMapper.mapInt("shownWeekCount", 16843585);
            this.mUnfocusedMonthDateColorId = propertyMapper.mapColor("unfocusedMonthDateColor", 16843588);
            this.mWeekDayTextAppearanceId = propertyMapper.mapResourceId("weekDayTextAppearance", 16843592);
            this.mWeekNumberColorId = propertyMapper.mapColor("weekNumberColor", 16843589);
            this.mWeekSeparatorLineColorId = propertyMapper.mapColor("weekSeparatorLineColor", 16843590);
            this.mPropertiesMapped = true;
        }

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(android.widget.CalendarView calendarView, android.view.inspector.PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new android.view.inspector.InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readResourceId(this.mDateTextAppearanceId, calendarView.getDateTextAppearance());
            propertyReader.readInt(this.mFirstDayOfWeekId, calendarView.getFirstDayOfWeek());
            propertyReader.readColor(this.mFocusedMonthDateColorId, calendarView.getFocusedMonthDateColor());
            propertyReader.readLong(this.mMaxDateId, calendarView.getMaxDate());
            propertyReader.readLong(this.mMinDateId, calendarView.getMinDate());
            propertyReader.readObject(this.mSelectedDateVerticalBarId, calendarView.getSelectedDateVerticalBar());
            propertyReader.readColor(this.mSelectedWeekBackgroundColorId, calendarView.getSelectedWeekBackgroundColor());
            propertyReader.readBoolean(this.mShowWeekNumberId, calendarView.getShowWeekNumber());
            propertyReader.readInt(this.mShownWeekCountId, calendarView.getShownWeekCount());
            propertyReader.readColor(this.mUnfocusedMonthDateColorId, calendarView.getUnfocusedMonthDateColor());
            propertyReader.readResourceId(this.mWeekDayTextAppearanceId, calendarView.getWeekDayTextAppearance());
            propertyReader.readColor(this.mWeekNumberColorId, calendarView.getWeekNumberColor());
            propertyReader.readColor(this.mWeekSeparatorLineColorId, calendarView.getWeekSeparatorLineColor());
        }
    }

    public CalendarView(android.content.Context context) {
        this(context, null);
    }

    public CalendarView(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 16843613);
    }

    public CalendarView(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public CalendarView(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.CalendarView, i, i2);
        saveAttributeDataForStyleable(context, com.android.internal.R.styleable.CalendarView, attributeSet, obtainStyledAttributes, i, i2);
        int i3 = obtainStyledAttributes.getInt(13, 0);
        obtainStyledAttributes.recycle();
        switch (i3) {
            case 0:
                this.mDelegate = new android.widget.CalendarViewLegacyDelegate(this, context, attributeSet, i, i2);
                return;
            case 1:
                this.mDelegate = new android.widget.CalendarViewMaterialDelegate(this, context, attributeSet, i, i2);
                return;
            default:
                throw new java.lang.IllegalArgumentException("invalid calendarViewMode attribute");
        }
    }

    @java.lang.Deprecated
    public void setShownWeekCount(int i) {
        this.mDelegate.setShownWeekCount(i);
    }

    @java.lang.Deprecated
    public int getShownWeekCount() {
        return this.mDelegate.getShownWeekCount();
    }

    @java.lang.Deprecated
    public void setSelectedWeekBackgroundColor(int i) {
        this.mDelegate.setSelectedWeekBackgroundColor(i);
    }

    @java.lang.Deprecated
    public int getSelectedWeekBackgroundColor() {
        return this.mDelegate.getSelectedWeekBackgroundColor();
    }

    @java.lang.Deprecated
    public void setFocusedMonthDateColor(int i) {
        this.mDelegate.setFocusedMonthDateColor(i);
    }

    @java.lang.Deprecated
    public int getFocusedMonthDateColor() {
        return this.mDelegate.getFocusedMonthDateColor();
    }

    @java.lang.Deprecated
    public void setUnfocusedMonthDateColor(int i) {
        this.mDelegate.setUnfocusedMonthDateColor(i);
    }

    @java.lang.Deprecated
    public int getUnfocusedMonthDateColor() {
        return this.mDelegate.getUnfocusedMonthDateColor();
    }

    @java.lang.Deprecated
    public void setWeekNumberColor(int i) {
        this.mDelegate.setWeekNumberColor(i);
    }

    @java.lang.Deprecated
    public int getWeekNumberColor() {
        return this.mDelegate.getWeekNumberColor();
    }

    @java.lang.Deprecated
    public void setWeekSeparatorLineColor(int i) {
        this.mDelegate.setWeekSeparatorLineColor(i);
    }

    @java.lang.Deprecated
    public int getWeekSeparatorLineColor() {
        return this.mDelegate.getWeekSeparatorLineColor();
    }

    @java.lang.Deprecated
    public void setSelectedDateVerticalBar(int i) {
        this.mDelegate.setSelectedDateVerticalBar(i);
    }

    @java.lang.Deprecated
    public void setSelectedDateVerticalBar(android.graphics.drawable.Drawable drawable) {
        this.mDelegate.setSelectedDateVerticalBar(drawable);
    }

    @java.lang.Deprecated
    public android.graphics.drawable.Drawable getSelectedDateVerticalBar() {
        return this.mDelegate.getSelectedDateVerticalBar();
    }

    public void setWeekDayTextAppearance(int i) {
        this.mDelegate.setWeekDayTextAppearance(i);
    }

    public int getWeekDayTextAppearance() {
        return this.mDelegate.getWeekDayTextAppearance();
    }

    public void setDateTextAppearance(int i) {
        this.mDelegate.setDateTextAppearance(i);
    }

    public int getDateTextAppearance() {
        return this.mDelegate.getDateTextAppearance();
    }

    public long getMinDate() {
        return this.mDelegate.getMinDate();
    }

    public void setMinDate(long j) {
        this.mDelegate.setMinDate(j);
    }

    public long getMaxDate() {
        return this.mDelegate.getMaxDate();
    }

    public void setMaxDate(long j) {
        this.mDelegate.setMaxDate(j);
    }

    @java.lang.Deprecated
    public void setShowWeekNumber(boolean z) {
        this.mDelegate.setShowWeekNumber(z);
    }

    @java.lang.Deprecated
    public boolean getShowWeekNumber() {
        return this.mDelegate.getShowWeekNumber();
    }

    public int getFirstDayOfWeek() {
        return this.mDelegate.getFirstDayOfWeek();
    }

    public void setFirstDayOfWeek(int i) {
        this.mDelegate.setFirstDayOfWeek(i);
    }

    public void setOnDateChangeListener(android.widget.CalendarView.OnDateChangeListener onDateChangeListener) {
        this.mDelegate.setOnDateChangeListener(onDateChangeListener);
    }

    public long getDate() {
        return this.mDelegate.getDate();
    }

    public void setDate(long j) {
        this.mDelegate.setDate(j);
    }

    public void setDate(long j, boolean z, boolean z2) {
        this.mDelegate.setDate(j, z, z2);
    }

    public boolean getBoundsForDate(long j, android.graphics.Rect rect) {
        return this.mDelegate.getBoundsForDate(j, rect);
    }

    @Override // android.view.View
    protected void onConfigurationChanged(android.content.res.Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mDelegate.onConfigurationChanged(configuration);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public java.lang.CharSequence getAccessibilityClassName() {
        return android.widget.CalendarView.class.getName();
    }

    static abstract class AbstractCalendarViewDelegate implements android.widget.CalendarView.CalendarViewDelegate {
        protected static final java.lang.String DEFAULT_MAX_DATE = "01/01/2100";
        protected static final java.lang.String DEFAULT_MIN_DATE = "01/01/1900";
        protected android.content.Context mContext;
        protected java.util.Locale mCurrentLocale;
        protected android.widget.CalendarView mDelegator;

        AbstractCalendarViewDelegate(android.widget.CalendarView calendarView, android.content.Context context) {
            this.mDelegator = calendarView;
            this.mContext = context;
            setCurrentLocale(java.util.Locale.getDefault());
        }

        protected void setCurrentLocale(java.util.Locale locale) {
            if (locale.equals(this.mCurrentLocale)) {
                return;
            }
            this.mCurrentLocale = locale;
        }

        @Override // android.widget.CalendarView.CalendarViewDelegate
        public void setShownWeekCount(int i) {
        }

        @Override // android.widget.CalendarView.CalendarViewDelegate
        public int getShownWeekCount() {
            return 0;
        }

        @Override // android.widget.CalendarView.CalendarViewDelegate
        public void setSelectedWeekBackgroundColor(int i) {
        }

        @Override // android.widget.CalendarView.CalendarViewDelegate
        public int getSelectedWeekBackgroundColor() {
            return 0;
        }

        @Override // android.widget.CalendarView.CalendarViewDelegate
        public void setFocusedMonthDateColor(int i) {
        }

        @Override // android.widget.CalendarView.CalendarViewDelegate
        public int getFocusedMonthDateColor() {
            return 0;
        }

        @Override // android.widget.CalendarView.CalendarViewDelegate
        public void setUnfocusedMonthDateColor(int i) {
        }

        @Override // android.widget.CalendarView.CalendarViewDelegate
        public int getUnfocusedMonthDateColor() {
            return 0;
        }

        @Override // android.widget.CalendarView.CalendarViewDelegate
        public void setWeekNumberColor(int i) {
        }

        @Override // android.widget.CalendarView.CalendarViewDelegate
        public int getWeekNumberColor() {
            return 0;
        }

        @Override // android.widget.CalendarView.CalendarViewDelegate
        public void setWeekSeparatorLineColor(int i) {
        }

        @Override // android.widget.CalendarView.CalendarViewDelegate
        public int getWeekSeparatorLineColor() {
            return 0;
        }

        @Override // android.widget.CalendarView.CalendarViewDelegate
        public void setSelectedDateVerticalBar(int i) {
        }

        @Override // android.widget.CalendarView.CalendarViewDelegate
        public void setSelectedDateVerticalBar(android.graphics.drawable.Drawable drawable) {
        }

        @Override // android.widget.CalendarView.CalendarViewDelegate
        public android.graphics.drawable.Drawable getSelectedDateVerticalBar() {
            return null;
        }

        @Override // android.widget.CalendarView.CalendarViewDelegate
        public void setShowWeekNumber(boolean z) {
        }

        @Override // android.widget.CalendarView.CalendarViewDelegate
        public boolean getShowWeekNumber() {
            return false;
        }

        @Override // android.widget.CalendarView.CalendarViewDelegate
        public void onConfigurationChanged(android.content.res.Configuration configuration) {
        }
    }

    public static boolean parseDate(java.lang.String str, android.icu.util.Calendar calendar) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        try {
            calendar.setTime(DATE_FORMATTER.parse(str));
            return true;
        } catch (java.text.ParseException e) {
            android.util.Log.w(LOG_TAG, "Date: " + str + " not in format: " + DATE_FORMAT);
            return false;
        }
    }
}
