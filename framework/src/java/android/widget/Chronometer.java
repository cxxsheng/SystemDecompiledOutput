package android.widget;

@android.widget.RemoteViews.RemoteView
/* loaded from: classes4.dex */
public class Chronometer extends android.widget.TextView {
    private static final int HOUR_IN_SEC = 3600;
    private static final int MIN_IN_SEC = 60;
    private static final java.lang.String TAG = "Chronometer";
    private long mBase;
    private boolean mCountDown;
    private java.lang.String mFormat;
    private java.lang.StringBuilder mFormatBuilder;
    private java.util.Formatter mFormatter;
    private java.lang.Object[] mFormatterArgs;
    private java.util.Locale mFormatterLocale;
    private boolean mLogged;
    private long mNow;
    private android.widget.Chronometer.OnChronometerTickListener mOnChronometerTickListener;
    private java.lang.StringBuilder mRecycle;
    private boolean mRunning;
    private boolean mStarted;
    private final java.lang.Runnable mTickRunnable;
    private boolean mVisible;

    public interface OnChronometerTickListener {
        void onChronometerTick(android.widget.Chronometer chronometer);
    }

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<android.widget.Chronometer> {
        private int mCountDownId;
        private int mFormatId;
        private boolean mPropertiesMapped = false;

        @Override // android.view.inspector.InspectionCompanion
        public void mapProperties(android.view.inspector.PropertyMapper propertyMapper) {
            this.mCountDownId = propertyMapper.mapBoolean("countDown", 16844059);
            this.mFormatId = propertyMapper.mapObject(android.provider.Telephony.CellBroadcasts.MESSAGE_FORMAT, 16843013);
            this.mPropertiesMapped = true;
        }

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(android.widget.Chronometer chronometer, android.view.inspector.PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new android.view.inspector.InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readBoolean(this.mCountDownId, chronometer.isCountDown());
            propertyReader.readObject(this.mFormatId, chronometer.getFormat());
        }
    }

    public Chronometer(android.content.Context context) {
        this(context, null, 0);
    }

    public Chronometer(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public Chronometer(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public Chronometer(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mFormatterArgs = new java.lang.Object[1];
        this.mRecycle = new java.lang.StringBuilder(8);
        this.mTickRunnable = new java.lang.Runnable() { // from class: android.widget.Chronometer.1
            @Override // java.lang.Runnable
            public void run() {
                if (android.widget.Chronometer.this.mRunning) {
                    android.widget.Chronometer.this.updateText(android.os.SystemClock.elapsedRealtime());
                    android.widget.Chronometer.this.dispatchChronometerTick();
                    android.widget.Chronometer.this.postDelayed(android.widget.Chronometer.this.mTickRunnable, 1000L);
                }
            }
        };
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.Chronometer, i, i2);
        saveAttributeDataForStyleable(context, com.android.internal.R.styleable.Chronometer, attributeSet, obtainStyledAttributes, i, i2);
        setFormat(obtainStyledAttributes.getString(0));
        setCountDown(obtainStyledAttributes.getBoolean(1, false));
        obtainStyledAttributes.recycle();
        init();
    }

    private void init() {
        this.mBase = android.os.SystemClock.elapsedRealtime();
        updateText(this.mBase);
    }

    @android.view.RemotableViewMethod
    public void setCountDown(boolean z) {
        this.mCountDown = z;
        updateText(android.os.SystemClock.elapsedRealtime());
    }

    public boolean isCountDown() {
        return this.mCountDown;
    }

    public boolean isTheFinalCountDown() {
        try {
            getContext().startActivity(new android.content.Intent("android.intent.action.VIEW", android.net.Uri.parse("https://youtu.be/9jK-NcRmVcw")).addCategory(android.content.Intent.CATEGORY_BROWSABLE).addFlags(528384));
            return true;
        } catch (java.lang.Exception e) {
            return false;
        }
    }

    @android.view.RemotableViewMethod
    public void setBase(long j) {
        this.mBase = j;
        dispatchChronometerTick();
        updateText(android.os.SystemClock.elapsedRealtime());
    }

    public long getBase() {
        return this.mBase;
    }

    @android.view.RemotableViewMethod
    public void setFormat(java.lang.String str) {
        this.mFormat = str;
        if (str != null && this.mFormatBuilder == null) {
            this.mFormatBuilder = new java.lang.StringBuilder(str.length() * 2);
        }
    }

    public java.lang.String getFormat() {
        return this.mFormat;
    }

    public void setOnChronometerTickListener(android.widget.Chronometer.OnChronometerTickListener onChronometerTickListener) {
        this.mOnChronometerTickListener = onChronometerTickListener;
    }

    public android.widget.Chronometer.OnChronometerTickListener getOnChronometerTickListener() {
        return this.mOnChronometerTickListener;
    }

    public void start() {
        this.mStarted = true;
        updateRunning();
    }

    public void stop() {
        this.mStarted = false;
        updateRunning();
    }

    @android.view.RemotableViewMethod
    public void setStarted(boolean z) {
        this.mStarted = z;
        updateRunning();
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mVisible = false;
        updateRunning();
    }

    @Override // android.view.View
    protected void onWindowVisibilityChanged(int i) {
        super.onWindowVisibilityChanged(i);
        this.mVisible = i == 0;
        updateRunning();
    }

    @Override // android.widget.TextView, android.view.View
    protected void onVisibilityChanged(android.view.View view, int i) {
        super.onVisibilityChanged(view, i);
        updateRunning();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void updateText(long j) {
        boolean z;
        this.mNow = j;
        long j2 = (this.mCountDown ? this.mBase - j : j - this.mBase) / 1000;
        if (j2 >= 0) {
            z = false;
        } else {
            j2 = -j2;
            z = true;
        }
        java.lang.String formatElapsedTime = android.text.format.DateUtils.formatElapsedTime(this.mRecycle, j2);
        if (z) {
            formatElapsedTime = getResources().getString(com.android.internal.R.string.negative_duration, formatElapsedTime);
        }
        if (this.mFormat != null) {
            java.util.Locale locale = java.util.Locale.getDefault();
            if (this.mFormatter == null || !locale.equals(this.mFormatterLocale)) {
                this.mFormatterLocale = locale;
                this.mFormatter = new java.util.Formatter(this.mFormatBuilder, locale);
            }
            this.mFormatBuilder.setLength(0);
            this.mFormatterArgs[0] = formatElapsedTime;
            try {
                this.mFormatter.format(this.mFormat, this.mFormatterArgs);
                formatElapsedTime = this.mFormatBuilder.toString();
            } catch (java.util.IllegalFormatException e) {
                if (!this.mLogged) {
                    android.util.Log.w(TAG, "Illegal format string: " + this.mFormat);
                    this.mLogged = true;
                }
            }
        }
        lambda$setTextAsync$0(formatElapsedTime);
    }

    private void updateRunning() {
        boolean z = this.mVisible && this.mStarted && isShown();
        if (z != this.mRunning) {
            if (z) {
                updateText(android.os.SystemClock.elapsedRealtime());
                dispatchChronometerTick();
                postDelayed(this.mTickRunnable, 1000L);
            } else {
                removeCallbacks(this.mTickRunnable);
            }
            this.mRunning = z;
        }
    }

    void dispatchChronometerTick() {
        if (this.mOnChronometerTickListener != null) {
            this.mOnChronometerTickListener.onChronometerTick(this);
        }
    }

    private static java.lang.String formatDuration(long j) {
        int i;
        int i2 = (int) (j / 1000);
        if (i2 < 0) {
            i2 = -i2;
        }
        int i3 = 0;
        if (i2 < 3600) {
            i = 0;
        } else {
            i = i2 / 3600;
            i2 -= i * 3600;
        }
        if (i2 >= 60) {
            i3 = i2 / 60;
            i2 -= i3 * 60;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if (i > 0) {
            arrayList.add(new android.icu.util.Measure(java.lang.Integer.valueOf(i), android.icu.util.MeasureUnit.HOUR));
        }
        if (i3 > 0) {
            arrayList.add(new android.icu.util.Measure(java.lang.Integer.valueOf(i3), android.icu.util.MeasureUnit.MINUTE));
        }
        arrayList.add(new android.icu.util.Measure(java.lang.Integer.valueOf(i2), android.icu.util.MeasureUnit.SECOND));
        return android.icu.text.MeasureFormat.getInstance(java.util.Locale.getDefault(), android.icu.text.MeasureFormat.FormatWidth.WIDE).formatMeasures((android.icu.util.Measure[]) arrayList.toArray(new android.icu.util.Measure[arrayList.size()]));
    }

    @Override // android.view.View
    public java.lang.CharSequence getContentDescription() {
        return formatDuration(this.mNow - this.mBase);
    }

    @Override // android.widget.TextView, android.view.View
    public java.lang.CharSequence getAccessibilityClassName() {
        return android.widget.Chronometer.class.getName();
    }
}
