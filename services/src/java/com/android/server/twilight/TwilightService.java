package com.android.server.twilight;

/* loaded from: classes2.dex */
public final class TwilightService extends com.android.server.SystemService implements android.app.AlarmManager.OnAlarmListener, android.os.Handler.Callback, android.location.LocationListener {
    private static final java.lang.String ATTRIBUTION_TAG = "TwilightService";
    private static final boolean DEBUG = false;
    private static final int MSG_START_LISTENING = 1;
    private static final int MSG_STOP_LISTENING = 2;
    private static final java.lang.String TAG = "TwilightService";
    protected android.app.AlarmManager mAlarmManager;
    private boolean mBootCompleted;
    private final android.os.Handler mHandler;
    private boolean mHasListeners;
    protected android.location.Location mLastLocation;

    @com.android.internal.annotations.GuardedBy({"mListeners"})
    protected com.android.server.twilight.TwilightState mLastTwilightState;

    @com.android.internal.annotations.GuardedBy({"mListeners"})
    private final android.util.ArrayMap<com.android.server.twilight.TwilightListener, android.os.Handler> mListeners;
    private android.location.LocationManager mLocationManager;
    private android.content.BroadcastReceiver mTimeChangedReceiver;

    public TwilightService(android.content.Context context) {
        super(context.createAttributionContext("TwilightService"));
        this.mListeners = new android.util.ArrayMap<>();
        this.mHandler = new android.os.Handler(android.os.Looper.getMainLooper(), this);
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishLocalService(com.android.server.twilight.TwilightManager.class, new com.android.server.twilight.TwilightManager() { // from class: com.android.server.twilight.TwilightService.1
            @Override // com.android.server.twilight.TwilightManager
            public void registerListener(@android.annotation.NonNull com.android.server.twilight.TwilightListener twilightListener, @android.annotation.NonNull android.os.Handler handler) {
                synchronized (com.android.server.twilight.TwilightService.this.mListeners) {
                    try {
                        boolean isEmpty = com.android.server.twilight.TwilightService.this.mListeners.isEmpty();
                        com.android.server.twilight.TwilightService.this.mListeners.put(twilightListener, handler);
                        if (isEmpty && !com.android.server.twilight.TwilightService.this.mListeners.isEmpty()) {
                            com.android.server.twilight.TwilightService.this.mHandler.sendEmptyMessage(1);
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }

            @Override // com.android.server.twilight.TwilightManager
            public void unregisterListener(@android.annotation.NonNull com.android.server.twilight.TwilightListener twilightListener) {
                synchronized (com.android.server.twilight.TwilightService.this.mListeners) {
                    try {
                        boolean isEmpty = com.android.server.twilight.TwilightService.this.mListeners.isEmpty();
                        com.android.server.twilight.TwilightService.this.mListeners.remove(twilightListener);
                        if (!isEmpty && com.android.server.twilight.TwilightService.this.mListeners.isEmpty()) {
                            com.android.server.twilight.TwilightService.this.mHandler.sendEmptyMessage(2);
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }

            @Override // com.android.server.twilight.TwilightManager
            public com.android.server.twilight.TwilightState getLastTwilightState() {
                com.android.server.twilight.TwilightState twilightState;
                synchronized (com.android.server.twilight.TwilightService.this.mListeners) {
                    twilightState = com.android.server.twilight.TwilightService.this.mLastTwilightState;
                }
                return twilightState;
            }
        });
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        if (i == 1000) {
            android.content.Context context = getContext();
            this.mAlarmManager = (android.app.AlarmManager) context.getSystemService(com.android.server.am.HostingRecord.TRIGGER_TYPE_ALARM);
            this.mLocationManager = (android.location.LocationManager) context.getSystemService("location");
            this.mBootCompleted = true;
            if (this.mHasListeners) {
                startListening();
            }
        }
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(android.os.Message message) {
        switch (message.what) {
            case 1:
                if (!this.mHasListeners) {
                    this.mHasListeners = true;
                    if (this.mBootCompleted) {
                        startListening();
                    }
                }
                return true;
            case 2:
                if (this.mHasListeners) {
                    this.mHasListeners = false;
                    if (this.mBootCompleted) {
                        stopListening();
                    }
                }
                return true;
            default:
                return false;
        }
    }

    private void startListening() {
        android.util.Slog.d("TwilightService", "startListening");
        this.mLocationManager.requestLocationUpdates((android.location.LocationRequest) null, this, android.os.Looper.getMainLooper());
        if (this.mLocationManager.getLastLocation() == null) {
            if (this.mLocationManager.isProviderEnabled("network")) {
                this.mLocationManager.getCurrentLocation("network", null, getContext().getMainExecutor(), new java.util.function.Consumer() { // from class: com.android.server.twilight.TwilightService$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        com.android.server.twilight.TwilightService.this.onLocationChanged((android.location.Location) obj);
                    }
                });
            } else if (this.mLocationManager.isProviderEnabled("gps")) {
                this.mLocationManager.getCurrentLocation("gps", null, getContext().getMainExecutor(), new java.util.function.Consumer() { // from class: com.android.server.twilight.TwilightService$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        com.android.server.twilight.TwilightService.this.onLocationChanged((android.location.Location) obj);
                    }
                });
            }
        }
        if (this.mTimeChangedReceiver == null) {
            this.mTimeChangedReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.twilight.TwilightService.2
                @Override // android.content.BroadcastReceiver
                public void onReceive(android.content.Context context, android.content.Intent intent) {
                    android.util.Slog.d("TwilightService", "onReceive: " + intent);
                    com.android.server.twilight.TwilightService.this.updateTwilightState();
                }
            };
            android.content.IntentFilter intentFilter = new android.content.IntentFilter("android.intent.action.TIME_SET");
            intentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
            getContext().registerReceiver(this.mTimeChangedReceiver, intentFilter);
        }
        updateTwilightState();
    }

    private void stopListening() {
        android.util.Slog.d("TwilightService", "stopListening");
        if (this.mTimeChangedReceiver != null) {
            getContext().unregisterReceiver(this.mTimeChangedReceiver);
            this.mTimeChangedReceiver = null;
        }
        if (this.mLastTwilightState != null) {
            this.mAlarmManager.cancel(this);
        }
        this.mLocationManager.removeUpdates(this);
        this.mLastLocation = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateTwilightState() {
        final com.android.server.twilight.TwilightState calculateTwilightState = calculateTwilightState(this.mLastLocation != null ? this.mLastLocation : this.mLocationManager.getLastLocation(), java.lang.System.currentTimeMillis());
        synchronized (this.mListeners) {
            try {
                if (!java.util.Objects.equals(this.mLastTwilightState, calculateTwilightState)) {
                    this.mLastTwilightState = calculateTwilightState;
                    for (int size = this.mListeners.size() - 1; size >= 0; size--) {
                        final com.android.server.twilight.TwilightListener keyAt = this.mListeners.keyAt(size);
                        this.mListeners.valueAt(size).post(new java.lang.Runnable() { // from class: com.android.server.twilight.TwilightService$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                com.android.server.twilight.TwilightListener.this.onTwilightStateChanged(calculateTwilightState);
                            }
                        });
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (calculateTwilightState != null) {
            this.mAlarmManager.setExact(1, calculateTwilightState.isNight() ? calculateTwilightState.sunriseTimeMillis() : calculateTwilightState.sunsetTimeMillis(), "TwilightService", this, this.mHandler);
        }
    }

    @Override // android.app.AlarmManager.OnAlarmListener
    public void onAlarm() {
        android.util.Slog.d("TwilightService", "onAlarm");
        updateTwilightState();
    }

    @Override // android.location.LocationListener
    public void onLocationChanged(@android.annotation.Nullable android.location.Location location) {
        if (location != null) {
            android.util.Slog.d("TwilightService", "onLocationChanged: provider=" + location.getProvider() + " accuracy=" + location.getAccuracy() + " time=" + location.getTime());
            this.mLastLocation = location;
            updateTwilightState();
        }
    }

    @Override // android.location.LocationListener
    public void onStatusChanged(java.lang.String str, int i, android.os.Bundle bundle) {
    }

    @Override // android.location.LocationListener
    public void onProviderEnabled(java.lang.String str) {
    }

    @Override // android.location.LocationListener
    public void onProviderDisabled(java.lang.String str) {
    }

    private static com.android.server.twilight.TwilightState calculateTwilightState(android.location.Location location, long j) {
        if (location == null) {
            return null;
        }
        com.ibm.icu.impl.CalendarAstronomer calendarAstronomer = new com.ibm.icu.impl.CalendarAstronomer(location.getLongitude(), location.getLatitude());
        android.icu.util.Calendar calendar = android.icu.util.Calendar.getInstance();
        calendar.setTimeInMillis(j);
        calendar.set(11, 12);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        calendarAstronomer.setTime(calendar.getTimeInMillis());
        long sunRiseSet = calendarAstronomer.getSunRiseSet(true);
        long sunRiseSet2 = calendarAstronomer.getSunRiseSet(false);
        if (sunRiseSet2 < j) {
            calendar.add(5, 1);
            calendarAstronomer.setTime(calendar.getTimeInMillis());
            sunRiseSet = calendarAstronomer.getSunRiseSet(true);
        } else if (sunRiseSet > j) {
            calendar.add(5, -1);
            calendarAstronomer.setTime(calendar.getTimeInMillis());
            sunRiseSet2 = calendarAstronomer.getSunRiseSet(false);
        }
        return new com.android.server.twilight.TwilightState(sunRiseSet, sunRiseSet2);
    }
}
