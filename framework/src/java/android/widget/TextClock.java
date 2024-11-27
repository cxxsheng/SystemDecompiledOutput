package android.widget;

@android.widget.RemoteViews.RemoteView
/* loaded from: classes4.dex */
public class TextClock extends android.widget.TextView {

    @java.lang.Deprecated
    public static final java.lang.CharSequence DEFAULT_FORMAT_12_HOUR = "h:mm a";

    @java.lang.Deprecated
    public static final java.lang.CharSequence DEFAULT_FORMAT_24_HOUR = "H:mm";
    private android.widget.TextClock.ClockEventDelegate mClockEventDelegate;
    private java.lang.CharSequence mDescFormat;
    private java.lang.CharSequence mDescFormat12;
    private java.lang.CharSequence mDescFormat24;

    @android.view.ViewDebug.ExportedProperty
    private java.lang.CharSequence mFormat;
    private java.lang.CharSequence mFormat12;
    private java.lang.CharSequence mFormat24;
    private android.database.ContentObserver mFormatChangeObserver;

    @android.view.ViewDebug.ExportedProperty
    private boolean mHasSeconds;
    private final android.content.BroadcastReceiver mIntentReceiver;
    private boolean mRegistered;
    private boolean mShouldRunTicker;
    private boolean mShowCurrentUserTime;
    private boolean mStopTicking;
    private final java.lang.Runnable mTicker;
    private java.util.Calendar mTime;
    private java.lang.String mTimeZone;

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<android.widget.TextClock> {
        private int mFormat12HourId;
        private int mFormat24HourId;
        private int mIs24HourModeEnabledId;
        private boolean mPropertiesMapped = false;
        private int mTimeZoneId;

        @Override // android.view.inspector.InspectionCompanion
        public void mapProperties(android.view.inspector.PropertyMapper propertyMapper) {
            this.mFormat12HourId = propertyMapper.mapObject("format12Hour", 16843722);
            this.mFormat24HourId = propertyMapper.mapObject("format24Hour", 16843723);
            this.mIs24HourModeEnabledId = propertyMapper.mapBoolean("is24HourModeEnabled", 0);
            this.mTimeZoneId = propertyMapper.mapObject("timeZone", 16843724);
            this.mPropertiesMapped = true;
        }

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(android.widget.TextClock textClock, android.view.inspector.PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new android.view.inspector.InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readObject(this.mFormat12HourId, textClock.getFormat12Hour());
            propertyReader.readObject(this.mFormat24HourId, textClock.getFormat24Hour());
            propertyReader.readBoolean(this.mIs24HourModeEnabledId, textClock.is24HourModeEnabled());
            propertyReader.readObject(this.mTimeZoneId, textClock.getTimeZone());
        }
    }

    private class FormatChangeObserver extends android.database.ContentObserver {
        public FormatChangeObserver(android.os.Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            android.widget.TextClock.this.chooseFormat();
            android.widget.TextClock.this.onTimeChanged();
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, android.net.Uri uri) {
            android.widget.TextClock.this.chooseFormat();
            android.widget.TextClock.this.onTimeChanged();
        }
    }

    public TextClock(android.content.Context context) {
        super(context);
        this.mIntentReceiver = new android.content.BroadcastReceiver() { // from class: android.widget.TextClock.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                if (android.widget.TextClock.this.mStopTicking) {
                    return;
                }
                if (android.widget.TextClock.this.mTimeZone == null && android.content.Intent.ACTION_TIMEZONE_CHANGED.equals(intent.getAction())) {
                    android.widget.TextClock.this.createTime(intent.getStringExtra(android.content.Intent.EXTRA_TIMEZONE));
                } else if (!android.widget.TextClock.this.mShouldRunTicker && android.content.Intent.ACTION_TIME_CHANGED.equals(intent.getAction())) {
                    return;
                }
                android.widget.TextClock.this.onTimeChanged();
            }
        };
        this.mTicker = new java.lang.Runnable() { // from class: android.widget.TextClock.2
            @Override // java.lang.Runnable
            public void run() {
                java.time.ZonedDateTime withNano;
                android.widget.TextClock.this.removeCallbacks(this);
                if (android.widget.TextClock.this.mStopTicking || !android.widget.TextClock.this.mShouldRunTicker) {
                    return;
                }
                android.widget.TextClock.this.onTimeChanged();
                java.time.Instant instant = android.widget.TextClock.this.mTime.toInstant();
                java.time.ZoneId zoneId = android.widget.TextClock.this.mTime.getTimeZone().toZoneId();
                if (android.widget.TextClock.this.mHasSeconds) {
                    withNano = instant.atZone(zoneId).plusSeconds(1L).withNano(0);
                } else {
                    withNano = instant.atZone(zoneId).plusMinutes(1L).withSecond(0).withNano(0);
                }
                long millis = java.time.Duration.between(instant, withNano.toInstant()).toMillis();
                if (millis <= 0) {
                    millis = 1000;
                }
                android.widget.TextClock.this.postDelayed(this, millis);
            }
        };
        init();
    }

    public TextClock(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TextClock(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public TextClock(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mIntentReceiver = new android.content.BroadcastReceiver() { // from class: android.widget.TextClock.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                if (android.widget.TextClock.this.mStopTicking) {
                    return;
                }
                if (android.widget.TextClock.this.mTimeZone == null && android.content.Intent.ACTION_TIMEZONE_CHANGED.equals(intent.getAction())) {
                    android.widget.TextClock.this.createTime(intent.getStringExtra(android.content.Intent.EXTRA_TIMEZONE));
                } else if (!android.widget.TextClock.this.mShouldRunTicker && android.content.Intent.ACTION_TIME_CHANGED.equals(intent.getAction())) {
                    return;
                }
                android.widget.TextClock.this.onTimeChanged();
            }
        };
        this.mTicker = new java.lang.Runnable() { // from class: android.widget.TextClock.2
            @Override // java.lang.Runnable
            public void run() {
                java.time.ZonedDateTime withNano;
                android.widget.TextClock.this.removeCallbacks(this);
                if (android.widget.TextClock.this.mStopTicking || !android.widget.TextClock.this.mShouldRunTicker) {
                    return;
                }
                android.widget.TextClock.this.onTimeChanged();
                java.time.Instant instant = android.widget.TextClock.this.mTime.toInstant();
                java.time.ZoneId zoneId = android.widget.TextClock.this.mTime.getTimeZone().toZoneId();
                if (android.widget.TextClock.this.mHasSeconds) {
                    withNano = instant.atZone(zoneId).plusSeconds(1L).withNano(0);
                } else {
                    withNano = instant.atZone(zoneId).plusMinutes(1L).withSecond(0).withNano(0);
                }
                long millis = java.time.Duration.between(instant, withNano.toInstant()).toMillis();
                if (millis <= 0) {
                    millis = 1000;
                }
                android.widget.TextClock.this.postDelayed(this, millis);
            }
        };
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.TextClock, i, i2);
        saveAttributeDataForStyleable(context, com.android.internal.R.styleable.TextClock, attributeSet, obtainStyledAttributes, i, i2);
        try {
            this.mFormat12 = obtainStyledAttributes.getText(0);
            this.mFormat24 = obtainStyledAttributes.getText(1);
            this.mTimeZone = obtainStyledAttributes.getString(2);
            obtainStyledAttributes.recycle();
            init();
        } catch (java.lang.Throwable th) {
            obtainStyledAttributes.recycle();
            throw th;
        }
    }

    private void init() {
        if (this.mFormat12 == null) {
            this.mFormat12 = getBestDateTimePattern("hm");
        }
        if (this.mFormat24 == null) {
            this.mFormat24 = getBestDateTimePattern("Hm");
        }
        this.mClockEventDelegate = new android.widget.TextClock.ClockEventDelegate(getContext());
        createTime(this.mTimeZone);
        chooseFormat();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void createTime(java.lang.String str) {
        if (str != null) {
            this.mTime = java.util.Calendar.getInstance(java.util.TimeZone.getTimeZone(str));
        } else {
            this.mTime = java.util.Calendar.getInstance();
        }
    }

    @android.view.ViewDebug.ExportedProperty
    public java.lang.CharSequence getFormat12Hour() {
        return this.mFormat12;
    }

    @android.view.RemotableViewMethod
    public void setFormat12Hour(java.lang.CharSequence charSequence) {
        this.mFormat12 = charSequence;
        chooseFormat();
        onTimeChanged();
    }

    public void setContentDescriptionFormat12Hour(java.lang.CharSequence charSequence) {
        this.mDescFormat12 = charSequence;
        chooseFormat();
        onTimeChanged();
    }

    @android.view.ViewDebug.ExportedProperty
    public java.lang.CharSequence getFormat24Hour() {
        return this.mFormat24;
    }

    @android.view.RemotableViewMethod
    public void setFormat24Hour(java.lang.CharSequence charSequence) {
        this.mFormat24 = charSequence;
        chooseFormat();
        onTimeChanged();
    }

    public void setContentDescriptionFormat24Hour(java.lang.CharSequence charSequence) {
        this.mDescFormat24 = charSequence;
        chooseFormat();
        onTimeChanged();
    }

    public void setShowCurrentUserTime(boolean z) {
        this.mShowCurrentUserTime = z;
        chooseFormat();
        onTimeChanged();
        unregisterObserver();
        registerObserver();
    }

    public void setClockEventDelegate(android.widget.TextClock.ClockEventDelegate clockEventDelegate) {
        com.android.internal.util.Preconditions.checkState(!this.mRegistered, "Clock events already registered");
        this.mClockEventDelegate = clockEventDelegate;
    }

    public void refreshTime() {
        onTimeChanged();
        invalidate();
    }

    public boolean is24HourModeEnabled() {
        if (this.mShowCurrentUserTime) {
            return android.text.format.DateFormat.is24HourFormat(getContext(), android.app.ActivityManager.getCurrentUser());
        }
        return android.text.format.DateFormat.is24HourFormat(getContext());
    }

    public java.lang.String getTimeZone() {
        return this.mTimeZone;
    }

    @android.view.RemotableViewMethod
    public void setTimeZone(java.lang.String str) {
        this.mTimeZone = str;
        createTime(str);
        onTimeChanged();
    }

    public java.lang.CharSequence getFormat() {
        return this.mFormat;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void chooseFormat() {
        if (is24HourModeEnabled()) {
            this.mFormat = abc(this.mFormat24, this.mFormat12, getBestDateTimePattern("Hm"));
            this.mDescFormat = abc(this.mDescFormat24, this.mDescFormat12, this.mFormat);
        } else {
            this.mFormat = abc(this.mFormat12, this.mFormat24, getBestDateTimePattern("hm"));
            this.mDescFormat = abc(this.mDescFormat12, this.mDescFormat24, this.mFormat);
        }
        boolean z = this.mHasSeconds;
        this.mHasSeconds = android.text.format.DateFormat.hasSeconds(this.mFormat);
        if (this.mShouldRunTicker && z != this.mHasSeconds) {
            this.mTicker.run();
        }
    }

    private java.lang.String getBestDateTimePattern(java.lang.String str) {
        return android.icu.text.DateTimePatternGenerator.getInstance(getContext().getResources().getConfiguration().locale).getBestPattern(str);
    }

    private static java.lang.CharSequence abc(java.lang.CharSequence charSequence, java.lang.CharSequence charSequence2, java.lang.CharSequence charSequence3) {
        return charSequence == null ? charSequence2 == null ? charSequence3 : charSequence2 : charSequence;
    }

    @Override // android.widget.TextView, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!this.mRegistered) {
            this.mRegistered = true;
            this.mClockEventDelegate.registerTimeChangeReceiver(this.mIntentReceiver, getHandler());
            registerObserver();
            createTime(this.mTimeZone);
        }
    }

    @Override // android.widget.TextView, android.view.View
    public void onVisibilityAggregated(boolean z) {
        super.onVisibilityAggregated(z);
        if (!this.mShouldRunTicker && z) {
            this.mShouldRunTicker = true;
            this.mTicker.run();
        } else if (this.mShouldRunTicker && !z) {
            this.mShouldRunTicker = false;
            removeCallbacks(this.mTicker);
        }
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mRegistered) {
            this.mClockEventDelegate.unregisterTimeChangeReceiver(this.mIntentReceiver);
            unregisterObserver();
            this.mRegistered = false;
        }
    }

    public void disableClockTick() {
        this.mStopTicking = true;
    }

    private void registerObserver() {
        if (this.mRegistered) {
            if (this.mFormatChangeObserver == null) {
                this.mFormatChangeObserver = new android.widget.TextClock.FormatChangeObserver(getHandler());
            }
            this.mClockEventDelegate.registerFormatChangeObserver(this.mFormatChangeObserver, this.mShowCurrentUserTime ? -1 : android.os.UserHandle.myUserId());
        }
    }

    private void unregisterObserver() {
        if (this.mFormatChangeObserver != null) {
            this.mClockEventDelegate.unregisterFormatChangeObserver(this.mFormatChangeObserver);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTimeChanged() {
        this.mTime.setTimeInMillis(java.lang.System.currentTimeMillis());
        lambda$setTextAsync$0(android.text.format.DateFormat.format(this.mFormat, this.mTime));
        setContentDescription(android.text.format.DateFormat.format(this.mDescFormat, this.mTime));
    }

    @Override // android.widget.TextView, android.view.View
    protected void encodeProperties(android.view.ViewHierarchyEncoder viewHierarchyEncoder) {
        super.encodeProperties(viewHierarchyEncoder);
        java.lang.CharSequence format12Hour = getFormat12Hour();
        viewHierarchyEncoder.addProperty("format12Hour", format12Hour == null ? null : format12Hour.toString());
        java.lang.CharSequence format24Hour = getFormat24Hour();
        viewHierarchyEncoder.addProperty("format24Hour", format24Hour == null ? null : format24Hour.toString());
        viewHierarchyEncoder.addProperty(android.provider.Telephony.CellBroadcasts.MESSAGE_FORMAT, this.mFormat != null ? this.mFormat.toString() : null);
        viewHierarchyEncoder.addProperty("hasSeconds", this.mHasSeconds);
    }

    public static class ClockEventDelegate {
        private final android.content.Context mContext;

        public ClockEventDelegate(android.content.Context context) {
            this.mContext = context;
        }

        public void registerTimeChangeReceiver(android.content.BroadcastReceiver broadcastReceiver, android.os.Handler handler) {
            android.content.IntentFilter intentFilter = new android.content.IntentFilter();
            intentFilter.addAction(android.content.Intent.ACTION_TIME_CHANGED);
            intentFilter.addAction(android.content.Intent.ACTION_TIMEZONE_CHANGED);
            this.mContext.registerReceiverAsUser(broadcastReceiver, android.os.Process.myUserHandle(), intentFilter, null, handler);
        }

        public void unregisterTimeChangeReceiver(android.content.BroadcastReceiver broadcastReceiver) {
            this.mContext.unregisterReceiver(broadcastReceiver);
        }

        public void registerFormatChangeObserver(android.database.ContentObserver contentObserver, int i) {
            this.mContext.getContentResolver().registerContentObserver(android.provider.Settings.System.getUriFor(android.provider.Settings.System.TIME_12_24), true, contentObserver, i);
        }

        public void unregisterFormatChangeObserver(android.database.ContentObserver contentObserver) {
            this.mContext.getContentResolver().unregisterContentObserver(contentObserver);
        }
    }
}
