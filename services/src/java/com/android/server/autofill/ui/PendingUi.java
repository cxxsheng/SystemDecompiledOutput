package com.android.server.autofill.ui;

/* loaded from: classes.dex */
public final class PendingUi {
    public static final int STATE_CREATED = 1;
    public static final int STATE_FINISHED = 4;
    public static final int STATE_PENDING = 2;
    public final android.view.autofill.IAutoFillManagerClient client;
    private int mState = 1;
    private final android.os.IBinder mToken;
    public final int sessionId;

    public PendingUi(@android.annotation.NonNull android.os.IBinder iBinder, int i, @android.annotation.NonNull android.view.autofill.IAutoFillManagerClient iAutoFillManagerClient) {
        this.mToken = iBinder;
        this.sessionId = i;
        this.client = iAutoFillManagerClient;
    }

    @android.annotation.NonNull
    public android.os.IBinder getToken() {
        return this.mToken;
    }

    public void setState(int i) {
        this.mState = i;
    }

    public int getState() {
        return this.mState;
    }

    public boolean matches(android.os.IBinder iBinder) {
        return this.mToken.equals(iBinder);
    }

    public java.lang.String toString() {
        return "PendingUi: [token=" + this.mToken + ", sessionId=" + this.sessionId + ", state=" + android.util.DebugUtils.flagsToString(com.android.server.autofill.ui.PendingUi.class, "STATE_", this.mState) + "]";
    }
}
