package com.android.server.power;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes2.dex */
public final /* synthetic */ class ThermalManagerService$$ExternalSyntheticLambda0 implements com.android.server.power.ThermalManagerService.ThermalHalWrapper.TemperatureChangedCallback {
    public final /* synthetic */ com.android.server.power.ThermalManagerService f$0;

    public /* synthetic */ ThermalManagerService$$ExternalSyntheticLambda0(com.android.server.power.ThermalManagerService thermalManagerService) {
        this.f$0 = thermalManagerService;
    }

    @Override // com.android.server.power.ThermalManagerService.ThermalHalWrapper.TemperatureChangedCallback
    public final void onValues(android.os.Temperature temperature) {
        this.f$0.onTemperatureChangedCallback(temperature);
    }
}
