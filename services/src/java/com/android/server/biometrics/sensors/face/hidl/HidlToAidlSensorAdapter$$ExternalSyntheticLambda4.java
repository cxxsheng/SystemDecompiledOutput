package com.android.server.biometrics.sensors.face.hidl;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class HidlToAidlSensorAdapter$$ExternalSyntheticLambda4 implements java.util.function.Supplier {
    public final /* synthetic */ com.android.server.biometrics.sensors.face.hidl.HidlToAidlSensorAdapter f$0;

    public /* synthetic */ HidlToAidlSensorAdapter$$ExternalSyntheticLambda4(com.android.server.biometrics.sensors.face.hidl.HidlToAidlSensorAdapter hidlToAidlSensorAdapter) {
        this.f$0 = hidlToAidlSensorAdapter;
    }

    @Override // java.util.function.Supplier
    public final java.lang.Object get() {
        android.hardware.biometrics.face.V1_0.IBiometricsFace iBiometricsFace;
        iBiometricsFace = this.f$0.getIBiometricsFace();
        return iBiometricsFace;
    }
}
