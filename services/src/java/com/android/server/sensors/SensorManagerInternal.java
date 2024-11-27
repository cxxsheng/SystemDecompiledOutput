package com.android.server.sensors;

/* loaded from: classes2.dex */
public abstract class SensorManagerInternal {

    public interface ProximityActiveListener {
        void onProximityActive(boolean z);
    }

    public interface RuntimeSensorCallback {
        int onConfigurationChanged(int i, boolean z, int i2, int i3);

        int onDirectChannelConfigured(int i, int i2, int i3);

        int onDirectChannelCreated(android.os.ParcelFileDescriptor parcelFileDescriptor);

        void onDirectChannelDestroyed(int i);
    }

    public abstract void addProximityActiveListener(@android.annotation.NonNull java.util.concurrent.Executor executor, @android.annotation.NonNull com.android.server.sensors.SensorManagerInternal.ProximityActiveListener proximityActiveListener);

    public abstract int createRuntimeSensor(int i, int i2, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, float f, float f2, float f3, int i3, int i4, int i5, @android.annotation.NonNull com.android.server.sensors.SensorManagerInternal.RuntimeSensorCallback runtimeSensorCallback);

    public abstract void removeProximityActiveListener(@android.annotation.NonNull com.android.server.sensors.SensorManagerInternal.ProximityActiveListener proximityActiveListener);

    public abstract void removeRuntimeSensor(int i);

    public abstract boolean sendSensorEvent(int i, int i2, long j, @android.annotation.NonNull float[] fArr);
}
