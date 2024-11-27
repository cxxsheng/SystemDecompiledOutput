package com.android.server.display.mode;

/* loaded from: classes.dex */
final class SkinThermalStatusObserver extends android.os.IThermalEventListener.Stub implements android.hardware.display.DisplayManager.DisplayListener {
    private static final java.lang.String TAG = "SkinThermalStatusObserver";
    private final android.os.Handler mHandler;
    private final com.android.server.display.mode.DisplayModeDirector.Injector mInjector;
    private boolean mLoggingEnabled;

    @com.android.internal.annotations.GuardedBy({"mThermalObserverLock"})
    private int mStatus;
    private final java.lang.Object mThermalObserverLock;

    @com.android.internal.annotations.GuardedBy({"mThermalObserverLock"})
    private final android.util.SparseArray<android.util.SparseArray<android.view.SurfaceControl.RefreshRateRange>> mThermalThrottlingByDisplay;
    private final com.android.server.display.mode.VotesStorage mVotesStorage;

    SkinThermalStatusObserver(com.android.server.display.mode.DisplayModeDirector.Injector injector, com.android.server.display.mode.VotesStorage votesStorage) {
        this(injector, votesStorage, com.android.internal.os.BackgroundThread.getHandler());
    }

    @com.android.internal.annotations.VisibleForTesting
    SkinThermalStatusObserver(com.android.server.display.mode.DisplayModeDirector.Injector injector, com.android.server.display.mode.VotesStorage votesStorage, android.os.Handler handler) {
        this.mThermalObserverLock = new java.lang.Object();
        this.mStatus = 0;
        this.mThermalThrottlingByDisplay = new android.util.SparseArray<>();
        this.mInjector = injector;
        this.mVotesStorage = votesStorage;
        this.mHandler = handler;
    }

    @android.annotation.Nullable
    public static android.view.SurfaceControl.RefreshRateRange findBestMatchingRefreshRateRange(int i, android.util.SparseArray<android.view.SurfaceControl.RefreshRateRange> sparseArray) {
        android.view.SurfaceControl.RefreshRateRange refreshRateRange = null;
        while (i >= 0) {
            refreshRateRange = sparseArray.get(i);
            if (refreshRateRange != null) {
                break;
            }
            i--;
        }
        return refreshRateRange;
    }

    void observe() {
        if (!this.mInjector.registerThermalServiceListener(this)) {
            return;
        }
        this.mInjector.registerDisplayListener(this, this.mHandler, 7L);
        populateInitialDisplayInfo();
    }

    void setLoggingEnabled(boolean z) {
        this.mLoggingEnabled = z;
    }

    public void notifyThrottling(android.os.Temperature temperature) {
        int status = temperature.getStatus();
        synchronized (this.mThermalObserverLock) {
            try {
                if (this.mStatus == status) {
                    return;
                }
                this.mStatus = status;
                this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.display.mode.SkinThermalStatusObserver$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.display.mode.SkinThermalStatusObserver.this.updateVotes();
                    }
                });
                if (this.mLoggingEnabled) {
                    android.util.Slog.d(TAG, "New thermal throttling status , current thermal status = " + status);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public void onDisplayAdded(int i) {
        updateThermalRefreshRateThrottling(i);
        if (this.mLoggingEnabled) {
            android.util.Slog.d(TAG, "Display added:" + i);
        }
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public void onDisplayRemoved(final int i) {
        synchronized (this.mThermalObserverLock) {
            this.mThermalThrottlingByDisplay.remove(i);
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.display.mode.SkinThermalStatusObserver$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.display.mode.SkinThermalStatusObserver.this.lambda$onDisplayRemoved$0(i);
                }
            });
        }
        if (this.mLoggingEnabled) {
            android.util.Slog.d(TAG, "Display removed and voted: displayId=" + i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDisplayRemoved$0(int i) {
        this.mVotesStorage.updateVote(i, 15, null);
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public void onDisplayChanged(int i) {
        updateThermalRefreshRateThrottling(i);
        if (this.mLoggingEnabled) {
            android.util.Slog.d(TAG, "Display changed:" + i);
        }
    }

    private void populateInitialDisplayInfo() {
        android.view.DisplayInfo displayInfo = new android.view.DisplayInfo();
        android.view.Display[] displays = this.mInjector.getDisplays();
        int length = displays.length;
        android.util.SparseArray sparseArray = new android.util.SparseArray(length);
        for (android.view.Display display : displays) {
            int displayId = display.getDisplayId();
            display.getDisplayInfo(displayInfo);
            sparseArray.put(displayId, displayInfo.thermalRefreshRateThrottling);
        }
        synchronized (this.mThermalObserverLock) {
            for (int i = 0; i < length; i++) {
                try {
                    this.mThermalThrottlingByDisplay.put(sparseArray.keyAt(i), (android.util.SparseArray) sparseArray.valueAt(i));
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
        if (this.mLoggingEnabled) {
            android.util.Slog.d(TAG, "Display initial info:" + sparseArray);
        }
    }

    private void updateThermalRefreshRateThrottling(final int i) {
        android.view.DisplayInfo displayInfo = new android.view.DisplayInfo();
        this.mInjector.getDisplayInfo(i, displayInfo);
        android.util.SparseArray<android.view.SurfaceControl.RefreshRateRange> sparseArray = displayInfo.thermalRefreshRateThrottling;
        synchronized (this.mThermalObserverLock) {
            this.mThermalThrottlingByDisplay.put(i, sparseArray);
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.display.mode.SkinThermalStatusObserver$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.display.mode.SkinThermalStatusObserver.this.lambda$updateThermalRefreshRateThrottling$1(i);
                }
            });
        }
        if (this.mLoggingEnabled) {
            android.util.Slog.d(TAG, "Thermal throttling updated: display=" + i + ", map=" + sparseArray);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateVotes() {
        int i;
        android.util.SparseArray<android.util.SparseArray<android.view.SurfaceControl.RefreshRateRange>> clone;
        synchronized (this.mThermalObserverLock) {
            i = this.mStatus;
            clone = this.mThermalThrottlingByDisplay.clone();
        }
        if (this.mLoggingEnabled) {
            android.util.Slog.d(TAG, "Updating votes for status=" + i + ", map=" + clone);
        }
        int size = clone.size();
        for (int i2 = 0; i2 < size; i2++) {
            reportThrottlingIfNeeded(clone.keyAt(i2), i, clone.valueAt(i2));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: updateVoteForDisplay, reason: merged with bridge method [inline-methods] */
    public void lambda$updateThermalRefreshRateThrottling$1(int i) {
        int i2;
        android.util.SparseArray<android.view.SurfaceControl.RefreshRateRange> sparseArray;
        synchronized (this.mThermalObserverLock) {
            i2 = this.mStatus;
            sparseArray = this.mThermalThrottlingByDisplay.get(i);
        }
        if (sparseArray == null) {
            android.util.Slog.d(TAG, "Updating votes, display already removed, display=" + i);
            return;
        }
        if (this.mLoggingEnabled) {
            android.util.Slog.d(TAG, "Updating votes for status=" + i2 + ", display =" + i + ", map=" + sparseArray);
        }
        reportThrottlingIfNeeded(i, i2, sparseArray);
    }

    private void reportThrottlingIfNeeded(int i, int i2, android.util.SparseArray<android.view.SurfaceControl.RefreshRateRange> sparseArray) {
        com.android.server.display.mode.Vote vote;
        if (i2 == -1) {
            return;
        }
        if (sparseArray.size() == 0) {
            fallbackReportThrottlingIfNeeded(i, i2);
            return;
        }
        android.view.SurfaceControl.RefreshRateRange findBestMatchingRefreshRateRange = findBestMatchingRefreshRateRange(i2, sparseArray);
        if (findBestMatchingRefreshRateRange == null) {
            vote = null;
        } else {
            vote = com.android.server.display.mode.Vote.forRenderFrameRates(findBestMatchingRefreshRateRange.min, findBestMatchingRefreshRateRange.max);
        }
        this.mVotesStorage.updateVote(i, 15, vote);
        if (this.mLoggingEnabled) {
            android.util.Slog.d(TAG, "Voted: vote=" + vote + ", display =" + i);
        }
    }

    private void fallbackReportThrottlingIfNeeded(int i, int i2) {
        com.android.server.display.mode.Vote vote;
        if (i2 < 4) {
            vote = null;
        } else {
            vote = com.android.server.display.mode.Vote.forRenderFrameRates(com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, 60.0f);
        }
        this.mVotesStorage.updateVote(i, 15, vote);
        if (this.mLoggingEnabled) {
            android.util.Slog.d(TAG, "Voted(fallback): vote=" + vote + ", display =" + i);
        }
    }

    void dumpLocked(java.io.PrintWriter printWriter) {
        int i;
        android.util.SparseArray<android.util.SparseArray<android.view.SurfaceControl.RefreshRateRange>> clone;
        synchronized (this.mThermalObserverLock) {
            i = this.mStatus;
            clone = this.mThermalThrottlingByDisplay.clone();
        }
        printWriter.println("  SkinThermalStatusObserver:");
        printWriter.println("    mStatus: " + i);
        printWriter.println("    mThermalThrottlingByDisplay: " + clone);
    }
}
