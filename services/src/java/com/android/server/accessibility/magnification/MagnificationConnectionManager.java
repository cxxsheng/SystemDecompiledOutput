package com.android.server.accessibility.magnification;

/* loaded from: classes.dex */
public class MagnificationConnectionManager implements com.android.server.accessibility.magnification.PanningScalingHandler.MagnificationDelegate, com.android.server.wm.WindowManagerInternal.AccessibilityControllerInternal.UiChangesForAccessibilityCallbacks {
    private static final int CONNECTED = 1;
    private static final int CONNECTING = 0;
    private static final boolean DBG = false;
    private static final int DISCONNECTED = 3;
    private static final int DISCONNECTING = 2;
    private static final java.lang.String TAG = "MagnificationConnectionManager";
    private static final int WAIT_CONNECTION_TIMEOUT_MILLIS = 100;
    public static final int WINDOW_POSITION_AT_CENTER = 0;
    public static final int WINDOW_POSITION_AT_TOP_LEFT = 1;
    private final com.android.server.accessibility.magnification.MagnificationConnectionManager.Callback mCallback;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private com.android.server.accessibility.magnification.MagnificationConnectionManager.ConnectionCallback mConnectionCallback;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    com.android.server.accessibility.magnification.MagnificationConnectionWrapper mConnectionWrapper;
    private final android.content.Context mContext;
    private final java.lang.Object mLock;
    private final com.android.server.accessibility.magnification.MagnificationScaleProvider mScaleProvider;
    private final com.android.server.accessibility.AccessibilityTraceManager mTrace;
    private int mConnectionState = 3;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.util.SparseArray<com.android.server.accessibility.magnification.MagnificationConnectionManager.WindowMagnifier> mWindowMagnifiers = new android.util.SparseArray<>();
    private boolean mMagnificationFollowTypingEnabled = true;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseBooleanArray mIsImeVisibleArray = new android.util.SparseBooleanArray();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<java.lang.Float> mLastActivatedScale = new android.util.SparseArray<>();
    private boolean mReceiverRegistered = false;

    @com.android.internal.annotations.VisibleForTesting
    protected final android.content.BroadcastReceiver mScreenStateReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.accessibility.magnification.MagnificationConnectionManager.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            int displayId = context.getDisplayId();
            com.android.server.accessibility.magnification.MagnificationConnectionManager.this.removeMagnificationButton(displayId);
            com.android.server.accessibility.magnification.MagnificationConnectionManager.this.disableWindowMagnification(displayId, false, null);
        }
    };

    public interface Callback {
        void onAccessibilityActionPerformed(int i);

        void onChangeMagnificationMode(int i, int i2);

        void onPerformScaleAction(int i, float f, boolean z);

        void onSourceBoundsChanged(int i, android.graphics.Rect rect);

        void onWindowMagnificationActivationState(int i, boolean z);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    private @interface ConnectionState {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface WindowPosition {
    }

    private static java.lang.String connectionStateToString(int i) {
        switch (i) {
            case 0:
                return "CONNECTING";
            case 1:
                return "CONNECTED";
            case 2:
                return "DISCONNECTING";
            case 3:
                return "DISCONNECTED";
            default:
                return "UNKNOWN:" + i;
        }
    }

    public MagnificationConnectionManager(android.content.Context context, java.lang.Object obj, @android.annotation.NonNull com.android.server.accessibility.magnification.MagnificationConnectionManager.Callback callback, com.android.server.accessibility.AccessibilityTraceManager accessibilityTraceManager, com.android.server.accessibility.magnification.MagnificationScaleProvider magnificationScaleProvider) {
        this.mContext = context;
        this.mLock = obj;
        this.mCallback = callback;
        this.mTrace = accessibilityTraceManager;
        this.mScaleProvider = magnificationScaleProvider;
    }

    public void setConnection(@android.annotation.Nullable android.view.accessibility.IMagnificationConnection iMagnificationConnection) {
        java.lang.Object obj;
        synchronized (this.mLock) {
            try {
                if (this.mConnectionWrapper != null) {
                    this.mConnectionWrapper.setConnectionCallback(null);
                    if (this.mConnectionCallback != null) {
                        this.mConnectionCallback.mExpiredDeathRecipient = true;
                    }
                    this.mConnectionWrapper.unlinkToDeath(this.mConnectionCallback);
                    this.mConnectionWrapper = null;
                    if (this.mConnectionState != 0) {
                        setConnectionState(3);
                    }
                }
                if (iMagnificationConnection != null) {
                    this.mConnectionWrapper = new com.android.server.accessibility.magnification.MagnificationConnectionWrapper(iMagnificationConnection, this.mTrace);
                }
                try {
                    if (this.mConnectionWrapper != null) {
                        try {
                            this.mConnectionCallback = new com.android.server.accessibility.magnification.MagnificationConnectionManager.ConnectionCallback();
                            this.mConnectionWrapper.linkToDeath(this.mConnectionCallback);
                            this.mConnectionWrapper.setConnectionCallback(this.mConnectionCallback);
                            setConnectionState(1);
                            obj = this.mLock;
                        } catch (android.os.RemoteException e) {
                            android.util.Slog.e(TAG, "setConnection failed", e);
                            this.mConnectionWrapper = null;
                            setConnectionState(3);
                            obj = this.mLock;
                        }
                        obj.notify();
                    }
                } catch (java.lang.Throwable th) {
                    this.mLock.notify();
                    throw th;
                }
            } catch (java.lang.Throwable th2) {
                throw th2;
            }
        }
    }

    public boolean isConnected() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mConnectionWrapper != null;
        }
        return z;
    }

    /* JADX WARN: Code restructure failed: missing block: B:40:0x002f, code lost:
    
        if (r8.mConnectionState != 0) goto L13;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean requestConnection(boolean z) {
        if (this.mTrace.isA11yTracingEnabledForTypes(128L)) {
            this.mTrace.logTrace("MagnificationConnectionManager.requestMagnificationConnection", 128L, "connect=" + z);
        }
        synchronized (this.mLock) {
            if (z) {
                try {
                    if (this.mConnectionState != 1) {
                    }
                    android.util.Slog.w(TAG, "requestConnection duplicated request: connect=" + z + ", mConnectionState=" + connectionStateToString(this.mConnectionState));
                    return false;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            if (z || (this.mConnectionState != 3 && this.mConnectionState != 2)) {
                if (z) {
                    android.content.IntentFilter intentFilter = new android.content.IntentFilter("android.intent.action.SCREEN_OFF");
                    if (!this.mReceiverRegistered) {
                        this.mContext.registerReceiver(this.mScreenStateReceiver, intentFilter);
                        this.mReceiverRegistered = true;
                    }
                } else {
                    disableAllWindowMagnifiers();
                    if (this.mReceiverRegistered) {
                        this.mContext.unregisterReceiver(this.mScreenStateReceiver);
                        this.mReceiverRegistered = false;
                    }
                }
                if (requestConnectionInternal(z)) {
                    setConnectionState(z ? 0 : 2);
                    return true;
                }
                setConnectionState(3);
                return false;
            }
            android.util.Slog.w(TAG, "requestConnection duplicated request: connect=" + z + ", mConnectionState=" + connectionStateToString(this.mConnectionState));
            return false;
        }
    }

    private boolean requestConnectionInternal(boolean z) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.statusbar.StatusBarManagerInternal statusBarManagerInternal = (com.android.server.statusbar.StatusBarManagerInternal) com.android.server.LocalServices.getService(com.android.server.statusbar.StatusBarManagerInternal.class);
            if (statusBarManagerInternal != null) {
                return statusBarManagerInternal.requestMagnificationConnection(z);
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return false;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public java.lang.String getConnectionState() {
        return connectionStateToString(this.mConnectionState);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setConnectionState(int i) {
        this.mConnectionState = i;
    }

    void disableAllWindowMagnifiers() {
        synchronized (this.mLock) {
            for (int i = 0; i < this.mWindowMagnifiers.size(); i++) {
                try {
                    this.mWindowMagnifiers.valueAt(i).disableWindowMagnificationInternal(null);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            this.mWindowMagnifiers.clear();
        }
    }

    public void resetAllIfNeeded(int i) {
        synchronized (this.mLock) {
            for (int i2 = 0; i2 < this.mWindowMagnifiers.size(); i2++) {
                try {
                    com.android.server.accessibility.magnification.MagnificationConnectionManager.WindowMagnifier valueAt = this.mWindowMagnifiers.valueAt(i2);
                    if (valueAt != null && valueAt.mEnabled && i == valueAt.getIdOfLastServiceToControl()) {
                        valueAt.disableWindowMagnificationInternal(null);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetWindowMagnifiers() {
        synchronized (this.mLock) {
            for (int i = 0; i < this.mWindowMagnifiers.size(); i++) {
                try {
                    this.mWindowMagnifiers.valueAt(i).reset();
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    @Override // com.android.server.wm.WindowManagerInternal.AccessibilityControllerInternal.UiChangesForAccessibilityCallbacks
    public void onRectangleOnScreenRequested(int i, int i2, int i3, int i4, int i5) {
        if (!this.mMagnificationFollowTypingEnabled) {
            return;
        }
        float f = (i2 + i4) / 2.0f;
        float f2 = (i3 + i5) / 2.0f;
        synchronized (this.mLock) {
            try {
                if (this.mIsImeVisibleArray.get(i, false) && !isPositionInSourceBounds(i, f, f2) && isTrackingTypingFocusEnabled(i)) {
                    moveWindowMagnifierToPositionInternal(i, f, f2, android.view.accessibility.MagnificationAnimationCallback.STUB_ANIMATION_CALLBACK);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void setMagnificationFollowTypingEnabled(boolean z) {
        this.mMagnificationFollowTypingEnabled = z;
    }

    boolean isMagnificationFollowTypingEnabled() {
        return this.mMagnificationFollowTypingEnabled;
    }

    public int getIdOfLastServiceToMagnify(int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.accessibility.magnification.MagnificationConnectionManager.WindowMagnifier windowMagnifier = this.mWindowMagnifiers.get(i);
                if (windowMagnifier != null) {
                    return windowMagnifier.mIdOfLastServiceToControl;
                }
                return -1;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void setTrackingTypingFocusEnabled(int i, boolean z) {
        synchronized (this.mLock) {
            try {
                com.android.server.accessibility.magnification.MagnificationConnectionManager.WindowMagnifier windowMagnifier = this.mWindowMagnifiers.get(i);
                if (windowMagnifier == null) {
                    return;
                }
                windowMagnifier.setTrackingTypingFocusEnabled(z);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void enableAllTrackingTypingFocus() {
        synchronized (this.mLock) {
            for (int i = 0; i < this.mWindowMagnifiers.size(); i++) {
                try {
                    this.mWindowMagnifiers.valueAt(i).setTrackingTypingFocusEnabled(true);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    private void pauseTrackingTypingFocusRecord(int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.accessibility.magnification.MagnificationConnectionManager.WindowMagnifier windowMagnifier = this.mWindowMagnifiers.get(i);
                if (windowMagnifier == null) {
                    return;
                }
                windowMagnifier.pauseTrackingTypingFocusRecord();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void onImeWindowVisibilityChanged(int i, boolean z) {
        synchronized (this.mLock) {
            this.mIsImeVisibleArray.put(i, z);
        }
        if (z) {
            enableAllTrackingTypingFocus();
        } else {
            pauseTrackingTypingFocusRecord(i);
        }
    }

    boolean isImeVisible(int i) {
        boolean z;
        synchronized (this.mLock) {
            z = this.mIsImeVisibleArray.get(i);
        }
        return z;
    }

    void logTrackingTypingFocus(long j) {
        com.android.internal.accessibility.util.AccessibilityStatsLogUtils.logMagnificationFollowTypingFocusSession(j);
    }

    @Override // com.android.server.accessibility.magnification.PanningScalingHandler.MagnificationDelegate
    public boolean processScroll(int i, float f, float f2) {
        moveWindowMagnification(i, -f, -f2);
        setTrackingTypingFocusEnabled(i, false);
        return true;
    }

    @Override // com.android.server.accessibility.magnification.PanningScalingHandler.MagnificationDelegate
    public void setScale(int i, float f) {
        synchronized (this.mLock) {
            try {
                com.android.server.accessibility.magnification.MagnificationConnectionManager.WindowMagnifier windowMagnifier = this.mWindowMagnifiers.get(i);
                if (windowMagnifier == null) {
                    return;
                }
                windowMagnifier.setScale(f);
                this.mLastActivatedScale.put(i, java.lang.Float.valueOf(f));
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean enableWindowMagnification(int i, float f, float f2, float f3) {
        return enableWindowMagnification(i, f, f2, f3, android.view.accessibility.MagnificationAnimationCallback.STUB_ANIMATION_CALLBACK, 0);
    }

    public boolean enableWindowMagnification(int i, float f, float f2, float f3, @android.annotation.Nullable android.view.accessibility.MagnificationAnimationCallback magnificationAnimationCallback, int i2) {
        return enableWindowMagnification(i, f, f2, f3, magnificationAnimationCallback, 0, i2);
    }

    public boolean enableWindowMagnification(int i, float f, float f2, float f3, int i2) {
        return enableWindowMagnification(i, f, f2, f3, android.view.accessibility.MagnificationAnimationCallback.STUB_ANIMATION_CALLBACK, i2, 0);
    }

    public boolean enableWindowMagnification(int i, float f, float f2, float f3, @android.annotation.Nullable android.view.accessibility.MagnificationAnimationCallback magnificationAnimationCallback, int i2, int i3) {
        com.android.server.accessibility.magnification.MagnificationConnectionManager.WindowMagnifier windowMagnifier;
        boolean z;
        boolean enableWindowMagnificationInternal;
        synchronized (this.mLock) {
            try {
                com.android.server.accessibility.magnification.MagnificationConnectionManager.WindowMagnifier windowMagnifier2 = this.mWindowMagnifiers.get(i);
                if (windowMagnifier2 != null) {
                    windowMagnifier = windowMagnifier2;
                } else {
                    windowMagnifier = createWindowMagnifier(i);
                }
                z = windowMagnifier.mEnabled;
                enableWindowMagnificationInternal = windowMagnifier.enableWindowMagnificationInternal(f, f2, f3, magnificationAnimationCallback, i2, i3);
                if (enableWindowMagnificationInternal) {
                    this.mLastActivatedScale.put(i, java.lang.Float.valueOf(getScale(i)));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (enableWindowMagnificationInternal) {
            setTrackingTypingFocusEnabled(i, true);
            if (!z) {
                this.mCallback.onWindowMagnificationActivationState(i, true);
            }
        }
        return enableWindowMagnificationInternal;
    }

    public boolean disableWindowMagnification(int i, boolean z) {
        return disableWindowMagnification(i, z, android.view.accessibility.MagnificationAnimationCallback.STUB_ANIMATION_CALLBACK);
    }

    public boolean disableWindowMagnification(int i, boolean z, android.view.accessibility.MagnificationAnimationCallback magnificationAnimationCallback) {
        synchronized (this.mLock) {
            try {
                com.android.server.accessibility.magnification.MagnificationConnectionManager.WindowMagnifier windowMagnifier = this.mWindowMagnifiers.get(i);
                if (windowMagnifier == null) {
                    return false;
                }
                boolean disableWindowMagnificationInternal = windowMagnifier.disableWindowMagnificationInternal(magnificationAnimationCallback);
                if (z) {
                    this.mWindowMagnifiers.delete(i);
                }
                if (disableWindowMagnificationInternal) {
                    this.mCallback.onWindowMagnificationActivationState(i, false);
                }
                return disableWindowMagnificationInternal;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean onFullscreenMagnificationActivationChanged(int i, boolean z) {
        synchronized (this.mLock) {
            try {
                waitForConnectionIfNeeded();
                if (this.mConnectionWrapper == null) {
                    android.util.Slog.w(TAG, "onFullscreenMagnificationActivationChanged mConnectionWrapper is null. mConnectionState=" + connectionStateToString(this.mConnectionState));
                    return false;
                }
                return this.mConnectionWrapper.onFullscreenMagnificationActivationChanged(i, z);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    int pointersInWindow(int i, android.view.MotionEvent motionEvent) {
        synchronized (this.mLock) {
            try {
                com.android.server.accessibility.magnification.MagnificationConnectionManager.WindowMagnifier windowMagnifier = this.mWindowMagnifiers.get(i);
                if (windowMagnifier == null) {
                    return 0;
                }
                return windowMagnifier.pointersInWindow(motionEvent);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    boolean isPositionInSourceBounds(int i, float f, float f2) {
        com.android.server.accessibility.magnification.MagnificationConnectionManager.WindowMagnifier windowMagnifier = this.mWindowMagnifiers.get(i);
        if (windowMagnifier == null) {
            return false;
        }
        return windowMagnifier.isPositionInSourceBounds(f, f2);
    }

    public boolean isWindowMagnifierEnabled(int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.accessibility.magnification.MagnificationConnectionManager.WindowMagnifier windowMagnifier = this.mWindowMagnifiers.get(i);
                if (windowMagnifier == null) {
                    return false;
                }
                return windowMagnifier.isEnabled();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    float getPersistedScale(int i) {
        return android.util.MathUtils.constrain(this.mScaleProvider.getScale(i), 1.3f, 8.0f);
    }

    void persistScale(int i) {
        float scale = getScale(i);
        if (scale < 1.3f) {
            return;
        }
        this.mScaleProvider.putScale(scale, i);
    }

    @Override // com.android.server.accessibility.magnification.PanningScalingHandler.MagnificationDelegate
    public float getScale(int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.accessibility.magnification.MagnificationConnectionManager.WindowMagnifier windowMagnifier = this.mWindowMagnifiers.get(i);
                if (windowMagnifier == null || !windowMagnifier.mEnabled) {
                    return 1.0f;
                }
                return windowMagnifier.getScale();
            } finally {
            }
        }
    }

    protected float getLastActivatedScale(int i) {
        synchronized (this.mLock) {
            try {
                if (!this.mLastActivatedScale.contains(i)) {
                    return -1.0f;
                }
                return this.mLastActivatedScale.get(i).floatValue();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void moveWindowMagnification(int i, float f, float f2) {
        synchronized (this.mLock) {
            try {
                com.android.server.accessibility.magnification.MagnificationConnectionManager.WindowMagnifier windowMagnifier = this.mWindowMagnifiers.get(i);
                if (windowMagnifier == null) {
                    return;
                }
                windowMagnifier.move(f, f2);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean showMagnificationButton(int i, int i2) {
        boolean z;
        synchronized (this.mLock) {
            try {
                z = this.mConnectionWrapper != null && this.mConnectionWrapper.showMagnificationButton(i, i2);
            } finally {
            }
        }
        return z;
    }

    public boolean removeMagnificationButton(int i) {
        boolean z;
        synchronized (this.mLock) {
            try {
                z = this.mConnectionWrapper != null && this.mConnectionWrapper.removeMagnificationButton(i);
            } finally {
            }
        }
        return z;
    }

    public boolean removeMagnificationSettingsPanel(int i) {
        boolean z;
        synchronized (this.mLock) {
            try {
                z = this.mConnectionWrapper != null && this.mConnectionWrapper.removeMagnificationSettingsPanel(i);
            } finally {
            }
        }
        return z;
    }

    public boolean onUserMagnificationScaleChanged(int i, int i2, float f) {
        boolean z;
        synchronized (this.mLock) {
            try {
                z = this.mConnectionWrapper != null && this.mConnectionWrapper.onUserMagnificationScaleChanged(i, i2, f);
            } finally {
            }
        }
        return z;
    }

    public float getCenterX(int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.accessibility.magnification.MagnificationConnectionManager.WindowMagnifier windowMagnifier = this.mWindowMagnifiers.get(i);
                if (windowMagnifier == null || !windowMagnifier.mEnabled) {
                    return Float.NaN;
                }
                return windowMagnifier.getCenterX();
            } finally {
            }
        }
    }

    public float getCenterY(int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.accessibility.magnification.MagnificationConnectionManager.WindowMagnifier windowMagnifier = this.mWindowMagnifiers.get(i);
                if (windowMagnifier == null || !windowMagnifier.mEnabled) {
                    return Float.NaN;
                }
                return windowMagnifier.getCenterY();
            } finally {
            }
        }
    }

    boolean isTrackingTypingFocusEnabled(int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.accessibility.magnification.MagnificationConnectionManager.WindowMagnifier windowMagnifier = this.mWindowMagnifiers.get(i);
                if (windowMagnifier == null) {
                    return false;
                }
                return windowMagnifier.isTrackingTypingFocusEnabled();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void getMagnificationSourceBounds(int i, @android.annotation.NonNull android.graphics.Region region) {
        synchronized (this.mLock) {
            try {
                com.android.server.accessibility.magnification.MagnificationConnectionManager.WindowMagnifier windowMagnifier = this.mWindowMagnifiers.get(i);
                if (windowMagnifier == null || !windowMagnifier.mEnabled) {
                    region.setEmpty();
                } else {
                    region.set(windowMagnifier.mSourceBounds);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public com.android.server.accessibility.magnification.MagnificationConnectionManager.WindowMagnifier createWindowMagnifier(int i) {
        com.android.server.accessibility.magnification.MagnificationConnectionManager.WindowMagnifier windowMagnifier = new com.android.server.accessibility.magnification.MagnificationConnectionManager.WindowMagnifier(i, this);
        this.mWindowMagnifiers.put(i, windowMagnifier);
        return windowMagnifier;
    }

    public void onDisplayRemoved(int i) {
        disableWindowMagnification(i, true);
    }

    private class ConnectionCallback extends android.view.accessibility.IMagnificationConnectionCallback.Stub implements android.os.IBinder.DeathRecipient {
        private boolean mExpiredDeathRecipient;

        private ConnectionCallback() {
            this.mExpiredDeathRecipient = false;
        }

        public void onWindowMagnifierBoundsChanged(int i, android.graphics.Rect rect) {
            if (com.android.server.accessibility.magnification.MagnificationConnectionManager.this.mTrace.isA11yTracingEnabledForTypes(256L)) {
                com.android.server.accessibility.magnification.MagnificationConnectionManager.this.mTrace.logTrace("MagnificationConnectionManagerConnectionCallback.onWindowMagnifierBoundsChanged", 256L, "displayId=" + i + ";bounds=" + rect);
            }
            synchronized (com.android.server.accessibility.magnification.MagnificationConnectionManager.this.mLock) {
                try {
                    com.android.server.accessibility.magnification.MagnificationConnectionManager.WindowMagnifier windowMagnifier = (com.android.server.accessibility.magnification.MagnificationConnectionManager.WindowMagnifier) com.android.server.accessibility.magnification.MagnificationConnectionManager.this.mWindowMagnifiers.get(i);
                    if (windowMagnifier == null) {
                        windowMagnifier = com.android.server.accessibility.magnification.MagnificationConnectionManager.this.createWindowMagnifier(i);
                    }
                    windowMagnifier.setMagnifierLocation(rect);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void onChangeMagnificationMode(int i, int i2) throws android.os.RemoteException {
            if (com.android.server.accessibility.magnification.MagnificationConnectionManager.this.mTrace.isA11yTracingEnabledForTypes(256L)) {
                com.android.server.accessibility.magnification.MagnificationConnectionManager.this.mTrace.logTrace("MagnificationConnectionManagerConnectionCallback.onChangeMagnificationMode", 256L, "displayId=" + i + ";mode=" + i2);
            }
            com.android.server.accessibility.magnification.MagnificationConnectionManager.this.mCallback.onChangeMagnificationMode(i, i2);
        }

        public void onSourceBoundsChanged(int i, android.graphics.Rect rect) {
            if (com.android.server.accessibility.magnification.MagnificationConnectionManager.this.mTrace.isA11yTracingEnabledForTypes(256L)) {
                com.android.server.accessibility.magnification.MagnificationConnectionManager.this.mTrace.logTrace("MagnificationConnectionManagerConnectionCallback.onSourceBoundsChanged", 256L, "displayId=" + i + ";source=" + rect);
            }
            synchronized (com.android.server.accessibility.magnification.MagnificationConnectionManager.this.mLock) {
                try {
                    com.android.server.accessibility.magnification.MagnificationConnectionManager.WindowMagnifier windowMagnifier = (com.android.server.accessibility.magnification.MagnificationConnectionManager.WindowMagnifier) com.android.server.accessibility.magnification.MagnificationConnectionManager.this.mWindowMagnifiers.get(i);
                    if (windowMagnifier == null) {
                        windowMagnifier = com.android.server.accessibility.magnification.MagnificationConnectionManager.this.createWindowMagnifier(i);
                    }
                    windowMagnifier.onSourceBoundsChanged(rect);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            com.android.server.accessibility.magnification.MagnificationConnectionManager.this.mCallback.onSourceBoundsChanged(i, rect);
        }

        public void onPerformScaleAction(int i, float f, boolean z) {
            if (com.android.server.accessibility.magnification.MagnificationConnectionManager.this.mTrace.isA11yTracingEnabledForTypes(256L)) {
                com.android.server.accessibility.magnification.MagnificationConnectionManager.this.mTrace.logTrace("MagnificationConnectionManagerConnectionCallback.onPerformScaleAction", 256L, "displayId=" + i + ";scale=" + f + ";updatePersistence=" + z);
            }
            com.android.server.accessibility.magnification.MagnificationConnectionManager.this.mCallback.onPerformScaleAction(i, f, z);
        }

        public void onAccessibilityActionPerformed(int i) {
            if (com.android.server.accessibility.magnification.MagnificationConnectionManager.this.mTrace.isA11yTracingEnabledForTypes(256L)) {
                com.android.server.accessibility.magnification.MagnificationConnectionManager.this.mTrace.logTrace("MagnificationConnectionManagerConnectionCallback.onAccessibilityActionPerformed", 256L, "displayId=" + i);
            }
            com.android.server.accessibility.magnification.MagnificationConnectionManager.this.mCallback.onAccessibilityActionPerformed(i);
        }

        public void onMove(int i) {
            if (com.android.server.accessibility.magnification.MagnificationConnectionManager.this.mTrace.isA11yTracingEnabledForTypes(256L)) {
                com.android.server.accessibility.magnification.MagnificationConnectionManager.this.mTrace.logTrace("MagnificationConnectionManagerConnectionCallback.onMove", 256L, "displayId=" + i);
            }
            com.android.server.accessibility.magnification.MagnificationConnectionManager.this.setTrackingTypingFocusEnabled(i, false);
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            synchronized (com.android.server.accessibility.magnification.MagnificationConnectionManager.this.mLock) {
                try {
                    android.util.Slog.w(com.android.server.accessibility.magnification.MagnificationConnectionManager.TAG, "binderDied DeathRecipient :" + this.mExpiredDeathRecipient);
                    if (this.mExpiredDeathRecipient) {
                        return;
                    }
                    com.android.server.accessibility.magnification.MagnificationConnectionManager.this.mConnectionWrapper.unlinkToDeath(this);
                    com.android.server.accessibility.magnification.MagnificationConnectionManager.this.mConnectionWrapper = null;
                    com.android.server.accessibility.magnification.MagnificationConnectionManager.this.mConnectionCallback = null;
                    com.android.server.accessibility.magnification.MagnificationConnectionManager.this.setConnectionState(3);
                    com.android.server.accessibility.magnification.MagnificationConnectionManager.this.resetWindowMagnifiers();
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    private static class WindowMagnifier {
        private static final java.util.concurrent.atomic.AtomicLongFieldUpdater<com.android.server.accessibility.magnification.MagnificationConnectionManager.WindowMagnifier> SUM_TIME_UPDATER = java.util.concurrent.atomic.AtomicLongFieldUpdater.newUpdater(com.android.server.accessibility.magnification.MagnificationConnectionManager.WindowMagnifier.class, "mTrackingTypingFocusSumTime");
        private final int mDisplayId;
        private boolean mEnabled;
        private final com.android.server.accessibility.magnification.MagnificationConnectionManager mMagnificationConnectionManager;
        private float mScale = 1.0f;
        private final android.graphics.Rect mBounds = new android.graphics.Rect();
        private final android.graphics.Rect mSourceBounds = new android.graphics.Rect();
        private int mIdOfLastServiceToControl = -1;
        private final android.graphics.PointF mMagnificationFrameOffsetRatio = new android.graphics.PointF(com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE);
        private boolean mTrackingTypingFocusEnabled = true;
        private volatile long mTrackingTypingFocusStartTime = 0;
        private volatile long mTrackingTypingFocusSumTime = 0;

        WindowMagnifier(int i, com.android.server.accessibility.magnification.MagnificationConnectionManager magnificationConnectionManager) {
            this.mDisplayId = i;
            this.mMagnificationConnectionManager = magnificationConnectionManager;
        }

        boolean enableWindowMagnificationInternal(float f, float f2, float f3, @android.annotation.Nullable android.view.accessibility.MagnificationAnimationCallback magnificationAnimationCallback, int i, int i2) {
            if (java.lang.Float.isNaN(f)) {
                f = getScale();
            }
            float constrainScale = com.android.server.accessibility.magnification.MagnificationScaleProvider.constrainScale(f);
            setMagnificationFrameOffsetRatioByWindowPosition(i);
            if (this.mMagnificationConnectionManager.enableWindowMagnificationInternal(this.mDisplayId, constrainScale, f2, f3, this.mMagnificationFrameOffsetRatio.x, this.mMagnificationFrameOffsetRatio.y, magnificationAnimationCallback)) {
                this.mScale = constrainScale;
                this.mEnabled = true;
                this.mIdOfLastServiceToControl = i2;
                return true;
            }
            return false;
        }

        void setMagnificationFrameOffsetRatioByWindowPosition(int i) {
            switch (i) {
                case 0:
                    this.mMagnificationFrameOffsetRatio.set(com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE);
                    break;
                case 1:
                    this.mMagnificationFrameOffsetRatio.set(-1.0f, -1.0f);
                    break;
            }
        }

        boolean disableWindowMagnificationInternal(@android.annotation.Nullable android.view.accessibility.MagnificationAnimationCallback magnificationAnimationCallback) {
            if (!this.mEnabled || !this.mMagnificationConnectionManager.disableWindowMagnificationInternal(this.mDisplayId, magnificationAnimationCallback)) {
                return false;
            }
            this.mEnabled = false;
            this.mIdOfLastServiceToControl = -1;
            this.mTrackingTypingFocusEnabled = false;
            pauseTrackingTypingFocusRecord();
            return true;
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        void setScale(float f) {
            if (!this.mEnabled) {
                return;
            }
            float constrainScale = com.android.server.accessibility.magnification.MagnificationScaleProvider.constrainScale(f);
            if (java.lang.Float.compare(this.mScale, constrainScale) != 0 && this.mMagnificationConnectionManager.setScaleForWindowMagnificationInternal(this.mDisplayId, f)) {
                this.mScale = constrainScale;
            }
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        float getScale() {
            return this.mScale;
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        void setMagnifierLocation(android.graphics.Rect rect) {
            this.mBounds.set(rect);
        }

        int getIdOfLastServiceToControl() {
            return this.mIdOfLastServiceToControl;
        }

        int pointersInWindow(android.view.MotionEvent motionEvent) {
            int pointerCount = motionEvent.getPointerCount();
            int i = 0;
            for (int i2 = 0; i2 < pointerCount; i2++) {
                if (this.mBounds.contains((int) motionEvent.getX(i2), (int) motionEvent.getY(i2))) {
                    i++;
                }
            }
            return i;
        }

        boolean isPositionInSourceBounds(float f, float f2) {
            return this.mSourceBounds.contains((int) f, (int) f2);
        }

        void setTrackingTypingFocusEnabled(boolean z) {
            if (this.mMagnificationConnectionManager.isWindowMagnifierEnabled(this.mDisplayId) && this.mMagnificationConnectionManager.isImeVisible(this.mDisplayId) && z) {
                startTrackingTypingFocusRecord();
            }
            if (this.mTrackingTypingFocusEnabled && !z) {
                stopAndLogTrackingTypingFocusRecordIfNeeded();
            }
            this.mTrackingTypingFocusEnabled = z;
        }

        boolean isTrackingTypingFocusEnabled() {
            return this.mTrackingTypingFocusEnabled;
        }

        void startTrackingTypingFocusRecord() {
            if (this.mTrackingTypingFocusStartTime == 0) {
                this.mTrackingTypingFocusStartTime = android.os.SystemClock.uptimeMillis();
            }
        }

        void pauseTrackingTypingFocusRecord() {
            if (this.mTrackingTypingFocusStartTime != 0) {
                SUM_TIME_UPDATER.addAndGet(this, android.os.SystemClock.uptimeMillis() - this.mTrackingTypingFocusStartTime);
                this.mTrackingTypingFocusStartTime = 0L;
            }
        }

        void stopAndLogTrackingTypingFocusRecordIfNeeded() {
            if (this.mTrackingTypingFocusStartTime != 0 || this.mTrackingTypingFocusSumTime != 0) {
                this.mMagnificationConnectionManager.logTrackingTypingFocus(this.mTrackingTypingFocusSumTime + (this.mTrackingTypingFocusStartTime != 0 ? android.os.SystemClock.uptimeMillis() - this.mTrackingTypingFocusStartTime : 0L));
                this.mTrackingTypingFocusStartTime = 0L;
                this.mTrackingTypingFocusSumTime = 0L;
            }
        }

        boolean isEnabled() {
            return this.mEnabled;
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        void move(float f, float f2) {
            this.mMagnificationConnectionManager.moveWindowMagnifierInternal(this.mDisplayId, f, f2);
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        void reset() {
            this.mEnabled = false;
            this.mIdOfLastServiceToControl = -1;
            this.mSourceBounds.setEmpty();
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        public void onSourceBoundsChanged(android.graphics.Rect rect) {
            this.mSourceBounds.set(rect);
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        float getCenterX() {
            return this.mSourceBounds.exactCenterX();
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        float getCenterY() {
            return this.mSourceBounds.exactCenterY();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public boolean enableWindowMagnificationInternal(int i, float f, float f2, float f3, float f4, float f5, android.view.accessibility.MagnificationAnimationCallback magnificationAnimationCallback) {
        waitForConnectionIfNeeded();
        if (this.mConnectionWrapper == null) {
            android.util.Slog.w(TAG, "enableWindowMagnificationInternal mConnectionWrapper is null. mConnectionState=" + connectionStateToString(this.mConnectionState));
            return false;
        }
        return this.mConnectionWrapper.enableWindowMagnification(i, f, f2, f3, f4, f5, magnificationAnimationCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public boolean setScaleForWindowMagnificationInternal(int i, float f) {
        return this.mConnectionWrapper != null && this.mConnectionWrapper.setScaleForWindowMagnification(i, f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public boolean disableWindowMagnificationInternal(int i, android.view.accessibility.MagnificationAnimationCallback magnificationAnimationCallback) {
        if (this.mConnectionWrapper == null) {
            android.util.Slog.w(TAG, "mConnectionWrapper is null");
            return false;
        }
        return this.mConnectionWrapper.disableWindowMagnification(i, magnificationAnimationCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public boolean moveWindowMagnifierInternal(int i, float f, float f2) {
        return this.mConnectionWrapper != null && this.mConnectionWrapper.moveWindowMagnifier(i, f, f2);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean moveWindowMagnifierToPositionInternal(int i, float f, float f2, android.view.accessibility.MagnificationAnimationCallback magnificationAnimationCallback) {
        return this.mConnectionWrapper != null && this.mConnectionWrapper.moveWindowMagnifierToPosition(i, f, f2, magnificationAnimationCallback);
    }

    private void waitForConnectionIfNeeded() {
        long uptimeMillis = android.os.SystemClock.uptimeMillis() + 100;
        while (this.mConnectionState == 0 && android.os.SystemClock.uptimeMillis() < uptimeMillis) {
            try {
                this.mLock.wait(uptimeMillis - android.os.SystemClock.uptimeMillis());
            } catch (java.lang.InterruptedException e) {
            }
        }
    }
}
