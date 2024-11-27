package com.android.server.ondeviceintelligence;

/* loaded from: classes2.dex */
public class RemoteOnDeviceIntelligenceService extends com.android.internal.infra.ServiceConnector.Impl<android.service.ondeviceintelligence.IOnDeviceIntelligenceService> {
    private static final java.lang.String TAG = com.android.server.ondeviceintelligence.RemoteOnDeviceIntelligenceService.class.getSimpleName();

    RemoteOnDeviceIntelligenceService(android.content.Context context, android.content.ComponentName componentName, int i) {
        super(context, new android.content.Intent("android.service.ondeviceintelligence.OnDeviceIntelligenceService").setComponent(componentName), 67112960, i, new java.util.function.Function() { // from class: com.android.server.ondeviceintelligence.RemoteOnDeviceIntelligenceService$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return android.service.ondeviceintelligence.IOnDeviceIntelligenceService.Stub.asInterface((android.os.IBinder) obj);
            }
        });
        connect();
    }

    protected long getAutoDisconnectTimeoutMs() {
        return -1L;
    }
}
