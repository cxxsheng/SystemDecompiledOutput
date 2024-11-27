package android.widget;

@android.widget.RemoteViews.RemoteView
@java.lang.Deprecated
/* loaded from: classes4.dex */
public class AnalogClock extends android.view.View {
    private static final java.lang.String LOG_TAG = "AnalogClock";
    private boolean mChanged;
    private java.time.Clock mClock;
    private android.widget.TextClock.ClockEventDelegate mClockEventDelegate;
    private android.graphics.drawable.Drawable mDial;
    private int mDialHeight;
    private final android.widget.AnalogClock.TintInfo mDialTintInfo;
    private int mDialWidth;
    private float mHour;
    private android.graphics.drawable.Drawable mHourHand;
    private final android.widget.AnalogClock.TintInfo mHourHandTintInfo;
    private final android.content.BroadcastReceiver mIntentReceiver;
    private android.graphics.drawable.Drawable mMinuteHand;
    private final android.widget.AnalogClock.TintInfo mMinuteHandTintInfo;
    private float mMinutes;
    private boolean mReceiverAttached;
    private android.graphics.drawable.Drawable mSecondHand;
    private final android.widget.AnalogClock.TintInfo mSecondHandTintInfo;
    private float mSeconds;
    private final int mSecondsHandFps;
    private final java.lang.Runnable mTick;
    private java.time.ZoneId mTimeZone;
    private boolean mVisible;

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<android.widget.AnalogClock> {
        private int mDialTintBlendModeId;
        private int mDialTintListId;
        private int mHourHandTintBlendModeId;
        private int mHourHandTintListId;
        private int mMinuteHandTintBlendModeId;
        private int mMinuteHandTintListId;
        private boolean mPropertiesMapped = false;
        private int mSecondHandTintBlendModeId;
        private int mSecondHandTintListId;
        private int mTimeZoneId;

        @Override // android.view.inspector.InspectionCompanion
        public void mapProperties(android.view.inspector.PropertyMapper propertyMapper) {
            this.mDialTintBlendModeId = propertyMapper.mapObject("dialTintBlendMode", 6);
            this.mDialTintListId = propertyMapper.mapObject("dialTintList", 5);
            this.mHourHandTintBlendModeId = propertyMapper.mapObject("hourHandTintBlendMode", 8);
            this.mHourHandTintListId = propertyMapper.mapObject("hourHandTintList", 7);
            this.mMinuteHandTintBlendModeId = propertyMapper.mapObject("minuteHandTintBlendMode", 10);
            this.mMinuteHandTintListId = propertyMapper.mapObject("minuteHandTintList", 9);
            this.mSecondHandTintBlendModeId = propertyMapper.mapObject("secondHandTintBlendMode", 12);
            this.mSecondHandTintListId = propertyMapper.mapObject("secondHandTintList", 11);
            this.mTimeZoneId = propertyMapper.mapObject("timeZone", 16843724);
            this.mPropertiesMapped = true;
        }

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(android.widget.AnalogClock analogClock, android.view.inspector.PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new android.view.inspector.InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readObject(this.mDialTintBlendModeId, analogClock.getDialTintBlendMode());
            propertyReader.readObject(this.mDialTintListId, analogClock.getDialTintList());
            propertyReader.readObject(this.mHourHandTintBlendModeId, analogClock.getHourHandTintBlendMode());
            propertyReader.readObject(this.mHourHandTintListId, analogClock.getHourHandTintList());
            propertyReader.readObject(this.mMinuteHandTintBlendModeId, analogClock.getMinuteHandTintBlendMode());
            propertyReader.readObject(this.mMinuteHandTintListId, analogClock.getMinuteHandTintList());
            propertyReader.readObject(this.mSecondHandTintBlendModeId, analogClock.getSecondHandTintBlendMode());
            propertyReader.readObject(this.mSecondHandTintListId, analogClock.getSecondHandTintList());
            propertyReader.readObject(this.mTimeZoneId, analogClock.getTimeZone());
        }
    }

    public AnalogClock(android.content.Context context) {
        this(context, null);
    }

    public AnalogClock(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AnalogClock(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public AnalogClock(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mHourHandTintInfo = new android.widget.AnalogClock.TintInfo();
        this.mMinuteHandTintInfo = new android.widget.AnalogClock.TintInfo();
        this.mSecondHandTintInfo = new android.widget.AnalogClock.TintInfo();
        this.mDialTintInfo = new android.widget.AnalogClock.TintInfo();
        this.mIntentReceiver = new android.content.BroadcastReceiver() { // from class: android.widget.AnalogClock.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                if (android.content.Intent.ACTION_TIMEZONE_CHANGED.equals(intent.getAction())) {
                    android.widget.AnalogClock.this.createClock();
                }
                android.widget.AnalogClock.this.mTick.run();
            }
        };
        this.mTick = new java.lang.Runnable() { // from class: android.widget.AnalogClock.2
            @Override // java.lang.Runnable
            public void run() {
                long j;
                android.widget.AnalogClock.this.removeCallbacks(this);
                if (!android.widget.AnalogClock.this.mVisible) {
                    return;
                }
                java.time.Instant now = android.widget.AnalogClock.this.now();
                java.time.ZonedDateTime atZone = now.atZone(android.widget.AnalogClock.this.mClock.getZone());
                java.time.LocalTime localTime = atZone.toLocalTime();
                if (android.widget.AnalogClock.this.mSecondHand == null || android.widget.AnalogClock.this.mSecondsHandFps <= 0) {
                    long millis = java.time.Duration.between(now, atZone.plusMinutes(1L).withSecond(0).toInstant()).toMillis();
                    if (millis > 0) {
                        j = millis;
                    } else {
                        j = java.time.Duration.ofMinutes(1L).toMillis();
                    }
                } else {
                    long millis2 = java.time.Duration.ofNanos(localTime.getNano()).toMillis();
                    double d = 1000.0d / android.widget.AnalogClock.this.mSecondsHandFps;
                    j = java.lang.Math.round(d - java.lang.Math.round(millis2 % d));
                    if (j <= 0) {
                        j = java.lang.Math.round(d);
                    }
                }
                android.widget.AnalogClock.this.postDelayed(this, j);
                android.widget.AnalogClock.this.onTimeChanged(localTime, now.toEpochMilli());
                android.widget.AnalogClock.this.invalidate();
            }
        };
        this.mClockEventDelegate = new android.widget.TextClock.ClockEventDelegate(context);
        this.mSecondsHandFps = android.app.AppGlobals.getIntCoreSetting(android.widget.WidgetFlags.KEY_ANALOG_CLOCK_SECONDS_HAND_FPS, context.getResources().getInteger(com.android.internal.R.integer.config_defaultAnalogClockSecondsHandFps));
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.AnalogClock, i, i2);
        saveAttributeDataForStyleable(context, com.android.internal.R.styleable.AnalogClock, attributeSet, obtainStyledAttributes, i, i2);
        this.mDial = obtainStyledAttributes.getDrawable(0);
        if (this.mDial == null) {
            this.mDial = context.getDrawable(com.android.internal.R.drawable.clock_dial);
        }
        android.content.res.ColorStateList colorStateList = obtainStyledAttributes.getColorStateList(5);
        if (colorStateList != null) {
            this.mDialTintInfo.mTintList = colorStateList;
            this.mDialTintInfo.mHasTintList = true;
        }
        android.graphics.BlendMode parseBlendMode = android.graphics.drawable.Drawable.parseBlendMode(obtainStyledAttributes.getInt(6, -1), null);
        if (parseBlendMode != null) {
            this.mDialTintInfo.mTintBlendMode = parseBlendMode;
            this.mDialTintInfo.mHasTintBlendMode = true;
        }
        if (this.mDialTintInfo.mHasTintList || this.mDialTintInfo.mHasTintBlendMode) {
            this.mDial = this.mDialTintInfo.apply(this.mDial);
        }
        this.mHourHand = obtainStyledAttributes.getDrawable(1);
        if (this.mHourHand == null) {
            this.mHourHand = context.getDrawable(com.android.internal.R.drawable.clock_hand_hour);
        }
        android.content.res.ColorStateList colorStateList2 = obtainStyledAttributes.getColorStateList(7);
        if (colorStateList2 != null) {
            this.mHourHandTintInfo.mTintList = colorStateList2;
            this.mHourHandTintInfo.mHasTintList = true;
        }
        android.graphics.BlendMode parseBlendMode2 = android.graphics.drawable.Drawable.parseBlendMode(obtainStyledAttributes.getInt(8, -1), null);
        if (parseBlendMode2 != null) {
            this.mHourHandTintInfo.mTintBlendMode = parseBlendMode2;
            this.mHourHandTintInfo.mHasTintBlendMode = true;
        }
        if (this.mHourHandTintInfo.mHasTintList || this.mHourHandTintInfo.mHasTintBlendMode) {
            this.mHourHand = this.mHourHandTintInfo.apply(this.mHourHand);
        }
        this.mMinuteHand = obtainStyledAttributes.getDrawable(2);
        if (this.mMinuteHand == null) {
            this.mMinuteHand = context.getDrawable(com.android.internal.R.drawable.clock_hand_minute);
        }
        android.content.res.ColorStateList colorStateList3 = obtainStyledAttributes.getColorStateList(9);
        if (colorStateList3 != null) {
            this.mMinuteHandTintInfo.mTintList = colorStateList3;
            this.mMinuteHandTintInfo.mHasTintList = true;
        }
        android.graphics.BlendMode parseBlendMode3 = android.graphics.drawable.Drawable.parseBlendMode(obtainStyledAttributes.getInt(10, -1), null);
        if (parseBlendMode3 != null) {
            this.mMinuteHandTintInfo.mTintBlendMode = parseBlendMode3;
            this.mMinuteHandTintInfo.mHasTintBlendMode = true;
        }
        if (this.mMinuteHandTintInfo.mHasTintList || this.mMinuteHandTintInfo.mHasTintBlendMode) {
            this.mMinuteHand = this.mMinuteHandTintInfo.apply(this.mMinuteHand);
        }
        this.mSecondHand = obtainStyledAttributes.getDrawable(4);
        android.content.res.ColorStateList colorStateList4 = obtainStyledAttributes.getColorStateList(11);
        if (colorStateList4 != null) {
            this.mSecondHandTintInfo.mTintList = colorStateList4;
            this.mSecondHandTintInfo.mHasTintList = true;
        }
        android.graphics.BlendMode parseBlendMode4 = android.graphics.drawable.Drawable.parseBlendMode(obtainStyledAttributes.getInt(12, -1), null);
        if (parseBlendMode4 != null) {
            this.mSecondHandTintInfo.mTintBlendMode = parseBlendMode4;
            this.mSecondHandTintInfo.mHasTintBlendMode = true;
        }
        if (this.mSecondHandTintInfo.mHasTintList || this.mSecondHandTintInfo.mHasTintBlendMode) {
            this.mSecondHand = this.mSecondHandTintInfo.apply(this.mSecondHand);
        }
        this.mTimeZone = toZoneId(obtainStyledAttributes.getString(3));
        createClock();
        obtainStyledAttributes.recycle();
        this.mDialWidth = this.mDial.getIntrinsicWidth();
        this.mDialHeight = this.mDial.getIntrinsicHeight();
    }

    @android.view.RemotableViewMethod
    public void setDial(android.graphics.drawable.Icon icon) {
        this.mDial = icon.loadDrawable(getContext());
        this.mDialWidth = this.mDial.getIntrinsicWidth();
        this.mDialHeight = this.mDial.getIntrinsicHeight();
        if (this.mDialTintInfo.mHasTintList || this.mDialTintInfo.mHasTintBlendMode) {
            this.mDial = this.mDialTintInfo.apply(this.mDial);
        }
        this.mChanged = true;
        invalidate();
    }

    @android.view.RemotableViewMethod
    public void setDialTintList(android.content.res.ColorStateList colorStateList) {
        this.mDialTintInfo.mTintList = colorStateList;
        this.mDialTintInfo.mHasTintList = true;
        this.mDial = this.mDialTintInfo.apply(this.mDial);
    }

    public android.content.res.ColorStateList getDialTintList() {
        return this.mDialTintInfo.mTintList;
    }

    @android.view.RemotableViewMethod
    public void setDialTintBlendMode(android.graphics.BlendMode blendMode) {
        this.mDialTintInfo.mTintBlendMode = blendMode;
        this.mDialTintInfo.mHasTintBlendMode = true;
        this.mDial = this.mDialTintInfo.apply(this.mDial);
    }

    public android.graphics.BlendMode getDialTintBlendMode() {
        return this.mDialTintInfo.mTintBlendMode;
    }

    @android.view.RemotableViewMethod
    public void setHourHand(android.graphics.drawable.Icon icon) {
        this.mHourHand = icon.loadDrawable(getContext());
        if (this.mHourHandTintInfo.mHasTintList || this.mHourHandTintInfo.mHasTintBlendMode) {
            this.mHourHand = this.mHourHandTintInfo.apply(this.mHourHand);
        }
        this.mChanged = true;
        invalidate();
    }

    @android.view.RemotableViewMethod
    public void setHourHandTintList(android.content.res.ColorStateList colorStateList) {
        this.mHourHandTintInfo.mTintList = colorStateList;
        this.mHourHandTintInfo.mHasTintList = true;
        this.mHourHand = this.mHourHandTintInfo.apply(this.mHourHand);
    }

    public android.content.res.ColorStateList getHourHandTintList() {
        return this.mHourHandTintInfo.mTintList;
    }

    @android.view.RemotableViewMethod
    public void setHourHandTintBlendMode(android.graphics.BlendMode blendMode) {
        this.mHourHandTintInfo.mTintBlendMode = blendMode;
        this.mHourHandTintInfo.mHasTintBlendMode = true;
        this.mHourHand = this.mHourHandTintInfo.apply(this.mHourHand);
    }

    public android.graphics.BlendMode getHourHandTintBlendMode() {
        return this.mHourHandTintInfo.mTintBlendMode;
    }

    @android.view.RemotableViewMethod
    public void setMinuteHand(android.graphics.drawable.Icon icon) {
        this.mMinuteHand = icon.loadDrawable(getContext());
        if (this.mMinuteHandTintInfo.mHasTintList || this.mMinuteHandTintInfo.mHasTintBlendMode) {
            this.mMinuteHand = this.mMinuteHandTintInfo.apply(this.mMinuteHand);
        }
        this.mChanged = true;
        invalidate();
    }

    @android.view.RemotableViewMethod
    public void setMinuteHandTintList(android.content.res.ColorStateList colorStateList) {
        this.mMinuteHandTintInfo.mTintList = colorStateList;
        this.mMinuteHandTintInfo.mHasTintList = true;
        this.mMinuteHand = this.mMinuteHandTintInfo.apply(this.mMinuteHand);
    }

    public android.content.res.ColorStateList getMinuteHandTintList() {
        return this.mMinuteHandTintInfo.mTintList;
    }

    @android.view.RemotableViewMethod
    public void setMinuteHandTintBlendMode(android.graphics.BlendMode blendMode) {
        this.mMinuteHandTintInfo.mTintBlendMode = blendMode;
        this.mMinuteHandTintInfo.mHasTintBlendMode = true;
        this.mMinuteHand = this.mMinuteHandTintInfo.apply(this.mMinuteHand);
    }

    public android.graphics.BlendMode getMinuteHandTintBlendMode() {
        return this.mMinuteHandTintInfo.mTintBlendMode;
    }

    @android.view.RemotableViewMethod
    public void setSecondHand(android.graphics.drawable.Icon icon) {
        this.mSecondHand = icon == null ? null : icon.loadDrawable(getContext());
        if (this.mSecondHandTintInfo.mHasTintList || this.mSecondHandTintInfo.mHasTintBlendMode) {
            this.mSecondHand = this.mSecondHandTintInfo.apply(this.mSecondHand);
        }
        this.mTick.run();
        this.mChanged = true;
        invalidate();
    }

    @android.view.RemotableViewMethod
    public void setSecondHandTintList(android.content.res.ColorStateList colorStateList) {
        this.mSecondHandTintInfo.mTintList = colorStateList;
        this.mSecondHandTintInfo.mHasTintList = true;
        this.mSecondHand = this.mSecondHandTintInfo.apply(this.mSecondHand);
    }

    public android.content.res.ColorStateList getSecondHandTintList() {
        return this.mSecondHandTintInfo.mTintList;
    }

    @android.view.RemotableViewMethod
    public void setSecondHandTintBlendMode(android.graphics.BlendMode blendMode) {
        this.mSecondHandTintInfo.mTintBlendMode = blendMode;
        this.mSecondHandTintInfo.mHasTintBlendMode = true;
        this.mSecondHand = this.mSecondHandTintInfo.apply(this.mSecondHand);
    }

    public android.graphics.BlendMode getSecondHandTintBlendMode() {
        return this.mSecondHandTintInfo.mTintBlendMode;
    }

    public java.lang.String getTimeZone() {
        java.time.ZoneId zoneId = this.mTimeZone;
        if (zoneId == null) {
            return null;
        }
        return zoneId.getId();
    }

    @android.view.RemotableViewMethod
    public void setTimeZone(java.lang.String str) {
        this.mTimeZone = toZoneId(str);
        createClock();
        onTimeChanged();
    }

    @Override // android.view.View
    public void onVisibilityAggregated(boolean z) {
        super.onVisibilityAggregated(z);
        if (z) {
            onVisible();
        } else {
            onInvisible();
        }
    }

    @Override // android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!this.mReceiverAttached) {
            this.mClockEventDelegate.registerTimeChangeReceiver(this.mIntentReceiver, getHandler());
            this.mReceiverAttached = true;
        }
        createClock();
        onTimeChanged();
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        if (this.mReceiverAttached) {
            this.mClockEventDelegate.unregisterTimeChangeReceiver(this.mIntentReceiver);
            this.mReceiverAttached = false;
        }
        super.onDetachedFromWindow();
    }

    public void setClockEventDelegate(android.widget.TextClock.ClockEventDelegate clockEventDelegate) {
        com.android.internal.util.Preconditions.checkState(!this.mReceiverAttached, "Clock events already registered");
        this.mClockEventDelegate = clockEventDelegate;
    }

    private void onVisible() {
        if (!this.mVisible) {
            this.mVisible = true;
            this.mTick.run();
        }
    }

    private void onInvisible() {
        if (this.mVisible) {
            removeCallbacks(this.mTick);
            this.mVisible = false;
        }
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        float f;
        int mode = android.view.View.MeasureSpec.getMode(i);
        int size = android.view.View.MeasureSpec.getSize(i);
        int mode2 = android.view.View.MeasureSpec.getMode(i2);
        int size2 = android.view.View.MeasureSpec.getSize(i2);
        float f2 = 1.0f;
        if (mode != 0 && size < this.mDialWidth) {
            f = size / this.mDialWidth;
        } else {
            f = 1.0f;
        }
        if (mode2 != 0 && size2 < this.mDialHeight) {
            f2 = size2 / this.mDialHeight;
        }
        float min = java.lang.Math.min(f, f2);
        setMeasuredDimension(resolveSizeAndState((int) (this.mDialWidth * min), i, 0), resolveSizeAndState((int) (this.mDialHeight * min), i2, 0));
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.mChanged = true;
    }

    @Override // android.view.View
    protected void onDraw(android.graphics.Canvas canvas) {
        super.onDraw(canvas);
        boolean z = this.mChanged;
        boolean z2 = false;
        if (z) {
            this.mChanged = false;
        }
        int i = this.mRight - this.mLeft;
        int i2 = this.mBottom - this.mTop;
        int i3 = i / 2;
        int i4 = i2 / 2;
        android.graphics.drawable.Drawable drawable = this.mDial;
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        if (i < intrinsicWidth || i2 < intrinsicHeight) {
            float min = java.lang.Math.min(i / intrinsicWidth, i2 / intrinsicHeight);
            canvas.save();
            canvas.scale(min, min, i3, i4);
            z2 = true;
        }
        if (z) {
            int i5 = intrinsicWidth / 2;
            int i6 = intrinsicHeight / 2;
            drawable.setBounds(i3 - i5, i4 - i6, i5 + i3, i6 + i4);
        }
        drawable.draw(canvas);
        canvas.save();
        float f = i3;
        float f2 = i4;
        canvas.rotate((this.mHour / 12.0f) * 360.0f, f, f2);
        android.graphics.drawable.Drawable drawable2 = this.mHourHand;
        if (z) {
            int intrinsicWidth2 = drawable2.getIntrinsicWidth() / 2;
            int intrinsicHeight2 = drawable2.getIntrinsicHeight() / 2;
            drawable2.setBounds(i3 - intrinsicWidth2, i4 - intrinsicHeight2, intrinsicWidth2 + i3, intrinsicHeight2 + i4);
        }
        drawable2.draw(canvas);
        canvas.restore();
        canvas.save();
        canvas.rotate((this.mMinutes / 60.0f) * 360.0f, f, f2);
        android.graphics.drawable.Drawable drawable3 = this.mMinuteHand;
        if (z) {
            int intrinsicWidth3 = drawable3.getIntrinsicWidth() / 2;
            int intrinsicHeight3 = drawable3.getIntrinsicHeight() / 2;
            drawable3.setBounds(i3 - intrinsicWidth3, i4 - intrinsicHeight3, intrinsicWidth3 + i3, intrinsicHeight3 + i4);
        }
        drawable3.draw(canvas);
        canvas.restore();
        android.graphics.drawable.Drawable drawable4 = this.mSecondHand;
        if (drawable4 != null && this.mSecondsHandFps > 0) {
            canvas.save();
            canvas.rotate((this.mSeconds / 60.0f) * 360.0f, f, f2);
            if (z) {
                int intrinsicWidth4 = drawable4.getIntrinsicWidth() / 2;
                int intrinsicHeight4 = drawable4.getIntrinsicHeight() / 2;
                drawable4.setBounds(i3 - intrinsicWidth4, i4 - intrinsicHeight4, i3 + intrinsicWidth4, i4 + intrinsicHeight4);
            }
            drawable4.draw(canvas);
            canvas.restore();
        }
        if (z2) {
            canvas.restore();
        }
    }

    protected java.time.Instant now() {
        return this.mClock.instant();
    }

    protected void onTimeChanged() {
        java.time.Instant now = now();
        onTimeChanged(now.atZone(this.mClock.getZone()).toLocalTime(), now.toEpochMilli());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTimeChanged(java.time.LocalTime localTime, long j) {
        float f = this.mHour;
        float f2 = this.mMinutes;
        float second = localTime.getSecond() + (localTime.getNano() / 1.0E9f);
        if (this.mSecondsHandFps > 0) {
            second = java.lang.Math.round(second * this.mSecondsHandFps) / this.mSecondsHandFps;
        }
        this.mSeconds = second;
        this.mMinutes = localTime.getMinute() + (this.mSeconds / 60.0f);
        this.mHour = localTime.getHour() + (this.mMinutes / 60.0f);
        this.mChanged = true;
        if (((int) f) != ((int) this.mHour) || ((int) f2) != ((int) this.mMinutes)) {
            updateContentDescription(j);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void createClock() {
        java.time.ZoneId zoneId = this.mTimeZone;
        if (zoneId == null) {
            this.mClock = java.time.Clock.systemDefaultZone();
        } else {
            this.mClock = java.time.Clock.system(zoneId);
        }
    }

    private void updateContentDescription(long j) {
        setContentDescription(android.text.format.DateUtils.formatDateRange(this.mContext, new java.util.Formatter(new java.lang.StringBuilder(50), java.util.Locale.getDefault()), j, j, 129, getTimeZone()).toString());
    }

    private static java.time.ZoneId toZoneId(java.lang.String str) {
        if (str == null) {
            return null;
        }
        try {
            return java.time.ZoneId.of(str);
        } catch (java.time.DateTimeException e) {
            android.util.Log.w(LOG_TAG, "Failed to parse time zone from " + str, e);
            return null;
        }
    }

    private final class TintInfo {
        boolean mHasTintBlendMode;
        boolean mHasTintList;
        android.graphics.BlendMode mTintBlendMode;
        android.content.res.ColorStateList mTintList;

        private TintInfo() {
        }

        android.graphics.drawable.Drawable apply(android.graphics.drawable.Drawable drawable) {
            if (drawable == null) {
                return null;
            }
            android.graphics.drawable.Drawable mutate = drawable.mutate();
            if (this.mHasTintList) {
                mutate.setTintList(this.mTintList);
            }
            if (this.mHasTintBlendMode) {
                mutate.setTintBlendMode(this.mTintBlendMode);
            }
            if (drawable.isStateful()) {
                mutate.setState(android.widget.AnalogClock.this.getDrawableState());
            }
            return mutate;
        }
    }
}
