package com.android.server.location.provider;

/* loaded from: classes2.dex */
public class PassiveLocationProvider extends com.android.server.location.provider.AbstractLocationProvider {
    private static final android.location.provider.ProviderProperties PROPERTIES = new android.location.provider.ProviderProperties.Builder().setPowerUsage(1).setAccuracy(1).build();

    public PassiveLocationProvider(android.content.Context context) {
        super(com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, android.location.util.identity.CallerIdentity.fromContext(context), PROPERTIES, java.util.Collections.emptySet());
        setAllowed(true);
    }

    public void updateLocation(android.location.LocationResult locationResult) {
        reportLocation(locationResult);
    }

    @Override // com.android.server.location.provider.AbstractLocationProvider
    public void onSetRequest(android.location.provider.ProviderRequest providerRequest) {
    }

    @Override // com.android.server.location.provider.AbstractLocationProvider
    protected void onFlush(java.lang.Runnable runnable) {
        runnable.run();
    }

    @Override // com.android.server.location.provider.AbstractLocationProvider
    protected void onExtraCommand(int i, int i2, java.lang.String str, android.os.Bundle bundle) {
    }

    @Override // com.android.server.location.provider.AbstractLocationProvider
    public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
    }
}
