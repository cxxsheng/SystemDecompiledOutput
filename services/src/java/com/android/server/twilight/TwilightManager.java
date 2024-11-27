package com.android.server.twilight;

/* loaded from: classes2.dex */
public interface TwilightManager {
    com.android.server.twilight.TwilightState getLastTwilightState();

    void registerListener(@android.annotation.NonNull com.android.server.twilight.TwilightListener twilightListener, @android.annotation.NonNull android.os.Handler handler);

    void unregisterListener(@android.annotation.NonNull com.android.server.twilight.TwilightListener twilightListener);
}
