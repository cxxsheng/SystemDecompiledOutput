package com.android.server.location.provider.proxy;

/* loaded from: classes2.dex */
public class ProxyGeocodeProvider {
    private final com.android.server.servicewatcher.ServiceWatcher mServiceWatcher;

    @android.annotation.Nullable
    public static com.android.server.location.provider.proxy.ProxyGeocodeProvider createAndRegister(android.content.Context context) {
        com.android.server.location.provider.proxy.ProxyGeocodeProvider proxyGeocodeProvider = new com.android.server.location.provider.proxy.ProxyGeocodeProvider(context);
        if (proxyGeocodeProvider.register()) {
            return proxyGeocodeProvider;
        }
        return null;
    }

    private ProxyGeocodeProvider(android.content.Context context) {
        this.mServiceWatcher = com.android.server.servicewatcher.ServiceWatcher.create(context, "GeocoderProxy", com.android.server.servicewatcher.CurrentUserServiceSupplier.createFromConfig(context, "com.android.location.service.GeocodeProvider", android.R.bool.config_enableCredentialFactoryResetProtection, android.R.string.config_emergency_dialer_package), null);
    }

    private boolean register() {
        boolean checkServiceResolves = this.mServiceWatcher.checkServiceResolves();
        if (checkServiceResolves) {
            this.mServiceWatcher.register();
        }
        return checkServiceResolves;
    }

    public void reverseGeocode(final android.location.provider.ReverseGeocodeRequest reverseGeocodeRequest, final android.location.provider.IGeocodeCallback iGeocodeCallback) {
        this.mServiceWatcher.runOnBinder(new com.android.server.servicewatcher.ServiceWatcher.BinderOperation() { // from class: com.android.server.location.provider.proxy.ProxyGeocodeProvider.1
            @Override // com.android.server.servicewatcher.ServiceWatcher.BinderOperation
            public void run(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.location.provider.IGeocodeProvider.Stub.asInterface(iBinder).reverseGeocode(reverseGeocodeRequest, iGeocodeCallback);
            }

            @Override // com.android.server.servicewatcher.ServiceWatcher.BinderOperation
            public void onError(java.lang.Throwable th) {
                try {
                    iGeocodeCallback.onError(th.toString());
                } catch (android.os.RemoteException e) {
                }
            }
        });
    }

    public void forwardGeocode(final android.location.provider.ForwardGeocodeRequest forwardGeocodeRequest, final android.location.provider.IGeocodeCallback iGeocodeCallback) {
        this.mServiceWatcher.runOnBinder(new com.android.server.servicewatcher.ServiceWatcher.BinderOperation() { // from class: com.android.server.location.provider.proxy.ProxyGeocodeProvider.2
            @Override // com.android.server.servicewatcher.ServiceWatcher.BinderOperation
            public void run(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.location.provider.IGeocodeProvider.Stub.asInterface(iBinder).forwardGeocode(forwardGeocodeRequest, iGeocodeCallback);
            }

            @Override // com.android.server.servicewatcher.ServiceWatcher.BinderOperation
            public void onError(java.lang.Throwable th) {
                try {
                    iGeocodeCallback.onError(th.toString());
                } catch (android.os.RemoteException e) {
                }
            }
        });
    }
}
