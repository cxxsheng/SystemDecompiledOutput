package com.android.server.biometrics.sensors;

/* loaded from: classes.dex */
public abstract class StopUserClient<T> extends com.android.server.biometrics.sensors.HalClientMonitor<T> {

    @android.annotation.NonNull
    @com.android.internal.annotations.VisibleForTesting
    private final com.android.server.biometrics.sensors.StopUserClient.UserStoppedCallback mUserStoppedCallback;

    public interface UserStoppedCallback {
        void onUserStopped();
    }

    public void onUserStopped() {
        this.mUserStoppedCallback.onUserStopped();
        getCallback().onClientFinished(this, true);
    }

    public StopUserClient(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull java.util.function.Supplier<T> supplier, @android.annotation.Nullable android.os.IBinder iBinder, int i, int i2, @android.annotation.NonNull com.android.server.biometrics.log.BiometricLogger biometricLogger, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext, @android.annotation.NonNull com.android.server.biometrics.sensors.StopUserClient.UserStoppedCallback userStoppedCallback) {
        super(context, supplier, iBinder, null, i, context.getOpPackageName(), 0, i2, biometricLogger, biometricContext);
        this.mUserStoppedCallback = userStoppedCallback;
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public int getProtoEnum() {
        return 16;
    }
}
