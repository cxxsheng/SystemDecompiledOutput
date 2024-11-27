package com.android.server.timezonedetector.location;

/* loaded from: classes2.dex */
class RealLocationTimeZoneProviderProxy extends com.android.server.timezonedetector.location.LocationTimeZoneProviderProxy implements com.android.server.servicewatcher.ServiceWatcher.ServiceListener<com.android.server.servicewatcher.CurrentUserServiceSupplier.BoundServiceInfo> {

    @com.android.internal.annotations.GuardedBy({"mSharedLock"})
    @android.annotation.Nullable
    private com.android.server.timezonedetector.location.RealLocationTimeZoneProviderProxy.ManagerProxy mManagerProxy;

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mSharedLock"})
    private com.android.server.timezonedetector.location.TimeZoneProviderRequest mRequest;

    @android.annotation.NonNull
    private final com.android.server.servicewatcher.ServiceWatcher mServiceWatcher;

    RealLocationTimeZoneProviderProxy(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.os.Handler handler, @android.annotation.NonNull com.android.server.timezonedetector.location.ThreadingDomain threadingDomain, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, boolean z) {
        super(context, threadingDomain);
        com.android.server.servicewatcher.CurrentUserServiceSupplier create;
        this.mManagerProxy = null;
        this.mRequest = com.android.server.timezonedetector.location.TimeZoneProviderRequest.createStopUpdatesRequest();
        java.util.Objects.requireNonNull(str2);
        if (!z) {
            create = com.android.server.servicewatcher.CurrentUserServiceSupplier.create(context, str, str2, "android.permission.BIND_TIME_ZONE_PROVIDER_SERVICE", "android.permission.INSTALL_LOCATION_TIME_ZONE_PROVIDER_SERVICE");
        } else {
            create = com.android.server.servicewatcher.CurrentUserServiceSupplier.createUnsafeForTestsOnly(context, str, str2, "android.permission.BIND_TIME_ZONE_PROVIDER_SERVICE", null);
        }
        this.mServiceWatcher = com.android.server.servicewatcher.ServiceWatcher.create(context, handler, "RealLocationTimeZoneProviderProxy", create, this);
    }

    @Override // com.android.server.timezonedetector.location.LocationTimeZoneProviderProxy
    void onInitialize() {
        if (!register()) {
            throw new java.lang.IllegalStateException("Unable to register binder proxy");
        }
    }

    @Override // com.android.server.timezonedetector.location.LocationTimeZoneProviderProxy
    void onDestroy() {
        this.mServiceWatcher.unregister();
    }

    private boolean register() {
        boolean checkServiceResolves = this.mServiceWatcher.checkServiceResolves();
        if (checkServiceResolves) {
            this.mServiceWatcher.register();
        }
        return checkServiceResolves;
    }

    @Override // com.android.server.servicewatcher.ServiceWatcher.ServiceListener
    public void onBind(android.os.IBinder iBinder, com.android.server.servicewatcher.CurrentUserServiceSupplier.BoundServiceInfo boundServiceInfo) {
        this.mThreadingDomain.assertCurrentThread();
        synchronized (this.mSharedLock) {
            this.mManagerProxy = new com.android.server.timezonedetector.location.RealLocationTimeZoneProviderProxy.ManagerProxy();
            this.mListener.onProviderBound();
            trySendCurrentRequest();
        }
    }

    @Override // com.android.server.servicewatcher.ServiceWatcher.ServiceListener
    public void onUnbind() {
        this.mThreadingDomain.assertCurrentThread();
        synchronized (this.mSharedLock) {
            this.mManagerProxy = null;
            this.mListener.onProviderUnbound();
        }
    }

    @Override // com.android.server.timezonedetector.location.LocationTimeZoneProviderProxy
    final void setRequest(@android.annotation.NonNull com.android.server.timezonedetector.location.TimeZoneProviderRequest timeZoneProviderRequest) {
        this.mThreadingDomain.assertCurrentThread();
        java.util.Objects.requireNonNull(timeZoneProviderRequest);
        synchronized (this.mSharedLock) {
            this.mRequest = timeZoneProviderRequest;
            trySendCurrentRequest();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mSharedLock"})
    private void trySendCurrentRequest() {
        final com.android.server.timezonedetector.location.RealLocationTimeZoneProviderProxy.ManagerProxy managerProxy = this.mManagerProxy;
        final com.android.server.timezonedetector.location.TimeZoneProviderRequest timeZoneProviderRequest = this.mRequest;
        this.mServiceWatcher.runOnBinder(new com.android.server.servicewatcher.ServiceWatcher.BinderOperation() { // from class: com.android.server.timezonedetector.location.RealLocationTimeZoneProviderProxy$$ExternalSyntheticLambda0
            @Override // com.android.server.servicewatcher.ServiceWatcher.BinderOperation
            public final void run(android.os.IBinder iBinder) {
                com.android.server.timezonedetector.location.RealLocationTimeZoneProviderProxy.lambda$trySendCurrentRequest$0(com.android.server.timezonedetector.location.TimeZoneProviderRequest.this, managerProxy, iBinder);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$trySendCurrentRequest$0(com.android.server.timezonedetector.location.TimeZoneProviderRequest timeZoneProviderRequest, com.android.server.timezonedetector.location.RealLocationTimeZoneProviderProxy.ManagerProxy managerProxy, android.os.IBinder iBinder) throws android.os.RemoteException {
        android.service.timezone.ITimeZoneProvider asInterface = android.service.timezone.ITimeZoneProvider.Stub.asInterface(iBinder);
        if (timeZoneProviderRequest.sendUpdates()) {
            asInterface.startUpdates(managerProxy, timeZoneProviderRequest.getInitializationTimeout().toMillis(), timeZoneProviderRequest.getEventFilteringAgeThreshold().toMillis());
        } else {
            asInterface.stopUpdates();
        }
    }

    @Override // com.android.server.timezonedetector.Dumpable
    public void dump(@android.annotation.NonNull android.util.IndentingPrintWriter indentingPrintWriter, @android.annotation.Nullable java.lang.String[] strArr) {
        synchronized (this.mSharedLock) {
            indentingPrintWriter.println("{RealLocationTimeZoneProviderProxy}");
            indentingPrintWriter.println("mRequest=" + this.mRequest);
            this.mServiceWatcher.dump(indentingPrintWriter);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class ManagerProxy extends android.service.timezone.ITimeZoneProviderManager.Stub {
        private ManagerProxy() {
        }

        public void onTimeZoneProviderEvent(android.service.timezone.TimeZoneProviderEvent timeZoneProviderEvent) {
            synchronized (com.android.server.timezonedetector.location.RealLocationTimeZoneProviderProxy.this.mSharedLock) {
                try {
                    if (com.android.server.timezonedetector.location.RealLocationTimeZoneProviderProxy.this.mManagerProxy != this) {
                        return;
                    }
                    com.android.server.timezonedetector.location.RealLocationTimeZoneProviderProxy.this.handleTimeZoneProviderEvent(timeZoneProviderEvent);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }
}
