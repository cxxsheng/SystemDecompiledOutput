package com.android.server.ondeviceintelligence;

/* loaded from: classes2.dex */
public class RemoteOnDeviceTrustedInferenceService extends com.android.internal.infra.ServiceConnector.Impl<android.service.ondeviceintelligence.IOnDeviceTrustedInferenceService> {
    RemoteOnDeviceTrustedInferenceService(android.content.Context context, android.content.ComponentName componentName, int i) {
        super(context, new android.content.Intent("android.service.ondeviceintelligence.OnDeviceTrustedInferenceService").setComponent(componentName), 67112960, i, new java.util.function.Function() { // from class: com.android.server.ondeviceintelligence.RemoteOnDeviceTrustedInferenceService$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return android.service.ondeviceintelligence.IOnDeviceTrustedInferenceService.Stub.asInterface((android.os.IBinder) obj);
            }
        });
        connect();
    }

    protected long getAutoDisconnectTimeoutMs() {
        return -1L;
    }
}
