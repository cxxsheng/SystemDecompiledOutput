package com.android.server.location.gnss;

/* loaded from: classes2.dex */
class GnssNmeaProvider extends com.android.server.location.gnss.GnssListenerMultiplexer<java.lang.Void, android.location.IGnssNmeaListener, java.lang.Void> implements com.android.server.location.gnss.hal.GnssNative.BaseCallbacks, com.android.server.location.gnss.hal.GnssNative.NmeaCallbacks {
    private final com.android.server.location.injector.AppOpsHelper mAppOpsHelper;
    private final com.android.server.location.gnss.hal.GnssNative mGnssNative;
    private final byte[] mNmeaBuffer;

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    protected /* bridge */ /* synthetic */ boolean registerWithService(java.lang.Object obj, java.util.Collection collection) {
        return registerWithService((java.lang.Void) obj, (java.util.Collection<com.android.server.location.gnss.GnssListenerMultiplexer<java.lang.Void, android.location.IGnssNmeaListener, java.lang.Void>.GnssListenerRegistration>) collection);
    }

    GnssNmeaProvider(com.android.server.location.injector.Injector injector, com.android.server.location.gnss.hal.GnssNative gnssNative) {
        super(injector);
        this.mNmeaBuffer = new byte[120];
        this.mAppOpsHelper = injector.getAppOpsHelper();
        this.mGnssNative = gnssNative;
        this.mGnssNative.addBaseCallbacks(this);
        this.mGnssNative.addNmeaCallbacks(this);
    }

    @Override // com.android.server.location.gnss.GnssListenerMultiplexer
    public void addListener(android.location.util.identity.CallerIdentity callerIdentity, android.location.IGnssNmeaListener iGnssNmeaListener) {
        super.addListener(callerIdentity, (android.location.util.identity.CallerIdentity) iGnssNmeaListener);
    }

    protected boolean registerWithService(java.lang.Void r1, java.util.Collection<com.android.server.location.gnss.GnssListenerMultiplexer<java.lang.Void, android.location.IGnssNmeaListener, java.lang.Void>.GnssListenerRegistration> collection) {
        if (this.mGnssNative.startNmeaMessageCollection()) {
            if (com.android.server.location.gnss.GnssManagerService.D) {
                android.util.Log.d(com.android.server.location.gnss.GnssManagerService.TAG, "starting gnss nmea messages collection");
                return true;
            }
            return true;
        }
        android.util.Log.e(com.android.server.location.gnss.GnssManagerService.TAG, "error starting gnss nmea messages collection");
        return false;
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    protected void unregisterWithService() {
        if (this.mGnssNative.stopNmeaMessageCollection()) {
            if (com.android.server.location.gnss.GnssManagerService.D) {
                android.util.Log.d(com.android.server.location.gnss.GnssManagerService.TAG, "stopping gnss nmea messages collection");
                return;
            }
            return;
        }
        android.util.Log.e(com.android.server.location.gnss.GnssManagerService.TAG, "error stopping gnss nmea messages collection");
    }

    @Override // com.android.server.location.gnss.hal.GnssNative.BaseCallbacks
    public void onHalRestarted() {
        resetService();
    }

    @Override // com.android.server.location.gnss.hal.GnssNative.NmeaCallbacks
    public void onReportNmea(long j) {
        deliverToListeners(new com.android.server.location.gnss.GnssNmeaProvider.AnonymousClass1(j));
    }

    /* renamed from: com.android.server.location.gnss.GnssNmeaProvider$1, reason: invalid class name */
    class AnonymousClass1 implements java.util.function.Function<com.android.server.location.gnss.GnssListenerMultiplexer<java.lang.Void, android.location.IGnssNmeaListener, java.lang.Void>.GnssListenerRegistration, com.android.internal.listeners.ListenerExecutor.ListenerOperation<android.location.IGnssNmeaListener>> {

        @android.annotation.Nullable
        private java.lang.String mNmea;
        final /* synthetic */ long val$timestamp;

        AnonymousClass1(long j) {
            this.val$timestamp = j;
        }

        @Override // java.util.function.Function
        public com.android.internal.listeners.ListenerExecutor.ListenerOperation<android.location.IGnssNmeaListener> apply(com.android.server.location.gnss.GnssListenerMultiplexer<java.lang.Void, android.location.IGnssNmeaListener, java.lang.Void>.GnssListenerRegistration gnssListenerRegistration) {
            if (com.android.server.location.gnss.GnssNmeaProvider.this.mAppOpsHelper.noteOpNoThrow(1, gnssListenerRegistration.getIdentity())) {
                if (this.mNmea == null) {
                    this.mNmea = new java.lang.String(com.android.server.location.gnss.GnssNmeaProvider.this.mNmeaBuffer, 0, com.android.server.location.gnss.GnssNmeaProvider.this.mGnssNative.readNmea(com.android.server.location.gnss.GnssNmeaProvider.this.mNmeaBuffer, com.android.server.location.gnss.GnssNmeaProvider.this.mNmeaBuffer.length));
                }
                final long j = this.val$timestamp;
                return new com.android.internal.listeners.ListenerExecutor.ListenerOperation() { // from class: com.android.server.location.gnss.GnssNmeaProvider$1$$ExternalSyntheticLambda0
                    public final void operate(java.lang.Object obj) {
                        com.android.server.location.gnss.GnssNmeaProvider.AnonymousClass1.this.lambda$apply$0(j, (android.location.IGnssNmeaListener) obj);
                    }
                };
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$apply$0(long j, android.location.IGnssNmeaListener iGnssNmeaListener) throws java.lang.Exception {
            iGnssNmeaListener.onNmeaReceived(j, this.mNmea);
        }
    }
}
