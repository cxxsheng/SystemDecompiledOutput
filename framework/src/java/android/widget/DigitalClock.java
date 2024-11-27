package android.widget;

@java.lang.Deprecated
/* loaded from: classes4.dex */
public class DigitalClock extends android.widget.TextView {
    java.util.Calendar mCalendar;
    java.lang.String mFormat;
    private android.widget.DigitalClock.FormatChangeObserver mFormatChangeObserver;
    private android.os.Handler mHandler;
    private java.lang.Runnable mTicker;
    private boolean mTickerStopped;

    public DigitalClock(android.content.Context context) {
        super(context);
        this.mTickerStopped = false;
        initClock();
    }

    public DigitalClock(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mTickerStopped = false;
        initClock();
    }

    private void initClock() {
        if (this.mCalendar == null) {
            this.mCalendar = java.util.Calendar.getInstance();
        }
    }

    @Override // android.widget.TextView, android.view.View
    protected void onAttachedToWindow() {
        this.mTickerStopped = false;
        super.onAttachedToWindow();
        this.mFormatChangeObserver = new android.widget.DigitalClock.FormatChangeObserver();
        getContext().getContentResolver().registerContentObserver(android.provider.Settings.System.CONTENT_URI, true, this.mFormatChangeObserver);
        setFormat();
        this.mHandler = new android.os.Handler();
        this.mTicker = new java.lang.Runnable() { // from class: android.widget.DigitalClock.1
            @Override // java.lang.Runnable
            public void run() {
                if (android.widget.DigitalClock.this.mTickerStopped) {
                    return;
                }
                android.widget.DigitalClock.this.mCalendar.setTimeInMillis(java.lang.System.currentTimeMillis());
                android.widget.DigitalClock.this.lambda$setTextAsync$0(android.text.format.DateFormat.format(android.widget.DigitalClock.this.mFormat, android.widget.DigitalClock.this.mCalendar));
                android.widget.DigitalClock.this.invalidate();
                long uptimeMillis = android.os.SystemClock.uptimeMillis();
                android.widget.DigitalClock.this.mHandler.postAtTime(android.widget.DigitalClock.this.mTicker, uptimeMillis + (1000 - (uptimeMillis % 1000)));
            }
        };
        this.mTicker.run();
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mTickerStopped = true;
        getContext().getContentResolver().unregisterContentObserver(this.mFormatChangeObserver);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setFormat() {
        this.mFormat = android.text.format.DateFormat.getTimeFormatString(getContext());
    }

    private class FormatChangeObserver extends android.database.ContentObserver {
        public FormatChangeObserver() {
            super(new android.os.Handler());
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            android.widget.DigitalClock.this.setFormat();
        }
    }

    @Override // android.widget.TextView, android.view.View
    public java.lang.CharSequence getAccessibilityClassName() {
        return android.widget.DigitalClock.class.getName();
    }
}
