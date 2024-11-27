package com.android.server.location.geofence;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes2.dex */
public final /* synthetic */ class GeofenceProxy$GeofenceProxyServiceConnection$$ExternalSyntheticLambda0 implements com.android.server.servicewatcher.ServiceWatcher.BinderOperation {
    public final /* synthetic */ com.android.server.location.geofence.GeofenceProxy f$0;

    @Override // com.android.server.servicewatcher.ServiceWatcher.BinderOperation
    public final void run(android.os.IBinder iBinder) {
        this.f$0.updateGeofenceHardware(iBinder);
    }
}
