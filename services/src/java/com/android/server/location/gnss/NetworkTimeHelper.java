package com.android.server.location.gnss;

/* loaded from: classes2.dex */
abstract class NetworkTimeHelper {
    static final boolean USE_TIME_DETECTOR_IMPL = true;

    interface InjectTimeCallback {
        void injectTime(long j, long j2, int i);
    }

    NetworkTimeHelper() {
    }

    abstract void demandUtcTimeInjection();

    abstract void dump(@android.annotation.NonNull java.io.PrintWriter printWriter);

    abstract void onNetworkAvailable();

    abstract void setPeriodicTimeInjectionMode(boolean z);

    static com.android.server.location.gnss.NetworkTimeHelper create(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.os.Looper looper, @android.annotation.NonNull com.android.server.location.gnss.NetworkTimeHelper.InjectTimeCallback injectTimeCallback) {
        return new com.android.server.location.gnss.TimeDetectorNetworkTimeHelper(new com.android.server.location.gnss.TimeDetectorNetworkTimeHelper.EnvironmentImpl(looper), injectTimeCallback);
    }
}
