package com.android.server.display;

/* loaded from: classes.dex */
public class BrightnessTracker {
    private static final java.lang.String AMBIENT_BRIGHTNESS_STATS_FILE = "ambient_brightness_stats.xml";
    private static final java.lang.String ATTR_BATTERY_LEVEL = "batteryLevel";
    private static final java.lang.String ATTR_COLOR_SAMPLE_DURATION = "colorSampleDuration";
    private static final java.lang.String ATTR_COLOR_TEMPERATURE = "colorTemperature";
    private static final java.lang.String ATTR_COLOR_VALUE_BUCKETS = "colorValueBuckets";
    private static final java.lang.String ATTR_DEFAULT_CONFIG = "defaultConfig";
    private static final java.lang.String ATTR_LAST_NITS = "lastNits";
    private static final java.lang.String ATTR_LUX = "lux";
    private static final java.lang.String ATTR_LUX_TIMESTAMPS = "luxTimestamps";
    private static final java.lang.String ATTR_NIGHT_MODE = "nightMode";
    private static final java.lang.String ATTR_NITS = "nits";
    private static final java.lang.String ATTR_PACKAGE_NAME = "packageName";
    private static final java.lang.String ATTR_POWER_SAVE = "powerSaveFactor";
    private static final java.lang.String ATTR_REDUCE_BRIGHT_COLORS = "reduceBrightColors";
    private static final java.lang.String ATTR_REDUCE_BRIGHT_COLORS_OFFSET = "reduceBrightColorsOffset";
    private static final java.lang.String ATTR_REDUCE_BRIGHT_COLORS_STRENGTH = "reduceBrightColorsStrength";
    private static final java.lang.String ATTR_TIMESTAMP = "timestamp";
    private static final java.lang.String ATTR_UNIQUE_DISPLAY_ID = "uniqueDisplayId";
    private static final java.lang.String ATTR_USER = "user";
    private static final java.lang.String ATTR_USER_POINT = "userPoint";
    private static final int COLOR_SAMPLE_COMPONENT_MASK = 4;
    private static final java.lang.String EVENTS_FILE = "brightness_events.xml";
    private static final int MAX_EVENTS = 100;
    private static final int MSG_BACKGROUND_START = 0;
    private static final int MSG_BRIGHTNESS_CHANGED = 1;
    private static final int MSG_SENSOR_CHANGED = 5;
    private static final int MSG_SHOULD_COLLECT_COLOR_SAMPLE_CHANGED = 4;
    private static final int MSG_START_SENSOR_LISTENER = 3;
    private static final int MSG_STOP_SENSOR_LISTENER = 2;
    private static final java.lang.String TAG_EVENT = "event";
    private static final java.lang.String TAG_EVENTS = "events";
    private com.android.server.display.AmbientBrightnessStatsTracker mAmbientBrightnessStatsTracker;
    private final android.os.Handler mBgHandler;
    private android.content.BroadcastReceiver mBroadcastReceiver;
    private boolean mColorSamplingEnabled;
    private final android.content.ContentResolver mContentResolver;
    private final android.content.Context mContext;
    private com.android.server.display.BrightnessTracker.DisplayListener mDisplayListener;

    @com.android.internal.annotations.GuardedBy({"mEventsLock"})
    private boolean mEventsDirty;
    private float mFrameRate;
    private final com.android.server.display.BrightnessTracker.Injector mInjector;
    private android.hardware.Sensor mLightSensor;
    private int mNoFramesToSample;
    private com.android.server.display.BrightnessTracker.SensorListener mSensorListener;
    private boolean mSensorRegistered;
    private com.android.server.display.BrightnessTracker.SettingsObserver mSettingsObserver;

    @com.android.internal.annotations.GuardedBy({"mDataCollectionLock"})
    private boolean mStarted;
    private final android.os.UserManager mUserManager;
    private volatile boolean mWriteBrightnessTrackerStateScheduled;
    static final java.lang.String TAG = "BrightnessTracker";
    static final boolean DEBUG = com.android.server.display.utils.DebugUtils.isDebuggable(TAG);
    private static final long MAX_EVENT_AGE = java.util.concurrent.TimeUnit.DAYS.toMillis(30);
    private static final java.text.SimpleDateFormat FORMAT = new java.text.SimpleDateFormat("MM-dd HH:mm:ss.SSS");
    private static final long COLOR_SAMPLE_DURATION = java.util.concurrent.TimeUnit.SECONDS.toSeconds(10);
    private final java.lang.Object mEventsLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mEventsLock"})
    private com.android.internal.util.RingBuffer<android.hardware.display.BrightnessChangeEvent> mEvents = new com.android.internal.util.RingBuffer<>(android.hardware.display.BrightnessChangeEvent.class, 100);
    private boolean mShouldCollectColorSample = false;
    private int mCurrentUserId = com.android.server.am.ProcessList.INVALID_ADJ;
    private final java.lang.Object mDataCollectionLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mDataCollectionLock"})
    private float mLastBatteryLevel = Float.NaN;

    @com.android.internal.annotations.GuardedBy({"mDataCollectionLock"})
    private float mLastBrightness = -1.0f;

    public BrightnessTracker(android.content.Context context, @android.annotation.Nullable com.android.server.display.BrightnessTracker.Injector injector) {
        this.mContext = context;
        this.mContentResolver = context.getContentResolver();
        if (injector != null) {
            this.mInjector = injector;
        } else {
            this.mInjector = new com.android.server.display.BrightnessTracker.Injector();
        }
        this.mBgHandler = new com.android.server.display.BrightnessTracker.TrackerHandler(this.mInjector.getBackgroundHandler().getLooper());
        this.mUserManager = (android.os.UserManager) this.mContext.getSystemService(android.os.UserManager.class);
    }

    public void start(float f) {
        if (DEBUG) {
            android.util.Slog.d(TAG, "Start");
        }
        this.mCurrentUserId = android.app.ActivityManager.getCurrentUser();
        this.mBgHandler.obtainMessage(0, java.lang.Float.valueOf(f)).sendToTarget();
    }

    public void setShouldCollectColorSample(boolean z) {
        this.mBgHandler.obtainMessage(4, java.lang.Boolean.valueOf(z)).sendToTarget();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void backgroundStart(float f) {
        synchronized (this.mDataCollectionLock) {
            try {
                if (this.mStarted) {
                    return;
                }
                if (DEBUG) {
                    android.util.Slog.d(TAG, "Background start");
                }
                readEvents();
                readAmbientBrightnessStats();
                this.mSensorListener = new com.android.server.display.BrightnessTracker.SensorListener();
                this.mSettingsObserver = new com.android.server.display.BrightnessTracker.SettingsObserver(this.mBgHandler);
                this.mInjector.registerBrightnessModeObserver(this.mContentResolver, this.mSettingsObserver);
                startSensorListener();
                android.content.IntentFilter intentFilter = new android.content.IntentFilter();
                intentFilter.addAction("android.intent.action.ACTION_SHUTDOWN");
                intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
                intentFilter.addAction("android.intent.action.SCREEN_ON");
                intentFilter.addAction("android.intent.action.SCREEN_OFF");
                this.mBroadcastReceiver = new com.android.server.display.BrightnessTracker.Receiver();
                this.mInjector.registerReceiver(this.mContext, this.mBroadcastReceiver, intentFilter);
                this.mInjector.scheduleIdleJob(this.mContext);
                synchronized (this.mDataCollectionLock) {
                    this.mLastBrightness = f;
                    this.mStarted = true;
                }
                enableColorSampling();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void stop() {
        synchronized (this.mDataCollectionLock) {
            try {
                if (this.mStarted) {
                    if (DEBUG) {
                        android.util.Slog.d(TAG, "Stop");
                    }
                    this.mBgHandler.removeMessages(0);
                    stopSensorListener();
                    this.mInjector.unregisterSensorListener(this.mContext, this.mSensorListener);
                    this.mInjector.unregisterBrightnessModeObserver(this.mContext, this.mSettingsObserver);
                    this.mInjector.unregisterReceiver(this.mContext, this.mBroadcastReceiver);
                    this.mInjector.cancelIdleJob(this.mContext);
                    synchronized (this.mDataCollectionLock) {
                        this.mStarted = false;
                    }
                    disableColorSampling();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void onSwitchUser(int i) {
        if (DEBUG) {
            android.util.Slog.d(TAG, "Used id updated from " + this.mCurrentUserId + " to " + i);
        }
        this.mCurrentUserId = i;
    }

    public android.content.pm.ParceledListSlice<android.hardware.display.BrightnessChangeEvent> getEvents(int i, boolean z) {
        android.hardware.display.BrightnessChangeEvent[] brightnessChangeEventArr;
        synchronized (this.mEventsLock) {
            brightnessChangeEventArr = (android.hardware.display.BrightnessChangeEvent[]) this.mEvents.toArray();
        }
        int[] profileIds = this.mInjector.getProfileIds(this.mUserManager, i);
        java.util.HashMap hashMap = new java.util.HashMap();
        int i2 = 0;
        while (true) {
            boolean z2 = true;
            if (i2 >= profileIds.length) {
                break;
            }
            int i3 = profileIds[i2];
            if (z && i3 == i) {
                z2 = false;
            }
            hashMap.put(java.lang.Integer.valueOf(profileIds[i2]), java.lang.Boolean.valueOf(z2));
            i2++;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList(brightnessChangeEventArr.length);
        for (int i4 = 0; i4 < brightnessChangeEventArr.length; i4++) {
            java.lang.Boolean bool = (java.lang.Boolean) hashMap.get(java.lang.Integer.valueOf(brightnessChangeEventArr[i4].userId));
            if (bool != null) {
                if (!bool.booleanValue()) {
                    arrayList.add(brightnessChangeEventArr[i4]);
                } else {
                    arrayList.add(new android.hardware.display.BrightnessChangeEvent(brightnessChangeEventArr[i4], true));
                }
            }
        }
        return new android.content.pm.ParceledListSlice<>(arrayList);
    }

    public void persistBrightnessTrackerState() {
        scheduleWriteBrightnessTrackerState();
    }

    public void notifyBrightnessChanged(float f, boolean z, float f2, boolean z2, boolean z3, java.lang.String str, float[] fArr, long[] jArr) {
        if (DEBUG) {
            android.util.Slog.d(TAG, java.lang.String.format("notifyBrightnessChanged(brightness=%f, userInitiated=%b)", java.lang.Float.valueOf(f), java.lang.Boolean.valueOf(z)));
        }
        this.mBgHandler.obtainMessage(1, z ? 1 : 0, 0, new com.android.server.display.BrightnessTracker.BrightnessChangeValues(f, f2, z2, z3, this.mInjector.currentTimeMillis(), str, fArr, jArr)).sendToTarget();
    }

    public void setLightSensor(android.hardware.Sensor sensor) {
        this.mBgHandler.obtainMessage(5, 0, 0, sensor).sendToTarget();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleBrightnessChanged(float f, boolean z, float f2, boolean z2, boolean z3, long j, java.lang.String str, float[] fArr, long[] jArr) {
        android.hardware.display.DisplayedContentSample sampleColor;
        synchronized (this.mDataCollectionLock) {
            try {
                if (this.mStarted) {
                    float f3 = this.mLastBrightness;
                    this.mLastBrightness = f;
                    if (z) {
                        android.hardware.display.BrightnessChangeEvent.Builder builder = new android.hardware.display.BrightnessChangeEvent.Builder();
                        builder.setBrightness(f);
                        builder.setTimeStamp(j);
                        builder.setPowerBrightnessFactor(f2);
                        builder.setUserBrightnessPoint(z2);
                        builder.setIsDefaultBrightnessConfig(z3);
                        builder.setUniqueDisplayId(str);
                        if (fArr.length == 0) {
                            return;
                        }
                        long[] jArr2 = new long[jArr.length];
                        long currentTimeMillis = this.mInjector.currentTimeMillis();
                        long elapsedRealtimeNanos = this.mInjector.elapsedRealtimeNanos();
                        for (int i = 0; i < jArr.length; i++) {
                            jArr2[i] = currentTimeMillis - (java.util.concurrent.TimeUnit.NANOSECONDS.toMillis(elapsedRealtimeNanos) - jArr[i]);
                        }
                        builder.setLuxValues(fArr);
                        builder.setLuxTimestamps(jArr2);
                        builder.setBatteryLevel(this.mLastBatteryLevel);
                        builder.setLastBrightness(f3);
                        try {
                            android.app.ActivityTaskManager.RootTaskInfo focusedStack = this.mInjector.getFocusedStack();
                            if (focusedStack != null && focusedStack.topActivity != null) {
                                builder.setUserId(focusedStack.userId);
                                builder.setPackageName(focusedStack.topActivity.getPackageName());
                                builder.setNightMode(this.mInjector.isNightDisplayActivated(this.mContext));
                                builder.setColorTemperature(this.mInjector.getNightDisplayColorTemperature(this.mContext));
                                builder.setReduceBrightColors(this.mInjector.isReduceBrightColorsActivated(this.mContext));
                                builder.setReduceBrightColorsStrength(this.mInjector.getReduceBrightColorsStrength(this.mContext));
                                builder.setReduceBrightColorsOffset(this.mInjector.getReduceBrightColorsOffsetFactor(this.mContext) * f);
                                if (this.mColorSamplingEnabled && (sampleColor = this.mInjector.sampleColor(this.mNoFramesToSample)) != null && sampleColor.getSampleComponent(android.hardware.display.DisplayedContentSample.ColorComponent.CHANNEL2) != null) {
                                    builder.setColorValues(sampleColor.getSampleComponent(android.hardware.display.DisplayedContentSample.ColorComponent.CHANNEL2), java.lang.Math.round((sampleColor.getNumFrames() / this.mFrameRate) * 1000.0f));
                                }
                                android.hardware.display.BrightnessChangeEvent build = builder.build();
                                if (DEBUG) {
                                    android.util.Slog.d(TAG, "Event: " + build.toString());
                                }
                                synchronized (this.mEventsLock) {
                                    this.mEventsDirty = true;
                                    this.mEvents.append(build);
                                }
                                return;
                            }
                            if (DEBUG) {
                                android.util.Slog.d(TAG, "Ignoring event due to null focusedTask.");
                            }
                        } catch (android.os.RemoteException e) {
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleSensorChanged(android.hardware.Sensor sensor) {
        if (this.mLightSensor != sensor) {
            this.mLightSensor = sensor;
            stopSensorListener();
            startSensorListener();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startSensorListener() {
        if (!this.mSensorRegistered && this.mLightSensor != null && this.mAmbientBrightnessStatsTracker != null && this.mInjector.isInteractive(this.mContext) && this.mInjector.isBrightnessModeAutomatic(this.mContentResolver)) {
            this.mAmbientBrightnessStatsTracker.start();
            this.mSensorRegistered = true;
            this.mInjector.registerSensorListener(this.mContext, this.mSensorListener, this.mLightSensor, this.mInjector.getBackgroundHandler());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopSensorListener() {
        if (this.mSensorRegistered) {
            this.mAmbientBrightnessStatsTracker.stop();
            this.mInjector.unregisterSensorListener(this.mContext, this.mSensorListener);
            this.mSensorRegistered = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void scheduleWriteBrightnessTrackerState() {
        if (!this.mWriteBrightnessTrackerStateScheduled) {
            this.mBgHandler.post(new java.lang.Runnable() { // from class: com.android.server.display.BrightnessTracker$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.display.BrightnessTracker.this.lambda$scheduleWriteBrightnessTrackerState$0();
                }
            });
            this.mWriteBrightnessTrackerStateScheduled = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleWriteBrightnessTrackerState$0() {
        this.mWriteBrightnessTrackerStateScheduled = false;
        writeEvents();
        writeAmbientBrightnessStats();
    }

    private void writeEvents() {
        java.io.FileOutputStream fileOutputStream;
        synchronized (this.mEventsLock) {
            try {
                if (this.mEventsDirty) {
                    android.util.AtomicFile file = this.mInjector.getFile(EVENTS_FILE);
                    if (file == null) {
                        return;
                    }
                    if (this.mEvents.isEmpty()) {
                        if (file.exists()) {
                            file.delete();
                        }
                        this.mEventsDirty = false;
                    } else {
                        try {
                            fileOutputStream = file.startWrite();
                        } catch (java.io.IOException e) {
                            e = e;
                            fileOutputStream = null;
                        }
                        try {
                            writeEventsLocked(fileOutputStream);
                            file.finishWrite(fileOutputStream);
                            this.mEventsDirty = false;
                        } catch (java.io.IOException e2) {
                            e = e2;
                            file.failWrite(fileOutputStream);
                            android.util.Slog.e(TAG, "Failed to write change mEvents.", e);
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void writeAmbientBrightnessStats() {
        java.io.FileOutputStream fileOutputStream;
        android.util.AtomicFile file = this.mInjector.getFile(AMBIENT_BRIGHTNESS_STATS_FILE);
        if (file == null) {
            return;
        }
        try {
            fileOutputStream = file.startWrite();
        } catch (java.io.IOException e) {
            e = e;
            fileOutputStream = null;
        }
        try {
            this.mAmbientBrightnessStatsTracker.writeStats(fileOutputStream);
            file.finishWrite(fileOutputStream);
        } catch (java.io.IOException e2) {
            e = e2;
            file.failWrite(fileOutputStream);
            android.util.Slog.e(TAG, "Failed to write ambient brightness stats.", e);
        }
    }

    private android.util.AtomicFile getFileWithLegacyFallback(java.lang.String str) {
        android.util.AtomicFile legacyFile;
        android.util.AtomicFile file = this.mInjector.getFile(str);
        if (file != null && !file.exists() && (legacyFile = this.mInjector.getLegacyFile(str)) != null && legacyFile.exists()) {
            android.util.Slog.i(TAG, "Reading " + str + " from old location");
            return legacyFile;
        }
        return file;
    }

    private void readEvents() {
        synchronized (this.mEventsLock) {
            this.mEventsDirty = true;
            this.mEvents.clear();
            android.util.AtomicFile fileWithLegacyFallback = getFileWithLegacyFallback(EVENTS_FILE);
            if (fileWithLegacyFallback != null && fileWithLegacyFallback.exists()) {
                java.io.FileInputStream fileInputStream = null;
                try {
                    try {
                        fileInputStream = fileWithLegacyFallback.openRead();
                        readEventsLocked(fileInputStream);
                    } catch (java.io.IOException e) {
                        fileWithLegacyFallback.delete();
                        android.util.Slog.e(TAG, "Failed to read change mEvents.", e);
                    }
                } finally {
                    libcore.io.IoUtils.closeQuietly(fileInputStream);
                }
            }
        }
    }

    private void readAmbientBrightnessStats() {
        java.io.FileInputStream fileInputStream = null;
        this.mAmbientBrightnessStatsTracker = new com.android.server.display.AmbientBrightnessStatsTracker(this.mUserManager, null);
        android.util.AtomicFile fileWithLegacyFallback = getFileWithLegacyFallback(AMBIENT_BRIGHTNESS_STATS_FILE);
        if (fileWithLegacyFallback != null && fileWithLegacyFallback.exists()) {
            try {
                try {
                    fileInputStream = fileWithLegacyFallback.openRead();
                    this.mAmbientBrightnessStatsTracker.readStats(fileInputStream);
                } catch (java.io.IOException e) {
                    fileWithLegacyFallback.delete();
                    android.util.Slog.e(TAG, "Failed to read ambient brightness stats.", e);
                }
            } finally {
                libcore.io.IoUtils.closeQuietly(fileInputStream);
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mEventsLock"})
    @com.android.internal.annotations.VisibleForTesting
    void writeEventsLocked(java.io.OutputStream outputStream) throws java.io.IOException {
        com.android.modules.utils.TypedXmlSerializer resolveSerializer = android.util.Xml.resolveSerializer(outputStream);
        resolveSerializer.startDocument((java.lang.String) null, true);
        resolveSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
        resolveSerializer.startTag((java.lang.String) null, TAG_EVENTS);
        android.hardware.display.BrightnessChangeEvent[] brightnessChangeEventArr = (android.hardware.display.BrightnessChangeEvent[]) this.mEvents.toArray();
        this.mEvents.clear();
        if (DEBUG) {
            android.util.Slog.d(TAG, "Writing events " + brightnessChangeEventArr.length);
        }
        long currentTimeMillis = this.mInjector.currentTimeMillis() - MAX_EVENT_AGE;
        for (int i = 0; i < brightnessChangeEventArr.length; i++) {
            int userSerialNumber = this.mInjector.getUserSerialNumber(this.mUserManager, brightnessChangeEventArr[i].userId);
            if (userSerialNumber != -1 && brightnessChangeEventArr[i].timeStamp > currentTimeMillis) {
                this.mEvents.append(brightnessChangeEventArr[i]);
                resolveSerializer.startTag((java.lang.String) null, TAG_EVENT);
                resolveSerializer.attributeFloat((java.lang.String) null, ATTR_NITS, brightnessChangeEventArr[i].brightness);
                resolveSerializer.attributeLong((java.lang.String) null, "timestamp", brightnessChangeEventArr[i].timeStamp);
                resolveSerializer.attribute((java.lang.String) null, "packageName", brightnessChangeEventArr[i].packageName);
                resolveSerializer.attributeInt((java.lang.String) null, ATTR_USER, userSerialNumber);
                java.lang.String str = brightnessChangeEventArr[i].uniqueDisplayId;
                if (str == null) {
                    str = "";
                }
                resolveSerializer.attribute((java.lang.String) null, ATTR_UNIQUE_DISPLAY_ID, str);
                resolveSerializer.attributeFloat((java.lang.String) null, ATTR_BATTERY_LEVEL, brightnessChangeEventArr[i].batteryLevel);
                resolveSerializer.attributeBoolean((java.lang.String) null, ATTR_NIGHT_MODE, brightnessChangeEventArr[i].nightMode);
                resolveSerializer.attributeInt((java.lang.String) null, ATTR_COLOR_TEMPERATURE, brightnessChangeEventArr[i].colorTemperature);
                resolveSerializer.attributeBoolean((java.lang.String) null, ATTR_REDUCE_BRIGHT_COLORS, brightnessChangeEventArr[i].reduceBrightColors);
                resolveSerializer.attributeInt((java.lang.String) null, ATTR_REDUCE_BRIGHT_COLORS_STRENGTH, brightnessChangeEventArr[i].reduceBrightColorsStrength);
                resolveSerializer.attributeFloat((java.lang.String) null, ATTR_REDUCE_BRIGHT_COLORS_OFFSET, brightnessChangeEventArr[i].reduceBrightColorsOffset);
                resolveSerializer.attributeFloat((java.lang.String) null, ATTR_LAST_NITS, brightnessChangeEventArr[i].lastBrightness);
                resolveSerializer.attributeBoolean((java.lang.String) null, ATTR_DEFAULT_CONFIG, brightnessChangeEventArr[i].isDefaultBrightnessConfig);
                resolveSerializer.attributeFloat((java.lang.String) null, ATTR_POWER_SAVE, brightnessChangeEventArr[i].powerBrightnessFactor);
                resolveSerializer.attributeBoolean((java.lang.String) null, ATTR_USER_POINT, brightnessChangeEventArr[i].isUserSetBrightness);
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                java.lang.StringBuilder sb2 = new java.lang.StringBuilder();
                for (int i2 = 0; i2 < brightnessChangeEventArr[i].luxValues.length; i2++) {
                    if (i2 > 0) {
                        sb.append(',');
                        sb2.append(',');
                    }
                    sb.append(java.lang.Float.toString(brightnessChangeEventArr[i].luxValues[i2]));
                    sb2.append(java.lang.Long.toString(brightnessChangeEventArr[i].luxTimestamps[i2]));
                }
                resolveSerializer.attribute((java.lang.String) null, ATTR_LUX, sb.toString());
                resolveSerializer.attribute((java.lang.String) null, ATTR_LUX_TIMESTAMPS, sb2.toString());
                if (brightnessChangeEventArr[i].colorValueBuckets != null && brightnessChangeEventArr[i].colorValueBuckets.length > 0) {
                    resolveSerializer.attributeLong((java.lang.String) null, ATTR_COLOR_SAMPLE_DURATION, brightnessChangeEventArr[i].colorSampleDuration);
                    java.lang.StringBuilder sb3 = new java.lang.StringBuilder();
                    for (int i3 = 0; i3 < brightnessChangeEventArr[i].colorValueBuckets.length; i3++) {
                        if (i3 > 0) {
                            sb3.append(',');
                        }
                        sb3.append(java.lang.Long.toString(brightnessChangeEventArr[i].colorValueBuckets[i3]));
                    }
                    resolveSerializer.attribute((java.lang.String) null, ATTR_COLOR_VALUE_BUCKETS, sb3.toString());
                }
                resolveSerializer.endTag((java.lang.String) null, TAG_EVENT);
            }
        }
        resolveSerializer.endTag((java.lang.String) null, TAG_EVENTS);
        resolveSerializer.endDocument();
        outputStream.flush();
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x0047, code lost:
    
        if (r8 != 4) goto L68;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0054, code lost:
    
        if (com.android.server.display.BrightnessTracker.TAG_EVENT.equals(r3.getName()) == false) goto L71;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x01a8, code lost:
    
        r5 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0056, code lost:
    
        r8 = new android.hardware.display.BrightnessChangeEvent.Builder();
        r8.setBrightness(r3.getAttributeFloat((java.lang.String) null, com.android.server.display.BrightnessTracker.ATTR_NITS));
        r8.setTimeStamp(r3.getAttributeLong((java.lang.String) null, "timestamp"));
        r8.setPackageName(r3.getAttributeValue((java.lang.String) null, "packageName"));
        r8.setUserId(r18.mInjector.getUserId(r18.mUserManager, r3.getAttributeInt((java.lang.String) null, com.android.server.display.BrightnessTracker.ATTR_USER)));
        r9 = r3.getAttributeValue((java.lang.String) null, com.android.server.display.BrightnessTracker.ATTR_UNIQUE_DISPLAY_ID);
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0093, code lost:
    
        if (r9 != null) goto L31;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0095, code lost:
    
        r9 = "";
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x0097, code lost:
    
        r8.setUniqueDisplayId(r9);
        r8.setBatteryLevel(r3.getAttributeFloat((java.lang.String) null, com.android.server.display.BrightnessTracker.ATTR_BATTERY_LEVEL));
        r8.setNightMode(r3.getAttributeBoolean((java.lang.String) null, com.android.server.display.BrightnessTracker.ATTR_NIGHT_MODE));
        r8.setColorTemperature(r3.getAttributeInt((java.lang.String) null, com.android.server.display.BrightnessTracker.ATTR_COLOR_TEMPERATURE));
        r8.setReduceBrightColors(r3.getAttributeBoolean((java.lang.String) null, com.android.server.display.BrightnessTracker.ATTR_REDUCE_BRIGHT_COLORS));
        r8.setReduceBrightColorsStrength(r3.getAttributeInt((java.lang.String) null, com.android.server.display.BrightnessTracker.ATTR_REDUCE_BRIGHT_COLORS_STRENGTH));
        r8.setReduceBrightColorsOffset(r3.getAttributeFloat((java.lang.String) null, com.android.server.display.BrightnessTracker.ATTR_REDUCE_BRIGHT_COLORS_OFFSET));
        r8.setLastBrightness(r3.getAttributeFloat((java.lang.String) null, com.android.server.display.BrightnessTracker.ATTR_LAST_NITS));
        r9 = r3.getAttributeValue((java.lang.String) null, com.android.server.display.BrightnessTracker.ATTR_LUX);
        r11 = r3.getAttributeValue((java.lang.String) null, com.android.server.display.BrightnessTracker.ATTR_LUX_TIMESTAMPS);
        r9 = r9.split(",");
        r11 = r11.split(",");
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00f6, code lost:
    
        if (r9.length == r11.length) goto L72;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00fa, code lost:
    
        r12 = r9.length;
        r13 = new float[r12];
        r14 = new long[r9.length];
        r15 = 0;
        r5 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0102, code lost:
    
        if (r5 >= r12) goto L82;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x0104, code lost:
    
        r13[r5] = java.lang.Float.parseFloat(r9[r5]);
        r14[r5] = java.lang.Long.parseLong(r11[r5]);
        r5 = r5 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0117, code lost:
    
        r8.setLuxValues(r13);
        r8.setLuxTimestamps(r14);
        r8.setIsDefaultBrightnessConfig(r3.getAttributeBoolean((java.lang.String) null, com.android.server.display.BrightnessTracker.ATTR_DEFAULT_CONFIG, false));
        r8.setPowerBrightnessFactor(r3.getAttributeFloat((java.lang.String) null, com.android.server.display.BrightnessTracker.ATTR_POWER_SAVE, 1.0f));
        r8.setUserBrightnessPoint(r3.getAttributeBoolean((java.lang.String) null, com.android.server.display.BrightnessTracker.ATTR_USER_POINT, false));
        r13 = r3.getAttributeLong((java.lang.String) null, com.android.server.display.BrightnessTracker.ATTR_COLOR_SAMPLE_DURATION, -1);
        r5 = r3.getAttributeValue((java.lang.String) null, com.android.server.display.BrightnessTracker.ATTR_COLOR_VALUE_BUCKETS);
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x014c, code lost:
    
        if (r13 == (-1)) goto L44;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x014e, code lost:
    
        if (r5 == null) goto L44;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x0150, code lost:
    
        r5 = r5.split(",");
        r9 = r5.length;
        r10 = new long[r9];
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0158, code lost:
    
        if (r15 >= r9) goto L83;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x015a, code lost:
    
        r10[r15] = java.lang.Long.parseLong(r5[r15]);
        r15 = r15 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0165, code lost:
    
        r8.setColorValues(r10, r13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x0168, code lost:
    
        r5 = r8.build();
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x016e, code lost:
    
        if (com.android.server.display.BrightnessTracker.DEBUG == false) goto L47;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x0170, code lost:
    
        android.util.Slog.i(com.android.server.display.BrightnessTracker.TAG, "Read event " + r5.brightness + " " + r5.packageName);
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0193, code lost:
    
        if (r5.userId == (-1)) goto L54;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x0199, code lost:
    
        if (r5.timeStamp <= r6) goto L54;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x019e, code lost:
    
        if (r5.luxValues.length <= 0) goto L54;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x01a0, code lost:
    
        r18.mEvents.append(r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x01a5, code lost:
    
        r5 = 1;
     */
    @com.android.internal.annotations.GuardedBy({"mEventsLock"})
    @com.android.internal.annotations.VisibleForTesting
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void readEventsLocked(java.io.InputStream inputStream) throws java.io.IOException {
        int next;
        int i;
        try {
            com.android.modules.utils.TypedXmlPullParser resolvePullParser = android.util.Xml.resolvePullParser(inputStream);
            do {
                next = resolvePullParser.next();
                i = 1;
                if (next == 1) {
                    break;
                }
            } while (next != 2);
            java.lang.String name = resolvePullParser.getName();
            if (!TAG_EVENTS.equals(name)) {
                throw new org.xmlpull.v1.XmlPullParserException("Events not found in brightness tracker file " + name);
            }
            long currentTimeMillis = this.mInjector.currentTimeMillis() - MAX_EVENT_AGE;
            int depth = resolvePullParser.getDepth();
            while (true) {
                int next2 = resolvePullParser.next();
                if (next2 != i) {
                    if (next2 == 3 && resolvePullParser.getDepth() <= depth) {
                        return;
                    }
                    i = 1;
                } else {
                    return;
                }
            }
        } catch (java.io.IOException | java.lang.NullPointerException | java.lang.NumberFormatException | org.xmlpull.v1.XmlPullParserException e) {
            this.mEvents = new com.android.internal.util.RingBuffer<>(android.hardware.display.BrightnessChangeEvent.class, 100);
            android.util.Slog.e(TAG, "Failed to parse brightness event", e);
            throw new java.io.IOException("failed to parse file", e);
        }
    }

    public void dump(final java.io.PrintWriter printWriter) {
        printWriter.println("BrightnessTracker state:");
        synchronized (this.mDataCollectionLock) {
            printWriter.println("  mStarted=" + this.mStarted);
            printWriter.println("  mLightSensor=" + this.mLightSensor);
            printWriter.println("  mLastBatteryLevel=" + this.mLastBatteryLevel);
            printWriter.println("  mLastBrightness=" + this.mLastBrightness);
        }
        synchronized (this.mEventsLock) {
            try {
                printWriter.println("  mEventsDirty=" + this.mEventsDirty);
                printWriter.println("  mEvents.size=" + this.mEvents.size());
                android.hardware.display.BrightnessChangeEvent[] brightnessChangeEventArr = (android.hardware.display.BrightnessChangeEvent[]) this.mEvents.toArray();
                for (int i = 0; i < brightnessChangeEventArr.length; i++) {
                    printWriter.print("    " + FORMAT.format(new java.util.Date(brightnessChangeEventArr[i].timeStamp)));
                    printWriter.print(", userId=" + brightnessChangeEventArr[i].userId);
                    printWriter.print(", " + brightnessChangeEventArr[i].lastBrightness + "->" + brightnessChangeEventArr[i].brightness);
                    java.lang.StringBuilder sb = new java.lang.StringBuilder();
                    sb.append(", isUserSetBrightness=");
                    sb.append(brightnessChangeEventArr[i].isUserSetBrightness);
                    printWriter.print(sb.toString());
                    printWriter.print(", powerBrightnessFactor=" + brightnessChangeEventArr[i].powerBrightnessFactor);
                    printWriter.print(", isDefaultBrightnessConfig=" + brightnessChangeEventArr[i].isDefaultBrightnessConfig);
                    printWriter.print(", recent lux values=");
                    printWriter.print(" {");
                    for (int i2 = 0; i2 < brightnessChangeEventArr[i].luxValues.length; i2++) {
                        if (i2 != 0) {
                            printWriter.print(", ");
                        }
                        printWriter.print("(" + brightnessChangeEventArr[i].luxValues[i2] + "," + brightnessChangeEventArr[i].luxTimestamps[i2] + ")");
                    }
                    printWriter.println("}");
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        printWriter.println("  mWriteBrightnessTrackerStateScheduled=" + this.mWriteBrightnessTrackerStateScheduled);
        this.mBgHandler.runWithScissors(new java.lang.Runnable() { // from class: com.android.server.display.BrightnessTracker$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.display.BrightnessTracker.this.lambda$dump$1(printWriter);
            }
        }, 1000L);
        if (this.mAmbientBrightnessStatsTracker != null) {
            printWriter.println();
            this.mAmbientBrightnessStatsTracker.dump(printWriter);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: dumpLocal, reason: merged with bridge method [inline-methods] */
    public void lambda$dump$1(java.io.PrintWriter printWriter) {
        printWriter.println("  mSensorRegistered=" + this.mSensorRegistered);
        printWriter.println("  mColorSamplingEnabled=" + this.mColorSamplingEnabled);
        printWriter.println("  mNoFramesToSample=" + this.mNoFramesToSample);
        printWriter.println("  mFrameRate=" + this.mFrameRate);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enableColorSampling() {
        if (!this.mInjector.isBrightnessModeAutomatic(this.mContentResolver) || !this.mInjector.isInteractive(this.mContext) || this.mColorSamplingEnabled || !this.mShouldCollectColorSample) {
            return;
        }
        this.mFrameRate = this.mInjector.getFrameRate(this.mContext);
        if (this.mFrameRate <= com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
            android.util.Slog.wtf(TAG, "Default display has a zero or negative framerate.");
            return;
        }
        this.mNoFramesToSample = (int) (this.mFrameRate * COLOR_SAMPLE_DURATION);
        android.hardware.display.DisplayedContentSamplingAttributes samplingAttributes = this.mInjector.getSamplingAttributes();
        if (DEBUG && samplingAttributes != null) {
            android.util.Slog.d(TAG, "Color sampling mask=0x" + java.lang.Integer.toHexString(samplingAttributes.getComponentMask()) + " dataSpace=0x" + java.lang.Integer.toHexString(samplingAttributes.getDataspace()) + " pixelFormat=0x" + java.lang.Integer.toHexString(samplingAttributes.getPixelFormat()));
        }
        if (samplingAttributes != null && samplingAttributes.getPixelFormat() == 55 && (samplingAttributes.getComponentMask() & 4) != 0) {
            this.mColorSamplingEnabled = this.mInjector.enableColorSampling(true, this.mNoFramesToSample);
            if (DEBUG) {
                android.util.Slog.i(TAG, "turning on color sampling for " + this.mNoFramesToSample + " frames, success=" + this.mColorSamplingEnabled);
            }
        }
        if (this.mColorSamplingEnabled && this.mDisplayListener == null) {
            this.mDisplayListener = new com.android.server.display.BrightnessTracker.DisplayListener();
            this.mInjector.registerDisplayListener(this.mContext, this.mDisplayListener, this.mBgHandler);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void disableColorSampling() {
        if (!this.mColorSamplingEnabled) {
            return;
        }
        this.mInjector.enableColorSampling(false, 0);
        this.mColorSamplingEnabled = false;
        if (this.mDisplayListener != null) {
            this.mInjector.unRegisterDisplayListener(this.mContext, this.mDisplayListener);
            this.mDisplayListener = null;
        }
        if (DEBUG) {
            android.util.Slog.i(TAG, "turning off color sampling");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateColorSampling() {
        if (this.mColorSamplingEnabled && this.mInjector.getFrameRate(this.mContext) != this.mFrameRate) {
            disableColorSampling();
            enableColorSampling();
        }
    }

    public android.content.pm.ParceledListSlice<android.hardware.display.AmbientBrightnessDayStats> getAmbientBrightnessStats(int i) {
        java.util.ArrayList<android.hardware.display.AmbientBrightnessDayStats> userStats;
        if (this.mAmbientBrightnessStatsTracker != null && (userStats = this.mAmbientBrightnessStatsTracker.getUserStats(i)) != null) {
            return new android.content.pm.ParceledListSlice<>(userStats);
        }
        return android.content.pm.ParceledListSlice.emptyList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void recordAmbientBrightnessStats(android.hardware.SensorEvent sensorEvent) {
        this.mAmbientBrightnessStatsTracker.add(this.mCurrentUserId, sensorEvent.values[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void batteryLevelChanged(int i, int i2) {
        synchronized (this.mDataCollectionLock) {
            this.mLastBatteryLevel = i / i2;
        }
    }

    private final class SensorListener implements android.hardware.SensorEventListener {
        private SensorListener() {
        }

        @Override // android.hardware.SensorEventListener
        public void onSensorChanged(android.hardware.SensorEvent sensorEvent) {
            com.android.server.display.BrightnessTracker.this.recordAmbientBrightnessStats(sensorEvent);
        }

        @Override // android.hardware.SensorEventListener
        public void onAccuracyChanged(android.hardware.Sensor sensor, int i) {
        }
    }

    private final class DisplayListener implements android.hardware.display.DisplayManager.DisplayListener {
        private DisplayListener() {
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayAdded(int i) {
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayRemoved(int i) {
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayChanged(int i) {
            if (i == 0) {
                com.android.server.display.BrightnessTracker.this.updateColorSampling();
            }
        }
    }

    private final class SettingsObserver extends android.database.ContentObserver {
        public SettingsObserver(android.os.Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, android.net.Uri uri) {
            if (com.android.server.display.BrightnessTracker.DEBUG) {
                android.util.Slog.v(com.android.server.display.BrightnessTracker.TAG, "settings change " + uri);
            }
            if (com.android.server.display.BrightnessTracker.this.mInjector.isBrightnessModeAutomatic(com.android.server.display.BrightnessTracker.this.mContentResolver)) {
                com.android.server.display.BrightnessTracker.this.mBgHandler.obtainMessage(3).sendToTarget();
            } else {
                com.android.server.display.BrightnessTracker.this.mBgHandler.obtainMessage(2).sendToTarget();
            }
        }
    }

    private final class Receiver extends android.content.BroadcastReceiver {
        private Receiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            if (com.android.server.display.BrightnessTracker.DEBUG) {
                android.util.Slog.d(com.android.server.display.BrightnessTracker.TAG, "Received " + intent.getAction());
            }
            java.lang.String action = intent.getAction();
            if ("android.intent.action.ACTION_SHUTDOWN".equals(action)) {
                com.android.server.display.BrightnessTracker.this.stop();
                com.android.server.display.BrightnessTracker.this.scheduleWriteBrightnessTrackerState();
                return;
            }
            if ("android.intent.action.BATTERY_CHANGED".equals(action)) {
                int intExtra = intent.getIntExtra("level", -1);
                int intExtra2 = intent.getIntExtra("scale", 0);
                if (intExtra != -1 && intExtra2 != 0) {
                    com.android.server.display.BrightnessTracker.this.batteryLevelChanged(intExtra, intExtra2);
                    return;
                }
                return;
            }
            if ("android.intent.action.SCREEN_OFF".equals(action)) {
                com.android.server.display.BrightnessTracker.this.mBgHandler.obtainMessage(2).sendToTarget();
            } else if ("android.intent.action.SCREEN_ON".equals(action)) {
                com.android.server.display.BrightnessTracker.this.mBgHandler.obtainMessage(3).sendToTarget();
            }
        }
    }

    private final class TrackerHandler extends android.os.Handler {
        public TrackerHandler(android.os.Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 0:
                    com.android.server.display.BrightnessTracker.this.backgroundStart(((java.lang.Float) message.obj).floatValue());
                    break;
                case 1:
                    com.android.server.display.BrightnessTracker.BrightnessChangeValues brightnessChangeValues = (com.android.server.display.BrightnessTracker.BrightnessChangeValues) message.obj;
                    com.android.server.display.BrightnessTracker.this.handleBrightnessChanged(brightnessChangeValues.brightness, message.arg1 == 1, brightnessChangeValues.powerBrightnessFactor, brightnessChangeValues.wasShortTermModelActive, brightnessChangeValues.isDefaultBrightnessConfig, brightnessChangeValues.timestamp, brightnessChangeValues.uniqueDisplayId, brightnessChangeValues.luxValues, brightnessChangeValues.luxTimestamps);
                    break;
                case 2:
                    com.android.server.display.BrightnessTracker.this.stopSensorListener();
                    com.android.server.display.BrightnessTracker.this.disableColorSampling();
                    break;
                case 3:
                    com.android.server.display.BrightnessTracker.this.startSensorListener();
                    com.android.server.display.BrightnessTracker.this.enableColorSampling();
                    break;
                case 4:
                    com.android.server.display.BrightnessTracker.this.mShouldCollectColorSample = ((java.lang.Boolean) message.obj).booleanValue();
                    if (com.android.server.display.BrightnessTracker.this.mShouldCollectColorSample && !com.android.server.display.BrightnessTracker.this.mColorSamplingEnabled) {
                        com.android.server.display.BrightnessTracker.this.enableColorSampling();
                        break;
                    } else if (!com.android.server.display.BrightnessTracker.this.mShouldCollectColorSample && com.android.server.display.BrightnessTracker.this.mColorSamplingEnabled) {
                        com.android.server.display.BrightnessTracker.this.disableColorSampling();
                        break;
                    }
                    break;
                case 5:
                    com.android.server.display.BrightnessTracker.this.handleSensorChanged((android.hardware.Sensor) message.obj);
                    break;
            }
        }
    }

    private static class BrightnessChangeValues {
        public final float brightness;
        public final boolean isDefaultBrightnessConfig;
        public final long[] luxTimestamps;
        public final float[] luxValues;
        public final float powerBrightnessFactor;
        public final long timestamp;
        public final java.lang.String uniqueDisplayId;
        public final boolean wasShortTermModelActive;

        BrightnessChangeValues(float f, float f2, boolean z, boolean z2, long j, java.lang.String str, float[] fArr, long[] jArr) {
            this.brightness = f;
            this.powerBrightnessFactor = f2;
            this.wasShortTermModelActive = z;
            this.isDefaultBrightnessConfig = z2;
            this.timestamp = j;
            this.uniqueDisplayId = str;
            this.luxValues = fArr;
            this.luxTimestamps = jArr;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static class Injector {
        Injector() {
        }

        public void registerSensorListener(android.content.Context context, android.hardware.SensorEventListener sensorEventListener, android.hardware.Sensor sensor, android.os.Handler handler) {
            ((android.hardware.SensorManager) context.getSystemService(android.hardware.SensorManager.class)).registerListener(sensorEventListener, sensor, 3, handler);
        }

        public void unregisterSensorListener(android.content.Context context, android.hardware.SensorEventListener sensorEventListener) {
            ((android.hardware.SensorManager) context.getSystemService(android.hardware.SensorManager.class)).unregisterListener(sensorEventListener);
        }

        public void registerBrightnessModeObserver(android.content.ContentResolver contentResolver, android.database.ContentObserver contentObserver) {
            contentResolver.registerContentObserver(android.provider.Settings.System.getUriFor("screen_brightness_mode"), false, contentObserver, -1);
        }

        public void unregisterBrightnessModeObserver(android.content.Context context, android.database.ContentObserver contentObserver) {
            context.getContentResolver().unregisterContentObserver(contentObserver);
        }

        public void registerReceiver(android.content.Context context, android.content.BroadcastReceiver broadcastReceiver, android.content.IntentFilter intentFilter) {
            context.registerReceiver(broadcastReceiver, intentFilter, 2);
        }

        public void unregisterReceiver(android.content.Context context, android.content.BroadcastReceiver broadcastReceiver) {
            context.unregisterReceiver(broadcastReceiver);
        }

        public android.os.Handler getBackgroundHandler() {
            return com.android.internal.os.BackgroundThread.getHandler();
        }

        public boolean isBrightnessModeAutomatic(android.content.ContentResolver contentResolver) {
            return android.provider.Settings.System.getIntForUser(contentResolver, "screen_brightness_mode", 0, -2) == 1;
        }

        public int getSecureIntForUser(android.content.ContentResolver contentResolver, java.lang.String str, int i, int i2) {
            return android.provider.Settings.Secure.getIntForUser(contentResolver, str, i, i2);
        }

        public android.util.AtomicFile getFile(java.lang.String str) {
            return new android.util.AtomicFile(new java.io.File(android.os.Environment.getDataSystemDirectory(), str));
        }

        public android.util.AtomicFile getLegacyFile(java.lang.String str) {
            return new android.util.AtomicFile(new java.io.File(android.os.Environment.getDataSystemDeDirectory(), str));
        }

        public long currentTimeMillis() {
            return java.lang.System.currentTimeMillis();
        }

        public long elapsedRealtimeNanos() {
            return android.os.SystemClock.elapsedRealtimeNanos();
        }

        public int getUserSerialNumber(android.os.UserManager userManager, int i) {
            return userManager.getUserSerialNumber(i);
        }

        public int getUserId(android.os.UserManager userManager, int i) {
            return userManager.getUserHandle(i);
        }

        public int[] getProfileIds(android.os.UserManager userManager, int i) {
            if (userManager != null) {
                return userManager.getProfileIds(i, false);
            }
            return new int[]{i};
        }

        public android.app.ActivityTaskManager.RootTaskInfo getFocusedStack() throws android.os.RemoteException {
            return android.app.ActivityTaskManager.getService().getFocusedRootTaskInfo();
        }

        public void scheduleIdleJob(android.content.Context context) {
            com.android.server.display.BrightnessIdleJob.scheduleJob(context);
        }

        public void cancelIdleJob(android.content.Context context) {
            com.android.server.display.BrightnessIdleJob.cancelJob(context);
        }

        public boolean isInteractive(android.content.Context context) {
            return ((android.os.PowerManager) context.getSystemService(android.os.PowerManager.class)).isInteractive();
        }

        public int getNightDisplayColorTemperature(android.content.Context context) {
            return ((android.hardware.display.ColorDisplayManager) context.getSystemService(android.hardware.display.ColorDisplayManager.class)).getNightDisplayColorTemperature();
        }

        public boolean isNightDisplayActivated(android.content.Context context) {
            return ((android.hardware.display.ColorDisplayManager) context.getSystemService(android.hardware.display.ColorDisplayManager.class)).isNightDisplayActivated();
        }

        public int getReduceBrightColorsStrength(android.content.Context context) {
            return ((android.hardware.display.ColorDisplayManager) context.getSystemService(android.hardware.display.ColorDisplayManager.class)).getReduceBrightColorsStrength();
        }

        public float getReduceBrightColorsOffsetFactor(android.content.Context context) {
            return ((android.hardware.display.ColorDisplayManager) context.getSystemService(android.hardware.display.ColorDisplayManager.class)).getReduceBrightColorsOffsetFactor();
        }

        public boolean isReduceBrightColorsActivated(android.content.Context context) {
            return ((android.hardware.display.ColorDisplayManager) context.getSystemService(android.hardware.display.ColorDisplayManager.class)).isReduceBrightColorsActivated();
        }

        public android.hardware.display.DisplayedContentSample sampleColor(int i) {
            return ((android.hardware.display.DisplayManagerInternal) com.android.server.LocalServices.getService(android.hardware.display.DisplayManagerInternal.class)).getDisplayedContentSample(0, i, 0L);
        }

        public float getFrameRate(android.content.Context context) {
            return ((android.hardware.display.DisplayManager) context.getSystemService(android.hardware.display.DisplayManager.class)).getDisplay(0).getRefreshRate();
        }

        public android.hardware.display.DisplayedContentSamplingAttributes getSamplingAttributes() {
            return ((android.hardware.display.DisplayManagerInternal) com.android.server.LocalServices.getService(android.hardware.display.DisplayManagerInternal.class)).getDisplayedContentSamplingAttributes(0);
        }

        public boolean enableColorSampling(boolean z, int i) {
            return ((android.hardware.display.DisplayManagerInternal) com.android.server.LocalServices.getService(android.hardware.display.DisplayManagerInternal.class)).setDisplayedContentSamplingEnabled(0, z, 4, i);
        }

        public void registerDisplayListener(android.content.Context context, android.hardware.display.DisplayManager.DisplayListener displayListener, android.os.Handler handler) {
            ((android.hardware.display.DisplayManager) context.getSystemService(android.hardware.display.DisplayManager.class)).registerDisplayListener(displayListener, handler);
        }

        public void unRegisterDisplayListener(android.content.Context context, android.hardware.display.DisplayManager.DisplayListener displayListener) {
            ((android.hardware.display.DisplayManager) context.getSystemService(android.hardware.display.DisplayManager.class)).unregisterDisplayListener(displayListener);
        }
    }
}
