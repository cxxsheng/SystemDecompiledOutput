package com.android.server.biometrics.sensors;

/* loaded from: classes.dex */
public interface BiometricServiceProvider<T extends android.hardware.biometrics.SensorPropertiesInternal> {
    boolean containsSensor(int i);

    void dumpInternal(int i, @android.annotation.NonNull java.io.PrintWriter printWriter);

    void dumpProtoMetrics(int i, @android.annotation.NonNull java.io.FileDescriptor fileDescriptor);

    void dumpProtoState(int i, @android.annotation.NonNull android.util.proto.ProtoOutputStream protoOutputStream, boolean z);

    long getAuthenticatorId(int i, int i2);

    int getLockoutModeForUser(int i, int i2);

    @android.annotation.Nullable
    T getSensorProperties(int i);

    @android.annotation.NonNull
    java.util.List<T> getSensorProperties();

    boolean hasEnrollments(int i, int i2);

    boolean isHardwareDetected(int i);
}
