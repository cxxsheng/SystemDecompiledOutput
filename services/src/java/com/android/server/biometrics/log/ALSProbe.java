package com.android.server.biometrics.log;

/* loaded from: classes.dex */
final class ALSProbe implements com.android.server.biometrics.log.Probe {
    private static final java.lang.String TAG = "ALSProbe";
    private boolean mDestroyRequested;
    private boolean mDestroyed;
    private boolean mDisableRequested;
    private boolean mEnabled;
    private volatile float mLastAmbientLux;

    @android.annotation.Nullable
    private final android.hardware.Sensor mLightSensor;
    private final android.hardware.SensorEventListener mLightSensorListener;
    private long mMaxSubscriptionTime;
    private com.android.server.biometrics.log.ALSProbe.NextConsumer mNextConsumer;

    @android.annotation.Nullable
    private final android.hardware.SensorManager mSensorManager;

    @android.annotation.NonNull
    private final android.os.Handler mTimer;

    ALSProbe(@android.annotation.NonNull android.hardware.SensorManager sensorManager) {
        this(sensorManager, new android.os.Handler(android.os.Looper.getMainLooper()), java.util.concurrent.TimeUnit.MINUTES.toMillis(1L));
    }

    @com.android.internal.annotations.VisibleForTesting
    ALSProbe(@android.annotation.Nullable android.hardware.SensorManager sensorManager, @android.annotation.NonNull android.os.Handler handler, long j) {
        this.mMaxSubscriptionTime = -1L;
        this.mEnabled = false;
        this.mDestroyed = false;
        this.mDestroyRequested = false;
        this.mDisableRequested = false;
        this.mNextConsumer = null;
        this.mLastAmbientLux = -1.0f;
        this.mLightSensorListener = new android.hardware.SensorEventListener() { // from class: com.android.server.biometrics.log.ALSProbe.1
            @Override // android.hardware.SensorEventListener
            public void onSensorChanged(android.hardware.SensorEvent sensorEvent) {
                com.android.server.biometrics.log.ALSProbe.this.onNext(sensorEvent.values[0]);
            }

            @Override // android.hardware.SensorEventListener
            public void onAccuracyChanged(android.hardware.Sensor sensor, int i) {
            }
        };
        this.mSensorManager = sensorManager;
        this.mLightSensor = sensorManager != null ? sensorManager.getDefaultSensor(5) : null;
        this.mTimer = handler;
        this.mMaxSubscriptionTime = j;
        if (this.mSensorManager == null || this.mLightSensor == null) {
            android.util.Slog.w(TAG, "No sensor - probe disabled");
            this.mDestroyed = true;
        }
    }

    @Override // com.android.server.biometrics.log.Probe
    public synchronized void enable() {
        if (!this.mDestroyed && !this.mDestroyRequested) {
            this.mDisableRequested = false;
            enableLightSensorLoggingLocked();
        }
    }

    @Override // com.android.server.biometrics.log.Probe
    public synchronized void disable() {
        this.mDisableRequested = true;
        if (!this.mDestroyed && this.mNextConsumer == null) {
            disableLightSensorLoggingLocked(false);
        }
    }

    @Override // com.android.server.biometrics.log.Probe
    public synchronized void destroy() {
        this.mDestroyRequested = true;
        if (!this.mDestroyed && this.mNextConsumer == null) {
            disableLightSensorLoggingLocked(true);
            this.mDestroyed = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void onNext(float f) {
        try {
            this.mLastAmbientLux = f;
            com.android.server.biometrics.log.ALSProbe.NextConsumer nextConsumer = this.mNextConsumer;
            this.mNextConsumer = null;
            if (nextConsumer != null) {
                android.util.Slog.v(TAG, "Finishing next consumer");
                if (this.mDestroyRequested) {
                    destroy();
                } else if (this.mDisableRequested) {
                    disable();
                }
                nextConsumer.consume(f);
            }
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    public float getMostRecentLux() {
        return this.mLastAmbientLux;
    }

    public synchronized void awaitNextLux(@android.annotation.NonNull java.util.function.Consumer<java.lang.Float> consumer, @android.annotation.Nullable android.os.Handler handler) {
        try {
            com.android.server.biometrics.log.ALSProbe.NextConsumer nextConsumer = new com.android.server.biometrics.log.ALSProbe.NextConsumer(consumer, handler);
            float f = this.mLastAmbientLux;
            if (f > -1.0f) {
                nextConsumer.consume(f);
            } else if (this.mNextConsumer != null) {
                this.mNextConsumer.add(nextConsumer);
            } else {
                this.mDestroyed = false;
                this.mNextConsumer = nextConsumer;
                enableLightSensorLoggingLocked();
            }
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    private void enableLightSensorLoggingLocked() {
        if (!this.mEnabled) {
            this.mEnabled = true;
            this.mLastAmbientLux = -1.0f;
            this.mSensorManager.registerListener(this.mLightSensorListener, this.mLightSensor, 3);
            android.util.Slog.v(TAG, "Enable ALS: " + this.mLightSensorListener.hashCode());
        }
        resetTimerLocked(true);
    }

    private void disableLightSensorLoggingLocked(boolean z) {
        resetTimerLocked(false);
        if (this.mEnabled) {
            this.mEnabled = false;
            if (!z) {
                this.mLastAmbientLux = -1.0f;
            }
            this.mSensorManager.unregisterListener(this.mLightSensorListener);
            android.util.Slog.v(TAG, "Disable ALS: " + this.mLightSensorListener.hashCode());
        }
    }

    private void resetTimerLocked(boolean z) {
        this.mTimer.removeCallbacksAndMessages(this);
        if (z && this.mMaxSubscriptionTime > 0) {
            this.mTimer.postDelayed(new java.lang.Runnable() { // from class: com.android.server.biometrics.log.ALSProbe$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.biometrics.log.ALSProbe.this.onTimeout();
                }
            }, this, this.mMaxSubscriptionTime);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void onTimeout() {
        android.util.Slog.e(TAG, "Max time exceeded for ALS logger - disabling: " + this.mLightSensorListener.hashCode());
        onNext(this.mLastAmbientLux);
        disable();
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class NextConsumer {

        @android.annotation.NonNull
        private final java.util.function.Consumer<java.lang.Float> mConsumer;

        @android.annotation.Nullable
        private final android.os.Handler mHandler;

        @android.annotation.NonNull
        private final java.util.List<com.android.server.biometrics.log.ALSProbe.NextConsumer> mOthers;

        private NextConsumer(@android.annotation.NonNull java.util.function.Consumer<java.lang.Float> consumer, @android.annotation.Nullable android.os.Handler handler) {
            this.mOthers = new java.util.ArrayList();
            this.mConsumer = consumer;
            this.mHandler = handler;
        }

        public void consume(final float f) {
            if (this.mHandler != null) {
                this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.log.ALSProbe$NextConsumer$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.biometrics.log.ALSProbe.NextConsumer.this.lambda$consume$0(f);
                    }
                });
            } else {
                this.mConsumer.accept(java.lang.Float.valueOf(f));
            }
            java.util.Iterator<com.android.server.biometrics.log.ALSProbe.NextConsumer> it = this.mOthers.iterator();
            while (it.hasNext()) {
                it.next().consume(f);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$consume$0(float f) {
            this.mConsumer.accept(java.lang.Float.valueOf(f));
        }

        public void add(com.android.server.biometrics.log.ALSProbe.NextConsumer nextConsumer) {
            this.mOthers.add(nextConsumer);
        }
    }
}
