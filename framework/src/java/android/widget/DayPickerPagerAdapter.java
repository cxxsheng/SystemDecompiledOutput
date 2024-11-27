package android.widget;

/* loaded from: classes4.dex */
class DayPickerPagerAdapter extends com.android.internal.widget.PagerAdapter {
    private static final int MONTHS_IN_YEAR = 12;
    private android.content.res.ColorStateList mCalendarTextColor;
    private final int mCalendarViewId;
    private int mCount;
    private android.content.res.ColorStateList mDayHighlightColor;
    private int mDayOfWeekTextAppearance;
    private android.content.res.ColorStateList mDaySelectorColor;
    private int mDayTextAppearance;
    private int mFirstDayOfWeek;
    private final android.view.LayoutInflater mInflater;
    private final int mLayoutResId;
    private int mMonthTextAppearance;
    private android.widget.DayPickerPagerAdapter.OnDaySelectedListener mOnDaySelectedListener;
    private final android.icu.util.Calendar mMinDate = android.icu.util.Calendar.getInstance();
    private final android.icu.util.Calendar mMaxDate = android.icu.util.Calendar.getInstance();
    private final android.util.SparseArray<android.widget.DayPickerPagerAdapter.ViewHolder> mItems = new android.util.SparseArray<>();
    private android.icu.util.Calendar mSelectedDay = null;
    private final android.widget.SimpleMonthView.OnDayClickListener mOnDayClickListener = new android.widget.SimpleMonthView.OnDayClickListener() { // from class: android.widget.DayPickerPagerAdapter.1
        @Override // android.widget.SimpleMonthView.OnDayClickListener
        public void onDayClick(android.widget.SimpleMonthView simpleMonthView, android.icu.util.Calendar calendar) {
            if (calendar != null) {
                android.widget.DayPickerPagerAdapter.this.setSelectedDay(calendar);
                if (android.widget.DayPickerPagerAdapter.this.mOnDaySelectedListener != null) {
                    android.widget.DayPickerPagerAdapter.this.mOnDaySelectedListener.onDaySelected(android.widget.DayPickerPagerAdapter.this, calendar);
                }
            }
        }
    };

    public interface OnDaySelectedListener {
        void onDaySelected(android.widget.DayPickerPagerAdapter dayPickerPagerAdapter, android.icu.util.Calendar calendar);
    }

    public DayPickerPagerAdapter(android.content.Context context, int i, int i2) {
        this.mInflater = android.view.LayoutInflater.from(context);
        this.mLayoutResId = i;
        this.mCalendarViewId = i2;
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(new int[]{16843820});
        this.mDayHighlightColor = obtainStyledAttributes.getColorStateList(0);
        obtainStyledAttributes.recycle();
    }

    public void setRange(android.icu.util.Calendar calendar, android.icu.util.Calendar calendar2) {
        this.mMinDate.setTimeInMillis(calendar.getTimeInMillis());
        this.mMaxDate.setTimeInMillis(calendar2.getTimeInMillis());
        this.mCount = (this.mMaxDate.get(2) - this.mMinDate.get(2)) + ((this.mMaxDate.get(1) - this.mMinDate.get(1)) * 12) + 1;
        notifyDataSetChanged();
    }

    public void setFirstDayOfWeek(int i) {
        this.mFirstDayOfWeek = i;
        int size = this.mItems.size();
        for (int i2 = 0; i2 < size; i2++) {
            this.mItems.valueAt(i2).calendar.setFirstDayOfWeek(i);
        }
    }

    public int getFirstDayOfWeek() {
        return this.mFirstDayOfWeek;
    }

    public boolean getBoundsForDate(android.icu.util.Calendar calendar, android.graphics.Rect rect) {
        android.widget.DayPickerPagerAdapter.ViewHolder viewHolder = this.mItems.get(getPositionForDay(calendar), null);
        if (viewHolder == null) {
            return false;
        }
        return viewHolder.calendar.getBoundsForDay(calendar.get(5), rect);
    }

    public void setSelectedDay(android.icu.util.Calendar calendar) {
        android.widget.DayPickerPagerAdapter.ViewHolder viewHolder;
        android.widget.DayPickerPagerAdapter.ViewHolder viewHolder2;
        int positionForDay = getPositionForDay(this.mSelectedDay);
        int positionForDay2 = getPositionForDay(calendar);
        if (positionForDay != positionForDay2 && positionForDay >= 0 && (viewHolder2 = this.mItems.get(positionForDay, null)) != null) {
            viewHolder2.calendar.setSelectedDay(-1);
        }
        if (positionForDay2 >= 0 && (viewHolder = this.mItems.get(positionForDay2, null)) != null) {
            viewHolder.calendar.setSelectedDay(calendar.get(5));
        }
        this.mSelectedDay = calendar;
    }

    public void setOnDaySelectedListener(android.widget.DayPickerPagerAdapter.OnDaySelectedListener onDaySelectedListener) {
        this.mOnDaySelectedListener = onDaySelectedListener;
    }

    void setCalendarTextColor(android.content.res.ColorStateList colorStateList) {
        this.mCalendarTextColor = colorStateList;
        notifyDataSetChanged();
    }

    void setDaySelectorColor(android.content.res.ColorStateList colorStateList) {
        this.mDaySelectorColor = colorStateList;
        notifyDataSetChanged();
    }

    void setMonthTextAppearance(int i) {
        this.mMonthTextAppearance = i;
        notifyDataSetChanged();
    }

    void setDayOfWeekTextAppearance(int i) {
        this.mDayOfWeekTextAppearance = i;
        notifyDataSetChanged();
    }

    int getDayOfWeekTextAppearance() {
        return this.mDayOfWeekTextAppearance;
    }

    void setDayTextAppearance(int i) {
        this.mDayTextAppearance = i;
        notifyDataSetChanged();
    }

    int getDayTextAppearance() {
        return this.mDayTextAppearance;
    }

    @Override // com.android.internal.widget.PagerAdapter
    public int getCount() {
        return this.mCount;
    }

    @Override // com.android.internal.widget.PagerAdapter
    public boolean isViewFromObject(android.view.View view, java.lang.Object obj) {
        return view == ((android.widget.DayPickerPagerAdapter.ViewHolder) obj).container;
    }

    private int getMonthForPosition(int i) {
        return (i + this.mMinDate.get(2)) % 12;
    }

    private int getYearForPosition(int i) {
        return ((i + this.mMinDate.get(2)) / 12) + this.mMinDate.get(1);
    }

    private int getPositionForDay(android.icu.util.Calendar calendar) {
        if (calendar == null) {
            return -1;
        }
        return ((calendar.get(1) - this.mMinDate.get(1)) * 12) + (calendar.get(2) - this.mMinDate.get(2));
    }

    @Override // com.android.internal.widget.PagerAdapter
    public java.lang.Object instantiateItem(android.view.ViewGroup viewGroup, int i) {
        int i2;
        int i3;
        int i4;
        android.view.View inflate = this.mInflater.inflate(this.mLayoutResId, viewGroup, false);
        android.widget.SimpleMonthView simpleMonthView = (android.widget.SimpleMonthView) inflate.findViewById(this.mCalendarViewId);
        simpleMonthView.setOnDayClickListener(this.mOnDayClickListener);
        simpleMonthView.setMonthTextAppearance(this.mMonthTextAppearance);
        simpleMonthView.setDayOfWeekTextAppearance(this.mDayOfWeekTextAppearance);
        simpleMonthView.setDayTextAppearance(this.mDayTextAppearance);
        if (this.mDaySelectorColor != null) {
            simpleMonthView.setDaySelectorColor(this.mDaySelectorColor);
        }
        if (this.mDayHighlightColor != null) {
            simpleMonthView.setDayHighlightColor(this.mDayHighlightColor);
        }
        if (this.mCalendarTextColor != null) {
            simpleMonthView.setMonthTextColor(this.mCalendarTextColor);
            simpleMonthView.setDayOfWeekTextColor(this.mCalendarTextColor);
            simpleMonthView.setDayTextColor(this.mCalendarTextColor);
        }
        int monthForPosition = getMonthForPosition(i);
        int yearForPosition = getYearForPosition(i);
        if (this.mSelectedDay != null && this.mSelectedDay.get(2) == monthForPosition && this.mSelectedDay.get(1) == yearForPosition) {
            i2 = this.mSelectedDay.get(5);
        } else {
            i2 = -1;
        }
        if (this.mMinDate.get(2) == monthForPosition && this.mMinDate.get(1) == yearForPosition) {
            i3 = this.mMinDate.get(5);
        } else {
            i3 = 1;
        }
        if (this.mMaxDate.get(2) == monthForPosition && this.mMaxDate.get(1) == yearForPosition) {
            i4 = this.mMaxDate.get(5);
        } else {
            i4 = 31;
        }
        simpleMonthView.setMonthParams(i2, monthForPosition, yearForPosition, this.mFirstDayOfWeek, i3, i4);
        android.widget.DayPickerPagerAdapter.ViewHolder viewHolder = new android.widget.DayPickerPagerAdapter.ViewHolder(i, inflate, simpleMonthView);
        this.mItems.put(i, viewHolder);
        viewGroup.addView(inflate);
        return viewHolder;
    }

    @Override // com.android.internal.widget.PagerAdapter
    public void destroyItem(android.view.ViewGroup viewGroup, int i, java.lang.Object obj) {
        viewGroup.removeView(((android.widget.DayPickerPagerAdapter.ViewHolder) obj).container);
        this.mItems.remove(i);
    }

    @Override // com.android.internal.widget.PagerAdapter
    public int getItemPosition(java.lang.Object obj) {
        return ((android.widget.DayPickerPagerAdapter.ViewHolder) obj).position;
    }

    @Override // com.android.internal.widget.PagerAdapter
    public java.lang.CharSequence getPageTitle(int i) {
        android.widget.SimpleMonthView simpleMonthView = this.mItems.get(i).calendar;
        if (simpleMonthView != null) {
            return simpleMonthView.getMonthYearLabel();
        }
        return null;
    }

    android.widget.SimpleMonthView getView(java.lang.Object obj) {
        if (obj == null) {
            return null;
        }
        return ((android.widget.DayPickerPagerAdapter.ViewHolder) obj).calendar;
    }

    private static class ViewHolder {
        public final android.widget.SimpleMonthView calendar;
        public final android.view.View container;
        public final int position;

        public ViewHolder(int i, android.view.View view, android.widget.SimpleMonthView simpleMonthView) {
            this.position = i;
            this.container = view;
            this.calendar = simpleMonthView;
        }
    }
}
