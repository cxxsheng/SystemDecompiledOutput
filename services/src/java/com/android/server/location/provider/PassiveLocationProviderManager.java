package com.android.server.location.provider;

/* loaded from: classes2.dex */
public class PassiveLocationProviderManager extends com.android.server.location.provider.LocationProviderManager {
    public PassiveLocationProviderManager(android.content.Context context, com.android.server.location.injector.Injector injector) {
        super(context, injector, "passive", null);
    }

    @Override // com.android.server.location.provider.LocationProviderManager
    public void setRealProvider(com.android.server.location.provider.AbstractLocationProvider abstractLocationProvider) {
        com.android.internal.util.Preconditions.checkArgument(abstractLocationProvider instanceof com.android.server.location.provider.PassiveLocationProvider);
        super.setRealProvider(abstractLocationProvider);
    }

    @Override // com.android.server.location.provider.LocationProviderManager
    public void setMockProvider(@android.annotation.Nullable com.android.server.location.provider.MockLocationProvider mockLocationProvider) {
        if (mockLocationProvider != null) {
            throw new java.lang.IllegalArgumentException("Cannot mock the passive provider");
        }
    }

    public void updateLocation(android.location.LocationResult locationResult) {
        synchronized (this.mMultiplexerLock) {
            try {
                com.android.server.location.provider.PassiveLocationProvider passiveLocationProvider = (com.android.server.location.provider.PassiveLocationProvider) this.mProvider.getProvider();
                com.android.internal.util.Preconditions.checkState(passiveLocationProvider != null);
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    passiveLocationProvider.updateLocation(locationResult);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.android.server.location.provider.LocationProviderManager, com.android.server.location.listeners.ListenerMultiplexer
    public android.location.provider.ProviderRequest mergeRegistrations(java.util.Collection<com.android.server.location.provider.LocationProviderManager.Registration> collection) {
        return new android.location.provider.ProviderRequest.Builder().setIntervalMillis(0L).build();
    }

    @Override // com.android.server.location.provider.LocationProviderManager
    protected long calculateRequestDelayMillis(long j, java.util.Collection<com.android.server.location.provider.LocationProviderManager.Registration> collection) {
        return 0L;
    }

    @Override // com.android.server.location.provider.LocationProviderManager, com.android.server.location.listeners.ListenerMultiplexer
    protected java.lang.String getServiceState() {
        return this.mProvider.getCurrentRequest().isActive() ? "registered" : "unregistered";
    }
}
