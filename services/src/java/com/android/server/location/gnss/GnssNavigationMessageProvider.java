package com.android.server.location.gnss;

/* loaded from: classes2.dex */
public class GnssNavigationMessageProvider extends com.android.server.location.gnss.GnssListenerMultiplexer<java.lang.Void, android.location.IGnssNavigationMessageListener, java.lang.Void> implements com.android.server.location.gnss.hal.GnssNative.BaseCallbacks, com.android.server.location.gnss.hal.GnssNative.NavigationMessageCallbacks {
    private final com.android.server.location.injector.AppOpsHelper mAppOpsHelper;
    private final com.android.server.location.gnss.hal.GnssNative mGnssNative;

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    protected /* bridge */ /* synthetic */ boolean registerWithService(java.lang.Object obj, java.util.Collection collection) {
        return registerWithService((java.lang.Void) obj, (java.util.Collection<com.android.server.location.gnss.GnssListenerMultiplexer<java.lang.Void, android.location.IGnssNavigationMessageListener, java.lang.Void>.GnssListenerRegistration>) collection);
    }

    /* JADX INFO: Access modifiers changed from: private */
    class GnssNavigationMessageListenerRegistration extends com.android.server.location.gnss.GnssListenerMultiplexer<java.lang.Void, android.location.IGnssNavigationMessageListener, java.lang.Void>.GnssListenerRegistration {
        protected GnssNavigationMessageListenerRegistration(android.location.util.identity.CallerIdentity callerIdentity, android.location.IGnssNavigationMessageListener iGnssNavigationMessageListener) {
            super(null, callerIdentity, iGnssNavigationMessageListener);
        }

        @Override // com.android.server.location.gnss.GnssListenerMultiplexer.GnssListenerRegistration, com.android.server.location.listeners.BinderListenerRegistration, com.android.server.location.listeners.RemovableListenerRegistration
        protected void onRegister() {
            super.onRegister();
            executeOperation(new com.android.internal.listeners.ListenerExecutor.ListenerOperation() { // from class: com.android.server.location.gnss.GnssNavigationMessageProvider$GnssNavigationMessageListenerRegistration$$ExternalSyntheticLambda0
                public final void operate(java.lang.Object obj) {
                    ((android.location.IGnssNavigationMessageListener) obj).onStatusChanged(1);
                }
            });
        }
    }

    public GnssNavigationMessageProvider(com.android.server.location.injector.Injector injector, com.android.server.location.gnss.hal.GnssNative gnssNative) {
        super(injector);
        this.mAppOpsHelper = injector.getAppOpsHelper();
        this.mGnssNative = gnssNative;
        this.mGnssNative.addBaseCallbacks(this);
        this.mGnssNative.addNavigationMessageCallbacks(this);
    }

    @Override // com.android.server.location.gnss.GnssListenerMultiplexer
    public boolean isSupported() {
        return this.mGnssNative.isNavigationMessageCollectionSupported();
    }

    @Override // com.android.server.location.gnss.GnssListenerMultiplexer
    public void addListener(android.location.util.identity.CallerIdentity callerIdentity, android.location.IGnssNavigationMessageListener iGnssNavigationMessageListener) {
        super.addListener(callerIdentity, (android.location.util.identity.CallerIdentity) iGnssNavigationMessageListener);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.location.gnss.GnssListenerMultiplexer
    public com.android.server.location.gnss.GnssListenerMultiplexer<java.lang.Void, android.location.IGnssNavigationMessageListener, java.lang.Void>.GnssListenerRegistration createRegistration(java.lang.Void r1, android.location.util.identity.CallerIdentity callerIdentity, android.location.IGnssNavigationMessageListener iGnssNavigationMessageListener) {
        return new com.android.server.location.gnss.GnssNavigationMessageProvider.GnssNavigationMessageListenerRegistration(callerIdentity, iGnssNavigationMessageListener);
    }

    protected boolean registerWithService(java.lang.Void r1, java.util.Collection<com.android.server.location.gnss.GnssListenerMultiplexer<java.lang.Void, android.location.IGnssNavigationMessageListener, java.lang.Void>.GnssListenerRegistration> collection) {
        if (this.mGnssNative.startNavigationMessageCollection()) {
            if (com.android.server.location.gnss.GnssManagerService.D) {
                android.util.Log.d(com.android.server.location.gnss.GnssManagerService.TAG, "starting gnss navigation messages");
                return true;
            }
            return true;
        }
        android.util.Log.e(com.android.server.location.gnss.GnssManagerService.TAG, "error starting gnss navigation messages");
        return false;
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    protected void unregisterWithService() {
        if (this.mGnssNative.stopNavigationMessageCollection()) {
            if (com.android.server.location.gnss.GnssManagerService.D) {
                android.util.Log.d(com.android.server.location.gnss.GnssManagerService.TAG, "stopping gnss navigation messages");
                return;
            }
            return;
        }
        android.util.Log.e(com.android.server.location.gnss.GnssManagerService.TAG, "error stopping gnss navigation messages");
    }

    @Override // com.android.server.location.gnss.hal.GnssNative.BaseCallbacks
    public void onHalRestarted() {
        resetService();
    }

    @Override // com.android.server.location.gnss.hal.GnssNative.NavigationMessageCallbacks
    public void onReportNavigationMessage(final android.location.GnssNavigationMessage gnssNavigationMessage) {
        deliverToListeners(new java.util.function.Function() { // from class: com.android.server.location.gnss.GnssNavigationMessageProvider$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                com.android.internal.listeners.ListenerExecutor.ListenerOperation lambda$onReportNavigationMessage$1;
                lambda$onReportNavigationMessage$1 = com.android.server.location.gnss.GnssNavigationMessageProvider.this.lambda$onReportNavigationMessage$1(gnssNavigationMessage, (com.android.server.location.gnss.GnssListenerMultiplexer.GnssListenerRegistration) obj);
                return lambda$onReportNavigationMessage$1;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ com.android.internal.listeners.ListenerExecutor.ListenerOperation lambda$onReportNavigationMessage$1(final android.location.GnssNavigationMessage gnssNavigationMessage, com.android.server.location.gnss.GnssListenerMultiplexer.GnssListenerRegistration gnssListenerRegistration) {
        if (this.mAppOpsHelper.noteOpNoThrow(1, gnssListenerRegistration.getIdentity())) {
            return new com.android.internal.listeners.ListenerExecutor.ListenerOperation() { // from class: com.android.server.location.gnss.GnssNavigationMessageProvider$$ExternalSyntheticLambda0
                public final void operate(java.lang.Object obj) {
                    ((android.location.IGnssNavigationMessageListener) obj).onGnssNavigationMessageReceived(gnssNavigationMessage);
                }
            };
        }
        return null;
    }
}
