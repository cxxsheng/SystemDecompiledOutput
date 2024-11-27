package com.android.server.location.geofence;

/* loaded from: classes2.dex */
public final class GeofenceProxy implements com.android.server.servicewatcher.ServiceWatcher.ServiceListener<com.android.server.servicewatcher.CurrentUserServiceSupplier.BoundServiceInfo> {
    private static final java.lang.String SERVICE_ACTION = "com.android.location.service.GeofenceProvider";
    private static final java.lang.String TAG = "GeofenceProxy";
    volatile android.hardware.location.IGeofenceHardware mGeofenceHardware;
    final android.location.IGpsGeofenceHardware mGpsGeofenceHardware;
    final com.android.server.servicewatcher.ServiceWatcher mServiceWatcher;

    @android.annotation.Nullable
    public static com.android.server.location.geofence.GeofenceProxy createAndBind(android.content.Context context, android.location.IGpsGeofenceHardware iGpsGeofenceHardware) {
        com.android.server.location.geofence.GeofenceProxy geofenceProxy = new com.android.server.location.geofence.GeofenceProxy(context, iGpsGeofenceHardware);
        if (geofenceProxy.register(context)) {
            return geofenceProxy;
        }
        return null;
    }

    private GeofenceProxy(android.content.Context context, android.location.IGpsGeofenceHardware iGpsGeofenceHardware) {
        java.util.Objects.requireNonNull(iGpsGeofenceHardware);
        this.mGpsGeofenceHardware = iGpsGeofenceHardware;
        this.mServiceWatcher = com.android.server.servicewatcher.ServiceWatcher.create(context, TAG, com.android.server.servicewatcher.CurrentUserServiceSupplier.createFromConfig(context, SERVICE_ACTION, android.R.bool.config_enableDefaultHdrConversionPassthrough, android.R.string.config_ethernet_iface_regex), this);
        this.mGeofenceHardware = null;
    }

    void updateGeofenceHardware(android.os.IBinder iBinder) throws android.os.RemoteException {
        android.location.IGeofenceProvider.Stub.asInterface(iBinder).setGeofenceHardware(this.mGeofenceHardware);
    }

    private boolean register(android.content.Context context) {
        boolean checkServiceResolves = this.mServiceWatcher.checkServiceResolves();
        if (checkServiceResolves) {
            this.mServiceWatcher.register();
            context.bindServiceAsUser(new android.content.Intent(context, (java.lang.Class<?>) android.hardware.location.GeofenceHardwareService.class), new com.android.server.location.geofence.GeofenceProxy.GeofenceProxyServiceConnection(), 1, android.os.UserHandle.SYSTEM);
        }
        return checkServiceResolves;
    }

    @Override // com.android.server.servicewatcher.ServiceWatcher.ServiceListener
    public void onBind(android.os.IBinder iBinder, com.android.server.servicewatcher.CurrentUserServiceSupplier.BoundServiceInfo boundServiceInfo) throws android.os.RemoteException {
        updateGeofenceHardware(iBinder);
    }

    @Override // com.android.server.servicewatcher.ServiceWatcher.ServiceListener
    public void onUnbind() {
    }

    private class GeofenceProxyServiceConnection implements android.content.ServiceConnection {
        GeofenceProxyServiceConnection() {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
            android.hardware.location.IGeofenceHardware asInterface = android.hardware.location.IGeofenceHardware.Stub.asInterface(iBinder);
            try {
                asInterface.setGpsGeofenceHardware(com.android.server.location.geofence.GeofenceProxy.this.mGpsGeofenceHardware);
                com.android.server.location.geofence.GeofenceProxy.this.mGeofenceHardware = asInterface;
                com.android.server.location.geofence.GeofenceProxy.this.mServiceWatcher.runOnBinder(new com.android.server.location.geofence.GeofenceProxy$GeofenceProxyServiceConnection$$ExternalSyntheticLambda0(com.android.server.location.geofence.GeofenceProxy.this));
            } catch (android.os.RemoteException e) {
                android.util.Log.w(com.android.server.location.geofence.GeofenceProxy.TAG, "unable to initialize geofence hardware", e);
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(android.content.ComponentName componentName) {
            com.android.server.location.geofence.GeofenceProxy.this.mGeofenceHardware = null;
            com.android.server.location.geofence.GeofenceProxy.this.mServiceWatcher.runOnBinder(new com.android.server.location.geofence.GeofenceProxy$GeofenceProxyServiceConnection$$ExternalSyntheticLambda0(com.android.server.location.geofence.GeofenceProxy.this));
        }
    }
}
