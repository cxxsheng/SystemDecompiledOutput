package com.android.server.location.provider;

/* loaded from: classes2.dex */
public class MockLocationProvider extends com.android.server.location.provider.AbstractLocationProvider {

    @android.annotation.Nullable
    private android.location.Location mLocation;

    public MockLocationProvider(android.location.provider.ProviderProperties providerProperties, android.location.util.identity.CallerIdentity callerIdentity, java.util.Set<java.lang.String> set) {
        super(com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, callerIdentity, providerProperties, set);
    }

    public void setProviderAllowed(boolean z) {
        setAllowed(z);
    }

    public void setProviderLocation(android.location.Location location) {
        android.location.Location location2 = new android.location.Location(location);
        location2.setIsFromMockProvider(true);
        this.mLocation = location2;
        try {
            reportLocation(android.location.LocationResult.wrap(new android.location.Location[]{location2}).validate());
        } catch (android.location.LocationResult.BadLocationException e) {
            throw new java.lang.IllegalArgumentException((java.lang.Throwable) e);
        }
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
        printWriter.println("last mock location=" + this.mLocation);
    }
}
