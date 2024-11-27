package android.widget;

/* loaded from: classes4.dex */
class SimpleMonthView extends android.view.View {
    private static final int DAYS_IN_WEEK = 7;
    private static final int DEFAULT_SELECTED_DAY = -1;
    private static final int DEFAULT_WEEK_START = 1;
    private static final int MAX_WEEKS_IN_MONTH = 6;
    private static final java.lang.String MONTH_YEAR_FORMAT = "MMMMy";
    private static final int SELECTED_HIGHLIGHT_ALPHA = 176;
    private int mActivatedDay;
    private final android.icu.util.Calendar mCalendar;
    private int mCellWidth;
    private final java.text.NumberFormat mDayFormatter;
    private int mDayHeight;
    private final android.graphics.Paint mDayHighlightPaint;
    private final android.graphics.Paint mDayHighlightSelectorPaint;
    private int mDayOfWeekHeight;
    private final java.lang.String[] mDayOfWeekLabels;
    private final android.text.TextPaint mDayOfWeekPaint;
    private int mDayOfWeekStart;
    private final android.text.TextPaint mDayPaint;
    private final android.graphics.Paint mDaySelectorPaint;
    private int mDaySelectorRadius;
    private android.content.res.ColorStateList mDayTextColor;
    private int mDaysInMonth;
    private final int mDesiredCellWidth;
    private final int mDesiredDayHeight;
    private final int mDesiredDayOfWeekHeight;
    private final int mDesiredDaySelectorRadius;
    private final int mDesiredMonthHeight;
    private int mEnabledDayEnd;
    private int mEnabledDayStart;
    private int mHighlightedDay;
    private boolean mIsTouchHighlighted;
    private final java.util.Locale mLocale;
    private int mMonth;
    private int mMonthHeight;
    private final android.text.TextPaint mMonthPaint;
    private java.lang.String mMonthYearLabel;
    private android.widget.SimpleMonthView.OnDayClickListener mOnDayClickListener;
    private int mPaddedHeight;
    private int mPaddedWidth;
    private int mPreviouslyHighlightedDay;
    private int mToday;
    private final android.widget.SimpleMonthView.MonthViewTouchHelper mTouchHelper;
    private int mWeekStart;
    private int mYear;

    public interface OnDayClickListener {
        void onDayClick(android.widget.SimpleMonthView simpleMonthView, android.icu.util.Calendar calendar);
    }

    public SimpleMonthView(android.content.Context context) {
        this(context, null);
    }

    public SimpleMonthView(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 16843612);
    }

    public SimpleMonthView(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SimpleMonthView(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mMonthPaint = new android.text.TextPaint();
        this.mDayOfWeekPaint = new android.text.TextPaint();
        this.mDayPaint = new android.text.TextPaint();
        this.mDaySelectorPaint = new android.graphics.Paint();
        this.mDayHighlightPaint = new android.graphics.Paint();
        this.mDayHighlightSelectorPaint = new android.graphics.Paint();
        this.mDayOfWeekLabels = new java.lang.String[7];
        this.mActivatedDay = -1;
        this.mToday = -1;
        this.mWeekStart = 1;
        this.mEnabledDayStart = 1;
        this.mEnabledDayEnd = 31;
        this.mHighlightedDay = -1;
        this.mPreviouslyHighlightedDay = -1;
        this.mIsTouchHighlighted = false;
        android.content.res.Resources resources = context.getResources();
        this.mDesiredMonthHeight = resources.getDimensionPixelSize(com.android.internal.R.dimen.date_picker_month_height);
        this.mDesiredDayOfWeekHeight = resources.getDimensionPixelSize(com.android.internal.R.dimen.date_picker_day_of_week_height);
        this.mDesiredDayHeight = resources.getDimensionPixelSize(com.android.internal.R.dimen.date_picker_day_height);
        this.mDesiredCellWidth = resources.getDimensionPixelSize(com.android.internal.R.dimen.date_picker_day_width);
        this.mDesiredDaySelectorRadius = resources.getDimensionPixelSize(com.android.internal.R.dimen.date_picker_day_selector_radius);
        this.mTouchHelper = new android.widget.SimpleMonthView.MonthViewTouchHelper(this);
        setAccessibilityDelegate(this.mTouchHelper);
        setImportantForAccessibility(1);
        this.mLocale = resources.getConfiguration().locale;
        this.mCalendar = android.icu.util.Calendar.getInstance(this.mLocale);
        this.mDayFormatter = java.text.NumberFormat.getIntegerInstance(this.mLocale);
        updateMonthYearLabel();
        updateDayOfWeekLabels();
        initPaints(resources);
    }

    private void updateMonthYearLabel() {
        android.icu.text.SimpleDateFormat simpleDateFormat = new android.icu.text.SimpleDateFormat(android.text.format.DateFormat.getBestDateTimePattern(this.mLocale, MONTH_YEAR_FORMAT), this.mLocale);
        simpleDateFormat.setContext(android.icu.text.DisplayContext.CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE);
        this.mMonthYearLabel = simpleDateFormat.format(this.mCalendar.getTime());
    }

    private void updateDayOfWeekLabels() {
        java.lang.String[] weekdays = android.icu.text.DateFormatSymbols.getInstance(this.mLocale).getWeekdays(0, 2);
        for (int i = 0; i < 7; i++) {
            this.mDayOfWeekLabels[i] = weekdays[(((this.mWeekStart + i) - 1) % 7) + 1];
        }
    }

    private android.content.res.ColorStateList applyTextAppearance(android.graphics.Paint paint, int i) {
        android.content.res.TypedArray obtainStyledAttributes = this.mContext.obtainStyledAttributes(null, com.android.internal.R.styleable.TextAppearance, 0, i);
        java.lang.String string = obtainStyledAttributes.getString(12);
        if (string != null) {
            paint.setTypeface(android.graphics.Typeface.create(string, 0));
        }
        paint.setTextSize(obtainStyledAttributes.getDimensionPixelSize(0, (int) paint.getTextSize()));
        android.content.res.ColorStateList colorStateList = obtainStyledAttributes.getColorStateList(3);
        if (colorStateList != null) {
            paint.setColor(colorStateList.getColorForState(ENABLED_STATE_SET, 0));
        }
        obtainStyledAttributes.recycle();
        return colorStateList;
    }

    public int getMonthHeight() {
        return this.mMonthHeight;
    }

    public int getCellWidth() {
        return this.mCellWidth;
    }

    public void setMonthTextAppearance(int i) {
        applyTextAppearance(this.mMonthPaint, i);
        invalidate();
    }

    public void setDayOfWeekTextAppearance(int i) {
        applyTextAppearance(this.mDayOfWeekPaint, i);
        invalidate();
    }

    public void setDayTextAppearance(int i) {
        android.content.res.ColorStateList applyTextAppearance = applyTextAppearance(this.mDayPaint, i);
        if (applyTextAppearance != null) {
            this.mDayTextColor = applyTextAppearance;
        }
        invalidate();
    }

    private void initPaints(android.content.res.Resources resources) {
        java.lang.String string = resources.getString(com.android.internal.R.string.date_picker_month_typeface);
        java.lang.String string2 = resources.getString(com.android.internal.R.string.date_picker_day_of_week_typeface);
        java.lang.String string3 = resources.getString(com.android.internal.R.string.date_picker_day_typeface);
        int dimensionPixelSize = resources.getDimensionPixelSize(com.android.internal.R.dimen.date_picker_month_text_size);
        int dimensionPixelSize2 = resources.getDimensionPixelSize(com.android.internal.R.dimen.date_picker_day_of_week_text_size);
        int dimensionPixelSize3 = resources.getDimensionPixelSize(com.android.internal.R.dimen.date_picker_day_text_size);
        this.mMonthPaint.setAntiAlias(true);
        this.mMonthPaint.setTextSize(dimensionPixelSize);
        this.mMonthPaint.setTypeface(android.graphics.Typeface.create(string, 0));
        this.mMonthPaint.setTextAlign(android.graphics.Paint.Align.CENTER);
        this.mMonthPaint.setStyle(android.graphics.Paint.Style.FILL);
        this.mDayOfWeekPaint.setAntiAlias(true);
        this.mDayOfWeekPaint.setTextSize(dimensionPixelSize2);
        this.mDayOfWeekPaint.setTypeface(android.graphics.Typeface.create(string2, 0));
        this.mDayOfWeekPaint.setTextAlign(android.graphics.Paint.Align.CENTER);
        this.mDayOfWeekPaint.setStyle(android.graphics.Paint.Style.FILL);
        this.mDaySelectorPaint.setAntiAlias(true);
        this.mDaySelectorPaint.setStyle(android.graphics.Paint.Style.FILL);
        this.mDayHighlightPaint.setAntiAlias(true);
        this.mDayHighlightPaint.setStyle(android.graphics.Paint.Style.FILL);
        this.mDayHighlightSelectorPaint.setAntiAlias(true);
        this.mDayHighlightSelectorPaint.setStyle(android.graphics.Paint.Style.FILL);
        this.mDayPaint.setAntiAlias(true);
        this.mDayPaint.setTextSize(dimensionPixelSize3);
        this.mDayPaint.setTypeface(android.graphics.Typeface.create(string3, 0));
        this.mDayPaint.setTextAlign(android.graphics.Paint.Align.CENTER);
        this.mDayPaint.setStyle(android.graphics.Paint.Style.FILL);
    }

    void setMonthTextColor(android.content.res.ColorStateList colorStateList) {
        this.mMonthPaint.setColor(colorStateList.getColorForState(ENABLED_STATE_SET, 0));
        invalidate();
    }

    void setDayOfWeekTextColor(android.content.res.ColorStateList colorStateList) {
        this.mDayOfWeekPaint.setColor(colorStateList.getColorForState(ENABLED_STATE_SET, 0));
        invalidate();
    }

    void setDayTextColor(android.content.res.ColorStateList colorStateList) {
        this.mDayTextColor = colorStateList;
        invalidate();
    }

    void setDaySelectorColor(android.content.res.ColorStateList colorStateList) {
        int colorForState = colorStateList.getColorForState(android.util.StateSet.get(40), 0);
        this.mDaySelectorPaint.setColor(colorForState);
        this.mDayHighlightSelectorPaint.setColor(colorForState);
        this.mDayHighlightSelectorPaint.setAlpha(176);
        invalidate();
    }

    void setDayHighlightColor(android.content.res.ColorStateList colorStateList) {
        this.mDayHighlightPaint.setColor(colorStateList.getColorForState(android.util.StateSet.get(24), 0));
        invalidate();
    }

    public void setOnDayClickListener(android.widget.SimpleMonthView.OnDayClickListener onDayClickListener) {
        this.mOnDayClickListener = onDayClickListener;
    }

    @Override // android.view.View
    public boolean dispatchHoverEvent(android.view.MotionEvent motionEvent) {
        return this.mTouchHelper.dispatchHoverEvent(motionEvent) || super.dispatchHoverEvent(motionEvent);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:12:0x003e A[RETURN] */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean onTouchEvent(android.view.MotionEvent motionEvent) {
        int x = (int) (motionEvent.getX() + 0.5f);
        int y = (int) (motionEvent.getY() + 0.5f);
        int action = motionEvent.getAction();
        switch (action) {
            case 0:
            case 2:
                int dayAtLocation = getDayAtLocation(x, y);
                this.mIsTouchHighlighted = true;
                if (this.mHighlightedDay != dayAtLocation) {
                    this.mHighlightedDay = dayAtLocation;
                    this.mPreviouslyHighlightedDay = dayAtLocation;
                    invalidate();
                }
                return action != 0 || dayAtLocation >= 0;
            case 1:
                onDayClicked(getDayAtLocation(x, y));
                this.mHighlightedDay = -1;
                this.mIsTouchHighlighted = false;
                invalidate();
            case 3:
                this.mHighlightedDay = -1;
                this.mIsTouchHighlighted = false;
                invalidate();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0036  */
    @Override // android.view.View, android.view.KeyEvent.Callback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean onKeyDown(int i, android.view.KeyEvent keyEvent) {
        int i2;
        boolean z = false;
        switch (keyEvent.getKeyCode()) {
            case 19:
                if (keyEvent.hasNoModifiers()) {
                    ensureFocusedDay();
                    if (this.mHighlightedDay > 7) {
                        this.mHighlightedDay -= 7;
                        z = true;
                        break;
                    }
                }
                break;
            case 20:
                if (keyEvent.hasNoModifiers()) {
                    ensureFocusedDay();
                    if (this.mHighlightedDay <= this.mDaysInMonth - 7) {
                        this.mHighlightedDay += 7;
                        z = true;
                        break;
                    }
                }
                break;
            case 21:
                if (keyEvent.hasNoModifiers()) {
                    z = moveOneDay(isLayoutRtl());
                    break;
                }
                break;
            case 22:
                if (keyEvent.hasNoModifiers()) {
                    z = moveOneDay(!isLayoutRtl());
                    break;
                }
                break;
            case 23:
            case 66:
            case 160:
                if (this.mHighlightedDay != -1) {
                    onDayClicked(this.mHighlightedDay);
                    return true;
                }
                break;
            case 61:
                if (keyEvent.hasNoModifiers()) {
                    i2 = 2;
                } else if (!keyEvent.hasModifiers(1)) {
                    i2 = 0;
                } else {
                    i2 = 1;
                }
                if (i2 != 0) {
                    android.view.ViewParent parent = getParent();
                    android.view.View view = this;
                    do {
                        view = view.focusSearch(i2);
                        if (view != null && view != this) {
                        }
                        if (view != null) {
                            view.requestFocus();
                            return true;
                        }
                    } while (view.getParent() == parent);
                    if (view != null) {
                    }
                }
                break;
        }
        if (z) {
            invalidate();
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }

    private boolean moveOneDay(boolean z) {
        ensureFocusedDay();
        if (z) {
            if (!isLastDayOfWeek(this.mHighlightedDay) && this.mHighlightedDay < this.mDaysInMonth) {
                this.mHighlightedDay++;
                return true;
            }
        } else if (!isFirstDayOfWeek(this.mHighlightedDay) && this.mHighlightedDay > 1) {
            this.mHighlightedDay--;
            return true;
        }
        return false;
    }

    @Override // android.view.View
    protected void onFocusChanged(boolean z, int i, android.graphics.Rect rect) {
        if (z) {
            int findDayOffset = findDayOffset();
            switch (i) {
                case 17:
                    this.mHighlightedDay = java.lang.Math.min(this.mDaysInMonth, ((findClosestRow(rect) + 1) * 7) - findDayOffset);
                    break;
                case 33:
                    int findClosestColumn = (findClosestColumn(rect) - findDayOffset) + (((this.mDaysInMonth + findDayOffset) / 7) * 7) + 1;
                    if (findClosestColumn > this.mDaysInMonth) {
                        findClosestColumn -= 7;
                    }
                    this.mHighlightedDay = findClosestColumn;
                    break;
                case 66:
                    int findClosestRow = findClosestRow(rect);
                    this.mHighlightedDay = findClosestRow != 0 ? 1 + ((findClosestRow * 7) - findDayOffset) : 1;
                    break;
                case 130:
                    int findClosestColumn2 = (findClosestColumn(rect) - findDayOffset) + 1;
                    if (findClosestColumn2 < 1) {
                        findClosestColumn2 += 7;
                    }
                    this.mHighlightedDay = findClosestColumn2;
                    break;
            }
            ensureFocusedDay();
            invalidate();
        }
        super.onFocusChanged(z, i, rect);
    }

    private int findClosestRow(android.graphics.Rect rect) {
        if (rect == null) {
            return 3;
        }
        if (this.mDayHeight == 0) {
            return 0;
        }
        int centerY = rect.centerY();
        android.text.TextPaint textPaint = this.mDayPaint;
        int i = this.mMonthHeight + this.mDayOfWeekHeight;
        int round = java.lang.Math.round(((int) (centerY - ((i + (r3 / 2)) - ((textPaint.ascent() + textPaint.descent()) / 2.0f)))) / this.mDayHeight);
        int findDayOffset = findDayOffset() + this.mDaysInMonth;
        return android.util.MathUtils.constrain(round, 0, (findDayOffset / 7) - (findDayOffset % 7 == 0 ? 1 : 0));
    }

    private int findClosestColumn(android.graphics.Rect rect) {
        if (rect == null) {
            return 3;
        }
        if (this.mCellWidth == 0) {
            return 0;
        }
        return isLayoutRtl() ? (7 - r3) - 1 : android.util.MathUtils.constrain((rect.centerX() - this.mPaddingLeft) / this.mCellWidth, 0, 6);
    }

    @Override // android.view.View
    public void getFocusedRect(android.graphics.Rect rect) {
        if (this.mHighlightedDay > 0) {
            getBoundsForDay(this.mHighlightedDay, rect);
        } else {
            super.getFocusedRect(rect);
        }
    }

    @Override // android.view.View
    protected void onFocusLost() {
        if (!this.mIsTouchHighlighted) {
            this.mPreviouslyHighlightedDay = this.mHighlightedDay;
            this.mHighlightedDay = -1;
            invalidate();
        }
        super.onFocusLost();
    }

    private void ensureFocusedDay() {
        if (this.mHighlightedDay != -1) {
            return;
        }
        if (this.mPreviouslyHighlightedDay != -1) {
            this.mHighlightedDay = this.mPreviouslyHighlightedDay;
        } else if (this.mActivatedDay != -1) {
            this.mHighlightedDay = this.mActivatedDay;
        } else {
            this.mHighlightedDay = 1;
        }
    }

    private boolean isFirstDayOfWeek(int i) {
        return ((findDayOffset() + i) - 1) % 7 == 0;
    }

    private boolean isLastDayOfWeek(int i) {
        return (findDayOffset() + i) % 7 == 0;
    }

    @Override // android.view.View
    protected void onDraw(android.graphics.Canvas canvas) {
        canvas.translate(getPaddingLeft(), getPaddingTop());
        drawMonth(canvas);
        drawDaysOfWeek(canvas);
        drawDays(canvas);
        canvas.translate(-r0, -r1);
    }

    private void drawMonth(android.graphics.Canvas canvas) {
        canvas.drawText(this.mMonthYearLabel, this.mPaddedWidth / 2.0f, (this.mMonthHeight - (this.mMonthPaint.ascent() + this.mMonthPaint.descent())) / 2.0f, this.mMonthPaint);
    }

    public java.lang.String getMonthYearLabel() {
        return this.mMonthYearLabel;
    }

    private void drawDaysOfWeek(android.graphics.Canvas canvas) {
        android.text.TextPaint textPaint = this.mDayOfWeekPaint;
        int i = this.mMonthHeight;
        int i2 = this.mDayOfWeekHeight;
        int i3 = this.mCellWidth;
        float ascent = (textPaint.ascent() + textPaint.descent()) / 2.0f;
        int i4 = i + (i2 / 2);
        for (int i5 = 0; i5 < 7; i5++) {
            int i6 = (i3 * i5) + (i3 / 2);
            if (isLayoutRtl()) {
                i6 = this.mPaddedWidth - i6;
            }
            canvas.drawText(this.mDayOfWeekLabels[i5], i6, i4 - ascent, textPaint);
        }
    }

    private void drawDays(android.graphics.Canvas canvas) {
        int i;
        int i2;
        int colorForState;
        android.graphics.Paint paint;
        android.text.TextPaint textPaint = this.mDayPaint;
        int i3 = this.mMonthHeight + this.mDayOfWeekHeight;
        int i4 = this.mDayHeight;
        int i5 = this.mCellWidth;
        float ascent = (textPaint.ascent() + textPaint.descent()) / 2.0f;
        int i6 = i3 + (i4 / 2);
        int findDayOffset = findDayOffset();
        int i7 = 1;
        while (i7 <= this.mDaysInMonth) {
            int i8 = (i5 * findDayOffset) + (i5 / 2);
            if (isLayoutRtl()) {
                i8 = this.mPaddedWidth - i8;
            }
            boolean isDayEnabled = isDayEnabled(i7);
            if (!isDayEnabled) {
                i = 0;
            } else {
                i = 8;
            }
            boolean z = this.mActivatedDay == i7;
            boolean z2 = this.mHighlightedDay == i7;
            if (z) {
                i |= 32;
                if (z2) {
                    paint = this.mDayHighlightSelectorPaint;
                } else {
                    paint = this.mDaySelectorPaint;
                }
                canvas.drawCircle(i8, i6, this.mDaySelectorRadius, paint);
            } else if (z2) {
                i |= 16;
                if (isDayEnabled) {
                    canvas.drawCircle(i8, i6, this.mDaySelectorRadius, this.mDayHighlightPaint);
                }
            }
            if ((this.mToday == i7) && !z) {
                colorForState = this.mDaySelectorPaint.getColor();
                i2 = 0;
            } else {
                i2 = 0;
                colorForState = this.mDayTextColor.getColorForState(android.util.StateSet.get(i), 0);
            }
            textPaint.setColor(colorForState);
            canvas.drawText(this.mDayFormatter.format(i7), i8, i6 - ascent, textPaint);
            findDayOffset++;
            if (findDayOffset == 7) {
                i6 += i4;
                findDayOffset = i2;
            }
            i7++;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isDayEnabled(int i) {
        return i >= this.mEnabledDayStart && i <= this.mEnabledDayEnd;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isValidDayOfMonth(int i) {
        return i >= 1 && i <= this.mDaysInMonth;
    }

    private static boolean isValidDayOfWeek(int i) {
        return i >= 1 && i <= 7;
    }

    private static boolean isValidMonth(int i) {
        return i >= 0 && i <= 11;
    }

    public void setSelectedDay(int i) {
        this.mActivatedDay = i;
        this.mTouchHelper.invalidateRoot();
        invalidate();
    }

    public void setFirstDayOfWeek(int i) {
        if (isValidDayOfWeek(i)) {
            this.mWeekStart = i;
        } else {
            this.mWeekStart = this.mCalendar.getFirstDayOfWeek();
        }
        updateDayOfWeekLabels();
        this.mTouchHelper.invalidateRoot();
        invalidate();
    }

    void setMonthParams(int i, int i2, int i3, int i4, int i5, int i6) {
        this.mActivatedDay = i;
        if (isValidMonth(i2)) {
            this.mMonth = i2;
        }
        this.mYear = i3;
        this.mCalendar.set(2, this.mMonth);
        this.mCalendar.set(1, this.mYear);
        this.mCalendar.set(5, 1);
        this.mDayOfWeekStart = this.mCalendar.get(7);
        if (isValidDayOfWeek(i4)) {
            this.mWeekStart = i4;
        } else {
            this.mWeekStart = this.mCalendar.getFirstDayOfWeek();
        }
        android.icu.util.Calendar calendar = android.icu.util.Calendar.getInstance();
        this.mToday = -1;
        this.mDaysInMonth = getDaysInMonth(this.mMonth, this.mYear);
        int i7 = 0;
        while (i7 < this.mDaysInMonth) {
            i7++;
            if (sameDay(i7, calendar)) {
                this.mToday = i7;
            }
        }
        this.mEnabledDayStart = android.util.MathUtils.constrain(i5, 1, this.mDaysInMonth);
        this.mEnabledDayEnd = android.util.MathUtils.constrain(i6, this.mEnabledDayStart, this.mDaysInMonth);
        updateMonthYearLabel();
        updateDayOfWeekLabels();
        this.mTouchHelper.invalidateRoot();
        invalidate();
    }

    private static int getDaysInMonth(int i, int i2) {
        switch (i) {
            case 0:
            case 2:
            case 4:
            case 6:
            case 7:
            case 9:
            case 11:
                return 31;
            case 1:
                return (i2 % 4 != 0 || (i2 % 100 == 0 && i2 % 400 != 0)) ? 28 : 29;
            case 3:
            case 5:
            case 8:
            case 10:
                return 30;
            default:
                throw new java.lang.IllegalArgumentException("Invalid Month");
        }
    }

    private boolean sameDay(int i, android.icu.util.Calendar calendar) {
        return this.mYear == calendar.get(1) && this.mMonth == calendar.get(2) && i == calendar.get(5);
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        setMeasuredDimension(resolveSize((this.mDesiredCellWidth * 7) + getPaddingStart() + getPaddingEnd(), i), resolveSize((this.mDesiredDayHeight * 6) + this.mDesiredDayOfWeekHeight + this.mDesiredMonthHeight + getPaddingTop() + getPaddingBottom(), i2));
    }

    @Override // android.view.View
    public void onRtlPropertiesChanged(int i) {
        super.onRtlPropertiesChanged(i);
        requestLayout();
    }

    @Override // android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (!z) {
            return;
        }
        int i5 = i3 - i;
        int i6 = i4 - i2;
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();
        int i7 = (i5 - paddingRight) - paddingLeft;
        int i8 = (i6 - paddingBottom) - paddingTop;
        if (i7 == this.mPaddedWidth || i8 == this.mPaddedHeight) {
            return;
        }
        this.mPaddedWidth = i7;
        this.mPaddedHeight = i8;
        float measuredHeight = i8 / ((getMeasuredHeight() - paddingTop) - paddingBottom);
        int i9 = this.mPaddedWidth / 7;
        this.mMonthHeight = (int) (this.mDesiredMonthHeight * measuredHeight);
        this.mDayOfWeekHeight = (int) (this.mDesiredDayOfWeekHeight * measuredHeight);
        this.mDayHeight = (int) (this.mDesiredDayHeight * measuredHeight);
        this.mCellWidth = i9;
        this.mDaySelectorRadius = java.lang.Math.min(this.mDesiredDaySelectorRadius, java.lang.Math.min((i9 / 2) + java.lang.Math.min(paddingLeft, paddingRight), (this.mDayHeight / 2) + paddingBottom));
        this.mTouchHelper.invalidateRoot();
    }

    private int findDayOffset() {
        int i = this.mDayOfWeekStart - this.mWeekStart;
        if (this.mDayOfWeekStart < this.mWeekStart) {
            return i + 7;
        }
        return i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getDayAtLocation(int i, int i2) {
        int i3;
        int paddingTop;
        int paddingLeft = i - getPaddingLeft();
        if (paddingLeft < 0 || paddingLeft >= this.mPaddedWidth || (paddingTop = i2 - getPaddingTop()) < (i3 = this.mMonthHeight + this.mDayOfWeekHeight) || paddingTop >= this.mPaddedHeight) {
            return -1;
        }
        if (isLayoutRtl()) {
            paddingLeft = this.mPaddedWidth - paddingLeft;
        }
        int findDayOffset = ((((paddingLeft * 7) / this.mPaddedWidth) + (((paddingTop - i3) / this.mDayHeight) * 7)) + 1) - findDayOffset();
        if (!isValidDayOfMonth(findDayOffset)) {
            return -1;
        }
        return findDayOffset;
    }

    public boolean getBoundsForDay(int i, android.graphics.Rect rect) {
        int paddingLeft;
        if (!isValidDayOfMonth(i)) {
            return false;
        }
        int findDayOffset = (i - 1) + findDayOffset();
        int i2 = findDayOffset % 7;
        int i3 = this.mCellWidth;
        if (isLayoutRtl()) {
            paddingLeft = (getWidth() - getPaddingRight()) - ((i2 + 1) * i3);
        } else {
            paddingLeft = getPaddingLeft() + (i2 * i3);
        }
        int i4 = this.mDayHeight;
        int paddingTop = getPaddingTop() + this.mMonthHeight + this.mDayOfWeekHeight + ((findDayOffset / 7) * i4);
        rect.set(paddingLeft, paddingTop, i3 + paddingLeft, i4 + paddingTop);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean onDayClicked(int i) {
        if (!isValidDayOfMonth(i) || !isDayEnabled(i)) {
            return false;
        }
        if (this.mOnDayClickListener != null) {
            android.icu.util.Calendar calendar = android.icu.util.Calendar.getInstance();
            calendar.set(this.mYear, this.mMonth, i);
            this.mOnDayClickListener.onDayClick(this, calendar);
        }
        this.mTouchHelper.sendEventForVirtualView(i, 1);
        return true;
    }

    @Override // android.view.View
    public android.view.PointerIcon onResolvePointerIcon(android.view.MotionEvent motionEvent, int i) {
        int i2;
        if (!isEnabled()) {
            return null;
        }
        if (motionEvent.isFromSource(8194) && getDayAtLocation((int) (motionEvent.getX() + 0.5f), (int) (motionEvent.getY() + 0.5f)) >= 0) {
            if (android.view.flags.Flags.enableArrowIconOnHoverWhenClickable()) {
                i2 = 1000;
            } else {
                i2 = 1002;
            }
            return android.view.PointerIcon.getSystemIcon(getContext(), i2);
        }
        return super.onResolvePointerIcon(motionEvent, i);
    }

    private class MonthViewTouchHelper extends com.android.internal.widget.ExploreByTouchHelper {
        private static final java.lang.String DATE_FORMAT = "dd MMMM yyyy";
        private final android.icu.util.Calendar mTempCalendar;
        private final android.graphics.Rect mTempRect;

        public MonthViewTouchHelper(android.view.View view) {
            super(view);
            this.mTempRect = new android.graphics.Rect();
            this.mTempCalendar = android.icu.util.Calendar.getInstance();
        }

        @Override // com.android.internal.widget.ExploreByTouchHelper
        protected int getVirtualViewAt(float f, float f2) {
            int dayAtLocation = android.widget.SimpleMonthView.this.getDayAtLocation((int) (f + 0.5f), (int) (f2 + 0.5f));
            if (dayAtLocation != -1) {
                return dayAtLocation;
            }
            return Integer.MIN_VALUE;
        }

        @Override // com.android.internal.widget.ExploreByTouchHelper
        protected void getVisibleVirtualViews(android.util.IntArray intArray) {
            for (int i = 1; i <= android.widget.SimpleMonthView.this.mDaysInMonth; i++) {
                intArray.add(i);
            }
        }

        @Override // com.android.internal.widget.ExploreByTouchHelper
        protected void onPopulateEventForVirtualView(int i, android.view.accessibility.AccessibilityEvent accessibilityEvent) {
            accessibilityEvent.setContentDescription(getDayDescription(i));
        }

        @Override // com.android.internal.widget.ExploreByTouchHelper
        protected void onPopulateNodeForVirtualView(int i, android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
            if (!android.widget.SimpleMonthView.this.getBoundsForDay(i, this.mTempRect)) {
                this.mTempRect.setEmpty();
                accessibilityNodeInfo.setContentDescription("");
                accessibilityNodeInfo.setBoundsInParent(this.mTempRect);
                accessibilityNodeInfo.setVisibleToUser(false);
                return;
            }
            accessibilityNodeInfo.setText(getDayText(i));
            accessibilityNodeInfo.setContentDescription(getDayDescription(i));
            if (i == android.widget.SimpleMonthView.this.mToday) {
                accessibilityNodeInfo.setStateDescription(android.icu.text.RelativeDateTimeFormatter.getInstance().format(android.icu.text.RelativeDateTimeFormatter.Direction.THIS, android.icu.text.RelativeDateTimeFormatter.AbsoluteUnit.DAY));
            }
            if (i == android.widget.SimpleMonthView.this.mActivatedDay) {
                accessibilityNodeInfo.setSelected(true);
            }
            accessibilityNodeInfo.setBoundsInParent(this.mTempRect);
            boolean isDayEnabled = android.widget.SimpleMonthView.this.isDayEnabled(i);
            if (isDayEnabled) {
                accessibilityNodeInfo.addAction(android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK);
            }
            accessibilityNodeInfo.setEnabled(isDayEnabled);
            accessibilityNodeInfo.setClickable(true);
            if (i == android.widget.SimpleMonthView.this.mActivatedDay) {
                accessibilityNodeInfo.setChecked(true);
            }
        }

        @Override // com.android.internal.widget.ExploreByTouchHelper
        protected boolean onPerformActionForVirtualView(int i, int i2, android.os.Bundle bundle) {
            switch (i2) {
                case 16:
                    return android.widget.SimpleMonthView.this.onDayClicked(i);
                default:
                    return false;
            }
        }

        private java.lang.CharSequence getDayDescription(int i) {
            if (android.widget.SimpleMonthView.this.isValidDayOfMonth(i)) {
                this.mTempCalendar.set(android.widget.SimpleMonthView.this.mYear, android.widget.SimpleMonthView.this.mMonth, i);
                return android.text.format.DateFormat.format(DATE_FORMAT, this.mTempCalendar.getTimeInMillis());
            }
            return "";
        }

        private java.lang.CharSequence getDayText(int i) {
            if (android.widget.SimpleMonthView.this.isValidDayOfMonth(i)) {
                return android.widget.SimpleMonthView.this.mDayFormatter.format(i);
            }
            return null;
        }
    }
}
