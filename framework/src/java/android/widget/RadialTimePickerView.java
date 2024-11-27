package android.widget;

/* loaded from: classes4.dex */
public class RadialTimePickerView extends android.view.View {
    private static final int AM = 0;
    private static final int ANIM_DURATION_NORMAL = 500;
    private static final int ANIM_DURATION_TOUCH = 60;
    private static final int DEGREES_FOR_ONE_HOUR = 30;
    private static final int DEGREES_FOR_ONE_MINUTE = 6;
    public static final int HOURS = 0;
    private static final int HOURS_INNER = 2;
    private static final int HOURS_IN_CIRCLE = 12;
    public static final int MINUTES = 1;
    private static final int MINUTES_IN_CIRCLE = 60;
    private static final int MISSING_COLOR = -65281;
    private static final int NUM_POSITIONS = 12;
    private static final int PM = 1;
    private static final int SELECTOR_CIRCLE = 0;
    private static final int SELECTOR_DOT = 1;
    private static final int SELECTOR_LINE = 2;
    private static final java.lang.String TAG = "RadialTimePickerView";
    private final android.util.FloatProperty<android.widget.RadialTimePickerView> HOURS_TO_MINUTES;
    private int mAmOrPm;
    private int mCenterDotRadius;
    boolean mChangedDuringTouch;
    private int mCircleRadius;
    private float mDisabledAlpha;
    private int mHalfwayDist;
    private final java.lang.String[] mHours12Texts;
    private float mHoursToMinutes;
    private android.animation.ObjectAnimator mHoursToMinutesAnimator;
    private final java.lang.String[] mInnerHours24Texts;
    private java.lang.String[] mInnerTextHours;
    private final float[] mInnerTextX;
    private final float[] mInnerTextY;
    private boolean mInputEnabled;
    private boolean mIs24HourMode;
    private boolean mIsOnInnerCircle;
    private android.widget.RadialTimePickerView.OnValueSelectedListener mListener;
    private int mMaxDistForOuterNumber;
    private int mMinDistForInnerNumber;
    private java.lang.String[] mMinutesText;
    private final java.lang.String[] mMinutesTexts;
    private final java.lang.String[] mOuterHours24Texts;
    private java.lang.String[] mOuterTextHours;
    private final float[][] mOuterTextX;
    private final float[][] mOuterTextY;
    private final android.graphics.Paint[] mPaint;
    private final android.graphics.Paint mPaintBackground;
    private final android.graphics.Paint mPaintCenter;
    private final android.graphics.Paint[] mPaintSelector;
    private final int[] mSelectionDegrees;
    private int mSelectorColor;
    private int mSelectorDotColor;
    private int mSelectorDotRadius;
    private final android.graphics.Path mSelectorPath;
    private int mSelectorRadius;
    private int mSelectorStroke;
    private boolean mShowHours;
    private final android.content.res.ColorStateList[] mTextColor;
    private final int[] mTextInset;
    private final int[] mTextSize;
    private final android.widget.RadialTimePickerView.RadialPickerTouchHelper mTouchHelper;
    private final android.graphics.Typeface mTypeface;
    private int mXCenter;
    private int mYCenter;
    private static final int[] HOURS_NUMBERS = {12, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
    private static final int[] HOURS_NUMBERS_24 = {0, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23};
    private static final int[] MINUTES_NUMBERS = {0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55};
    private static final int[] SNAP_PREFER_30S_MAP = new int[361];
    private static final float[] COS_30 = new float[12];
    private static final float[] SIN_30 = new float[12];

    interface OnValueSelectedListener {
        void onValueSelected(int i, int i2, boolean z);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface PickerType {
    }

    static {
        preparePrefer30sMap();
        double d = 1.5707963267948966d;
        for (int i = 0; i < 12; i++) {
            COS_30[i] = (float) java.lang.Math.cos(d);
            SIN_30[i] = (float) java.lang.Math.sin(d);
            d += 0.5235987755982988d;
        }
    }

    private static void preparePrefer30sMap() {
        int i = 1;
        int i2 = 8;
        int i3 = 0;
        for (int i4 = 0; i4 < 361; i4++) {
            SNAP_PREFER_30S_MAP[i4] = i3;
            if (i == i2) {
                i3 += 6;
                if (i3 == 360) {
                    i2 = 7;
                } else if (i3 % 30 == 0) {
                    i2 = 14;
                } else {
                    i2 = 4;
                }
                i = 1;
            } else {
                i++;
            }
        }
    }

    private static int snapPrefer30s(int i) {
        if (SNAP_PREFER_30S_MAP == null) {
            return -1;
        }
        return SNAP_PREFER_30S_MAP[i];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int snapOnly30s(int i, int i2) {
        int i3 = (i / 30) * 30;
        int i4 = i3 + 30;
        if (i2 != 1) {
            if (i2 == -1) {
                return i == i3 ? i3 - 30 : i3;
            }
            if (i - i3 < i4 - i) {
                return i3;
            }
        }
        return i4;
    }

    public RadialTimePickerView(android.content.Context context) {
        this(context, null);
    }

    public RadialTimePickerView(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 16843933);
    }

    public RadialTimePickerView(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public RadialTimePickerView(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet);
        this.HOURS_TO_MINUTES = new android.util.FloatProperty<android.widget.RadialTimePickerView>("hoursToMinutes") { // from class: android.widget.RadialTimePickerView.1
            @Override // android.util.Property
            public java.lang.Float get(android.widget.RadialTimePickerView radialTimePickerView) {
                return java.lang.Float.valueOf(radialTimePickerView.mHoursToMinutes);
            }

            @Override // android.util.FloatProperty
            public void setValue(android.widget.RadialTimePickerView radialTimePickerView, float f) {
                radialTimePickerView.mHoursToMinutes = f;
                radialTimePickerView.invalidate();
            }
        };
        this.mHours12Texts = new java.lang.String[12];
        this.mOuterHours24Texts = new java.lang.String[12];
        this.mInnerHours24Texts = new java.lang.String[12];
        this.mMinutesTexts = new java.lang.String[12];
        this.mPaint = new android.graphics.Paint[2];
        this.mPaintCenter = new android.graphics.Paint();
        this.mPaintSelector = new android.graphics.Paint[3];
        this.mPaintBackground = new android.graphics.Paint();
        this.mTextColor = new android.content.res.ColorStateList[3];
        this.mTextSize = new int[3];
        this.mTextInset = new int[3];
        this.mOuterTextX = (float[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Float.TYPE, 2, 12);
        this.mOuterTextY = (float[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Float.TYPE, 2, 12);
        this.mInnerTextX = new float[12];
        this.mInnerTextY = new float[12];
        this.mSelectionDegrees = new int[2];
        this.mSelectorPath = new android.graphics.Path();
        this.mInputEnabled = true;
        this.mChangedDuringTouch = false;
        applyAttributes(attributeSet, i, i2);
        android.util.TypedValue typedValue = new android.util.TypedValue();
        context.getTheme().resolveAttribute(16842803, typedValue, true);
        this.mDisabledAlpha = typedValue.getFloat();
        this.mTypeface = android.graphics.Typeface.create(android.graphics.Typeface.DEFAULT_FAMILY, 0);
        this.mPaint[0] = new android.graphics.Paint();
        this.mPaint[0].setAntiAlias(true);
        this.mPaint[0].setTextAlign(android.graphics.Paint.Align.CENTER);
        this.mPaint[1] = new android.graphics.Paint();
        this.mPaint[1].setAntiAlias(true);
        this.mPaint[1].setTextAlign(android.graphics.Paint.Align.CENTER);
        this.mPaintCenter.setAntiAlias(true);
        this.mPaintSelector[0] = new android.graphics.Paint();
        this.mPaintSelector[0].setAntiAlias(true);
        this.mPaintSelector[1] = new android.graphics.Paint();
        this.mPaintSelector[1].setAntiAlias(true);
        this.mPaintSelector[2] = new android.graphics.Paint();
        this.mPaintSelector[2].setAntiAlias(true);
        this.mPaintSelector[2].setStrokeWidth(2.0f);
        this.mPaintBackground.setAntiAlias(true);
        android.content.res.Resources resources = getResources();
        this.mSelectorRadius = resources.getDimensionPixelSize(com.android.internal.R.dimen.timepicker_selector_radius);
        this.mSelectorStroke = resources.getDimensionPixelSize(com.android.internal.R.dimen.timepicker_selector_stroke);
        this.mSelectorDotRadius = resources.getDimensionPixelSize(com.android.internal.R.dimen.timepicker_selector_dot_radius);
        this.mCenterDotRadius = resources.getDimensionPixelSize(com.android.internal.R.dimen.timepicker_center_dot_radius);
        this.mTextSize[0] = resources.getDimensionPixelSize(com.android.internal.R.dimen.timepicker_text_size_normal);
        this.mTextSize[1] = resources.getDimensionPixelSize(com.android.internal.R.dimen.timepicker_text_size_normal);
        this.mTextSize[2] = resources.getDimensionPixelSize(com.android.internal.R.dimen.timepicker_text_size_inner);
        this.mTextInset[0] = resources.getDimensionPixelSize(com.android.internal.R.dimen.timepicker_text_inset_normal);
        this.mTextInset[1] = resources.getDimensionPixelSize(com.android.internal.R.dimen.timepicker_text_inset_normal);
        this.mTextInset[2] = resources.getDimensionPixelSize(com.android.internal.R.dimen.timepicker_text_inset_inner);
        this.mShowHours = true;
        this.mHoursToMinutes = 0.0f;
        this.mIs24HourMode = false;
        this.mAmOrPm = 0;
        this.mTouchHelper = new android.widget.RadialTimePickerView.RadialPickerTouchHelper();
        setAccessibilityDelegate(this.mTouchHelper);
        if (getImportantForAccessibility() == 0) {
            setImportantForAccessibility(1);
        }
        initHoursAndMinutesText();
        initData();
        java.util.Calendar calendar = java.util.Calendar.getInstance(java.util.Locale.getDefault());
        int i3 = calendar.get(11);
        int i4 = calendar.get(12);
        setCurrentHourInternal(i3, false, false);
        setCurrentMinuteInternal(i4, false);
        setHapticFeedbackEnabled(true);
    }

    void applyAttributes(android.util.AttributeSet attributeSet, int i, int i2) {
        android.content.Context context = getContext();
        android.content.res.TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.TimePicker, i, i2);
        saveAttributeDataForStyleable(context, com.android.internal.R.styleable.TimePicker, attributeSet, obtainStyledAttributes, i, i2);
        android.content.res.ColorStateList colorStateList = obtainStyledAttributes.getColorStateList(3);
        android.content.res.ColorStateList colorStateList2 = obtainStyledAttributes.getColorStateList(9);
        android.content.res.ColorStateList[] colorStateListArr = this.mTextColor;
        if (colorStateList == null) {
            colorStateList = android.content.res.ColorStateList.valueOf(-65281);
        }
        colorStateListArr[0] = colorStateList;
        android.content.res.ColorStateList[] colorStateListArr2 = this.mTextColor;
        if (colorStateList2 == null) {
            colorStateList2 = android.content.res.ColorStateList.valueOf(-65281);
        }
        colorStateListArr2[2] = colorStateList2;
        this.mTextColor[1] = this.mTextColor[0];
        android.content.res.ColorStateList colorStateList3 = obtainStyledAttributes.getColorStateList(5);
        int colorForState = colorStateList3 != null ? colorStateList3.getColorForState(android.util.StateSet.get(40), 0) : -65281;
        this.mPaintCenter.setColor(colorForState);
        int[] iArr = android.util.StateSet.get(40);
        this.mSelectorColor = colorForState;
        this.mSelectorDotColor = this.mTextColor[0].getColorForState(iArr, 0);
        this.mPaintBackground.setColor(obtainStyledAttributes.getColor(4, context.getColor(com.android.internal.R.color.timepicker_default_numbers_background_color_material)));
        obtainStyledAttributes.recycle();
    }

    public void initialize(int i, int i2, boolean z) {
        if (this.mIs24HourMode != z) {
            this.mIs24HourMode = z;
            initData();
        }
        setCurrentHourInternal(i, false, false);
        setCurrentMinuteInternal(i2, false);
    }

    public void setCurrentItemShowing(int i, boolean z) {
        switch (i) {
            case 0:
                showHours(z);
                break;
            case 1:
                showMinutes(z);
                break;
            default:
                android.util.Log.e(TAG, "ClockView does not support showing item " + i);
                break;
        }
    }

    public int getCurrentItemShowing() {
        return !this.mShowHours ? 1 : 0;
    }

    public void setOnValueSelectedListener(android.widget.RadialTimePickerView.OnValueSelectedListener onValueSelectedListener) {
        this.mListener = onValueSelectedListener;
    }

    public void setCurrentHour(int i) {
        setCurrentHourInternal(i, true, false);
    }

    private void setCurrentHourInternal(int i, boolean z, boolean z2) {
        this.mSelectionDegrees[0] = (i % 12) * 30;
        int i2 = (i == 0 || i % 24 < 12) ? 0 : 1;
        boolean innerCircleForHour = getInnerCircleForHour(i);
        if (this.mAmOrPm != i2 || this.mIsOnInnerCircle != innerCircleForHour) {
            this.mAmOrPm = i2;
            this.mIsOnInnerCircle = innerCircleForHour;
            initData();
            this.mTouchHelper.invalidateRoot();
        }
        invalidate();
        if (z && this.mListener != null) {
            this.mListener.onValueSelected(0, i, z2);
        }
    }

    public int getCurrentHour() {
        return getHourForDegrees(this.mSelectionDegrees[0], this.mIsOnInnerCircle);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getHourForDegrees(int i, boolean z) {
        int i2 = (i / 30) % 12;
        if (this.mIs24HourMode) {
            if (!z && i2 == 0) {
                return 12;
            }
            if (z && i2 != 0) {
                return i2 + 12;
            }
        } else if (this.mAmOrPm == 1) {
            return i2 + 12;
        }
        return i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getDegreesForHour(int i) {
        if (this.mIs24HourMode) {
            if (i >= 12) {
                i -= 12;
            }
        } else if (i == 12) {
            i = 0;
        }
        return i * 30;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean getInnerCircleForHour(int i) {
        return this.mIs24HourMode && (i == 0 || i > 12);
    }

    public void setCurrentMinute(int i) {
        setCurrentMinuteInternal(i, true);
    }

    private void setCurrentMinuteInternal(int i, boolean z) {
        this.mSelectionDegrees[1] = (i % 60) * 6;
        invalidate();
        if (z && this.mListener != null) {
            this.mListener.onValueSelected(1, i, false);
        }
    }

    public int getCurrentMinute() {
        return getMinuteForDegrees(this.mSelectionDegrees[1]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getMinuteForDegrees(int i) {
        return i / 6;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getDegreesForMinute(int i) {
        return i * 6;
    }

    public boolean setAmOrPm(int i) {
        if (this.mAmOrPm == i || this.mIs24HourMode) {
            return false;
        }
        this.mAmOrPm = i;
        invalidate();
        this.mTouchHelper.invalidateRoot();
        return true;
    }

    public int getAmOrPm() {
        return this.mAmOrPm;
    }

    public void showHours(boolean z) {
        showPicker(true, z);
    }

    public void showMinutes(boolean z) {
        showPicker(false, z);
    }

    private void initHoursAndMinutesText() {
        for (int i = 0; i < 12; i++) {
            this.mHours12Texts[i] = java.lang.String.format("%d", java.lang.Integer.valueOf(HOURS_NUMBERS[i]));
            this.mInnerHours24Texts[i] = java.lang.String.format("%02d", java.lang.Integer.valueOf(HOURS_NUMBERS_24[i]));
            this.mOuterHours24Texts[i] = java.lang.String.format("%d", java.lang.Integer.valueOf(HOURS_NUMBERS[i]));
            this.mMinutesTexts[i] = java.lang.String.format("%02d", java.lang.Integer.valueOf(MINUTES_NUMBERS[i]));
        }
    }

    private void initData() {
        if (this.mIs24HourMode) {
            this.mOuterTextHours = this.mOuterHours24Texts;
            this.mInnerTextHours = this.mInnerHours24Texts;
        } else {
            this.mOuterTextHours = this.mHours12Texts;
            this.mInnerTextHours = this.mHours12Texts;
        }
        this.mMinutesText = this.mMinutesTexts;
    }

    @Override // android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (!z) {
            return;
        }
        this.mXCenter = getWidth() / 2;
        this.mYCenter = getHeight() / 2;
        this.mCircleRadius = java.lang.Math.min(this.mXCenter, this.mYCenter);
        this.mMinDistForInnerNumber = (this.mCircleRadius - this.mTextInset[2]) - this.mSelectorRadius;
        this.mMaxDistForOuterNumber = (this.mCircleRadius - this.mTextInset[0]) + this.mSelectorRadius;
        this.mHalfwayDist = this.mCircleRadius - ((this.mTextInset[0] + this.mTextInset[2]) / 2);
        calculatePositionsHours();
        calculatePositionsMinutes();
        this.mTouchHelper.invalidateRoot();
    }

    @Override // android.view.View
    public void onDraw(android.graphics.Canvas canvas) {
        float f = this.mInputEnabled ? 1.0f : this.mDisabledAlpha;
        drawCircleBackground(canvas);
        android.graphics.Path path = this.mSelectorPath;
        drawSelector(canvas, path);
        drawHours(canvas, path, f);
        drawMinutes(canvas, path, f);
        drawCenter(canvas, f);
    }

    private void showPicker(boolean z, boolean z2) {
        if (this.mShowHours == z) {
            return;
        }
        this.mShowHours = z;
        if (z2) {
            animatePicker(z, 500L);
        } else {
            if (this.mHoursToMinutesAnimator != null && this.mHoursToMinutesAnimator.isStarted()) {
                this.mHoursToMinutesAnimator.cancel();
                this.mHoursToMinutesAnimator = null;
            }
            this.mHoursToMinutes = z ? 0.0f : 1.0f;
        }
        initData();
        invalidate();
        this.mTouchHelper.invalidateRoot();
    }

    private void animatePicker(boolean z, long j) {
        float f = z ? 0.0f : 1.0f;
        if (this.mHoursToMinutes == f) {
            if (this.mHoursToMinutesAnimator != null && this.mHoursToMinutesAnimator.isStarted()) {
                this.mHoursToMinutesAnimator.cancel();
                this.mHoursToMinutesAnimator = null;
                return;
            }
            return;
        }
        this.mHoursToMinutesAnimator = android.animation.ObjectAnimator.ofFloat(this, this.HOURS_TO_MINUTES, f);
        this.mHoursToMinutesAnimator.setAutoCancel(true);
        this.mHoursToMinutesAnimator.setDuration(j);
        this.mHoursToMinutesAnimator.start();
    }

    private void drawCircleBackground(android.graphics.Canvas canvas) {
        canvas.drawCircle(this.mXCenter, this.mYCenter, this.mCircleRadius, this.mPaintBackground);
    }

    private void drawHours(android.graphics.Canvas canvas, android.graphics.Path path, float f) {
        int i = (int) (((1.0f - this.mHoursToMinutes) * 255.0f * f) + 0.5f);
        if (i > 0) {
            canvas.save(2);
            canvas.clipPath(path, android.graphics.Region.Op.DIFFERENCE);
            drawHoursClipped(canvas, i, false);
            canvas.restore();
            canvas.save(2);
            canvas.clipPath(path, android.graphics.Region.Op.INTERSECT);
            drawHoursClipped(canvas, i, true);
            canvas.restore();
        }
    }

    private void drawHoursClipped(android.graphics.Canvas canvas, int i, boolean z) {
        drawTextElements(canvas, this.mTextSize[0], this.mTypeface, this.mTextColor[0], this.mOuterTextHours, this.mOuterTextX[0], this.mOuterTextY[0], this.mPaint[0], i, z && !this.mIsOnInnerCircle, this.mSelectionDegrees[0], z);
        if (this.mIs24HourMode && this.mInnerTextHours != null) {
            drawTextElements(canvas, this.mTextSize[2], this.mTypeface, this.mTextColor[2], this.mInnerTextHours, this.mInnerTextX, this.mInnerTextY, this.mPaint[0], i, z && this.mIsOnInnerCircle, this.mSelectionDegrees[0], z);
        }
    }

    private void drawMinutes(android.graphics.Canvas canvas, android.graphics.Path path, float f) {
        int i = (int) ((this.mHoursToMinutes * 255.0f * f) + 0.5f);
        if (i > 0) {
            canvas.save(2);
            canvas.clipPath(path, android.graphics.Region.Op.DIFFERENCE);
            drawMinutesClipped(canvas, i, false);
            canvas.restore();
            canvas.save(2);
            canvas.clipPath(path, android.graphics.Region.Op.INTERSECT);
            drawMinutesClipped(canvas, i, true);
            canvas.restore();
        }
    }

    private void drawMinutesClipped(android.graphics.Canvas canvas, int i, boolean z) {
        drawTextElements(canvas, this.mTextSize[1], this.mTypeface, this.mTextColor[1], this.mMinutesText, this.mOuterTextX[1], this.mOuterTextY[1], this.mPaint[1], i, z, this.mSelectionDegrees[1], z);
    }

    private void drawCenter(android.graphics.Canvas canvas, float f) {
        this.mPaintCenter.setAlpha((int) ((f * 255.0f) + 0.5f));
        canvas.drawCircle(this.mXCenter, this.mYCenter, this.mCenterDotRadius, this.mPaintCenter);
    }

    private int getMultipliedAlpha(int i, int i2) {
        return (int) ((android.graphics.Color.alpha(i) * (i2 / 255.0d)) + 0.5d);
    }

    private void drawSelector(android.graphics.Canvas canvas, android.graphics.Path path) {
        int i = this.mIsOnInnerCircle ? 2 : 0;
        int i2 = this.mTextInset[i];
        int i3 = i % 2;
        int i4 = this.mSelectionDegrees[i3];
        float f = this.mSelectionDegrees[i3] % 30 != 0 ? 1.0f : 0.0f;
        int i5 = this.mTextInset[1];
        int i6 = this.mSelectionDegrees[1];
        float f2 = this.mSelectionDegrees[1] % 30 == 0 ? 0.0f : 1.0f;
        int i7 = this.mSelectorRadius;
        float lerp = this.mCircleRadius - android.util.MathUtils.lerp(i2, i5, this.mHoursToMinutes);
        double radians = java.lang.Math.toRadians(android.util.MathUtils.lerpDeg(i4, i6, this.mHoursToMinutes));
        float sin = this.mXCenter + (((float) java.lang.Math.sin(radians)) * lerp);
        float cos = this.mYCenter - (((float) java.lang.Math.cos(radians)) * lerp);
        android.graphics.Paint paint = this.mPaintSelector[0];
        paint.setColor(this.mSelectorColor);
        float f3 = i7;
        canvas.drawCircle(sin, cos, f3, paint);
        if (path != null) {
            path.reset();
            path.addCircle(sin, cos, f3, android.graphics.Path.Direction.CCW);
        }
        float lerp2 = android.util.MathUtils.lerp(f, f2, this.mHoursToMinutes);
        if (lerp2 > 0.0f) {
            android.graphics.Paint paint2 = this.mPaintSelector[1];
            paint2.setColor(this.mSelectorDotColor);
            canvas.drawCircle(sin, cos, this.mSelectorDotRadius * lerp2, paint2);
        }
        double sin2 = java.lang.Math.sin(radians);
        double cos2 = java.lang.Math.cos(radians);
        double d = lerp - f3;
        float f4 = this.mXCenter + ((int) (this.mCenterDotRadius * sin2)) + ((int) (sin2 * d));
        float f5 = (this.mYCenter - ((int) (this.mCenterDotRadius * cos2))) - ((int) (d * cos2));
        android.graphics.Paint paint3 = this.mPaintSelector[2];
        paint3.setColor(this.mSelectorColor);
        paint3.setStrokeWidth(this.mSelectorStroke);
        canvas.drawLine(this.mXCenter, this.mYCenter, f4, f5, paint3);
    }

    private void calculatePositionsHours() {
        calculatePositions(this.mPaint[0], this.mCircleRadius - this.mTextInset[0], this.mXCenter, this.mYCenter, this.mTextSize[0], this.mOuterTextX[0], this.mOuterTextY[0]);
        if (this.mIs24HourMode) {
            calculatePositions(this.mPaint[0], this.mCircleRadius - this.mTextInset[2], this.mXCenter, this.mYCenter, this.mTextSize[2], this.mInnerTextX, this.mInnerTextY);
        }
    }

    private void calculatePositionsMinutes() {
        calculatePositions(this.mPaint[1], this.mCircleRadius - this.mTextInset[1], this.mXCenter, this.mYCenter, this.mTextSize[1], this.mOuterTextX[1], this.mOuterTextY[1]);
    }

    private static void calculatePositions(android.graphics.Paint paint, float f, float f2, float f3, float f4, float[] fArr, float[] fArr2) {
        paint.setTextSize(f4);
        float descent = f3 - ((paint.descent() + paint.ascent()) / 2.0f);
        for (int i = 0; i < 12; i++) {
            fArr[i] = f2 - (COS_30[i] * f);
            fArr2[i] = descent - (SIN_30[i] * f);
        }
    }

    private void drawTextElements(android.graphics.Canvas canvas, float f, android.graphics.Typeface typeface, android.content.res.ColorStateList colorStateList, java.lang.String[] strArr, float[] fArr, float[] fArr2, android.graphics.Paint paint, int i, boolean z, int i2, boolean z2) {
        paint.setTextSize(f);
        paint.setTypeface(typeface);
        float f2 = i2 / 30.0f;
        int i3 = (int) f2;
        int ceil = ((int) java.lang.Math.ceil(f2)) % 12;
        int i4 = 0;
        while (i4 < 12) {
            boolean z3 = i3 == i4 || ceil == i4;
            if (!z2 || z3) {
                int colorForState = colorStateList.getColorForState(android.util.StateSet.get(((z && z3) ? 32 : 0) | 8), 0);
                paint.setColor(colorForState);
                paint.setAlpha(getMultipliedAlpha(colorForState, i));
                canvas.drawText(strArr[i4], fArr[i4], fArr2[i4], paint);
            }
            i4++;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getDegreesFromXY(float f, float f2, boolean z) {
        int i;
        int i2;
        if (this.mIs24HourMode && this.mShowHours) {
            i = this.mMinDistForInnerNumber;
            i2 = this.mMaxDistForOuterNumber;
        } else {
            int i3 = this.mCircleRadius - this.mTextInset[!this.mShowHours ? 1 : 0];
            i = i3 - this.mSelectorRadius;
            i2 = i3 + this.mSelectorRadius;
        }
        double d = f - this.mXCenter;
        double d2 = f2 - this.mYCenter;
        double sqrt = java.lang.Math.sqrt((d * d) + (d2 * d2));
        if (sqrt < i) {
            return -1;
        }
        if (z && sqrt > i2) {
            return -1;
        }
        int degrees = (int) (java.lang.Math.toDegrees(java.lang.Math.atan2(d2, d) + 1.5707963267948966d) + 0.5d);
        if (degrees < 0) {
            return degrees + 360;
        }
        return degrees;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean getInnerCircleFromXY(float f, float f2) {
        if (!this.mIs24HourMode || !this.mShowHours) {
            return false;
        }
        double d = f - this.mXCenter;
        double d2 = f2 - this.mYCenter;
        return java.lang.Math.sqrt((d * d) + (d2 * d2)) <= ((double) this.mHalfwayDist);
    }

    @Override // android.view.View
    public boolean onTouchEvent(android.view.MotionEvent motionEvent) {
        boolean z;
        if (!this.mInputEnabled) {
            return true;
        }
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 2 || actionMasked == 1 || actionMasked == 0) {
            boolean z2 = false;
            if (actionMasked == 0) {
                this.mChangedDuringTouch = false;
            } else if (actionMasked == 1) {
                if (this.mChangedDuringTouch) {
                    z = true;
                } else {
                    z = true;
                    z2 = true;
                }
                this.mChangedDuringTouch = handleTouchInput(motionEvent.getX(), motionEvent.getY(), z2, z) | this.mChangedDuringTouch;
            }
            z = false;
            this.mChangedDuringTouch = handleTouchInput(motionEvent.getX(), motionEvent.getY(), z2, z) | this.mChangedDuringTouch;
        }
        return true;
    }

    private boolean handleTouchInput(float f, float f2, boolean z, boolean z2) {
        boolean z3;
        int currentMinute;
        int i;
        boolean innerCircleFromXY = getInnerCircleFromXY(f, f2);
        int degreesFromXY = getDegreesFromXY(f, f2, false);
        if (degreesFromXY == -1) {
            return false;
        }
        animatePicker(this.mShowHours, 60L);
        if (this.mShowHours) {
            int snapOnly30s = snapOnly30s(degreesFromXY, 0) % 360;
            z3 = (this.mIsOnInnerCircle == innerCircleFromXY && this.mSelectionDegrees[0] == snapOnly30s) ? false : true;
            this.mIsOnInnerCircle = innerCircleFromXY;
            this.mSelectionDegrees[0] = snapOnly30s;
            currentMinute = getCurrentHour();
            i = 0;
        } else {
            int snapPrefer30s = snapPrefer30s(degreesFromXY) % 360;
            z3 = this.mSelectionDegrees[1] != snapPrefer30s;
            this.mSelectionDegrees[1] = snapPrefer30s;
            currentMinute = getCurrentMinute();
            i = 1;
        }
        if (!z3 && !z && !z2) {
            return false;
        }
        if (this.mListener != null) {
            this.mListener.onValueSelected(i, currentMinute, z2);
        }
        if (z3 || z) {
            performHapticFeedback(4);
            invalidate();
        }
        return true;
    }

    @Override // android.view.View
    public boolean dispatchHoverEvent(android.view.MotionEvent motionEvent) {
        if (this.mTouchHelper.dispatchHoverEvent(motionEvent)) {
            return true;
        }
        return super.dispatchHoverEvent(motionEvent);
    }

    public void setInputEnabled(boolean z) {
        this.mInputEnabled = z;
        invalidate();
    }

    @Override // android.view.View
    public android.view.PointerIcon onResolvePointerIcon(android.view.MotionEvent motionEvent, int i) {
        int i2;
        if (!isEnabled()) {
            return null;
        }
        if (motionEvent.isFromSource(8194) && getDegreesFromXY(motionEvent.getX(), motionEvent.getY(), false) != -1) {
            if (android.view.flags.Flags.enableArrowIconOnHoverWhenClickable()) {
                i2 = 1000;
            } else {
                i2 = 1002;
            }
            return android.view.PointerIcon.getSystemIcon(getContext(), i2);
        }
        return super.onResolvePointerIcon(motionEvent, i);
    }

    private class RadialPickerTouchHelper extends com.android.internal.widget.ExploreByTouchHelper {
        private final int MASK_TYPE;
        private final int MASK_VALUE;
        private final int MINUTE_INCREMENT;
        private final int SHIFT_TYPE;
        private final int SHIFT_VALUE;
        private final int TYPE_HOUR;
        private final int TYPE_MINUTE;
        private final android.graphics.Rect mTempRect;

        public RadialPickerTouchHelper() {
            super(android.widget.RadialTimePickerView.this);
            this.mTempRect = new android.graphics.Rect();
            this.TYPE_HOUR = 1;
            this.TYPE_MINUTE = 2;
            this.SHIFT_TYPE = 0;
            this.MASK_TYPE = 15;
            this.SHIFT_VALUE = 8;
            this.MASK_VALUE = 255;
            this.MINUTE_INCREMENT = 5;
        }

        @Override // android.view.View.AccessibilityDelegate
        public void onInitializeAccessibilityNodeInfo(android.view.View view, android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
            accessibilityNodeInfo.addAction(android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_FORWARD);
            accessibilityNodeInfo.addAction(android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_BACKWARD);
        }

        @Override // android.view.View.AccessibilityDelegate
        public boolean performAccessibilityAction(android.view.View view, int i, android.os.Bundle bundle) {
            if (super.performAccessibilityAction(view, i, bundle)) {
                return true;
            }
            switch (i) {
                case 4096:
                    adjustPicker(1);
                    break;
                case 8192:
                    adjustPicker(-1);
                    break;
            }
            return true;
        }

        private void adjustPicker(int i) {
            int i2;
            int currentMinute;
            int i3;
            int i4 = 0;
            if (android.widget.RadialTimePickerView.this.mShowHours) {
                currentMinute = android.widget.RadialTimePickerView.this.getCurrentHour();
                i2 = 1;
                if (android.widget.RadialTimePickerView.this.mIs24HourMode) {
                    i3 = 23;
                } else {
                    currentMinute = hour24To12(currentMinute);
                    i3 = 12;
                    i4 = 1;
                }
            } else {
                i2 = 5;
                currentMinute = android.widget.RadialTimePickerView.this.getCurrentMinute() / 5;
                i3 = 55;
            }
            int constrain = android.util.MathUtils.constrain((currentMinute + i) * i2, i4, i3);
            if (android.widget.RadialTimePickerView.this.mShowHours) {
                android.widget.RadialTimePickerView.this.setCurrentHour(constrain);
            } else {
                android.widget.RadialTimePickerView.this.setCurrentMinute(constrain);
            }
        }

        @Override // com.android.internal.widget.ExploreByTouchHelper
        protected int getVirtualViewAt(float f, float f2) {
            int degreesFromXY = android.widget.RadialTimePickerView.this.getDegreesFromXY(f, f2, true);
            if (degreesFromXY != -1) {
                int snapOnly30s = android.widget.RadialTimePickerView.snapOnly30s(degreesFromXY, 0) % 360;
                if (android.widget.RadialTimePickerView.this.mShowHours) {
                    int hourForDegrees = android.widget.RadialTimePickerView.this.getHourForDegrees(snapOnly30s, android.widget.RadialTimePickerView.this.getInnerCircleFromXY(f, f2));
                    if (!android.widget.RadialTimePickerView.this.mIs24HourMode) {
                        hourForDegrees = hour24To12(hourForDegrees);
                    }
                    return makeId(1, hourForDegrees);
                }
                int currentMinute = android.widget.RadialTimePickerView.this.getCurrentMinute();
                int minuteForDegrees = android.widget.RadialTimePickerView.this.getMinuteForDegrees(degreesFromXY);
                int minuteForDegrees2 = android.widget.RadialTimePickerView.this.getMinuteForDegrees(snapOnly30s);
                if (getCircularDiff(currentMinute, minuteForDegrees, 60) >= getCircularDiff(minuteForDegrees2, minuteForDegrees, 60)) {
                    currentMinute = minuteForDegrees2;
                }
                return makeId(2, currentMinute);
            }
            return Integer.MIN_VALUE;
        }

        private int getCircularDiff(int i, int i2, int i3) {
            int abs = java.lang.Math.abs(i - i2);
            return abs > i3 / 2 ? i3 - abs : abs;
        }

        @Override // com.android.internal.widget.ExploreByTouchHelper
        protected void getVisibleVirtualViews(android.util.IntArray intArray) {
            if (android.widget.RadialTimePickerView.this.mShowHours) {
                int i = android.widget.RadialTimePickerView.this.mIs24HourMode ? 23 : 12;
                for (int i2 = !android.widget.RadialTimePickerView.this.mIs24HourMode ? 1 : 0; i2 <= i; i2++) {
                    intArray.add(makeId(1, i2));
                }
                return;
            }
            int currentMinute = android.widget.RadialTimePickerView.this.getCurrentMinute();
            for (int i3 = 0; i3 < 60; i3 += 5) {
                intArray.add(makeId(2, i3));
                if (currentMinute > i3 && currentMinute < i3 + 5) {
                    intArray.add(makeId(2, currentMinute));
                }
            }
        }

        @Override // com.android.internal.widget.ExploreByTouchHelper
        protected void onPopulateEventForVirtualView(int i, android.view.accessibility.AccessibilityEvent accessibilityEvent) {
            accessibilityEvent.setClassName(getClass().getName());
            accessibilityEvent.setContentDescription(getVirtualViewDescription(getTypeFromId(i), getValueFromId(i)));
        }

        @Override // com.android.internal.widget.ExploreByTouchHelper
        protected void onPopulateNodeForVirtualView(int i, android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
            accessibilityNodeInfo.setClassName(getClass().getName());
            accessibilityNodeInfo.addAction(android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK);
            int typeFromId = getTypeFromId(i);
            int valueFromId = getValueFromId(i);
            accessibilityNodeInfo.setContentDescription(getVirtualViewDescription(typeFromId, valueFromId));
            getBoundsForVirtualView(i, this.mTempRect);
            accessibilityNodeInfo.setBoundsInParent(this.mTempRect);
            accessibilityNodeInfo.setSelected(isVirtualViewSelected(typeFromId, valueFromId));
            int virtualViewIdAfter = getVirtualViewIdAfter(typeFromId, valueFromId);
            if (virtualViewIdAfter != Integer.MIN_VALUE) {
                accessibilityNodeInfo.setTraversalBefore(android.widget.RadialTimePickerView.this, virtualViewIdAfter);
            }
        }

        private int getVirtualViewIdAfter(int i, int i2) {
            if (i == 1) {
                int i3 = i2 + 1;
                if (i3 <= (android.widget.RadialTimePickerView.this.mIs24HourMode ? 23 : 12)) {
                    return makeId(i, i3);
                }
                return Integer.MIN_VALUE;
            }
            if (i == 2) {
                int currentMinute = android.widget.RadialTimePickerView.this.getCurrentMinute();
                int i4 = (i2 - (i2 % 5)) + 5;
                if (i2 < currentMinute && i4 > currentMinute) {
                    return makeId(i, currentMinute);
                }
                if (i4 < 60) {
                    return makeId(i, i4);
                }
                return Integer.MIN_VALUE;
            }
            return Integer.MIN_VALUE;
        }

        @Override // com.android.internal.widget.ExploreByTouchHelper
        protected boolean onPerformActionForVirtualView(int i, int i2, android.os.Bundle bundle) {
            if (i2 == 16) {
                int typeFromId = getTypeFromId(i);
                int valueFromId = getValueFromId(i);
                if (typeFromId == 1) {
                    if (!android.widget.RadialTimePickerView.this.mIs24HourMode) {
                        valueFromId = hour12To24(valueFromId, android.widget.RadialTimePickerView.this.mAmOrPm);
                    }
                    android.widget.RadialTimePickerView.this.setCurrentHour(valueFromId);
                    return true;
                }
                if (typeFromId == 2) {
                    android.widget.RadialTimePickerView.this.setCurrentMinute(valueFromId);
                    return true;
                }
                return false;
            }
            return false;
        }

        private int hour12To24(int i, int i2) {
            if (i == 12) {
                if (i2 == 0) {
                    return 0;
                }
                return i;
            }
            if (i2 == 1) {
                return i + 12;
            }
            return i;
        }

        private int hour24To12(int i) {
            if (i == 0) {
                return 12;
            }
            if (i > 12) {
                return i - 12;
            }
            return i;
        }

        private void getBoundsForVirtualView(int i, android.graphics.Rect rect) {
            float f;
            float f2;
            float f3;
            int typeFromId = getTypeFromId(i);
            int valueFromId = getValueFromId(i);
            if (typeFromId != 1) {
                if (typeFromId == 2) {
                    f = android.widget.RadialTimePickerView.this.mCircleRadius - android.widget.RadialTimePickerView.this.mTextInset[1];
                    f2 = android.widget.RadialTimePickerView.this.getDegreesForMinute(valueFromId);
                    f3 = android.widget.RadialTimePickerView.this.mSelectorRadius;
                } else {
                    f = 0.0f;
                    f2 = 0.0f;
                    f3 = 0.0f;
                }
            } else {
                if (android.widget.RadialTimePickerView.this.getInnerCircleForHour(valueFromId)) {
                    f = android.widget.RadialTimePickerView.this.mCircleRadius - android.widget.RadialTimePickerView.this.mTextInset[2];
                    f3 = android.widget.RadialTimePickerView.this.mSelectorRadius;
                } else {
                    f = android.widget.RadialTimePickerView.this.mCircleRadius - android.widget.RadialTimePickerView.this.mTextInset[0];
                    f3 = android.widget.RadialTimePickerView.this.mSelectorRadius;
                }
                f2 = android.widget.RadialTimePickerView.this.getDegreesForHour(valueFromId);
            }
            double radians = java.lang.Math.toRadians(f2);
            float sin = android.widget.RadialTimePickerView.this.mXCenter + (((float) java.lang.Math.sin(radians)) * f);
            float cos = android.widget.RadialTimePickerView.this.mYCenter - (f * ((float) java.lang.Math.cos(radians)));
            rect.set((int) (sin - f3), (int) (cos - f3), (int) (sin + f3), (int) (cos + f3));
        }

        private java.lang.CharSequence getVirtualViewDescription(int i, int i2) {
            if (i == 1 || i == 2) {
                return java.lang.Integer.toString(i2);
            }
            return null;
        }

        private boolean isVirtualViewSelected(int i, int i2) {
            return i == 1 ? android.widget.RadialTimePickerView.this.getCurrentHour() == i2 : i == 2 && android.widget.RadialTimePickerView.this.getCurrentMinute() == i2;
        }

        private int makeId(int i, int i2) {
            return (i << 0) | (i2 << 8);
        }

        private int getTypeFromId(int i) {
            return (i >>> 0) & 15;
        }

        private int getValueFromId(int i) {
            return (i >>> 8) & 255;
        }
    }
}
