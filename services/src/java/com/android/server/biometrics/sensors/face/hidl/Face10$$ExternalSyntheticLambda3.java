package com.android.server.biometrics.sensors.face.hidl;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class Face10$$ExternalSyntheticLambda3 implements java.util.function.Supplier {
    public final /* synthetic */ com.android.server.biometrics.sensors.face.hidl.Face10 f$0;

    public /* synthetic */ Face10$$ExternalSyntheticLambda3(com.android.server.biometrics.sensors.face.hidl.Face10 face10) {
        this.f$0 = face10;
    }

    @Override // java.util.function.Supplier
    public final java.lang.Object get() {
        android.hardware.biometrics.face.V1_0.IBiometricsFace daemon;
        daemon = this.f$0.getDaemon();
        return daemon;
    }
}
