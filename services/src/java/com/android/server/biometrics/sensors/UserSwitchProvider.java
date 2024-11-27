package com.android.server.biometrics.sensors;

/* loaded from: classes.dex */
public interface UserSwitchProvider<T, U> {
    @android.annotation.NonNull
    com.android.server.biometrics.sensors.StartUserClient<T, U> getStartUserClient(int i);

    @android.annotation.NonNull
    com.android.server.biometrics.sensors.StopUserClient<U> getStopUserClient(int i);
}
