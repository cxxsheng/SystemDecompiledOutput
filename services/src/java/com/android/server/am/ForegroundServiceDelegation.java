package com.android.server.am;

/* loaded from: classes.dex */
public class ForegroundServiceDelegation {
    public final android.os.IBinder mBinder = new android.os.Binder();

    @android.annotation.Nullable
    public final android.content.ServiceConnection mConnection;

    @android.annotation.NonNull
    public final android.app.ForegroundServiceDelegationOptions mOptions;

    public ForegroundServiceDelegation(@android.annotation.NonNull android.app.ForegroundServiceDelegationOptions foregroundServiceDelegationOptions, @android.annotation.Nullable android.content.ServiceConnection serviceConnection) {
        this.mOptions = foregroundServiceDelegationOptions;
        this.mConnection = serviceConnection;
    }
}
