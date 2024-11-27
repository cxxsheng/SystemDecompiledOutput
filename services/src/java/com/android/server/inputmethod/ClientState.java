package com.android.server.inputmethod;

/* loaded from: classes2.dex */
final class ClientState {

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    android.util.SparseArray<com.android.server.inputmethod.InputMethodManagerService.AccessibilitySessionState> mAccessibilitySessions = new android.util.SparseArray<>();
    final android.view.inputmethod.InputBinding mBinding;
    final com.android.server.inputmethod.IInputMethodClientInvoker mClient;
    final android.os.IBinder.DeathRecipient mClientDeathRecipient;

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    com.android.server.inputmethod.InputMethodManagerService.SessionState mCurSession;
    final com.android.internal.inputmethod.IRemoteInputConnection mFallbackInputConnection;
    final int mPid;
    final int mSelfReportedDisplayId;

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    boolean mSessionRequested;

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    boolean mSessionRequestedForAccessibility;
    final int mUid;

    public java.lang.String toString() {
        return "ClientState{" + java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)) + " mUid=" + this.mUid + " mPid=" + this.mPid + " mSelfReportedDisplayId=" + this.mSelfReportedDisplayId + "}";
    }

    ClientState(com.android.server.inputmethod.IInputMethodClientInvoker iInputMethodClientInvoker, com.android.internal.inputmethod.IRemoteInputConnection iRemoteInputConnection, int i, int i2, int i3, android.os.IBinder.DeathRecipient deathRecipient) {
        this.mClient = iInputMethodClientInvoker;
        this.mFallbackInputConnection = iRemoteInputConnection;
        this.mUid = i;
        this.mPid = i2;
        this.mSelfReportedDisplayId = i3;
        this.mBinding = new android.view.inputmethod.InputBinding(null, this.mFallbackInputConnection.asBinder(), this.mUid, this.mPid);
        this.mClientDeathRecipient = deathRecipient;
    }
}
