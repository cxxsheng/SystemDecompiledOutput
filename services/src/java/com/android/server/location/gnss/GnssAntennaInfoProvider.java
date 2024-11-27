package com.android.server.location.gnss;

/* loaded from: classes2.dex */
public class GnssAntennaInfoProvider extends com.android.server.location.listeners.ListenerMultiplexer<android.os.IBinder, android.location.IGnssAntennaInfoListener, com.android.server.location.listeners.ListenerRegistration<android.location.IGnssAntennaInfoListener>, java.lang.Void> implements com.android.server.location.gnss.hal.GnssNative.BaseCallbacks, com.android.server.location.gnss.hal.GnssNative.AntennaInfoCallbacks {

    @android.annotation.Nullable
    private volatile java.util.List<android.location.GnssAntennaInfo> mAntennaInfos;
    private final com.android.server.location.gnss.hal.GnssNative mGnssNative;

    protected class AntennaInfoListenerRegistration extends com.android.server.location.listeners.BinderListenerRegistration<android.os.IBinder, android.location.IGnssAntennaInfoListener> {
        private final android.location.util.identity.CallerIdentity mIdentity;

        protected AntennaInfoListenerRegistration(android.location.util.identity.CallerIdentity callerIdentity, android.location.IGnssAntennaInfoListener iGnssAntennaInfoListener) {
            super(callerIdentity.isMyProcess() ? com.android.server.FgThread.getExecutor() : com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, iGnssAntennaInfoListener);
            this.mIdentity = callerIdentity;
        }

        @Override // com.android.server.location.listeners.ListenerRegistration
        protected java.lang.String getTag() {
            return com.android.server.location.gnss.GnssManagerService.TAG;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.android.server.location.listeners.RemovableListenerRegistration
        public com.android.server.location.gnss.GnssAntennaInfoProvider getOwner() {
            return com.android.server.location.gnss.GnssAntennaInfoProvider.this;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.android.server.location.listeners.BinderListenerRegistration
        public android.os.IBinder getBinderFromKey(android.os.IBinder iBinder) {
            return iBinder;
        }

        @Override // com.android.server.location.listeners.ListenerRegistration
        public java.lang.String toString() {
            return this.mIdentity.toString();
        }
    }

    GnssAntennaInfoProvider(com.android.server.location.gnss.hal.GnssNative gnssNative) {
        this.mGnssNative = gnssNative;
        this.mGnssNative.addBaseCallbacks(this);
        this.mGnssNative.addAntennaInfoCallbacks(this);
    }

    @android.annotation.Nullable
    java.util.List<android.location.GnssAntennaInfo> getAntennaInfos() {
        return this.mAntennaInfos;
    }

    public boolean isSupported() {
        return this.mGnssNative.isAntennaInfoSupported();
    }

    public void addListener(android.location.util.identity.CallerIdentity callerIdentity, android.location.IGnssAntennaInfoListener iGnssAntennaInfoListener) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            putRegistration(iGnssAntennaInfoListener.asBinder(), new com.android.server.location.gnss.GnssAntennaInfoProvider.AntennaInfoListenerRegistration(callerIdentity, iGnssAntennaInfoListener));
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void removeListener(android.location.IGnssAntennaInfoListener iGnssAntennaInfoListener) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            removeRegistration((com.android.server.location.gnss.GnssAntennaInfoProvider) iGnssAntennaInfoListener.asBinder());
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public boolean registerWithService(java.lang.Void r1, java.util.Collection<com.android.server.location.listeners.ListenerRegistration<android.location.IGnssAntennaInfoListener>> collection) {
        return true;
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    protected void unregisterWithService() {
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    protected boolean isActive(com.android.server.location.listeners.ListenerRegistration<android.location.IGnssAntennaInfoListener> listenerRegistration) {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public java.lang.Void mergeRegistrations(java.util.Collection<com.android.server.location.listeners.ListenerRegistration<android.location.IGnssAntennaInfoListener>> collection) {
        return null;
    }

    @Override // com.android.server.location.gnss.hal.GnssNative.BaseCallbacks
    public void onHalStarted() {
        this.mGnssNative.startAntennaInfoListening();
    }

    @Override // com.android.server.location.gnss.hal.GnssNative.BaseCallbacks
    public void onHalRestarted() {
        this.mGnssNative.startAntennaInfoListening();
    }

    @Override // com.android.server.location.gnss.hal.GnssNative.AntennaInfoCallbacks
    public void onReportAntennaInfo(final java.util.List<android.location.GnssAntennaInfo> list) {
        if (list.equals(this.mAntennaInfos)) {
            return;
        }
        this.mAntennaInfos = list;
        deliverToListeners(new com.android.internal.listeners.ListenerExecutor.ListenerOperation() { // from class: com.android.server.location.gnss.GnssAntennaInfoProvider$$ExternalSyntheticLambda0
            public final void operate(java.lang.Object obj) {
                ((android.location.IGnssAntennaInfoListener) obj).onGnssAntennaInfoChanged(list);
            }
        });
    }
}
