package com.android.server.policy.keyguard;

/* loaded from: classes2.dex */
public class KeyguardStateMonitor extends com.android.internal.policy.IKeyguardStateCallback.Stub {
    private static final java.lang.String TAG = "KeyguardStateMonitor";
    private final com.android.server.policy.keyguard.KeyguardStateMonitor.StateCallback mCallback;
    private final com.android.internal.widget.LockPatternUtils mLockPatternUtils;
    private volatile boolean mIsShowing = true;
    private volatile boolean mSimSecure = true;
    private volatile boolean mInputRestricted = true;
    private volatile boolean mTrusted = false;
    private int mCurrentUserId = android.app.ActivityManager.getCurrentUser();

    public interface StateCallback {
        void onShowingChanged();

        void onTrustedChanged();
    }

    public KeyguardStateMonitor(android.content.Context context, com.android.internal.policy.IKeyguardService iKeyguardService, com.android.server.policy.keyguard.KeyguardStateMonitor.StateCallback stateCallback) {
        this.mLockPatternUtils = new com.android.internal.widget.LockPatternUtils(context);
        this.mCallback = stateCallback;
        try {
            iKeyguardService.addStateMonitorCallback(this);
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "Remote Exception", e);
        }
    }

    public boolean isShowing() {
        return this.mIsShowing;
    }

    public boolean isSecure(int i) {
        return this.mLockPatternUtils.isSecure(i) || this.mSimSecure;
    }

    public boolean isInputRestricted() {
        return this.mInputRestricted;
    }

    public boolean isTrusted() {
        return this.mTrusted;
    }

    public int getCurrentUser() {
        return this.mCurrentUserId;
    }

    public void onShowingStateChanged(boolean z, int i) {
        if (i != this.mCurrentUserId) {
            return;
        }
        this.mIsShowing = z;
        this.mCallback.onShowingChanged();
    }

    public void onSimSecureStateChanged(boolean z) {
        this.mSimSecure = z;
    }

    public synchronized void setCurrentUser(int i) {
        this.mCurrentUserId = i;
    }

    public void onInputRestrictedStateChanged(boolean z) {
        this.mInputRestricted = z;
    }

    public void onTrustedChanged(boolean z) {
        this.mTrusted = z;
        this.mCallback.onTrustedChanged();
    }

    public void dump(java.lang.String str, java.io.PrintWriter printWriter) {
        printWriter.println(str + TAG);
        java.lang.String str2 = str + "  ";
        printWriter.println(str2 + "mIsShowing=" + this.mIsShowing);
        printWriter.println(str2 + "mSimSecure=" + this.mSimSecure);
        printWriter.println(str2 + "mInputRestricted=" + this.mInputRestricted);
        printWriter.println(str2 + "mTrusted=" + this.mTrusted);
        printWriter.println(str2 + "mCurrentUserId=" + this.mCurrentUserId);
    }
}
