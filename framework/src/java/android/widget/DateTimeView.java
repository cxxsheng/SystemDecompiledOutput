package android.widget;

@android.widget.RemoteViews.RemoteView
/* loaded from: classes4.dex */
public class DateTimeView extends android.widget.TextView {
    private static final int SHOW_MONTH_DAY_YEAR = 1;
    private static final int SHOW_TIME = 0;
    private static final java.lang.ThreadLocal<android.widget.DateTimeView.ReceiverInfo> sReceiverInfo = new java.lang.ThreadLocal<>();
    int mLastDisplay;
    java.text.DateFormat mLastFormat;
    private java.time.LocalDateTime mLocalTime;
    private java.lang.String mNowText;
    private boolean mShowRelativeTime;
    private long mTimeMillis;
    private long mUpdateTimeMillis;

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<android.widget.DateTimeView> {
        private boolean mPropertiesMapped = false;
        private int mShowReleativeId;

        @Override // android.view.inspector.InspectionCompanion
        public void mapProperties(android.view.inspector.PropertyMapper propertyMapper) {
            this.mShowReleativeId = propertyMapper.mapBoolean("showReleative", 0);
            this.mPropertiesMapped = true;
        }

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(android.widget.DateTimeView dateTimeView, android.view.inspector.PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new android.view.inspector.InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readBoolean(this.mShowReleativeId, dateTimeView.isShowRelativeTime());
        }
    }

    public DateTimeView(android.content.Context context) {
        this(context, null);
    }

    public DateTimeView(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mLastDisplay = -1;
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.DateTimeView, 0, 0);
        int indexCount = obtainStyledAttributes.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            switch (obtainStyledAttributes.getIndex(i)) {
                case 0:
                    setShowRelativeTime(obtainStyledAttributes.getBoolean(i, false));
                    break;
            }
        }
        obtainStyledAttributes.recycle();
    }

    @Override // android.widget.TextView, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        android.widget.DateTimeView.ReceiverInfo receiverInfo = sReceiverInfo.get();
        if (receiverInfo == null) {
            receiverInfo = new android.widget.DateTimeView.ReceiverInfo();
            sReceiverInfo.set(receiverInfo);
        }
        receiverInfo.addView(this);
        if (this.mShowRelativeTime) {
            update();
        }
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        android.widget.DateTimeView.ReceiverInfo receiverInfo = sReceiverInfo.get();
        if (receiverInfo != null) {
            receiverInfo.removeView(this);
        }
    }

    @android.view.RemotableViewMethod
    public void setTime(long j) {
        this.mTimeMillis = j;
        this.mLocalTime = toLocalDateTime(j, java.time.ZoneId.systemDefault()).withSecond(0);
        update();
    }

    @android.view.RemotableViewMethod
    public void setShowRelativeTime(boolean z) {
        this.mShowRelativeTime = z;
        updateNowText();
        update();
    }

    public boolean isShowRelativeTime() {
        return this.mShowRelativeTime;
    }

    @Override // android.view.View
    @android.view.RemotableViewMethod
    public void setVisibility(int i) {
        boolean z = i != 8 && getVisibility() == 8;
        super.setVisibility(i);
        if (z) {
            update();
        }
    }

    void update() {
        java.text.DateFormat timeFormat;
        if (this.mLocalTime == null || getVisibility() == 8) {
            return;
        }
        if (this.mShowRelativeTime) {
            updateRelativeTime();
            return;
        }
        java.time.ZoneId systemDefault = java.time.ZoneId.systemDefault();
        java.time.LocalDateTime localDateTime = this.mLocalTime;
        java.time.LocalDateTime of = java.time.LocalDateTime.of(localDateTime.toLocalDate(), java.time.LocalTime.MIDNIGHT);
        java.time.LocalDateTime plusDays = of.plusDays(1L);
        int i = 0;
        java.time.LocalDateTime withSecond = java.time.LocalDateTime.now(systemDefault).withSecond(0);
        long epochMillis = toEpochMillis(localDateTime.minusHours(12L), systemDefault);
        long epochMillis2 = toEpochMillis(localDateTime.plusHours(12L), systemDefault);
        long epochMillis3 = toEpochMillis(of, systemDefault);
        long epochMillis4 = toEpochMillis(plusDays, systemDefault);
        long epochMillis5 = toEpochMillis(localDateTime, systemDefault);
        long epochMillis6 = toEpochMillis(withSecond, systemDefault);
        if ((epochMillis6 < epochMillis3 || epochMillis6 >= epochMillis4) && (epochMillis6 < epochMillis || epochMillis6 >= epochMillis2)) {
            i = 1;
        }
        if (i == this.mLastDisplay && this.mLastFormat != null) {
            timeFormat = this.mLastFormat;
        } else {
            switch (i) {
                case 0:
                    timeFormat = getTimeFormat();
                    break;
                case 1:
                    timeFormat = java.text.DateFormat.getDateInstance(3);
                    break;
                default:
                    throw new java.lang.RuntimeException("unknown display value: " + i);
            }
            this.mLastFormat = timeFormat;
        }
        maybeSetText(timeFormat.format(new java.util.Date(epochMillis5)));
        if (i == 0) {
            if (epochMillis2 <= epochMillis4) {
                epochMillis2 = epochMillis4;
            }
            this.mUpdateTimeMillis = epochMillis2;
        } else {
            if (this.mTimeMillis < epochMillis6) {
                this.mUpdateTimeMillis = 0L;
                return;
            }
            if (epochMillis >= epochMillis3) {
                epochMillis = epochMillis3;
            }
            this.mUpdateTimeMillis = epochMillis;
        }
    }

    private void updateRelativeTime() {
        int i;
        int i2;
        java.lang.String string;
        int i3;
        int i4;
        int i5;
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        long abs = java.lang.Math.abs(currentTimeMillis - this.mTimeMillis);
        boolean z = currentTimeMillis >= this.mTimeMillis;
        long j = 60000;
        if (abs < 60000) {
            maybeSetText(this.mNowText);
            this.mUpdateTimeMillis = this.mTimeMillis + 60000 + 1;
            return;
        }
        if (abs < 3600000) {
            i = (int) (abs / 60000);
            android.content.res.Resources resources = getContext().getResources();
            if (z) {
                i5 = com.android.internal.R.string.duration_minutes_shortest;
            } else {
                i5 = com.android.internal.R.string.duration_minutes_shortest_future;
            }
            string = resources.getString(i5, java.lang.Integer.valueOf(i));
        } else {
            j = 86400000;
            if (abs < 86400000) {
                i = (int) (abs / 3600000);
                android.content.res.Resources resources2 = getContext().getResources();
                if (z) {
                    i4 = com.android.internal.R.string.duration_hours_shortest;
                } else {
                    i4 = com.android.internal.R.string.duration_hours_shortest_future;
                }
                string = resources2.getString(i4, java.lang.Integer.valueOf(i));
                j = 3600000;
            } else if (abs < 31449600000L) {
                java.time.LocalDateTime localDateTime = this.mLocalTime;
                java.time.ZoneId systemDefault = java.time.ZoneId.systemDefault();
                java.time.LocalDateTime localDateTime2 = toLocalDateTime(currentTimeMillis, systemDefault);
                int max = java.lang.Math.max(java.lang.Math.abs(dayDistance(localDateTime, localDateTime2)), 1);
                android.content.res.Resources resources3 = getContext().getResources();
                if (z) {
                    i3 = com.android.internal.R.string.duration_days_shortest;
                } else {
                    i3 = com.android.internal.R.string.duration_days_shortest_future;
                }
                java.lang.String string2 = resources3.getString(i3, java.lang.Integer.valueOf(max));
                if (z || max != 1) {
                    this.mUpdateTimeMillis = computeNextMidnight(localDateTime2, systemDefault);
                    j = -1;
                }
                i = max;
                string = string2;
            } else {
                i = (int) (abs / 31449600000L);
                android.content.res.Resources resources4 = getContext().getResources();
                if (z) {
                    i2 = com.android.internal.R.string.duration_years_shortest;
                } else {
                    i2 = com.android.internal.R.string.duration_years_shortest_future;
                }
                string = resources4.getString(i2, java.lang.Integer.valueOf(i));
                j = 31449600000L;
            }
        }
        if (j != -1) {
            if (z) {
                this.mUpdateTimeMillis = this.mTimeMillis + (j * (i + 1)) + 1;
            } else {
                this.mUpdateTimeMillis = (this.mTimeMillis - (j * i)) + 1;
            }
        }
        maybeSetText(string);
    }

    private void maybeSetText(java.lang.String str) {
        if (android.text.TextUtils.equals(getText(), str)) {
            return;
        }
        lambda$setTextAsync$0(str);
    }

    private static long computeNextMidnight(java.time.LocalDateTime localDateTime, java.time.ZoneId zoneId) {
        return toEpochMillis(java.time.LocalDateTime.of(localDateTime.toLocalDate().plusDays(1L), java.time.LocalTime.MIDNIGHT), zoneId);
    }

    @Override // android.widget.TextView, android.view.View
    protected void onConfigurationChanged(android.content.res.Configuration configuration) {
        super.onConfigurationChanged(configuration);
        updateNowText();
        update();
    }

    private void updateNowText() {
        if (!this.mShowRelativeTime) {
            return;
        }
        this.mNowText = getContext().getResources().getString(com.android.internal.R.string.now_string_shortest);
    }

    private static int dayDistance(java.time.LocalDateTime localDateTime, java.time.LocalDateTime localDateTime2) {
        return (int) (localDateTime2.getLong(java.time.temporal.JulianFields.JULIAN_DAY) - localDateTime.getLong(java.time.temporal.JulianFields.JULIAN_DAY));
    }

    private java.text.DateFormat getTimeFormat() {
        return android.text.format.DateFormat.getTimeFormat(getContext());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void clearFormatAndUpdate() {
        this.mLastFormat = null;
        update();
    }

    @Override // android.widget.TextView, android.view.View
    public void onInitializeAccessibilityNodeInfoInternal(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
        java.lang.String format;
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        if (this.mShowRelativeTime) {
            long currentTimeMillis = java.lang.System.currentTimeMillis();
            long abs = java.lang.Math.abs(currentTimeMillis - this.mTimeMillis);
            boolean z = currentTimeMillis >= this.mTimeMillis;
            java.util.HashMap hashMap = new java.util.HashMap();
            if (abs < 60000) {
                format = this.mNowText;
            } else if (abs < 3600000) {
                hashMap.put("count", java.lang.Integer.valueOf((int) (abs / 60000)));
                format = android.util.PluralsMessageFormatter.format(getContext().getResources(), hashMap, z ? com.android.internal.R.string.duration_minutes_relative : com.android.internal.R.string.duration_minutes_relative_future);
            } else if (abs < 86400000) {
                hashMap.put("count", java.lang.Integer.valueOf((int) (abs / 3600000)));
                format = android.util.PluralsMessageFormatter.format(getContext().getResources(), hashMap, z ? com.android.internal.R.string.duration_hours_relative : com.android.internal.R.string.duration_hours_relative_future);
            } else if (abs < 31449600000L) {
                hashMap.put("count", java.lang.Integer.valueOf(java.lang.Math.max(java.lang.Math.abs(dayDistance(this.mLocalTime, toLocalDateTime(currentTimeMillis, java.time.ZoneId.systemDefault()))), 1)));
                format = android.util.PluralsMessageFormatter.format(getContext().getResources(), hashMap, z ? com.android.internal.R.string.duration_days_relative : com.android.internal.R.string.duration_days_relative_future);
            } else {
                hashMap.put("count", java.lang.Integer.valueOf((int) (abs / 31449600000L)));
                format = android.util.PluralsMessageFormatter.format(getContext().getResources(), hashMap, z ? com.android.internal.R.string.duration_years_relative : com.android.internal.R.string.duration_years_relative_future);
            }
            accessibilityNodeInfo.setText(format);
        }
    }

    public static void setReceiverHandler(android.os.Handler handler) {
        android.widget.DateTimeView.ReceiverInfo receiverInfo = sReceiverInfo.get();
        if (receiverInfo == null) {
            receiverInfo = new android.widget.DateTimeView.ReceiverInfo();
            sReceiverInfo.set(receiverInfo);
        }
        receiverInfo.setHandler(handler);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class ReceiverInfo {
        private final java.util.ArrayList<android.widget.DateTimeView> mAttachedViews;
        private android.os.Handler mHandler;
        private final android.database.ContentObserver mObserver;
        private final android.content.BroadcastReceiver mReceiver;

        private ReceiverInfo() {
            this.mAttachedViews = new java.util.ArrayList<>();
            this.mReceiver = new android.content.BroadcastReceiver() { // from class: android.widget.DateTimeView.ReceiverInfo.1
                @Override // android.content.BroadcastReceiver
                public void onReceive(android.content.Context context, android.content.Intent intent) {
                    if (android.content.Intent.ACTION_TIME_TICK.equals(intent.getAction()) && java.lang.System.currentTimeMillis() < android.widget.DateTimeView.ReceiverInfo.this.getSoonestUpdateTime()) {
                        return;
                    }
                    android.widget.DateTimeView.ReceiverInfo.this.updateAll();
                }
            };
            this.mObserver = new android.database.ContentObserver(new android.os.Handler()) { // from class: android.widget.DateTimeView.ReceiverInfo.2
                @Override // android.database.ContentObserver
                public void onChange(boolean z) {
                    android.widget.DateTimeView.ReceiverInfo.this.updateAll();
                }
            };
            this.mHandler = new android.os.Handler();
        }

        public void addView(android.widget.DateTimeView dateTimeView) {
            synchronized (this.mAttachedViews) {
                boolean isEmpty = this.mAttachedViews.isEmpty();
                this.mAttachedViews.add(dateTimeView);
                if (isEmpty) {
                    register(getApplicationContextIfAvailable(dateTimeView.getContext()));
                }
            }
        }

        public void removeView(android.widget.DateTimeView dateTimeView) {
            synchronized (this.mAttachedViews) {
                if (this.mAttachedViews.remove(dateTimeView) && this.mAttachedViews.isEmpty()) {
                    unregister(getApplicationContextIfAvailable(dateTimeView.getContext()));
                }
            }
        }

        void updateAll() {
            synchronized (this.mAttachedViews) {
                int size = this.mAttachedViews.size();
                for (int i = 0; i < size; i++) {
                    final android.widget.DateTimeView dateTimeView = this.mAttachedViews.get(i);
                    dateTimeView.post(new java.lang.Runnable() { // from class: android.widget.DateTimeView$ReceiverInfo$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.widget.DateTimeView.this.clearFormatAndUpdate();
                        }
                    });
                }
            }
        }

        long getSoonestUpdateTime() {
            long j;
            synchronized (this.mAttachedViews) {
                int size = this.mAttachedViews.size();
                j = Long.MAX_VALUE;
                for (int i = 0; i < size; i++) {
                    long j2 = this.mAttachedViews.get(i).mUpdateTimeMillis;
                    if (j2 < j) {
                        j = j2;
                    }
                }
            }
            return j;
        }

        static final android.content.Context getApplicationContextIfAvailable(android.content.Context context) {
            android.content.Context applicationContext = context.getApplicationContext();
            return applicationContext != null ? applicationContext : android.app.ActivityThread.currentApplication().getApplicationContext();
        }

        void register(android.content.Context context) {
            android.content.IntentFilter intentFilter = new android.content.IntentFilter();
            intentFilter.addAction(android.content.Intent.ACTION_TIME_TICK);
            intentFilter.addAction(android.content.Intent.ACTION_TIME_CHANGED);
            intentFilter.addAction(android.content.Intent.ACTION_CONFIGURATION_CHANGED);
            intentFilter.addAction(android.content.Intent.ACTION_TIMEZONE_CHANGED);
            context.registerReceiver(this.mReceiver, intentFilter, null, this.mHandler);
        }

        void unregister(android.content.Context context) {
            context.unregisterReceiver(this.mReceiver);
        }

        public void setHandler(android.os.Handler handler) {
            this.mHandler = handler;
            synchronized (this.mAttachedViews) {
                if (!this.mAttachedViews.isEmpty()) {
                    unregister(this.mAttachedViews.get(0).getContext());
                    register(this.mAttachedViews.get(0).getContext());
                }
            }
        }
    }

    private static java.time.LocalDateTime toLocalDateTime(long j, java.time.ZoneId zoneId) {
        return java.time.LocalDateTime.ofInstant(java.time.Instant.ofEpochMilli(j), zoneId);
    }

    private static long toEpochMillis(java.time.LocalDateTime localDateTime, java.time.ZoneId zoneId) {
        return localDateTime.toInstant(zoneId.getRules().getOffset(localDateTime)).toEpochMilli();
    }
}
