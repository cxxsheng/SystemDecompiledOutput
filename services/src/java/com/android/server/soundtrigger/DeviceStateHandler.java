package com.android.server.soundtrigger;

/* loaded from: classes2.dex */
public class DeviceStateHandler implements com.android.server.soundtrigger.PhoneCallStateHandler.Callback {
    public static final long CALL_INACTIVE_MSG_DELAY_MS = 1000;
    private final java.util.concurrent.Executor mCallbackExecutor;
    private final com.android.server.utils.EventLogger mEventLogger;
    private final java.lang.Object mLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    com.android.server.soundtrigger.DeviceStateHandler.SoundTriggerDeviceState mSoundTriggerDeviceState = com.android.server.soundtrigger.DeviceStateHandler.SoundTriggerDeviceState.ENABLE;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mSoundTriggerPowerSaveMode = 0;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mIsPhoneCallOngoing = false;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private com.android.server.soundtrigger.DeviceStateHandler.NotificationTask mPhoneStateChangePendingNotify = null;
    private java.util.Set<com.android.server.soundtrigger.DeviceStateHandler.DeviceStateListener> mCallbackSet = java.util.concurrent.ConcurrentHashMap.newKeySet(4);
    private final java.util.concurrent.Executor mDelayedNotificationExecutor = java.util.concurrent.Executors.newSingleThreadExecutor();

    public interface DeviceStateListener {
        void onSoundTriggerDeviceStateUpdate(com.android.server.soundtrigger.DeviceStateHandler.SoundTriggerDeviceState soundTriggerDeviceState);
    }

    public enum SoundTriggerDeviceState {
        DISABLE,
        CRITICAL,
        ENABLE
    }

    public void onPowerModeChanged(int i) {
        this.mEventLogger.enqueue(new com.android.server.soundtrigger.DeviceStateHandler.SoundTriggerPowerEvent(i));
        synchronized (this.mLock) {
            try {
                if (i == this.mSoundTriggerPowerSaveMode) {
                    return;
                }
                this.mSoundTriggerPowerSaveMode = i;
                evaluateStateChange();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.soundtrigger.PhoneCallStateHandler.Callback
    public void onPhoneCallStateChanged(boolean z) {
        this.mEventLogger.enqueue(new com.android.server.soundtrigger.DeviceStateHandler.PhoneCallEvent(z));
        synchronized (this.mLock) {
            try {
                if (this.mIsPhoneCallOngoing == z) {
                    return;
                }
                if (this.mPhoneStateChangePendingNotify != null) {
                    this.mPhoneStateChangePendingNotify.cancel();
                    this.mPhoneStateChangePendingNotify = null;
                }
                this.mIsPhoneCallOngoing = z;
                if (!this.mIsPhoneCallOngoing) {
                    this.mPhoneStateChangePendingNotify = new com.android.server.soundtrigger.DeviceStateHandler.NotificationTask(new java.lang.Runnable() { // from class: com.android.server.soundtrigger.DeviceStateHandler.1
                        @Override // java.lang.Runnable
                        public void run() {
                            synchronized (com.android.server.soundtrigger.DeviceStateHandler.this.mLock) {
                                try {
                                    if (com.android.server.soundtrigger.DeviceStateHandler.this.mPhoneStateChangePendingNotify != null && com.android.server.soundtrigger.DeviceStateHandler.this.mPhoneStateChangePendingNotify.runnableEquals(this)) {
                                        com.android.server.soundtrigger.DeviceStateHandler.this.mPhoneStateChangePendingNotify = null;
                                        com.android.server.soundtrigger.DeviceStateHandler.this.evaluateStateChange();
                                    }
                                } catch (java.lang.Throwable th) {
                                    throw th;
                                }
                            }
                        }
                    }, 1000L);
                    this.mDelayedNotificationExecutor.execute(this.mPhoneStateChangePendingNotify);
                } else {
                    evaluateStateChange();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public DeviceStateHandler(java.util.concurrent.Executor executor, com.android.server.utils.EventLogger eventLogger) {
        java.util.Objects.requireNonNull(executor);
        this.mCallbackExecutor = executor;
        java.util.Objects.requireNonNull(eventLogger);
        this.mEventLogger = eventLogger;
    }

    public com.android.server.soundtrigger.DeviceStateHandler.SoundTriggerDeviceState getDeviceState() {
        com.android.server.soundtrigger.DeviceStateHandler.SoundTriggerDeviceState soundTriggerDeviceState;
        synchronized (this.mLock) {
            soundTriggerDeviceState = this.mSoundTriggerDeviceState;
        }
        return soundTriggerDeviceState;
    }

    public void registerListener(final com.android.server.soundtrigger.DeviceStateHandler.DeviceStateListener deviceStateListener) {
        final com.android.server.soundtrigger.DeviceStateHandler.SoundTriggerDeviceState deviceState = getDeviceState();
        this.mCallbackExecutor.execute(new java.lang.Runnable() { // from class: com.android.server.soundtrigger.DeviceStateHandler$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.soundtrigger.DeviceStateHandler.DeviceStateListener.this.onSoundTriggerDeviceStateUpdate(deviceState);
            }
        });
        this.mCallbackSet.add(deviceStateListener);
    }

    public void unregisterListener(com.android.server.soundtrigger.DeviceStateHandler.DeviceStateListener deviceStateListener) {
        this.mCallbackSet.remove(deviceStateListener);
    }

    void dump(java.io.PrintWriter printWriter) {
        synchronized (this.mLock) {
            printWriter.println("DeviceState: " + this.mSoundTriggerDeviceState.name());
            printWriter.println("PhoneState: " + this.mIsPhoneCallOngoing);
            printWriter.println("PowerSaveMode: " + this.mSoundTriggerPowerSaveMode);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void evaluateStateChange() {
        com.android.server.soundtrigger.DeviceStateHandler.SoundTriggerDeviceState computeState = computeState();
        if (this.mPhoneStateChangePendingNotify != null || this.mSoundTriggerDeviceState == computeState) {
            return;
        }
        this.mSoundTriggerDeviceState = computeState;
        this.mEventLogger.enqueue(new com.android.server.soundtrigger.DeviceStateHandler.DeviceStateEvent(this.mSoundTriggerDeviceState));
        final com.android.server.soundtrigger.DeviceStateHandler.SoundTriggerDeviceState soundTriggerDeviceState = this.mSoundTriggerDeviceState;
        for (final com.android.server.soundtrigger.DeviceStateHandler.DeviceStateListener deviceStateListener : this.mCallbackSet) {
            this.mCallbackExecutor.execute(new java.lang.Runnable() { // from class: com.android.server.soundtrigger.DeviceStateHandler$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.soundtrigger.DeviceStateHandler.DeviceStateListener.this.onSoundTriggerDeviceStateUpdate(soundTriggerDeviceState);
                }
            });
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private com.android.server.soundtrigger.DeviceStateHandler.SoundTriggerDeviceState computeState() {
        if (this.mIsPhoneCallOngoing) {
            return com.android.server.soundtrigger.DeviceStateHandler.SoundTriggerDeviceState.DISABLE;
        }
        switch (this.mSoundTriggerPowerSaveMode) {
            case 0:
                return com.android.server.soundtrigger.DeviceStateHandler.SoundTriggerDeviceState.ENABLE;
            case 1:
                return com.android.server.soundtrigger.DeviceStateHandler.SoundTriggerDeviceState.CRITICAL;
            case 2:
                return com.android.server.soundtrigger.DeviceStateHandler.SoundTriggerDeviceState.DISABLE;
            default:
                throw new java.lang.IllegalStateException("Received unexpected power state code" + this.mSoundTriggerPowerSaveMode);
        }
    }

    private static class NotificationTask implements java.lang.Runnable {
        private final java.util.concurrent.CountDownLatch mCancelLatch = new java.util.concurrent.CountDownLatch(1);
        private final java.lang.Runnable mRunnable;
        private final long mWaitInMillis;

        NotificationTask(java.lang.Runnable runnable, long j) {
            this.mRunnable = runnable;
            this.mWaitInMillis = j;
        }

        void cancel() {
            this.mCancelLatch.countDown();
        }

        boolean runnableEquals(java.lang.Runnable runnable) {
            return this.mRunnable == runnable;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                if (!this.mCancelLatch.await(this.mWaitInMillis, java.util.concurrent.TimeUnit.MILLISECONDS)) {
                    this.mRunnable.run();
                }
            } catch (java.lang.InterruptedException e) {
                java.lang.Thread.currentThread().interrupt();
                throw new java.lang.AssertionError("Unexpected InterruptedException", e);
            }
        }
    }

    private static class PhoneCallEvent extends com.android.server.utils.EventLogger.Event {
        final boolean mIsInPhoneCall;

        PhoneCallEvent(boolean z) {
            this.mIsInPhoneCall = z;
        }

        @Override // com.android.server.utils.EventLogger.Event
        public java.lang.String eventToString() {
            return "PhoneCallChange - inPhoneCall: " + this.mIsInPhoneCall;
        }
    }

    private static class SoundTriggerPowerEvent extends com.android.server.utils.EventLogger.Event {
        final int mSoundTriggerPowerState;

        SoundTriggerPowerEvent(int i) {
            this.mSoundTriggerPowerState = i;
        }

        @Override // com.android.server.utils.EventLogger.Event
        public java.lang.String eventToString() {
            return "SoundTriggerPowerChange: " + stateToString();
        }

        private java.lang.String stateToString() {
            switch (this.mSoundTriggerPowerState) {
                case 0:
                    return "All enabled";
                case 1:
                    return "Critical only";
                case 2:
                    return "All disabled";
                default:
                    return "Unknown power state: " + this.mSoundTriggerPowerState;
            }
        }
    }

    private static class DeviceStateEvent extends com.android.server.utils.EventLogger.Event {
        final com.android.server.soundtrigger.DeviceStateHandler.SoundTriggerDeviceState mSoundTriggerDeviceState;

        DeviceStateEvent(com.android.server.soundtrigger.DeviceStateHandler.SoundTriggerDeviceState soundTriggerDeviceState) {
            this.mSoundTriggerDeviceState = soundTriggerDeviceState;
        }

        @Override // com.android.server.utils.EventLogger.Event
        public java.lang.String eventToString() {
            return "DeviceStateChange: " + this.mSoundTriggerDeviceState.name();
        }
    }
}
