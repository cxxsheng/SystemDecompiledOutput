package com.android.server.devicestate;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class DeviceStateManagerService$$ExternalSyntheticLambda0 implements java.lang.Runnable {
    public final /* synthetic */ com.android.server.devicestate.DeviceStateManagerService f$0;

    public /* synthetic */ DeviceStateManagerService$$ExternalSyntheticLambda0(com.android.server.devicestate.DeviceStateManagerService deviceStateManagerService) {
        this.f$0 = deviceStateManagerService;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.f$0.notifyPolicyIfNeeded();
    }
}
