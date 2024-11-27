package android.widget;

/* loaded from: classes4.dex */
class CalendarViewLegacyDelegate extends android.widget.CalendarView.AbstractCalendarViewDelegate {
    private static final int ADJUSTMENT_SCROLL_DURATION = 500;
    private static final int DAYS_PER_WEEK = 7;
    private static final int DEFAULT_DATE_TEXT_SIZE = 14;
    private static final int DEFAULT_SHOWN_WEEK_COUNT = 6;
    private static final boolean DEFAULT_SHOW_WEEK_NUMBER = true;
    private static final int DEFAULT_WEEK_DAY_TEXT_APPEARANCE_RES_ID = -1;
    private static final int GOTO_SCROLL_DURATION = 1000;
    private static final long MILLIS_IN_DAY = 86400000;
    private static final long MILLIS_IN_WEEK = 604800000;
    private static final int SCROLL_CHANGE_DELAY = 40;
    private static final int SCROLL_HYST_WEEKS = 2;
    private static final int UNSCALED_BOTTOM_BUFFER = 20;
    private static final int UNSCALED_LIST_SCROLL_TOP_OFFSET = 2;
    private static final int UNSCALED_SELECTED_DATE_VERTICAL_BAR_WIDTH = 6;
    private static final int UNSCALED_WEEK_MIN_VISIBLE_HEIGHT = 12;
    private static final int UNSCALED_WEEK_SEPARATOR_LINE_WIDTH = 1;
    private android.widget.CalendarViewLegacyDelegate.WeeksAdapter mAdapter;
    private int mBottomBuffer;
    private int mCurrentMonthDisplayed;
    private int mCurrentScrollState;
    private int mDateTextAppearanceResId;
    private int mDateTextSize;
    private android.view.ViewGroup mDayNamesHeader;
    private java.lang.String[] mDayNamesLong;
    private java.lang.String[] mDayNamesShort;
    private int mDaysPerWeek;
    private android.icu.util.Calendar mFirstDayOfMonth;
    private int mFirstDayOfWeek;
    private int mFocusedMonthDateColor;
    private float mFriction;
    private boolean mIsScrollingUp;
    private int mListScrollTopOffset;
    private android.widget.ListView mListView;
    private android.icu.util.Calendar mMaxDate;
    private android.icu.util.Calendar mMinDate;
    private android.widget.TextView mMonthName;
    private android.widget.CalendarView.OnDateChangeListener mOnDateChangeListener;
    private long mPreviousScrollPosition;
    private int mPreviousScrollState;
    private android.widget.CalendarViewLegacyDelegate.ScrollStateRunnable mScrollStateChangedRunnable;
    private android.graphics.drawable.Drawable mSelectedDateVerticalBar;
    private final int mSelectedDateVerticalBarWidth;
    private int mSelectedWeekBackgroundColor;
    private boolean mShowWeekNumber;
    private int mShownWeekCount;
    private android.icu.util.Calendar mTempDate;
    private int mUnfocusedMonthDateColor;
    private float mVelocityScale;
    private int mWeekDayTextAppearanceResId;
    private int mWeekMinVisibleHeight;
    private int mWeekNumberColor;
    private int mWeekSeparatorLineColor;
    private final int mWeekSeparatorLineWidth;

    CalendarViewLegacyDelegate(android.widget.CalendarView calendarView, android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(calendarView, context);
        this.mListScrollTopOffset = 2;
        this.mWeekMinVisibleHeight = 12;
        this.mBottomBuffer = 20;
        this.mDaysPerWeek = 7;
        this.mFriction = 0.05f;
        this.mVelocityScale = 0.333f;
        this.mCurrentMonthDisplayed = -1;
        this.mIsScrollingUp = false;
        this.mPreviousScrollState = 0;
        this.mCurrentScrollState = 0;
        this.mScrollStateChangedRunnable = new android.widget.CalendarViewLegacyDelegate.ScrollStateRunnable();
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.CalendarView, i, i2);
        this.mShowWeekNumber = obtainStyledAttributes.getBoolean(1, true);
        this.mFirstDayOfWeek = obtainStyledAttributes.getInt(0, android.icu.util.Calendar.getInstance().getFirstDayOfWeek());
        if (!android.widget.CalendarView.parseDate(obtainStyledAttributes.getString(2), this.mMinDate)) {
            android.widget.CalendarView.parseDate("01/01/1900", this.mMinDate);
        }
        if (!android.widget.CalendarView.parseDate(obtainStyledAttributes.getString(3), this.mMaxDate)) {
            android.widget.CalendarView.parseDate("01/01/2100", this.mMaxDate);
        }
        if (this.mMaxDate.before(this.mMinDate)) {
            throw new java.lang.IllegalArgumentException("Max date cannot be before min date.");
        }
        this.mShownWeekCount = obtainStyledAttributes.getInt(4, 6);
        this.mSelectedWeekBackgroundColor = obtainStyledAttributes.getColor(5, 0);
        this.mFocusedMonthDateColor = obtainStyledAttributes.getColor(6, 0);
        this.mUnfocusedMonthDateColor = obtainStyledAttributes.getColor(7, 0);
        this.mWeekSeparatorLineColor = obtainStyledAttributes.getColor(9, 0);
        this.mWeekNumberColor = obtainStyledAttributes.getColor(8, 0);
        this.mSelectedDateVerticalBar = obtainStyledAttributes.getDrawable(10);
        this.mDateTextAppearanceResId = obtainStyledAttributes.getResourceId(12, 16973894);
        updateDateTextSize();
        this.mWeekDayTextAppearanceResId = obtainStyledAttributes.getResourceId(11, -1);
        obtainStyledAttributes.recycle();
        android.util.DisplayMetrics displayMetrics = this.mDelegator.getResources().getDisplayMetrics();
        this.mWeekMinVisibleHeight = (int) android.util.TypedValue.applyDimension(1, 12.0f, displayMetrics);
        this.mListScrollTopOffset = (int) android.util.TypedValue.applyDimension(1, 2.0f, displayMetrics);
        this.mBottomBuffer = (int) android.util.TypedValue.applyDimension(1, 20.0f, displayMetrics);
        this.mSelectedDateVerticalBarWidth = (int) android.util.TypedValue.applyDimension(1, 6.0f, displayMetrics);
        this.mWeekSeparatorLineWidth = (int) android.util.TypedValue.applyDimension(1, 1.0f, displayMetrics);
        android.view.View inflate = ((android.view.LayoutInflater) this.mContext.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE)).inflate(com.android.internal.R.layout.calendar_view, (android.view.ViewGroup) null, false);
        this.mDelegator.addView(inflate);
        this.mListView = (android.widget.ListView) this.mDelegator.findViewById(16908298);
        this.mDayNamesHeader = (android.view.ViewGroup) inflate.findViewById(com.android.internal.R.id.day_names);
        this.mMonthName = (android.widget.TextView) inflate.findViewById(com.android.internal.R.id.month_name);
        setUpHeader();
        setUpListView();
        setUpAdapter();
        this.mTempDate.setTimeInMillis(java.lang.System.currentTimeMillis());
        if (this.mTempDate.before(this.mMinDate)) {
            goTo(this.mMinDate, false, true, true);
        } else if (this.mMaxDate.before(this.mTempDate)) {
            goTo(this.mMaxDate, false, true, true);
        } else {
            goTo(this.mTempDate, false, true, true);
        }
        this.mDelegator.invalidate();
    }

    @Override // android.widget.CalendarView.AbstractCalendarViewDelegate, android.widget.CalendarView.CalendarViewDelegate
    public void setShownWeekCount(int i) {
        if (this.mShownWeekCount != i) {
            this.mShownWeekCount = i;
            this.mDelegator.invalidate();
        }
    }

    @Override // android.widget.CalendarView.AbstractCalendarViewDelegate, android.widget.CalendarView.CalendarViewDelegate
    public int getShownWeekCount() {
        return this.mShownWeekCount;
    }

    @Override // android.widget.CalendarView.AbstractCalendarViewDelegate, android.widget.CalendarView.CalendarViewDelegate
    public void setSelectedWeekBackgroundColor(int i) {
        if (this.mSelectedWeekBackgroundColor != i) {
            this.mSelectedWeekBackgroundColor = i;
            int childCount = this.mListView.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                android.widget.CalendarViewLegacyDelegate.WeekView weekView = (android.widget.CalendarViewLegacyDelegate.WeekView) this.mListView.getChildAt(i2);
                if (weekView.mHasSelectedDay) {
                    weekView.invalidate();
                }
            }
        }
    }

    @Override // android.widget.CalendarView.AbstractCalendarViewDelegate, android.widget.CalendarView.CalendarViewDelegate
    public int getSelectedWeekBackgroundColor() {
        return this.mSelectedWeekBackgroundColor;
    }

    @Override // android.widget.CalendarView.AbstractCalendarViewDelegate, android.widget.CalendarView.CalendarViewDelegate
    public void setFocusedMonthDateColor(int i) {
        if (this.mFocusedMonthDateColor != i) {
            this.mFocusedMonthDateColor = i;
            int childCount = this.mListView.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                android.widget.CalendarViewLegacyDelegate.WeekView weekView = (android.widget.CalendarViewLegacyDelegate.WeekView) this.mListView.getChildAt(i2);
                if (weekView.mHasFocusedDay) {
                    weekView.invalidate();
                }
            }
        }
    }

    @Override // android.widget.CalendarView.AbstractCalendarViewDelegate, android.widget.CalendarView.CalendarViewDelegate
    public int getFocusedMonthDateColor() {
        return this.mFocusedMonthDateColor;
    }

    @Override // android.widget.CalendarView.AbstractCalendarViewDelegate, android.widget.CalendarView.CalendarViewDelegate
    public void setUnfocusedMonthDateColor(int i) {
        if (this.mUnfocusedMonthDateColor != i) {
            this.mUnfocusedMonthDateColor = i;
            int childCount = this.mListView.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                android.widget.CalendarViewLegacyDelegate.WeekView weekView = (android.widget.CalendarViewLegacyDelegate.WeekView) this.mListView.getChildAt(i2);
                if (weekView.mHasUnfocusedDay) {
                    weekView.invalidate();
                }
            }
        }
    }

    @Override // android.widget.CalendarView.AbstractCalendarViewDelegate, android.widget.CalendarView.CalendarViewDelegate
    public int getUnfocusedMonthDateColor() {
        return this.mUnfocusedMonthDateColor;
    }

    @Override // android.widget.CalendarView.AbstractCalendarViewDelegate, android.widget.CalendarView.CalendarViewDelegate
    public void setWeekNumberColor(int i) {
        if (this.mWeekNumberColor != i) {
            this.mWeekNumberColor = i;
            if (this.mShowWeekNumber) {
                invalidateAllWeekViews();
            }
        }
    }

    @Override // android.widget.CalendarView.AbstractCalendarViewDelegate, android.widget.CalendarView.CalendarViewDelegate
    public int getWeekNumberColor() {
        return this.mWeekNumberColor;
    }

    @Override // android.widget.CalendarView.AbstractCalendarViewDelegate, android.widget.CalendarView.CalendarViewDelegate
    public void setWeekSeparatorLineColor(int i) {
        if (this.mWeekSeparatorLineColor != i) {
            this.mWeekSeparatorLineColor = i;
            invalidateAllWeekViews();
        }
    }

    @Override // android.widget.CalendarView.AbstractCalendarViewDelegate, android.widget.CalendarView.CalendarViewDelegate
    public int getWeekSeparatorLineColor() {
        return this.mWeekSeparatorLineColor;
    }

    @Override // android.widget.CalendarView.AbstractCalendarViewDelegate, android.widget.CalendarView.CalendarViewDelegate
    public void setSelectedDateVerticalBar(int i) {
        setSelectedDateVerticalBar(this.mDelegator.getContext().getDrawable(i));
    }

    @Override // android.widget.CalendarView.AbstractCalendarViewDelegate, android.widget.CalendarView.CalendarViewDelegate
    public void setSelectedDateVerticalBar(android.graphics.drawable.Drawable drawable) {
        if (this.mSelectedDateVerticalBar != drawable) {
            this.mSelectedDateVerticalBar = drawable;
            int childCount = this.mListView.getChildCount();
            for (int i = 0; i < childCount; i++) {
                android.widget.CalendarViewLegacyDelegate.WeekView weekView = (android.widget.CalendarViewLegacyDelegate.WeekView) this.mListView.getChildAt(i);
                if (weekView.mHasSelectedDay) {
                    weekView.invalidate();
                }
            }
        }
    }

    @Override // android.widget.CalendarView.AbstractCalendarViewDelegate, android.widget.CalendarView.CalendarViewDelegate
    public android.graphics.drawable.Drawable getSelectedDateVerticalBar() {
        return this.mSelectedDateVerticalBar;
    }

    @Override // android.widget.CalendarView.CalendarViewDelegate
    public void setWeekDayTextAppearance(int i) {
        if (this.mWeekDayTextAppearanceResId != i) {
            this.mWeekDayTextAppearanceResId = i;
            setUpHeader();
        }
    }

    @Override // android.widget.CalendarView.CalendarViewDelegate
    public int getWeekDayTextAppearance() {
        return this.mWeekDayTextAppearanceResId;
    }

    @Override // android.widget.CalendarView.CalendarViewDelegate
    public void setDateTextAppearance(int i) {
        if (this.mDateTextAppearanceResId != i) {
            this.mDateTextAppearanceResId = i;
            updateDateTextSize();
            invalidateAllWeekViews();
        }
    }

    @Override // android.widget.CalendarView.CalendarViewDelegate
    public int getDateTextAppearance() {
        return this.mDateTextAppearanceResId;
    }

    @Override // android.widget.CalendarView.CalendarViewDelegate
    public void setMinDate(long j) {
        this.mTempDate.setTimeInMillis(j);
        if (isSameDate(this.mTempDate, this.mMinDate)) {
            return;
        }
        this.mMinDate.setTimeInMillis(j);
        android.icu.util.Calendar calendar = this.mAdapter.mSelectedDate;
        if (calendar.before(this.mMinDate)) {
            this.mAdapter.setSelectedDay(this.mMinDate);
        }
        this.mAdapter.init();
        if (calendar.before(this.mMinDate)) {
            setDate(this.mTempDate.getTimeInMillis());
        } else {
            goTo(calendar, false, true, false);
        }
    }

    @Override // android.widget.CalendarView.CalendarViewDelegate
    public long getMinDate() {
        return this.mMinDate.getTimeInMillis();
    }

    @Override // android.widget.CalendarView.CalendarViewDelegate
    public void setMaxDate(long j) {
        this.mTempDate.setTimeInMillis(j);
        if (isSameDate(this.mTempDate, this.mMaxDate)) {
            return;
        }
        this.mMaxDate.setTimeInMillis(j);
        this.mAdapter.init();
        android.icu.util.Calendar calendar = this.mAdapter.mSelectedDate;
        if (calendar.after(this.mMaxDate)) {
            setDate(this.mMaxDate.getTimeInMillis());
        } else {
            goTo(calendar, false, true, false);
        }
    }

    @Override // android.widget.CalendarView.CalendarViewDelegate
    public long getMaxDate() {
        return this.mMaxDate.getTimeInMillis();
    }

    @Override // android.widget.CalendarView.AbstractCalendarViewDelegate, android.widget.CalendarView.CalendarViewDelegate
    public void setShowWeekNumber(boolean z) {
        if (this.mShowWeekNumber == z) {
            return;
        }
        this.mShowWeekNumber = z;
        this.mAdapter.notifyDataSetChanged();
        setUpHeader();
    }

    @Override // android.widget.CalendarView.AbstractCalendarViewDelegate, android.widget.CalendarView.CalendarViewDelegate
    public boolean getShowWeekNumber() {
        return this.mShowWeekNumber;
    }

    @Override // android.widget.CalendarView.CalendarViewDelegate
    public void setFirstDayOfWeek(int i) {
        if (this.mFirstDayOfWeek == i) {
            return;
        }
        this.mFirstDayOfWeek = i;
        this.mAdapter.init();
        this.mAdapter.notifyDataSetChanged();
        setUpHeader();
    }

    @Override // android.widget.CalendarView.CalendarViewDelegate
    public int getFirstDayOfWeek() {
        return this.mFirstDayOfWeek;
    }

    @Override // android.widget.CalendarView.CalendarViewDelegate
    public void setDate(long j) {
        setDate(j, false, false);
    }

    @Override // android.widget.CalendarView.CalendarViewDelegate
    public void setDate(long j, boolean z, boolean z2) {
        this.mTempDate.setTimeInMillis(j);
        if (isSameDate(this.mTempDate, this.mAdapter.mSelectedDate)) {
            return;
        }
        goTo(this.mTempDate, z, true, z2);
    }

    @Override // android.widget.CalendarView.CalendarViewDelegate
    public long getDate() {
        return this.mAdapter.mSelectedDate.getTimeInMillis();
    }

    @Override // android.widget.CalendarView.CalendarViewDelegate
    public void setOnDateChangeListener(android.widget.CalendarView.OnDateChangeListener onDateChangeListener) {
        this.mOnDateChangeListener = onDateChangeListener;
    }

    @Override // android.widget.CalendarView.CalendarViewDelegate
    public boolean getBoundsForDate(long j, android.graphics.Rect rect) {
        android.icu.util.Calendar calendar = android.icu.util.Calendar.getInstance();
        calendar.setTimeInMillis(j);
        int count = this.mListView.getCount();
        for (int i = 0; i < count; i++) {
            android.widget.CalendarViewLegacyDelegate.WeekView weekView = (android.widget.CalendarViewLegacyDelegate.WeekView) this.mListView.getChildAt(i);
            if (weekView.getBoundsForDate(calendar, rect)) {
                int[] iArr = new int[2];
                int[] iArr2 = new int[2];
                weekView.getLocationOnScreen(iArr);
                this.mDelegator.getLocationOnScreen(iArr2);
                int i2 = iArr[1] - iArr2[1];
                rect.top += i2;
                rect.bottom += i2;
                return true;
            }
        }
        return false;
    }

    @Override // android.widget.CalendarView.AbstractCalendarViewDelegate, android.widget.CalendarView.CalendarViewDelegate
    public void onConfigurationChanged(android.content.res.Configuration configuration) {
        setCurrentLocale(configuration.locale);
    }

    @Override // android.widget.CalendarView.AbstractCalendarViewDelegate
    protected void setCurrentLocale(java.util.Locale locale) {
        super.setCurrentLocale(locale);
        this.mTempDate = getCalendarForLocale(this.mTempDate, locale);
        this.mFirstDayOfMonth = getCalendarForLocale(this.mFirstDayOfMonth, locale);
        this.mMinDate = getCalendarForLocale(this.mMinDate, locale);
        this.mMaxDate = getCalendarForLocale(this.mMaxDate, locale);
    }

    private void updateDateTextSize() {
        android.content.res.TypedArray obtainStyledAttributes = this.mDelegator.getContext().obtainStyledAttributes(this.mDateTextAppearanceResId, com.android.internal.R.styleable.TextAppearance);
        this.mDateTextSize = obtainStyledAttributes.getDimensionPixelSize(0, 14);
        obtainStyledAttributes.recycle();
    }

    private void invalidateAllWeekViews() {
        int childCount = this.mListView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            this.mListView.getChildAt(i).invalidate();
        }
    }

    private static android.icu.util.Calendar getCalendarForLocale(android.icu.util.Calendar calendar, java.util.Locale locale) {
        if (calendar == null) {
            return android.icu.util.Calendar.getInstance(locale);
        }
        long timeInMillis = calendar.getTimeInMillis();
        android.icu.util.Calendar calendar2 = android.icu.util.Calendar.getInstance(locale);
        calendar2.setTimeInMillis(timeInMillis);
        return calendar2;
    }

    private static boolean isSameDate(android.icu.util.Calendar calendar, android.icu.util.Calendar calendar2) {
        return calendar.get(6) == calendar2.get(6) && calendar.get(1) == calendar2.get(1);
    }

    private void setUpAdapter() {
        if (this.mAdapter == null) {
            this.mAdapter = new android.widget.CalendarViewLegacyDelegate.WeeksAdapter(this.mContext);
            this.mAdapter.registerDataSetObserver(new android.database.DataSetObserver() { // from class: android.widget.CalendarViewLegacyDelegate.1
                @Override // android.database.DataSetObserver
                public void onChanged() {
                    if (android.widget.CalendarViewLegacyDelegate.this.mOnDateChangeListener != null) {
                        android.icu.util.Calendar selectedDay = android.widget.CalendarViewLegacyDelegate.this.mAdapter.getSelectedDay();
                        android.widget.CalendarViewLegacyDelegate.this.mOnDateChangeListener.onSelectedDayChange(android.widget.CalendarViewLegacyDelegate.this.mDelegator, selectedDay.get(1), selectedDay.get(2), selectedDay.get(5));
                    }
                }
            });
            this.mListView.setAdapter((android.widget.ListAdapter) this.mAdapter);
        }
        this.mAdapter.notifyDataSetChanged();
    }

    private void setUpHeader() {
        this.mDayNamesShort = new java.lang.String[this.mDaysPerWeek];
        this.mDayNamesLong = new java.lang.String[this.mDaysPerWeek];
        int i = this.mFirstDayOfWeek;
        int i2 = this.mFirstDayOfWeek + this.mDaysPerWeek;
        while (i < i2) {
            int i3 = i > 7 ? i - 7 : i;
            this.mDayNamesShort[i - this.mFirstDayOfWeek] = android.text.format.DateUtils.getDayOfWeekString(i3, 50);
            this.mDayNamesLong[i - this.mFirstDayOfWeek] = android.text.format.DateUtils.getDayOfWeekString(i3, 10);
            i++;
        }
        android.widget.TextView textView = (android.widget.TextView) this.mDayNamesHeader.getChildAt(0);
        if (this.mShowWeekNumber) {
            textView.setVisibility(0);
        } else {
            textView.setVisibility(8);
        }
        int childCount = this.mDayNamesHeader.getChildCount();
        for (int i4 = 1; i4 < childCount; i4++) {
            android.widget.TextView textView2 = (android.widget.TextView) this.mDayNamesHeader.getChildAt(i4);
            if (this.mWeekDayTextAppearanceResId > -1) {
                textView2.setTextAppearance(this.mWeekDayTextAppearanceResId);
            }
            if (i4 < this.mDaysPerWeek + 1) {
                int i5 = i4 - 1;
                textView2.lambda$setTextAsync$0(this.mDayNamesShort[i5]);
                textView2.setContentDescription(this.mDayNamesLong[i5]);
                textView2.setVisibility(0);
            } else {
                textView2.setVisibility(8);
            }
        }
        this.mDayNamesHeader.invalidate();
    }

    private void setUpListView() {
        this.mListView.setDivider(null);
        this.mListView.setItemsCanFocus(true);
        this.mListView.setVerticalScrollBarEnabled(false);
        this.mListView.setOnScrollListener(new android.widget.AbsListView.OnScrollListener() { // from class: android.widget.CalendarViewLegacyDelegate.2
            @Override // android.widget.AbsListView.OnScrollListener
            public void onScrollStateChanged(android.widget.AbsListView absListView, int i) {
                android.widget.CalendarViewLegacyDelegate.this.onScrollStateChanged(absListView, i);
            }

            @Override // android.widget.AbsListView.OnScrollListener
            public void onScroll(android.widget.AbsListView absListView, int i, int i2, int i3) {
                android.widget.CalendarViewLegacyDelegate.this.onScroll(absListView, i, i2, i3);
            }
        });
        this.mListView.setFriction(this.mFriction);
        this.mListView.setVelocityScale(this.mVelocityScale);
    }

    private void goTo(android.icu.util.Calendar calendar, boolean z, boolean z2, boolean z3) {
        int weeksSinceMinDate;
        if (calendar.before(this.mMinDate) || calendar.after(this.mMaxDate)) {
            throw new java.lang.IllegalArgumentException("timeInMillis must be between the values of getMinDate() and getMaxDate()");
        }
        int firstVisiblePosition = this.mListView.getFirstVisiblePosition();
        android.view.View childAt = this.mListView.getChildAt(0);
        if (childAt != null && childAt.getTop() < 0) {
            firstVisiblePosition++;
        }
        int i = (this.mShownWeekCount + firstVisiblePosition) - 1;
        if (childAt != null && childAt.getTop() > this.mBottomBuffer) {
            i--;
        }
        if (z2) {
            this.mAdapter.setSelectedDay(calendar);
        }
        int weeksSinceMinDate2 = getWeeksSinceMinDate(calendar);
        if (weeksSinceMinDate2 < firstVisiblePosition || weeksSinceMinDate2 > i || z3) {
            this.mFirstDayOfMonth.setTimeInMillis(calendar.getTimeInMillis());
            this.mFirstDayOfMonth.set(5, 1);
            setMonthDisplayed(this.mFirstDayOfMonth);
            if (this.mFirstDayOfMonth.before(this.mMinDate)) {
                weeksSinceMinDate = 0;
            } else {
                weeksSinceMinDate = getWeeksSinceMinDate(this.mFirstDayOfMonth);
            }
            this.mPreviousScrollState = 2;
            if (z) {
                this.mListView.smoothScrollToPositionFromTop(weeksSinceMinDate, this.mListScrollTopOffset, 1000);
                return;
            } else {
                this.mListView.setSelectionFromTop(weeksSinceMinDate, this.mListScrollTopOffset);
                onScrollStateChanged(this.mListView, 0);
                return;
            }
        }
        if (z2) {
            setMonthDisplayed(calendar);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onScrollStateChanged(android.widget.AbsListView absListView, int i) {
        this.mScrollStateChangedRunnable.doScrollStateChange(absListView, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onScroll(android.widget.AbsListView absListView, int i, int i2, int i3) {
        int monthOfLastWeekDay;
        android.widget.CalendarViewLegacyDelegate.WeekView weekView = (android.widget.CalendarViewLegacyDelegate.WeekView) absListView.getChildAt(0);
        if (weekView == null) {
            return;
        }
        long firstVisiblePosition = (absListView.getFirstVisiblePosition() * weekView.getHeight()) - weekView.getBottom();
        int i4 = 1;
        if (firstVisiblePosition < this.mPreviousScrollPosition) {
            this.mIsScrollingUp = true;
        } else if (firstVisiblePosition > this.mPreviousScrollPosition) {
            this.mIsScrollingUp = false;
        } else {
            return;
        }
        int i5 = weekView.getBottom() < this.mWeekMinVisibleHeight ? 1 : 0;
        if (this.mIsScrollingUp) {
            weekView = (android.widget.CalendarViewLegacyDelegate.WeekView) absListView.getChildAt(i5 + 2);
        } else if (i5 != 0) {
            weekView = (android.widget.CalendarViewLegacyDelegate.WeekView) absListView.getChildAt(i5);
        }
        if (weekView != null) {
            if (this.mIsScrollingUp) {
                monthOfLastWeekDay = weekView.getMonthOfFirstWeekDay();
            } else {
                monthOfLastWeekDay = weekView.getMonthOfLastWeekDay();
            }
            if (this.mCurrentMonthDisplayed != 11 || monthOfLastWeekDay != 0) {
                if (this.mCurrentMonthDisplayed == 0 && monthOfLastWeekDay == 11) {
                    i4 = -1;
                } else {
                    i4 = monthOfLastWeekDay - this.mCurrentMonthDisplayed;
                }
            }
            if ((!this.mIsScrollingUp && i4 > 0) || (this.mIsScrollingUp && i4 < 0)) {
                android.icu.util.Calendar firstDay = weekView.getFirstDay();
                if (this.mIsScrollingUp) {
                    firstDay.add(5, -7);
                } else {
                    firstDay.add(5, 7);
                }
                setMonthDisplayed(firstDay);
            }
        }
        this.mPreviousScrollPosition = firstVisiblePosition;
        this.mPreviousScrollState = this.mCurrentScrollState;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setMonthDisplayed(android.icu.util.Calendar calendar) {
        this.mCurrentMonthDisplayed = calendar.get(2);
        this.mAdapter.setFocusMonth(this.mCurrentMonthDisplayed);
        long timeInMillis = calendar.getTimeInMillis();
        this.mMonthName.lambda$setTextAsync$0(android.text.format.DateUtils.formatDateRange(this.mContext, timeInMillis, timeInMillis, 52));
        this.mMonthName.invalidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getWeeksSinceMinDate(android.icu.util.Calendar calendar) {
        if (calendar.before(this.mMinDate)) {
            throw new java.lang.IllegalArgumentException("fromDate: " + this.mMinDate.getTime() + " does not precede toDate: " + calendar.getTime());
        }
        return (int) ((((calendar.getTimeInMillis() + calendar.getTimeZone().getOffset(calendar.getTimeInMillis())) - (this.mMinDate.getTimeInMillis() + this.mMinDate.getTimeZone().getOffset(this.mMinDate.getTimeInMillis()))) + ((this.mMinDate.get(7) - this.mFirstDayOfWeek) * 86400000)) / 604800000);
    }

    private class ScrollStateRunnable implements java.lang.Runnable {
        private int mNewState;
        private android.widget.AbsListView mView;

        private ScrollStateRunnable() {
        }

        public void doScrollStateChange(android.widget.AbsListView absListView, int i) {
            this.mView = absListView;
            this.mNewState = i;
            android.widget.CalendarViewLegacyDelegate.this.mDelegator.removeCallbacks(this);
            android.widget.CalendarViewLegacyDelegate.this.mDelegator.postDelayed(this, 40L);
        }

        @Override // java.lang.Runnable
        public void run() {
            android.widget.CalendarViewLegacyDelegate.this.mCurrentScrollState = this.mNewState;
            if (this.mNewState == 0 && android.widget.CalendarViewLegacyDelegate.this.mPreviousScrollState != 0) {
                android.view.View childAt = this.mView.getChildAt(0);
                if (childAt == null) {
                    return;
                }
                int bottom = childAt.getBottom() - android.widget.CalendarViewLegacyDelegate.this.mListScrollTopOffset;
                if (bottom > android.widget.CalendarViewLegacyDelegate.this.mListScrollTopOffset) {
                    if (android.widget.CalendarViewLegacyDelegate.this.mIsScrollingUp) {
                        this.mView.smoothScrollBy(bottom - childAt.getHeight(), 500);
                    } else {
                        this.mView.smoothScrollBy(bottom, 500);
                    }
                }
            }
            android.widget.CalendarViewLegacyDelegate.this.mPreviousScrollState = this.mNewState;
        }
    }

    private class WeeksAdapter extends android.widget.BaseAdapter implements android.view.View.OnTouchListener {
        private int mFocusedMonth;
        private android.view.GestureDetector mGestureDetector;
        private final android.icu.util.Calendar mSelectedDate = android.icu.util.Calendar.getInstance();
        private int mSelectedWeek;
        private int mTotalWeekCount;

        public WeeksAdapter(android.content.Context context) {
            android.widget.CalendarViewLegacyDelegate.this.mContext = context;
            this.mGestureDetector = new android.view.GestureDetector(android.widget.CalendarViewLegacyDelegate.this.mContext, new android.widget.CalendarViewLegacyDelegate.WeeksAdapter.CalendarGestureListener());
            init();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void init() {
            this.mSelectedWeek = android.widget.CalendarViewLegacyDelegate.this.getWeeksSinceMinDate(this.mSelectedDate);
            this.mTotalWeekCount = android.widget.CalendarViewLegacyDelegate.this.getWeeksSinceMinDate(android.widget.CalendarViewLegacyDelegate.this.mMaxDate);
            if (android.widget.CalendarViewLegacyDelegate.this.mMinDate.get(7) != android.widget.CalendarViewLegacyDelegate.this.mFirstDayOfWeek || android.widget.CalendarViewLegacyDelegate.this.mMaxDate.get(7) != android.widget.CalendarViewLegacyDelegate.this.mFirstDayOfWeek) {
                this.mTotalWeekCount++;
            }
            notifyDataSetChanged();
        }

        public void setSelectedDay(android.icu.util.Calendar calendar) {
            if (calendar.get(6) == this.mSelectedDate.get(6) && calendar.get(1) == this.mSelectedDate.get(1)) {
                return;
            }
            this.mSelectedDate.setTimeInMillis(calendar.getTimeInMillis());
            this.mSelectedWeek = android.widget.CalendarViewLegacyDelegate.this.getWeeksSinceMinDate(this.mSelectedDate);
            this.mFocusedMonth = this.mSelectedDate.get(2);
            notifyDataSetChanged();
        }

        public android.icu.util.Calendar getSelectedDay() {
            return this.mSelectedDate;
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return this.mTotalWeekCount;
        }

        @Override // android.widget.Adapter
        public java.lang.Object getItem(int i) {
            return null;
        }

        @Override // android.widget.Adapter
        public long getItemId(int i) {
            return i;
        }

        @Override // android.widget.Adapter
        public android.view.View getView(int i, android.view.View view, android.view.ViewGroup viewGroup) {
            android.widget.CalendarViewLegacyDelegate.WeekView weekView;
            if (view != null) {
                weekView = (android.widget.CalendarViewLegacyDelegate.WeekView) view;
            } else {
                weekView = android.widget.CalendarViewLegacyDelegate.this.new WeekView(android.widget.CalendarViewLegacyDelegate.this.mContext);
                weekView.setLayoutParams(new android.widget.AbsListView.LayoutParams(-2, -2));
                weekView.setClickable(true);
                weekView.setOnTouchListener(this);
            }
            weekView.init(i, this.mSelectedWeek == i ? this.mSelectedDate.get(7) : -1, this.mFocusedMonth);
            return weekView;
        }

        public void setFocusMonth(int i) {
            if (this.mFocusedMonth == i) {
                return;
            }
            this.mFocusedMonth = i;
            notifyDataSetChanged();
        }

        @Override // android.view.View.OnTouchListener
        public boolean onTouch(android.view.View view, android.view.MotionEvent motionEvent) {
            if (android.widget.CalendarViewLegacyDelegate.this.mListView.isEnabled() && this.mGestureDetector.onTouchEvent(motionEvent)) {
                if (!((android.widget.CalendarViewLegacyDelegate.WeekView) view).getDayFromLocation(motionEvent.getX(), android.widget.CalendarViewLegacyDelegate.this.mTempDate) || android.widget.CalendarViewLegacyDelegate.this.mTempDate.before(android.widget.CalendarViewLegacyDelegate.this.mMinDate) || android.widget.CalendarViewLegacyDelegate.this.mTempDate.after(android.widget.CalendarViewLegacyDelegate.this.mMaxDate)) {
                    return true;
                }
                onDateTapped(android.widget.CalendarViewLegacyDelegate.this.mTempDate);
                return true;
            }
            return false;
        }

        private void onDateTapped(android.icu.util.Calendar calendar) {
            setSelectedDay(calendar);
            android.widget.CalendarViewLegacyDelegate.this.setMonthDisplayed(calendar);
        }

        class CalendarGestureListener extends android.view.GestureDetector.SimpleOnGestureListener {
            CalendarGestureListener() {
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onSingleTapUp(android.view.MotionEvent motionEvent) {
                return true;
            }
        }
    }

    private class WeekView extends android.view.View {
        private java.lang.String[] mDayNumbers;
        private final android.graphics.Paint mDrawPaint;
        private android.icu.util.Calendar mFirstDay;
        private boolean[] mFocusDay;
        private boolean mHasFocusedDay;
        private boolean mHasSelectedDay;
        private boolean mHasUnfocusedDay;
        private int mHeight;
        private int mLastWeekDayMonth;
        private final android.graphics.Paint mMonthNumDrawPaint;
        private int mMonthOfFirstWeekDay;
        private int mNumCells;
        private int mSelectedDay;
        private int mSelectedLeft;
        private int mSelectedRight;
        private final android.graphics.Rect mTempRect;
        private int mWeek;
        private int mWidth;

        public WeekView(android.content.Context context) {
            super(context);
            this.mTempRect = new android.graphics.Rect();
            this.mDrawPaint = new android.graphics.Paint();
            this.mMonthNumDrawPaint = new android.graphics.Paint();
            this.mMonthOfFirstWeekDay = -1;
            this.mLastWeekDayMonth = -1;
            this.mWeek = -1;
            this.mHasSelectedDay = false;
            this.mSelectedDay = -1;
            this.mSelectedLeft = -1;
            this.mSelectedRight = -1;
            initializePaints();
        }

        public void init(int i, int i2, int i3) {
            int i4;
            this.mSelectedDay = i2;
            this.mHasSelectedDay = this.mSelectedDay != -1;
            this.mNumCells = android.widget.CalendarViewLegacyDelegate.this.mShowWeekNumber ? android.widget.CalendarViewLegacyDelegate.this.mDaysPerWeek + 1 : android.widget.CalendarViewLegacyDelegate.this.mDaysPerWeek;
            this.mWeek = i;
            android.widget.CalendarViewLegacyDelegate.this.mTempDate.setTimeInMillis(android.widget.CalendarViewLegacyDelegate.this.mMinDate.getTimeInMillis());
            android.widget.CalendarViewLegacyDelegate.this.mTempDate.add(3, this.mWeek);
            android.widget.CalendarViewLegacyDelegate.this.mTempDate.setFirstDayOfWeek(android.widget.CalendarViewLegacyDelegate.this.mFirstDayOfWeek);
            this.mDayNumbers = new java.lang.String[this.mNumCells];
            this.mFocusDay = new boolean[this.mNumCells];
            if (!android.widget.CalendarViewLegacyDelegate.this.mShowWeekNumber) {
                i4 = 0;
            } else {
                this.mDayNumbers[0] = java.lang.String.format(java.util.Locale.getDefault(), "%d", java.lang.Integer.valueOf(android.widget.CalendarViewLegacyDelegate.this.mTempDate.get(3)));
                i4 = 1;
            }
            android.widget.CalendarViewLegacyDelegate.this.mTempDate.add(5, android.widget.CalendarViewLegacyDelegate.this.mFirstDayOfWeek - android.widget.CalendarViewLegacyDelegate.this.mTempDate.get(7));
            this.mFirstDay = (android.icu.util.Calendar) android.widget.CalendarViewLegacyDelegate.this.mTempDate.clone();
            this.mMonthOfFirstWeekDay = android.widget.CalendarViewLegacyDelegate.this.mTempDate.get(2);
            this.mHasUnfocusedDay = true;
            while (i4 < this.mNumCells) {
                boolean z = android.widget.CalendarViewLegacyDelegate.this.mTempDate.get(2) == i3;
                this.mFocusDay[i4] = z;
                this.mHasFocusedDay |= z;
                this.mHasUnfocusedDay = (!z) & this.mHasUnfocusedDay;
                if (android.widget.CalendarViewLegacyDelegate.this.mTempDate.before(android.widget.CalendarViewLegacyDelegate.this.mMinDate) || android.widget.CalendarViewLegacyDelegate.this.mTempDate.after(android.widget.CalendarViewLegacyDelegate.this.mMaxDate)) {
                    this.mDayNumbers[i4] = "";
                } else {
                    this.mDayNumbers[i4] = java.lang.String.format(java.util.Locale.getDefault(), "%d", java.lang.Integer.valueOf(android.widget.CalendarViewLegacyDelegate.this.mTempDate.get(5)));
                }
                android.widget.CalendarViewLegacyDelegate.this.mTempDate.add(5, 1);
                i4++;
            }
            if (android.widget.CalendarViewLegacyDelegate.this.mTempDate.get(5) == 1) {
                android.widget.CalendarViewLegacyDelegate.this.mTempDate.add(5, -1);
            }
            this.mLastWeekDayMonth = android.widget.CalendarViewLegacyDelegate.this.mTempDate.get(2);
            updateSelectionPositions();
        }

        private void initializePaints() {
            this.mDrawPaint.setFakeBoldText(false);
            this.mDrawPaint.setAntiAlias(true);
            this.mDrawPaint.setStyle(android.graphics.Paint.Style.FILL);
            this.mMonthNumDrawPaint.setFakeBoldText(true);
            this.mMonthNumDrawPaint.setAntiAlias(true);
            this.mMonthNumDrawPaint.setStyle(android.graphics.Paint.Style.FILL);
            this.mMonthNumDrawPaint.setTextAlign(android.graphics.Paint.Align.CENTER);
            this.mMonthNumDrawPaint.setTextSize(android.widget.CalendarViewLegacyDelegate.this.mDateTextSize);
        }

        public int getMonthOfFirstWeekDay() {
            return this.mMonthOfFirstWeekDay;
        }

        public int getMonthOfLastWeekDay() {
            return this.mLastWeekDayMonth;
        }

        public android.icu.util.Calendar getFirstDay() {
            return this.mFirstDay;
        }

        public boolean getDayFromLocation(float f, android.icu.util.Calendar calendar) {
            int i;
            int i2;
            boolean isLayoutRtl = isLayoutRtl();
            if (isLayoutRtl) {
                i2 = android.widget.CalendarViewLegacyDelegate.this.mShowWeekNumber ? this.mWidth - (this.mWidth / this.mNumCells) : this.mWidth;
                i = 0;
            } else {
                i = android.widget.CalendarViewLegacyDelegate.this.mShowWeekNumber ? this.mWidth / this.mNumCells : 0;
                i2 = this.mWidth;
            }
            float f2 = i;
            if (f < f2 || f > i2) {
                calendar.clear();
                return false;
            }
            int i3 = (int) (((f - f2) * android.widget.CalendarViewLegacyDelegate.this.mDaysPerWeek) / (i2 - i));
            if (isLayoutRtl) {
                i3 = (android.widget.CalendarViewLegacyDelegate.this.mDaysPerWeek - 1) - i3;
            }
            calendar.setTimeInMillis(this.mFirstDay.getTimeInMillis());
            calendar.add(5, i3);
            return true;
        }

        public boolean getBoundsForDate(android.icu.util.Calendar calendar, android.graphics.Rect rect) {
            int i;
            android.icu.util.Calendar calendar2 = android.icu.util.Calendar.getInstance();
            calendar2.setTime(this.mFirstDay.getTime());
            int i2 = 0;
            while (i2 < android.widget.CalendarViewLegacyDelegate.this.mDaysPerWeek) {
                if (calendar.get(1) == calendar2.get(1) && calendar.get(2) == calendar2.get(2) && calendar.get(5) == calendar2.get(5)) {
                    int i3 = this.mWidth / this.mNumCells;
                    if (isLayoutRtl()) {
                        if (!android.widget.CalendarViewLegacyDelegate.this.mShowWeekNumber) {
                            i = (this.mNumCells - i2) - 1;
                        } else {
                            i = (this.mNumCells - i2) - 2;
                        }
                        rect.left = i * i3;
                    } else {
                        if (android.widget.CalendarViewLegacyDelegate.this.mShowWeekNumber) {
                            i2++;
                        }
                        rect.left = i2 * i3;
                    }
                    rect.top = 0;
                    rect.right = rect.left + i3;
                    rect.bottom = getHeight();
                    return true;
                }
                calendar2.add(5, 1);
                i2++;
            }
            return false;
        }

        @Override // android.view.View
        protected void onDraw(android.graphics.Canvas canvas) {
            drawBackground(canvas);
            drawWeekNumbersAndDates(canvas);
            drawWeekSeparators(canvas);
            drawSelectedDateVerticalBars(canvas);
        }

        private void drawBackground(android.graphics.Canvas canvas) {
            if (!this.mHasSelectedDay) {
                return;
            }
            this.mDrawPaint.setColor(android.widget.CalendarViewLegacyDelegate.this.mSelectedWeekBackgroundColor);
            this.mTempRect.top = android.widget.CalendarViewLegacyDelegate.this.mWeekSeparatorLineWidth;
            this.mTempRect.bottom = this.mHeight;
            boolean isLayoutRtl = isLayoutRtl();
            if (isLayoutRtl) {
                this.mTempRect.left = 0;
                this.mTempRect.right = this.mSelectedLeft - 2;
            } else {
                this.mTempRect.left = android.widget.CalendarViewLegacyDelegate.this.mShowWeekNumber ? this.mWidth / this.mNumCells : 0;
                this.mTempRect.right = this.mSelectedLeft - 2;
            }
            canvas.drawRect(this.mTempRect, this.mDrawPaint);
            if (isLayoutRtl) {
                this.mTempRect.left = this.mSelectedRight + 3;
                this.mTempRect.right = android.widget.CalendarViewLegacyDelegate.this.mShowWeekNumber ? this.mWidth - (this.mWidth / this.mNumCells) : this.mWidth;
            } else {
                this.mTempRect.left = this.mSelectedRight + 3;
                this.mTempRect.right = this.mWidth;
            }
            canvas.drawRect(this.mTempRect, this.mDrawPaint);
        }

        private void drawWeekNumbersAndDates(android.graphics.Canvas canvas) {
            int textSize = ((int) ((this.mHeight + this.mDrawPaint.getTextSize()) / 2.0f)) - android.widget.CalendarViewLegacyDelegate.this.mWeekSeparatorLineWidth;
            int i = this.mNumCells;
            int i2 = i * 2;
            this.mDrawPaint.setTextAlign(android.graphics.Paint.Align.CENTER);
            this.mDrawPaint.setTextSize(android.widget.CalendarViewLegacyDelegate.this.mDateTextSize);
            int i3 = 0;
            if (isLayoutRtl()) {
                int i4 = 0;
                while (true) {
                    int i5 = i - 1;
                    if (i4 >= i5) {
                        break;
                    }
                    this.mMonthNumDrawPaint.setColor(this.mFocusDay[i4] ? android.widget.CalendarViewLegacyDelegate.this.mFocusedMonthDateColor : android.widget.CalendarViewLegacyDelegate.this.mUnfocusedMonthDateColor);
                    canvas.drawText(this.mDayNumbers[i5 - i4], (((i4 * 2) + 1) * this.mWidth) / i2, textSize, this.mMonthNumDrawPaint);
                    i4++;
                }
                if (android.widget.CalendarViewLegacyDelegate.this.mShowWeekNumber) {
                    this.mDrawPaint.setColor(android.widget.CalendarViewLegacyDelegate.this.mWeekNumberColor);
                    canvas.drawText(this.mDayNumbers[0], this.mWidth - (this.mWidth / i2), textSize, this.mDrawPaint);
                    return;
                }
                return;
            }
            if (android.widget.CalendarViewLegacyDelegate.this.mShowWeekNumber) {
                this.mDrawPaint.setColor(android.widget.CalendarViewLegacyDelegate.this.mWeekNumberColor);
                canvas.drawText(this.mDayNumbers[0], this.mWidth / i2, textSize, this.mDrawPaint);
                i3 = 1;
            }
            while (i3 < i) {
                this.mMonthNumDrawPaint.setColor(this.mFocusDay[i3] ? android.widget.CalendarViewLegacyDelegate.this.mFocusedMonthDateColor : android.widget.CalendarViewLegacyDelegate.this.mUnfocusedMonthDateColor);
                canvas.drawText(this.mDayNumbers[i3], (((i3 * 2) + 1) * this.mWidth) / i2, textSize, this.mMonthNumDrawPaint);
                i3++;
            }
        }

        private void drawWeekSeparators(android.graphics.Canvas canvas) {
            float f;
            float f2;
            int firstVisiblePosition = android.widget.CalendarViewLegacyDelegate.this.mListView.getFirstVisiblePosition();
            if (android.widget.CalendarViewLegacyDelegate.this.mListView.getChildAt(0).getTop() < 0) {
                firstVisiblePosition++;
            }
            if (firstVisiblePosition == this.mWeek) {
                return;
            }
            this.mDrawPaint.setColor(android.widget.CalendarViewLegacyDelegate.this.mWeekSeparatorLineColor);
            this.mDrawPaint.setStrokeWidth(android.widget.CalendarViewLegacyDelegate.this.mWeekSeparatorLineWidth);
            if (isLayoutRtl()) {
                f = android.widget.CalendarViewLegacyDelegate.this.mShowWeekNumber ? this.mWidth - (this.mWidth / this.mNumCells) : this.mWidth;
                f2 = 0.0f;
            } else {
                float f3 = android.widget.CalendarViewLegacyDelegate.this.mShowWeekNumber ? this.mWidth / this.mNumCells : 0.0f;
                f = this.mWidth;
                f2 = f3;
            }
            canvas.drawLine(f2, 0.0f, f, 0.0f, this.mDrawPaint);
        }

        private void drawSelectedDateVerticalBars(android.graphics.Canvas canvas) {
            if (!this.mHasSelectedDay) {
                return;
            }
            android.widget.CalendarViewLegacyDelegate.this.mSelectedDateVerticalBar.setBounds(this.mSelectedLeft - (android.widget.CalendarViewLegacyDelegate.this.mSelectedDateVerticalBarWidth / 2), android.widget.CalendarViewLegacyDelegate.this.mWeekSeparatorLineWidth, this.mSelectedLeft + (android.widget.CalendarViewLegacyDelegate.this.mSelectedDateVerticalBarWidth / 2), this.mHeight);
            android.widget.CalendarViewLegacyDelegate.this.mSelectedDateVerticalBar.draw(canvas);
            android.widget.CalendarViewLegacyDelegate.this.mSelectedDateVerticalBar.setBounds(this.mSelectedRight - (android.widget.CalendarViewLegacyDelegate.this.mSelectedDateVerticalBarWidth / 2), android.widget.CalendarViewLegacyDelegate.this.mWeekSeparatorLineWidth, this.mSelectedRight + (android.widget.CalendarViewLegacyDelegate.this.mSelectedDateVerticalBarWidth / 2), this.mHeight);
            android.widget.CalendarViewLegacyDelegate.this.mSelectedDateVerticalBar.draw(canvas);
        }

        @Override // android.view.View
        protected void onSizeChanged(int i, int i2, int i3, int i4) {
            this.mWidth = i;
            updateSelectionPositions();
        }

        private void updateSelectionPositions() {
            if (this.mHasSelectedDay) {
                boolean isLayoutRtl = isLayoutRtl();
                int i = this.mSelectedDay - android.widget.CalendarViewLegacyDelegate.this.mFirstDayOfWeek;
                if (i < 0) {
                    i += 7;
                }
                if (android.widget.CalendarViewLegacyDelegate.this.mShowWeekNumber && !isLayoutRtl) {
                    i++;
                }
                if (isLayoutRtl) {
                    this.mSelectedLeft = (((android.widget.CalendarViewLegacyDelegate.this.mDaysPerWeek - 1) - i) * this.mWidth) / this.mNumCells;
                } else {
                    this.mSelectedLeft = (i * this.mWidth) / this.mNumCells;
                }
                this.mSelectedRight = this.mSelectedLeft + (this.mWidth / this.mNumCells);
            }
        }

        @Override // android.view.View
        protected void onMeasure(int i, int i2) {
            this.mHeight = ((android.widget.CalendarViewLegacyDelegate.this.mListView.getHeight() - android.widget.CalendarViewLegacyDelegate.this.mListView.getPaddingTop()) - android.widget.CalendarViewLegacyDelegate.this.mListView.getPaddingBottom()) / android.widget.CalendarViewLegacyDelegate.this.mShownWeekCount;
            setMeasuredDimension(android.view.View.MeasureSpec.getSize(i), this.mHeight);
        }
    }
}
