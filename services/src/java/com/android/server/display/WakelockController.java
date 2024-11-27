package com.android.server.display;

/* loaded from: classes.dex */
public final class WakelockController {

    @com.android.internal.annotations.VisibleForTesting
    static final int WAKE_LOCK_MAX = 5;
    public static final int WAKE_LOCK_PROXIMITY_DEBOUNCE = 3;
    public static final int WAKE_LOCK_PROXIMITY_NEGATIVE = 2;
    public static final int WAKE_LOCK_PROXIMITY_POSITIVE = 1;
    public static final int WAKE_LOCK_STATE_CHANGED = 4;
    public static final int WAKE_LOCK_UNFINISHED_BUSINESS = 5;
    private final int mDisplayId;
    private final android.hardware.display.DisplayManagerInternal.DisplayPowerCallbacks mDisplayPowerCallbacks;
    private boolean mHasProximityDebounced;
    private boolean mIsProximityNegativeAcquired;
    private boolean mIsProximityPositiveAcquired;
    private boolean mOnStateChangedPending;
    private final java.lang.String mSuspendBlockerIdOnStateChanged;
    private final java.lang.String mSuspendBlockerIdProxDebounce;
    private final java.lang.String mSuspendBlockerIdProxNegative;
    private final java.lang.String mSuspendBlockerIdProxPositive;
    private final java.lang.String mSuspendBlockerIdUnfinishedBusiness;
    private final java.lang.String mTag;
    private boolean mUnfinishedBusiness;
    private static final java.lang.String TAG = "WakelockController";
    private static final boolean DEBUG = com.android.server.display.utils.DebugUtils.isDebuggable(TAG);

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface WAKE_LOCK_TYPE {
    }

    public WakelockController(int i, android.hardware.display.DisplayManagerInternal.DisplayPowerCallbacks displayPowerCallbacks) {
        this.mDisplayId = i;
        this.mTag = "WakelockController[" + this.mDisplayId + "]";
        this.mDisplayPowerCallbacks = displayPowerCallbacks;
        this.mSuspendBlockerIdUnfinishedBusiness = "[" + i + "]unfinished business";
        this.mSuspendBlockerIdOnStateChanged = "[" + i + "]on state changed";
        this.mSuspendBlockerIdProxPositive = "[" + i + "]prox positive";
        this.mSuspendBlockerIdProxNegative = "[" + i + "]prox negative";
        this.mSuspendBlockerIdProxDebounce = "[" + i + "]prox debounce";
    }

    public boolean acquireWakelock(int i) {
        return acquireWakelockInternal(i);
    }

    public boolean releaseWakelock(int i) {
        return releaseWakelockInternal(i);
    }

    public void releaseAll() {
        for (int i = 1; i <= 5; i++) {
            releaseWakelockInternal(i);
        }
    }

    private boolean acquireWakelockInternal(int i) {
        switch (i) {
            case 1:
                return acquireProxPositiveSuspendBlocker();
            case 2:
                return acquireProxNegativeSuspendBlocker();
            case 3:
                return acquireProxDebounceSuspendBlocker();
            case 4:
                return acquireStateChangedSuspendBlocker();
            case 5:
                return acquireUnfinishedBusinessSuspendBlocker();
            default:
                throw new java.lang.RuntimeException("Invalid wakelock attempted to be acquired");
        }
    }

    private boolean releaseWakelockInternal(int i) {
        switch (i) {
            case 1:
                return releaseProxPositiveSuspendBlocker();
            case 2:
                return releaseProxNegativeSuspendBlocker();
            case 3:
                return releaseProxDebounceSuspendBlocker();
            case 4:
                return releaseStateChangedSuspendBlocker();
            case 5:
                return releaseUnfinishedBusinessSuspendBlocker();
            default:
                throw new java.lang.RuntimeException("Invalid wakelock attempted to be released");
        }
    }

    private boolean acquireProxPositiveSuspendBlocker() {
        if (!this.mIsProximityPositiveAcquired) {
            this.mDisplayPowerCallbacks.acquireSuspendBlocker(this.mSuspendBlockerIdProxPositive);
            this.mIsProximityPositiveAcquired = true;
            return true;
        }
        return false;
    }

    private boolean acquireStateChangedSuspendBlocker() {
        if (!this.mOnStateChangedPending) {
            if (DEBUG) {
                android.util.Slog.d(this.mTag, "State Changed...");
            }
            this.mDisplayPowerCallbacks.acquireSuspendBlocker(this.mSuspendBlockerIdOnStateChanged);
            this.mOnStateChangedPending = true;
            return true;
        }
        return false;
    }

    private boolean releaseStateChangedSuspendBlocker() {
        if (!this.mOnStateChangedPending) {
            return false;
        }
        this.mDisplayPowerCallbacks.releaseSuspendBlocker(this.mSuspendBlockerIdOnStateChanged);
        this.mOnStateChangedPending = false;
        return true;
    }

    private boolean acquireUnfinishedBusinessSuspendBlocker() {
        if (!this.mUnfinishedBusiness) {
            if (DEBUG) {
                android.util.Slog.d(this.mTag, "Unfinished business...");
            }
            this.mDisplayPowerCallbacks.acquireSuspendBlocker(this.mSuspendBlockerIdUnfinishedBusiness);
            this.mUnfinishedBusiness = true;
            return true;
        }
        return false;
    }

    private boolean releaseUnfinishedBusinessSuspendBlocker() {
        if (!this.mUnfinishedBusiness) {
            return false;
        }
        if (DEBUG) {
            android.util.Slog.d(this.mTag, "Finished business...");
        }
        this.mDisplayPowerCallbacks.releaseSuspendBlocker(this.mSuspendBlockerIdUnfinishedBusiness);
        this.mUnfinishedBusiness = false;
        return true;
    }

    private boolean releaseProxPositiveSuspendBlocker() {
        if (!this.mIsProximityPositiveAcquired) {
            return false;
        }
        this.mDisplayPowerCallbacks.releaseSuspendBlocker(this.mSuspendBlockerIdProxPositive);
        this.mIsProximityPositiveAcquired = false;
        return true;
    }

    private boolean acquireProxNegativeSuspendBlocker() {
        if (!this.mIsProximityNegativeAcquired) {
            this.mDisplayPowerCallbacks.acquireSuspendBlocker(this.mSuspendBlockerIdProxNegative);
            this.mIsProximityNegativeAcquired = true;
            return true;
        }
        return false;
    }

    private boolean releaseProxNegativeSuspendBlocker() {
        if (!this.mIsProximityNegativeAcquired) {
            return false;
        }
        this.mDisplayPowerCallbacks.releaseSuspendBlocker(this.mSuspendBlockerIdProxNegative);
        this.mIsProximityNegativeAcquired = false;
        return true;
    }

    private boolean acquireProxDebounceSuspendBlocker() {
        if (!this.mHasProximityDebounced) {
            this.mDisplayPowerCallbacks.acquireSuspendBlocker(this.mSuspendBlockerIdProxDebounce);
            this.mHasProximityDebounced = true;
            return true;
        }
        return false;
    }

    private boolean releaseProxDebounceSuspendBlocker() {
        if (!this.mHasProximityDebounced) {
            return false;
        }
        this.mDisplayPowerCallbacks.releaseSuspendBlocker(this.mSuspendBlockerIdProxDebounce);
        this.mHasProximityDebounced = false;
        return true;
    }

    public java.lang.Runnable getOnProximityPositiveRunnable() {
        return new java.lang.Runnable() { // from class: com.android.server.display.WakelockController$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.display.WakelockController.this.lambda$getOnProximityPositiveRunnable$0();
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getOnProximityPositiveRunnable$0() {
        if (this.mIsProximityPositiveAcquired) {
            this.mIsProximityPositiveAcquired = false;
            this.mDisplayPowerCallbacks.onProximityPositive();
            this.mDisplayPowerCallbacks.releaseSuspendBlocker(this.mSuspendBlockerIdProxPositive);
        }
    }

    public java.lang.Runnable getOnStateChangedRunnable() {
        return new java.lang.Runnable() { // from class: com.android.server.display.WakelockController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.display.WakelockController.this.lambda$getOnStateChangedRunnable$1();
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getOnStateChangedRunnable$1() {
        if (this.mOnStateChangedPending) {
            this.mOnStateChangedPending = false;
            this.mDisplayPowerCallbacks.onStateChanged();
            this.mDisplayPowerCallbacks.releaseSuspendBlocker(this.mSuspendBlockerIdOnStateChanged);
        }
    }

    public java.lang.Runnable getOnProximityNegativeRunnable() {
        return new java.lang.Runnable() { // from class: com.android.server.display.WakelockController$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.display.WakelockController.this.lambda$getOnProximityNegativeRunnable$2();
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getOnProximityNegativeRunnable$2() {
        if (this.mIsProximityNegativeAcquired) {
            this.mIsProximityNegativeAcquired = false;
            this.mDisplayPowerCallbacks.onProximityNegative();
            this.mDisplayPowerCallbacks.releaseSuspendBlocker(this.mSuspendBlockerIdProxNegative);
        }
    }

    public void dumpLocal(java.io.PrintWriter printWriter) {
        printWriter.println("WakelockController State:");
        printWriter.println("  mDisplayId=" + this.mDisplayId);
        printWriter.println("  mUnfinishedBusiness=" + hasUnfinishedBusiness());
        printWriter.println("  mOnStateChangePending=" + isOnStateChangedPending());
        printWriter.println("  mOnProximityPositiveMessages=" + isProximityPositiveAcquired());
        printWriter.println("  mOnProximityNegativeMessages=" + isProximityNegativeAcquired());
    }

    @com.android.internal.annotations.VisibleForTesting
    java.lang.String getSuspendBlockerUnfinishedBusinessId() {
        return this.mSuspendBlockerIdUnfinishedBusiness;
    }

    @com.android.internal.annotations.VisibleForTesting
    java.lang.String getSuspendBlockerOnStateChangedId() {
        return this.mSuspendBlockerIdOnStateChanged;
    }

    @com.android.internal.annotations.VisibleForTesting
    java.lang.String getSuspendBlockerProxPositiveId() {
        return this.mSuspendBlockerIdProxPositive;
    }

    @com.android.internal.annotations.VisibleForTesting
    java.lang.String getSuspendBlockerProxNegativeId() {
        return this.mSuspendBlockerIdProxNegative;
    }

    @com.android.internal.annotations.VisibleForTesting
    java.lang.String getSuspendBlockerProxDebounceId() {
        return this.mSuspendBlockerIdProxDebounce;
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean hasUnfinishedBusiness() {
        return this.mUnfinishedBusiness;
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean isOnStateChangedPending() {
        return this.mOnStateChangedPending;
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean isProximityPositiveAcquired() {
        return this.mIsProximityPositiveAcquired;
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean isProximityNegativeAcquired() {
        return this.mIsProximityNegativeAcquired;
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean hasProximitySensorDebounced() {
        return this.mHasProximityDebounced;
    }
}
