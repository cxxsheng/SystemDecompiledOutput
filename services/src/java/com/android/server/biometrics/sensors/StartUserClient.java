package com.android.server.biometrics.sensors;

/* loaded from: classes.dex */
public abstract class StartUserClient<T, U> extends com.android.server.biometrics.sensors.HalClientMonitor<T> {

    @android.annotation.NonNull
    @com.android.internal.annotations.VisibleForTesting
    protected final com.android.server.biometrics.sensors.StartUserClient.UserStartedCallback<U> mUserStartedCallback;

    public interface UserStartedCallback<U> {
        void onUserStarted(int i, U u, int i2);
    }

    public StartUserClient(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull java.util.function.Supplier<T> supplier, @android.annotation.Nullable android.os.IBinder iBinder, int i, int i2, @android.annotation.NonNull com.android.server.biometrics.log.BiometricLogger biometricLogger, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext, @android.annotation.NonNull com.android.server.biometrics.sensors.StartUserClient.UserStartedCallback<U> userStartedCallback) {
        super(context, supplier, iBinder, null, i, context.getOpPackageName(), 0, i2, biometricLogger, biometricContext);
        this.mUserStartedCallback = userStartedCallback;
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public int getProtoEnum() {
        return 17;
    }
}
