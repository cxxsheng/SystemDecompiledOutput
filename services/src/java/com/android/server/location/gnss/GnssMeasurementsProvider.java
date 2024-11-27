package com.android.server.location.gnss;

/* loaded from: classes2.dex */
public final class GnssMeasurementsProvider extends com.android.server.location.gnss.GnssListenerMultiplexer<android.location.GnssMeasurementRequest, android.location.IGnssMeasurementsListener, android.location.GnssMeasurementRequest> implements com.android.server.location.injector.SettingsHelper.GlobalSettingChangedListener, com.android.server.location.gnss.hal.GnssNative.BaseCallbacks, com.android.server.location.gnss.hal.GnssNative.MeasurementCallbacks {
    private final com.android.server.location.injector.AppOpsHelper mAppOpsHelper;
    private final com.android.server.location.gnss.hal.GnssNative mGnssNative;

    @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
    private android.location.GnssMeasurementsEvent mLastGnssMeasurementsEvent;
    private final com.android.server.location.injector.LocationUsageLogger mLogger;

    @Override // com.android.server.location.gnss.GnssListenerMultiplexer, com.android.server.location.listeners.ListenerMultiplexer
    protected /* bridge */ /* synthetic */ java.lang.Object mergeRegistrations(java.util.Collection collection) {
        return mergeRegistrations((java.util.Collection<com.android.server.location.gnss.GnssListenerMultiplexer<android.location.GnssMeasurementRequest, android.location.IGnssMeasurementsListener, android.location.GnssMeasurementRequest>.GnssListenerRegistration>) collection);
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    protected /* bridge */ /* synthetic */ boolean registerWithService(java.lang.Object obj, java.util.Collection collection) {
        return registerWithService((android.location.GnssMeasurementRequest) obj, (java.util.Collection<com.android.server.location.gnss.GnssListenerMultiplexer<android.location.GnssMeasurementRequest, android.location.IGnssMeasurementsListener, android.location.GnssMeasurementRequest>.GnssListenerRegistration>) collection);
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    protected /* bridge */ /* synthetic */ boolean reregisterWithService(java.lang.Object obj, java.lang.Object obj2, @android.annotation.NonNull java.util.Collection collection) {
        return reregisterWithService((android.location.GnssMeasurementRequest) obj, (android.location.GnssMeasurementRequest) obj2, (java.util.Collection<com.android.server.location.gnss.GnssListenerMultiplexer<android.location.GnssMeasurementRequest, android.location.IGnssMeasurementsListener, android.location.GnssMeasurementRequest>.GnssListenerRegistration>) collection);
    }

    /* JADX INFO: Access modifiers changed from: private */
    class GnssMeasurementListenerRegistration extends com.android.server.location.gnss.GnssListenerMultiplexer<android.location.GnssMeasurementRequest, android.location.IGnssMeasurementsListener, android.location.GnssMeasurementRequest>.GnssListenerRegistration {
        protected GnssMeasurementListenerRegistration(@android.annotation.Nullable android.location.GnssMeasurementRequest gnssMeasurementRequest, android.location.util.identity.CallerIdentity callerIdentity, android.location.IGnssMeasurementsListener iGnssMeasurementsListener) {
            super(gnssMeasurementRequest, callerIdentity, iGnssMeasurementsListener);
        }

        @Override // com.android.server.location.gnss.GnssListenerMultiplexer.GnssListenerRegistration, com.android.server.location.listeners.BinderListenerRegistration, com.android.server.location.listeners.RemovableListenerRegistration
        protected void onRegister() {
            super.onRegister();
            com.android.server.location.eventlog.LocationEventLog.EVENT_LOG.logGnssMeasurementClientRegistered(getIdentity(), (android.location.GnssMeasurementRequest) getRequest());
            executeOperation(new com.android.internal.listeners.ListenerExecutor.ListenerOperation() { // from class: com.android.server.location.gnss.GnssMeasurementsProvider$GnssMeasurementListenerRegistration$$ExternalSyntheticLambda0
                public final void operate(java.lang.Object obj) {
                    ((android.location.IGnssMeasurementsListener) obj).onStatusChanged(1);
                }
            });
        }

        @Override // com.android.server.location.listeners.BinderListenerRegistration, com.android.server.location.listeners.RemovableListenerRegistration, com.android.server.location.listeners.ListenerRegistration
        protected void onUnregister() {
            com.android.server.location.eventlog.LocationEventLog.EVENT_LOG.logGnssMeasurementClientUnregistered(getIdentity());
            super.onUnregister();
        }

        @Override // com.android.server.location.listeners.ListenerRegistration
        @android.annotation.Nullable
        protected void onActive() {
            com.android.server.location.gnss.GnssMeasurementsProvider.this.mAppOpsHelper.startOpNoThrow(42, getIdentity());
        }

        @Override // com.android.server.location.listeners.ListenerRegistration
        @android.annotation.Nullable
        protected void onInactive() {
            com.android.server.location.gnss.GnssMeasurementsProvider.this.mAppOpsHelper.finishOp(42, getIdentity());
        }
    }

    public GnssMeasurementsProvider(com.android.server.location.injector.Injector injector, com.android.server.location.gnss.hal.GnssNative gnssNative) {
        super(injector);
        this.mAppOpsHelper = injector.getAppOpsHelper();
        this.mLogger = injector.getLocationUsageLogger();
        this.mGnssNative = gnssNative;
        this.mGnssNative.addBaseCallbacks(this);
        this.mGnssNative.addMeasurementCallbacks(this);
    }

    @Override // com.android.server.location.gnss.GnssListenerMultiplexer
    public boolean isSupported() {
        return this.mGnssNative.isMeasurementSupported();
    }

    @Override // com.android.server.location.gnss.GnssListenerMultiplexer
    public void addListener(android.location.GnssMeasurementRequest gnssMeasurementRequest, android.location.util.identity.CallerIdentity callerIdentity, android.location.IGnssMeasurementsListener iGnssMeasurementsListener) {
        super.addListener((com.android.server.location.gnss.GnssMeasurementsProvider) gnssMeasurementRequest, callerIdentity, (android.location.util.identity.CallerIdentity) iGnssMeasurementsListener);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.location.gnss.GnssListenerMultiplexer
    public com.android.server.location.gnss.GnssListenerMultiplexer<android.location.GnssMeasurementRequest, android.location.IGnssMeasurementsListener, android.location.GnssMeasurementRequest>.GnssListenerRegistration createRegistration(android.location.GnssMeasurementRequest gnssMeasurementRequest, android.location.util.identity.CallerIdentity callerIdentity, android.location.IGnssMeasurementsListener iGnssMeasurementsListener) {
        return new com.android.server.location.gnss.GnssMeasurementsProvider.GnssMeasurementListenerRegistration(gnssMeasurementRequest, callerIdentity, iGnssMeasurementsListener);
    }

    protected boolean registerWithService(android.location.GnssMeasurementRequest gnssMeasurementRequest, java.util.Collection<com.android.server.location.gnss.GnssListenerMultiplexer<android.location.GnssMeasurementRequest, android.location.IGnssMeasurementsListener, android.location.GnssMeasurementRequest>.GnssListenerRegistration> collection) {
        if (gnssMeasurementRequest.getIntervalMillis() == Integer.MAX_VALUE) {
            return true;
        }
        if (this.mGnssNative.startMeasurementCollection(gnssMeasurementRequest.isFullTracking(), gnssMeasurementRequest.isCorrelationVectorOutputsEnabled(), gnssMeasurementRequest.getIntervalMillis())) {
            if (com.android.server.location.gnss.GnssManagerService.D) {
                android.util.Log.d(com.android.server.location.gnss.GnssManagerService.TAG, "starting gnss measurements (" + gnssMeasurementRequest + ")");
            }
            return true;
        }
        android.util.Log.e(com.android.server.location.gnss.GnssManagerService.TAG, "error starting gnss measurements");
        return false;
    }

    protected boolean reregisterWithService(android.location.GnssMeasurementRequest gnssMeasurementRequest, android.location.GnssMeasurementRequest gnssMeasurementRequest2, @android.annotation.NonNull java.util.Collection<com.android.server.location.gnss.GnssListenerMultiplexer<android.location.GnssMeasurementRequest, android.location.IGnssMeasurementsListener, android.location.GnssMeasurementRequest>.GnssListenerRegistration> collection) {
        if (gnssMeasurementRequest2.getIntervalMillis() == Integer.MAX_VALUE) {
            unregisterWithService();
            return true;
        }
        com.android.server.location.gnss.GnssConfiguration.HalInterfaceVersion halInterfaceVersion = this.mGnssNative.getConfiguration().getHalInterfaceVersion();
        if (!(halInterfaceVersion.mMajor == 3 && halInterfaceVersion.mMinor >= 3)) {
            unregisterWithService();
        }
        return registerWithService(gnssMeasurementRequest2, collection);
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    protected void unregisterWithService() {
        if (this.mGnssNative.stopMeasurementCollection()) {
            if (com.android.server.location.gnss.GnssManagerService.D) {
                android.util.Log.d(com.android.server.location.gnss.GnssManagerService.TAG, "stopping gnss measurements");
                return;
            }
            return;
        }
        android.util.Log.e(com.android.server.location.gnss.GnssManagerService.TAG, "error stopping gnss measurements");
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    protected void onActive() {
        this.mSettingsHelper.addOnGnssMeasurementsFullTrackingEnabledChangedListener(this);
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    protected void onInactive() {
        this.mSettingsHelper.removeOnGnssMeasurementsFullTrackingEnabledChangedListener(this);
    }

    @Override // com.android.server.location.injector.SettingsHelper.GlobalSettingChangedListener
    public void onSettingChanged() {
        updateService();
    }

    @Override // com.android.server.location.gnss.GnssListenerMultiplexer, com.android.server.location.listeners.ListenerMultiplexer
    protected android.location.GnssMeasurementRequest mergeRegistrations(java.util.Collection<com.android.server.location.gnss.GnssListenerMultiplexer<android.location.GnssMeasurementRequest, android.location.IGnssMeasurementsListener, android.location.GnssMeasurementRequest>.GnssListenerRegistration> collection) {
        boolean z;
        boolean z2 = false;
        if (!this.mSettingsHelper.isGnssMeasurementsFullTrackingEnabled()) {
            z = false;
        } else {
            z = true;
        }
        java.util.Iterator<com.android.server.location.gnss.GnssListenerMultiplexer<android.location.GnssMeasurementRequest, android.location.IGnssMeasurementsListener, android.location.GnssMeasurementRequest>.GnssListenerRegistration> it = collection.iterator();
        int i = Integer.MAX_VALUE;
        while (it.hasNext()) {
            android.location.GnssMeasurementRequest request = it.next().getRequest();
            if (request.getIntervalMillis() != Integer.MAX_VALUE) {
                if (request.isFullTracking()) {
                    z = true;
                }
                if (request.isCorrelationVectorOutputsEnabled()) {
                    z2 = true;
                }
                i = java.lang.Math.min(i, request.getIntervalMillis());
            }
        }
        return new android.location.GnssMeasurementRequest.Builder().setFullTracking(z).setCorrelationVectorOutputsEnabled(z2).setIntervalMillis(i).build();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public void onRegistrationAdded(android.os.IBinder iBinder, com.android.server.location.gnss.GnssListenerMultiplexer<android.location.GnssMeasurementRequest, android.location.IGnssMeasurementsListener, android.location.GnssMeasurementRequest>.GnssListenerRegistration gnssListenerRegistration) {
        this.mLogger.logLocationApiUsage(0, 2, gnssListenerRegistration.getIdentity().getPackageName(), gnssListenerRegistration.getIdentity().getAttributionTag(), null, null, true, false, null, gnssListenerRegistration.isForeground());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public void onRegistrationRemoved(android.os.IBinder iBinder, com.android.server.location.gnss.GnssListenerMultiplexer<android.location.GnssMeasurementRequest, android.location.IGnssMeasurementsListener, android.location.GnssMeasurementRequest>.GnssListenerRegistration gnssListenerRegistration) {
        this.mLogger.logLocationApiUsage(1, 2, gnssListenerRegistration.getIdentity().getPackageName(), gnssListenerRegistration.getIdentity().getAttributionTag(), null, null, true, false, null, gnssListenerRegistration.isForeground());
    }

    @Override // com.android.server.location.gnss.hal.GnssNative.BaseCallbacks
    public void onHalRestarted() {
        resetService();
    }

    @Override // com.android.server.location.gnss.hal.GnssNative.MeasurementCallbacks
    public void onReportMeasurements(final android.location.GnssMeasurementsEvent gnssMeasurementsEvent) {
        deliverToListeners(new java.util.function.Function() { // from class: com.android.server.location.gnss.GnssMeasurementsProvider$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                com.android.internal.listeners.ListenerExecutor.ListenerOperation lambda$onReportMeasurements$1;
                lambda$onReportMeasurements$1 = com.android.server.location.gnss.GnssMeasurementsProvider.this.lambda$onReportMeasurements$1(gnssMeasurementsEvent, (com.android.server.location.gnss.GnssListenerMultiplexer.GnssListenerRegistration) obj);
                return lambda$onReportMeasurements$1;
            }
        });
        synchronized (this.mMultiplexerLock) {
            this.mLastGnssMeasurementsEvent = gnssMeasurementsEvent;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ com.android.internal.listeners.ListenerExecutor.ListenerOperation lambda$onReportMeasurements$1(final android.location.GnssMeasurementsEvent gnssMeasurementsEvent, com.android.server.location.gnss.GnssListenerMultiplexer.GnssListenerRegistration gnssListenerRegistration) {
        if (this.mAppOpsHelper.noteOpNoThrow(1, gnssListenerRegistration.getIdentity())) {
            com.android.server.location.eventlog.LocationEventLog.EVENT_LOG.logGnssMeasurementsDelivered(gnssMeasurementsEvent.getMeasurements().size(), gnssListenerRegistration.getIdentity());
            return new com.android.internal.listeners.ListenerExecutor.ListenerOperation() { // from class: com.android.server.location.gnss.GnssMeasurementsProvider$$ExternalSyntheticLambda1
                public final void operate(java.lang.Object obj) {
                    ((android.location.IGnssMeasurementsListener) obj).onGnssMeasurementsReceived(gnssMeasurementsEvent);
                }
            };
        }
        return null;
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        super.dump(fileDescriptor, printWriter, strArr);
        printWriter.print("last measurements=");
        printWriter.println(getLastMeasurementEventSummary());
    }

    private java.lang.String getLastMeasurementEventSummary() {
        synchronized (this.mMultiplexerLock) {
            try {
                if (this.mLastGnssMeasurementsEvent == null) {
                    return null;
                }
                java.lang.StringBuilder sb = new java.lang.StringBuilder("[");
                sb.append("elapsedRealtimeNs=");
                sb.append(this.mLastGnssMeasurementsEvent.getClock().getElapsedRealtimeNanos());
                sb.append(" measurementCount=");
                sb.append(this.mLastGnssMeasurementsEvent.getMeasurements().size());
                float f = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
                int i = 0;
                for (android.location.GnssMeasurement gnssMeasurement : this.mLastGnssMeasurementsEvent.getMeasurements()) {
                    if (gnssMeasurement.hasBasebandCn0DbHz()) {
                        f = (float) (f + gnssMeasurement.getBasebandCn0DbHz());
                        i++;
                    }
                }
                if (i > 0) {
                    sb.append(" avgBasebandCn0=");
                    sb.append(f / i);
                }
                sb.append("]");
                return sb.toString();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }
}
