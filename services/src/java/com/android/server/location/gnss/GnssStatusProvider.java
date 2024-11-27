package com.android.server.location.gnss;

/* loaded from: classes2.dex */
public class GnssStatusProvider extends com.android.server.location.gnss.GnssListenerMultiplexer<java.lang.Void, android.location.IGnssStatusListener, java.lang.Void> implements com.android.server.location.gnss.hal.GnssNative.BaseCallbacks, com.android.server.location.gnss.hal.GnssNative.StatusCallbacks, com.android.server.location.gnss.hal.GnssNative.SvStatusCallbacks {
    private final com.android.server.location.injector.AppOpsHelper mAppOpsHelper;
    private final com.android.server.location.gnss.hal.GnssNative mGnssNative;
    private boolean mIsNavigating;
    private final com.android.server.location.injector.LocationUsageLogger mLogger;

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    protected /* bridge */ /* synthetic */ boolean registerWithService(java.lang.Object obj, java.util.Collection collection) {
        return registerWithService((java.lang.Void) obj, (java.util.Collection<com.android.server.location.gnss.GnssListenerMultiplexer<java.lang.Void, android.location.IGnssStatusListener, java.lang.Void>.GnssListenerRegistration>) collection);
    }

    public GnssStatusProvider(com.android.server.location.injector.Injector injector, com.android.server.location.gnss.hal.GnssNative gnssNative) {
        super(injector);
        this.mIsNavigating = false;
        this.mAppOpsHelper = injector.getAppOpsHelper();
        this.mLogger = injector.getLocationUsageLogger();
        this.mGnssNative = gnssNative;
        gnssNative.addBaseCallbacks(this);
        gnssNative.addStatusCallbacks(this);
        gnssNative.addSvStatusCallbacks(this);
    }

    @Override // com.android.server.location.gnss.GnssListenerMultiplexer
    public void addListener(android.location.util.identity.CallerIdentity callerIdentity, android.location.IGnssStatusListener iGnssStatusListener) {
        super.addListener(callerIdentity, (android.location.util.identity.CallerIdentity) iGnssStatusListener);
    }

    protected boolean registerWithService(java.lang.Void r1, java.util.Collection<com.android.server.location.gnss.GnssListenerMultiplexer<java.lang.Void, android.location.IGnssStatusListener, java.lang.Void>.GnssListenerRegistration> collection) {
        if (this.mGnssNative.startSvStatusCollection()) {
            if (com.android.server.location.gnss.GnssManagerService.D) {
                android.util.Log.d(com.android.server.location.gnss.GnssManagerService.TAG, "starting gnss sv status");
                return true;
            }
            return true;
        }
        android.util.Log.e(com.android.server.location.gnss.GnssManagerService.TAG, "error starting gnss sv status");
        return false;
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    protected void unregisterWithService() {
        if (this.mGnssNative.stopSvStatusCollection()) {
            if (com.android.server.location.gnss.GnssManagerService.D) {
                android.util.Log.d(com.android.server.location.gnss.GnssManagerService.TAG, "stopping gnss sv status");
                return;
            }
            return;
        }
        android.util.Log.e(com.android.server.location.gnss.GnssManagerService.TAG, "error stopping gnss sv status");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public void onRegistrationAdded(android.os.IBinder iBinder, com.android.server.location.gnss.GnssListenerMultiplexer<java.lang.Void, android.location.IGnssStatusListener, java.lang.Void>.GnssListenerRegistration gnssListenerRegistration) {
        this.mLogger.logLocationApiUsage(0, 3, gnssListenerRegistration.getIdentity().getPackageName(), gnssListenerRegistration.getIdentity().getAttributionTag(), null, null, true, false, null, gnssListenerRegistration.isForeground());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public void onRegistrationRemoved(android.os.IBinder iBinder, com.android.server.location.gnss.GnssListenerMultiplexer<java.lang.Void, android.location.IGnssStatusListener, java.lang.Void>.GnssListenerRegistration gnssListenerRegistration) {
        this.mLogger.logLocationApiUsage(1, 3, gnssListenerRegistration.getIdentity().getPackageName(), gnssListenerRegistration.getIdentity().getAttributionTag(), null, null, true, false, null, gnssListenerRegistration.isForeground());
    }

    @Override // com.android.server.location.gnss.hal.GnssNative.BaseCallbacks
    public void onHalRestarted() {
        resetService();
    }

    @Override // com.android.server.location.gnss.hal.GnssNative.StatusCallbacks
    public void onReportStatus(int i) {
        boolean z;
        switch (i) {
            case 1:
                z = true;
                break;
            case 2:
            case 4:
                z = false;
                break;
            case 3:
            default:
                z = this.mIsNavigating;
                break;
        }
        if (z != this.mIsNavigating) {
            this.mIsNavigating = z;
            if (z) {
                deliverToListeners(new com.android.internal.listeners.ListenerExecutor.ListenerOperation() { // from class: com.android.server.location.gnss.GnssStatusProvider$$ExternalSyntheticLambda3
                    public final void operate(java.lang.Object obj) {
                        ((android.location.IGnssStatusListener) obj).onGnssStarted();
                    }
                });
            } else {
                deliverToListeners(new com.android.internal.listeners.ListenerExecutor.ListenerOperation() { // from class: com.android.server.location.gnss.GnssStatusProvider$$ExternalSyntheticLambda4
                    public final void operate(java.lang.Object obj) {
                        ((android.location.IGnssStatusListener) obj).onGnssStopped();
                    }
                });
            }
        }
    }

    @Override // com.android.server.location.gnss.hal.GnssNative.StatusCallbacks
    public void onReportFirstFix(final int i) {
        deliverToListeners(new com.android.internal.listeners.ListenerExecutor.ListenerOperation() { // from class: com.android.server.location.gnss.GnssStatusProvider$$ExternalSyntheticLambda0
            public final void operate(java.lang.Object obj) {
                ((android.location.IGnssStatusListener) obj).onFirstFix(i);
            }
        });
    }

    @Override // com.android.server.location.gnss.hal.GnssNative.SvStatusCallbacks
    public void onReportSvStatus(final android.location.GnssStatus gnssStatus) {
        deliverToListeners(new java.util.function.Function() { // from class: com.android.server.location.gnss.GnssStatusProvider$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                com.android.internal.listeners.ListenerExecutor.ListenerOperation lambda$onReportSvStatus$2;
                lambda$onReportSvStatus$2 = com.android.server.location.gnss.GnssStatusProvider.this.lambda$onReportSvStatus$2(gnssStatus, (com.android.server.location.gnss.GnssListenerMultiplexer.GnssListenerRegistration) obj);
                return lambda$onReportSvStatus$2;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ com.android.internal.listeners.ListenerExecutor.ListenerOperation lambda$onReportSvStatus$2(final android.location.GnssStatus gnssStatus, com.android.server.location.gnss.GnssListenerMultiplexer.GnssListenerRegistration gnssListenerRegistration) {
        if (this.mAppOpsHelper.noteOpNoThrow(1, gnssListenerRegistration.getIdentity())) {
            return new com.android.internal.listeners.ListenerExecutor.ListenerOperation() { // from class: com.android.server.location.gnss.GnssStatusProvider$$ExternalSyntheticLambda1
                public final void operate(java.lang.Object obj) {
                    ((android.location.IGnssStatusListener) obj).onSvStatusChanged(gnssStatus);
                }
            };
        }
        return null;
    }
}
