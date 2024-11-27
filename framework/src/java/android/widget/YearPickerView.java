package android.widget;

/* loaded from: classes4.dex */
class YearPickerView extends android.widget.ListView {
    private final android.widget.YearPickerView.YearAdapter mAdapter;
    private final int mChildSize;
    private android.widget.YearPickerView.OnYearSelectedListener mOnYearSelectedListener;
    private final int mViewSize;

    public interface OnYearSelectedListener {
        void onYearChanged(android.widget.YearPickerView yearPickerView, int i);
    }

    public YearPickerView(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 16842868);
    }

    public YearPickerView(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public YearPickerView(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        setLayoutParams(new android.widget.AbsListView.LayoutParams(-1, -2));
        android.content.res.Resources resources = context.getResources();
        this.mViewSize = resources.getDimensionPixelOffset(com.android.internal.R.dimen.datepicker_view_animator_height);
        this.mChildSize = resources.getDimensionPixelOffset(com.android.internal.R.dimen.datepicker_year_label_height);
        setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() { // from class: android.widget.YearPickerView.1
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(android.widget.AdapterView<?> adapterView, android.view.View view, int i3, long j) {
                int yearForPosition = android.widget.YearPickerView.this.mAdapter.getYearForPosition(i3);
                android.widget.YearPickerView.this.mAdapter.setSelection(yearForPosition);
                if (android.widget.YearPickerView.this.mOnYearSelectedListener != null) {
                    android.widget.YearPickerView.this.mOnYearSelectedListener.onYearChanged(android.widget.YearPickerView.this, yearForPosition);
                }
            }
        });
        this.mAdapter = new android.widget.YearPickerView.YearAdapter(getContext());
        setAdapter((android.widget.ListAdapter) this.mAdapter);
    }

    public void setOnYearSelectedListener(android.widget.YearPickerView.OnYearSelectedListener onYearSelectedListener) {
        this.mOnYearSelectedListener = onYearSelectedListener;
    }

    public void setYear(final int i) {
        this.mAdapter.setSelection(i);
        post(new java.lang.Runnable() { // from class: android.widget.YearPickerView.2
            @Override // java.lang.Runnable
            public void run() {
                int positionForYear = android.widget.YearPickerView.this.mAdapter.getPositionForYear(i);
                if (positionForYear >= 0 && positionForYear < android.widget.YearPickerView.this.getCount()) {
                    android.widget.YearPickerView.this.setSelectionCentered(positionForYear);
                }
            }
        });
    }

    public void setSelectionCentered(int i) {
        setSelectionFromTop(i, (this.mViewSize / 2) - (this.mChildSize / 2));
    }

    public void setRange(android.icu.util.Calendar calendar, android.icu.util.Calendar calendar2) {
        this.mAdapter.setRange(calendar, calendar2);
    }

    private static class YearAdapter extends android.widget.BaseAdapter {
        private static final int ITEM_LAYOUT = 17367386;
        private static final int ITEM_TEXT_ACTIVATED_APPEARANCE = 16974777;
        private static final int ITEM_TEXT_APPEARANCE = 16974776;
        private int mActivatedYear;
        private int mCount;
        private final android.view.LayoutInflater mInflater;
        private int mMinYear;

        public YearAdapter(android.content.Context context) {
            this.mInflater = android.view.LayoutInflater.from(context);
        }

        public void setRange(android.icu.util.Calendar calendar, android.icu.util.Calendar calendar2) {
            int i = calendar.get(1);
            int i2 = (calendar2.get(1) - i) + 1;
            if (this.mMinYear != i || this.mCount != i2) {
                this.mMinYear = i;
                this.mCount = i2;
                notifyDataSetInvalidated();
            }
        }

        public boolean setSelection(int i) {
            if (this.mActivatedYear != i) {
                this.mActivatedYear = i;
                notifyDataSetChanged();
                return true;
            }
            return false;
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return this.mCount;
        }

        @Override // android.widget.Adapter
        public java.lang.Integer getItem(int i) {
            return java.lang.Integer.valueOf(getYearForPosition(i));
        }

        @Override // android.widget.Adapter
        public long getItemId(int i) {
            return getYearForPosition(i);
        }

        public int getPositionForYear(int i) {
            return i - this.mMinYear;
        }

        public int getYearForPosition(int i) {
            return this.mMinYear + i;
        }

        @Override // android.widget.BaseAdapter, android.widget.Adapter
        public boolean hasStableIds() {
            return true;
        }

        @Override // android.widget.Adapter
        public android.view.View getView(int i, android.view.View view, android.view.ViewGroup viewGroup) {
            android.widget.TextView textView;
            int i2;
            boolean z = view == null;
            if (z) {
                textView = (android.widget.TextView) this.mInflater.inflate(17367386, viewGroup, false);
            } else {
                textView = (android.widget.TextView) view;
            }
            int yearForPosition = getYearForPosition(i);
            boolean z2 = this.mActivatedYear == yearForPosition;
            if (z || textView.isActivated() != z2) {
                if (z2) {
                    i2 = 16974777;
                } else {
                    i2 = 16974776;
                }
                textView.setTextAppearance(i2);
                textView.setActivated(z2);
            }
            textView.lambda$setTextAsync$0(java.lang.Integer.toString(yearForPosition));
            return textView;
        }

        @Override // android.widget.BaseAdapter, android.widget.Adapter
        public int getItemViewType(int i) {
            return 0;
        }

        @Override // android.widget.BaseAdapter, android.widget.Adapter
        public int getViewTypeCount() {
            return 1;
        }

        @Override // android.widget.BaseAdapter, android.widget.Adapter
        public boolean isEmpty() {
            return false;
        }

        @Override // android.widget.BaseAdapter, android.widget.ListAdapter
        public boolean areAllItemsEnabled() {
            return true;
        }

        @Override // android.widget.BaseAdapter, android.widget.ListAdapter
        public boolean isEnabled(int i) {
            return true;
        }
    }

    public int getFirstPositionOffset() {
        android.view.View childAt = getChildAt(0);
        if (childAt == null) {
            return 0;
        }
        return childAt.getTop();
    }

    @Override // android.widget.AdapterView, android.view.View
    public void onInitializeAccessibilityEventInternal(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEventInternal(accessibilityEvent);
        if (accessibilityEvent.getEventType() == 4096) {
            accessibilityEvent.setFromIndex(0);
            accessibilityEvent.setToIndex(0);
        }
    }
}
