package com.android.server.input;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes2.dex */
public final /* synthetic */ class BatteryController$$ExternalSyntheticLambda8 implements java.lang.Runnable {
    public final /* synthetic */ com.android.server.input.BatteryController f$0;

    public /* synthetic */ BatteryController$$ExternalSyntheticLambda8(com.android.server.input.BatteryController batteryController) {
        this.f$0 = batteryController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.f$0.handlePollEvent();
    }
}
