package com.android.server.display;

/* loaded from: classes.dex */
public final class ExternalDisplayStatsService {
    private static final int AUDIO_SINK_CHANGED = 10;
    private static final int CONNECTED_STATE = 2;
    private static final int DISABLED_STATE = 3;
    private static final int DISCONNECTED_STATE = 1;
    private static final int ERROR_CABLE_NOT_CAPABLE_DISPLAYPORT = 13;
    private static final int ERROR_DISPLAYPORT_LINK_FAILED = 12;
    private static final int ERROR_HOTPLUG_CONNECTION = 11;
    private static final int EXTENDED_STATE = 6;
    private static final int INVALID_DISPLAYS_COUNT = -1;
    private static final int KEYGUARD = 4;
    private static final int MIRRORING_STATE = 5;
    private static final int PRESENTATION_ENDED = 9;
    private static final int PRESENTATION_WHILE_EXTENDED = 8;
    private static final int PRESENTATION_WHILE_MIRRORING = 7;
    private final android.media.AudioManager.AudioPlaybackCallback mAudioPlaybackCallback;

    @com.android.internal.annotations.GuardedBy({"mExternalDisplayStates"})
    private final android.util.SparseIntArray mExternalDisplayStates;
    private final com.android.server.display.ExternalDisplayStatsService.Injector mInjector;
    private int mInteractiveExternalDisplays;
    private final android.content.BroadcastReceiver mInteractivityReceiver;
    private boolean mIsExternalDisplayUsedForAudio;
    private boolean mIsInitialized;
    private static final java.lang.String TAG = "ExternalDisplayStatsService";
    private static final boolean DEBUG = com.android.server.display.utils.DebugUtils.isDebuggable(TAG);

    /* renamed from: com.android.server.display.ExternalDisplayStatsService$1, reason: invalid class name */
    class AnonymousClass1 extends android.media.AudioManager.AudioPlaybackCallback {
        private final java.lang.Runnable mLogStateAfterAudioSinkEnabled = new java.lang.Runnable() { // from class: com.android.server.display.ExternalDisplayStatsService$1$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.display.ExternalDisplayStatsService.AnonymousClass1.this.lambda$$0();
            }
        };
        private final java.lang.Runnable mLogStateAfterAudioSinkDisabled = new java.lang.Runnable() { // from class: com.android.server.display.ExternalDisplayStatsService$1$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.display.ExternalDisplayStatsService.AnonymousClass1.this.lambda$$1();
            }
        };

        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$$0() {
            com.android.server.display.ExternalDisplayStatsService.this.logStateAfterAudioSinkChanged(true);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$$1() {
            com.android.server.display.ExternalDisplayStatsService.this.logStateAfterAudioSinkChanged(false);
        }

        @Override // android.media.AudioManager.AudioPlaybackCallback
        public void onPlaybackConfigChanged(java.util.List<android.media.AudioPlaybackConfiguration> list) {
            super.onPlaybackConfigChanged(list);
            scheduleAudioSinkChange(isExternalDisplayUsedForAudio(list));
        }

        private boolean isExternalDisplayUsedForAudio(java.util.List<android.media.AudioPlaybackConfiguration> list) {
            for (android.media.AudioPlaybackConfiguration audioPlaybackConfiguration : list) {
                android.media.AudioDeviceInfo audioDeviceInfo = audioPlaybackConfiguration.getAudioDeviceInfo();
                if (audioPlaybackConfiguration.isActive() && audioDeviceInfo != null && audioDeviceInfo.isSink() && (audioDeviceInfo.getType() == 9 || audioDeviceInfo.getType() == 10 || audioDeviceInfo.getType() == 11)) {
                    if (com.android.server.display.ExternalDisplayStatsService.DEBUG) {
                        android.util.Slog.d(com.android.server.display.ExternalDisplayStatsService.TAG, "isExternalDisplayUsedForAudio: use " + ((java.lang.Object) audioDeviceInfo.getProductName()) + " isActive=" + audioPlaybackConfiguration.isActive() + " isSink=" + audioDeviceInfo.isSink() + " type=" + audioDeviceInfo.getType());
                        return true;
                    }
                    return true;
                }
                if (com.android.server.display.ExternalDisplayStatsService.DEBUG && audioDeviceInfo != null) {
                    android.util.Slog.d(com.android.server.display.ExternalDisplayStatsService.TAG, "isExternalDisplayUsedForAudio: drop " + ((java.lang.Object) audioDeviceInfo.getProductName()) + " isActive=" + audioPlaybackConfiguration.isActive() + " isSink=" + audioDeviceInfo.isSink() + " type=" + audioDeviceInfo.getType());
                }
            }
            return false;
        }

        private void scheduleAudioSinkChange(boolean z) {
            if (com.android.server.display.ExternalDisplayStatsService.DEBUG) {
                android.util.Slog.d(com.android.server.display.ExternalDisplayStatsService.TAG, "scheduleAudioSinkChange: mIsExternalDisplayUsedForAudio=" + com.android.server.display.ExternalDisplayStatsService.this.mIsExternalDisplayUsedForAudio + " isAudioOnExternalDisplay=" + z);
            }
            com.android.server.display.ExternalDisplayStatsService.this.mInjector.getHandler().removeCallbacks(this.mLogStateAfterAudioSinkEnabled);
            com.android.server.display.ExternalDisplayStatsService.this.mInjector.getHandler().removeCallbacks(this.mLogStateAfterAudioSinkDisabled);
            java.lang.Runnable runnable = z ? this.mLogStateAfterAudioSinkEnabled : this.mLogStateAfterAudioSinkDisabled;
            if (z) {
                com.android.server.display.ExternalDisplayStatsService.this.mInjector.getHandler().postDelayed(runnable, com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY);
            } else {
                com.android.server.display.ExternalDisplayStatsService.this.mInjector.getHandler().post(runnable);
            }
        }
    }

    ExternalDisplayStatsService(android.content.Context context, android.os.Handler handler) {
        this(new com.android.server.display.ExternalDisplayStatsService.Injector(context, handler));
    }

    @com.android.internal.annotations.VisibleForTesting
    ExternalDisplayStatsService(com.android.server.display.ExternalDisplayStatsService.Injector injector) {
        this.mExternalDisplayStates = new android.util.SparseIntArray();
        this.mAudioPlaybackCallback = new com.android.server.display.ExternalDisplayStatsService.AnonymousClass1();
        this.mInteractivityReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.display.ExternalDisplayStatsService.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context, android.content.Intent intent) {
                synchronized (com.android.server.display.ExternalDisplayStatsService.this.mExternalDisplayStates) {
                    try {
                        if (com.android.server.display.ExternalDisplayStatsService.this.mExternalDisplayStates.size() == 0) {
                            return;
                        }
                        int i = 0;
                        for (int i2 = 0; i2 < com.android.server.display.ExternalDisplayStatsService.this.mExternalDisplayStates.size(); i2++) {
                            if (com.android.server.display.ExternalDisplayStatsService.this.mInjector.isInteractive(com.android.server.display.ExternalDisplayStatsService.this.mExternalDisplayStates.keyAt(i2))) {
                                i++;
                            }
                        }
                        if (com.android.server.display.ExternalDisplayStatsService.this.mInteractiveExternalDisplays == i) {
                            return;
                        }
                        if (i == 0) {
                            com.android.server.display.ExternalDisplayStatsService.this.logExternalDisplayIdleStarted();
                        } else if (-1 != com.android.server.display.ExternalDisplayStatsService.this.mInteractiveExternalDisplays) {
                            com.android.server.display.ExternalDisplayStatsService.this.logExternalDisplayIdleEnded();
                        }
                        com.android.server.display.ExternalDisplayStatsService.this.mInteractiveExternalDisplays = i;
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
        };
        this.mInjector = injector;
    }

    public void onHotplugConnectionError() {
        logExternalDisplayError(11);
    }

    public void onDisplayPortLinkTrainingFailure() {
        logExternalDisplayError(12);
    }

    public void onCableNotCapableDisplayPort() {
        logExternalDisplayError(13);
    }

    void onDisplayConnected(com.android.server.display.LogicalDisplay logicalDisplay) {
        android.view.DisplayInfo displayInfoLocked = logicalDisplay.getDisplayInfoLocked();
        if (displayInfoLocked == null || displayInfoLocked.type != 2) {
            return;
        }
        logStateConnected(logicalDisplay.getDisplayIdLocked());
    }

    void onDisplayAdded(int i) {
        if (this.mInjector.isExtendedDisplayEnabled()) {
            logStateExtended(i);
        } else {
            logStateMirroring(i);
        }
    }

    void onDisplayDisabled(int i) {
        logStateDisabled(i);
    }

    void onDisplayDisconnected(int i) {
        logStateDisconnected(i);
    }

    void onPresentationWindowAdded(int i) {
        logExternalDisplayPresentationStarted(i);
    }

    void onPresentationWindowRemoved(int i) {
        logExternalDisplayPresentationEnded(i);
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean isInteractiveExternalDisplays() {
        return this.mInteractiveExternalDisplays != 0;
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean isExternalDisplayUsedForAudio() {
        return this.mIsExternalDisplayUsedForAudio;
    }

    private void logExternalDisplayError(int i) {
        int size;
        synchronized (this.mExternalDisplayStates) {
            size = this.mExternalDisplayStates.size();
        }
        this.mInjector.writeLog(com.android.internal.util.FrameworkStatsLog.EXTERNAL_DISPLAY_STATE_CHANGED, i, size, this.mIsExternalDisplayUsedForAudio);
        if (DEBUG) {
            android.util.Slog.d(TAG, "logExternalDisplayError countOfExternalDisplays=" + size + " errorType=" + i + " mIsExternalDisplayUsedForAudio=" + this.mIsExternalDisplayUsedForAudio);
        }
    }

    private void scheduleInit() {
        this.mInjector.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.display.ExternalDisplayStatsService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.display.ExternalDisplayStatsService.this.lambda$scheduleInit$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleInit$0() {
        if (this.mIsInitialized) {
            android.util.Slog.e(TAG, "scheduleInit is called but already initialized");
            return;
        }
        this.mIsInitialized = true;
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        this.mInteractiveExternalDisplays = -1;
        this.mIsExternalDisplayUsedForAudio = false;
        this.mInjector.registerInteractivityReceiver(this.mInteractivityReceiver, intentFilter);
        this.mInjector.registerAudioPlaybackCallback(this.mAudioPlaybackCallback);
    }

    private void scheduleDeinit() {
        this.mInjector.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.display.ExternalDisplayStatsService$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.display.ExternalDisplayStatsService.this.lambda$scheduleDeinit$1();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleDeinit$1() {
        if (!this.mIsInitialized) {
            android.util.Slog.e(TAG, "scheduleDeinit is called but never initialized");
            return;
        }
        this.mIsInitialized = false;
        this.mInjector.unregisterInteractivityReceiver(this.mInteractivityReceiver);
        this.mInjector.unregisterAudioPlaybackCallback(this.mAudioPlaybackCallback);
    }

    private void logStateConnected(int i) {
        synchronized (this.mExternalDisplayStates) {
            try {
                int i2 = this.mExternalDisplayStates.get(i, 1);
                if (i2 != 1) {
                    return;
                }
                this.mExternalDisplayStates.put(i, 2);
                int size = this.mExternalDisplayStates.size();
                if (size == 1) {
                    scheduleInit();
                }
                this.mInjector.writeLog(com.android.internal.util.FrameworkStatsLog.EXTERNAL_DISPLAY_STATE_CHANGED, 2, size, this.mIsExternalDisplayUsedForAudio);
                if (DEBUG) {
                    android.util.Slog.d(TAG, "logStateConnected displayId=" + i + " countOfExternalDisplays=" + size + " currentState=" + i2 + " state=2 mIsExternalDisplayUsedForAudio=" + this.mIsExternalDisplayUsedForAudio);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void logStateDisconnected(int i) {
        synchronized (this.mExternalDisplayStates) {
            try {
                int i2 = this.mExternalDisplayStates.get(i, 1);
                if (i2 == 1) {
                    if (DEBUG) {
                        android.util.Slog.d(TAG, "logStateDisconnected displayId=" + i + " already disconnected");
                    }
                    return;
                }
                int size = this.mExternalDisplayStates.size();
                this.mExternalDisplayStates.delete(i);
                this.mInjector.writeLog(com.android.internal.util.FrameworkStatsLog.EXTERNAL_DISPLAY_STATE_CHANGED, 1, size, this.mIsExternalDisplayUsedForAudio);
                if (DEBUG) {
                    android.util.Slog.d(TAG, "logStateDisconnected displayId=" + i + " countOfExternalDisplays=" + size + " currentState=" + i2 + " state=1 mIsExternalDisplayUsedForAudio=" + this.mIsExternalDisplayUsedForAudio);
                }
                if (size == 1) {
                    scheduleDeinit();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void logStateMirroring(int i) {
        synchronized (this.mExternalDisplayStates) {
            try {
                int i2 = this.mExternalDisplayStates.get(i, 1);
                if (i2 == 1 || i2 == 5) {
                    return;
                }
                for (int i3 = 0; i3 < this.mExternalDisplayStates.size(); i3++) {
                    if (this.mExternalDisplayStates.keyAt(i3) == i) {
                        this.mExternalDisplayStates.put(i, 5);
                        int i4 = i3 + 1;
                        this.mInjector.writeLog(com.android.internal.util.FrameworkStatsLog.EXTERNAL_DISPLAY_STATE_CHANGED, 5, i4, this.mIsExternalDisplayUsedForAudio);
                        if (DEBUG) {
                            android.util.Slog.d(TAG, "logStateMirroring displayId=" + i + " countOfExternalDisplays=" + i4 + " currentState=" + i2 + " state=5 mIsExternalDisplayUsedForAudio=" + this.mIsExternalDisplayUsedForAudio);
                        }
                    }
                }
            } finally {
            }
        }
    }

    private void logStateExtended(int i) {
        synchronized (this.mExternalDisplayStates) {
            try {
                int i2 = this.mExternalDisplayStates.get(i, 1);
                if (i2 == 1 || i2 == 6) {
                    return;
                }
                for (int i3 = 0; i3 < this.mExternalDisplayStates.size(); i3++) {
                    if (this.mExternalDisplayStates.keyAt(i3) == i) {
                        this.mExternalDisplayStates.put(i, 6);
                        int i4 = i3 + 1;
                        this.mInjector.writeLog(com.android.internal.util.FrameworkStatsLog.EXTERNAL_DISPLAY_STATE_CHANGED, 6, i4, this.mIsExternalDisplayUsedForAudio);
                        if (DEBUG) {
                            android.util.Slog.d(TAG, "logStateExtended displayId=" + i + " countOfExternalDisplays=" + i4 + " currentState=" + i2 + " state=6 mIsExternalDisplayUsedForAudio=" + this.mIsExternalDisplayUsedForAudio);
                        }
                    }
                }
            } finally {
            }
        }
    }

    private void logStateDisabled(int i) {
        synchronized (this.mExternalDisplayStates) {
            try {
                int i2 = this.mExternalDisplayStates.get(i, 1);
                if (i2 == 1 || i2 == 3) {
                    return;
                }
                for (int i3 = 0; i3 < this.mExternalDisplayStates.size(); i3++) {
                    if (this.mExternalDisplayStates.keyAt(i3) == i) {
                        this.mExternalDisplayStates.put(i, 3);
                        int i4 = i3 + 1;
                        this.mInjector.writeLog(com.android.internal.util.FrameworkStatsLog.EXTERNAL_DISPLAY_STATE_CHANGED, 3, i4, this.mIsExternalDisplayUsedForAudio);
                        if (DEBUG) {
                            android.util.Slog.d(TAG, "logStateDisabled displayId=" + i + " countOfExternalDisplays=" + i4 + " currentState=" + i2 + " state=3 mIsExternalDisplayUsedForAudio=" + this.mIsExternalDisplayUsedForAudio);
                        }
                    }
                }
            } finally {
            }
        }
    }

    private void logExternalDisplayPresentationStarted(int i) {
        synchronized (this.mExternalDisplayStates) {
            try {
                int i2 = this.mExternalDisplayStates.get(i, 1);
                if (i2 == 1) {
                    return;
                }
                int size = this.mExternalDisplayStates.size();
                int i3 = this.mInjector.isExtendedDisplayEnabled() ? 8 : 7;
                this.mInjector.writeLog(com.android.internal.util.FrameworkStatsLog.EXTERNAL_DISPLAY_STATE_CHANGED, i3, size, this.mIsExternalDisplayUsedForAudio);
                if (DEBUG) {
                    android.util.Slog.d(TAG, "logExternalDisplayPresentationStarted state=" + i2 + " newState=" + i3 + " mIsExternalDisplayUsedForAudio=" + this.mIsExternalDisplayUsedForAudio);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void logExternalDisplayPresentationEnded(int i) {
        synchronized (this.mExternalDisplayStates) {
            try {
                int i2 = this.mExternalDisplayStates.get(i, 1);
                if (i2 == 1) {
                    return;
                }
                int size = this.mExternalDisplayStates.size();
                this.mInjector.writeLog(com.android.internal.util.FrameworkStatsLog.EXTERNAL_DISPLAY_STATE_CHANGED, 9, size, this.mIsExternalDisplayUsedForAudio);
                if (DEBUG) {
                    android.util.Slog.d(TAG, "logExternalDisplayPresentationEnded state=" + i2 + " countOfExternalDisplays=" + size + " mIsExternalDisplayUsedForAudio=" + this.mIsExternalDisplayUsedForAudio);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void logExternalDisplayIdleStarted() {
        synchronized (this.mExternalDisplayStates) {
            int i = 0;
            while (i < this.mExternalDisplayStates.size()) {
                try {
                    int i2 = i + 1;
                    this.mInjector.writeLog(com.android.internal.util.FrameworkStatsLog.EXTERNAL_DISPLAY_STATE_CHANGED, 4, i2, this.mIsExternalDisplayUsedForAudio);
                    if (DEBUG) {
                        int keyAt = this.mExternalDisplayStates.keyAt(i);
                        android.util.Slog.d(TAG, "logExternalDisplayIdleStarted displayId=" + keyAt + " currentState=" + this.mExternalDisplayStates.get(keyAt, 1) + " countOfExternalDisplays=" + i2 + " state=4 mIsExternalDisplayUsedForAudio=" + this.mIsExternalDisplayUsedForAudio);
                    }
                    i = i2;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void logExternalDisplayIdleEnded() {
        synchronized (this.mExternalDisplayStates) {
            int i = 0;
            while (i < this.mExternalDisplayStates.size()) {
                try {
                    int keyAt = this.mExternalDisplayStates.keyAt(i);
                    int i2 = this.mExternalDisplayStates.get(keyAt, 1);
                    if (i2 == 1) {
                        return;
                    }
                    i++;
                    this.mInjector.writeLog(com.android.internal.util.FrameworkStatsLog.EXTERNAL_DISPLAY_STATE_CHANGED, i2, i, this.mIsExternalDisplayUsedForAudio);
                    if (DEBUG) {
                        android.util.Slog.d(TAG, "logExternalDisplayIdleEnded displayId=" + keyAt + " state=" + i2 + " countOfExternalDisplays=" + i + " mIsExternalDisplayUsedForAudio=" + this.mIsExternalDisplayUsedForAudio);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void logStateAfterAudioSinkChanged(boolean z) {
        int size;
        if (this.mIsExternalDisplayUsedForAudio == z) {
            return;
        }
        this.mIsExternalDisplayUsedForAudio = z;
        synchronized (this.mExternalDisplayStates) {
            size = this.mExternalDisplayStates.size();
        }
        this.mInjector.writeLog(com.android.internal.util.FrameworkStatsLog.EXTERNAL_DISPLAY_STATE_CHANGED, 10, size, this.mIsExternalDisplayUsedForAudio);
        if (DEBUG) {
            android.util.Slog.d(TAG, "logStateAfterAudioSinkChanged countOfExternalDisplays)=" + size + " mIsExternalDisplayUsedForAudio=" + this.mIsExternalDisplayUsedForAudio);
        }
    }

    static class Injector {

        @android.annotation.Nullable
        private android.media.AudioManager mAudioManager;

        @android.annotation.NonNull
        private final android.content.Context mContext;

        @android.annotation.NonNull
        private final android.os.Handler mHandler;

        @android.annotation.Nullable
        private android.os.PowerManager mPowerManager;

        Injector(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.os.Handler handler) {
            this.mContext = context;
            this.mHandler = handler;
        }

        boolean isExtendedDisplayEnabled() {
            try {
                return android.provider.Settings.Global.getInt(this.mContext.getContentResolver(), "force_desktop_mode_on_external_displays", 0) != 0;
            } catch (java.lang.Throwable th) {
                return false;
            }
        }

        void registerInteractivityReceiver(android.content.BroadcastReceiver broadcastReceiver, android.content.IntentFilter intentFilter) {
            this.mContext.registerReceiver(broadcastReceiver, intentFilter, null, this.mHandler, 4);
        }

        void unregisterInteractivityReceiver(android.content.BroadcastReceiver broadcastReceiver) {
            this.mContext.unregisterReceiver(broadcastReceiver);
        }

        void registerAudioPlaybackCallback(android.media.AudioManager.AudioPlaybackCallback audioPlaybackCallback) {
            if (this.mAudioManager == null) {
                this.mAudioManager = (android.media.AudioManager) this.mContext.getSystemService(android.media.AudioManager.class);
            }
            if (this.mAudioManager != null) {
                this.mAudioManager.registerAudioPlaybackCallback(audioPlaybackCallback, this.mHandler);
            }
        }

        void unregisterAudioPlaybackCallback(android.media.AudioManager.AudioPlaybackCallback audioPlaybackCallback) {
            if (this.mAudioManager == null) {
                this.mAudioManager = (android.media.AudioManager) this.mContext.getSystemService(android.media.AudioManager.class);
            }
            if (this.mAudioManager != null) {
                this.mAudioManager.unregisterAudioPlaybackCallback(audioPlaybackCallback);
            }
        }

        boolean isInteractive(int i) {
            if (this.mPowerManager == null) {
                this.mPowerManager = (android.os.PowerManager) this.mContext.getSystemService(android.os.PowerManager.class);
            }
            return this.mPowerManager == null || this.mPowerManager.isInteractive(i);
        }

        @android.annotation.NonNull
        android.os.Handler getHandler() {
            return this.mHandler;
        }

        void writeLog(int i, int i2, int i3, boolean z) {
            com.android.internal.util.FrameworkStatsLog.write(i, i2, i3, z);
        }
    }
}
